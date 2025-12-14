package br.com.pointel.jarch.mage;

public class WizApp {

    private static String title = null;

    public static String getTitle() {
        if (title == null) {
            title = WizLang.getPointelMainClassSimpleName();
        }
        return title;
    }

    public static void setTitle(String title) {
        WizApp.title = title;
    }

    private static String name = null;

    public static String getName() {
        if (name == null) {
            name = getTitle().toLowerCase();
        }
        return name;
    }

    public static void setName(String name) {
        WizApp.name = name;
    }

}
