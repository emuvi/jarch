package com.vidlus.jarch.mage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.vidlus.jarch.desk.SwingFramer;
import com.vidlus.jarch.desk.SwingNotify;

/**
 * A comprehensive utility class for interacting with Java Swing and AWT GUI components.
 * <p>
 * This class provides helper methods for window management, clipboards, menus, 
 * look-and-feel configuration, dialog popups, font handling, and standard 
 * Swing operations to significantly reduce boilerplate code.
 * </p>
 */
public class WizGUI {

    private static final Logger logger = LoggerFactory.getLogger(WizGUI.class);

    private static Image logo = null;
    private static boolean started = false;

    static {
        try {
            logo = ImageIO.read(WizGUI.class.getResourceAsStream("/gui/logo.png"));
        } catch (Exception e) {
            logo = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        }
    }

    /**
     * Gets the application's logo image.
     *
     * @return the {@link Image} representation of the logo, or an empty buffer if not found
     */
    public static Image getLogo() {
        return logo;
    }

    private static final String KEY_FONT_NAME = "WIZGUI_FONT_NAME";

    /**
     * Gets the globally configured font name for the application.
     *
     * @return the font name, defaulting to {@link Font#MONOSPACED}
     */
    public static String getFontName() {
        return WizProps.get(KEY_FONT_NAME, Font.MONOSPACED);
    }

    /**
     * Sets the globally configured font name for the application.
     *
     * @param name the new font name
     */
    public static void setFontName(String name) {
        WizProps.set(KEY_FONT_NAME, name);
    }

    private static final String KEY_FONT_SIZE = "WIZGUI_FONT_SIZE";

    /**
     * Gets the globally configured font size for the application.
     *
     * @return the font size, defaulting to 12
     */
    public static int getFontSize() {
        return WizProps.get(KEY_FONT_SIZE, 12);
    }

    /**
     * Sets the globally configured font size for the application.
     *
     * @param size the new font size
     */
    public static void setFontSize(int size) {
        WizProps.set(KEY_FONT_SIZE, size);
    }

    private static Font FONT = new Font(getFontName(), Font.PLAIN, getFontSize());

    /**
     * Gets the global base {@link Font} used by the application interface.
     *
     * @return the application font
     */
    public static Font getFont() {
        return FONT;
    }

    private static final String KEY_LOOK_AND_FEEL = "WIZGUI_LOOK_AND_FEEL";

    private static final String KEY_LOOK_AND_FEEL_SYSTEM = "System";
    private static final String KEY_LOOK_AND_FEEL_LIGHT = "Light";
    private static final String KEY_LOOK_AND_FEEL_DARK = "Dark";
    private static final String KEY_LOOK_AND_FEEL_DARCULA = "Darcula";

    private static final String[] KEY_LOOK_AND_FEEL_OPTIONS = new String[] {
            KEY_LOOK_AND_FEEL_SYSTEM,
            KEY_LOOK_AND_FEEL_LIGHT,
            KEY_LOOK_AND_FEEL_DARK,
            KEY_LOOK_AND_FEEL_DARCULA
    };

    /**
     * Gets the available Look and Feel theme options.
     *
     * @return an array of available theme names
     */
    public static String[] getLookAndFeelOptions() {
        return KEY_LOOK_AND_FEEL_OPTIONS;
    }

    /**
     * Gets the current Look and Feel theme setting.
     *
     * @return the theme name, defaulting to system
     */
    public static String getLookAndFeel() {
        return WizProps.get(KEY_LOOK_AND_FEEL, KEY_LOOK_AND_FEEL_SYSTEM);
    }

    /**
     * Sets the application Look and Feel theme setting.
     *
     * @param option the theme name to apply
     */
    public static void setLookAndFeel(String option) {
        WizProps.set(KEY_LOOK_AND_FEEL, option);
    }

    /**
     * Initializes the desktop application with the default title.
     */
    public static void start() {
        start(WizLang.getTitle(), null);
    }

    /**
     * Initializes the desktop application with a specific title.
     *
     * @param title the application window title
     */
    public static void start(String title) {
        start(title, null);
    }

    /**
     * Initializes the desktop application and runs a callback upon success.
     *
     * @param afterStart the {@link Runnable} to execute after initialization
     */
    public static void start(Runnable afterStart) {
        start(WizLang.getTitle(), afterStart);
    }

    /**
     * Initializes the desktop application with a title and runs a callback upon success.
     * <p>
     * Applies the configured Look and Feel on the AWT Event Dispatch Thread.
     * </p>
     *
     * @param title      the application window title
     * @param afterStart the {@link Runnable} to execute after initialization
     */
    public static void start(String title, Runnable afterStart) {
        logger.info("Starting desk of {} application", WizLang.getName());
        EventQueue.invokeLater(() -> {
            try {
                switch (getLookAndFeel()) {
                    case KEY_LOOK_AND_FEEL_LIGHT -> UIManager.setLookAndFeel(new FlatLightLaf());
                    case KEY_LOOK_AND_FEEL_DARK -> UIManager.setLookAndFeel(new FlatDarkLaf());
                    case KEY_LOOK_AND_FEEL_DARCULA -> UIManager.setLookAndFeel(new FlatDarculaLaf());
                    default -> UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                started = true;
                if (afterStart != null) {
                    afterStart.run();
                }
            } catch (Exception e) {
                logger.error("Could not start desktop application: " + title, e);
            }
        });
    }

    /**
     * Checks if the GUI has been fully initialized and started.
     *
     * @return {@code true} if the GUI is active, {@code false} otherwise
     */
    public static boolean isStarted() {
        return started;
    }

    /**
     * Finds the currently active {@link Window}.
     *
     * @return the active {@link Window}, or {@code null} if none is found
     */
    public static Window getActiveWindow() {
        for (Window window : Window.getWindows()) {
            if (window.isActive()) {
                return window;
            }
        }
        return null;
    }

    /**
     * Positions a new window with a slight offset relative to the active window.
     * <p>
     * If no active window exists, the position is randomized. Ensures the window 
     * remains fully visible on the screen.
     * </p>
     *
     * @param window the {@link Window} to position
     */
    public static void setNextLocationFor(Window window) {
        Point result = null;
        var active = WizGUI.getActiveWindow();
        if (active != null) {
            result = new Point(active.getX() + 45, active.getY() + 45);
        }
        var screen = Toolkit.getDefaultToolkit().getScreenSize();
        if (result == null) {
            result = new Point(WizRand.getInt(screen.width - window.getWidth()),
                    WizRand.getInt(screen.height - window.getHeight()));
        } else {
            if (result.x + window.getWidth() > screen.width) {
                result.x = screen.width - window.getWidth();
            }
            if (result.y + window.getHeight() > screen.height) {
                result.y = screen.height - window.getHeight();
            }
        }
        window.setLocation(result);
    }

    /**
     * Equalizes the preferred and minimum widths of the given components 
     * based on the largest component's width.
     *
     * @param ofComponents the components to resize
     */
    public static void setWidthMinAsPreferredMax(JComponent... ofComponents) {
        var maxValue = 0;
        for (JComponent component : ofComponents) {
            if (component.getPreferredSize().width > maxValue) {
                maxValue = component.getPreferredSize().width;
            }
        }
        for (JComponent component : ofComponents) {
            var dimension = new Dimension(maxValue, component.getPreferredSize().height);
            component.setMinimumSize(dimension);
            component.setPreferredSize(dimension);
        }
    }

    /**
     * Registers a keyboard shortcut on a component.
     *
     * @param component the target {@link JComponent}
     * @param name      the action identifier
     * @param keyStroke the shortcut keystroke (e.g., "ctrl C")
     * @param runnable  the callback to execute
     */
    public static void putShortCut(JComponent component, String name, String keyStroke,
            Runnable runnable) {
        var inputMap = component.getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        var actionMap = component.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(keyStroke), name);
        actionMap.put(name, WizGUI.getAction(runnable));
    }

    /**
     * Creates an {@link Action} that executes a {@link Runnable}.
     *
     * @param runnable the runnable to wrap
     * @return a new {@link Action} instance
     */
    public static Action getAction(Runnable runnable) {
        return new AbstractAction() {
            private static final long serialVersionUID = -1482117853128881492L;

            @Override
            public void actionPerformed(ActionEvent e) {
                runnable.run();
            }
        };
    }

    /**
     * Reads a string from the system clipboard.
     *
     * @return the clipboard string contents
     * @throws Exception if reading the clipboard fails
     */
    public static String getStringFromClipboard() throws Exception {
        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(
                DataFlavor.stringFlavor);
    }

    /**
     * Writes a string to the system clipboard.
     *
     * @param theString the string to copy
     */
    public static void copyToClipboard(String theString) {
        var selection = new StringSelection(theString);
        var clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    /**
     * Reads an image from the system clipboard.
     *
     * @return the clipboard image as a {@link BufferedImage}, or {@code null} if none exists
     * @throws Exception if reading the clipboard fails
     */
    public static BufferedImage getImageFromClipboard() throws Exception {
        var transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            var pasted = (BufferedImage) transferable.getTransferData(DataFlavor.imageFlavor);
            return WizGUI.convertToRGB(pasted);
        }
        return null;
    }

    /**
     * Converts an image to the standard RGB color space.
     *
     * @param image the image to convert
     * @return a new {@link BufferedImage} in RGB format
     */
    public static BufferedImage convertToRGB(BufferedImage image) {
        var result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        return result;
    }

    public static Pattern deskMnemonic = Pattern.compile("\\s\\s\\s\\[\\s.\\s\\]$");

    /**
     * Removes the mnemonic suffix pattern (e.g., "   [ x ]") from a window title.
     *
     * @param fromTitle the title string
     * @return the cleaned title string
     */
    public static String delMnemonic(String fromTitle) {
        if (fromTitle == null) {
            return fromTitle;
        }
        if (WizGUI.deskMnemonic.matcher(fromTitle).find()) {
            return fromTitle.substring(0, fromTitle.length() - 8);
        }
        return fromTitle;
    }

    /**
     * Executes multiple {@link ActionListener}s sequentially with a null event.
     *
     * @param actions the array of actions to execute
     */
    public static void execute(ActionListener[] actions) {
        execute(actions, null);
    }

    /**
     * Executes multiple {@link ActionListener}s sequentially with a specific event.
     *
     * @param actions the array of actions to execute
     * @param event   the triggering {@link ActionEvent}
     */
    public static void execute(ActionListener[] actions, ActionEvent event) {
        if (actions != null) {
            for (ActionListener act : actions) {
                act.actionPerformed(event);
            }
        }
    }

    /**
     * Executes a runnable immediately if on the Event Dispatch Thread, or queues it via invokeLater.
     *
     * @param runnable the logic to execute safely
     */
    public static void callOrInvoke(Runnable runnable) {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeLater(runnable);
        }
    }

    /**
     * Executes a runnable immediately if on the Event Dispatch Thread, or queues it via invokeAndWait.
     *
     * @param runnable the logic to execute safely
     * @throws Exception if execution fails or is interrupted
     */
    public static void callOrWait(Runnable runnable) throws Exception {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeAndWait(runnable);
        }
    }

    /**
     * Displays a non-blocking toast notification for 3 seconds.
     *
     * @param message the text to display
     */
    public static void showNotify(String message) {
        showNotify(message, 3);
    }

    /**
     * Displays a non-blocking toast notification for a specified duration.
     *
     * @param message the text to display
     * @param seconds the duration in seconds
     */
    public static void showNotify(String message, double seconds) {
        SwingNotify.show(message, seconds);
    }

    /**
     * Displays an informational blocking dialog box.
     *
     * @param message the information message
     */
    public static void showInfo(String message) {
        logger.info(message);
        Runnable runner = () -> {
            JOptionPane.showMessageDialog(WizGUI.getActiveWindow(), message,
                    WizLang.getTitle(), JOptionPane.INFORMATION_MESSAGE);
        };
        if (SwingUtilities.isEventDispatchThread()) {
            runner.run();
        } else {
            SwingUtilities.invokeLater(runner);
        }
    }

    /**
     * Displays an error blocking dialog box based on an exception.
     *
     * @param error the exception to report
     */
    public static void showError(Throwable error) {
        showError(error, null);
    }

    /**
     * Displays an error blocking dialog box based on an exception and custom detail string.
     *
     * @param error  the exception to report
     * @param detail additional context string
     */
    public static void showError(Throwable error, String detail) {
        String message = error.getMessage() + (detail != null ? " " + detail : "");
        logger.error(message, error);
        Runnable runner = () -> {
            JOptionPane.showMessageDialog(WizGUI.getActiveWindow(), message,
                    WizLang.getTitle(), JOptionPane.ERROR_MESSAGE);
        };
        if (SwingUtilities.isEventDispatchThread()) {
            runner.run();
        } else {
            SwingUtilities.invokeLater(runner);
        }
    }

    /**
     * Displays a Yes/No confirmation dialog.
     *
     * @param message the question to ask
     * @return {@code true} if YES is clicked, {@code false} otherwise
     */
    public static boolean showConfirm(String message) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
                WizGUI.getActiveWindow(), message, "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Displays an input prompt dialog.
     *
     * @param message the input prompt message
     * @return the user input, or {@code null} if cancelled
     */
    public static String showInput(String message) {
        return JOptionPane.showInputDialog(
                WizGUI.getActiveWindow(), message, "Input",
                JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Displays an input prompt dialog with a default pre-filled value.
     *
     * @param question the input prompt message
     * @param value    the default value
     * @return the user input, or {@code null} if cancelled
     */
    public static String showInput(String question, String value) {
        return (String) JOptionPane.showInputDialog(WizGUI.getActiveWindow(), question,
                WizLang.getTitle(), JOptionPane.QUESTION_MESSAGE, null, null, value);
    }

    /**
     * Attempts to extract a string representation from the system clipboard.
     * <p>
     * If the clipboard contains an image, this returns the placeholder {@code "<IMAGE>"}.
     * </p>
     *
     * @return the clipboard contents as a {@link String}
     * @throws Exception if access to the clipboard fails
     */
    public static String getStringOnClipboard() throws Exception {
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipboardContents = systemClipboard.getContents(null);
        if (clipboardContents != null) {
            try {
                if (clipboardContents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    return (String) clipboardContents.getTransferData(DataFlavor.stringFlavor);
                } else if (clipboardContents.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                    return "<IMAGE>";
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Writes a string to the system clipboard.
     *
     * @param string the content to place on the clipboard
     */
    public static void putStringOnClipboard(String string) {
        StringSelection selection = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    /**
     * Opens a file or directory using the OS native desktop handler.
     *
     * @param file the file to open
     * @throws Exception if opening fails
     */
    public static void open(File file) throws Exception {
        Desktop.getDesktop().open(file);
    }

    /**
     * Opens a web URI using the OS native browser.
     *
     * @param address the web address
     * @throws Exception if navigation fails
     */
    public static void navigate(String address) throws Exception {
        Desktop.getDesktop().browse(new URI(address));
    }

    /**
     * Opens the OS file explorer and selects the specified file.
     *
     * @param filePath the file to select
     * @throws Exception if the OS command fails
     */
    public static void exploreAndSelect(File filePath) throws Exception {
        if (filePath == null) {
            throw new Exception("File path cannot be null");
        }
        if (!filePath.exists()) {
            throw new Exception("File does not exist: " + filePath);
        }
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            Runtime.getRuntime().exec(new String[] { "explorer.exe", "/select,", filePath.getAbsolutePath() });
        } else if (os.contains("mac")) {
            Runtime.getRuntime().exec(new String[] { "open", "-R", filePath.getAbsolutePath() });
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            File parentDir = filePath.getParentFile();
            if (parentDir != null && parentDir.isDirectory()) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                    Desktop.getDesktop().open(parentDir);
                } else {
                    Runtime.getRuntime().exec(new String[] { "xdg-open", parentDir.getAbsolutePath() });
                }
            } else {
                throw new Exception("Could not determine parent directory for: " + filePath);
            }
        } else {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                File parentDir = filePath.getParentFile();
                if (parentDir != null && parentDir.isDirectory()) {
                    Desktop.getDesktop().open(parentDir);
                } else {
                    throw new Exception("Could not determine parent directory for: " + filePath);
                }
            } else {
                throw new Exception("No desktop support on the operating system: " + os);
            }
        }
    }

    /**
     * Creates a Monospaced font at size 12.
     *
     * @return the new {@link Font}
     */
    public static Font fontMonospaced() {
        return fontMonospaced(12);
    }

    /**
     * Creates a Monospaced font at the specified size.
     *
     * @param size the font size
     * @return the new {@link Font}
     */
    public static Font fontMonospaced(int size) {
        return new Font(Font.MONOSPACED, Font.PLAIN, size);
    }

    /**
     * Creates a Serif font at size 12.
     *
     * @return the new {@link Font}
     */
    public static Font fontSerif() {
        return fontSerif(12);
    }

    /**
     * Creates a Serif font at the specified size.
     *
     * @param size the font size
     * @return the new {@link Font}
     */
    public static Font fontSerif(int size) {
        return new Font(Font.SERIF, Font.PLAIN, size);
    }

    /**
     * Creates a SansSerif font at size 12.
     *
     * @return the new {@link Font}
     */
    public static Font fontSansSerif() {
        return fontSansSerif(12);
    }

    /**
     * Creates a SansSerif font at the specified size.
     *
     * @param size the font size
     * @return the new {@link Font}
     */
    public static Font fontSansSerif(int size) {
        return new Font(Font.SANS_SERIF, Font.PLAIN, size);
    }

    /**
     * Recursively applies a font to a component and all its children.
     *
     * @param component the root component
     * @param font      the font to apply
     */
    public static void setAllComponentsFont(Component component, Font font) {
        if (font == null) {
            return;
        }
        component.setFont(font);
        if (component instanceof Container container) {
            for (Component inside : container.getComponents()) {
                setAllComponentsFont(inside, font);
            }
        }
    }

    /**
     * Recursively searches for components that are instances of specific classes.
     *
     * @param <T>   the generic type of the class filter
     * @param root  the component to start searching from
     * @param clazz the classes to match
     * @return a {@link List} of matching components
     */
    public static <T extends Class<? extends Component>> List<Component> getAllComponentsOf(Component root,
            T... clazz) {
        var results = new ArrayList<Component>();
        getAllComponentsOf(results, root, clazz);
        return results;
    }

    /**
     * Recursively searches for components and adds matches to the provided list.
     *
     * @param <T>     the generic type of the class filter
     * @param results the list to populate
     * @param root    the component to start searching from
     * @param clazz   the classes to match
     */
    public static <T extends Class<? extends Component>> void getAllComponentsOf(List<Component> results,
            Component root, T... clazz) {
        for (var kind : clazz) {
            if (kind.isInstance(root)) {
                results.add(root);
            }
        }
        if (root instanceof Container container) {
            for (Component inside : container.getComponents()) {
                getAllComponentsOf(results, inside, clazz);
            }
        }
    }

    /**
     * Recursively nullifies the name properties of a component and all its children.
     *
     * @param root the root component
     */
    public static void cleanAllNames(Component root) {
        root.setName(null);
        if (root instanceof Container container) {
            for (Component inside : container.getComponents()) {
                cleanAllNames(inside);
            }
        }
    }

    /**
     * Initializes a frame wrapper utility using the global font.
     *
     * @param frame the {@link JFrame} to wrap
     * @return the {@link SwingFramer} initialized instance
     */
    public static SwingFramer initFrame(JFrame frame) {
        return initFrame(frame, getFont());
    }

    /**
     * Initializes a frame wrapper utility with a specific font.
     *
     * @param frame the {@link JFrame} to wrap
     * @param font  the base font
     * @return the {@link SwingFramer} initialized instance
     */
    public static SwingFramer initFrame(JFrame frame, Font font) {
        return new SwingFramer(frame, font).init();
    }

    /**
     * Configures a frame to close automatically when the ESCAPE key is pressed.
     *
     * @param frame the {@link JFrame} to configure
     */
    public static void initEscaper(JFrame frame) {
        String ESCAPER_KEY = "FramEscaperAction";
        frame.getRootPane().getActionMap().put(ESCAPER_KEY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close(frame);
            }
        });
        frame.getRootPane().getInputMap().put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                ESCAPER_KEY);
    }

    /**
     * Closes a frame programmatically by dispatching a closing event.
     *
     * @param frame the {@link JFrame} to close
     */
    public static void close(JFrame frame) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.setVisible(false);
        frame.dispose();
    }

    /**
     * Closes all active application frames.
     */
    public static void closeAll() {
        closeAll(false);
        closeAll(true);
    }

    /**
     * Closes all active application frames, conditionally filtering by their close operation.
     *
     * @param closeExitOnClose {@code true} to close frames marked as EXIT_ON_CLOSE, {@code false} to spare them
     */
    public static void closeAll(boolean closeExitOnClose) {
        for (var frame : JFrame.getFrames()) {
            if (frame instanceof JFrame jFrame) {
                if (jFrame.isVisible()) {
                    if ((closeExitOnClose && jFrame.getDefaultCloseOperation() == JFrame.EXIT_ON_CLOSE)
                            || (!closeExitOnClose && jFrame
                                    .getDefaultCloseOperation() != JFrame.EXIT_ON_CLOSE)) {
                        close(jFrame);
                    }
                }
            }
        }
    }

    /**
     * Adds a button to a container.
     *
     * @param component the container {@link JComponent}
     * @param button    the button to add
     */
    public static void addButton(JComponent component, AbstractButton button) {
        addButton(component, button, null);
    }

    /**
     * Adds a button to a container and optionally binds an action listener.
     *
     * @param component the container {@link JComponent}
     * @param button    the button to add
     * @param action    the action listener to attach
     */
    public static void addButton(JComponent component, AbstractButton button, ActionListener action) {
        if (action != null) {
            button.addActionListener(action);
        }
        component.add(button);
    }

    /**
     * Adds a default action (triggered via double-click or ENTER) to a component.
     *
     * @param component the target component
     * @param action    the listener to fire
     */
    public static void addDefaultAction(JComponent component, ActionListener action) {
        addDefaultAction(component, null, action);
    }

    /**
     * Adds a default action (triggered via double-click or ENTER) with a command payload.
     *
     * @param component the target component
     * @param command   the command string payload
     * @param action    the listener to fire
     */
    public static void addDefaultAction(JComponent component, String command, ActionListener action) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    action.actionPerformed(new ActionEvent(component, (int) ActionEvent.MOUSE_EVENT_MASK, command));
                }
            }
        });
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    action.actionPerformed(new ActionEvent(component, (int) ActionEvent.KEY_EVENT_MASK, command));
                }
            }
        });
    }

    /**
     * Debounces a component by temporarily disabling it for 1 second.
     *
     * @param component the component to disable
     */
    public static void debounceEnable(JComponent component) {
        debounceEnable(1000, component);
    }

    /**
     * Debounces a component by temporarily disabling it for a specified time.
     *
     * @param millis    duration in milliseconds to disable
     * @param component the component to disable
     */
    public static void debounceEnable(int millis, JComponent component) {
        component.setEnabled(false);
        debounceAction(millis, () -> component.setEnabled(true));
    }

    /**
     * Defers execution of an action by a set delay using a background thread.
     *
     * @param millis duration in milliseconds to wait
     * @param action the {@link Runnable} to execute on the GUI thread afterwards
     */
    public static void debounceAction(int millis, Runnable action) {
        new Thread("Trigger Debounce") {
            @Override
            public void run() {
                WizThread.sleep(millis);
                SwingUtilities.invokeLater(action);
            }
        }.start();
    }

    /**
     * Gets the current absolute screen coordinates of the mouse cursor.
     *
     * @return the {@link Point} coordinate
     */
    public static Point getMouseCurrentPoint() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    /**
     * Identifies which screen device currently contains the mouse cursor.
     *
     * @return the {@link GraphicsDevice} holding the cursor
     */
    public static GraphicsDevice getScreenWithMouse() {
        var mousePoint = getMouseCurrentPoint();
        for (var screen : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            if (screen.getDefaultConfiguration().getBounds().contains(mousePoint)) {
                return screen;
            }
        }
        return null;
    }

    /**
     * Adjusts a rectangle so that it fits entirely within the boundaries of the screen it originates on.
     *
     * @param bounds the initial rectangle to adjust
     * @return the adjusted rectangle guaranteed to fit on screen
     */
    public static Rectangle getBoundsInsideScreen(Rectangle bounds) {
        GraphicsDevice insideScreen = null;
        var screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (var screen : screens) {
            var screenBounds = screen.getDefaultConfiguration().getBounds();
            if (screenBounds.contains(bounds.getLocation())) {
                insideScreen = screen;
                break;
            }
        }
        if (insideScreen == null) {
            insideScreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        }
        var screenBounds = insideScreen.getDefaultConfiguration().getBounds();
        if (bounds.x + bounds.width > screenBounds.x + screenBounds.width) {
            bounds.x = screenBounds.x + screenBounds.width - bounds.width;
        }
        if (bounds.x < screenBounds.x) {
            bounds.x = screenBounds.x;
        }
        if (bounds.y + bounds.height > screenBounds.y + screenBounds.height) {
            bounds.y = screenBounds.y + screenBounds.height - bounds.height;
        }
        if (bounds.y < screenBounds.y) {
            bounds.y = screenBounds.y;
        }
        return bounds;
    }
    /**
     * Attaches a JPopupMenu to a JComponent, triggering on right-click or
     * Ctrl+Space.
     *
     * @param onComponent The component to attach the popup to.
     * @param popup       The popup menu to display.
     */
    public static void putPopup(JComponent onComponent, JPopupMenu popup) {
        onComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) {
                    popup.show(onComponent, e.getX(), e.getY());
                }
            }
        });
        onComponent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    popup.show(onComponent, 0, 0);
                }
            }
        });
    }

    /**
     * Attaches a JPopupMenu to a JButton, triggering on action performed.
     * The popup is shown directly under the button.
     *
     * @param onButton The button to attach the popup to.
     * @param popup    The popup menu to display.
     */
    public static void putPopup(JButton onButton, JPopupMenu popup) {
        onButton.addActionListener(e -> popup.show(onButton, 0, onButton.getHeight()));
    }

    /**
     * Adds a separator to the popup menu.
     *
     * @param onPopup The popup menu.
     */
    public static void putSeparator(JPopupMenu onPopup) {
        onPopup.addSeparator();
    }

    /**
     * Adds a menu item to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the menu item.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text) {
        return putMenuItem(onPopup, text, null, null, null, null);
    }

    /**
     * Adds a menu item with an action listener to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the menu item.
     * @param action  The ActionListener to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, ActionListener action) {
        return putMenuItem(onPopup, text, null, null, null, action);
    }

    /**
     * Adds a menu item with an icon and action listener to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the menu item.
     * @param icon    The icon for the menu item.
     * @param action  The ActionListener to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, Icon icon, ActionListener action) {
        return putMenuItem(onPopup, text, icon, null, null, action);
    }

    /**
     * Adds a menu item with a keyboard accelerator and action listener to the popup
     * menu.
     *
     * @param onPopup     The popup menu.
     * @param text        The text of the menu item.
     * @param accelerator The KeyStroke accelerator.
     * @param action      The ActionListener to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, KeyStroke accelerator, ActionListener action) {
        return putMenuItem(onPopup, text, null, accelerator, null, action);
    }

    /**
     * Adds a menu item with a tooltip and action listener to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the menu item.
     * @param tooltip The tooltip text.
     * @param action  The ActionListener to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, String tooltip, ActionListener action) {
        return putMenuItem(onPopup, text, null, null, tooltip, action);
    }

    /**
     * Adds a menu item with an icon, tooltip, and action listener to the popup
     * menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the menu item.
     * @param icon    The icon for the menu item.
     * @param tooltip The tooltip text.
     * @param action  The ActionListener to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, Icon icon, String tooltip,
            ActionListener action) {
        return putMenuItem(onPopup, text, icon, null, tooltip, action);
    }

    /**
     * Adds a menu item with a keyboard accelerator, tooltip, and action listener to
     * the popup menu.
     *
     * @param onPopup     The popup menu.
     * @param text        The text of the menu item.
     * @param accelerator The KeyStroke accelerator.
     * @param tooltip     The tooltip text.
     * @param action      The ActionListener to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, KeyStroke accelerator, String tooltip,
            ActionListener action) {
        return putMenuItem(onPopup, text, null, accelerator, tooltip, action);
    }

    /**
     * Adds a menu item with an icon, keyboard accelerator, and action listener to
     * the popup menu.
     *
     * @param onPopup     The popup menu.
     * @param text        The text of the menu item.
     * @param icon        The icon for the menu item.
     * @param accelerator The KeyStroke accelerator.
     * @param action      The ActionListener to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, Icon icon, KeyStroke accelerator,
            ActionListener action) {
        return putMenuItem(onPopup, text, icon, accelerator, null, action);
    }

    /**
     * Adds a menu item with an Action to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the menu item.
     * @param action  The Action to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, Action action) {
        return putMenuItem(onPopup, text, null, null, null, action);
    }

    /**
     * Adds a menu item with an icon and Action to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the menu item.
     * @param icon    The icon for the menu item.
     * @param action  The Action to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, Icon icon, Action action) {
        return putMenuItem(onPopup, text, icon, null, null, action);
    }

    /**
     * Adds a menu item with a keyboard accelerator and Action to the popup menu.
     *
     * @param onPopup     The popup menu.
     * @param text        The text of the menu item.
     * @param accelerator The KeyStroke accelerator.
     * @param action      The Action to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, KeyStroke accelerator, Action action) {
        return putMenuItem(onPopup, text, null, accelerator, null, action);
    }

    /**
     * Adds a menu item with a tooltip and Action to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the menu item.
     * @param tooltip The tooltip text.
     * @param action  The Action to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, String tooltip, Action action) {
        return putMenuItem(onPopup, text, null, null, tooltip, action);
    }

    /**
     * Adds a menu item with an icon, tooltip, and Action to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the menu item.
     * @param icon    The icon for the menu item.
     * @param tooltip The tooltip text.
     * @param action  The Action to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, Icon icon, String tooltip, Action action) {
        return putMenuItem(onPopup, text, icon, null, tooltip, action);
    }

    /**
     * Adds a menu item with a keyboard accelerator, tooltip, and Action to the
     * popup menu.
     *
     * @param onPopup     The popup menu.
     * @param text        The text of the menu item.
     * @param accelerator The KeyStroke accelerator.
     * @param tooltip     The tooltip text.
     * @param action      The Action to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, KeyStroke accelerator, String tooltip,
            Action action) {
        return putMenuItem(onPopup, text, null, accelerator, tooltip, action);
    }

    /**
     * Adds a menu item with an icon, keyboard accelerator, and Action to the popup
     * menu.
     *
     * @param onPopup     The popup menu.
     * @param text        The text of the menu item.
     * @param icon        The icon for the menu item.
     * @param accelerator The KeyStroke accelerator.
     * @param action      The Action to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, Icon icon, KeyStroke accelerator,
            Action action) {
        return putMenuItem(onPopup, text, icon, accelerator, null, action);
    }

    /**
     * Adds a fully customized menu item to the popup menu.
     *
     * @param onPopup     The popup menu.
     * @param text        The text of the menu item.
     * @param icon        The icon for the menu item.
     * @param accelerator The KeyStroke accelerator.
     * @param tooltip     The tooltip text.
     * @param action      The ActionListener to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, Icon icon, KeyStroke accelerator,
            String tooltip, ActionListener action) {
        var item = new JMenuItem(text);
        if (icon != null) {
            item.setIcon(icon);
        }
        if (accelerator != null) {
            item.setAccelerator(accelerator);
        }
        if (tooltip != null) {
            item.setToolTipText(tooltip);
        }
        if (action != null) {
            item.addActionListener(action);
        }
        onPopup.add(item);
        return item;
    }

    /**
     * Adds a fully customized menu item with an Action to the popup menu.
     *
     * @param onPopup     The popup menu.
     * @param text        The text of the menu item.
     * @param icon        The icon for the menu item.
     * @param accelerator The KeyStroke accelerator.
     * @param tooltip     The tooltip text.
     * @param action      The Action to attach.
     * @return The created JMenuItem.
     */
    public static JMenuItem putMenuItem(JPopupMenu onPopup, String text, Icon icon, KeyStroke accelerator,
            String tooltip, Action action) {
        var item = new JMenuItem();
        if (action != null) {
            item.setAction(action);
        }
        if (text != null) {
            item.setText(text);
        }
        if (icon != null) {
            item.setIcon(icon);
        }
        if (accelerator != null) {
            item.setAccelerator(accelerator);
        }
        if (tooltip != null) {
            item.setToolTipText(tooltip);
        }
        onPopup.add(item);
        return item;
    }

    /**
     * Adds a checkbox menu item to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the menu item.
     * @return The created JCheckBoxMenuItem.
     */
    public static JCheckBoxMenuItem putCheckItem(JPopupMenu onPopup, String text) {
        return putCheckItem(onPopup, text, null, false, null, null);
    }

    /**
     * Adds a checkbox menu item with selection state to the popup menu.
     *
     * @param onPopup  The popup menu.
     * @param text     The text of the menu item.
     * @param selected The initial selection state.
     * @return The created JCheckBoxMenuItem.
     */
    public static JCheckBoxMenuItem putCheckItem(JPopupMenu onPopup, String text, boolean selected) {
        return putCheckItem(onPopup, text, null, selected, null, null);
    }

    /**
     * Adds a checkbox menu item with selection state and action listener to the
     * popup menu.
     *
     * @param onPopup  The popup menu.
     * @param text     The text of the menu item.
     * @param selected The initial selection state.
     * @param action   The ActionListener to attach.
     * @return The created JCheckBoxMenuItem.
     */
    public static JCheckBoxMenuItem putCheckItem(JPopupMenu onPopup, String text, boolean selected,
            ActionListener action) {
        return putCheckItem(onPopup, text, null, selected, null, action);
    }

    /**
     * Adds a checkbox menu item with icon, selection state, and action listener to
     * the popup menu.
     *
     * @param onPopup  The popup menu.
     * @param text     The text of the menu item.
     * @param icon     The icon for the menu item.
     * @param selected The initial selection state.
     * @param action   The ActionListener to attach.
     * @return The created JCheckBoxMenuItem.
     */
    public static JCheckBoxMenuItem putCheckItem(JPopupMenu onPopup, String text, Icon icon, boolean selected,
            ActionListener action) {
        return putCheckItem(onPopup, text, icon, selected, null, action);
    }

    /**
     * Adds a checkbox menu item with tooltip, selection state, and action listener
     * to the popup menu.
     *
     * @param onPopup  The popup menu.
     * @param text     The text of the menu item.
     * @param tooltip  The tooltip text.
     * @param selected The initial selection state.
     * @param action   The ActionListener to attach.
     * @return The created JCheckBoxMenuItem.
     */
    public static JCheckBoxMenuItem putCheckItem(JPopupMenu onPopup, String text, String tooltip, boolean selected,
            ActionListener action) {
        return putCheckItem(onPopup, text, null, selected, tooltip, action);
    }

    /**
     * Adds a checkbox menu item with selection state and Action to the popup menu.
     *
     * @param onPopup  The popup menu.
     * @param text     The text of the menu item.
     * @param selected The initial selection state.
     * @param action   The Action to attach.
     * @return The created JCheckBoxMenuItem.
     */
    public static JCheckBoxMenuItem putCheckItem(JPopupMenu onPopup, String text, boolean selected, Action action) {
        return putCheckItem(onPopup, text, null, selected, null, action);
    }

    /**
     * Adds a checkbox menu item with icon, selection state, and Action to the popup
     * menu.
     *
     * @param onPopup  The popup menu.
     * @param text     The text of the menu item.
     * @param icon     The icon for the menu item.
     * @param selected The initial selection state.
     * @param action   The Action to attach.
     * @return The created JCheckBoxMenuItem.
     */
    public static JCheckBoxMenuItem putCheckItem(JPopupMenu onPopup, String text, Icon icon, boolean selected,
            Action action) {
        return putCheckItem(onPopup, text, icon, selected, null, action);
    }

    /**
     * Adds a checkbox menu item with tooltip, selection state, and Action to the
     * popup menu.
     *
     * @param onPopup  The popup menu.
     * @param text     The text of the menu item.
     * @param tooltip  The tooltip text.
     * @param selected The initial selection state.
     * @param action   The Action to attach.
     * @return The created JCheckBoxMenuItem.
     */
    public static JCheckBoxMenuItem putCheckItem(JPopupMenu onPopup, String text, String tooltip, boolean selected,
            Action action) {
        return putCheckItem(onPopup, text, null, selected, tooltip, action);
    }

    /**
     * Adds a fully customized checkbox menu item to the popup menu.
     *
     * @param onPopup  The popup menu.
     * @param text     The text of the menu item.
     * @param icon     The icon for the menu item.
     * @param selected The initial selection state.
     * @param tooltip  The tooltip text.
     * @param action   The ActionListener to attach.
     * @return The created JCheckBoxMenuItem.
     */
    public static JCheckBoxMenuItem putCheckItem(JPopupMenu onPopup, String text, Icon icon, boolean selected,
            String tooltip, ActionListener action) {
        var item = new JCheckBoxMenuItem(text, selected);
        if (icon != null) {
            item.setIcon(icon);
        }
        if (tooltip != null) {
            item.setToolTipText(tooltip);
        }
        if (action != null) {
            item.addActionListener(action);
        }
        onPopup.add(item);
        return item;
    }

    /**
     * Adds a fully customized checkbox menu item with an Action to the popup menu.
     *
     * @param onPopup  The popup menu.
     * @param text     The text of the menu item.
     * @param icon     The icon for the menu item.
     * @param selected The initial selection state.
     * @param tooltip  The tooltip text.
     * @param action   The Action to attach.
     * @return The created JCheckBoxMenuItem.
     */
    public static JCheckBoxMenuItem putCheckItem(JPopupMenu onPopup, String text, Icon icon, boolean selected,
            String tooltip, Action action) {
        var item = new JCheckBoxMenuItem();
        if (action != null) {
            item.setAction(action);
        }
        if (text != null) {
            item.setText(text);
        }
        if (icon != null) {
            item.setIcon(icon);
        }
        if (tooltip != null) {
            item.setToolTipText(tooltip);
        }
        item.setSelected(selected);
        onPopup.add(item);
        return item;
    }

    /**
     * Adds a radio button menu item to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the menu item.
     * @return The created JRadioButtonMenuItem.
     */
    public static JRadioButtonMenuItem putRadioItem(JPopupMenu onPopup, String text) {
        return putRadioItem(onPopup, null, text, null, false, null, null);
    }

    /**
     * Adds a radio button menu item to the popup menu, associated with a
     * ButtonGroup.
     *
     * @param onPopup The popup menu.
     * @param group   The ButtonGroup to add the item to.
     * @param text    The text of the menu item.
     * @return The created JRadioButtonMenuItem.
     */
    public static JRadioButtonMenuItem putRadioItem(JPopupMenu onPopup, ButtonGroup group, String text) {
        return putRadioItem(onPopup, group, text, null, false, null, null);
    }

    /**
     * Adds a radio button menu item with selection state to the popup menu,
     * associated with a ButtonGroup.
     *
     * @param onPopup  The popup menu.
     * @param group    The ButtonGroup to add the item to.
     * @param text     The text of the menu item.
     * @param selected The initial selection state.
     * @return The created JRadioButtonMenuItem.
     */
    public static JRadioButtonMenuItem putRadioItem(JPopupMenu onPopup, ButtonGroup group, String text,
            boolean selected) {
        return putRadioItem(onPopup, group, text, null, selected, null, null);
    }

    /**
     * Adds a radio button menu item with selection state and action listener to the
     * popup menu, associated with a ButtonGroup.
     *
     * @param onPopup  The popup menu.
     * @param group    The ButtonGroup to add the item to.
     * @param text     The text of the menu item.
     * @param selected The initial selection state.
     * @param action   The ActionListener to attach.
     * @return The created JRadioButtonMenuItem.
     */
    public static JRadioButtonMenuItem putRadioItem(JPopupMenu onPopup, ButtonGroup group, String text,
            boolean selected, ActionListener action) {
        return putRadioItem(onPopup, group, text, null, selected, null, action);
    }

    /**
     * Adds a radio button menu item with icon, selection state, and action listener
     * to the popup menu, associated with a ButtonGroup.
     *
     * @param onPopup  The popup menu.
     * @param group    The ButtonGroup to add the item to.
     * @param text     The text of the menu item.
     * @param icon     The icon for the menu item.
     * @param selected The initial selection state.
     * @param action   The ActionListener to attach.
     * @return The created JRadioButtonMenuItem.
     */
    public static JRadioButtonMenuItem putRadioItem(JPopupMenu onPopup, ButtonGroup group, String text, Icon icon,
            boolean selected, ActionListener action) {
        return putRadioItem(onPopup, group, text, icon, selected, null, action);
    }

    /**
     * Adds a radio button menu item with tooltip, selection state, and action
     * listener to the popup menu, associated with a ButtonGroup.
     *
     * @param onPopup  The popup menu.
     * @param group    The ButtonGroup to add the item to.
     * @param text     The text of the menu item.
     * @param tooltip  The tooltip text.
     * @param selected The initial selection state.
     * @param action   The ActionListener to attach.
     * @return The created JRadioButtonMenuItem.
     */
    public static JRadioButtonMenuItem putRadioItem(JPopupMenu onPopup, ButtonGroup group, String text, String tooltip,
            boolean selected, ActionListener action) {
        return putRadioItem(onPopup, group, text, null, selected, tooltip, action);
    }

    /**
     * Adds a radio button menu item with selection state and Action to the popup
     * menu, associated with a ButtonGroup.
     *
     * @param onPopup  The popup menu.
     * @param group    The ButtonGroup to add the item to.
     * @param text     The text of the menu item.
     * @param selected The initial selection state.
     * @param action   The Action to attach.
     * @return The created JRadioButtonMenuItem.
     */
    public static JRadioButtonMenuItem putRadioItem(JPopupMenu onPopup, ButtonGroup group, String text,
            boolean selected, Action action) {
        return putRadioItem(onPopup, group, text, null, selected, null, action);
    }

    /**
     * Adds a radio button menu item with icon, selection state, and Action to the
     * popup menu, associated with a ButtonGroup.
     *
     * @param onPopup  The popup menu.
     * @param group    The ButtonGroup to add the item to.
     * @param text     The text of the menu item.
     * @param icon     The icon for the menu item.
     * @param selected The initial selection state.
     * @param action   The Action to attach.
     * @return The created JRadioButtonMenuItem.
     */
    public static JRadioButtonMenuItem putRadioItem(JPopupMenu onPopup, ButtonGroup group, String text, Icon icon,
            boolean selected, Action action) {
        return putRadioItem(onPopup, group, text, icon, selected, null, action);
    }

    /**
     * Adds a radio button menu item with tooltip, selection state, and Action to
     * the popup menu, associated with a ButtonGroup.
     *
     * @param onPopup  The popup menu.
     * @param group    The ButtonGroup to add the item to.
     * @param text     The text of the menu item.
     * @param tooltip  The tooltip text.
     * @param selected The initial selection state.
     * @param action   The Action to attach.
     * @return The created JRadioButtonMenuItem.
     */
    public static JRadioButtonMenuItem putRadioItem(JPopupMenu onPopup, ButtonGroup group, String text, String tooltip,
            boolean selected, Action action) {
        return putRadioItem(onPopup, group, text, null, selected, tooltip, action);
    }

    /**
     * Adds a fully customized radio button menu item to the popup menu.
     *
     * @param onPopup  The popup menu.
     * @param group    The ButtonGroup to add the item to.
     * @param text     The text of the menu item.
     * @param icon     The icon for the menu item.
     * @param selected The initial selection state.
     * @param tooltip  The tooltip text.
     * @param action   The ActionListener to attach.
     * @return The created JRadioButtonMenuItem.
     */
    public static JRadioButtonMenuItem putRadioItem(JPopupMenu onPopup, ButtonGroup group, String text, Icon icon,
            boolean selected, String tooltip, ActionListener action) {
        var item = new JRadioButtonMenuItem(text, selected);
        if (group != null) {
            group.add(item);
        }
        if (icon != null) {
            item.setIcon(icon);
        }
        if (tooltip != null) {
            item.setToolTipText(tooltip);
        }
        if (action != null) {
            item.addActionListener(action);
        }
        onPopup.add(item);
        return item;
    }

    /**
     * Adds a fully customized radio button menu item with an Action to the popup
     * menu.
     *
     * @param onPopup  The popup menu.
     * @param group    The ButtonGroup to add the item to.
     * @param text     The text of the menu item.
     * @param icon     The icon for the menu item.
     * @param selected The initial selection state.
     * @param tooltip  The tooltip text.
     * @param action   The Action to attach.
     * @return The created JRadioButtonMenuItem.
     */
    public static JRadioButtonMenuItem putRadioItem(JPopupMenu onPopup, ButtonGroup group, String text, Icon icon,
            boolean selected, String tooltip, Action action) {
        var item = new JRadioButtonMenuItem();
        if (action != null) {
            item.setAction(action);
        }
        if (text != null) {
            item.setText(text);
        }
        if (icon != null) {
            item.setIcon(icon);
        }
        if (tooltip != null) {
            item.setToolTipText(tooltip);
        }
        item.setSelected(selected);
        if (group != null) {
            group.add(item);
        }
        onPopup.add(item);
        return item;
    }

    /**
     * Adds a submenu to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the submenu.
     * @return The created JMenu.
     */
    public static JMenu putMenu(JPopupMenu onPopup, String text) {
        return putMenu(onPopup, text, null, null, (ActionListener) null);
    }

    /**
     * Adds a submenu with an icon to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the submenu.
     * @param icon    The icon for the submenu.
     * @return The created JMenu.
     */
    public static JMenu putMenu(JPopupMenu onPopup, String text, Icon icon) {
        return putMenu(onPopup, text, icon, null, (ActionListener) null);
    }

    /**
     * Adds a submenu with an icon and tooltip to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the submenu.
     * @param icon    The icon for the submenu.
     * @param tooltip The tooltip text.
     * @return The created JMenu.
     */
    public static JMenu putMenu(JPopupMenu onPopup, String text, Icon icon, String tooltip) {
        return putMenu(onPopup, text, icon, tooltip, (ActionListener) null);
    }

    /**
     * Adds a submenu with an action listener to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the submenu.
     * @param action  The ActionListener to attach.
     * @return The created JMenu.
     */
    public static JMenu putMenu(JPopupMenu onPopup, String text, ActionListener action) {
        return putMenu(onPopup, text, null, null, action);
    }

    /**
     * Adds a submenu with an icon and action listener to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the submenu.
     * @param icon    The icon for the submenu.
     * @param action  The ActionListener to attach.
     * @return The created JMenu.
     */
    public static JMenu putMenu(JPopupMenu onPopup, String text, Icon icon, ActionListener action) {
        return putMenu(onPopup, text, icon, null, action);
    }

    /**
     * Adds a submenu with a tooltip and action listener to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the submenu.
     * @param tooltip The tooltip text.
     * @param action  The ActionListener to attach.
     * @return The created JMenu.
     */
    public static JMenu putMenu(JPopupMenu onPopup, String text, String tooltip, ActionListener action) {
        return putMenu(onPopup, text, null, tooltip, action);
    }

    /**
     * Adds a fully customized submenu to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the submenu.
     * @param icon    The icon for the submenu.
     * @param tooltip The tooltip text.
     * @param action  The ActionListener to attach.
     * @return The created JMenu.
     */
    public static JMenu putMenu(JPopupMenu onPopup, String text, Icon icon, String tooltip, ActionListener action) {
        var item = new JMenu(text);
        if (icon != null) {
            item.setIcon(icon);
        }
        if (tooltip != null) {
            item.setToolTipText(tooltip);
        }
        if (action != null) {
            item.addActionListener(action);
        }
        onPopup.add(item);
        return item;
    }

    /**
     * Adds a submenu with an Action to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the submenu.
     * @param action  The Action to attach.
     * @return The created JMenu.
     */
    public static JMenu putMenu(JPopupMenu onPopup, String text, Action action) {
        return putMenu(onPopup, text, null, null, action);
    }

    /**
     * Adds a submenu with an icon and Action to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the submenu.
     * @param icon    The icon for the submenu.
     * @param action  The Action to attach.
     * @return The created JMenu.
     */
    public static JMenu putMenu(JPopupMenu onPopup, String text, Icon icon, Action action) {
        return putMenu(onPopup, text, icon, null, action);
    }

    /**
     * Adds a submenu with a tooltip and Action to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the submenu.
     * @param tooltip The tooltip text.
     * @param action  The Action to attach.
     * @return The created JMenu.
     */
    public static JMenu putMenu(JPopupMenu onPopup, String text, String tooltip, Action action) {
        return putMenu(onPopup, text, null, tooltip, action);
    }

    /**
     * Adds a fully customized submenu with an Action to the popup menu.
     *
     * @param onPopup The popup menu.
     * @param text    The text of the submenu.
     * @param icon    The icon for the submenu.
     * @param tooltip The tooltip text.
     * @param action  The Action to attach.
     * @return The created JMenu.
     */
    public static JMenu putMenu(JPopupMenu onPopup, String text, Icon icon, String tooltip, Action action) {
        var item = new JMenu();
        if (action != null) {
            item.setAction(action);
        }
        if (text != null) {
            item.setText(text);
        }
        if (icon != null) {
            item.setIcon(icon);
        }
        if (tooltip != null) {
            item.setToolTipText(tooltip);
        }
        onPopup.add(item);
        return item;
    }

    // =========================================================================
    // WINDOW AND COMPONENT UTILITIES
    // =========================================================================

    /**
     * Centers a window on the screen.
     *
     * @param window The window to center.
     */
    public static void center(Window window) {
        if (window != null) {
            window.setLocationRelativeTo(null);
        }
    }

    /**
     * Packs and centers a window on the screen.
     *
     * @param window The window to pack and center.
     */
    public static void packAndCenter(Window window) {
        if (window != null) {
            window.pack();
            window.setLocationRelativeTo(null);
        }
    }

    /**
     * Sets the icon image for a window.
     *
     * @param window The window.
     * @param icon The image icon.
     */
    public static void setIcon(Window window, Image icon) {
        if (window != null && icon != null) {
            window.setIconImage(icon);
        }
    }

    /**
     * Checks if a dark theme is currently active.
     *
     * @return true if a dark theme is active, false otherwise.
     */
    public static boolean isDarkTheme() {
        String laf = getLookAndFeel();
        return KEY_LOOK_AND_FEEL_DARK.equals(laf) || KEY_LOOK_AND_FEEL_DARCULA.equals(laf);
    }

    /**
     * Adds a simple hover effect to a component by changing its background color.
     *
     * @param component The component.
     * @param hoverColor The background color on hover.
     * @param defaultColor The default background color.
     */
    public static void addHoverEffect(JComponent component, Color hoverColor, Color defaultColor) {
        if (component == null) return;
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (component.isEnabled()) {
                    component.setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (component.isEnabled()) {
                    component.setBackground(defaultColor);
                }
            }
        });
    }

}
