package com.vidlus.jarch.mage;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WizFloat {

    private WizFloat() {
    }

    /**
     * Checks if the given value can be converted to a Float.
     * Supports Float, Number, Boolean, and String.
     *
     * @param value the value to check
     * @return true if the value can be converted, false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Float.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given value to a Float.
     * Handles Float, Number, Boolean (true=1.0f, false=0.0f), and String parsing.
     * Blank strings return null.
     *
     * @param value the value to convert
     * @return the converted Float, or null if value is null or blank string
     * @throws Exception if conversion fails
     */
    public static Float get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Float.class)) {
            return Float.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).floatValue();
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? 1.0f : 0.0f;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Float.parseFloat(string);
        }
        throw new Exception("Could not convert to a Float value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts a value to Float, returning a default if conversion fails.
     *
     * @param value the value to convert
     * @param orDefault the fallback value
     * @return the converted Float, or orDefault if conversion fails
     */
    public static Float get(Object value, Float orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats a Float as a string.
     *
     * @param value the Float to format
     * @return the string representation, or empty string if null
     */
    public static String format(Float value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts a Float to a primitive float.
     * Null values are treated as 0.0f.
     *
     * @param value the Float to convert
     * @return the primitive float value, or 0.0f if null
     */
    public static float toPrimitive(Float value) {
        return value != null ? value : 0.0f;
    }

    /**
     * Converts a Float to a primitive float with a custom default for null.
     *
     * @param value the Float to convert
     * @param defaultIfNull the fallback value
     * @return the primitive float value, or defaultIfNull if null
     */
    public static float toPrimitive(Float value, float defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Safely compares two Float values.
     * Null is considered less than any non-null value.
     *
     * @param f1 the first Float
     * @param f2 the second Float
     * @return a negative value if f1 < f2, zero if equal, positive if f1 > f2
     */
    public static int compare(Float f1, Float f2) {
        if (f1 == null && f2 == null) return 0;
        if (f1 == null) return -1;
        if (f2 == null) return 1;
        return Float.compare(f1, f2);
    }

    /**
     * Checks if two Float values are exactly equal.
     *
     * @param f1 the first Float
     * @param f2 the second Float
     * @return true if equal, false otherwise (including if one is null and the other isn't)
     */
    public static boolean isEqual(Float f1, Float f2) {
        return compare(f1, f2) == 0;
    }

    /**
     * Checks if a Float is exactly zero.
     *
     * @param value the Float to check
     * @return true if value is 0.0f (or -0.0f)
     */
    public static boolean isZero(Float value) {
        return value != null && value == 0.0f;
    }

    /**
     * Checks if a Float is positive (greater than 0).
     *
     * @param value the Float to check
     * @return true if positive, false if null or <= 0
     */
    public static boolean isPositive(Float value) {
        return value != null && value > 0.0f;
    }

    /**
     * Checks if a Float is negative (less than 0).
     *
     * @param value the Float to check
     * @return true if negative, false if null or >= 0
     */
    public static boolean isNegative(Float value) {
        return value != null && value < 0.0f;
    }

    /**
     * Checks if a Float is a finite floating-point value.
     *
     * @param value the Float to check
     * @return true if finite, false if null, NaN, or Infinite
     */
    public static boolean isFinite(Float value) {
        return value != null && Float.isFinite(value);
    }

    /**
     * Checks if a Float is Not-a-Number (NaN).
     *
     * @param value the Float to check
     * @return true if NaN, false if null or a valid number
     */
    public static boolean isNaN(Float value) {
        return value != null && Float.isNaN(value);
    }

    /**
     * Safely adds two Float values.
     * Nulls are treated as 0.0f.
     *
     * @param f1 the first Float
     * @param f2 the second Float
     * @return the sum
     */
    public static Float sum(Float f1, Float f2) {
        if (f1 == null && f2 == null) return null;
        return toPrimitive(f1) + toPrimitive(f2);
    }

    /**
     * Returns the minimum of two Float values.
     * Nulls are ignored if one value is present. If both are null, returns null.
     *
     * @param f1 the first Float
     * @param f2 the second Float
     * @return the minimum value
     */
    public static Float min(Float f1, Float f2) {
        if (f1 == null) return f2;
        if (f2 == null) return f1;
        return Math.min(f1, f2);
    }

    /**
     * Returns the maximum of two Float values.
     * Nulls are ignored if one value is present. If both are null, returns null.
     *
     * @param f1 the first Float
     * @param f2 the second Float
     * @return the maximum value
     */
    public static Float max(Float f1, Float f2) {
        if (f1 == null) return f2;
        if (f2 == null) return f1;
        return Math.max(f1, f2);
    }

    /**
     * Rounds a Float to a specific number of decimal places.
     * Uses HALF_UP rounding mode.
     *
     * @param value the Float to round
     * @param places the number of decimal places
     * @return the rounded Float, or null if input is null or NaN/Infinite
     */
    public static Float round(Float value, int places) {
        if (value == null || !isFinite(value)) return value;
        if (places < 0) throw new IllegalArgumentException("Decimal places cannot be negative");
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

}
