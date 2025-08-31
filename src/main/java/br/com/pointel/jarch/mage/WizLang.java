package br.com.pointel.jarch.mage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class WizLang {

    private WizLang() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static final String OS_NAME_PROPERTY = "os.name";

    public static Boolean isWin() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("win"));
    }

    public static Boolean isNix() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0);
    }

    public static Boolean isMac() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("mac"));
    }

    public static Boolean isSol() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("sunos"));
    }

    public static Class<?> getPrimitive(Class<?> ofClazz) {
        if (ofClazz == null) {
            return null;
        }
        if (ofClazz.equals(Boolean.class)) {
            return boolean.class;
        }
        if (ofClazz.equals(Byte.class)) {
            return byte.class;
        } else if (ofClazz.equals(Character.class)) {
            return char.class;
        } else if (ofClazz.equals(Double.class)) {
            return double.class;
        } else if (ofClazz.equals(Float.class)) {
            return float.class;
        } else if (ofClazz.equals(Integer.class)) {
            return int.class;
        } else if (ofClazz.equals(Long.class)) {
            return long.class;
        } else if (ofClazz.equals(Short.class)) {
            return short.class;
        } else if (ofClazz.equals(Void.class)) {
            return void.class;
        } else {
            return ofClazz;
        }
    }

    public static Class<?> getFromPrimitive(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        if (clazz.equals(boolean.class)) {
            return Boolean.class;
        }
        if (clazz.equals(byte.class)) {
            return Byte.class;
        } else if (clazz.equals(char.class)) {
            return Character.class;
        } else if (clazz.equals(double.class)) {
            return Double.class;
        } else if (clazz.equals(float.class)) {
            return Float.class;
        } else if (clazz.equals(int.class)) {
            return Integer.class;
        } else if (clazz.equals(long.class)) {
            return Long.class;
        } else if (clazz.equals(short.class)) {
            return Short.class;
        } else if (clazz.equals(void.class)) {
            return Void.class;
        } else {
            return clazz;
        }
    }

    public static Boolean isPrimitive(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        if (clazz.equals(boolean.class)) {
            return true;
        } else if (clazz.equals(byte.class)) {
            return true;
        } else if (clazz.equals(char.class)) {
            return true;
        } else if (clazz.equals(double.class)) {
            return true;
        } else if (clazz.equals(float.class)) {
            return true;
        } else if (clazz.equals(int.class)) {
            return true;
        } else if (clazz.equals(long.class)) {
            return true;
        } else if (clazz.equals(short.class)) {
            return true;
        } else if (clazz.equals(void.class)) {
            return true;
        }
        return false;
    }

    public static boolean isClassChildOf(Class<?> clazz, Class<?> parent) {
        if (clazz == null || parent == null) {
            return false;
        }
        clazz = getFromPrimitive(clazz);
        parent = getFromPrimitive(parent);
        return parent.isAssignableFrom(clazz);
    }

    public static void forceSetField(Field field, Object ofObject, Object toValue) throws Exception {
        var accessible = field.canAccess(ofObject);
        if (!accessible) {
            field.setAccessible(true);
        }
        field.set(ofObject, toValue);
        if (!accessible) {
            field.setAccessible(false);
        }
    }
}
