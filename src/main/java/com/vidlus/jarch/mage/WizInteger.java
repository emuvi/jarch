package com.vidlus.jarch.mage;

public class WizInteger {
    
    private WizInteger() {
    }

    /**
     * Checks if the given value can be converted to an Integer.
     * Supports Integer, Number, Boolean, and String.
     *
     * @param value the value to check
     * @return true if the value can be converted, false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Integer.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given value to an Integer.
     * Handles Integer, Number, Boolean (true=1, false=0), and String parsing.
     * Blank strings return null.
     *
     * @param value the value to convert
     * @return the converted Integer, or null if value is null or blank string
     * @throws Exception if conversion fails
     */
    public static Integer get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Integer.class)) {
            return Integer.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).intValue();
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? 1 : 0;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Integer.parseInt(string.trim());
        }
        throw new Exception("Could not convert to an Integer value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts a value to Integer, returning a default if conversion fails.
     *
     * @param value the value to convert
     * @param orDefault the fallback value
     * @return the converted Integer, or orDefault if conversion fails
     */
    public static Integer get(Object value, Integer orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats an Integer as a string.
     *
     * @param value the Integer to format
     * @return the string representation, or empty string if null
     */
    public static String format(Integer value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts an Integer to a primitive int.
     * Null values are treated as 0.
     *
     * @param value the Integer to convert
     * @return the primitive int value, or 0 if null
     */
    public static int toPrimitive(Integer value) {
        return value != null ? value : 0;
    }

    /**
     * Converts an Integer to a primitive int with a custom default for null.
     *
     * @param value the Integer to convert
     * @param defaultIfNull the fallback value
     * @return the primitive int value, or defaultIfNull if null
     */
    public static int toPrimitive(Integer value, int defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Safely compares two Integer values.
     * Null is considered less than any non-null value.
     *
     * @param i1 the first Integer
     * @param i2 the second Integer
     * @return a negative value if i1 < i2, zero if equal, positive if i1 > i2
     */
    public static int compare(Integer i1, Integer i2) {
        if (i1 == null && i2 == null) return 0;
        if (i1 == null) return -1;
        if (i2 == null) return 1;
        return Integer.compare(i1, i2);
    }

    /**
     * Checks if two Integer values are exactly equal.
     *
     * @param i1 the first Integer
     * @param i2 the second Integer
     * @return true if equal, false otherwise
     */
    public static boolean isEqual(Integer i1, Integer i2) {
        return compare(i1, i2) == 0;
    }

    /**
     * Checks if an Integer is exactly zero.
     *
     * @param value the Integer to check
     * @return true if value is 0, false otherwise
     */
    public static boolean isZero(Integer value) {
        return value != null && value == 0;
    }

    /**
     * Checks if an Integer is positive (greater than 0).
     *
     * @param value the Integer to check
     * @return true if positive, false if null or <= 0
     */
    public static boolean isPositive(Integer value) {
        return value != null && value > 0;
    }

    /**
     * Checks if an Integer is negative (less than 0).
     *
     * @param value the Integer to check
     * @return true if negative, false if null or >= 0
     */
    public static boolean isNegative(Integer value) {
        return value != null && value < 0;
    }

    /**
     * Checks if an Integer is even.
     *
     * @param value the Integer to check
     * @return true if even, false if null or odd
     */
    public static boolean isEven(Integer value) {
        return value != null && (value % 2 == 0);
    }

    /**
     * Checks if an Integer is odd.
     *
     * @param value the Integer to check
     * @return true if odd, false if null or even
     */
    public static boolean isOdd(Integer value) {
        return value != null && (value % 2 != 0);
    }

    /**
     * Safely adds two Integer values.
     * Nulls are treated as 0.
     *
     * @param i1 the first Integer
     * @param i2 the second Integer
     * @return the sum
     */
    public static Integer sum(Integer i1, Integer i2) {
        if (i1 == null && i2 == null) return null;
        return toPrimitive(i1) + toPrimitive(i2);
    }

    /**
     * Returns the minimum of two Integer values.
     * Nulls are ignored if one value is present. If both are null, returns null.
     *
     * @param i1 the first Integer
     * @param i2 the second Integer
     * @return the minimum value
     */
    public static Integer min(Integer i1, Integer i2) {
        if (i1 == null) return i2;
        if (i2 == null) return i1;
        return Math.min(i1, i2);
    }

    /**
     * Returns the maximum of two Integer values.
     * Nulls are ignored if one value is present. If both are null, returns null.
     *
     * @param i1 the first Integer
     * @param i2 the second Integer
     * @return the maximum value
     */
    public static Integer max(Integer i1, Integer i2) {
        if (i1 == null) return i2;
        if (i2 == null) return i1;
        return Math.max(i1, i2);
    }

}
