package br.com.pointel.jarch.desk;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.Box;
import br.com.pointel.jarch.mage.WizGUI;

public class DTextDesk extends DFrame {

    private final DText textValue = new DText();
    private final DScroll scrollText = new DScroll(textValue);

    private final DButton buttonConfirm = new DButton("Confirm")
            .onAction(this::confirm);
    private final DButton buttonCancel = new DButton("Cancel")
            .onAction(this::cancel);

    private final DPane actsBody = new DRowPane().insets(2)
            .growHorizontal().put(Box.createHorizontalGlue())
            .growNone().put(buttonConfirm)
            .growNone().put(buttonCancel);

    private final DPane paneBody = new DColPane().insets(2)
            .growBoth().put(scrollText)
            .growHorizontal().put(actsBody)
            .borderEmpty(7);

    private Consumer<String> onConfirm;

    public DTextDesk() {
        this("Text Desk", null, null);
    }

    public DTextDesk(String title) {
        this(title, null, null);
    }

    public DTextDesk(String title, String initialText) {
        this(title, initialText, null);
    }

    public DTextDesk(Consumer<String> onConfirm) {
        this("Text Desk", null, onConfirm);
    }

    public DTextDesk(String title, Consumer<String> onConfirm) {
        this(title, null, onConfirm);
    }

    public DTextDesk(String title, String initialText, Consumer<String> onConfirm) {
        super(title);
        this.onConfirm = onConfirm;
        initComponents(initialText);
    }
    
    private void initComponents(String initialText) {
        body(paneBody);
        if (initialText != null) {
            textValue.value(initialText);
        }
    }

    public DTextDesk putButton(DButton button) {
        actsBody.put(button);
        return this;
    }

    public DTextDesk delButtons() {
        actsBody.removeAll();
        return this;
    }

    public String text() {
        return textValue.value();
    }

    public DTextDesk text(String text) {
        textValue.value(text);
        return this;
    }

    public boolean editable() {
        return textValue.editable();
    }

    public DTextDesk editable(boolean editable) {
        textValue.editable(editable);
        return this;
    }

    public DTextDesk onConfirm(Consumer<String> onConfirm) {
        this.onConfirm = onConfirm;
        return this;
    }

    private void confirm(ActionEvent e) {
        var text = textValue.value();
        if (text == null) {
            return;
        }
        if (onConfirm != null) {
            onConfirm.accept(text);
            WizGUI.close(this);
        }
    }

    private void cancel(ActionEvent e) {
        WizGUI.close(this);
    }

    @Override
    public DTextDesk view() {
        setVisible(true);
        textValue.selectionStart(0);
        textValue.selectionEnd(0);
        textValue.requestFocusInWindow();
        textValue.requestFocus();
        return this;
    }

}
