package com.jiaruiblog.utils;

import java.util.Collection;

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
