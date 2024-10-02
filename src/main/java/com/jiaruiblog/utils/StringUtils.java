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

    /**
     * <p>判断String是否为空</p>
     * @param str 准备判断空的字符串
     * @return boolean 如果为空则返回true，否则返回false
     **/
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
