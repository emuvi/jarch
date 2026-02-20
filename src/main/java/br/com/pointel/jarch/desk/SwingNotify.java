package br.com.pointel.jarch.desk;

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

import br.com.pointel.jarch.mage.WizGUI;
import br.com.pointel.jarch.mage.WizThread;

public class SwingNotify {

    private static final Logger logger = LoggerFactory.getLogger(SwingNotify.class);
    
    public static void show(Object message, double seconds) {
        if (message == null) {
            return;
        }
        var messageStr = message.toString();
        if (messageStr.isBlank()) {
            return;
        }
        show(message.toString(), seconds);
    }

    public static void show(String message, double seconds) {
        if (message == null) {
            return;
        }
        logger.info(message);
        var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        var panelBody = new JPanel(new BorderLayout());
        panelBody.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
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
        var viewProgress = new JProgressBar(0, (int)(seconds * 100));
        viewProgress.setValue(viewProgress.getMinimum());
        viewProgress.setFocusable(false);
        panelBody.add(viewProgress, BorderLayout.SOUTH);
        frame.pack();
        frame.setFocusable(false);
        var bounds = WizGUI.getScreenWithMouse().getDefaultConfiguration().getBounds();
        frame.setBounds(bounds.x + bounds.width - 400, bounds.y + 30, 360, 80);
        frame.setVisible(true);
        WizGUI.setAllComponentsFont(frame, WizGUI.getFont());
        new Thread("SwingNotify Closer") {
            @Override
            public void run() {
                while (frame.isVisible()) {
                    WizThread.sleep(10);
                    SwingUtilities.invokeLater(() -> {
                        viewProgress.setValue(viewProgress.getValue() + 1);
                        if (viewProgress.getValue() == viewProgress.getMaximum()) {
                            WizGUI.close(frame);
                        }
                    });
                }
            }
        }.start();
    }

}
