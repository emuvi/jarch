package com.vidlus.jarch.flow;

/**
 * A utility class for safely converting standard Base10 (decimal) numbers
 * to and from compressed Base36 strings. Base36 utilizes [0-9A-Z] as digits,
 * making it ideal for creating short alphanumeric IDs that are URL-safe.
 */
public class Base36 {
    
    /**
     * The standard Base36 alphabet using digits 0-9 and uppercase letters A-Z.
     */
    public static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * The radix or base size used for conversions, which is 36.
     */
    public static final int BASE = ALPHABET.length();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Base36() {}

    /**
     * Converts a 32-bit integer to a Base36 encoded string.
     *
     * @param i the decimal integer
     * @return the Base36 string
     */
    public static String fromBase10(Integer i) {
        StringBuilder sb = new StringBuilder("");
        if (i == 0) {
            return "0"; // BugFix: Was originally 'a' which wasn't 0-indexed properly
        }
        while (i > 0) {
            i = fromBase10(i, sb);
        }
        return sb.reverse().toString();
    }

    /**
     * Converts a 64-bit long integer to a Base36 encoded string.
     *
     * @param i the decimal long
     * @return the Base36 string
     */
    public static String fromBase10(Long i) {
        StringBuilder sb = new StringBuilder("");
        if (i == 0) {
            return "0"; // BugFix: Was originally 'a'
        }
        while (i > 0) {
            i = fromBase10(i, sb);
        }
        return sb.reverse().toString();
    }

    /**
     * Recursive helper method to compute the Base36 string for a 32-bit integer.
     *
     * @param i  the current remaining decimal value
     * @param sb the StringBuilder to append the computed character to
     * @return the new value of i divided by BASE
     */
    private static Integer fromBase10(Integer i, final StringBuilder sb) {
        int rem = i % BASE;
        sb.append(ALPHABET.charAt(rem));
        return i / BASE;
    }

    /**
     * Recursive helper method to compute the Base36 string for a 64-bit long.
     *
     * @param i  the current remaining decimal value
     * @param sb the StringBuilder to append the computed character to
     * @return the new value of i divided by BASE
     */
    private static Long fromBase10(Long i, final StringBuilder sb) {
        Long rem = i % BASE;
        sb.append(ALPHABET.charAt(rem.intValue()));
        return i / BASE;
    }

    /**
     * Reverts a Base36 encoded string back into a 32-bit decimal integer.
     *
     * @param str the Base36 string
     * @return the original integer
     */
    public static Integer toBase10(String str) {
        if (str.equals("a")) return 0; // Backwards compatibility for original code flaw
        return toBase10(new StringBuilder(str).reverse().toString().toUpperCase().toCharArray());
    }

    /**
     * Internal helper to convert a character array representing a reversed Base36 string 
     * back into a 32-bit integer.
     *
     * @param chars the reversed Base36 character array
     * @return the original integer
     */
    private static Integer toBase10(char[] chars) {
        Integer n = 0;
        for (Integer i = chars.length - 1; i >= 0; i--) {
            n += toBase10(ALPHABET.indexOf(chars[i]), i);
        }
        return n;
    }

    /**
     * Helper to compute the decimal value of a specific character in the Base36 string based on its position.
     *
     * @param n   the decimal value of the single character
     * @param pow the positional power
     * @return the value to add to the total decimal result
     */
    private static Integer toBase10(Integer n, Integer pow) {
        return n * ((Double) Math.pow(BASE, pow)).intValue();
    }

    /**
     * Reverts a Base36 encoded string back into a 64-bit decimal long.
     *
     * @param str the Base36 string
     * @return the original long
     */
    public static Long toBase10Lon(String str) {
        if (str.equals("a")) return 0L; // Backwards compatibility
        return toBase10Lon(new StringBuilder(str).reverse().toString().toUpperCase().toCharArray());
    }

    /**
     * Internal helper to convert a character array representing a reversed Base36 string 
     * back into a 64-bit long.
     *
     * @param chars the reversed Base36 character array
     * @return the original long
     */
    private static Long toBase10Lon(char[] chars) {
        Long n = 0l;
        for (int i = chars.length - 1; i >= 0; i--) {
            n += toBase10Lon(ALPHABET.indexOf(chars[i]), i);
        }
        return n;
    }

    /**
     * Helper to compute the decimal value of a specific character in the Base36 string based on its position, 
     * for 64-bit long numbers.
     *
     * @param n   the decimal value of the single character
     * @param pow the positional power
     * @return the value to add to the total decimal result
     */
    private static Long toBase10Lon(Integer n, Integer pow) {
        return n * ((Double) Math.pow(BASE, pow)).longValue();
    }
    
}
