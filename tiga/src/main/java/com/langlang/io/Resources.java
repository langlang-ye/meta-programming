package com.langlang.io;

import java.io.InputStream;

/**
 * @author langlang.ye
 * @date 2021/4/26
 */
public class Resources {

    /**
     * 类加载器把配置文件加载为字节流, 存储在内存
     * path: 配置文件的路径
      */
    public static InputStream getResourceAsStream(String path) {
        var resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }

}
