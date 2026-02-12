package br.com.pointel.jarch.desk;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import br.com.pointel.jarch.mage.WizApp;
import br.com.pointel.jarch.mage.WizGUI;
import br.com.pointel.jarch.mage.WizString;

public class DFrame extends JFrame {

    public DFrame() {
        this(WizApp.getTitle());
    }

    public DFrame(String title) {
        this(title, null);
    }

    public DFrame(String title, Container contentPane) {
        super(title);
        setName(WizString.getParameterName(title));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(WizGUI.getLogo());
        setLocationRelativeTo(null);
        if (contentPane != null) {
            setContentPane(contentPane);
            pack();
        }
        WizGUI.initFrame(this);
    }

    public DFrame exitOnClose() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return this;
    }

    public DFrame body(Container body) {
        setContentPane(body);
        pack();
        return this;
    }

}
