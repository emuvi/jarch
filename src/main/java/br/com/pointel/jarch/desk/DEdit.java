package br.com.pointel.jarch.desk;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.JComponent;

public abstract class DEdit<T> implements DValue<T> {

    private JComponent comp;

    public DEdit() {
        this.comp = null;
    }

    public DEdit(JComponent comp) {
        this.comp = comp;
    }

    public JComponent comp() {
        return comp;
    }

    public DEdit<T> comp(JComponent comp) {
        this.comp = comp;
        return this;
    }

    public T value() {
        return getValue();
    }

    public DEdit<T> value(T value) {
        setValue(value);
        return this;
    }

    public boolean enabled() {
        return comp.isEnabled();
    }

    public DEdit<T> enabled(boolean enabled) {
        comp.setEnabled(enabled);
        return this;
    }

    public boolean focusable() {
        return comp.isFocusable();
    }

    public DEdit<T> focusable(boolean focusable) {
        comp.setFocusable(focusable);
        return this;
    }

    public DEdit<T> requestFocus() {
        comp.requestFocus();
        return this;
    }

    public DEdit<T> requestFocusInWindow() {
        comp.requestFocusInWindow();
        return this;
    }

    public String name() {
        return comp.getName();
    }

    public DEdit<T> name(String name) {
        comp.setName(name);
        return this;
    }

    public String hint() {
        return comp.getToolTipText();
    }

    public DEdit<T> hint(String hint) {
        comp.setToolTipText(hint);
        return this;
    }

    public DEdit<T> onAction(Consumer<ActionEvent> consumer) {
         var listenerMouse = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    consumer.accept(new ActionEvent(comp, ActionEvent.ACTION_PERFORMED, "double-click"));
                }
            }
        };
        var listenerKeyboard = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
                    consumer.accept(new ActionEvent(comp, ActionEvent.ACTION_PERFORMED, "enter-key"));
                }
            }
        };
        comp.addMouseListener(listenerMouse);
        comp.addKeyListener(listenerKeyboard);
        return this;
    }

    public DEdit<T> onMouseClicked(Consumer<MouseEvent> consumer) {
        var listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        };
        comp.addMouseListener(listener);
        return this;
    }

    public DEdit<T> onMousePressed(Consumer<MouseEvent> consumer) {
        var listener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                consumer.accept(e);
            }
        };
        comp.addMouseListener(listener);
        return this;
    }

    public DEdit<T> onMouseReleased(Consumer<MouseEvent> consumer) {
        var listener = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                consumer.accept(e);
            }
        };
        comp.addMouseListener(listener);
        return this;
    }

    public DEdit<T> onMouseEntered(Consumer<MouseEvent> consumer) {
        var listener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                consumer.accept(e);
            }
        };
        comp.addMouseListener(listener);
        return this;
    }

    public DEdit<T> onMouseExited(Consumer<MouseEvent> consumer) {
        var listener = new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                consumer.accept(e);
            }
        };
        comp.addMouseListener(listener);
        return this;
    }

    public DEdit<T> onKeyTyped(Consumer<KeyEvent> consumer) {
        var listener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                consumer.accept(e);
            }
        };
        comp.addKeyListener(listener);
        return this;
    }

    public DEdit<T> onKeyPressed(Consumer<KeyEvent> consumer) {
        var listener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                consumer.accept(e);
            }
        };
        comp.addKeyListener(listener);
        return this;
    }

    public DEdit<T> onKeyReleased(Consumer<KeyEvent> consumer) {
        var listener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                consumer.accept(e);
            }
        };
        comp.addKeyListener(listener);
        return this;
    }

    public DEdit<T> onFocusGained(Consumer<FocusEvent> consumer) {
        comp().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                consumer.accept(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        return this;
    }

    public DEdit<T> onFocusLost(Consumer<FocusEvent> consumer) {
        comp().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                consumer.accept(e);
            }
        });
        return this;
    }

}
