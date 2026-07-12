package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;

/**
 * A UI component for editing and selecting a password.
 * Provides a masked password field and a button that reveals the password when held.
 */
public class DChangePass extends DEditChange<String> {

    /**
     * Constructs a new DChangePass component.
     * Replaces the default text field with a JPasswordField and sets up a mouse listener 
     * on the action button to toggle password visibility.
     */
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
                if (!editable()) return;
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

    /**
     * Retrieves the entered password string.
     * 
     * @return the password String
     */
    @Override
    public String getValue() {
        return new String(((JPasswordField) field).getPassword());
    }

    /**
     * Sets the password value.
     * 
     * @param value the password String to set
     */
    @Override
    public void setValue(String value) {
        field.setText(value == null ? "" : value);
    }

    /**
     * Handled internally by a MouseListener to show/hide the password.
     */
    @Override
    protected void onActionPressed() {
        // Handled by MouseListener
    }

    /**
     * Fluent setter for the password value.
     * 
     * @param pass the password String to set
     * @return this DChangePass instance
     */
    public DChangePass pass(String pass) {
        setValue(pass);
        return this;
    }
}
