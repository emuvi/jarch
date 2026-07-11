package com.vidlus.jarch.mage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;


public class WizBigInteger {

    private WizBigInteger() {
    }

    /**
     * Checks if the given value can be converted to a BigInteger.
     * Supports BigInteger, BigDecimal, Number, Boolean, and String types.
     *
     * @param value the value to check
     * @return true if value can be converted to BigInteger; false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), BigInteger.class)
            || WizLang.isChildOf(value.getClass(), BigDecimal.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given value to a BigInteger.
     * Handles BigInteger, BigDecimal, Number, Boolean, and String types.
     * Strings must not be blank.
     *
     * @param value the value to convert
     * @return the converted BigInteger, or null if value is null
     * @throws Exception if the value cannot be converted to BigInteger
     */
    public static BigInteger get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), BigInteger.class)) {
            return BigInteger.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), BigDecimal.class)) {
            return (BigDecimal.class.cast(value)).toBigInteger();
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return BigInteger.valueOf(Number.class.cast(value).longValue());
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? BigInteger.ONE : BigInteger.ZERO;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return new BigInteger(string);
        }
        throw new Exception("Could not convert to a BigInteger value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts the given value to a BigInteger, returning a default if conversion fails.
     * 
     * @param value the value to convert
     * @param orDefault the default value to return if conversion fails
     * @return the converted BigInteger, or orDefault if conversion fails or value is null
     */
    public static BigInteger get(Object value, BigInteger orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats a BigInteger as a string.
     *
     * @param value the BigInteger to format
     * @return the string representation, or empty string if value is null
     */
    public static String format(BigInteger value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Returns the given value or BigInteger.ZERO if value is null.
     *
     * @param value the BigInteger value
     * @return the value, or BigInteger.ZERO if value is null
     */
    public static BigInteger nullToZero(BigInteger value) {
        return value == null ? BigInteger.ZERO : value;
    }

    /**
     * Returns the given value or a default value if value is null.
     *
     * @param value the BigInteger value
     * @param defaultValue the default value to return if value is null
     * @return the value, or defaultValue if value is null
     */
    public static BigInteger defaultIfNull(BigInteger value, BigInteger defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * Checks if the value equals zero.
     *
     * @param value the BigInteger to check
     * @return true if value is not null and equals zero; false otherwise
     */
    public static boolean isZero(BigInteger value) {
        return value != null && value.compareTo(BigInteger.ZERO) == 0;
    }

    /**
     * Checks if the value is greater than zero.
     *
     * @param value the BigInteger to check
     * @return true if value is not null and positive; false otherwise
     */
    public static boolean isPositive(BigInteger value) {
        return value != null && value.compareTo(BigInteger.ZERO) > 0;
    }

    /**
     * Checks if the value is less than zero.
     *
     * @param value the BigInteger to check
     * @return true if value is not null and negative; false otherwise
     */
    public static boolean isNegative(BigInteger value) {
        return value != null && value.compareTo(BigInteger.ZERO) < 0;
    }

    /**
     * Checks if the value is greater than or equal to zero.
     *
     * @param value the BigInteger to check
     * @return true if value is not null and positive or zero; false otherwise
     */
    public static boolean isPositiveOrZero(BigInteger value) {
        return value != null && value.compareTo(BigInteger.ZERO) >= 0;
    }

    /**
     * Checks if the value is less than or equal to zero.
     *
     * @param value the BigInteger to check
     * @return true if value is not null and negative or zero; false otherwise
     */
    public static boolean isNegativeOrZero(BigInteger value) {
        return value != null && value.compareTo(BigInteger.ZERO) <= 0;
    }

    /**
     * Checks if two BigInteger values are equal.
     * Uses compareTo() for proper numeric comparison.
     *
     * @param v1 the first BigInteger value
     * @param v2 the second BigInteger value
     * @return true if both values are equal; false otherwise (including if either is null)
     */
    public static boolean isEqual(BigInteger v1, BigInteger v2) {
        if (v1 == v2) return true;
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) == 0;
    }

    /**
     * Checks if v1 is greater than v2.
     *
     * @param v1 the first BigInteger value
     * @param v2 the second BigInteger value
     * @return true if v1 > v2; false otherwise (including if either is null)
     */
    public static boolean isGreaterThan(BigInteger v1, BigInteger v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) > 0;
    }

    /**
     * Checks if v1 is greater than or equal to v2.
     *
     * @param v1 the first BigInteger value
     * @param v2 the second BigInteger value
     * @return true if v1 >= v2; false otherwise (including if either is null)
     */
    public static boolean isGreaterThanOrEqual(BigInteger v1, BigInteger v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) >= 0;
    }

    /**
     * Checks if v1 is less than v2.
     *
     * @param v1 the first BigInteger value
     * @param v2 the second BigInteger value
     * @return true if v1 < v2; false otherwise (including if either is null)
     */
    public static boolean isLessThan(BigInteger v1, BigInteger v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) < 0;
    }

    /**
     * Checks if v1 is less than or equal to v2.
     *
     * @param v1 the first BigInteger value
     * @param v2 the second BigInteger value
     * @return true if v1 <= v2; false otherwise (including if either is null)
     */
    public static boolean isLessThanOrEqual(BigInteger v1, BigInteger v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) <= 0;
    }

    /**
     * Returns the minimum of two values.
     * Handles null values gracefully.
     *
     * @param v1 the first BigInteger value
     * @param v2 the second BigInteger value
     * @return the smaller value, or the non-null value if one is null
     */
    public static BigInteger min(BigInteger v1, BigInteger v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1.compareTo(v2) <= 0 ? v1 : v2;
    }

    /**
     * Returns the maximum of two values.
     * Handles null values gracefully.
     *
     * @param v1 the first BigInteger value
     * @param v2 the second BigInteger value
     * @return the larger value, or the non-null value if one is null
     */
    public static BigInteger max(BigInteger v1, BigInteger v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1.compareTo(v2) >= 0 ? v1 : v2;
    }

    /**
     * Adds two BigInteger values.
     * Treats null values as zero.
     *
     * @param v1 the first BigInteger value
     * @param v2 the second BigInteger value
     * @return the sum of v1 and v2
     */
    public static BigInteger add(BigInteger v1, BigInteger v2) {
        return nullToZero(v1).add(nullToZero(v2));
    }

    /**
     * Subtracts v2 from v1.
     * Treats null values as zero.
     *
     * @param v1 the first BigInteger value
     * @param v2 the second BigInteger value
     * @return the difference v1 - v2
     */
    public static BigInteger subtract(BigInteger v1, BigInteger v2) {
        return nullToZero(v1).subtract(nullToZero(v2));
    }

    /**
     * Multiplies two BigInteger values.
     * Treats null values as zero.
     *
     * @param v1 the first BigInteger value
     * @param v2 the second BigInteger value
     * @return the product v1 * v2
     */
    public static BigInteger multiply(BigInteger v1, BigInteger v2) {
        return nullToZero(v1).multiply(nullToZero(v2));
    }

    /**
     * Divides v1 by v2.
     * Treats null values as zero. Uses integer division (truncates result).
     *
     * @param v1 the dividend
     * @param v2 the divisor
     * @return the quotient v1 / v2 (integer division)
     * @throws ArithmeticException if v2 is zero
     */
    public static BigInteger divide(BigInteger v1, BigInteger v2) {
        BigInteger num = nullToZero(v1);
        BigInteger den = nullToZero(v2);
        if (den.compareTo(BigInteger.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return num.divide(den);
    }

    /**
     * Calculates the remainder of v1 divided by v2.
     * Treats null values as zero.
     *
     * @param v1 the dividend
     * @param v2 the divisor
     * @return the remainder of v1 / v2
     * @throws ArithmeticException if v2 is zero
     */
    public static BigInteger remainder(BigInteger v1, BigInteger v2) {
        BigInteger num = nullToZero(v1);
        BigInteger den = nullToZero(v2);
        if (den.compareTo(BigInteger.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return num.remainder(den);
    }

    /**
     * Sums an array of BigInteger values.
     * Skips null values and returns zero if array is null or empty.
     *
     * @param values the array of BigInteger values to sum
     * @return the sum of all non-null values
     */
    public static BigInteger sum(BigInteger... values) {
        if (values == null || values.length == 0) return BigInteger.ZERO;
        BigInteger total = BigInteger.ZERO;
        for (BigInteger value : values) {
            if (value != null) {
                total = total.add(value);
            }
        }
        return total;
    }

    /**
     * Sums a collection of BigInteger values.
     * Skips null values and returns zero if collection is null or empty.
     *
     * @param values the collection of BigInteger values to sum
     * @return the sum of all non-null values
     */
    public static BigInteger sum(Collection<BigInteger> values) {
        if (values == null || values.isEmpty()) return BigInteger.ZERO;
        BigInteger total = BigInteger.ZERO;
        for (BigInteger value : values) {
            if (value != null) {
                total = total.add(value);
            }
        }
        return total;
    }

}
