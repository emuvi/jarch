package br.com.pointel.jarch.mage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.pointel.jarch.data.Nature;
import br.com.pointel.jarch.flow.FixBool;
import br.com.pointel.jarch.flow.FixByte;
import br.com.pointel.jarch.flow.FixChar;
import br.com.pointel.jarch.flow.FixChars;
import br.com.pointel.jarch.flow.FixDouble;
import br.com.pointel.jarch.flow.FixFloat;
import br.com.pointel.jarch.flow.FixInt;
import br.com.pointel.jarch.flow.FixLong;
import br.com.pointel.jarch.flow.NotFix;
import br.com.pointel.jarch.flow.NotFixEnvs;
import br.com.pointel.jarch.flow.NotFixNulls;
import br.com.pointel.jarch.flow.FixObject;
import br.com.pointel.jarch.flow.FixShort;
import br.com.pointel.jarch.flow.FixVals;

public class WizLang {

    private static final Logger log = LoggerFactory.getLogger(WizLang.class);

    private WizLang() {
    }

    public static boolean deepEquals(Object valueA, Object valueB) {
        try {
            if (valueB == valueA) return true;
            if (valueA == null || valueB == null) return false;
            if (!valueA.getClass().equals(valueB.getClass())) return false;
            if (WizArray.is(valueA)) {
                final var valueAItems = WizArray.get(valueA);
                final var valueBItems = WizArray.get(valueB);
                return Objects.deepEquals(valueAItems, valueBItems);
            } else {
                final var valueAMembers = WizLang.getValuesFromMembers(valueA);
                final var valueBMembers = WizLang.getValuesFromMembers(valueB);
                return Objects.deepEquals(valueAMembers, valueBMembers);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int deepHash(Object value) {
        try {
            if (WizArray.is(value)) {
                final var valueItems = WizArray.get(value);
                return Objects.hash(valueItems);
            } else {
                final var valueMembers = WizLang.getValuesFromMembers(value);
                return Objects.hash(valueMembers);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public static Class<?> stripPrimitive(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        if (clazz.equals(boolean.class)) {
            return Boolean.class;
        } else if (clazz.equals(byte.class)) {
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

    public static boolean isChildOf(Class<?> child, Class<?> parent) {
        if (child == null || parent == null) {
            return false;
        }
        child = stripPrimitive(child);
        parent = stripPrimitive(parent);
        if (child.isArray() && parent.isArray()) {
            Class<?> childComponent = child.getComponentType();
            Class<?> parentComponent = parent.getComponentType();
            return isChildOf(childComponent, parentComponent);
        }
        return parent.isAssignableFrom(child);
    }

    public static Constructor<?> getBestConstructor(Class<?> fromClazz, Nature[] natures, String[] names) {
        if (fromClazz == null || natures == null || natures.length == 0) {
            return null;
        }
        Constructor<?> bestOne = null;
        int bestScore = 0;
        for (Constructor<?> constructor : fromClazz.getConstructors()) {
            Class<?>[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length > natures.length) {
                continue;
            }
            int score = 0;
            boolean match = true;
            for (int iP = 0; iP < paramTypes.length; iP++) {
                Class<?> paramType = paramTypes[iP];
                Nature nature = natures[iP];
                boolean found = true;
                for (int iN = 0; iN < nature.getMapTypes().length; iN++) {
                    Class<?> natureType = nature.getMapTypes()[iN];
                    if (isChildOf(natureType, paramType)) {
                        score = score + (100 - (iN * 10));
                    } else {
                        found = false;
                        break;
                    }
                }
                if (!found) {
                    match = false;
                    break;
                }
            }
            if (match && score > bestScore) {
                bestScore = score;
                bestOne = constructor;
            }
        }
        return bestOne;
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

    public static void fixNullsOnMembers(Object ofValue) throws Exception {
        for (var field : ofValue.getClass().getDeclaredFields()) {
            var mods = field.getModifiers();
            if (Modifier.isStatic(mods) || Modifier.isFinal(mods) || Modifier.isTransient(mods)) {
                continue;
            }
            var value = field.get(ofValue);
            if (value == null || value.toString().isEmpty()) {
                if (field.isAnnotationPresent(NotFix.class) 
                        || field.isAnnotationPresent(NotFixNulls.class)) {
                    continue;
                } else if (field.isAnnotationPresent(FixBool.class)) {
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

    public static void fixEnvsOnMembers(Object ofValue) throws IllegalAccessException, Exception {
        for (var field : ofValue.getClass().getDeclaredFields()) {
            var mods = field.getModifiers();
            if (Modifier.isStatic(mods) || Modifier.isFinal(mods) || Modifier.isTransient(mods)) {
                continue;
            }
            var value = field.get(ofValue);
            if (field.isAnnotationPresent(NotFix.class)
                    || field.isAnnotationPresent(NotFixEnvs.class)) {
                continue;
            } 
            if (value instanceof FixVals fixable) {
                fixable.fixEnvs();
            }
            if (value instanceof String strValue) {
                value = WizString.replaceEnvVars(strValue);
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
