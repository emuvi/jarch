package br.com.pointel.jarch.desk;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

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

    public DFrame onOpened(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DFrame onClosing(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DFrame onClosed(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DFrame onIconified(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DFrame onDeiconified(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeiconified(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DFrame onActivated(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DFrame onDeactivated(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DFrame onStateChanged(Consumer<WindowEvent> consumer) {
        addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DFrame onGainedFocus(Consumer<WindowEvent> consumer) {
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

    public DFrame onLostFocus(Consumer<WindowEvent> consumer) {
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

}
