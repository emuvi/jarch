package br.com.pointel.jarch.desk;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

/**
 * A fluent API wrapper for JPopupMenu to easily create and attach popup menus.
 */
public class DPopup extends JPopupMenu {

    /**
     * Creates a new DPopup.
     */
    public DPopup() {
        super();
    }
    
    /**
     * Attaches this popup to a JComponent.
     * The popup will show on right-click or Ctrl+Space.
     * 
     * @param component The component to attach to.
     * @return This DPopup instance.
     */
    public DPopup on(JComponent component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1) {
                    showPopup(component, e.getX(), e.getY());
                }
            }
        });
        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    showPopup(component, 0, 0);
                }
            }
        });
        return this;
    }

    /**
     * Attaches this popup to a DButton.
     * The popup will show directly under the button when clicked.
     * 
     * @param button The button to attach to.
     * @return This DPopup instance.
     */
    public DPopup on(DButton button) {
        button.addActionListener(e -> show(button, 0, button.getHeight()));
        return this;
    }

    /**
     * Attaches this popup to a JButton.
     * The popup will show directly under the button when clicked.
     * 
     * @param button The button to attach to.
     * @return This DPopup instance.
     */
    public DPopup on(JButton button) {
        button.addActionListener(e -> show(button, 0, button.getHeight()));
        return this;
    }

    /**
     * Adds a separator to the menu.
     * 
     * @return This DPopup instance.
     */
    public DPopup separator() {
        addSeparator();
        return this;
    }

    /**
     * Adds a custom JMenuItem to the menu.
     * 
     * @param item The item to add.
     * @return This DPopup instance.
     */
    public DPopup item(JMenuItem item) {
        add(item);
        return this;
    }

    /**
     * Adds a menu item with the specified text.
     * 
     * @param text The text of the item.
     * @return This DPopup instance.
     */
    public DPopup item(String text) {
        return item(new JMenuItem(text));
    }

    /**
     * Adds a menu item with text and an action listener.
     * 
     * @param text The text of the item.
     * @param action The action to perform when clicked.
     * @return This DPopup instance.
     */
    public DPopup item(String text, ActionListener action) {
        var item = new JMenuItem(text);
        item.addActionListener(action);
        return item(item);
    }

    /**
     * Adds a menu item with text, icon, and an action listener.
     * 
     * @param text The text of the item.
     * @param icon The icon of the item.
     * @param action The action to perform when clicked.
     * @return This DPopup instance.
     */
    public DPopup item(String text, Icon icon, ActionListener action) {
        var item = new JMenuItem(text, icon);
        item.addActionListener(action);
        return item(item);
    }
    
    /**
     * Adds a menu item with text, keyboard accelerator, and an action listener.
     * 
     * @param text The text of the item.
     * @param accelerator The keyboard accelerator.
     * @param action The action to perform when clicked.
     * @return This DPopup instance.
     */
    public DPopup item(String text, KeyStroke accelerator, ActionListener action) {
        var item = new JMenuItem(text);
        item.setAccelerator(accelerator);
        item.addActionListener(action);
        return item(item);
    }
    
    /**
     * Adds a menu item with text, tooltip, and an action listener.
     * 
     * @param text The text of the item.
     * @param tooltip The tooltip text.
     * @param action The action to perform when clicked.
     * @return This DPopup instance.
     */
    public DPopup item(String text, String tooltip, ActionListener action) {
        var item = new JMenuItem(text);
        item.setToolTipText(tooltip);
        item.addActionListener(action);
        return item(item);
    }

    /**
     * Adds a menu item defined by an Action.
     * 
     * @param action The action defining the item.
     * @return This DPopup instance.
     */
    public DPopup item(Action action) {
        return item(new JMenuItem(action));
    }

    /**
     * Adds a custom JCheckBoxMenuItem to the menu.
     * 
     * @param item The checkbox item to add.
     * @return This DPopup instance.
     */
    public DPopup check(JCheckBoxMenuItem item) {
        add(item);
        return this;
    }

    /**
     * Adds a checkbox menu item with the specified text.
     * 
     * @param text The text of the item.
     * @return This DPopup instance.
     */
    public DPopup check(String text) {
        return check(new JCheckBoxMenuItem(text));
    }

    /**
     * Adds a checkbox menu item with text and selection state.
     * 
     * @param text The text of the item.
     * @param selected The initial selection state.
     * @return This DPopup instance.
     */
    public DPopup check(String text, boolean selected) {
        return check(new JCheckBoxMenuItem(text, selected));
    }

    /**
     * Adds a checkbox menu item with text, selection state, and action listener.
     * 
     * @param text The text of the item.
     * @param selected The initial selection state.
     * @param action The action to perform when clicked.
     * @return This DPopup instance.
     */
    public DPopup check(String text, boolean selected, ActionListener action) {
        var item = new JCheckBoxMenuItem(text, selected);
        item.addActionListener(action);
        return check(item);
    }

    /**
     * Adds a checkbox menu item with text, icon, selection state, and action listener.
     * 
     * @param text The text of the item.
     * @param icon The icon of the item.
     * @param selected The initial selection state.
     * @param action The action to perform when clicked.
     * @return This DPopup instance.
     */
    public DPopup check(String text, Icon icon, boolean selected, ActionListener action) {
        var item = new JCheckBoxMenuItem(text, icon, selected);
        item.addActionListener(action);
        return check(item);
    }

    /**
     * Adds a checkbox menu item defined by an Action.
     * 
     * @param action The action defining the item.
     * @return This DPopup instance.
     */
    public DPopup check(Action action) {
        var item = new JCheckBoxMenuItem(action);
        return check(item);
    }

    /**
     * Adds a custom JRadioButtonMenuItem to the menu.
     * 
     * @param item The radio button item to add.
     * @return This DPopup instance.
     */
    public DPopup radio(JRadioButtonMenuItem item) {
        add(item);
        return this;
    }

    /**
     * Adds a radio button menu item with the specified text.
     * 
     * @param text The text of the item.
     * @return This DPopup instance.
     */
    public DPopup radio(String text) {
        return radio(new JRadioButtonMenuItem(text));
    }

    /**
     * Adds a radio button menu item with text and selection state.
     * 
     * @param text The text of the item.
     * @param selected The initial selection state.
     * @return This DPopup instance.
     */
    public DPopup radio(String text, boolean selected) {
        return radio(new JRadioButtonMenuItem(text, selected));
    }

    /**
     * Adds a radio button menu item with text, selection state, and action listener.
     * 
     * @param text The text of the item.
     * @param selected The initial selection state.
     * @param action The action to perform when clicked.
     * @return This DPopup instance.
     */
    public DPopup radio(String text, boolean selected, ActionListener action) {
        var item = new JRadioButtonMenuItem(text, selected);
        item.addActionListener(action);
        return radio(item);
    }
    
    /**
     * Adds a radio button menu item with text, icon, selection state, and action listener.
     * 
     * @param text The text of the item.
     * @param icon The icon of the item.
     * @param selected The initial selection state.
     * @param action The action to perform when clicked.
     * @return This DPopup instance.
     */
    public DPopup radio(String text, Icon icon, boolean selected, ActionListener action) {
        var item = new JRadioButtonMenuItem(text, icon, selected);
        item.addActionListener(action);
        return radio(item);
    }

    /**
     * Adds a radio button menu item defined by an Action.
     * 
     * @param action The action defining the item.
     * @return This DPopup instance.
     */
    public DPopup radio(Action action) {
        var item = new JRadioButtonMenuItem(action);
        return radio(item);
    }
    
    /**
     * Adds a radio button menu item to a ButtonGroup with text, selection state, and action listener.
     * 
     * @param group The button group to add the item to.
     * @param text The text of the item.
     * @param selected The initial selection state.
     * @param action The action to perform when clicked.
     * @return This DPopup instance.
     */
    public DPopup radio(ButtonGroup group, String text, boolean selected, ActionListener action) {
        var item = new JRadioButtonMenuItem(text, selected);
        item.addActionListener(action);
        group.add(item);
        return radio(item);
    }

    /**
     * Adds a custom JMenu (submenu) to the menu.
     * 
     * @param menu The menu to add.
     * @return This DPopup instance.
     */
    public DPopup menu(JMenu menu) {
        add(menu);
        return this;
    }

    /**
     * Adds a submenu with the specified text.
     * 
     * @param text The text of the submenu.
     * @return This DPopup instance.
     */
    public DPopup menu(String text) {
        return menu(new JMenu(text));
    }
    
    /**
     * Adds a submenu with text and icon.
     * 
     * @param text The text of the submenu.
     * @param icon The icon of the submenu.
     * @return This DPopup instance.
     */
    public DPopup menu(String text, Icon icon) {
        var menu = new JMenu(text);
        menu.setIcon(icon);
        return menu(menu);
    }
    
    /**
     * Adds a submenu defined by an Action.
     * 
     * @param text The text of the submenu.
     * @param action The action defining the submenu.
     * @return This DPopup instance.
     */
    public DPopup menu(String text, Action action) {
        var menu = new JMenu(text);
        menu.setAction(action);
        return menu(menu);
    }
    
    /**
     * Shows the popup menu at the specified location relative to the invoker.
     * 
     * @param invoker The component invoking the popup.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return This DPopup instance.
     */
    public DPopup showPopup(Component invoker, int x, int y) {
        super.show(invoker, x, y);
        return this;
    }

}
