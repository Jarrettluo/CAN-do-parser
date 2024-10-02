package com.jiaruiblog.utils;

/**
 * StringUtils
 * 字符串处理工具
 * @author Jarrett Luo
 * @version 1.0
 */
public class StringUtils {

    private StringUtils(){
        throw new IllegalArgumentException("Utility class");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
