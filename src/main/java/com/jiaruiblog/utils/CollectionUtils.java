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

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
