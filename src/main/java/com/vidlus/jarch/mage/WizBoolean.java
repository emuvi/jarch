package com.vidlus.jarch.mage;

public class WizBoolean {

    private WizBoolean() {
    }

    /**
     * Checks if the given value can be converted to a Boolean.
     * Supports Boolean, Number, and String types.
     *
     * @param value the value to check
     * @return true if value can be converted to Boolean; false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given value to a Boolean.
     * Handles Boolean, Number (positive > 0), and String types.
     * Blank strings return null.
     *
     * @param value the value to convert
     * @return the converted Boolean, or null if value is null or blank string
     * @throws Exception if the value cannot be converted to Boolean
     */
    public static Boolean get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return Boolean.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).intValue() > 0;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Boolean.parseBoolean(string);
        }
        throw new Exception("Could not convert to an Boolean value the value of class: " + value.getClass().getName());
    }

    /**
     * Formats a Boolean as a string.
     *
     * @param value the Boolean to format
     * @return the string representation ("true"/"false"), or empty string if value is null
     */
    public static String format(Boolean value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts the given value to a Boolean, returning a default if conversion fails.
     * 
     * @param value the value to convert
     * @param orDefault the default Boolean to return if conversion fails
     * @return the converted Boolean, or orDefault if conversion fails or value is null
     */
    public static Boolean get(Object value, Boolean orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Checks if a Boolean value is true.
     * Returns false for null or false values.
     *
     * @param value the Boolean to check
     * @return true if value is Boolean.TRUE; false otherwise
     */
    public static boolean isTrue(Boolean value) {
        return Boolean.TRUE.equals(value);
    }

    /**
     * Checks if a Boolean value is not true.
     * Returns true for null or false values.
     *
     * @param value the Boolean to check
     * @return true if value is not Boolean.TRUE; false otherwise
     */
    public static boolean isNotTrue(Boolean value) {
        return !isTrue(value);
    }

    /**
     * Checks if a Boolean value is false.
     * Returns false for null or true values.
     *
     * @param value the Boolean to check
     * @return true if value is Boolean.FALSE; false otherwise
     */
    public static boolean isFalse(Boolean value) {
        return Boolean.FALSE.equals(value);
    }

    /**
     * Checks if a Boolean value is not false.
     * Returns true for null or true values.
     *
     * @param value the Boolean to check
     * @return true if value is not Boolean.FALSE; false otherwise
     */
    public static boolean isNotFalse(Boolean value) {
        return !isFalse(value);
    }

    /**
     * Converts a Boolean to a primitive boolean.
     * Null values are treated as false.
     *
     * @param value the Boolean to convert
     * @return the primitive boolean value, or false if value is null
     */
    public static boolean toPrimitive(Boolean value) {
        return value != null ? value : false;
    }

    /**
     * Converts a Boolean to a primitive boolean with a custom default for null.
     *
     * @param value the Boolean to convert
     * @param defaultIfNull the default boolean value to use if value is null
     * @return the primitive boolean value, or defaultIfNull if value is null
     */
    public static boolean toPrimitive(Boolean value, boolean defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Returns the logical negation of a Boolean value.
     * Null remains null.
     *
     * @param value the Boolean to negate
     * @return the negated Boolean, or null if value is null
     */
    public static Boolean negate(Boolean value) {
        if (value == null) return null;
        return !value;
    }

    /**
     * Performs logical AND operation on all given boolean values.
     * Returns false if no values provided or any value is false.
     *
     * @param values the boolean values to combine
     * @return true if all values are true; false otherwise
     */
    public static boolean and(boolean... values) {
        if (values == null || values.length == 0) return false;
        for (boolean value : values) {
            if (!value) return false;
        }
        return true;
    }

    /**
     * Performs logical OR operation on all given boolean values.
     * Returns false if no values provided; true if any value is true.
     *
     * @param values the boolean values to combine
     * @return true if at least one value is true; false otherwise
     */
    public static boolean or(boolean... values) {
        if (values == null || values.length == 0) return false;
        for (boolean value : values) {
            if (value) return true;
        }
        return false;
    }

    /**
     * Performs logical XOR (exclusive OR) operation on all given boolean values.
     * Returns false if no values provided; true if odd number of values are true.
     *
     * @param values the boolean values to combine
     * @return true if an odd number of values are true; false otherwise
     */
    public static boolean xor(boolean... values) {
        if (values == null || values.length == 0) return false;
        boolean result = false;
        for (boolean value : values) {
            result ^= value;
        }
        return result;
    }

    /**
     * Converts a Boolean to an integer with custom mappings for each state.
     *
     * @param value the Boolean to convert
     * @param trueValue the integer to return if value is true
     * @param falseValue the integer to return if value is false
     * @param nullValue the integer to return if value is null
     * @return the mapped integer value
     */
    public static int toInteger(Boolean value, int trueValue, int falseValue, int nullValue) {
        if (value == null) return nullValue;
        return value ? trueValue : falseValue;
    }
    
    /**
     * Converts a Boolean to an integer using default mappings (true=1, false=0, null=0).
     *
     * @param value the Boolean to convert
     * @return 1 if true, 0 if false or null
     */
    public static int toInteger(Boolean value) {
        return toInteger(value, 1, 0, 0);
    }

    /**
     * Converts a Boolean to a string with custom mappings for each state.
     *
     * @param value the Boolean to convert
     * @param trueString the string to return if value is true
     * @param falseString the string to return if value is false
     * @param nullString the string to return if value is null
     * @return the mapped string value
     */
    public static String toString(Boolean value, String trueString, String falseString, String nullString) {
        if (value == null) return nullString;
        return value ? trueString : falseString;
    }

}
