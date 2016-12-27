package com.alexbt.appdirect.notifications.util;

import java.util.Map;

/**
 * Utility class to validate method arguments. I implemented this instead of importing apache's common-lang3, to ensure
 * that this project doesn't have any transitive dependencies.
 *
 * @author alexbt
 *
 */
public final class Validate {
    private static final String THE_VALUE_MUST_NOT_BE_NULL = "The value must not be null";
    private static final String THE_ARRAY_MUST_NOT_BE_EMPTY = "The array must not be empty";
    private static final String THE_MAP_MUST_NOT_BE_EMPTY = "The map must not be empty";
    private static final String THE_VALUE_MUST_NOT_BE_EMPTY = "The value must not be empty";

    private Validate() {
    }

    /**
     * Validate that the specified String is not null and not empty (length &gt; 0); otherwise, it throws
     * IllegalArgumentException.
     * @param str the String to validate
     */
    public static void notEmpty(String str) {
        notNull(str);
        if (str.length() == 0) {
            throw new IllegalArgumentException(THE_VALUE_MUST_NOT_BE_EMPTY);
        }
    }

    /**
     * Validate that the specified array is not null and not empty (length &gt; 0); otherwise, it throws
     * IllegalArgumentException.
     * @param array the array to validate
     */
    public static void notEmpty(Object[] array) {
        notNull(array);
        if (array.length == 0) {
            throw new IllegalArgumentException(THE_ARRAY_MUST_NOT_BE_EMPTY);
        }
    }

    /**
     * Validate that the specified Object is not null; otherwise, it throws IllegalArgumentException.
     * @param object the Object to validate
     */
    public static void notNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException(THE_VALUE_MUST_NOT_BE_NULL);
        }
    }

    /**
     * Validate that the specified array is not null and not empty (length &gt; 0); otherwise, it throws
     * IllegalArgumentException.
     * @param map the map to validate
     */
    public static void notEmpty(Map<?, ?> map) {
        notNull(map);
        if (map.isEmpty()) {
            throw new IllegalArgumentException(THE_MAP_MUST_NOT_BE_EMPTY);
        }
    }
}
