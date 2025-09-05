package br.com.pointel.jarch.mage;

public class WizCharacter {

    private WizCharacter() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Character.class)
            || WizLang.isChildOf(value.getClass(), Number.class)
            || WizLang.isChildOf(value.getClass(), String.class);
    }

    public static Character get(Object value) throws Exception {
        if (value == null) return null;
        if (WizLang.isChildOf(value.getClass(), Character.class)) {
            return Character.class.cast(value);
        }
        if (WizLang.isChildOf(value.getClass(), Number.class)) {
            return Character.class.cast(Number.class.cast(value).intValue());
        }
        if (WizLang.isChildOf(value.getClass(), String.class)) {
            var string = String.class.cast(value);
            if (string.isBlank()) return null;
            return string.charAt(0);
        }
        throw new Exception("Could not convert to a Character value the value of class: " + value.getClass().getName());
    }

    public static String format(Character value) {
        return value == null ? "" : value.toString();
    }

}
