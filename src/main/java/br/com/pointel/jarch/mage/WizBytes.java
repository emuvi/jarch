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

    public static String byteArrayOnBase64Prefix = "byte[]^64:";

    private WizBytes() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), byte[].class)
            || WizLang.isChildOf(value.getClass(), Blob.class)
            || WizLang.isChildOf(value.getClass(), Clob.class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Serializable.class);
    }

    public static byte[] get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            return byte[].class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Blob.class)) {
            var blob = Blob.class.cast(value);
            return blob.getBytes(1, (int) blob.length());
        }
        if (WizLang.isChildOf(value.getClass(), Clob.class)) {
            var clob = Clob.class.cast(value);
            return clob.getSubString(1, (int) clob.length()).getBytes(StandardCharsets.UTF_8);
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isEmpty()) return null;
            if (string.startsWith(byteArrayOnBase64Prefix)) {
                return WizBytes.decodeFromBase64(string.substring(byteArrayOnBase64Prefix.length()));
            } else if (string.startsWith(WizObject.objectOnBase64Prefix)) {
                return WizBytes.decodeFromBase64(string.substring(WizObject.objectOnBase64Prefix.length()));
            } else {
                return string.getBytes(StandardCharsets.UTF_8);
            }
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return String.valueOf(Number.class.cast(value)).getBytes(StandardCharsets.UTF_8);
        }
        if (WizLang.isChildOf(value.getClass(), Serializable.class)) {
            try (var bos = new ByteArrayOutputStream();
                    var oos = new ObjectOutputStream(bos)) {
                oos.writeObject(Serializable.class.cast(value));
                oos.flush();
                return bos.toByteArray();
            }
        }
        throw new Exception("Could not convert to a byte[] value the value of class: " + value.getClass().getName());
    }

    public static String format(byte[] value) {
        if (value == null) return "";
        return byteArrayOnBase64Prefix + WizBytes.encodeToBase64(value);
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

    public static String getMD5(File file) throws Exception {
        return WizBytes.getMD5(Files.readAllBytes(file.toPath()));
    }

    public static String getMD5(byte[] bytes) throws Exception {
        return WizBytes.encodeToHex(MessageDigest.getInstance("MD5").digest(bytes));
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
