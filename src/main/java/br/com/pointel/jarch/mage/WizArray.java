package br.com.pointel.jarch.mage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import br.com.pointel.jarch.data.Pair;

public class WizArray {
    
    private WizArray() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return value.getClass().isArray()
            || WizLang.isChildOf(value.getClass(), List.class)
            || WizLang.isChildOf(value.getClass(), Set.class)
            || WizLang.isChildOf(value.getClass(), Map.class);
    }

    public static Object[] get(Object value) {
        if (value == null) return null;
        if (value.getClass().isArray()) {
            return (Object[]) value;
        }
        if (WizLang.isChildOf(value.getClass(), List.class)) {
            return List.class.cast(value).toArray();
        }
        if (WizLang.isChildOf(value.getClass(), Set.class)) {
            return Set.class.cast(value).toArray();
        }
        if (WizLang.isChildOf(value.getClass(), Map.class)) {
            Map<?, ?> map = Map.class.cast(value);
            Object[] result = new Object[map.size()];
            int i = 0;
            for (var entry : map.entrySet()) {
                result[i++] = new Pair<>(entry.getKey(), entry.getValue());
            }
            return result;
        }
        throw new IllegalArgumentException("Could not convert to Object[] the value of class: " + value.getClass().getName());
    }

    public static <T> boolean has(T value, T... onArray) {
        if (onArray != null) {
            for (Object daMatriz : onArray) {
                if (Objects.equals(value, daMatriz)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> boolean has(T[] values, T[] onArray) {
        if (values == null || onArray == null) {
            return false;
        }
        var founds = new boolean[values.length];
        for (Object item : onArray) {
            for (int i = 0; i < values.length; i++) {
                if (Objects.equals(values[i], item)) {
                    founds[i] = true;
                }
            }
        }
        for (var found : founds) {
            if (!found) {
                return false;
            }
        }
        return true; 
    }

    public static <T> boolean hasAny(T[] values, T[] onArray) {
        if (values == null || onArray == null) {
            return false;
        }
        for (Object item : onArray) {
            for (int i = 0; i < values.length; i++) {
                if (Objects.equals(values[i], item)) {
                    return true;
                }
            }
        }
        return false; 
    }

    public static boolean has(boolean value, boolean... onArray) {
        if (onArray != null) {
            for (boolean daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean has(byte value, byte... onArray) {
        if (onArray != null) {
            for (byte daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean has(short value, short... onArray) {
        if (onArray != null) {
            for (short daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean has(char value, char... onArray) {
        if (onArray != null) {
            for (char daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean has(int value, int... onArray) {
        if (onArray != null) {
            for (int daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean has(long value, long... onArray) {
        if (onArray != null) {
            for (long daMatriz : onArray) {
                if (daMatriz == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean has(float value, float... onArray) {
        if (onArray != null) {
            for (float daMatriz : onArray) {
                if (Float.compare(daMatriz, value) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean has(double value, double... onArray) {
        if (onArray != null) {
            for (double daMatriz : onArray) {
                if (Double.compare(daMatriz, value) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("all")
    public static <T> T[] insert(int index, T value, T... onArray) {
        if (onArray == null) {
            return null;
        }
        var result = Arrays.copyOf(onArray, Math.max(onArray.length + 1, index + 1));
        result[index] = value;
        for (var i = index; i < onArray.length; i++) {
            result[i + 1] = onArray[i];
        }
        return result;
    }

    @SuppressWarnings("all")
    public static <T> T[] getNotNull(T... elements) {
        if (elements == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (T element : elements) {
            if (element != null) {
                list.add(element);
            }
        }
        var result = Arrays.copyOf(elements, list.size());
        for (var i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    @SuppressWarnings("all")
    public static <T> T[] make(Class<T> clazz, T value, int size) {
        var result = (T[]) Array.newInstance(clazz, size);
        Arrays.fill(result, value);
        return result;
    }
}
