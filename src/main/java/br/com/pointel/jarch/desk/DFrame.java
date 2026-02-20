package br.com.pointel.jarch.desk;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
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

    public DFrame body(Container body) {
        setContentPane(body);
        pack();
        return this;
    }

    public String title() {
        return super.getTitle();
    }

    public DFrame title(String title) {
        super.setTitle(title);
        return this;
    }

    public Dimension dimension() {
        return super.getSize();
    }

    public DFrame dimension(int width, int height) {
        super.setSize(width, height);
        return this;
    }

    public boolean visible() {
        return super.isVisible();
    }

    public DFrame visible(boolean visible) {
        super.setVisible(visible);
        return this;
    }

    public boolean resizable() {
        return super.isResizable();
    }

    public DFrame resizable(boolean resizable) {
        super.setResizable(resizable);
        return this;
    }

    public boolean undecorated() {
        return super.isUndecorated();
    }

    public DFrame undecorated(boolean undecorated) {
        super.setUndecorated(undecorated);
        return this;
    }

    public boolean alwaysOnTop() {
        return super.isAlwaysOnTop();
    }

    public DFrame alwaysOnTop(boolean alwaysOnTop) {
        super.setAlwaysOnTop(alwaysOnTop);
        return this;
    }

    public Point position() {
        return super.getLocation();
    }

    public DFrame position(int x, int y) {
        super.setLocation(x, y);
        return this;
    }

    public DFrame position(Point p) {
        super.setLocation(p);
        return this;
    }

    public Rectangle place() {
        return super.getBounds();
    }

    public DFrame place(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        return this;
    }

    public DFrame place(Rectangle r) {
        super.setBounds(r);
        return this;
    }

    public int state() {
        return super.getExtendedState();
    }

    public DFrame state(int state) {
        super.setExtendedState(state);
        return this;
    }

    public DFrame stateNormal() {
        return state(JFrame.NORMAL);
    }

    public DFrame stateIconified() {
        return state(JFrame.ICONIFIED);
    }

    public DFrame stateMaximizedHorizontal() {
        return state(JFrame.MAXIMIZED_HORIZ);
    }

    public DFrame stateMaximizedVertical() {
        return state(JFrame.MAXIMIZED_VERT);
    }

    public DFrame stateMaximizedBoth() {
        return state(JFrame.MAXIMIZED_BOTH);
    }

    public int defaultCloseOperation() {
        return super.getDefaultCloseOperation();
    }

    public DFrame defaultCloseOperation(int operation) {
        super.setDefaultCloseOperation(operation);
        return this;
    }

    public DFrame doNothingOnClose() {
        super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        return this;
    }

    public DFrame hideOnClose() {
        super.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        return this;
    }

    public DFrame disposeOnClose() {
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        return this;
    }

    public DFrame exitOnClose() {
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return this;
    }

    public Image iconImage() {
        return super.getIconImage();
    }

    public DFrame iconImage(Image image) {
        super.setIconImage(image);
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

    public DFrame onFirstOpened(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            boolean firstOpened = true;
            @Override
            public void windowOpened(WindowEvent e) {
                if (firstOpened) {
                    firstOpened = false;
                } else {
                    return;
                }
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

    public DFrame onFirstActivated(Consumer<WindowEvent> consumer) {
        addWindowListener(new WindowAdapter() {
            boolean firstActivated = true;
            @Override
            public void windowActivated(WindowEvent e) {
                if (firstActivated) {
                    firstActivated = false;
                } else {
                    return;
                }
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
