package br.com.pointel.jarch.mage;

import java.io.Closeable;

public class WizBase {
    
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

    public static void closeAside(Closeable closeable) {
        new Thread(() -> {
            try {
                closeable.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    } 
    
}
