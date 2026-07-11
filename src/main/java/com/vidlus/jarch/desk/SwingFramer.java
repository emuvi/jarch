package com.vidlus.jarch.desk;

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
import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JTabbedPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import com.vidlus.jarch.mage.WizGUI;
import com.vidlus.jarch.mage.WizLang;
import com.vidlus.jarch.mage.WizObject;
import com.vidlus.jarch.mage.WizProps;
import com.vidlus.jarch.mage.WizString;

/**
 * Utility class for managing and persisting the state, bounds, and UI configurations 
 * of a {@link JFrame} and its enclosed components. It also provides a built-in 
 * customizable popup menu for managing the window state.
 */
public class SwingFramer {

    private final JFrame frame;
    private final Font font;
    private final String rootName;
    private final JPopupMenu popMenu;

    /**
     * Constructs a new {@code SwingFramer} to manage the specified frame.
     * 
     * @param frame The {@link JFrame} to be managed.
     * @param font  The default {@link Font} to apply to the frame and its components.
     */
    public SwingFramer(JFrame frame, Font font) {
        this.frame = frame;
        this.font = font;
        this.rootName = "FRAME_" + WizString.getParameterName(!frame.getName().isEmpty() ? frame.getName() : frame.getTitle());
        this.popMenu = new JPopupMenu();
    }

    /**
     * Centers the managed frame relative to the screen.
     * 
     * @return This {@code SwingFramer} instance for method chaining.
     */
    public SwingFramer centerFrame() {
        frame.setLocationRelativeTo(null);
        return this;
    }

    /**
     * Maximizes the managed frame to fill the screen.
     * 
     * @return This {@code SwingFramer} instance for method chaining.
     */
    public SwingFramer maximizeFrame() {
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        return this;
    }

    /**
     * Minimizes (iconifies) the managed frame.
     * 
     * @return This {@code SwingFramer} instance for method chaining.
     */
    public SwingFramer minimizeFrame() {
        frame.setExtendedState(JFrame.ICONIFIED);
        return this;
    }

    /**
     * Restores the managed frame to its normal state.
     * 
     * @return This {@code SwingFramer} instance for method chaining.
     */
    public SwingFramer restoreFrame() {
        frame.setExtendedState(JFrame.NORMAL);
        return this;
    }

    /**
     * Adds a custom item to the built-in popup menu.
     * 
     * @param item The {@link JMenuItem} to add.
     * @return This {@code SwingFramer} instance for method chaining.
     */
    public SwingFramer addPopMenuItem(JMenuItem item) {
        popMenu.add(item);
        return this;
    }

    /**
     * Adds a separator to the built-in popup menu.
     * 
     * @return This {@code SwingFramer} instance for method chaining.
     */
    public SwingFramer addPopMenuSeparator() {
        popMenu.addSeparator();
        return this;
    }

    /**
     * Initializes the window listeners and the popup menu for the managed frame.
     * 
     * @return This {@code SwingFramer} instance for method chaining.
     */
    public SwingFramer init() {
        initWindow();
        initPopMenu();
        return this;
    }

    /**
     * Retrieves the managed {@link JFrame}.
     * 
     * @return The managed frame.
     */
    public JFrame getFrame() {
        return this.frame;
    }

    /**
     * Retrieves the root property name used for persisting frame properties.
     * 
     * @return The root property name string.
     */
    public String getRootName() {
        return this.rootName;
    }

    /**
     * Retrieves the popup menu associated with the managed frame.
     * 
     * @return The {@link JPopupMenu}.
     */
    public JPopupMenu getPopMenu() {
        return this.popMenu;
    }

    /**
     * Initializes window event listeners to handle loading properties upon opening
     * and saving properties upon closing.
     */
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

    /**
     * Loads the frame's previously saved properties (bounds, top-level state) 
     * from the properties store and applies them.
     * 
     * @throws HeadlessException If GraphicsEnvironment.isHeadless() returns true.
     * @throws SecurityException If a security manager is present and denies access.
     */
    private void loadFrameProps() throws HeadlessException, SecurityException {
        var left = WizProps.get(rootName + "_LEFT", frame.getBounds().x);
        var top = WizProps.get(rootName + "_TOP", frame.getBounds().y);
        var width = WizProps.get(rootName + "_WIDTH", frame.getBounds().width);
        var height = WizProps.get(rootName + "_HEIGHT", frame.getBounds().height);
        var bounds = WizGUI.getBoundsInsideScreen(new Rectangle(left, top, width, height));
        frame.setBounds(bounds);
        frame.setAlwaysOnTop(WizProps.get(rootName + "_ON_TOP", frame.isAlwaysOnTop()));
    }

    /**
     * Recursively loads the saved properties (values, selections, etc.) for all named 
     * components inside the given container hierarchy.
     * 
     * @param component The root component to begin scanning and applying loaded states.
     */
    public void loadFramePropsComps(Component component) {
        if (component instanceof Container container &&
                !WizLang.isChildOf(component.getClass(), JTextComponent.class, JComboBox.class, JSpinner.class, JCheckBox.class, JSlider.class, JRadioButton.class, JToggleButton.class, JProgressBar.class)
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
                    case JSlider sliderField ->
                        sliderField.setValue(WizProps.get(paramName, sliderField.getValue()));
                    case JRadioButton radioField ->
                        radioField.setSelected(WizProps.get(paramName, radioField.isSelected()));
                    case JToggleButton toggleField ->
                        toggleField.setSelected(WizProps.get(paramName, toggleField.isSelected()));
                    case JTabbedPane tabbedPane ->
                        tabbedPane.setSelectedIndex(WizProps.get(paramName, tabbedPane.getSelectedIndex()));
                    case JProgressBar progressBar ->
                        progressBar.setValue(WizProps.get(paramName, progressBar.getValue()));
                    default -> {}
                }
            });
        }
    }

    /**
     * Saves the frame's current properties (bounds, top-level state) 
     * to the properties store.
     */
    private void saveFrameProps() {
        WizProps.set(rootName + "_LEFT", frame.getBounds().x);
        WizProps.set(rootName + "_TOP", frame.getBounds().y);
        WizProps.set(rootName + "_WIDTH", frame.getBounds().width);
        WizProps.set(rootName + "_HEIGHT", frame.getBounds().height);
        WizProps.set(rootName + "_ON_TOP", frame.isAlwaysOnTop());
    }

    /**
     * Recursively saves the current states (values, selections, etc.) for all named 
     * components inside the given container hierarchy.
     * 
     * @param component The root component to begin scanning and saving states from.
     */
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
                case JSlider sliderField ->
                    WizProps.set(paramName, sliderField.getValue());
                case JRadioButton radioField ->
                    WizProps.set(paramName, radioField.isSelected());
                case JToggleButton toggleField ->
                    WizProps.set(paramName, toggleField.isSelected());
                case JTabbedPane tabbedPane ->
                    WizProps.set(paramName, tabbedPane.getSelectedIndex());
                case JProgressBar progressBar ->
                    WizProps.set(paramName, progressBar.getValue());
                default -> {
                }
            }
        }
        if (component instanceof Container container &&
                !WizLang.isChildOf(component.getClass(), JTextComponent.class, JComboBox.class, JSpinner.class, JCheckBox.class, JSlider.class, JRadioButton.class, JToggleButton.class, JProgressBar.class)
        ) {
            for (Component inside : container.getComponents()) {
                saveFramePropsComps(inside);
            }
        }
    }

    /**
     * Initializes the mouse listeners required to trigger the popup menu 
     * on right-click events inside the frame.
     */
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

    /**
     * Constructs the default items and submenus for the popup menu.
     */
    private void createPopMenu() {
        createMenuSizes();
        createMenuStates();
        WizGUI.addButton(popMenu, new JMenuItem("OnTop"), (e) -> menuOnTop());
        WizGUI.addButton(popMenu, new JMenuItem("Close"), (e) -> menuClose());
        popMenu.addSeparator();
        createMenuStyles();
    }

    /**
     * Creates and adds the "States" submenu for window maximization, minimization, 
     * restoration, and centering.
     */
    private void createMenuStates() {
        var states = new JMenu("States");
        WizGUI.addButton(states, new JMenuItem("Maximize"), e -> maximizeFrame());
        WizGUI.addButton(states, new JMenuItem("Minimize"), e -> minimizeFrame());
        WizGUI.addButton(states, new JMenuItem("Restore"), e -> restoreFrame());
        WizGUI.addButton(states, new JMenuItem("Center"), e -> centerFrame());
        popMenu.add(states);
    }

    /**
     * Creates and adds the "Sizes" submenu for saving and loading preset window sizes.
     */
    private void createMenuSizes() {
        var sizes = new JMenu("Sizes");
        WizGUI.addButton(sizes, new JMenuItem("Tag as Size A"), e -> menuSizesTagAsSizeA());
        WizGUI.addButton(sizes, new JMenuItem("Put on Size A"), e -> menuSizesPutOnSizeA());
        WizGUI.addButton(sizes, new JMenuItem("Tag as Size B"), e -> menuSizesTagAsSizeB());
        WizGUI.addButton(sizes, new JMenuItem("Put on Size B"), e -> menuSizesPutOnSizeB());
        popMenu.add(sizes);
    }

    /**
     * Saves the current frame dimensions as "Size A".
     */
    private void menuSizesTagAsSizeA() {
        WizProps.set(rootName + "_WIDTH_SIZE_A", frame.getBounds().width);
        WizProps.set(rootName + "_HEIGHT_SIZE_A", frame.getBounds().height);
    }

    /**
     * Applies the previously saved "Size A" dimensions to the frame.
     */
    private void menuSizesPutOnSizeA() {
        var width = WizProps.get(rootName + "_WIDTH_SIZE_A", frame.getBounds().width);
        var height = WizProps.get(rootName + "_HEIGHT_SIZE_A", frame.getBounds().height);
        frame.setSize(width, height);
    }

    /**
     * Saves the current frame dimensions as "Size B".
     */
    private void menuSizesTagAsSizeB() {
        WizProps.set(rootName + "_WIDTH_SIZE_B", frame.getBounds().width);
        WizProps.set(rootName + "_HEIGHT_SIZE_B", frame.getBounds().height);
    }

    /**
     * Applies the previously saved "Size B" dimensions to the frame.
     */
    private void menuSizesPutOnSizeB() {
        var width = WizProps.get(rootName + "_WIDTH_SIZE_B", frame.getBounds().width);
        var height = WizProps.get(rootName + "_HEIGHT_SIZE_B", frame.getBounds().height);
        frame.setSize(width, height);
    }

    /**
     * Toggles the "Always on Top" property for the managed frame.
     */
    private void menuOnTop() {
        frame.setAlwaysOnTop(!frame.isAlwaysOnTop());
    }

    /**
     * Closes the managed frame using the WizGUI utility.
     */
    private void menuClose() {
        WizGUI.close(frame);
    }

    /**
     * Creates and adds the "Styles" submenu allowing dynamic Look and Feel switching.
     */
    private void createMenuStyles() {
        var styles = new JMenu("Styles");
        for (var styleOption : WizGUI.getLookAndFeelOptions()) {
            WizGUI.addButton(styles, new JMenuItem(styleOption), e -> WizGUI.setLookAndFeel(styleOption));    
        }
        popMenu.add(styles);
    }

}
