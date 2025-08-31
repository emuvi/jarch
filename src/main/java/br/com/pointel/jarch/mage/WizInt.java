package br.com.pointel.jarch.mage;

public class WizInt {
    
    private WizInt() {
    }

    public static Integer fromChars(String chars) {
        if (chars == null || chars.isBlank()) {
            return null;
        }
        return Integer.parseInt(chars);
    }
}
