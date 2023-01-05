package com.langlang.config;

import java.lang.reflect.*;

public class TypeParameterResolver {

    public static Class<?> resolveReturnType(Method method, Type srcType) {
        Type returnType = method.getGenericReturnType();
        return resolveType(method, returnType);
    }


    private static Class<?> resolveType(Method method, Type type) {
        if (type instanceof TypeVariable) {
            return null;
        } else if (type instanceof ParameterizedType) {
            // 解析集合类型中的泛型类型List<Star> 得到 Star 类型
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            return (Class<?>) actualTypeArguments[0];
        } else if (type instanceof GenericArrayType) {
            return null;
        } else {
            return method.getReturnType();
        }
    }



}
