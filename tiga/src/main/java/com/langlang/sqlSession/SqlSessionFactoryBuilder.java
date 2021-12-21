package com.langlang.sqlSession;

import com.langlang.config.XMLConfigBuilder;
import com.langlang.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author langlang.ye
 * @date 2021/4/28
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream is) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        // 使用 dom4j 解析配置文件, 将解析出来的内容封装到 Configuration 中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(is);

        // 创建 SqlSessionFactory 对象: 工厂类: 生产SqlSession: 会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }
}
