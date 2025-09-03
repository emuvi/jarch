package br.com.pointel.jarch.mage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Base64;

public class WizBytes {

    private WizBytes() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), byte[].class)
                || value instanceof Serializable
                || value instanceof Blob
                || value instanceof Clob
                || value instanceof String
                || value instanceof Number;
    }

    public static byte[] get(Object value) throws Exception {
        if (value == null) {
            return null;
        }
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            return byte[].class.cast(value);
        }
        if (value instanceof Serializable) {
            try (var bos = new ByteArrayOutputStream(); 
                var oos = new ObjectOutputStream(bos)) {
                oos.writeObject(value);
                oos.flush();
                return bos.toByteArray();
            }
        } else if (value instanceof Blob blob) {
            return blob.getBytes(1, (int) blob.length());
        } else if (value instanceof Clob clob) {
            return clob.getSubString(1, (int) clob.length()).getBytes();
        } else if (value instanceof String str) {
            return str.getBytes(StandardCharsets.UTF_8);
        } else if (value instanceof Number number) {
            return String.valueOf(number).getBytes(StandardCharsets.UTF_8);
        }
        throw new Exception("Could not convert to a byte[] value the value of class: " + value.getClass().getName());
    }

    public static String encodeToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] decodeFromBase64(String formatted) {
        return Base64.getDecoder().decode(formatted);
    }

    public static String encodeToHex(byte[] bytes) {
        var hexString = new StringBuilder(2 * bytes.length);
        for (byte element : bytes) {
            var hex = Integer.toHexString(0xff & element);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String getSHA1(File file) throws Exception {
        return WizBytes.getSHA1(Files.readAllBytes(file.toPath()));
    }

    public static String getSHA1(byte[] bytes) throws Exception {
        return WizBytes.encodeToHex(MessageDigest.getInstance("SHA-1").digest(bytes));
    }

    public static String getSHA256(File file) throws Exception {
        return WizBytes.getSHA256(Files.readAllBytes(file.toPath()));
    }

    public static String getSHA256(byte[] bytes) throws Exception {
        return WizBytes.encodeToHex(MessageDigest.getInstance("SHA-256").digest(bytes));
    }

}
