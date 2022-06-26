package com.langlang.type;

import com.langlang.pojo.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author langlang.ye
 * @date 2022/6/14
 */
public class TypeHandlerRegistry {

    private Configuration configuration;

    private final Map<Class<?>, Object> TYPE_HANDLERS = new HashMap<>();

    public TypeHandlerRegistry(Configuration configuration) {
        this.configuration = configuration;

        register(String.class, String.class);
        register(Byte.class, Byte.class);
        register(Long.class, Long.class);
        register(Short.class, Short.class);
        register(Integer.class, Integer.class);
        register(Double.class, Double.class);
        register(Float.class, Float.class);
        register(Boolean.class, Boolean.class);

        configuration.setTypeHandlerRegistry(this);
    }

    private void register(Class<?> typeClass, Object object) {
        TYPE_HANDLERS.put(typeClass, object);
    }

    public boolean hasTypeHandler(Class<?> clazz) {
        return TYPE_HANDLERS.get(clazz) != null;
    }
}
