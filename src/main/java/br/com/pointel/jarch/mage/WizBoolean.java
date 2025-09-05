package br.com.pointel.jarch.mage;

public class WizBoolean {

    private WizBoolean() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Boolean.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    public static Boolean get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Boolean.class)) {
            return Boolean.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Number.class.cast(value).intValue() > 0;
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return Boolean.parseBoolean(string);
        }
        throw new Exception("Could not convert to an Boolean value the value of class: " + value.getClass().getName());
    }

    public static String format(Boolean value) {
        return value == null ? "" : value.toString();
    }

}
