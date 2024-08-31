package com.langlang.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 反射常用的工具类
 */
public class ReflectUtils {

    private static final Set<Class> set = new HashSet<>();

    private static final Set<Class> superClassSet = new HashSet<>();
    private static final Set<Class> superInterfacesSet = new HashSet<>();
    private static Class<Object> obj = Object.class;

    /**
     * 通过反射调用指定属性XXX的getXXX, 返回指定属性的值
     *
     * @param fieldName
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
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

    /**
     * 获取该类所有的父类和接口
     *
     * @param clazz
     * @return
     */

    public static Set<Class> getSuperClassAndInterfaces(Class clazz) {
        Type superclass = clazz.getSuperclass();
        Class[] interfaces = clazz.getInterfaces();

        if (superclass != null && !superclass.equals(obj)) {
            getSuperClassAndInterfaces((Class) superclass);
        }
        for (Class genericInterface : interfaces) {
            getSuperClassAndInterfaces(genericInterface);
        }

        if (superclass != null && !superclass.equals(obj)) {
            set.add((Class) superclass);
        }

        for (Class i : interfaces) {
            set.add(i);
        }

        return set;

    }

    /**
     * 获取该类所有的父类
     *
     * @param clazz
     * @return
     */
    public static Set<Class> getAllSuperClass(Class clazz) {
        Type superclass = clazz.getSuperclass();

        if (!superclass.equals(obj)) {
            getAllSuperClass((Class) superclass);
            superClassSet.add((Class) superclass);
        }

        return superClassSet;
    }

    /**
     * 获取该类的所有接口(去重)
     * @param clazz
     * @return
     */
    public static Set<Class> getAllSuperInterfaces(Class clazz) {
        Type superclass = clazz.getSuperclass();
        Class[] interfaces = clazz.getInterfaces();

        if (superclass != null && !superclass.equals(obj)) {
            getAllSuperInterfaces((Class) superclass);
        }
        for (Class genericInterface : interfaces) {
            getAllSuperInterfaces(genericInterface);
        }

        for (Class i : interfaces) {
            superInterfacesSet.add(i);
        }
        return superInterfacesSet;
    }

    // todo 两个类之间的关系: 返回继承或实现关系的中间
    /**
     *
     * @param clazz 类实例对象
     * @param targetClazz 接口或者父类
     * @return
     */
    public static Set<Class> getRef(Class clazz, Class targetClazz) {
        if (clazz.equals(targetClazz)) {
            return null;
        }
        Set<Class> classSet = new LinkedHashSet<>();

        if (!targetClazz.isInterface()) { // 目标类型是类
            Class superclass = clazz.getSuperclass();

            while (!superclass.equals(obj)) {
                if (!superclass.equals(targetClazz)) {
                    classSet.add(superclass);
                    superclass = superclass.getSuperclass();
                } else {
                    break;
                }
            }
        } else {     // 目标类型是接口

        }
        return classSet;
    }




}
