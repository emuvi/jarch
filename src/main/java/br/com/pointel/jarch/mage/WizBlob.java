package br.com.pointel.jarch.mage;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import javax.sql.rowset.serial.SerialBlob;

public class WizBlob {

    private WizBlob() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Blob.class)
            || WizLang.isChildOf(value.getClass(), byte[].class)
            || WizLang.isChildOf(value.getClass(), Clob.class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Serializable.class);
    }

    public static Blob get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Blob.class)) {
            return Blob.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            return new SerialBlob(byte[].class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), Clob.class)) {
            var clob = Clob.class.cast(value);
            return new SerialBlob(clob.getSubString(1, (int) clob.length()).getBytes(StandardCharsets.UTF_8));
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            return new SerialBlob(string.getBytes(StandardCharsets.UTF_8));
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            var number = Number.class.cast(value);
            return new SerialBlob(String.valueOf(number).getBytes(StandardCharsets.UTF_8));
        }
        if (WizLang.isChildOf(value.getClass(), Serializable.class)) {
            try (var bos = new ByteArrayOutputStream();
                var oos = new ObjectOutputStream(bos)) {
                oos.writeObject(value);
                oos.flush();
                return new SerialBlob(bos.toByteArray());
            }
        }
        throw new Exception("Could not convert to a Blob value the value of class: " + value.getClass().getName());
    }

}
