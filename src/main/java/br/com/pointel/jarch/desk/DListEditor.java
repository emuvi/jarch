package br.com.pointel.jarch.desk;

import java.util.List;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.pointel.jarch.mage.WizDesk;

public class DListEditor<T> extends DEdit<List<T>> {

    private final JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
    private final JButton buttonAdd = new JButton("+");
    private final JButton buttonDel = new JButton("-");
    private final JButton buttonEdit = new JButton("&");
    
    private final DListEdit<T> list = new DListEdit<>();
    private final DScroll scroll = new DScroll(list);

    private final Class<? extends DEditFrame<T>> editFrameClass;

    public DListEditor(Class<? extends DEditFrame<T>> editFrameClass) {
        super(new JPanel(new BorderLayout(2, 2)));
        comp().add(panelTop, BorderLayout.NORTH);
        panelTop.add(buttonAdd);
        panelTop.add(buttonDel);
        panelTop.add(buttonEdit);
        buttonAdd.addActionListener(this::actAdd);
        buttonDel.addActionListener(this::actDel);
        buttonEdit.addActionListener(this::actEdit);
        comp().add(scroll, BorderLayout.CENTER);
        this.editFrameClass = editFrameClass;
    }

    @Override
    public JPanel comp() {
        return (JPanel) super.comp();
    }

    @Override
    public List<T> getValue() {
        return list.getValue();
    }

    @Override
    public void setValue(List<T> value) {
        list.setValue(value);
    }

    private void actAdd(ActionEvent event) {
        try {
            var editFrame = editFrameClass.getConstructor().newInstance();
            editFrame.onConfirm(list::addAtSelection);
            editFrame.setVisible(true);
        } catch (Exception e) {
            WizDesk.showError(e);
        }
    }

    private void actDel(ActionEvent event) {
        try { 
            list.delAtSelection();
        } catch (Exception e) {
            WizDesk.showError(e);
        }
    }

    private void actEdit(ActionEvent event) {
        try { 
            int index = list.getSelectedIndex();
            if (index >= 0) {
                var editFrame = editFrameClass.getConstructor().newInstance();
                editFrame.setValue(list.getSelectedValue());
                editFrame.onConfirm(value -> list.set(index, value));
                editFrame.setVisible(true);
            }
        } catch (Exception e) {
            WizDesk.showError(e);
        }
    }

}
