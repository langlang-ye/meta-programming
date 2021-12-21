package com.langlang.sqlSession;

import com.langlang.executor.Executor;
import com.langlang.executor.SimpleExecutor;
import com.langlang.pojo.Configuration;

/**
 * @author langlang.ye
 * @date 2021/4/28
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Executor executor = new SimpleExecutor();
        return new DefaultSqlSession(configuration, executor);
    }
}
