package br.com.pointel.jarch.mage;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import br.com.pointel.jarch.data.Base;
import br.com.pointel.jarch.data.DataClazz;
import br.com.pointel.jarch.flow.FixVals;

public class WizObject {

    public static final String dataClazzPrefix = "dataClazz:";
    public static final String objectOnBase64Prefix = "object^64:";

    private WizObject() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), DataClazz.class)
            || WizLang.isChildOf(value.getClass(), String.class)
            || WizLang.isChildOf(value.getClass(), Object.class);
    }

    public static Object get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), DataClazz.class)) {
            return DataClazz.class.cast(value).getValue();
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isEmpty()) return null;
            if (string.startsWith(dataClazzPrefix)) {
                return DataClazz.fromChars(string.substring(dataClazzPrefix.length())).getValue();
            } else if (string.startsWith(WizBytes.byteArrayOnBase64Prefix)) {
                return WizBytes.decodeFromBase64(string.substring(WizBytes.byteArrayOnBase64Prefix.length()));
            } else if (string.startsWith(WizObject.objectOnBase64Prefix)) {
                var bytes = WizBytes.decodeFromBase64(string.substring(WizObject.objectOnBase64Prefix.length()));
                try (var bis = new ByteArrayInputStream(bytes);
                        var ois = new ObjectInputStream(bis)) {
                    return ois.readObject();
                }
            } else {
                return string;
            }
        }
        return value;
    }

    public static String format(Object value) {
        if (value == null) return "";
        if (WizLang.isChildOf(value.getClass(), DataClazz.class)) {
            return dataClazzPrefix + DataClazz.class.cast(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), FixVals.class)) {
            return dataClazzPrefix + new DataClazz(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), Base.class)) {
            return dataClazzPrefix + new DataClazz(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), Record.class)) {
            return dataClazzPrefix + new DataClazz(value).toString();
        }
        if (WizLang.isChildOf(value.getClass(), byte[].class)) {
            return WizBytes.byteArrayOnBase64Prefix + WizBytes.encodeToBase64(byte[].class.cast(value));
        }
        try (var bos = new java.io.ByteArrayOutputStream();
                var oos = new java.io.ObjectOutputStream(bos)) {
            oos.writeObject(value);
            oos.flush();
            return objectOnBase64Prefix + WizBytes.encodeToBase64(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object of class: " + value.getClass().getName(), e);
        }
    }

}
