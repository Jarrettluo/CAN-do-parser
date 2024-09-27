package com.jiaruiblog.utils;

/**
 * @ClassName StringUtils
 * @Description 字符串处理工具
 * @Author Jarrett Luo
 * @Date 2024/9/27 13:41
 * @Version 1.0
 */
public class StringUtils {

    private StringUtils(){
        throw new IllegalArgumentException("Utility class");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
