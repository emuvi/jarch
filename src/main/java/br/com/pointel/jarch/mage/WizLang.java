package br.com.pointel.jarch.mage;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import br.com.pointel.jarch.flow.FixBool;
import br.com.pointel.jarch.flow.FixByte;
import br.com.pointel.jarch.flow.FixChar;
import br.com.pointel.jarch.flow.FixChars;
import br.com.pointel.jarch.flow.FixDouble;
import br.com.pointel.jarch.flow.FixFloat;
import br.com.pointel.jarch.flow.FixInt;
import br.com.pointel.jarch.flow.FixLong;
import br.com.pointel.jarch.flow.FixObject;
import br.com.pointel.jarch.flow.FixShort;
import br.com.pointel.jarch.flow.FixVals;

public class WizLang {

    private static final Logger log = LoggerFactory.getLogger(WizLang.class);
    
    private static final String OS_NAME_PROPERTY = "os.name";

    private WizLang() {
    }


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
        return clazz.equals(boolean.class) ||
            clazz.equals(byte.class) ||
            clazz.equals(char.class) ||
            clazz.equals(double.class) ||
            clazz.equals(float.class) ||
            clazz.equals(int.class) ||
            clazz.equals(long.class) ||
            clazz.equals(short.class) ||
            clazz.equals(void.class);
    }

    public static boolean isClassChildOf(Class<?> clazz, Class<?> ofParent) {
        if (clazz == null || ofParent == null) {
            return false;
        }
        clazz = getFromPrimitive(clazz);
        ofParent = getFromPrimitive(ofParent);
        return ofParent.isAssignableFrom(clazz);
    }

    public static Object[] getValuesFromMembers(Object ofObject) throws Exception {
        if (ofObject == null) {
            return null;
        }
        var fields = ofObject.getClass().getDeclaredFields();
        var values = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            var field = fields[i];
            var accessible = field.canAccess(ofObject);
            if (!accessible) {
                field.setAccessible(true);
            }
            values[i] = field.get(ofObject);
            if (!accessible) {
                field.setAccessible(false);
            }
        }
        return values;
    }

    public static void fixEnvsOnMembers(Object ofValue) throws IllegalAccessException, Exception {
        for (var field : ofValue.getClass().getDeclaredFields()) {
            var mods = field.getModifiers();
            if (Modifier.isStatic(mods) || Modifier.isFinal(mods) || Modifier.isTransient(mods)) {
                continue;
            }
            var value = field.get(ofValue);
            if (value instanceof String strValue) {
                value = WizChars.replaceEnvVars(strValue);
                try {
                    WizLang.forceSetField(field, ofValue, value);
                } catch (Exception e) {
                    log.error("Error setting field value: " + field.getName(), e);
                }
            }
        }
    }

    public static void fixNullsOnMembers(Object ofValue) throws Exception {
        for (var field : ofValue.getClass().getDeclaredFields()) {
            var mods = field.getModifiers();
            if (Modifier.isStatic(mods) || Modifier.isFinal(mods) || Modifier.isTransient(mods)) {
                continue;
            }
            var value = field.get(ofValue);
            if (value == null || value.toString().isEmpty()) {
                if (field.isAnnotationPresent(FixBool.class)) {
                    value = field.getAnnotation(FixBool.class).value();
                } else if (field.isAnnotationPresent(FixByte.class)) {
                    value = field.getAnnotation(FixByte.class).value();
                } else if (field.isAnnotationPresent(FixChar.class)) {
                    value = field.getAnnotation(FixChar.class).value();
                } else if (field.isAnnotationPresent(FixChars.class)) {
                    value = field.getAnnotation(FixChars.class).value();
                } else if (field.isAnnotationPresent(FixDouble.class)) {
                    value = field.getAnnotation(FixDouble.class).value();
                } else if (field.isAnnotationPresent(FixFloat.class)) {
                    value = field.getAnnotation(FixFloat.class).value();
                } else if (field.isAnnotationPresent(FixInt.class)) {
                    value = field.getAnnotation(FixInt.class).value();
                } else if (field.isAnnotationPresent(FixLong.class)) {
                    value = field.getAnnotation(FixLong.class).value();
                } else if (field.isAnnotationPresent(FixShort.class)) {
                    value = field.getAnnotation(FixShort.class).value();
                } else if (field.isAnnotationPresent(FixObject.class)) {
                    var valueStr = field.getAnnotation(FixObject.class).value();
                    var valueType = field.getAnnotation(FixObject.class).type();
                    value = WizData.fromChars(valueStr, valueType);
                } else {
                    try {
                        value = field.getType().getConstructor().newInstance();
                    } catch (Exception e) {
                        log.error("Error creating instance of class: " + field.getType().getCanonicalName(), e);
                    }
                }
                if (value instanceof FixVals fixable) {
                    fixable.fixNulls();
                }
                try {
                    WizLang.forceSetField(field, ofValue, value);
                } catch (Exception e) {
                    log.error("Error setting field value: " + field.getName(), e);
                }
            }
        }
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
