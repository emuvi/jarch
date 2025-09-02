package br.com.pointel.jarch.mage;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public class WizNative {

    private static final String OS_NAME_PROPERTY = "os.name";

    public static boolean isWin() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("win"));
    }

    public static boolean isLin() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0);
    }

    public static boolean isMac() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("mac"));
    }

    public static String getActiveWindowTitle() throws Exception {
        if (isWin()) {
            return getWinActiveWindowTitle();
        } else if (isLin()) {
            return getLinActiveWindowTitle();
        } else if (isMac()) {
            return getMacActiveWindowTitle();
        } else {
            throw new UnsupportedOperationException("Not supported on this OS.");
        }
    }

    private static String getWinActiveWindowTitle() {
        WinUser32 user32 = WinUser32.getInstance();
        Pointer hWnd = user32.GetForegroundWindow();
        char[] windowText = new char[1024];
        user32.GetWindowTextW(hWnd, windowText, 512);
        return Native.toString(windowText);
    }

    private static String getLinActiveWindowTitle() throws Exception {
        LinX11 x11 = LinX11.getInstance();
        LinX11.Display display = x11.XOpenDisplay(null);
        if (display == null) {
            throw new UnsupportedOperationException("Cannot open X display");
        }
        try {
            LinX11.Window window = x11.XDefaultRootWindow(display);
            LinX11.WindowByReference activeWindow = new LinX11.WindowByReference();
            x11.XGetInputFocus(display, activeWindow, new int[1]);
            if (activeWindow.getValue() == null || activeWindow.getValue().longValue() == 0) {
                return "";
            }

            String windowTitle = getWindowProperty(display, activeWindow.getValue(), "_NET_WM_NAME");
            if (windowTitle == null || windowTitle.isEmpty()) {
                windowTitle = getWindowProperty(display, activeWindow.getValue(), "WM_NAME");
            }
            return windowTitle != null ? windowTitle : "";
        } finally {
            x11.XCloseDisplay(display);
        }
    }

    private static String getWindowProperty(LinX11.Display display, LinX11.Window window, String property) {
        LinX11 x11 = LinX11.getInstance();
        LinX11.Atom propertyAtom = x11.XInternAtom(display, property, false);
        LinX11.AtomByReference actualType = new LinX11.AtomByReference();
        int[] actualFormat = new int[1];
        NativeLongByReference nitems = new NativeLongByReference();
        NativeLongByReference bytesAfter = new NativeLongByReference();
        PointerByReference prop = new PointerByReference();

        LinX11.Atom anyPropertyTypeAtom = x11.XInternAtom(display, "ANY_PROPERTY_TYPE", true);
        int status = x11.XGetWindowProperty(display, window, propertyAtom, new NativeLong(0), new NativeLong(1024),
                false, anyPropertyTypeAtom, actualType, actualFormat, nitems, bytesAfter, prop);
        if (status == LinX11.Success && prop.getValue() != null) {
            String value = prop.getValue().getString(0, "UTF-8");
            x11.XFree(prop.getValue());
            return value;
        }
        return null;
    }

    private static String getMacActiveWindowTitle() throws Exception {
        MacCoreGraphics instance = MacCoreGraphics.getInstance();
        MacCoreGraphics.CGWindowID[] windowArray = instance.CGWindowListCopyWindowInfo(MacCoreGraphics.kCGWindowListOptionOnScreenOnly | MacCoreGraphics.kCGWindowListExcludeDesktopElements, MacCoreGraphics.kCGNullWindowID);
        if (windowArray == null || windowArray.length == 0) {
            return "";
        }
        for (MacCoreGraphics.CGWindowID window : windowArray) {
            String title = instance.CGWindowTitle(window);
            if (title != null && !title.isEmpty()) {
                return title;
            }
        }
        return "";
    }

    private interface WinUser32 extends StdCallLibrary {

        static WinUser32 getInstance() {
            if (isWin()) {
                return Native.load("user32", WinUser32.class, W32APIOptions.DEFAULT_OPTIONS);
            } else {
                throw new UnsupportedOperationException("This method is only supported on Windows OS.");
            }
        }

        Pointer GetForegroundWindow();

        int GetWindowTextW(Pointer hWnd, char[] lpString, int nMaxCount);

    }

    private interface LinX11 extends X11 {
        
         static LinX11 getInstance() {
            if (isLin()) {
                return Native.load("X11", LinX11.class);
            } else {
                throw new UnsupportedOperationException("This method is only supported on Linux OS.");
            }
        }

        int Success = 0;

        int XGetInputFocus(Display display, WindowByReference focus_return, int[] revert_to_return);

        int XGetWindowProperty(Display display, Window w, Atom property, NativeLong long_offset, NativeLong long_length,
                              boolean delete, Atom req_type, AtomByReference actual_type_return, int[] actual_format_return,
                              NativeLongByReference nitems_return, NativeLongByReference bytes_after_return,
                              PointerByReference prop_return);

        Atom XInternAtom(Display display, String atom_name, boolean only_if_exists);

        int XFree(Pointer data);

        class WindowByReference extends ByReference {

            public WindowByReference() {
                super(Native.POINTER_SIZE);
            }

            public Window getValue() {
                Pointer p = getPointer().getPointer(0);
                if (p == null) return null;
                return new Window(Pointer.nativeValue(p));
            }

        }

        class AtomByReference extends ByReference {

            public AtomByReference() {
                super(Native.POINTER_SIZE);
            }

            public Atom getValue() {
                Pointer p = getPointer().getPointer(0);
                if (p == null) return null;
                return new Atom(Pointer.nativeValue(p));
            }

        }
    }

    private interface MacCoreGraphics extends com.sun.jna.Library {

        static MacCoreGraphics getInstance() {
            if (isMac()) {
                return Native.load("CoreGraphics", MacCoreGraphics.class);
            } else {
                throw new UnsupportedOperationException("This method is only supported on Mac OS.");
            }
        }

        int kCGWindowListOptionOnScreenOnly = 1;
        int kCGWindowListExcludeDesktopElements = 16;
        int kCGNullWindowID = 0;

        CGWindowID[] CGWindowListCopyWindowInfo(int option, int relativeToWindow);

        String CGWindowTitle(CGWindowID window);

        class CGWindowID extends com.sun.jna.IntegerType {
            public CGWindowID() { super(4); }
            public CGWindowID(long value) { super(4, value); }
        }

    }

}
