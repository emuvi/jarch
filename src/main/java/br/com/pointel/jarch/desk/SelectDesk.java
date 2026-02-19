package br.com.pointel.jarch.desk;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.com.pointel.jarch.mage.WizGUI;

public class SelectDesk<T> extends DFrame {

    private final DefaultListModel<T> modelLastSelected = new DefaultListModel<>();
    private final JList<T> listLastSelected = new JList<>(modelLastSelected);
    private final JScrollPane scrollLastSelected = new JScrollPane(listLastSelected);

    private final DButton buttonSelect = new DButton("Select")
            .onClick(this::select);
    private final DButton buttonCancel = new DButton("Cancel")
            .onClick(this::cancel);

    private final DPane actsBody = new DRowPane().insets(2)
            .growNone().put(buttonSelect)
            .growNone().put(buttonCancel)
            .growHorizontal().put(Box.createHorizontalGlue());

    private final DPane paneBody = new DColPane().insets(2)
            .growBoth().put(scrollLastSelected)
            .growHorizontal().put(actsBody)
            .borderEmpty(7);

    private Consumer<T> onSelect;

    public SelectDesk() {
        this("Select Desk", null, null);
    }

    public SelectDesk(String title) {
        this(title, null, null);
    }

    public SelectDesk(List<T> options) {
        this("Select Desk", options, null);
    }

    public SelectDesk(Consumer<T> onSelect) {
        this("Select Desk", null, onSelect);
    }

    public SelectDesk(String title, List<T> options) {
        this(title, options, null);
    }

    public SelectDesk(String title, Consumer<T> onSelect) {
        this(title, null, onSelect);
    }

    public SelectDesk(List<T> options, Consumer<T> onSelect) {
        this("Select Desk", options, onSelect);
    }

    public SelectDesk(String title, List<T> options, Consumer<T> onSelect) {
        super(title);
        this.onSelect = onSelect;
        initComponents(options);
    }
    
    private void initComponents(List<T> options) {
        body(paneBody);
        if (options != null) {
            for (var option : options) {
                modelLastSelected.addElement(option);
            }
        }
        listLastSelected.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    select(new ActionEvent(listLastSelected, ActionEvent.ACTION_PERFORMED, "select"));
                }
            }
        });
        listLastSelected.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2) {
                    select(new ActionEvent(listLastSelected, ActionEvent.ACTION_PERFORMED, "select"));
                }
            }
        });
    }

    public List<T> options() {
        var result = new ArrayList<T>(modelLastSelected.getSize());
        for (int i = 0; i < modelLastSelected.getSize(); i++) {
            result.add(modelLastSelected.getElementAt(i));
        }
        return result;
    }

    public SelectDesk<T> options(List<T> options) {
        modelLastSelected.clear();
        if (options != null) {
            for (var option : options) {
                modelLastSelected.addElement(option);
            }
        }
        return this;
    }

    public SelectDesk<T> addOption(T option) {
        modelLastSelected.addElement(option);
        return this;
    }

    public SelectDesk<T> delOption(T option) {
        modelLastSelected.removeElement(option);
        return this;
    }

    public SelectDesk<T> onSelect(Consumer<T> onSelect) {
        this.onSelect = onSelect;
        return this;
    }

    public T selected() {
        return listLastSelected.getSelectedValue();
    }

    public SelectDesk<T> select(T option) {
        listLastSelected.setSelectedValue(option, true);
        return this;
    }

    private void select(ActionEvent e) {
        var selected = listLastSelected.getSelectedValue();
        if (selected == null) {
            return;
        }
        if (onSelect != null) {
            onSelect.accept(selected);
        }
        WizGUI.close(this);
    }

    private void cancel(ActionEvent e) {
        WizGUI.close(this);
    }

}
