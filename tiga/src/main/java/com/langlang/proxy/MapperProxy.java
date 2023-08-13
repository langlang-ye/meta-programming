package com.langlang.proxy;

import com.langlang.binding.MapperMethod;
import com.langlang.sqlSession.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author langlang.ye
 * @date 2021/5/15
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {

    private SqlSession sqlSession;

    private Class<T> mapperInterface;

    private Map<Method, MapperMethod> methodCache;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    private MapperMethod cachedMapperMethod(Method method) {
        return methodCache.computeIfAbsent(method, k -> new MapperMethod(sqlSession.getConfiguration(), mapperInterface, method));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object... param) throws Throwable {
        // 获取 mapperMethod 对象并执行sql
        final MapperMethod mapperMethod = cachedMapperMethod(method);
        return mapperMethod.execute(sqlSession, param);
    }

}
