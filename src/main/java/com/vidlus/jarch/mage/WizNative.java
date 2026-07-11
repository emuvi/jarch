package com.vidlus.jarch.mage;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

import java.awt.Desktop;
import java.io.File;
import java.net.InetAddress;
import java.net.URI;

/**
 * Native integration utility providing OS detection, process insights,
 * hardware profiling, desktop integration, and native JNA bindings.
 */
public class WizNative {

    private static final String OS_NAME_PROPERTY = "os.name";

    /**
     * Checks if the underlying operating system is Windows.
     *
     * @return true if Windows, false otherwise
     */
    public static boolean isWin() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("win"));
    }

    /**
     * Checks if the underlying operating system is a Unix/Linux variant.
     *
     * @return true if Linux/Unix/AIX, false otherwise
     */
    public static boolean isLin() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0);
    }

    /**
     * Checks if the underlying operating system is macOS.
     *
     * @return true if Mac, false otherwise
     */
    public static boolean isMac() {
        var os = System.getProperty(OS_NAME_PROPERTY).toLowerCase();
        return (os.contains("mac"));
    }

    // =========================================================================
    // SYSTEM AND PROCESS INFORMATION
    // =========================================================================

    /**
     * Retrieves the process ID (PID) of the currently running Java Virtual Machine.
     *
     * @return the process ID
     */
    public static long getPID() {
        return ProcessHandle.current().pid();
    }

    /**
     * Retrieves the raw operating system name property.
     *
     * @return the OS name
     */
    public static String getOsName() {
        return System.getProperty(OS_NAME_PROPERTY);
    }

    /**
     * Retrieves the underlying operating system version.
     *
     * @return the OS version string
     */
    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    /**
     * Retrieves the operating system architecture (e.g., amd64, x86, aarch64).
     *
     * @return the OS architecture string
     */
    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    /**
     * Retrieves the username of the account executing the application.
     *
     * @return the username
     */
    public static String getUserName() {
        return System.getProperty("user.name");
    }

    /**
     * Retrieves the home directory path of the active user.
     *
     * @return the user's home directory
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * Retrieves the current working directory from which the application was launched.
     *
     * @return the user directory path
     */
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Resolves the local network hostname of the machine.
     * Falls back to 'localhost' if resolution fails.
     *
     * @return the hostname
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "localhost";
        }
    }

    // =========================================================================
    // HARDWARE AND MEMORY
    // =========================================================================

    /**
     * Retrieves the number of logical processors available to the Java Virtual Machine.
     *
     * @return the number of available processors
     */
    public static int getAvailableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Retrieves the amount of free memory in the Java Virtual Machine.
     *
     * @return the free memory in bytes
     */
    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * Retrieves the total amount of memory currently allocated to the Java Virtual Machine.
     *
     * @return the total memory in bytes
     */
    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * Retrieves the maximum amount of memory that the Java Virtual Machine will attempt to use.
     *
     * @return the max memory in bytes
     */
    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    /**
     * Calculates the amount of memory currently being used by the JVM.
     *
     * @return the used memory in bytes
     */
    public static long getUsedMemory() {
        return getTotalMemory() - getFreeMemory();
    }

    // =========================================================================
    // DESKTOP INTEGRATION
    // =========================================================================

    /**
     * Requests the native operating system to open a URL in the default web browser.
     * Fails silently if desktop integration is unsupported.
     *
     * @param url the URL to open
     */
    public static void openUrl(String url) {
        if (url == null || url.isBlank()) return;
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            // Silent fallback
        }
    }

    /**
     * Requests the native operating system to open a file using its default associated application.
     * Fails silently if desktop integration is unsupported.
     *
     * @param file the file to open
     */
    public static void openFile(File file) {
        if (file == null || !file.exists()) return;
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop.getDesktop().open(file);
            }
        } catch (Exception e) {
            // Silent fallback
        }
    }

    // =========================================================================
    // JNA / ACTIVE WINDOW (NATIVE)
    // =========================================================================

    /**
     * Retrieves the title of the currently focused/active window on the operating system.
     * Leverages native JNA calls tailored for Windows, Linux, or macOS.
     *
     * @return the title of the active window, or an empty string if unresolvable
     * @throws Exception if native library loading or interrogation fails
     */
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

    /**
     * Fetches the active window title natively on Windows using user32.dll.
     *
     * @return the window title
     */
    private static String getWinActiveWindowTitle() {
        WinUser32 user32 = WinUser32.getInstance();
        Pointer hWnd = user32.GetForegroundWindow();
        char[] windowText = new char[1024];
        user32.GetWindowTextW(hWnd, windowText, 512);
        return Native.toString(windowText);
    }

    /**
     * Fetches the active window title natively on Linux/Unix using X11 bindings.
     *
     * @return the window title
     * @throws Exception if X11 communication fails
     */
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

    /**
     * Helper to read specific window properties from an X11 Atom.
     *
     * @param display the X11 display connection
     * @param window the X11 window
     * @param property the property key to query
     * @return the string value of the property, or null if missing
     */
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

    /**
     * Fetches the active window title natively on macOS using CoreGraphics.
     *
     * @return the window title
     * @throws Exception if CoreGraphics interrogation fails
     */
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

    /**
     * JNA bindings for Windows user32.dll API.
     */
    private interface WinUser32 extends StdCallLibrary {

        /**
         * Loads and returns the User32 instance safely.
         */
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

    /**
     * JNA bindings for Linux/Unix libX11.so API.
     */
    private interface LinX11 extends X11 {
        
         /**
          * Loads and returns the X11 instance safely.
          */
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

        /**
         * JNA by-reference pointer wrapper for X11 Window.
         */
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

        /**
         * JNA by-reference pointer wrapper for X11 Atom.
         */
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

    /**
     * JNA bindings for macOS CoreGraphics framework API.
     */
    private interface MacCoreGraphics extends com.sun.jna.Library {

        /**
         * Loads and returns the CoreGraphics instance safely.
         */
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

        /**
         * JNA specific wrapper for macOS Window ID tracking.
         */
        class CGWindowID extends com.sun.jna.IntegerType {
            public CGWindowID() { super(4); }
            public CGWindowID(long value) { super(4, value); }
        }

    }

}
