package com.vidlus.jarch.mage;

import java.util.Objects;

/**
 * Utility class providing null-safe conversion, math, logic, and comparison 
 * operations for Short objects and short primitives.
 */
public class WizShort {

    private WizShort() {
    }

    // =========================================================================
    // CONVERSION & VALIDATION
    // =========================================================================

    /**
     * Checks if a given object can be parsed or converted into a Short.
     * Supports Numbers, Booleans, and Strings.
     *
     * @param value the object to check
     * @return true if the object is convertible to a Short, false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Short.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts a given object into a Short object.
     * Numbers are downcast, Booleans become 1 or 0, and Strings are parsed.
     *
     * @param value the object to convert
     * @return the Short representation, or null if the input is null or an empty string
     * @throws Exception if the conversion fails or the type is unsupported
     */
    public static Short get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Short.class)) {
            return Short.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).shortValue();
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? (short) 1 : (short) 0;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Short.parseShort(string);
        }
        throw new Exception("Could not convert to a Short value the value of class: " + value.getClass().getName());
    }

    /**
     * Safely attempts to convert an object to a Short, returning a fallback default 
     * if the conversion fails or the result is null.
     *
     * @param value     the object to convert
     * @param orDefault the fallback value
     * @return the converted Short, or the default value on failure
     */
    public static Short get(Object value, Short orDefault) {
        try {
            Short result = get(value);
            return result != null ? result : orDefault;
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Safely unboxes a Short object into a primitive short, defaulting to 0 if null.
     *
     * @param value the Short object
     * @return the primitive short value, or 0 if null
     */
    public static short toPrimitive(Short value) {
        return toPrimitive(value, (short) 0);
    }

    /**
     * Safely unboxes a Short object into a primitive short, using a specified default if null.
     *
     * @param value        the Short object
     * @param defaultValue the fallback value
     * @return the primitive short value, or the default if null
     */
    public static short toPrimitive(Short value, short defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * Converts a Short to its String representation in a null-safe manner.
     *
     * @param value the Short object
     * @return the string value, or an empty string if null
     */
    public static String format(Short value) {
        return value == null ? "" : value.toString();
    }

    // =========================================================================
    // MATH & LOGIC
    // =========================================================================

    /**
     * Safely adds two Short values. 
     * Nulls are ignored if one value is present, returning null only if both are null.
     *
     * @param a the first Short
     * @param b the second Short
     * @return the sum, or the non-null value if one is null
     */
    public static Short sum(Short a, Short b) {
        if (a == null && b == null) return null;
        if (a == null) return b;
        if (b == null) return a;
        return (short) (a + b);
    }

    /**
     * Returns the smaller of two Short values in a null-safe manner.
     *
     * @param a the first Short
     * @param b the second Short
     * @return the smaller value, or the non-null value if one is null
     */
    public static Short min(Short a, Short b) {
        if (a == null && b == null) return null;
        if (a == null) return b;
        if (b == null) return a;
        return a < b ? a : b;
    }

    /**
     * Returns the larger of two Short values in a null-safe manner.
     *
     * @param a the first Short
     * @param b the second Short
     * @return the larger value, or the non-null value if one is null
     */
    public static Short max(Short a, Short b) {
        if (a == null && b == null) return null;
        if (a == null) return b;
        if (b == null) return a;
        return a > b ? a : b;
    }

    /**
     * Returns the absolute (positive) value of a Short.
     *
     * @param value the input Short
     * @return the absolute value, or null if the input is null
     */
    public static Short abs(Short value) {
        if (value == null) return null;
        return (short) Math.abs(value);
    }

    /**
     * Checks if a Short value is an even number.
     *
     * @param value the Short to check
     * @return true if even, false if odd or null
     */
    public static boolean isEven(Short value) {
        if (value == null) return false;
        return value % 2 == 0;
    }

    /**
     * Checks if a Short value is an odd number.
     *
     * @param value the Short to check
     * @return true if odd, false if even or null
     */
    public static boolean isOdd(Short value) {
        if (value == null) return false;
        return value % 2 != 0;
    }

    // =========================================================================
    // COMPARISONS
    // =========================================================================

    /**
     * Safely checks if two Short values are exactly equal.
     *
     * @param a the first Short
     * @param b the second Short
     * @return true if equal (or both null), false otherwise
     */
    public static boolean isEqual(Short a, Short b) {
        return Objects.equals(a, b);
    }

    /**
     * Compares two Short values numerically. 
     * Nulls are evaluated as "less than" non-null values.
     *
     * @param a the first Short
     * @param b the second Short
     * @return 0 if equal, -1 if a is less than b (or a is null), 1 if a is greater than b (or b is null)
     */
    public static int compare(Short a, Short b) {
        if (a == null && b == null) return 0;
        if (a == null) return -1;
        if (b == null) return 1;
        return Short.compare(a, b);
    }

    /**
     * Checks if the first Short is strictly greater than the second.
     * Nulls are evaluated as "less than" non-null values.
     *
     * @param a the first Short
     * @param b the second Short
     * @return true if a is greater than b
     */
    public static boolean isGreater(Short a, Short b) {
        return compare(a, b) > 0;
    }

    /**
     * Checks if the first Short is strictly less than the second.
     * Nulls are evaluated as "less than" non-null values.
     *
     * @param a the first Short
     * @param b the second Short
     * @return true if a is less than b
     */
    public static boolean isLess(Short a, Short b) {
        return compare(a, b) < 0;
    }

}
