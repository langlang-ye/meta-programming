package com.langlang.sqlSession;

import cn.hutool.core.util.ClassUtil;
import com.langlang.proxy.MapperProxyFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author langlang.ye
 * @date 2021/12/15
 */
public class MapperRegistry {

    /**
     * 存储 Mapper Class -> MapperProxyFactory 映射
     */
    private final Map<Class<?>, MapperProxyFactory<?>> mappers = new HashMap<>();

    /**
     *
     * @param type
     * @param sqlSession
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<?> type, DefaultSqlSession sqlSession) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) mappers.get(type);
        return mapperProxyFactory.newInstance(sqlSession);
    }

    /**
     * 根据指定包名扫描包, 将接口添加到 map 中
     * @param packageName
     */
    public void addMappers(String packageName) {
        Set<Class<?>> classes = ClassUtil.scanPackage(packageName);
        for (Class<?> mapperClass : classes) {
            addMapper(mapperClass);
        }
    }

    public void addMapper(Class<?> type) {
        if (type.isInterface()) { // Mapper 必须是接口类型
            if (hasMapper(type)) { // 已经添加过的 Mapper Class
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
            mappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    /**
     * 判断是否存在 Mapper Class 的 key
     * @param type
     * @param <T>
     * @return
     */
    public <T> boolean hasMapper(Class<T> type) {
        return mappers.containsKey(type);
    }

}
