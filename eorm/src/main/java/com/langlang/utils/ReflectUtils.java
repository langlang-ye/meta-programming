package com.langlang.utils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 反射常用的工具类
 */
public class ReflectUtils {

    /**
     * 通过反射调用指定属性XXX的getXXX, 返回指定属性的值
     *
     * @param fieldName
     * @param obj
     * @return
     */
    public static Object invokeGet(String fieldName, Object obj) {
        try {
            Class c = obj.getClass();
            Method m = c.getDeclaredMethod("get" + StringUtils.firstChar2UpperCase(fieldName), null);
            return m.invoke(obj, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过返回调用XXX属性的setXXX 方法, 给指定的属性赋值value
     *
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void invokeSet(Object obj, String fieldName, Object value) {
        try {
            if (fieldName != null) {
                Method m = obj.getClass().getDeclaredMethod("set" + StringUtils.firstChar2UpperCase(fieldName), obj.getClass());
                m.invoke(obj, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // todo: 获取该类所有的父类和接口

    // todo: 两个类 是否存在继承 或者实现关系




}
