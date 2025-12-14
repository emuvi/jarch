package br.com.pointel.jarch.mage;

public class WizApp {

    private static String title = WizLang.getMainClassSimpleName();

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        WizApp.title = title;
    }

}
