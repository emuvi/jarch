package br.com.pointel.jarch.desk;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import br.com.pointel.jarch.mage.WizDesk;
import br.com.pointel.jarch.mage.WizString;

public class DFrame extends JFrame {

    public DFrame(String title) {
        this(title, null);
    }

    public DFrame(String title, Container contentPane) {
        super(title);
        setName(WizString.getParameterName(title));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(WizDesk.getLogo());
        setLocationRelativeTo(null);
        if (contentPane != null) {
            setContentPane(contentPane);
            pack();
        }
        WizDesk.initFrame(this);
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
