package com.learning.common.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author over.li
 * @since 2023/3/29
 */
public final class ObjectIdUtil {

    private static final int SEQ = 100;

    private static AtomicInteger COUNT = new AtomicInteger(1);

    private ObjectIdUtil() {
    }

    public static Long objId() {
        int index = COUNT.incrementAndGet();
        if (index >= 10) {
            COUNT = new AtomicInteger(0);
        }
        return System.nanoTime() * SEQ + index;
    }
}
