package com.jiaruiblog.utils;

import java.util.Collection;

/**
 * @ClassName CollectionUtils
 * @Description 集合处理工具
 * @Author Jarrett Luo
 * @Date 2024/9/27 13:41
 * @Version 1.0
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
