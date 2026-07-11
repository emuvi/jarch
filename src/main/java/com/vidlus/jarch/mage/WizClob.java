package com.vidlus.jarch.mage;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import javax.sql.rowset.serial.SerialClob;

public class WizClob {

    private WizClob() {
    }

    /**
     * Checks if the given value can be converted to a Clob.
     * Supports Clob, Blob, byte[], String, Number, and Serializable objects.
     *
     * @param value the value to check
     * @return true if the value can be converted to Clob; false otherwise
     */
    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Clob.class)
            || WizLang.isChildOf(value.getClass(), Blob.class)
            || WizLang.isChildOf(value.getClass(), byte[].class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Serializable.class);
    }

    /**
     * Converts the given value to a Clob.
     * Handles Clob, Blob, byte[], String, Number, and Serializable types.
     *
     * @param value the value to convert
     * @return the converted Clob, or null if value is null
     * @throws Exception if the value cannot be converted
     */
    public static Clob get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Clob.class)) {
            return Clob.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Blob.class)) {
            var blob = Blob.class.cast(value);
            return new SerialClob(new String(blob.getBytes(1, (int) blob.length()), StandardCharsets.UTF_8).toCharArray());
        }
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            var bytes = byte[].class.cast(value);
            return new SerialClob(new String(bytes, StandardCharsets.UTF_8).toCharArray());
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            return new SerialClob(string.toCharArray());
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            var number = Number.class.cast(value);
            return new SerialClob(String.valueOf(number).toCharArray());
        }
        if (WizLang.isChildOf(value.getClass(), Serializable.class)) {
            try (var bos = new ByteArrayOutputStream();
                var oos = new ObjectOutputStream(bos)) {
                oos.writeObject(value);
                oos.flush();
                String base = bos.toString(StandardCharsets.UTF_8);
                return new SerialClob(base.toCharArray());
            }
        }
        throw new Exception("Could not convert to a Clob value the value of class: " + value.getClass().getName());
    }

    /**
     * Converts the given value to a Clob, returning a default if conversion fails.
     *
     * @param value the value to convert
     * @param orDefault the default value to return if conversion fails
     * @return the converted Clob, or orDefault if conversion fails
     */
    public static Clob get(Object value, Clob orDefault) {
        try {
            return get(value);
        } catch (Exception e) {
            return orDefault;
        }
    }

    /**
     * Reads the entire Clob into a String.
     *
     * @param clob the Clob to read
     * @return the string content, or null if Clob is null
     * @throws Exception if reading fails
     */
    public static String toString(Clob clob) throws Exception {
        if (clob == null) return null;
        return clob.getSubString(1, (int) clob.length());
    }

    /**
     * Reads a portion of the Clob into a String safely.
     *
     * @param clob the Clob to read
     * @param pos the first character to extract (1-based)
     * @param length the number of characters to extract
     * @return the extracted string, or null if Clob is null
     */
    public static String getSubString(Clob clob, long pos, int length) {
        if (clob == null) return null;
        try {
            long clobLength = clob.length();
            if (pos > clobLength) return "";
            int safeLength = (int) Math.min(length, clobLength - pos + 1);
            return clob.getSubString(pos, safeLength);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the length of the Clob in characters.
     *
     * @param clob the Clob to check
     * @return the length of the Clob, or 0 if null
     */
    public static long length(Clob clob) {
        if (clob == null) return 0;
        try {
            return clob.length();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Checks if the given Clob is null or empty.
     *
     * @param clob the Clob to check
     * @return true if the Clob is null or has length 0; false otherwise
     */
    public static boolean isEmpty(Clob clob) {
        return length(clob) == 0;
    }

    /**
     * Checks if the given Clob is not null and not empty.
     *
     * @param clob the Clob to check
     * @return true if the Clob is not null and has length > 0; false otherwise
     */
    public static boolean isNotEmpty(Clob clob) {
        return !isEmpty(clob);
    }

    /**
     * Gets a character stream (Reader) from the Clob.
     *
     * @param clob the Clob to read
     * @return a Reader for the Clob content, or null if Clob is null
     */
    public static Reader getReader(Clob clob) {
        if (clob == null) return null;
        try {
            return clob.getCharacterStream();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Finds the index of a string pattern within the Clob.
     *
     * @param clob the Clob to search
     * @param searchStr the string to search for
     * @param start the position to start searching (1-based)
     * @return the position (1-based) where the string starts, or -1 if not found
     */
    public static long indexOf(Clob clob, String searchStr, long start) {
        if (clob == null || searchStr == null || start < 1) return -1;
        try {
            return clob.position(searchStr, start);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Compares two Clobs for content equality.
     *
     * @param clob1 the first Clob
     * @param clob2 the second Clob
     * @return true if both Clobs have the same content; false otherwise
     */
    public static boolean isEqual(Clob clob1, Clob clob2) {
        if (clob1 == clob2) return true;
        if (clob1 == null || clob2 == null) return false;
        try {
            long len1 = clob1.length();
            long len2 = clob2.length();
            if (len1 != len2) return false;
            if (len1 == 0) return true;
            
            // For small clobs, compare direct strings
            if (len1 <= 8192) {
                return clob1.getSubString(1, (int) len1).equals(clob2.getSubString(1, (int) len2));
            }
            
            // For large clobs, compare in chunks
            long pos = 1;
            int chunkSize = 8192;
            while (pos <= len1) {
                int readLen = (int) Math.min(chunkSize, len1 - pos + 1);
                String s1 = clob1.getSubString(pos, readLen);
                String s2 = clob2.getSubString(pos, readLen);
                if (!s1.equals(s2)) return false;
                pos += readLen;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Truncates the Clob to the specified length.
     *
     * @param clob the Clob to truncate
     * @param len the new length
     * @return true if successfully truncated; false otherwise
     */
    public static boolean truncate(Clob clob, long len) {
        if (clob == null || len < 0) return false;
        try {
            clob.truncate(len);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Safely frees the Clob resources.
     *
     * @param clob the Clob to free
     */
    public static void free(Clob clob) {
        if (clob != null) {
            try {
                clob.free();
            } catch (Exception e) {
                // Ignore
            }
        }
    }

}
