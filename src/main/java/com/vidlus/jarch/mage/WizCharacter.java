package com.vidlus.jarch.mage;

public class WizCharacter {

    private WizCharacter() {
    }

    /**
     * Checks if the given value can be converted to a Character.
     * Supports Character, Number, and String types.
     *
     * @param value the value to check
     * @return true if value can be converted to Character; false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Character.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    /**
     * Converts the given value to a Character.
     * Handles Character, Number (using intValue as char code), and String types.
     * Blank strings return null. For strings, only the first character is used.
     *
     * @param value the value to convert
     * @return the converted Character, or null if value is null or blank string
     * @throws Exception if the value cannot be converted to Character
     */
    public static Character get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Character.class)) {
            return Character.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return (char) Number.class.cast(value).intValue();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return string.charAt(0);
        }
        throw new Exception("Could not convert to a Character value the value of class: " + value.getClass().getName());
    }

    /**
     * Formats a Character as a string.
     *
     * @param value the Character to format
     * @return the string representation, or empty string if value is null
     */
    public static String format(Character value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Converts the given value to a Character, returning a default if conversion fails.
     * 
     * @param value the value to convert
     * @param orDefault the default Character to return if conversion fails
     * @return the converted Character, or orDefault if conversion fails or value is null
     */
    public static Character get(Object value, Character orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Converts a Character to a primitive char.
     * Null values are treated as the null character ('\u0000').
     *
     * @param value the Character to convert
     * @return the primitive char value, or '\u0000' if value is null
     */
    public static char toPrimitive(Character value) {
        return value != null ? value : '\u0000';
    }

    /**
     * Converts a Character to a primitive char with a custom default for null.
     *
     * @param value the Character to convert
     * @param defaultIfNull the default char value to use if value is null
     * @return the primitive char value, or defaultIfNull if value is null
     */
    public static char toPrimitive(Character value, char defaultIfNull) {
        return value != null ? value : defaultIfNull;
    }

    /**
     * Checks if two Character values are equal.
     *
     * @param c1 the first Character value
     * @param c2 the second Character value
     * @return true if both values are equal; false otherwise (including if either is null)
     */
    public static boolean isEqual(Character c1, Character c2) {
        if (c1 == c2) return true;
        if (c1 == null || c2 == null) return false;
        return c1.equals(c2);
    }

    /**
     * Checks if a Character is an ASCII character (value < 128).
     *
     * @param value the Character to check
     * @return true if value is not null and is ASCII; false otherwise
     */
    public static boolean isAscii(Character value) {
        return value != null && value < 128;
    }

    /**
     * Checks if a Character is an ASCII printable character (32-126).
     *
     * @param value the Character to check
     * @return true if value is not null and is a printable ASCII character; false otherwise
     */
    public static boolean isAsciiPrintable(Character value) {
        return value != null && value >= 32 && value < 127;
    }

    /**
     * Checks if a Character is a letter (a-z, A-Z, or other Unicode letters).
     *
     * @param value the Character to check
     * @return true if value is not null and is a letter; false otherwise
     */
    public static boolean isLetter(Character value) {
        return value != null && Character.isLetter(value);
    }

    /**
     * Checks if a Character is a digit (0-9).
     *
     * @param value the Character to check
     * @return true if value is not null and is a digit; false otherwise
     */
    public static boolean isDigit(Character value) {
        return value != null && Character.isDigit(value);
    }
    
    /**
     * Checks if a Character is either a letter or a digit.
     *
     * @param value the Character to check
     * @return true if value is not null and is a letter or digit; false otherwise
     */
    public static boolean isLetterOrDigit(Character value) {
        return value != null && Character.isLetterOrDigit(value);
    }

    /**
     * Checks if a Character is whitespace (space, tab, newline, etc.).
     *
     * @param value the Character to check
     * @return true if value is not null and is whitespace; false otherwise
     */
    public static boolean isWhitespace(Character value) {
        return value != null && Character.isWhitespace(value);
    }

    /**
     * Checks if a Character is uppercase.
     *
     * @param value the Character to check
     * @return true if value is not null and is uppercase; false otherwise
     */
    public static boolean isUpperCase(Character value) {
        return value != null && Character.isUpperCase(value);
    }

    /**
     * Checks if a Character is lowercase.
     *
     * @param value the Character to check
     * @return true if value is not null and is lowercase; false otherwise
     */
    public static boolean isLowerCase(Character value) {
        return value != null && Character.isLowerCase(value);
    }

    /**
     * Converts a Character to uppercase.
     *
     * @param value the Character to convert
     * @return the uppercase Character, or null if value is null
     */
    public static Character toUpperCase(Character value) {
        if (value == null) return null;
        return Character.toUpperCase(value);
    }

    /**
     * Converts a Character to lowercase.
     *
     * @param value the Character to convert
     * @return the lowercase Character, or null if value is null
     */
    public static Character toLowerCase(Character value) {
        if (value == null) return null;
        return Character.toLowerCase(value);
    }
    
    /**
     * Compares two Character values lexicographically.
     * Null is considered less than any non-null character.
     *
     * @param c1 the first Character value
     * @param c2 the second Character value
     * @return a negative value if c1 < c2, zero if c1 == c2, positive if c1 > c2
     */
    public static int compare(Character c1, Character c2) {
        if (c1 == c2) return 0;
        if (c1 == null) return -1;
        if (c2 == null) return 1;
        return c1.compareTo(c2);
    }

}
