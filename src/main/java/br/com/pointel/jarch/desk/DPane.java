package br.com.pointel.jarch.desk;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.awt.LayoutManager;

public class DPane extends JPanel {

    public DPane() {
    }

    public DPane(LayoutManager layout) {
        super(layout);
    }

    public DPane borderEmpty(int size) {
        setBorder(BorderFactory.createEmptyBorder(size, size, size, size));
        return this;
    }

}
