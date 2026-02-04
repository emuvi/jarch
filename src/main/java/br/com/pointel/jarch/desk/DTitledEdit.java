package br.com.pointel.jarch.desk;

import java.awt.BorderLayout;

import javax.swing.JLabel;

public class DTitledEdit<T> extends DPane {

    private final JLabel label = new JLabel();
    private final DEdit<T> edit;

    public DTitledEdit(String title, DEdit<T> edit) {
        super(new BorderLayout(2, 2));
        this.edit = edit;
        this.label.setText(title);
        add(this.label, BorderLayout.NORTH);
        add(edit.comp(), BorderLayout.CENTER);
    }

    public JLabel label() {
        return label;
    }

    public DEdit<T> edit() {
        return edit;
    }

}
