package br.com.pointel.jarch.desk;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class DButton extends JButton {
    
    public DButton() {
        super();
    }

    public DButton(String text) {
        super(text);
    }

    public DButton onClick(ActionListener listener) {
        addActionListener(listener);
        return this;
    }

    public DButton text(String text) {
        setText(text);
        return this;
    }

    public DButton enabled(boolean enabled) {
        setEnabled(enabled);
        return this;
    }
    
}
