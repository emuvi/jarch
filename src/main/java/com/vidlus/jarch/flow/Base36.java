package com.vidlus.jarch.flow;

/**
 * A utility class for safely converting standard Base10 (decimal) numbers
 * to and from compressed Base36 strings. Base36 utilizes [0-9A-Z] as digits,
 * making it ideal for creating short alphanumeric IDs that are URL-safe.
 */
public class Base36 {
    
    public static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final int BASE = ALPHABET.length();

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

    private static Integer fromBase10(Integer i, final StringBuilder sb) {
        int rem = i % BASE;
        sb.append(ALPHABET.charAt(rem));
        return i / BASE;
    }

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

    private static Integer toBase10(char[] chars) {
        Integer n = 0;
        for (Integer i = chars.length - 1; i >= 0; i--) {
            n += toBase10(ALPHABET.indexOf(chars[i]), i);
        }
        return n;
    }

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

    private static Long toBase10Lon(char[] chars) {
        Long n = 0l;
        for (int i = chars.length - 1; i >= 0; i--) {
            n += toBase10Lon(ALPHABET.indexOf(chars[i]), i);
        }
        return n;
    }

    private static Long toBase10Lon(Integer n, Integer pow) {
        return n * ((Double) Math.pow(BASE, pow)).longValue();
    }
    
}
