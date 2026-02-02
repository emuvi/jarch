package br.com.pointel.jarch.desk;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.awt.LayoutManager;

public class DPanel extends JPanel {

    public DPanel() {
    }

    public DPanel(LayoutManager layout) {
        super(layout);
    }

    public DPanel borderEmpty(int size) {
        setBorder(BorderFactory.createEmptyBorder(size, size, size, size));
        return this;
    }

}
