package br.com.pointel.jarch.mage;

import java.math.BigDecimal;
import java.math.BigInteger;

public class WizBigDecimal {

    private WizBigDecimal() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), BigDecimal.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    public static BigDecimal get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), BigDecimal.class)) {
            return BigDecimal.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), BigInteger.class)) {
            return new BigDecimal(BigInteger.class.cast(value));
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return BigDecimal.valueOf(Number.class.cast(value).doubleValue());
        }
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return (Boolean.TRUE.equals(Boolean.class.cast(value))) ? BigDecimal.ONE : BigDecimal.ZERO;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return new BigDecimal(string);
        }
        throw new Exception("Could not convert to a BigDecimal value the value of class: " + value.getClass().getName());
    }

    public static String format(BigDecimal value) {
        return value == null ? "" : value.toString();
    }

}
