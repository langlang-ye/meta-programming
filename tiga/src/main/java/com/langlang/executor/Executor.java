package com.langlang.executor;

import com.langlang.pojo.Configuration;
import com.langlang.pojo.MappedStatement;

import java.util.List;

/**
 * @author langlang.ye
 * @date 2021/4/28
 */
public interface Executor {

    /**
     * 执行查询的接口:
     * @param configuration
     * @param mappedStatement
     * @param params
     * @param <E>
     * @return
     * @throws Exception
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    /**
     * insert update delete 可以统一对外提供一个接口
     * 执行 添加/修改/删除的 接口
     * @param configuration
     * @param mappedStatement
     * @param params
     * @return
     * @throws Exception
     */
    int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

}
