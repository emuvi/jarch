package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidlus.jarch.mage.WizGUI;
import com.vidlus.jarch.mage.WizThread;

/**
 * Utility class for displaying various types of desktop notifications using
 * Swing.
 * Provides multiple styles including standard bottom-right alerts, toasts,
 * banners, modals, and tooltips.
 */
public class SwingNotify {

    private static final Logger log = LoggerFactory.getLogger(SwingNotify.class);

    /**
     * Shows a standard notification with the given message object for a default
     * duration.
     * 
     * @param message the message object to display
     */
    public static void show(Object message) {
        show(message, 3.0);
    }

    /**
     * Shows a standard notification with the specified message for a default
     * duration.
     * 
     * @param message the message to display
     */
    public static void show(String message) {
        show(message, 3.0);
    }

    /**
     * Shows a informational notification with the given message object for a
     * default duration.
     * 
     * @param message the message object to display
     */
    public static void showInfo(Object message) {
        showInfo(message, 3.0);
    }

    /**
     * Shows a informational notification with the given string message for a
     * default duration.
     * 
     * @param message the message to display
     */
    public static void showInfo(String message) {
        showInfo(message, 3.0);
    }

    /**
     * Shows a informational notification with the given message object and
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void showInfo(Object message, double seconds) {
        if (message == null) {
            return;
        }
        showInfo(message.toString(), seconds);
    }

    /**
     * Shows a informational notification with the given string message and
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void showInfo(String message, double seconds) {
        show(message, seconds, new Color(20, 100, 200));
    }

    /**
     * Shows a warning notification with the given message object for a default
     * duration.
     * 
     * @param message the message object to display
     */
    public static void showWarning(Object message) {
        showWarning(message, 4.0);
    }

    /**
     * Shows a warning notification with the given string message for a default
     * duration.
     * 
     * @param message the message to display
     */
    public static void showWarning(String message) {
        showWarning(message, 4.0);
    }

    /**
     * Shows a warning notification with the given message object and duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void showWarning(Object message, double seconds) {
        if (message == null) {
            return;
        }
        showWarning(message.toString(), seconds);
    }

    /**
     * Shows a warning notification with the given string message and duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void showWarning(String message, double seconds) {
        show(message, seconds, new Color(220, 140, 20));
    }

    /**
     * Shows a error notification with the given message object for a default
     * duration.
     * 
     * @param message the message object to display
     */
    public static void showError(Object message) {
        showError(message, 5.0);
    }

    /**
     * Shows a error notification with the given string message for a default
     * duration.
     * 
     * @param message the message to display
     */
    public static void showError(String message) {
        showError(message, 5.0);
    }

    /**
     * Shows a error notification with the given message object and duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void showError(Object message, double seconds) {
        if (message == null) {
            return;
        }
        showError(message.toString(), seconds);
    }

    /**
     * Shows a error notification with the given string message and duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void showError(String message, double seconds) {
        show(message, seconds, new Color(200, 20, 20));
    }

    /**
     * Shows a success notification with the given message object for a default
     * duration.
     * 
     * @param message the message object to display
     */
    public static void showSuccess(Object message) {
        showSuccess(message, 3.0);
    }

    /**
     * Shows a success notification with the given string message for a default
     * duration.
     * 
     * @param message the message to display
     */
    public static void showSuccess(String message) {
        showSuccess(message, 3.0);
    }

    /**
     * Shows a success notification with the given message object and duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void showSuccess(Object message, double seconds) {
        if (message == null) {
            return;
        }
        showSuccess(message.toString(), seconds);
    }

    /**
     * Shows a success notification with the given string message and duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void showSuccess(String message, double seconds) {
        show(message, seconds, new Color(20, 200, 50));
    }

    /**
     * Displays a informational toast notification with the given message object for
     * a default duration.
     * 
     * @param message the message object to display
     */
    public static void toastInfo(Object message) {
        toastInfo(message, 2.0);
    }

    /**
     * Displays a informational toast notification with the given string message for
     * a default duration.
     * 
     * @param message the message to display
     */
    public static void toastInfo(String message) {
        toastInfo(message, 2.0);
    }

    /**
     * Displays a informational toast notification with the given message object and
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void toastInfo(Object message, double seconds) {
        if (message != null) {
            toastInfo(message.toString(), seconds);
        }
    }

    /**
     * Displays a informational toast notification with the given string message and
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void toastInfo(String message, double seconds) {
        toast(message, seconds, new Color(20, 100, 200, 180), Color.WHITE);
    }

    /**
     * Displays a warning toast notification with the given message object for a
     * default duration.
     * 
     * @param message the message object to display
     */
    public static void toastWarning(Object message) {
        toastWarning(message, 3.0);
    }

    /**
     * Displays a warning toast notification with the given string message for a
     * default duration.
     * 
     * @param message the message to display
     */
    public static void toastWarning(String message) {
        toastWarning(message, 3.0);
    }

    /**
     * Displays a warning toast notification with the given message object and
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void toastWarning(Object message, double seconds) {
        if (message != null) {
            toastWarning(message.toString(), seconds);
        }
    }

    /**
     * Displays a warning toast notification with the given string message and
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void toastWarning(String message, double seconds) {
        toast(message, seconds, new Color(220, 140, 20, 180), Color.WHITE);
    }

    /**
     * Displays a error toast notification with the given message object for a
     * default duration.
     * 
     * @param message the message object to display
     */
    public static void toastError(Object message) {
        toastError(message, 4.0);
    }

    /**
     * Displays a error toast notification with the given string message for a
     * default duration.
     * 
     * @param message the message to display
     */
    public static void toastError(String message) {
        toastError(message, 4.0);
    }

    /**
     * Displays a error toast notification with the given message object and
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void toastError(Object message, double seconds) {
        if (message != null) {
            toastError(message.toString(), seconds);
        }
    }

    /**
     * Displays a error toast notification with the given string message and
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void toastError(String message, double seconds) {
        toast(message, seconds, new Color(200, 20, 20, 180), Color.WHITE);
    }

    /**
     * Displays a success toast notification with the given message object for a
     * default duration.
     * 
     * @param message the message object to display
     */
    public static void toastSuccess(Object message) {
        toastSuccess(message, 2.0);
    }

    /**
     * Displays a success toast notification with the given string message for a
     * default duration.
     * 
     * @param message the message to display
     */
    public static void toastSuccess(String message) {
        toastSuccess(message, 2.0);
    }

    /**
     * Displays a success toast notification with the given message object and
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void toastSuccess(Object message, double seconds) {
        if (message != null) {
            toastSuccess(message.toString(), seconds);
        }
    }

    /**
     * Displays a success toast notification with the given string message and
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void toastSuccess(String message, double seconds) {
        toast(message, seconds, new Color(20, 200, 50, 180), Color.WHITE);
    }

    /**
     * Displays a toast notification with the given message object for a default
     * duration.
     * 
     * @param message the message object to display
     */
    public static void toast(Object message) {
        toast(message, 2.0);
    }

    /**
     * Displays a toast notification with the given string message for a default
     * duration.
     * 
     * @param message the message to display
     */
    public static void toast(String message) {
        toast(message, 2.0);
    }

    /**
     * Displays a toast notification with the given message object and duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void toast(Object message, double seconds) {
        if (message == null) {
            return;
        }
        toast(message.toString(), seconds);
    }

    /**
     * Displays a toast notification with the given string message and duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void toast(String message, double seconds) {
        toast(message, seconds, new Color(0, 0, 0, 180), Color.WHITE);
    }

    /**
     * Displays a toast notification with custom background and text colors.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     * @param bgColor the background color
     * @param fgColor the text color
     */
    public static void toast(String message, double seconds, Color bgColor, Color fgColor) {
        if (message == null || message.isBlank()) {
            return;
        }
        log.info("TOAST: " + message);
        var frame = createFrame();
        try {
            frame.setBackground(bgColor);
        } catch (Exception e) {
            frame.getContentPane().setBackground(Color.DARK_GRAY);
        }
        var label = new javax.swing.JLabel(message);
        label.setForeground(fgColor);
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        frame.add(label);
        frame.pack();
        var bounds = getScreenBounds();
        frame.setBounds(bounds.x + (bounds.width - frame.getWidth()) / 2, bounds.y + bounds.height - 150,
                frame.getWidth(), frame.getHeight());
        displayAndClose(frame, "SwingNotify Toast Closer", seconds, null);
    }

    /**
     * Displays a top informational banner notification with the given message
     * object for a default duration.
     * 
     * @param message the message object to display
     */
    public static void bannerInfo(Object message) {
        bannerInfo(message, 3.0);
    }

    /**
     * Displays a top informational banner notification with the given string
     * message for a default duration.
     * 
     * @param message the message to display
     */
    public static void bannerInfo(String message) {
        bannerInfo(message, 3.0);
    }

    /**
     * Displays a top informational banner notification with the given message
     * object and duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void bannerInfo(Object message, double seconds) {
        if (message != null) {
            bannerInfo(message.toString(), seconds);
        }
    }

    /**
     * Displays a top informational banner notification with the given string
     * message and duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void bannerInfo(String message, double seconds) {
        banner(message, seconds, new Color(20, 100, 200), Color.WHITE);
    }

    /**
     * Displays a top warning banner notification with the given message object for
     * a default duration.
     * 
     * @param message the message object to display
     */
    public static void bannerWarning(Object message) {
        bannerWarning(message, 4.0);
    }

    /**
     * Displays a top warning banner notification with the given string message for
     * a default duration.
     * 
     * @param message the message to display
     */
    public static void bannerWarning(String message) {
        bannerWarning(message, 4.0);
    }

    /**
     * Displays a top warning banner notification with the given message object and
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void bannerWarning(Object message, double seconds) {
        if (message != null) {
            bannerWarning(message.toString(), seconds);
        }
    }

    /**
     * Displays a top warning banner notification with the given string message and
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void bannerWarning(String message, double seconds) {
        banner(message, seconds, new Color(220, 140, 20), Color.WHITE);
    }

    /**
     * Displays a top error banner notification with the given message object for a
     * default duration.
     * 
     * @param message the message object to display
     */
    public static void bannerError(Object message) {
        bannerError(message, 5.0);
    }

    /**
     * Displays a top error banner notification with the given string message for a
     * default duration.
     * 
     * @param message the message to display
     */
    public static void bannerError(String message) {
        bannerError(message, 5.0);
    }

    /**
     * Displays a top error banner notification with the given message object and
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void bannerError(Object message, double seconds) {
        if (message != null) {
            bannerError(message.toString(), seconds);
        }
    }

    /**
     * Displays a top error banner notification with the given string message and
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void bannerError(String message, double seconds) {
        banner(message, seconds, new Color(200, 20, 20), Color.WHITE);
    }

    /**
     * Displays a top success banner notification with the given message object for
     * a default duration.
     * 
     * @param message the message object to display
     */
    public static void bannerSuccess(Object message) {
        bannerSuccess(message, 3.0);
    }

    /**
     * Displays a top success banner notification with the given string message for
     * a default duration.
     * 
     * @param message the message to display
     */
    public static void bannerSuccess(String message) {
        bannerSuccess(message, 3.0);
    }

    /**
     * Displays a top success banner notification with the given message object and
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void bannerSuccess(Object message, double seconds) {
        if (message != null) {
            bannerSuccess(message.toString(), seconds);
        }
    }

    /**
     * Displays a top success banner notification with the given string message and
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void bannerSuccess(String message, double seconds) {
        banner(message, seconds, new Color(20, 200, 50), Color.WHITE);
    }

    /**
     * Displays a top banner notification with the given message object for a
     * default duration.
     * 
     * @param message the message object to display
     */
    public static void banner(Object message) {
        banner(message, 3.0);
    }

    /**
     * Displays a top banner notification with the given string message for a
     * default duration.
     * 
     * @param message the message to display
     */
    public static void banner(String message) {
        banner(message, 3.0);
    }

    /**
     * Displays a top banner notification with the given message object and
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void banner(Object message, double seconds) {
        if (message == null) {
            return;
        }
        banner(message.toString(), seconds);
    }

    /**
     * Displays a top banner notification with the given string message and
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void banner(String message, double seconds) {
        banner(message, seconds, new Color(50, 50, 50), Color.WHITE);
    }

    /**
     * Displays a top banner notification with custom background and text colors.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     * @param bgColor the background color
     * @param fgColor the text color
     */
    public static void banner(String message, double seconds, Color bgColor, Color fgColor) {
        if (message == null || message.isBlank()) {
            return;
        }
        log.info("BANNER: " + message);
        var frame = createFrame();
        var panelBody = new JPanel(new BorderLayout());
        panelBody.setBackground(bgColor);
        frame.setContentPane(panelBody);
        var label = new javax.swing.JLabel(message, javax.swing.SwingConstants.CENTER);
        label.setForeground(fgColor);
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelBody.add(label, BorderLayout.CENTER);
        frame.pack();
        var bounds = getScreenBounds();
        frame.setBounds(bounds.x, bounds.y, bounds.width, frame.getHeight());
        displayAndClose(frame, "SwingNotify Banner Closer", seconds, null);
    }

    /**
     * Displays a modal notification centered on the screen with the given message
     * object for a default duration.
     * 
     * @param message the message object to display
     */
    public static void modal(Object message) {
        modal(message, 3.0);
    }

    /**
     * Displays a modal notification centered on the screen with the given string
     * message for a default duration.
     * 
     * @param message the message to display
     */
    public static void modal(String message) {
        modal(message, 3.0);
    }

    /**
     * Displays a modal notification centered on the screen with the given message
     * object and duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void modal(Object message, double seconds) {
        if (message != null) {
            modal(message.toString(), seconds);
        }
    }

    /**
     * Displays a modal notification centered on the screen with the given string
     * message and duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void modal(String message, double seconds) {
        if (message == null || message.isBlank()) {
            return;
        }
        log.info("MODAL: " + message);
        var frame = createFrame();
        var panelBody = new JPanel(new BorderLayout());
        panelBody.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
        frame.setContentPane(panelBody);
        var label = new javax.swing.JLabel(message, javax.swing.SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelBody.add(label, BorderLayout.CENTER);
        frame.pack();
        var bounds = getScreenBounds();
        frame.setBounds(bounds.x + (bounds.width - frame.getWidth()) / 2,
                bounds.y + (bounds.height - frame.getHeight()) / 2, frame.getWidth(), frame.getHeight());
        displayAndClose(frame, "SwingNotify Modal Closer", seconds, null);
    }

    /**
     * Displays a tooltip notification near the mouse cursor with the given message
     * object for a default duration.
     * 
     * @param message the message object to display
     */
    public static void tooltip(Object message) {
        tooltip(message, 2.0);
    }

    /**
     * Displays a tooltip notification near the mouse cursor with the given string
     * message for a default duration.
     * 
     * @param message the message to display
     */
    public static void tooltip(String message) {
        tooltip(message, 2.0);
    }

    /**
     * Displays a tooltip notification near the mouse cursor with the given message
     * object and duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void tooltip(Object message, double seconds) {
        if (message != null) {
            tooltip(message.toString(), seconds);
        }
    }

    /**
     * Displays a tooltip notification near the mouse cursor with the given string
     * message and duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void tooltip(String message, double seconds) {
        if (message == null || message.isBlank()) {
            return;
        }
        log.info("TOOLTIP: " + message);
        var frame = createFrame();
        var panelBody = new JPanel(new BorderLayout());
        panelBody.setBackground(new Color(255, 255, 200));
        panelBody.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, false));
        frame.setContentPane(panelBody);
        var label = new javax.swing.JLabel(message);
        label.setForeground(Color.BLACK);
        label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        panelBody.add(label, BorderLayout.CENTER);
        frame.pack();
        var mouse = WizGUI.getMouseCurrentPoint();
        frame.setBounds(mouse.x + 15, mouse.y + 15, frame.getWidth(), frame.getHeight());
        displayAndClose(frame, "SwingNotify Tooltip Closer", seconds, null);
    }

    /**
     * Shows a standard notification with the given message object and duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds to display the notification
     */
    public static void show(Object message, double seconds) {
        if (message == null) {
            return;
        }
        var messageStr = message.toString();
        if (messageStr.isBlank()) {
            return;
        }
        show(messageStr, seconds);
    }

    /**
     * Shows a standard notification with the specified message and duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void show(String message, double seconds) {
        show(message, seconds, Color.BLACK);
    }

    /**
     * Shows a standard notification with the specified message, duration, and
     * border color.
     * 
     * @param message     the message to display
     * @param seconds     the duration in seconds
     * @param borderColor the color of the border and progress bar
     */
    public static void show(String message, double seconds, Color borderColor) {
        if (message == null) {
            return;
        }
        log.info(message);
        var frame = createFrame();
        var panelBody = new JPanel(new BorderLayout());
        panelBody.setBorder(BorderFactory.createLineBorder(borderColor, 2, true));
        frame.setContentPane(panelBody);
        var fieldText = new JTextArea(4, 40);
        fieldText.setLineWrap(true);
        fieldText.setWrapStyleWord(true);
        fieldText.setEditable(false);
        fieldText.setFocusable(false);
        fieldText.setText(message);
        var scrollText = new JScrollPane(fieldText);
        scrollText.setFocusable(false);
        panelBody.add(scrollText, BorderLayout.CENTER);
        var viewProgress = new JProgressBar(0, (int) (seconds * 100));
        viewProgress.setValue(viewProgress.getMinimum());
        viewProgress.setFocusable(false);
        viewProgress.setForeground(borderColor);
        panelBody.add(viewProgress, BorderLayout.SOUTH);
        frame.pack();
        var bounds = getScreenBounds();
        frame.setBounds(bounds.x + bounds.width - 400, bounds.y + 30, 360, 80);
        displayAndClose(frame, "SwingNotify Closer", seconds, viewProgress);
    }

    // --- Snackbar Methods ---

    /**
     * Displays a bottom-left informational snackbar notification for a default
     * duration.
     * 
     * @param message the message object to display
     */
    public static void snackbarInfo(Object message) {
        snackbarInfo(message, 3.0);
    }

    /**
     * Displays a bottom-left informational snackbar notification for a default
     * duration.
     * 
     * @param message the message to display
     */
    public static void snackbarInfo(String message) {
        snackbarInfo(message, 3.0);
    }

    /**
     * Displays a bottom-left informational snackbar notification with the given
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void snackbarInfo(Object message, double seconds) {
        if (message != null) {
            snackbarInfo(message.toString(), seconds);
        }
    }

    /**
     * Displays a bottom-left informational snackbar notification with the given
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void snackbarInfo(String message, double seconds) {
        snackbar(message, seconds, new Color(20, 100, 200), Color.WHITE);
    }

    /**
     * Displays a bottom-left warning snackbar notification for a default duration.
     * 
     * @param message the message object to display
     */
    public static void snackbarWarning(Object message) {
        snackbarWarning(message, 4.0);
    }

    /**
     * Displays a bottom-left warning snackbar notification for a default duration.
     * 
     * @param message the message to display
     */
    public static void snackbarWarning(String message) {
        snackbarWarning(message, 4.0);
    }

    /**
     * Displays a bottom-left warning snackbar notification with the given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void snackbarWarning(Object message, double seconds) {
        if (message != null) {
            snackbarWarning(message.toString(), seconds);
        }
    }

    /**
     * Displays a bottom-left warning snackbar notification with the given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void snackbarWarning(String message, double seconds) {
        snackbar(message, seconds, new Color(220, 140, 20), Color.WHITE);
    }

    /**
     * Displays a bottom-left error snackbar notification for a default duration.
     * 
     * @param message the message object to display
     */
    public static void snackbarError(Object message) {
        snackbarError(message, 5.0);
    }

    /**
     * Displays a bottom-left error snackbar notification for a default duration.
     * 
     * @param message the message to display
     */
    public static void snackbarError(String message) {
        snackbarError(message, 5.0);
    }

    /**
     * Displays a bottom-left error snackbar notification with the given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void snackbarError(Object message, double seconds) {
        if (message != null) {
            snackbarError(message.toString(), seconds);
        }
    }

    /**
     * Displays a bottom-left error snackbar notification with the given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void snackbarError(String message, double seconds) {
        snackbar(message, seconds, new Color(200, 20, 20), Color.WHITE);
    }

    /**
     * Displays a bottom-left success snackbar notification for a default duration.
     * 
     * @param message the message object to display
     */
    public static void snackbarSuccess(Object message) {
        snackbarSuccess(message, 3.0);
    }

    /**
     * Displays a bottom-left success snackbar notification for a default duration.
     * 
     * @param message the message to display
     */
    public static void snackbarSuccess(String message) {
        snackbarSuccess(message, 3.0);
    }

    /**
     * Displays a bottom-left success snackbar notification with the given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void snackbarSuccess(Object message, double seconds) {
        if (message != null) {
            snackbarSuccess(message.toString(), seconds);
        }
    }

    /**
     * Displays a bottom-left success snackbar notification with the given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void snackbarSuccess(String message, double seconds) {
        snackbar(message, seconds, new Color(20, 200, 50), Color.WHITE);
    }

    /**
     * Displays a bottom-left snackbar notification for a default duration.
     * 
     * @param message the message object to display
     */
    public static void snackbar(Object message) {
        snackbar(message, 3.0);
    }

    /**
     * Displays a bottom-left snackbar notification for a default duration.
     * 
     * @param message the message to display
     */
    public static void snackbar(String message) {
        snackbar(message, 3.0);
    }

    /**
     * Displays a bottom-left snackbar notification with the given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void snackbar(Object message, double seconds) {
        if (message != null) {
            snackbar(message.toString(), seconds);
        }
    }

    /**
     * Displays a bottom-left snackbar notification with the given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void snackbar(String message, double seconds) {
        snackbar(message, seconds, new Color(40, 40, 40), Color.WHITE);
    }

    /**
     * Displays a snackbar notification in the bottom-left corner with custom
     * background and text colors.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     * @param bgColor the background color
     * @param fgColor the text color
     */
    public static void snackbar(String message, double seconds, Color bgColor, Color fgColor) {
        if (message == null || message.isBlank()) {
            return;
        }
        log.info("SNACKBAR: " + message);
        var frame = createFrame();
        var panelBody = new JPanel(new BorderLayout());
        panelBody.setBackground(bgColor);
        panelBody.setBorder(BorderFactory.createLineBorder(bgColor.darker(), 2, false));
        frame.setContentPane(panelBody);
        var label = new javax.swing.JLabel(message);
        label.setForeground(fgColor);
        label.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        panelBody.add(label, BorderLayout.CENTER);
        frame.pack();
        var bounds = getScreenBounds();
        frame.setBounds(bounds.x + 30, bounds.y + bounds.height - frame.getHeight() - 50, frame.getWidth(),
                frame.getHeight());
        displayAndClose(frame, "SwingNotify Snackbar Closer", seconds, null);
    }

    // --- Popup Methods ---

    /**
     * Displays a top-right informational popup notification for a default duration.
     * 
     * @param message the message object to display
     */
    public static void popupInfo(Object message) {
        popupInfo(message, 3.0);
    }

    /**
     * Displays a top-right informational popup notification for a default duration.
     * 
     * @param message the message to display
     */
    public static void popupInfo(String message) {
        popupInfo(message, 3.0);
    }

    /**
     * Displays a top-right informational popup notification with the given
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void popupInfo(Object message, double seconds) {
        if (message != null) {
            popupInfo(message.toString(), seconds);
        }
    }

    /**
     * Displays a top-right informational popup notification with the given
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void popupInfo(String message, double seconds) {
        popup(message, seconds, new Color(20, 100, 200), Color.WHITE);
    }

    /**
     * Displays a top-right warning popup notification for a default duration.
     * 
     * @param message the message object to display
     */
    public static void popupWarning(Object message) {
        popupWarning(message, 4.0);
    }

    /**
     * Displays a top-right warning popup notification for a default duration.
     * 
     * @param message the message to display
     */
    public static void popupWarning(String message) {
        popupWarning(message, 4.0);
    }

    /**
     * Displays a top-right warning popup notification with the given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void popupWarning(Object message, double seconds) {
        if (message != null) {
            popupWarning(message.toString(), seconds);
        }
    }

    /**
     * Displays a top-right warning popup notification with the given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void popupWarning(String message, double seconds) {
        popup(message, seconds, new Color(220, 140, 20), Color.WHITE);
    }

    /**
     * Displays a top-right error popup notification for a default duration.
     * 
     * @param message the message object to display
     */
    public static void popupError(Object message) {
        popupError(message, 5.0);
    }

    /**
     * Displays a top-right error popup notification for a default duration.
     * 
     * @param message the message to display
     */
    public static void popupError(String message) {
        popupError(message, 5.0);
    }

    /**
     * Displays a top-right error popup notification with the given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void popupError(Object message, double seconds) {
        if (message != null) {
            popupError(message.toString(), seconds);
        }
    }

    /**
     * Displays a top-right error popup notification with the given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void popupError(String message, double seconds) {
        popup(message, seconds, new Color(200, 20, 20), Color.WHITE);
    }

    /**
     * Displays a top-right success popup notification for a default duration.
     * 
     * @param message the message object to display
     */
    public static void popupSuccess(Object message) {
        popupSuccess(message, 3.0);
    }

    /**
     * Displays a top-right success popup notification for a default duration.
     * 
     * @param message the message to display
     */
    public static void popupSuccess(String message) {
        popupSuccess(message, 3.0);
    }

    /**
     * Displays a top-right success popup notification with the given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void popupSuccess(Object message, double seconds) {
        if (message != null) {
            popupSuccess(message.toString(), seconds);
        }
    }

    /**
     * Displays a top-right success popup notification with the given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void popupSuccess(String message, double seconds) {
        popup(message, seconds, new Color(20, 200, 50), Color.WHITE);
    }

    /**
     * Displays a top-right popup notification for a default duration.
     * 
     * @param message the message object to display
     */
    public static void popup(Object message) {
        popup(message, 3.0);
    }

    /**
     * Displays a top-right popup notification for a default duration.
     * 
     * @param message the message to display
     */
    public static void popup(String message) {
        popup(message, 3.0);
    }

    /**
     * Displays a top-right popup notification with the given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void popup(Object message, double seconds) {
        if (message != null) {
            popup(message.toString(), seconds);
        }
    }

    /**
     * Displays a top-right popup notification with the given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void popup(String message, double seconds) {
        popup(message, seconds, new Color(250, 250, 250), Color.BLACK);
    }

    /**
     * Displays a popup notification in the top-right corner with custom background
     * and text colors.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     * @param bgColor the background color
     * @param fgColor the text color
     */
    public static void popup(String message, double seconds, Color bgColor, Color fgColor) {
        if (message == null || message.isBlank()) {
            return;
        }
        log.info("POPUP: " + message);
        var frame = createFrame();
        var panelBody = new JPanel(new BorderLayout());
        panelBody.setBackground(bgColor);
        panelBody.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, false));
        frame.setContentPane(panelBody);
        var label = new javax.swing.JLabel(message);
        label.setForeground(fgColor);
        label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panelBody.add(label, BorderLayout.CENTER);
        frame.pack();
        var bounds = getScreenBounds();
        frame.setBounds(bounds.x + bounds.width - frame.getWidth() - 30, bounds.y + 50, frame.getWidth(),
                frame.getHeight());
        displayAndClose(frame, "SwingNotify Popup Closer", seconds, null);
    }

    // --- Overlay Methods ---

    /**
     * Displays a full-screen semi-transparent overlay informational notification.
     * 
     * @param message the message object to display
     */
    public static void overlayInfo(Object message) {
        overlayInfo(message, 4.0);
    }

    /**
     * Displays a full-screen semi-transparent overlay informational notification.
     * 
     * @param message the message to display
     */
    public static void overlayInfo(String message) {
        overlayInfo(message, 4.0);
    }

    /**
     * Displays a full-screen semi-transparent overlay informational notification
     * with the given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void overlayInfo(Object message, double seconds) {
        if (message != null) {
            overlayInfo(message.toString(), seconds);
        }
    }

    /**
     * Displays a full-screen semi-transparent overlay informational notification
     * with the given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void overlayInfo(String message, double seconds) {
        overlay(message, seconds, new Color(20, 100, 200, 200), Color.WHITE);
    }

    /**
     * Displays a full-screen semi-transparent overlay warning notification.
     * 
     * @param message the message object to display
     */
    public static void overlayWarning(Object message) {
        overlayWarning(message, 5.0);
    }

    /**
     * Displays a full-screen semi-transparent overlay warning notification.
     * 
     * @param message the message to display
     */
    public static void overlayWarning(String message) {
        overlayWarning(message, 5.0);
    }

    /**
     * Displays a full-screen semi-transparent overlay warning notification with the
     * given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void overlayWarning(Object message, double seconds) {
        if (message != null) {
            overlayWarning(message.toString(), seconds);
        }
    }

    /**
     * Displays a full-screen semi-transparent overlay warning notification with the
     * given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void overlayWarning(String message, double seconds) {
        overlay(message, seconds, new Color(220, 140, 20, 200), Color.WHITE);
    }

    /**
     * Displays a full-screen semi-transparent overlay error notification.
     * 
     * @param message the message object to display
     */
    public static void overlayError(Object message) {
        overlayError(message, 6.0);
    }

    /**
     * Displays a full-screen semi-transparent overlay error notification.
     * 
     * @param message the message to display
     */
    public static void overlayError(String message) {
        overlayError(message, 6.0);
    }

    /**
     * Displays a full-screen semi-transparent overlay error notification with the
     * given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void overlayError(Object message, double seconds) {
        if (message != null) {
            overlayError(message.toString(), seconds);
        }
    }

    /**
     * Displays a full-screen semi-transparent overlay error notification with the
     * given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void overlayError(String message, double seconds) {
        overlay(message, seconds, new Color(200, 20, 20, 200), Color.WHITE);
    }

    /**
     * Displays a full-screen semi-transparent overlay success notification.
     * 
     * @param message the message object to display
     */
    public static void overlaySuccess(Object message) {
        overlaySuccess(message, 4.0);
    }

    /**
     * Displays a full-screen semi-transparent overlay success notification.
     * 
     * @param message the message to display
     */
    public static void overlaySuccess(String message) {
        overlaySuccess(message, 4.0);
    }

    /**
     * Displays a full-screen semi-transparent overlay success notification with the
     * given duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void overlaySuccess(Object message, double seconds) {
        if (message != null) {
            overlaySuccess(message.toString(), seconds);
        }
    }

    /**
     * Displays a full-screen semi-transparent overlay success notification with the
     * given duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void overlaySuccess(String message, double seconds) {
        overlay(message, seconds, new Color(20, 200, 50, 200), Color.WHITE);
    }

    /**
     * Displays a full-screen semi-transparent overlay notification for a default
     * duration.
     * 
     * @param message the message object to display
     */
    public static void overlay(Object message) {
        overlay(message, 4.0);
    }

    /**
     * Displays a full-screen semi-transparent overlay notification for a default
     * duration.
     * 
     * @param message the message to display
     */
    public static void overlay(String message) {
        overlay(message, 4.0);
    }

    /**
     * Displays a full-screen semi-transparent overlay notification with the given
     * duration.
     * 
     * @param message the message object to display
     * @param seconds the duration in seconds
     */
    public static void overlay(Object message, double seconds) {
        if (message != null) {
            overlay(message.toString(), seconds);
        }
    }

    /**
     * Displays a full-screen semi-transparent overlay notification with the given
     * duration.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     */
    public static void overlay(String message, double seconds) {
        overlay(message, seconds, new Color(0, 0, 0, 220), Color.WHITE);
    }

    /**
     * Displays a full-screen overlay notification with custom background and text
     * colors.
     * 
     * @param message the message to display
     * @param seconds the duration in seconds
     * @param bgColor the background color (should typically be semi-transparent)
     * @param fgColor the text color
     */
    public static void overlay(String message, double seconds, Color bgColor, Color fgColor) {
        if (message == null || message.isBlank()) {
            return;
        }
        log.info("OVERLAY: " + message);
        var frame = createFrame();
        try {
            frame.setBackground(bgColor);
        } catch (Exception e) {
            frame.getContentPane().setBackground(Color.DARK_GRAY);
        }
        var label = new javax.swing.JLabel(message, javax.swing.SwingConstants.CENTER);
        label.setForeground(fgColor);
        var baseFont = WizGUI.getFont();
        label.setFont(baseFont.deriveFont(baseFont.getSize() * 3.0f));
        frame.add(label);
        var bounds = getScreenBounds();
        frame.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        // Using custom thread to avoid displayAndClose which overrides our custom big
        // font!
        frame.setVisible(true);
        new Thread("SwingNotify Overlay Closer") {
            @Override
            public void run() {
                int totalSteps = (int) (seconds * 100);
                for (int i = 0; i < totalSteps; i++) {
                    WizThread.sleep(10);
                    if (!frame.isVisible())
                        break;
                }
                SwingUtilities.invokeLater(() -> WizGUI.close(frame));
            }
        }.start();
    }

    /**
     * Creates the base JFrame configuration used by all notifications.
     * 
     * @return a configured, undecorated JFrame
     */
    private static JFrame createFrame() {
        var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setFocusable(false);
        return frame;
    }

    /**
     * Helper to get the screen bounds of the monitor where the mouse is currently
     * located.
     * 
     * @return a Rectangle representing the screen bounds
     */
    private static java.awt.Rectangle getScreenBounds() {
        return WizGUI.getScreenWithMouse().getDefaultConfiguration().getBounds();
    }

    /**
     * Displays the configured frame and starts the background thread to
     * automatically close it.
     * 
     * @param frame        the JFrame to display
     * @param threadName   the name for the closer thread
     * @param seconds      the time before closing
     * @param viewProgress an optional progress bar to animate
     */
    private static void displayAndClose(JFrame frame, String threadName, double seconds, JProgressBar viewProgress) {
        frame.setVisible(true);
        WizGUI.setAllComponentsFont(frame, WizGUI.getFont());
        new Thread(threadName) {
            @Override
            public void run() {
                int totalSteps = (int) (seconds * 100);
                for (int i = 0; i < totalSteps; i++) {
                    WizThread.sleep(10);
                    if (!frame.isVisible())
                        break;
                    if (viewProgress != null) {
                        SwingUtilities.invokeLater(() -> {
                            viewProgress.setValue(viewProgress.getValue() + 1);
                        });
                    }
                }
                SwingUtilities.invokeLater(() -> WizGUI.close(frame));
            }
        }.start();
    }

}
