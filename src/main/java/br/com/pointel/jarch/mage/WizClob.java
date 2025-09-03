package br.com.pointel.jarch.mage;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import javax.sql.rowset.serial.SerialClob;

public class WizClob {

    private WizClob() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Clob.class)
            || WizLang.isChildOf(value.getClass(), Blob.class)
            || WizLang.isChildOf(value.getClass(), byte[].class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Serializable.class);
    }

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

}
