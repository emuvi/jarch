package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;

public class DChangePass extends DEditChange<String> {

    public DChangePass() {
        super("👁");
        
        panel.remove(field);
        
        JPasswordField passField = new JPasswordField();
        passField.setBorder(field.getBorder());
        passField.setBackground(field.getBackground());
        passField.setOpaque(field.isOpaque());
        passField.setColumns(field.getColumns());
        
        this.field = passField;
        panel.add(field, BorderLayout.CENTER);
        
        if (actionButton.getActionListeners().length > 0) {
            actionButton.removeActionListener(actionButton.getActionListeners()[0]);
        }
        
        actionButton.addMouseListener(new MouseAdapter() {
            private char defaultEchoChar = passField.getEchoChar();
            
            @Override
            public void mousePressed(MouseEvent e) {
                passField.setEchoChar((char) 0);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                passField.setEchoChar(defaultEchoChar);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                passField.setEchoChar(defaultEchoChar);
            }
        });
    }

    @Override
    public String getValue() {
        return new String(((JPasswordField) field).getPassword());
    }

    @Override
    public void setValue(String value) {
        field.setText(value == null ? "" : value);
    }

    @Override
    protected void onActionPressed() {
        // Handled by MouseListener
    }
}
