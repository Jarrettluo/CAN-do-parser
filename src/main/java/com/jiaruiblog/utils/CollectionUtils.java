package com.jiaruiblog.utils;

import java.util.Collection;

/**
 * 集合处理工具
 *
 * @author Jarrett Luo
 * @version 1.0
 */
public class CollectionUtils {

    private CollectionUtils() {
        throw new IllegalArgumentException("Utility class");
    }

    /**
     * <p>判断集合是否是空的</p>
     * @param collection 输入的集合体
     * @return boolean 如果集合是空的，则返回true，反之返回false
     **/
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * <p>判断集合是非空的</p>
     * @param collection 输入的集合体
     * @return boolean 如果集合是非空的，则返回true，反之返回false
     **/
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
