package com.vidlus.jarch.desk;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;

/**
 * A {@link DButton} that natively acts as a dropdown menu launcher. 
 * When clicked, it automatically displays an attached {@link JPopupMenu} containing the defined items.
 */
public class DButtonMenu extends DButton {
    
    /** The popup menu that appears directly underneath this button when clicked. */
    private JPopupMenu popupMenu;

    /**
     * Constructs a new {@code DButtonMenu} with no initial text.
     */
    public DButtonMenu() {
        super();
        init();
    }

    /**
     * Constructs a new {@code DButtonMenu} with the specified text.
     * 
     * @param text the text to display on the button
     */
    public DButtonMenu(String text) {
        super(text);
        init();
    }
    
    /**
     * Constructs a new {@code DButtonMenu} with the specified text and icon.
     * 
     * @param text the text to display on the button
     * @param icon the {@link DIcon} to display on the button
     */
    public DButtonMenu(String text, DIcon icon) {
        super(text, icon);
        init();
    }

    /**
     * Initializes the popup menu and sets up the action listener that shows it upon button click.
     */
    private void init() {
        popupMenu = new JPopupMenu();
        onAction(e -> {
            popupMenu.show(this, 0, getHeight());
        });
    }

    /**
     * Adds a new selectable menu item to the dropdown.
     * 
     * @param name the label of the new menu item
     * @param action the {@link ActionListener} to trigger when the item is selected
     * @return this {@code DButtonMenu} instance to allow for method chaining
     */
    public DButtonMenu addMenuItem(String name, ActionListener action) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(action);
        popupMenu.add(item);
        return this;
    }
    
    /**
     * Adds a visual horizontal separator line to the dropdown menu to organize items.
     * 
     * @return this {@code DButtonMenu} instance to allow for method chaining
     */
    public DButtonMenu addSeparator() {
        popupMenu.addSeparator();
        return this;
    }
    
    /**
     * Retrieves the underlying {@link JPopupMenu} for further advanced customization.
     * 
     * @return the associated {@link JPopupMenu}
     */
    public JPopupMenu getMenu() {
        return popupMenu;
    }

    /**
     * Adds a new selectable menu item with an icon to the dropdown.
     * 
     * @param name the label of the new menu item
     * @param icon the {@link DIcon} to display next to the text
     * @param action the {@link ActionListener} to trigger when the item is selected
     * @return this {@code DButtonMenu} instance to allow for method chaining
     */
    public DButtonMenu addMenuItem(String name, DIcon icon, ActionListener action) {
        JMenuItem item = new JMenuItem(name, icon);
        item.addActionListener(action);
        popupMenu.add(item);
        return this;
    }

    /**
     * Removes all items and separators from the dropdown menu.
     * 
     * @return this {@code DButtonMenu} instance to allow for method chaining
     */
    public DButtonMenu removeAllItems() {
        popupMenu.removeAll();
        return this;
    }

    /**
     * Injects a functional action that is triggered immediately before the menu becomes visible.
     * Useful for dynamically populating the menu based on current context.
     * 
     * @param action the {@link Runnable} to execute before the menu opens
     * @return this {@code DButtonMenu} instance to allow for method chaining
     */
    public DButtonMenu onMenuOpen(Runnable action) {
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                action.run();
            }
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
        return this;
    }
}
