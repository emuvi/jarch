package br.com.pointel.jarch.desk;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import br.com.pointel.jarch.mage.WizGUI;

public class DListDesk<T> extends DFrame {

    private final DefaultListModel<T> modelValues = new DefaultListModel<>();
    private final JList<T> listValues = new JList<>(modelValues);
    private final JScrollPane scrollList = new JScrollPane(listValues);

    private final DButton buttonSelect = new DButton("Select")
            .onClick(this::select);
    private final DButton buttonCancel = new DButton("Cancel")
            .onClick(this::cancel);

    private final DPane actsBody = new DRowPane().insets(2)
            .growHorizontal().put(Box.createHorizontalGlue())
            .growNone().put(buttonSelect)
            .growNone().put(buttonCancel);

    private final DPane paneBody = new DColPane().insets(2)
            .growBoth().put(scrollList)
            .growHorizontal().put(actsBody)
            .borderEmpty(7);

    private Consumer<T> onSelect;

    public DListDesk() {
        this("Select Desk", null, null);
    }

    public DListDesk(String title) {
        this(title, null, null);
    }

    public DListDesk(List<T> options) {
        this("Select Desk", options, null);
    }

    public DListDesk(Consumer<T> onSelect) {
        this("Select Desk", null, onSelect);
    }

    public DListDesk(String title, List<T> options) {
        this(title, options, null);
    }

    public DListDesk(String title, Consumer<T> onSelect) {
        this(title, null, onSelect);
    }

    public DListDesk(List<T> options, Consumer<T> onSelect) {
        this("Select Desk", options, onSelect);
    }

    public DListDesk(String title, List<T> options, Consumer<T> onSelect) {
        super(title);
        this.onSelect = onSelect;
        initComponents(options);
    }
    
    private void initComponents(List<T> options) {
        body(paneBody);
        if (options != null) {
            for (var option : options) {
                modelValues.addElement(option);
            }
        }
        listValues.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    select(new ActionEvent(listValues, ActionEvent.ACTION_PERFORMED, "select"));
                }
            }
        });
        listValues.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
                    select(new ActionEvent(listValues, ActionEvent.ACTION_PERFORMED, "select"));
                }
            }
        });
    }

    public DListDesk<T> putButton(DButton button) {
        actsBody.put(button);
        return this;
    }

    public DListDesk<T> delButtons() {
        actsBody.removeAll();
        return this;
    }

    public List<T> options() {
        var result = new ArrayList<T>(modelValues.getSize());
        for (int i = 0; i < modelValues.getSize(); i++) {
            result.add(modelValues.getElementAt(i));
        }
        return result;
    }

    public DListDesk<T> options(List<T> options) {
        modelValues.clear();
        if (options != null) {
            for (var option : options) {
                modelValues.addElement(option);
            }
        }
        return this;
    }

    public DListDesk<T> addOption(T option) {
        modelValues.addElement(option);
        return this;
    }

    public DListDesk<T> delOption(T option) {
        modelValues.removeElement(option);
        return this;
    }

    public DListDesk<T> onSelect(Consumer<T> onSelect) {
        this.onSelect = onSelect;
        return this;
    }

    public T selected() {
        return listValues.getSelectedValue();
    }

    public DListDesk<T> select(T option) {
        listValues.setSelectedValue(option, true);
        return this;
    }

    private void select(ActionEvent e) {
        var selected = listValues.getSelectedValue();
        if (selected == null) {
            return;
        }
        if (onSelect != null) {
            onSelect.accept(selected);
            WizGUI.close(this);
        }
    }

    private void cancel(ActionEvent e) {
        WizGUI.close(this);
    }

    @Override
    public DListDesk<T> view() {
        setVisible(true);
        return this;
    }

}
