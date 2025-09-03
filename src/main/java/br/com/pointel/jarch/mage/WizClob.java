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
        if (value == null) {
            return false;
        }
        return WizLang.isChildOf(value.getClass(), byte[].class)
                || value instanceof Serializable
                || value instanceof Blob
                || value instanceof Clob
                || value instanceof String
                || value instanceof Number;
    }

    public static Clob get(Object data) throws Exception {
        if (data == null) {
            return null;
        }
        if (WizLang.isChildOf(data.getClass(), Clob.class)) {
            return Clob.class.cast(data);
        }
        String base;
        if (data instanceof String string) {
            base = string;
        } else if (WizLang.isChildOf(data.getClass(), byte[].class)) {
            base = new String(byte[].class.cast(data), StandardCharsets.UTF_8);
        } else if (data instanceof Serializable) {
            try (var bos = new ByteArrayOutputStream();
                 var oos = new ObjectOutputStream(bos)) {
                oos.writeObject(data);
                oos.flush();
                base = bos.toString(StandardCharsets.UTF_8);
            }
        } else if (data instanceof Number number) {
            base = String.valueOf(number);
        } else {
            throw new Exception("Could not convert to a Clob value the value of class: " + data.getClass().getName());
        }
        return new SerialClob(base.toCharArray());
    }

}
