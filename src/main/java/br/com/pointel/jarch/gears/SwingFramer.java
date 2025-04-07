package br.com.pointel.jarch.gears;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import br.com.pointel.jarch.mage.WizChars;
import br.com.pointel.jarch.mage.WizDesk;
import br.com.pointel.jarch.mage.WizProps;

public class SwingFramer {

    private final JFrame frame;
    private final Font font;
    private final String rootName;
    private final JPopupMenu popMenu;

    public SwingFramer(JFrame frame, Font font) {
        this.frame = frame;
        this.font = font;
        this.rootName = "FRAME_" + WizChars.makeParameterName(!frame.getName().isEmpty() ? frame.getName() : frame.getTitle());
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
            Boolean firstActivated = true;
            
            @Override
            public void windowOpened(WindowEvent e) {
                loadFrameProps();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                if (firstActivated) {
                    firstActivated = false;
                    loadFramePropsComps(frame);
                    WizDesk.setAllComponentsFont(frame, font);
                    WizDesk.setAllComponentsFont(frame.getContentPane(), font);
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
        var bounds = WizDesk.getBoundsInsideScreen(new Rectangle(left, top, width, height));
        frame.setBounds(bounds);
        frame.setAlwaysOnTop(WizProps.get(rootName + "_ON_TOP", frame.isAlwaysOnTop()));
    }

    public void loadFramePropsComps(Component component) {
        if (component instanceof Container container) {
            for (Component inside : container.getComponents()) {
                loadFramePropsComps(inside);
            }
        }
        if (component != null && component.getName() != null && !component.getName().isEmpty()) {
            var paramName =  rootName + "_COMP_" + WizChars.makeParameterName(component.getName());
            SwingUtilities.invokeLater(() -> {
                switch (component) {
                    case JTextComponent textField ->
                        textField.setText(WizProps.get(paramName, textField.getText()));
                    case JComboBox comboField ->
                        comboField.setSelectedIndex(WizProps.get(paramName, comboField.getSelectedIndex()));
                    case JSpinner spinnerField ->
                        spinnerField.setValue(WizProps.get(paramName, (Integer) spinnerField.getValue()));
                    case JCheckBox checkField ->
                        checkField.setSelected(WizProps.get(paramName, checkField.isSelected()));
                    case JSplitPane splitPane ->
                        splitPane.setDividerLocation(WizProps.get(paramName, ((double) splitPane.getDividerLocation() / (double) splitPane.getDividerSize()) / 100.0));    
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
            var paramName = rootName + "_COMP_" + WizChars.makeParameterName(component.getName());
            switch (component) {
                case JTextComponent textField ->
                    WizProps.set(paramName, textField.getText());
                case JComboBox comboField ->
                    WizProps.set(paramName, comboField.getSelectedIndex());
                case JSpinner spinnerField ->
                    WizProps.set(paramName, (Integer) spinnerField.getValue());
                case JCheckBox checkField ->
                    WizProps.set(paramName, checkField.isSelected());
                case JSplitPane splitPane ->
                    WizProps.set(paramName, ((double) splitPane.getDividerLocation() / (double) splitPane.getDividerSize()) / 100.0);
                default -> {
                }
            }
        }
        if (component instanceof Container container) {
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
        WizDesk.addMenu(popMenu, new JMenuItem("OnTop"), (e) -> menuOnTop());
        WizDesk.addMenu(popMenu, new JMenuItem("Close"), (e) -> menuClose());
    }

    private void createMenuSizes() {
        var sizes = new JMenu("Sizes");
        WizDesk.addMenu(sizes, new JMenuItem("Tag as Size A"), (e) -> menuSizesTagAsSizeA());
        WizDesk.addMenu(sizes, new JMenuItem("Put on Size A"), (e) -> menuSizesPutOnSizeA());
        WizDesk.addMenu(sizes, new JMenuItem("Tag as Size B"), (e) -> menuSizesTagAsSizeB());
        WizDesk.addMenu(sizes, new JMenuItem("Put on Size B"), (e) -> menuSizesPutOnSizeB());
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
        WizDesk.close(frame);
    }

}
