package br.com.pointel.jarch.desk;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import br.com.pointel.jarch.mage.WizString;
import br.com.pointel.jarch.mage.WizGUI;
import br.com.pointel.jarch.mage.WizLang;
import br.com.pointel.jarch.mage.WizObject;
import br.com.pointel.jarch.mage.WizProps;

public class SwingFramer {

    private final JFrame frame;
    private final Font font;
    private final String rootName;
    private final JPopupMenu popMenu;

    public SwingFramer(JFrame frame, Font font) {
        this.frame = frame;
        this.font = font;
        this.rootName = "FRAME_" + WizString.getParameterName(!frame.getName().isEmpty() ? frame.getName() : frame.getTitle());
        this.popMenu = new JPopupMenu();
    }

    public SwingFramer init() {
        initWindow();
        initPopMenu();
        return this;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public String getRootName() {
        return this.rootName;
    }


    public JPopupMenu getPopMenu() {
        return this.popMenu;
    }

    private void initWindow() {
        frame.addWindowListener(new WindowAdapter() {
            
            boolean firstActivated = true;
            
            @Override
            public void windowOpened(WindowEvent e) {
                loadFrameProps();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                if (firstActivated) {
                    firstActivated = false;
                    loadFramePropsComps(frame);
                    WizGUI.setAllComponentsFont(frame, font);
                    WizGUI.setAllComponentsFont(frame.getContentPane(), font);
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(frame.getContentPane());
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                saveFrameProps();
                saveFramePropsComps(frame);
            }
        });
    }

    private void loadFrameProps() throws HeadlessException, SecurityException {
        var left = WizProps.get(rootName + "_LEFT", frame.getBounds().x);
        var top = WizProps.get(rootName + "_TOP", frame.getBounds().y);
        var width = WizProps.get(rootName + "_WIDTH", frame.getBounds().width);
        var height = WizProps.get(rootName + "_HEIGHT", frame.getBounds().height);
        var bounds = WizGUI.getBoundsInsideScreen(new Rectangle(left, top, width, height));
        frame.setBounds(bounds);
        frame.setAlwaysOnTop(WizProps.get(rootName + "_ON_TOP", frame.isAlwaysOnTop()));
    }

    public void loadFramePropsComps(Component component) {
        if (component instanceof Container container &&
                !WizLang.isChildOf(component.getClass(), JTextComponent.class, JComboBox.class, JSpinner.class, JCheckBox.class)
            ) {
            for (Component inside : container.getComponents()) {
                loadFramePropsComps(inside);
            }
        }
        if (component != null && component.getName() != null && !component.getName().isEmpty()) {
            var paramName =  rootName + "_COMP_" + WizString.getParameterName(component.getName());
            SwingUtilities.invokeLater(() -> {
                switch (component) {
                    case JTextComponent textField ->
                        textField.setText(WizProps.get(paramName, textField.getText()));
                    case JComboBox comboField -> {
                        if (comboField.isEditable()) {
                            var comboList = WizProps.get(paramName + "_LIST", "").split("\\-\\|\\-");
                            comboField.removeAllItems();
                            for (var listItem : comboList) {
                                comboField.addItem(listItem);
                            }
                            comboField.setSelectedItem(WizProps.get(paramName, ""));
                        } else {
                            comboField.setSelectedIndex(WizProps.get(paramName, comboField.getSelectedIndex()));
                        }
                    }
                    case JSpinner spinnerField ->
                        spinnerField.setValue(WizProps.get(paramName, (Integer) spinnerField.getValue()));
                    case JCheckBox checkField ->
                        checkField.setSelected(WizProps.get(paramName, checkField.isSelected()));
                    case JSplitPane splitPane -> {
                        var dividerLocation = WizProps.get(paramName, splitPane.getDividerLocation());
                        SwingUtilities.invokeLater(() -> splitPane.setDividerLocation(dividerLocation));
                    }
                    default -> {}
                }
            });
        }
    }

    private void saveFrameProps() {
        WizProps.set(rootName + "_LEFT", frame.getBounds().x);
        WizProps.set(rootName + "_TOP", frame.getBounds().y);
        WizProps.set(rootName + "_WIDTH", frame.getBounds().width);
        WizProps.set(rootName + "_HEIGHT", frame.getBounds().height);
        WizProps.set(rootName + "_ON_TOP", frame.isAlwaysOnTop());
    }

    public void saveFramePropsComps(Component component) {
        if (component != null && component.getName() != null && !component.getName().isEmpty()) {
            var paramName = rootName + "_COMP_" + WizString.getParameterName(component.getName());
            switch (component) {
                case JTextComponent textField ->
                    WizProps.set(paramName, textField.getText());
                case JComboBox comboField -> {
                    if (comboField.isEditable()) {
                        WizProps.set(paramName, WizObject.getFirstNonNull(comboField.getSelectedItem(), "").toString());
                        var comboList = new String[comboField.getModel().getSize()];
                        for (int i = 0; i < comboField.getModel().getSize(); i++) {
                            comboList[i] = WizObject.getFirstNonNull(comboField.getModel().getElementAt(i), "").toString();
                        }
                        WizProps.set(paramName + "_LIST", String.join("-|-", comboList));
                    } else {
                        WizProps.set(paramName, comboField.getSelectedIndex());
                    }
                }
                case JSpinner spinnerField ->
                    WizProps.set(paramName, (Integer) spinnerField.getValue());
                case JCheckBox checkField ->
                    WizProps.set(paramName, checkField.isSelected());
                case JSplitPane splitPane -> 
                    WizProps.set(paramName, splitPane.getDividerLocation());
                default -> {
                }
            }
        }
        if (component instanceof Container container &&
                !WizLang.isChildOf(component.getClass(), JTextComponent.class, JComboBox.class, JSpinner.class, JCheckBox.class)
        ) {
            for (Component inside : container.getComponents()) {
                saveFramePropsComps(inside);
            }
        }
    }

    private void initPopMenu() {
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popMenu.show(frame, e.getX(), e.getY());
                }
            }
        });
        createPopMenu();
    }

    private void createPopMenu() {
        createMenuSizes();
        WizGUI.addButton(popMenu, new JMenuItem("OnTop"), (e) -> menuOnTop());
        WizGUI.addButton(popMenu, new JMenuItem("Close"), (e) -> menuClose());
    }

    private void createMenuSizes() {
        var sizes = new JMenu("Sizes");
        WizGUI.addButton(sizes, new JMenuItem("Tag as Size A"), (e) -> menuSizesTagAsSizeA());
        WizGUI.addButton(sizes, new JMenuItem("Put on Size A"), (e) -> menuSizesPutOnSizeA());
        WizGUI.addButton(sizes, new JMenuItem("Tag as Size B"), (e) -> menuSizesTagAsSizeB());
        WizGUI.addButton(sizes, new JMenuItem("Put on Size B"), (e) -> menuSizesPutOnSizeB());
        popMenu.add(sizes);
    }

    private void menuSizesTagAsSizeA() {
        WizProps.set(rootName + "_WIDTH_SIZE_A", frame.getBounds().width);
        WizProps.set(rootName + "_HEIGHT_SIZE_A", frame.getBounds().height);
    }

    private void menuSizesPutOnSizeA() {
        var width = WizProps.get(rootName + "_WIDTH_SIZE_A", frame.getBounds().width);
        var height = WizProps.get(rootName + "_HEIGHT_SIZE_A", frame.getBounds().height);
        frame.setSize(width, height);
    }

    private void menuSizesTagAsSizeB() {
        WizProps.set(rootName + "_WIDTH_SIZE_B", frame.getBounds().width);
        WizProps.set(rootName + "_HEIGHT_SIZE_B", frame.getBounds().height);
    }

    private void menuSizesPutOnSizeB() {
        var width = WizProps.get(rootName + "_WIDTH_SIZE_B", frame.getBounds().width);
        var height = WizProps.get(rootName + "_HEIGHT_SIZE_B", frame.getBounds().height);
        frame.setSize(width, height);
    }

    private void menuOnTop() {
        frame.setAlwaysOnTop(!frame.isAlwaysOnTop());
    }

    private void menuClose() {
        WizGUI.close(frame);
    }

}
