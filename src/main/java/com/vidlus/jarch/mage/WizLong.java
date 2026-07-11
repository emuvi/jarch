package com.vidlus.jarch.mage;

public class WizLong {

    private WizLong() {
    }

    /**
     * Checks if the given value can be converted to a Long.
     * Supports Long, Number, Boolean, and String.
     *
     * @param value the value to check
     * @return true if the value can be converted, false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Long.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given value to a Long.
     * Handles Long, Number, Boolean (true=1, false=0), and String parsing.
     * Blank strings return null.
     *
     * @param value the value to convert
     * @return the converted Long, or null if value is null or blank string
     * @throws Exception if conversion fails
     */
    public static Long get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Long.class)) {
            return Long.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).longValue();
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? 1L : 0L;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Long.parseLong(string.trim());
        }
        throw new Exception("Could not convert to a Long value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts a value to Long, returning a default if conversion fails.
     *
     * @param value the value to convert
     * @param orDefault the fallback value
     * @return the converted Long, or orDefault if conversion fails
     */
    public static Long get(Object value, Long orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats a Long as a string.
     *
     * @param value the Long to format
     * @return the string representation, or empty string if null
     */
    public static String format(Long value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts a Long to a primitive long.
     * Null values are treated as 0L.
     *
     * @param value the Long to convert
     * @return the primitive long value, or 0L if null
     */
    public static long toPrimitive(Long value) {
        return value != null ? value : 0L;
    }

    /**
     * Converts a Long to a primitive long with a custom default for null.
     *
     * @param value the Long to convert
     * @param defaultIfNull the fallback value
     * @return the primitive long value, or defaultIfNull if null
     */
    public static long toPrimitive(Long value, long defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Safely compares two Long values.
     * Null is considered less than any non-null value.
     *
     * @param l1 the first Long
     * @param l2 the second Long
     * @return a negative value if l1 < l2, zero if equal, positive if l1 > l2
     */
    public static int compare(Long l1, Long l2) {
        if (l1 == null && l2 == null) return 0;
        if (l1 == null) return -1;
        if (l2 == null) return 1;
        return Long.compare(l1, l2);
    }

    /**
     * Checks if two Long values are exactly equal.
     *
     * @param l1 the first Long
     * @param l2 the second Long
     * @return true if equal, false otherwise
     */
    public static boolean isEqual(Long l1, Long l2) {
        return compare(l1, l2) == 0;
    }

    /**
     * Checks if a Long is exactly zero.
     *
     * @param value the Long to check
     * @return true if value is 0, false otherwise
     */
    public static boolean isZero(Long value) {
        return value != null && value == 0L;
    }

    /**
     * Checks if a Long is positive (greater than 0).
     *
     * @param value the Long to check
     * @return true if positive, false if null or <= 0
     */
    public static boolean isPositive(Long value) {
        return value != null && value > 0L;
    }

    /**
     * Checks if a Long is negative (less than 0).
     *
     * @param value the Long to check
     * @return true if negative, false if null or >= 0
     */
    public static boolean isNegative(Long value) {
        return value != null && value < 0L;
    }

    /**
     * Checks if a Long is even.
     *
     * @param value the Long to check
     * @return true if even, false if null or odd
     */
    public static boolean isEven(Long value) {
        return value != null && (value % 2 == 0L);
    }

    /**
     * Checks if a Long is odd.
     *
     * @param value the Long to check
     * @return true if odd, false if null or even
     */
    public static boolean isOdd(Long value) {
        return value != null && (value % 2 != 0L);
    }

    /**
     * Safely adds two Long values.
     * Nulls are treated as 0L.
     *
     * @param l1 the first Long
     * @param l2 the second Long
     * @return the sum
     */
    public static Long sum(Long l1, Long l2) {
        if (l1 == null && l2 == null) return null;
        return toPrimitive(l1) + toPrimitive(l2);
    }

    /**
     * Returns the minimum of two Long values.
     * Nulls are ignored if one value is present. If both are null, returns null.
     *
     * @param l1 the first Long
     * @param l2 the second Long
     * @return the minimum value
     */
    public static Long min(Long l1, Long l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        return Math.min(l1, l2);
    }

    /**
     * Returns the maximum of two Long values.
     * Nulls are ignored if one value is present. If both are null, returns null.
     *
     * @param l1 the first Long
     * @param l2 the second Long
     * @return the maximum value
     */
    public static Long max(Long l1, Long l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        return Math.max(l1, l2);
    }

}
