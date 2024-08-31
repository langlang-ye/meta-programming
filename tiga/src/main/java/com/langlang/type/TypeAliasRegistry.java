package com.langlang.type;

import cn.hutool.core.util.ClassUtil;
import com.langlang.pojo.Configuration;
import com.langlang.utils.ReflectionUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author langlang.ye
 * @date 2021/5/4
 */
public class TypeAliasRegistry {

    private Configuration configuration;

    /**
     * 类型与别名的映射。
     */
    private final Map<String, Class<?>> TYPE_ALIASES = new HashMap<>();

    public TypeAliasRegistry(Configuration configuration) {
        this.configuration = configuration;
        registerAliases("string", String.class);
        registerAliases("byte", Byte.class);
        registerAliases("long", Long.class);
        registerAliases("short", Short.class);
        registerAliases("int", Integer.class);
        registerAliases("integer", Integer.class);
        registerAliases("double", Double.class);
        registerAliases("float", Float.class);
        registerAliases("boolean", Boolean.class);
    }

    /**
     * 给指定类型的实体起别名
     * @param alias
     * @param classType
     * @throws ClassNotFoundException
     */
    public void addTypeAlias(String alias, String classType) throws ClassNotFoundException {
        Class<?> targetClass = ReflectionUtils.classForName(classType);
        registerAliases(alias, targetClass);
    }

    /**
     * 包下面的所有类
     * @param packageName
     */
    public void addTypeAliases(String packageName) {
        registerAliases(packageName);
    }

    /**
     * 遍历 set 集合并注册到别名容器中.
     * k: 别名
     * v: 对应的类对象
     * @param packageName
     */
    public void registerAliases(String packageName) {
        Set<Class<?>> classes = ClassUtil.scanPackage(packageName);
        for(Class<?> tClass : classes) {
            String name = tClass.getSimpleName().toLowerCase(Locale.ENGLISH);
            registerAliases(name, tClass);
        }
        configuration.setTypeAliasRegistry(this);
    }

    /**
     * 存入别名容器中
     * @param name key
     * @param tClass value
     */
    private void registerAliases(String name, Class<?> tClass) {
        TYPE_ALIASES.put(name, tClass);
    }

    /**
     * 别名解析器: 优先从 TYPE_ALIASES 集合中查找, 如果找不到, 再使用 classForName 获取类对象
     * @param string
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Class<T> resolveAlias(String string) {
        try {
            if (string == null) {
                return null;
            }

            String key = string.toLowerCase(Locale.ENGLISH);
            Class<T> value;

            if (TYPE_ALIASES.containsKey(key)) {
                value = (Class<T>) TYPE_ALIASES.get(key);
            } else {
                value = (Class<T>) ReflectionUtils.classForName(string);
            }

            return value;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not resolve type alias '" + string + "'.  Cause: " + e, e);
        }

    }

}
