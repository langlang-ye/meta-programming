package com.ykl.test;

import com.langlang.io.Resources;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author langlang.ye
 * @date 2021/4/29
 */
public class TestBoundSql {

    public static void main(String[] args) throws Exception {
        InputStream is = Resources.getResourceAsStream("PersonMapper.xml");

        Document document = new SAXReader().read(is);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");

        List<Node> list = rootElement.selectNodes("//select");
        //
    }
}
