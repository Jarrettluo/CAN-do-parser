package com.jiaruiblog.utils;

public class StringUtils {

    private StringUtils(){
        throw new IllegalArgumentException("Utility class");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
