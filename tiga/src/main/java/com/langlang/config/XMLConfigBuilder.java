package com.langlang.config;

import cn.hutool.core.util.ClassUtil;
import com.langlang.io.Resources;
import com.langlang.pojo.Configuration;
import com.langlang.utils.ReflectionUtils;
import com.zaxxer.hikari.HikariDataSource;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author langlang.ye
 * @date 2021/4/28
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 将配置文件进行解析, 封装到 Configuration
     * @param is
     * @return
     */
    public Configuration parseConfig(InputStream is) throws DocumentException, PropertyVetoException, ClassNotFoundException {

        Document document = new SAXReader().read(is);
        // 获得 configuration 根结点
        Element rootElement = document.getRootElement();

        List<Element> list = rootElement.elements("dataSource").get(0).elements("property");
        Properties properties = new Properties(); // 存储解析的数据库连接所需的信息
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(properties.getProperty("driverClass"));
        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(dataSource);
        // List<Element> mapperList = rootElement.elements("mapper");

        // mapper.xml 解析: 获得路径-->字节输入流-->dom4j进行解析
        Element mappers = rootElement.element("mappers");
        List<Element> elements = mappers.elements();
        for (Element mapper : elements) {
            if ("package".equals(mapper.getName())) {
                String packageName = mapper.attributeValue("name");
                parseXmlMappers(packageName);
                configuration.addMappers(packageName);
            } else {
                String mapperPath = mapper.attributeValue("resource");
                String namespace = parseXmlMapper(mapperPath);
                Class<?> classType = ReflectionUtils.classForName(namespace);
                configuration.addMapper(classType);
            }
        }

        // 解析别名
        Element typeAliases = rootElement.element("typeAliases");
        if (typeAliases != null) {
            List<Element> aliasesList = typeAliases.elements();
            for (Element element : aliasesList) {
                if ("package".equals(element.getName())) {
                    String packageName = element.attributeValue("name");
                    configuration.addTypeAlias(packageName);
                } else {
                    String classType = element.attributeValue("type");
                    String alias = element.attributeValue("alias");
                    configuration.addTypeAlias(alias, classType);
                }
            }

        }
        return configuration;
    }

    private String parseXmlMapper(String mapperPath) throws DocumentException, ClassNotFoundException {
        InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        String namespace = xmlMapperBuilder.parce(resourceAsStream);

        Class<?> type = ReflectionUtils.classForName(namespace);
        MapperAnnotationBuilder parser = new MapperAnnotationBuilder(configuration, type);
        parser.parse();
        return namespace;
    }

    /**
     * 由包的名称拼接出 mapper.xml 的路径
     * aa.bb.cc.XXMapper.xml --> aa/bb/cc/XXMapper.xml
     * @param packageName
     */
    private void parseXmlMappers(String packageName) throws DocumentException, ClassNotFoundException {
        Set<Class<?>> classes = ClassUtil.scanPackage(packageName);
        for (Class<?> tClass : classes) {
            String name = tClass.getName();
            String mapperPath = name.replace(".", "/") + ".xml";

            InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            String namespace = xmlMapperBuilder.parce(resourceAsStream);

            Class<?> type = ReflectionUtils.classForName(namespace);
            MapperAnnotationBuilder parser = new MapperAnnotationBuilder(configuration, type);
            parser.parse();


        }
    }

}
