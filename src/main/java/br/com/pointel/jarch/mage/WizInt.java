package br.com.pointel.jarch.mage;

public class WizInt {
    
    private WizInt() {
    }

    public static boolean is(Object value) {
        if (value == null) return false;
        return WizLang.isChildOf(value.getClass(), Integer.class)
                || WizLang.isChildOf(value.getClass(), Number.class)
                || value instanceof String;
    }

    public static Integer get(String chars) {
        if (chars == null || chars.isBlank()) {
            return null;
        }
        return Integer.parseInt(chars);
    }
}
