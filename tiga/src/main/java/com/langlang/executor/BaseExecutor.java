package com.langlang.executor;

import com.langlang.config.BoundSql;
import com.langlang.parsing.GenericTokenParser;
import com.langlang.parsing.ParameterMappingTokenHandler;
import com.langlang.pojo.Configuration;
import com.langlang.pojo.MappedStatement;
import com.langlang.mapping.ParameterMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author langlang.ye
 * @date 2021/5/8
 */
public abstract class BaseExecutor implements Executor {

    // 一级缓存
    private Map<String, Object> localCache = new HashMap<>(16);

    /**
     * 使用标记解析器来完成对占位符的解析处理: 1. #{} 替换 ? ; 2. 占位符里面的值封装到 boundSql 参数集合中
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();

        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);

        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);
        return boundSql;
    }

    /**
     * 创建一级缓存的key
     * @param mappedStatement
     * @param boundSql
     * @param params
     * @return
     */
    private String createCacheKey(MappedStatement mappedStatement, BoundSql boundSql, Object params) {
        // 简化版的key: id + [params] + sql
        // 实际 mybatis 是将 id、offset、limit、sql、Environment.id 以及对应的参数生成的
        if (params == null) {
            return mappedStatement.getId() + boundSql.getSqlText();
        }
        return mappedStatement.getId() + params.hashCode() + boundSql.getSqlText();
    }

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object params) throws Exception {
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        String key = createCacheKey(mappedStatement, boundSql, params); // 创建本次查询的key
        return query(configuration, mappedStatement, key, boundSql, params);
    }

    /**
     * 带有缓存的查询方法
     * 这里只实现了一级缓存, 如果有二级缓存, 应该先查询二级缓存, 再查询一级缓存, 最后才查询数据库
     * @param configuration
     * @param mappedStatement
     * @param key
     * @param boundSql
     * @param params
     * @param <E>
     * @return
     * @throws Exception
     */
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, String key, BoundSql boundSql, Object params) throws Exception {
        // 每次查询优先从缓存中查找, 缓存中没有再查询数据库
        List<E> list = (List<E>) localCache.get(key);
        if(list != null) {
            System.out.println("命中缓存...");
            return list;
        }
        return queryDB(configuration, mappedStatement, key, boundSql, params);
    }

    private <E> List<E> queryDB(Configuration configuration, MappedStatement mappedStatement, String key, BoundSql boundSql, Object params) throws Exception {
        List<E> list = doQuery(configuration, mappedStatement, boundSql, params);
        localCache.put(key, list);
        return list;
    }

    protected abstract <E> List<E> doQuery(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object params) throws Exception;


    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object params) throws Exception {
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        cleanCache(); // 执行增加 修改 删除操作, 需要清空缓存
        return doUpdate(configuration, mappedStatement, boundSql, params);
    }

    /**
     * 更新的接口: 具体实现由子类实现
     * @param configuration
     * @param mappedStatement
     * @param boundSql
     * @param params
     * @return
     * @throws Exception
     */
    protected abstract int doUpdate(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql, Object params) throws  Exception;

    /**
     * 清空缓存
     */
    private void cleanCache(){
        localCache.clear();
    }

}
