package br.com.pointel.jarch.mage;

import java.math.BigDecimal;
import java.math.BigInteger;

public class WizBigInteger {

    private WizBigInteger() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), BigInteger.class)
            || WizLang.isChildOf(value.getClass(), BigDecimal.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    public static BigInteger get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), BigInteger.class)) {
            return BigInteger.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), BigDecimal.class)) {
            return (BigDecimal.class.cast(value)).toBigInteger();
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return BigInteger.valueOf(Number.class.cast(value).longValue());
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? BigInteger.ONE : BigInteger.ZERO;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return new BigInteger(string);
        }
        throw new Exception("Could not convert to a BigInteger value the value of class: " + value.getClass().getName());
    }

    public static String format(BigInteger value) {
        return value == null ? "" : value.toString();
    }

}
