package br.com.pointel.jarch.mage;

/**
 *
 * @author emuvi
 */
public class WizBase {
    
    public static final String APP_NAME = "jeemuvi";
    public static final String APP_TITLE = "Jeemuvi";
    
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Thread startDaemon(Runnable task, String name) {
        var result = new Thread(task, name);
        result.setDaemon(true);
        result.start();
        return result;
    }
    
}
