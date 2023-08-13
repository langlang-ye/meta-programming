package com.langlang.sqlSession;

import com.langlang.executor.Executor;
import com.langlang.pojo.Configuration;
import com.langlang.pojo.MappedStatement;

import java.util.List;

/**
 * @author langlang.ye
 * @date 2021/4/28
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object param) throws Exception {
        // 将要去完成 executor 中的 query 方法的调用
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<E> list = executor.query(configuration, mappedStatement, param);

        return list;
    }

    @Override
    public <E> List<E> selectList(String statementId) throws Exception {
        return this.selectList(statementId, null);
    }

    @Override
    public <T> T selectOne(String statementId, Object param) throws Exception {
        List<T> lists = selectList(statementId, param);
        if (lists.size() == 1) {
            return lists.get(0);
        } else {
            throw new RuntimeException("查询结果集为空或者返回结果过多");
        }
    }

    @Override
    public <T> T selectOne(String statementId) throws Exception {
        return this.selectOne(statementId, null);
    }

    /**
     * 添加 删除 更新 都是调用 executor.update 方法, 最后的实现 preparedStatement.executeUpdate()
     * @param statementId
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public int update(String statementId, Object param) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        int update = executor.update(configuration, mappedStatement, param);
        return update;
    }

    @Override
    public int insert(String statementId, Object param) throws Exception {
        int insert = update(statementId, param);
        return insert;
    }

    @Override
    public int delete(String statementId, Object param) throws Exception {
        int delete = update(statementId, param);
        return delete;
    }

    @Override
    public <T> T getMapper(Class<?> clazz) {
        return configuration.getMapper(clazz, this);
    }


}
