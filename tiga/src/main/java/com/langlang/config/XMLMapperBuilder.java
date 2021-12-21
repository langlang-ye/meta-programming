package com.langlang.config;

import com.langlang.pojo.Configuration;
import com.langlang.pojo.MappedStatement;
import com.langlang.pojo.SqlCommandType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

/**
 * @author langlang.ye
 * @date 2021/4/28
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public String parce(InputStream is) throws DocumentException {
        Document document = new SAXReader().read(is);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");

        List<Node> nodes = rootElement.selectNodes("//select|//insert|//update|//delete");
        for (Node node : nodes) {
            Element element = (Element) node;
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sql = element.getTextTrim();
            String elementName = element.getName();
            SqlCommandType sqlCommandType = SqlCommandType.valueOf(elementName.toUpperCase(Locale.ENGLISH));

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            mappedStatement.setSqlCommandType(sqlCommandType);

            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key, mappedStatement);

        }
        return namespace;
    }
}
