package com.vidlus.jarch.mage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collection;


public class WizBigDecimal {

    private WizBigDecimal() {
    }

    /**
     * Checks if the given value can be converted to a BigDecimal.
     * Supports BigDecimal, Number, Boolean, and String types.
     *
     * @param value the value to check
     * @return true if value can be converted to BigDecimal; false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), BigDecimal.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given value to a BigDecimal.
     * Handles BigDecimal, BigInteger, Number, Boolean, and String types.
     * Strings must not be blank.
     *
     * @param value the value to convert
     * @return the converted BigDecimal, or null if value is null
     * @throws Exception if the value cannot be converted to BigDecimal
     */
    public static BigDecimal get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), BigDecimal.class)) {
            return BigDecimal.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), BigInteger.class)) {
            return new BigDecimal(BigInteger.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return BigDecimal.valueOf(Number.class.cast(value).doubleValue());
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? BigDecimal.ONE : BigDecimal.ZERO;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return new BigDecimal(string);
        }
        throw new Exception("Could not convert to a BigDecimal value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts the given value to a BigDecimal, returning a default if conversion fails.
     * 
     * @param value the value to convert
     * @param orDefault the default value to return if conversion fails
     * @return the converted BigDecimal, or orDefault if conversion fails or value is null
     */
    public static BigDecimal get(Object value, BigDecimal orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats a BigDecimal as a string.
     *
     * @param value the BigDecimal to format
     * @return the string representation, or empty string if value is null
     */
    public static String format(BigDecimal value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Returns the given value or BigDecimal.ZERO if value is null.
     *
     * @param value the BigDecimal value
     * @return the value, or BigDecimal.ZERO if value is null
     */
    public static BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    /**
     * Returns the given value or a default value if value is null.
     *
     * @param value the BigDecimal value
     * @param defaultValue the default value to return if value is null
     * @return the value, or defaultValue if value is null
     */
    public static BigDecimal defaultIfNull(BigDecimal value, BigDecimal defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * Checks if the value equals zero.
     *
     * @param value the BigDecimal to check
     * @return true if value is not null and equals zero; false otherwise
     */
    public static boolean isZero(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Checks if the value is greater than zero.
     *
     * @param value the BigDecimal to check
     * @return true if value is not null and positive; false otherwise
     */
    public static boolean isPositive(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Checks if the value is less than zero.
     *
     * @param value the BigDecimal to check
     * @return true if value is not null and negative; false otherwise
     */
    public static boolean isNegative(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * Checks if the value is greater than or equal to zero.
     *
     * @param value the BigDecimal to check
     * @return true if value is not null and positive or zero; false otherwise
     */
    public static boolean isPositiveOrZero(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * Checks if the value is less than or equal to zero.
     *
     * @param value the BigDecimal to check
     * @return true if value is not null and negative or zero; false otherwise
     */
    public static boolean isNegativeOrZero(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) <= 0;
    }

    /**
     * Checks if two BigDecimal values are equal.
     * Uses compareTo() for proper numeric comparison.
     *
     * @param v1 the first BigDecimal value
     * @param v2 the second BigDecimal value
     * @return true if both values are equal; false otherwise (including if either is null)
     */
    public static boolean isEqual(BigDecimal v1, BigDecimal v2) {
        if (v1 == v2) return true;
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) == 0;
    }

    /**
     * Checks if v1 is greater than v2.
     *
     * @param v1 the first BigDecimal value
     * @param v2 the second BigDecimal value
     * @return true if v1 > v2; false otherwise (including if either is null)
     */
    public static boolean isGreaterThan(BigDecimal v1, BigDecimal v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) > 0;
    }

    /**
     * Checks if v1 is greater than or equal to v2.
     *
     * @param v1 the first BigDecimal value
     * @param v2 the second BigDecimal value
     * @return true if v1 >= v2; false otherwise (including if either is null)
     */
    public static boolean isGreaterThanOrEqual(BigDecimal v1, BigDecimal v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) >= 0;
    }

    /**
     * Checks if v1 is less than v2.
     *
     * @param v1 the first BigDecimal value
     * @param v2 the second BigDecimal value
     * @return true if v1 < v2; false otherwise (including if either is null)
     */
    public static boolean isLessThan(BigDecimal v1, BigDecimal v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) < 0;
    }

    /**
     * Checks if v1 is less than or equal to v2.
     *
     * @param v1 the first BigDecimal value
     * @param v2 the second BigDecimal value
     * @return true if v1 <= v2; false otherwise (including if either is null)
     */
    public static boolean isLessThanOrEqual(BigDecimal v1, BigDecimal v2) {
        if (v1 == null || v2 == null) return false;
        return v1.compareTo(v2) <= 0;
    }

    /**
     * Returns the minimum of two values.
     * Handles null values gracefully.
     *
     * @param v1 the first BigDecimal value
     * @param v2 the second BigDecimal value
     * @return the smaller value, or the non-null value if one is null
     */
    public static BigDecimal min(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1.compareTo(v2) <= 0 ? v1 : v2;
    }

    /**
     * Returns the maximum of two values.
     * Handles null values gracefully.
     *
     * @param v1 the first BigDecimal value
     * @param v2 the second BigDecimal value
     * @return the larger value, or the non-null value if one is null
     */
    public static BigDecimal max(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1.compareTo(v2) >= 0 ? v1 : v2;
    }

    /**
     * Adds two BigDecimal values.
     * Treats null values as zero.
     *
     * @param v1 the first BigDecimal value
     * @param v2 the second BigDecimal value
     * @return the sum of v1 and v2
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
        return nullToZero(v1).add(nullToZero(v2));
    }

    /**
     * Subtracts v2 from v1.
     * Treats null values as zero.
     *
     * @param v1 the first BigDecimal value
     * @param v2 the second BigDecimal value
     * @return the difference v1 - v2
     */
    public static BigDecimal subtract(BigDecimal v1, BigDecimal v2) {
        return nullToZero(v1).subtract(nullToZero(v2));
    }

    /**
     * Multiplies two BigDecimal values.
     * Treats null values as zero.
     *
     * @param v1 the first BigDecimal value
     * @param v2 the second BigDecimal value
     * @return the product v1 * v2
     */
    public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
        return nullToZero(v1).multiply(nullToZero(v2));
    }

    /**
     * Divides v1 by v2 with default scale of 2 and HALF_UP rounding.
     * Treats null values as zero.
     *
     * @param v1 the dividend
     * @param v2 the divisor
     * @return the quotient v1 / v2 with scale 2
     * @throws ArithmeticException if v2 is zero
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {
        return divide(v1, v2, 2, RoundingMode.HALF_UP);
    }

    /**
     * Divides v1 by v2 with specified scale and rounding mode.
     * Treats null values as zero.
     *
     * @param v1 the dividend
     * @param v2 the divisor
     * @param scale the number of decimal places to round to
     * @param roundingMode the rounding mode to apply
     * @return the quotient v1 / v2 with specified scale and rounding
     * @throws ArithmeticException if v2 is zero
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale, RoundingMode roundingMode) {
        BigDecimal num = nullToZero(v1);
        BigDecimal den = nullToZero(v2);
        if (den.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return num.divide(den, scale, roundingMode);
    }

    /**
     * Sums an array of BigDecimal values.
     * Skips null values and returns zero if array is null or empty.
     *
     * @param values the array of BigDecimal values to sum
     * @return the sum of all non-null values
     */
    public static BigDecimal sum(BigDecimal... values) {
        if (values == null || values.length == 0) return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            if (value != null) {
                total = total.add(value);
            }
        }
        return total;
    }

    /**
     * Sums a collection of BigDecimal values.
     * Skips null values and returns zero if collection is null or empty.
     *
     * @param values the collection of BigDecimal values to sum
     * @return the sum of all non-null values
     */
    public static BigDecimal sum(Collection<BigDecimal> values) {
        if (values == null || values.isEmpty()) return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            if (value != null) {
                total = total.add(value);
            }
        }
        return total;
    }

    /**
     * Calculates the average of a collection of BigDecimal values.
     * Returns zero if collection is null or empty.
     *
     * @param values the collection of BigDecimal values
     * @param scale the number of decimal places for the result
     * @param roundingMode the rounding mode to apply
     * @return the average value with specified scale and rounding
     */
    public static BigDecimal average(Collection<BigDecimal> values, int scale, RoundingMode roundingMode) {
        if (values == null || values.isEmpty()) return BigDecimal.ZERO;
        BigDecimal total = sum(values);
        return total.divide(BigDecimal.valueOf(values.size()), scale, roundingMode);
    }

    /**
     * Calculates the average of a collection with default scale of 2 and HALF_UP rounding.
     * Returns zero if collection is null or empty.
     *
     * @param values the collection of BigDecimal values
     * @return the average value with scale 2
     */
    public static BigDecimal average(Collection<BigDecimal> values) {
        return average(values, 2, RoundingMode.HALF_UP);
    }

    /**
     * Formats a BigDecimal to a string with specified scale using HALF_UP rounding.
     *
     * @param value the BigDecimal to format
     * @param scale the number of decimal places
     * @return the formatted string, or empty string if value is null
     */
    public static String format(BigDecimal value, int scale) {
        return format(value, scale, RoundingMode.HALF_UP);
    }

    /**
     * Formats a BigDecimal to a string with specified scale and rounding mode.
     *
     * @param value the BigDecimal to format
     * @param scale the number of decimal places
     * @param roundingMode the rounding mode to apply
     * @return the formatted string, or empty string if value is null
     */
    public static String format(BigDecimal value, int scale, RoundingMode roundingMode) {
        return value == null ? "" : value.setScale(scale, roundingMode).toString();
    }

}
