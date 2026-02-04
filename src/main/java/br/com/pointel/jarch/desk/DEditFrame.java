package br.com.pointel.jarch.desk;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.pointel.jarch.mage.WizDesk;

public class DEditFrame<T> extends DFrame implements DValue<T> {

    private final JPanel panelBody = new JPanel(new BorderLayout(2, 2));
    private final JPanel panelAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 2));
    private final JButton buttonConfirm = new JButton("Confirm");
    private final JButton buttonCancel = new JButton("Cancel");

    private DEdit<T> edit;
    private Consumer<T> onConfirm;

    public DEditFrame(String title) {
        this(title, null, null);
    }

    public DEditFrame(String title, DEdit<T> edit) {
        this(title, edit, null);
    }

    public DEditFrame(String title, Consumer<T> onConfirm) {
        this(title, null, onConfirm);
    }

    public DEditFrame(String title, DEdit<T> edit, Consumer<T> onConfirm) {
        super(title);
        this.edit = edit;
        this.onConfirm = onConfirm;
        buttonConfirm.addActionListener(this::actConfirm);
        buttonCancel.addActionListener(this::actCancel);
        if (edit != null) {
            panelBody.add(edit.comp(), BorderLayout.CENTER);
        }
        panelAction.add(buttonConfirm);
        panelAction.add(buttonCancel);
        panelBody.add(panelAction, BorderLayout.SOUTH);
        panelBody.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
        body(panelBody);
    }

    @Override
    public T getValue() {
        return edit.getValue();
    }

    @Override
    public void setValue(T value) {
        edit.setValue(value);
    }

    public DEdit<T> edit() {
        return edit;
    }

    public DEditFrame<T> edit(DEdit<T> edit) {
        this.edit = edit;
        if (edit != null) {
            panelBody.add(edit.comp(), BorderLayout.CENTER);
        }
        return this;
    }

    public Consumer<T> onConfirm() {
        return onConfirm;
    }

    public DEditFrame<T> onConfirm(Consumer<T> onConfirm) {
        this.onConfirm = onConfirm;
        return this;
    }

    private void actConfirm(ActionEvent event) {
        if (onConfirm != null) {
            onConfirm.accept(edit.getValue());
        }
        WizDesk.close(this);
    }

    private void actCancel(ActionEvent event) {
        WizDesk.close(this);
    }

}
