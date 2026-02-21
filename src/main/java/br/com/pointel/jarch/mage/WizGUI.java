package br.com.pointel.jarch.mage;

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
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import br.com.pointel.jarch.desk.SwingFramer;
import br.com.pointel.jarch.desk.SwingNotify;

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
    
    public static Image getLogo() {
        return logo;
    }

    private static final String KEY_FONT_NAME = "WIZGUI_FONT_NAME";

    public static String getFontName() {
        return WizProps.get(KEY_FONT_NAME, Font.MONOSPACED);
    }

    public static void setFontName(String name) {
        WizProps.set(KEY_FONT_NAME, name);
    }

    private static final String KEY_FONT_SIZE = "WIZGUI_FONT_SIZE";

    public static int getFontSize() {
        return WizProps.get(KEY_FONT_SIZE, 12);
    }

    public static void setFontSize(int size) {
        WizProps.set(KEY_FONT_SIZE, size);
    }

    private static Font FONT = new Font(getFontName(), Font.PLAIN, getFontSize());

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

    public static String[] getLookAndFeelOptions() {
        return KEY_LOOK_AND_FEEL_OPTIONS;
    }

    public static String getLookAndFeel() {
        return WizProps.get(KEY_LOOK_AND_FEEL, KEY_LOOK_AND_FEEL_SYSTEM);
    }

    public static void setLookAndFeel(String option) {
        WizProps.set(KEY_LOOK_AND_FEEL, option);
    }

    public static void start() {
        start(WizApp.getTitle(), null);
    }

    public static void start(String title) {
        start(title, null);
    }

    public static void start(Runnable afterStart) {
        start(WizApp.getTitle(), afterStart);
    }

    public static void start(String title, Runnable afterStart) {
        logger.info("Starting desk of {} application", WizApp.getName());
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

    public static boolean isStarted() {
        return started;
    }


    public static Window getActiveWindow() {
        for (Window old : Window.getWindows()) {
            if (old.isActive()) {
                return old;
            }
        }
        return null;
    }

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

    public static void putShortCut(JComponent component, String name, String keyStroke,
                    Runnable runnable) {
        var inputMap = component.getInputMap(
                        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        var actionMap = component.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(keyStroke), name);
        actionMap.put(name, WizGUI.getAction(runnable));
    }

    public static Action getAction(Runnable runnable) {
        return new AbstractAction() {
            private static final long serialVersionUID = -1482117853128881492L;

            @Override
            public void actionPerformed(ActionEvent e) {
                runnable.run();
            }
        };
    }

    public static String getStringFromClipboard() throws Exception {
        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(
                        DataFlavor.stringFlavor);
    }

    public static void copyToClipboard(String theString) {
        var selection = new StringSelection(theString);
        var clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static BufferedImage getImageFromClipboard() throws Exception {
        var transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            var pasted = (BufferedImage) transferable.getTransferData(DataFlavor.imageFlavor);
            return WizGUI.convertToRGB(pasted);
        }
        return null;
    }

    public static BufferedImage convertToRGB(BufferedImage image) {
        var result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        return result;
    }

    public static Pattern deskMnemonic = Pattern.compile("\\s\\s\\s\\[\\s.\\s\\]$");

    public static String delMnemonic(String fromTitle) {
        if (fromTitle == null) {
            return fromTitle;
        }
        if (WizGUI.deskMnemonic.matcher(fromTitle).find()) {
            return fromTitle.substring(0, fromTitle.length() - 8);
        }
        return fromTitle;
    }

    public static JComponent getItem(JMenuBar fromBar, String onPath) {
        JComponent result = fromBar;
        if (onPath != null) {
            var parts = onPath.split("\\.");
            if (parts.length > 0) {
                for (String part : parts) {
                    if (result instanceof JMenu) {
                        var found = false;
                        for (Component comp : ((JMenu) result).getMenuComponents()) {
                            if (comp instanceof JMenu) {
                                if (part.equals(WizGUI.delMnemonic(((JMenu) comp)
                                                .getText()))) {
                                    result = (JMenu) comp;
                                    found = true;
                                }
                            } else if (comp instanceof JMenuItem) {
                                if (part.equals(WizGUI.delMnemonic(((JMenuItem) comp)
                                                .getText()))) {
                                    result = (JMenuItem) comp;
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
                            return null;
                        }
                    } else {
                        var found = false;
                        for (Component comp : result.getComponents()) {
                            if (comp instanceof JMenu) {
                                if (part.equals(WizGUI.delMnemonic(((JMenu) comp)
                                                .getText()))) {
                                    result = (JMenu) comp;
                                    found = true;
                                }
                            } else if (comp instanceof JMenuItem) {
                                if (part.equals(WizGUI.delMnemonic(((JMenuItem) comp)
                                                .getText()))) {
                                    result = (JMenuItem) comp;
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
                            return null;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static JComponent getItem(JPopupMenu doPopup, String onPath) {
        JComponent result = doPopup;
        if (onPath != null) {
            var parts = onPath.split("\\.");
            if (parts.length > 0) {
                for (String part : parts) {
                    if (result instanceof JMenu) {
                        var found = false;
                        for (Component comp : ((JMenu) result).getMenuComponents()) {
                            if (comp instanceof JMenu) {
                                if (part.equals(WizGUI.delMnemonic(((JMenu) comp)
                                                .getText()))) {
                                    result = (JMenu) comp;
                                    found = true;
                                }
                            } else if (comp instanceof JMenuItem) {
                                if (part.equals(WizGUI.delMnemonic(((JMenuItem) comp)
                                                .getText()))) {
                                    result = (JMenuItem) comp;
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
                            return null;
                        }
                    } else {
                        var found = false;
                        for (Component comp : result.getComponents()) {
                            if (comp instanceof JMenu) {
                                if (part.equals(WizGUI.delMnemonic(((JMenu) comp)
                                                .getText()))) {
                                    result = (JMenu) comp;
                                    found = true;
                                }
                            } else if (comp instanceof JMenuItem) {
                                if (part.equals(WizGUI.delMnemonic(((JMenuItem) comp)
                                                .getText()))) {
                                    result = (JMenuItem) comp;
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
                            return null;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static JMenu getMenu(JMenuBar fromBar, String onPath) {
        JMenu result = null;
        if (onPath != null) {
            var parts = onPath.split("\\.");
            if (parts.length > 0) {
                for (Component comp : fromBar.getComponents()) {
                    if (comp instanceof JMenu) {
                        if (parts[0].equals(WizGUI.delMnemonic(((JMenu) comp)
                                        .getText()))) {
                            result = (JMenu) comp;
                            break;
                        }
                    }
                }
                for (var ip = 1; ip < parts.length; ip++) {
                    result = WizGUI.getMenu(result, parts[ip]);
                    if (result == null) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static JMenu getMenu(JPopupMenu doPopup, String onPath) {
        JMenu result = null;
        if (onPath != null) {
            var parts = onPath.split("\\.");
            if (parts.length > 0) {
                for (Component comp : doPopup.getComponents()) {
                    if (comp instanceof JMenu) {
                        if (parts[0].equals(WizGUI.delMnemonic(((JMenu) comp)
                                        .getText()))) {
                            result = (JMenu) comp;
                            break;
                        }
                    }
                }
                for (var ip = 1; ip < parts.length; ip++) {
                    result = WizGUI.getMenu(result, parts[ip]);
                    if (result == null) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static JMenu getMenu(JMenu fromMenu, String withTitle) {
        JMenu result = null;
        if (withTitle != null) {
            for (Component comp : fromMenu.getMenuComponents()) {
                if (comp instanceof JMenu) {
                    if (withTitle.equals(WizGUI.delMnemonic(((JMenu) comp).getText()))) {
                        result = (JMenu) comp;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static JMenuItem getMenuItem(JPopupMenu doPopup, String onPath) {
        if (onPath != null) {
            var parts = onPath.split("\\.");
            if (parts.length > 0) {
                JMenu menu = null;
                for (Component comp : doPopup.getComponents()) {
                    if (comp instanceof JMenu) {
                        if (parts[0].equals(WizGUI.delMnemonic(((JMenu) comp)
                                        .getText()))) {
                            menu = (JMenu) comp;
                            break;
                        }
                    } else if (comp instanceof JMenuItem) {
                        if (parts[0].equals(WizGUI.delMnemonic(((JMenuItem) comp)
                                        .getText()))) {
                            return (JMenuItem) comp;
                        }
                    }
                }
                if (menu == null) {
                    return null;
                }
                for (var ip = 1; ip < parts.length - 1; ip++) {
                    menu = WizGUI.getMenu(menu, parts[ip]);
                    if (menu == null) {
                        return null;
                    }
                }
                return WizGUI.getMenuItem(menu, parts[parts.length - 1]);
            }
        }
        return null;
    }

    public static JMenuItem getMenuItem(JMenu fromMenu, String withTitle) {
        JMenuItem result = null;
        if (withTitle != null) {
            for (Component comp : fromMenu.getMenuComponents()) {
                if (comp instanceof JMenuItem) {
                    if (withTitle.equals(WizGUI.delMnemonic(((JMenuItem) comp)
                                    .getText()))) {
                        result = (JMenuItem) comp;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public static void execute(ActionListener[] actions) {
        if (actions != null) {
            for (ActionListener act : actions) {
                act.actionPerformed(null);
            }
        }
    }

    public static void callOrInvoke(Runnable runnable) {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeLater(runnable);
        }
    }

    public static void callOrWait(Runnable runnable) throws Exception {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeAndWait(runnable);
        }
    }

    public static void showNotify(String message) {
        showNotify(message, 3);
    }

    public static void showNotify(String message, double seconds) {
        SwingNotify.show(message, seconds);
    }

    public static void showInfo(String message) {
        logger.info(message);
        Runnable runner = () -> {
            JOptionPane.showMessageDialog(WizGUI.getActiveWindow(), message,
                            WizApp.getTitle(),JOptionPane.INFORMATION_MESSAGE);
        };
        if (SwingUtilities.isEventDispatchThread()) {
            runner.run();
        } else {
            SwingUtilities.invokeLater(runner);
        }
    }

    public static void showError(Throwable error) {
        showError(error, null);
    }

    public static void showError(Throwable error, String detail) {
        String message = error.getMessage() + (detail != null ? " " + detail : "");
        logger.error(message, error);
        Runnable runner = () -> {
            JOptionPane.showMessageDialog(WizGUI.getActiveWindow(), message,
                            WizApp.getTitle(),JOptionPane.ERROR_MESSAGE);
        };
        if (SwingUtilities.isEventDispatchThread()) {
            runner.run();
        } else {
            SwingUtilities.invokeLater(runner);
        }
    }

    public static boolean showConfirm(String message) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
                        WizGUI.getActiveWindow(), message, "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    public static String showInput(String message) {
        return JOptionPane.showInputDialog(
                        WizGUI.getActiveWindow(), message, "Input",
                        JOptionPane.QUESTION_MESSAGE);
    }

    public static String showInput(String question, String value) {
        return (String) JOptionPane.showInputDialog(WizGUI.getActiveWindow(), question,
                        WizApp.getTitle(), JOptionPane.QUESTION_MESSAGE, null, null, value);
    }

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

    public static void putStringOnClipboard(String string) {
        StringSelection selection = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static void open(File file) throws Exception {
        Desktop.getDesktop().open(file);
    }

    public static void navigate(String address) throws Exception {
        Desktop.getDesktop().browse(new URI(address));
    }

    public static void exploreAndSelect(File filePath) throws Exception {
        if (filePath == null) {
            throw new Exception("File path cannot be null");
        }
        if (!filePath.exists()) {
            throw new Exception("File does not exist: " + filePath);
        }
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            Runtime.getRuntime().exec(new String[] {"explorer.exe", "/select,", filePath.getAbsolutePath()});
        } else if (os.contains("mac")) {
            Runtime.getRuntime().exec(new String[] {"open", "-R", filePath.getAbsolutePath()});
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            File parentDir = filePath.getParentFile();
            if (parentDir != null && parentDir.isDirectory()) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                    Desktop.getDesktop().open(parentDir);
                } else {
                    Runtime.getRuntime().exec(new String[] {"xdg-open", parentDir.getAbsolutePath()});
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

    public static Font fontMonospaced() {
        return fontMonospaced(12);
    }

    public static Font fontMonospaced(int size) {
        return new Font(Font.MONOSPACED, Font.PLAIN, size);
    }

    public static Font fontSerif() {
        return fontSerif(12);
    }

    public static Font fontSerif(int size) {
        return new Font(Font.SERIF, Font.PLAIN, size);
    }

    public static Font fontSansSerif() {
        return fontSansSerif(12);
    }

    public static Font fontSansSerif(int size) {
        return new Font(Font.SANS_SERIF, Font.PLAIN, size);
    }

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

    public static <T extends Class<? extends Component>> List<Component> getAllComponentsOf(Component root, T... clazz) {
        var results = new ArrayList<Component>();
        getAllComponentsOf(results, root, clazz);
        return results;
    }

    public static <T extends Class<? extends Component>> void getAllComponentsOf(List<Component> results, Component root, T... clazz) {
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

    public static void cleanAllNames(Component root) {
        root.setName(null);
        if (root instanceof Container container) {
            for (Component inside : container.getComponents()) {
                cleanAllNames(inside);
            }
        }
    }

    public static SwingFramer initFrame(JFrame frame) {
        return initFrame(frame, getFont());
    }

    public static SwingFramer initFrame(JFrame frame, Font font) {
        return new SwingFramer(frame, font).init();
    }

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

    public static void close(JFrame frame) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame.setVisible(false);
        frame.dispose();
    }

    public static void closeAll() {
        closeAll(false);
        closeAll(true);
    }

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

    public static void addPopup(JComponent component, JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) {
                    popup.show(component, e.getX(), e.getY());
                }
            }
        });
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    popup.show(component, 0, 0);
                }
            }
        });
    }

    public static void addButton(JComponent component, AbstractButton button) {
        addButton(component, button, null);
    }

    public static void addButton(JComponent component, AbstractButton button, ActionListener action) {
        if (action != null) {
            button.addActionListener(action);
        }
        component.add(button);
    }

    public static void addDefaultAction(JComponent component, ActionListener action) {
        addDefaultAction(component, null, action);
    }

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

    public static void debounceEnable(JComponent component) {
        debounceEnable(1000, component);
    }

    public static void debounceEnable(int millis, JComponent component) {
        component.setEnabled(false);
        debounceAction(millis, () -> component.setEnabled(true));
    }

    public static void debounceAction(int millis, Runnable action) {
        new Thread("Trigger Debounce") {
            @Override
            public void run() {
                WizThread.sleep(millis);
                SwingUtilities.invokeLater(action);
            }
        }.start();
    }

    public static Point getMouseCurrentPoint() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    public static GraphicsDevice getScreenWithMouse() {
        var mousePoint = getMouseCurrentPoint();
        for (var screen : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
            if (screen.getDefaultConfiguration().getBounds().contains(mousePoint)) {
                return screen;
            }
        }
        return null;
    }

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

}
