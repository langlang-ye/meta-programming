package com.langlang.plugin;

import java.util.Properties;

public interface Interceptor {

    //执行代理类方法
    Object intercept(Invocation invocation) throws Throwable;
    // 用于创建代理对象
    Object plugin(Object target);
    // 插件自定义属性
    void setProperties(Properties properties);
}
