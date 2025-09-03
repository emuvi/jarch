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

    public static Blob get(Object data) throws Exception {
        if (data == null) {
            return null;
        }
        if (data instanceof Blob blob) {
            return blob;
        }
        byte[] bytes;
        if (WizLang.isChildOf(data.getClass(), byte[].class)) {
            bytes = byte[].class.cast(data);
        } else if (data instanceof Serializable) {
            try (var bos = new ByteArrayOutputStream();
                 var oos = new ObjectOutputStream(bos)) {
                oos.writeObject(data);
                oos.flush();
                bytes = bos.toByteArray();
            }
        } else if (data instanceof Clob clob) {
            bytes = clob.getSubString(1, (int) clob.length()).getBytes(StandardCharsets.UTF_8);
        } else if (data instanceof String string) {
            bytes = string.getBytes(StandardCharsets.UTF_8);
        } else if (data instanceof Number number) {
            bytes = String.valueOf(number).getBytes(StandardCharsets.UTF_8);
        } else {
            throw new Exception("Could not convert to a Blob value the value of class: " + data.getClass().getName());
        }
        return new SerialBlob(bytes);
    }

}
