package com.vidlus.jarch.mage;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WizDouble {

    private WizDouble() {
    }

    /**
     * Checks if the given value can be converted to a Double.
     * Supports Double, Number, Boolean, and String.
     *
     * @param value the value to check
     * @return true if the value can be converted, false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Double.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given value to a Double.
     * Handles Double, Number, Boolean (true=1.0, false=0.0), and String parsing.
     * Blank strings return null.
     *
     * @param value the value to convert
     * @return the converted Double, or null if value is null or blank string
     * @throws Exception if conversion fails
     */
    public static Double get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Double.class)) {
            return Double.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).doubleValue();
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? 1.0 : 0.0;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Double.parseDouble(string);
        }
        throw new Exception("Could not convert to a Double value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts a value to Double, returning a default if conversion fails.
     *
     * @param value the value to convert
     * @param orDefault the fallback value
     * @return the converted Double, or orDefault if conversion fails
     */
    public static Double get(Object value, Double orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats a Double as a string.
     *
     * @param value the Double to format
     * @return the string representation, or empty string if null
     */
    public static String format(Double value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts a Double to a primitive double.
     * Null values are treated as 0.0.
     *
     * @param value the Double to convert
     * @return the primitive double value, or 0.0 if null
     */
    public static double toPrimitive(Double value) {
        return value != null ? value : 0.0;
    }

    /**
     * Converts a Double to a primitive double with a custom default for null.
     *
     * @param value the Double to convert
     * @param defaultIfNull the fallback value
     * @return the primitive double value, or defaultIfNull if null
     */
    public static double toPrimitive(Double value, double defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Safely compares two Double values.
     * Null is considered less than any non-null value.
     *
     * @param d1 the first Double
     * @param d2 the second Double
     * @return a negative value if d1 < d2, zero if equal, positive if d1 > d2
     */
    public static int compare(Double d1, Double d2) {
        if (d1 == null && d2 == null) return 0;
        if (d1 == null) return -1;
        if (d2 == null) return 1;
        return Double.compare(d1, d2);
    }

    /**
     * Checks if two Double values are exactly equal.
     *
     * @param d1 the first Double
     * @param d2 the second Double
     * @return true if equal, false otherwise (including if one is null and the other isn't)
     */
    public static boolean isEqual(Double d1, Double d2) {
        return compare(d1, d2) == 0;
    }

    /**
     * Checks if a Double is exactly zero.
     *
     * @param value the Double to check
     * @return true if value is 0.0 (or -0.0)
     */
    public static boolean isZero(Double value) {
        return value != null && value == 0.0;
    }

    /**
     * Checks if a Double is positive (greater than 0).
     *
     * @param value the Double to check
     * @return true if positive, false if null or <= 0
     */
    public static boolean isPositive(Double value) {
        return value != null && value > 0.0;
    }

    /**
     * Checks if a Double is negative (less than 0).
     *
     * @param value the Double to check
     * @return true if negative, false if null or >= 0
     */
    public static boolean isNegative(Double value) {
        return value != null && value < 0.0;
    }

    /**
     * Checks if a Double is a finite floating-point value.
     *
     * @param value the Double to check
     * @return true if finite, false if null, NaN, or Infinite
     */
    public static boolean isFinite(Double value) {
        return value != null && Double.isFinite(value);
    }

    /**
     * Checks if a Double is Not-a-Number (NaN).
     *
     * @param value the Double to check
     * @return true if NaN, false if null or a valid number
     */
    public static boolean isNaN(Double value) {
        return value != null && Double.isNaN(value);
    }

    /**
     * Safely adds two Double values.
     * Nulls are treated as 0.0.
     *
     * @param d1 the first Double
     * @param d2 the second Double
     * @return the sum
     */
    public static Double sum(Double d1, Double d2) {
        if (d1 == null && d2 == null) return null;
        return toPrimitive(d1) + toPrimitive(d2);
    }

    /**
     * Returns the minimum of two Double values.
     * Nulls are ignored if one value is present. If both are null, returns null.
     *
     * @param d1 the first Double
     * @param d2 the second Double
     * @return the minimum value
     */
    public static Double min(Double d1, Double d2) {
        if (d1 == null) return d2;
        if (d2 == null) return d1;
        return Math.min(d1, d2);
    }

    /**
     * Returns the maximum of two Double values.
     * Nulls are ignored if one value is present. If both are null, returns null.
     *
     * @param d1 the first Double
     * @param d2 the second Double
     * @return the maximum value
     */
    public static Double max(Double d1, Double d2) {
        if (d1 == null) return d2;
        if (d2 == null) return d1;
        return Math.max(d1, d2);
    }

    /**
     * Rounds a Double to a specific number of decimal places.
     * Uses HALF_UP rounding mode.
     *
     * @param value the Double to round
     * @param places the number of decimal places
     * @return the rounded Double, or null if input is null or NaN/Infinite
     */
    public static Double round(Double value, int places) {
        if (value == null || !isFinite(value)) return value;
        if (places < 0) throw new IllegalArgumentException("Decimal places cannot be negative");
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
