package com.langlang.utils;

/**
 * 字符串常用操作
 */
public class StringUtils {

    /**
     * 将字符串 str 首字母大写返回
     * 如: hello --> Hello
     * @param str
     * @return
     */
    public static String firstChar2UpperCase(String str) {
        return str.toUpperCase().substring(0, 1) + str.substring(1);
    }




}
