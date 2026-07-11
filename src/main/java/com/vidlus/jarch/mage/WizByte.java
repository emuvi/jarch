package com.vidlus.jarch.mage;

public class WizByte {

    private WizByte() {
    }

    /**
     * Checks if the given value can be converted to a Byte.
     * Supports Byte, Number, Boolean, and String types.
     *
     * @param value the value to check
     * @return true if value can be converted to Byte; false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Byte.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given value to a Byte.
     * Handles Byte, Number, Boolean (true=1, false=0), and String types.
     * Blank strings return null.
     *
     * @param value the value to convert
     * @return the converted Byte, or null if value is null or blank string
     * @throws Exception if the value cannot be converted to Byte
     */
    public static Byte get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Byte.class)) {
            return Byte.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).byteValue();
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? (byte) 1 : (byte) 0;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Byte.parseByte(string);
        }
        throw new Exception("Could not convert to an Byte value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts the given value to a Byte, returning a default if conversion fails.
     * 
     * @param value the value to convert
     * @param orDefault the default Byte to return if conversion fails
     * @return the converted Byte, or orDefault if conversion fails or value is null
     */
    public static Byte get(Object value, Byte orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Formats a Byte as a string.
     *
     * @param value the Byte to format
     * @return the string representation, or empty string if value is null
     */
    public static String format(Byte value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Returns the given value or 0 if value is null.
     *
     * @param value the Byte value
     * @return the value, or 0 if value is null
     */
    public static Byte nullToZero(Byte value) {
        return value == null ? (byte) 0 : value;
    }

    /**
     * Returns the given value or a default value if value is null.
     *
     * @param value the Byte value
     * @param defaultValue the default value to return if value is null
     * @return the value, or defaultValue if value is null
     */
    public static Byte defaultIfNull(Byte value, Byte defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * Converts a Byte to a primitive byte.
     * Null values are treated as 0.
     *
     * @param value the Byte to convert
     * @return the primitive byte value, or 0 if value is null
     */
    public static byte toPrimitive(Byte value) {
        return value != null ? value : 0;
    }

    /**
     * Converts a Byte to a primitive byte with a custom default for null.
     *
     * @param value the Byte to convert
     * @param defaultIfNull the default byte value to use if value is null
     * @return the primitive byte value, or defaultIfNull if value is null
     */
    public static byte toPrimitive(Byte value, byte defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Checks if two Byte values are equal.
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return true if both values are equal; false otherwise (including if either is null)
     */
    public static boolean isEqual(Byte v1, Byte v2) {
        if (v1 == v2) return true;
        if (v1 == null || v2 == null) return false;
        return v1.equals(v2);
    }

    /**
     * Checks if v1 is greater than v2.
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return true if v1 > v2; false otherwise (including if either is null)
     */
    public static boolean isGreaterThan(Byte v1, Byte v2) {
        if (v1 == null || v2 == null) return false;
        return v1 > v2;
    }

    /**
     * Checks if v1 is greater than or equal to v2.
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return true if v1 >= v2; false otherwise (including if either is null)
     */
    public static boolean isGreaterThanOrEqual(Byte v1, Byte v2) {
        if (v1 == null || v2 == null) return false;
        return v1 >= v2;
    }

    /**
     * Checks if v1 is less than v2.
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return true if v1 < v2; false otherwise (including if either is null)
     */
    public static boolean isLessThan(Byte v1, Byte v2) {
        if (v1 == null || v2 == null) return false;
        return v1 < v2;
    }

    /**
     * Checks if v1 is less than or equal to v2.
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return true if v1 <= v2; false otherwise (including if either is null)
     */
    public static boolean isLessThanOrEqual(Byte v1, Byte v2) {
        if (v1 == null || v2 == null) return false;
        return v1 <= v2;
    }

    /**
     * Returns the minimum of two Byte values.
     * Handles null values gracefully.
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return the smaller value, or the non-null value if one is null
     */
    public static Byte min(Byte v1, Byte v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1 <= v2 ? v1 : v2;
    }

    /**
     * Returns the maximum of two Byte values.
     * Handles null values gracefully.
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return the larger value, or the non-null value if one is null
     */
    public static Byte max(Byte v1, Byte v2) {
        if (v1 == null) return v2;
        if (v2 == null) return v1;
        return v1 >= v2 ? v1 : v2;
    }

    /**
     * Returns the minimum of multiple byte values.
     * Returns 0 if no values provided.
     *
     * @param values the byte values to compare
     * @return the smallest value, or 0 if values array is null or empty
     */
    public static byte min(byte... values) {
        if (values == null || values.length == 0) return 0;
        byte min = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
            }
        }
        return min;
    }

    /**
     * Returns the maximum of multiple byte values.
     * Returns 0 if no values provided.
     *
     * @param values the byte values to compare
     * @return the largest value, or 0 if values array is null or empty
     */
    public static byte max(byte... values) {
        if (values == null || values.length == 0) return 0;
        byte max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
            }
        }
        return max;
    }

    /**
     * Adds two Byte values.
     * Treats null values as 0. Result is cast to byte (may overflow).
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return the sum of v1 and v2 as a Byte
     */
    public static Byte add(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) + toPrimitive(v2));
    }

    /**
     * Subtracts v2 from v1.
     * Treats null values as 0. Result is cast to byte (may overflow).
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return the difference v1 - v2 as a Byte
     */
    public static Byte subtract(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) - toPrimitive(v2));
    }

    /**
     * Multiplies two Byte values.
     * Treats null values as 0. Result is cast to byte (may overflow).
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return the product v1 * v2 as a Byte
     */
    public static Byte multiply(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) * toPrimitive(v2));
    }

    /**
     * Divides v1 by v2.
     * Treats null values as 0. Result is cast to byte (may overflow).
     *
     * @param v1 the dividend
     * @param v2 the divisor
     * @return the quotient v1 / v2 as a Byte
     * @throws ArithmeticException if v2 is zero
     */
    public static Byte divide(Byte v1, Byte v2) {
        byte den = toPrimitive(v2);
        if (den == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return (byte) (toPrimitive(v1) / den);
    }
    
    /**
     * Calculates the remainder of v1 divided by v2.
     * Treats null values as 0. Result is cast to byte (may overflow).
     *
     * @param v1 the dividend
     * @param v2 the divisor
     * @return the remainder v1 % v2 as a Byte
     * @throws ArithmeticException if v2 is zero
     */
    public static Byte remainder(Byte v1, Byte v2) {
        byte den = toPrimitive(v2);
        if (den == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return (byte) (toPrimitive(v1) % den);
    }

    /**
     * Performs bitwise AND on two Byte values.
     * Treats null values as 0.
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return the bitwise AND result as a Byte
     */
    public static Byte and(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) & toPrimitive(v2));
    }

    /**
     * Performs bitwise OR on two Byte values.
     * Treats null values as 0.
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return the bitwise OR result as a Byte
     */
    public static Byte or(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) | toPrimitive(v2));
    }

    /**
     * Performs bitwise XOR on two Byte values.
     * Treats null values as 0.
     *
     * @param v1 the first Byte value
     * @param v2 the second Byte value
     * @return the bitwise XOR result as a Byte
     */
    public static Byte xor(Byte v1, Byte v2) {
        return (byte) (toPrimitive(v1) ^ toPrimitive(v2));
    }

    /**
     * Performs bitwise NOT (complement) on a Byte value.
     * Treats null as 0.
     *
     * @param value the Byte value to invert
     * @return the bitwise NOT result as a Byte
     */
    public static Byte not(Byte value) {
        return (byte) (~toPrimitive(value));
    }
    
    /**
     * Shifts a Byte value left by the specified number of bits.
     * Treats null as 0. Result is cast to byte (may overflow).
     *
     * @param value the Byte value to shift
     * @param shift the number of bits to shift left
     * @return the left-shifted value as a Byte
     */
    public static Byte shiftLeft(Byte value, int shift) {
        return (byte) (toPrimitive(value) << shift);
    }
    
    /**
     * Shifts a Byte value right by the specified number of bits (signed).
     * Treats null as 0. Result is cast to byte (may overflow).
     *
     * @param value the Byte value to shift
     * @param shift the number of bits to shift right
     * @return the right-shifted value as a Byte
     */
    public static Byte shiftRight(Byte value, int shift) {
        return (byte) (toPrimitive(value) >> shift);
    }
    
    /**
     * Shifts a Byte value right by the specified number of bits (unsigned).
     * Treats null as 0. Result is cast to byte (may overflow).
     *
     * @param value the Byte value to shift
     * @param shift the number of bits to shift right
     * @return the unsigned right-shifted value as a Byte
     */
    public static Byte unsignedShiftRight(Byte value, int shift) {
        return (byte) ((toPrimitive(value) & 0xFF) >>> shift);
    }

}
