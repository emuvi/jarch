package br.com.pointel.jarch.mage;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

/**
 *
 * @author emuvi
 */
public class WizNative {
    
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        Pointer GetForegroundWindow();
        int GetWindowTextW(Pointer hWnd, char[] lpString, int nMaxCount);
    }
    
    public static String getWindowActiveTitle() {
        Pointer hWnd = User32.INSTANCE.GetForegroundWindow();
        char[] windowText = new char[1024];
        User32.INSTANCE.GetWindowTextW(hWnd, windowText, 512);
        return Native.toString(windowText);
    }
    
}
