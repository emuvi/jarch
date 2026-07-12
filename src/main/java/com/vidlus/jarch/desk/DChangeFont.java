package com.vidlus.jarch.desk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class DChangeFont extends DEditChange<Font> {

    private Font currentFont = new JLabel().getFont();

    public DChangeFont() {
        super("F");
    }

    @Override
    public Font getValue() {
        return currentFont;
    }

    @Override
    public void setValue(Font value) {
        this.currentFont = value == null ? new JLabel().getFont() : value;
        getField().setText(currentFont.getFamily() + ", " + currentFont.getSize() + "pt");
        getField().setFont(currentFont);
    }

    @Override
    protected void onActionPressed() {
        if (!editable()) return;
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JList<String> fontList = new JList<>(fonts);
        fontList.setSelectedValue(currentFont.getFamily(), true);
        
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(currentFont.getSize(), 8, 72, 1));
        
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JScrollPane(fontList), BorderLayout.CENTER);
        panel.add(sizeSpinner, BorderLayout.SOUTH);
        panel.setPreferredSize(new Dimension(250, 300));
        
        DAlert alert = new DAlert()
            .parent(comp())
            .title("Select Font")
            .message(panel)
            .plain()
            .okCancel();
            
        if (alert.confirm() == JOptionPane.OK_OPTION) {
            String family = fontList.getSelectedValue();
            if (family == null) family = currentFont.getFamily();
            int size = (Integer) sizeSpinner.getValue();
            setValue(new Font(family, currentFont.getStyle(), size));
        }
    }
}
