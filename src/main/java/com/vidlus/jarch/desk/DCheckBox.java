package com.vidlus.jarch.desk;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import java.util.function.Consumer;

import javax.swing.Action;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

/**
 * A fluent API wrapper for {@link JCheckBox} that simplifies the creation and
 * configuration
 * of check boxes. It supports method chaining for concise UI definitions.
 */
public class DCheckBox extends JCheckBox {

    /**
     * Creates an initially unselected check box button with no text or icon.
     */
    public DCheckBox() {
        super();
    }

    /**
     * Creates an initially unselected check box with the specified text.
     * 
     * @param text the text of the check box
     */
    public DCheckBox(String text) {
        super(text);
    }

    /**
     * Creates an initially unselected check box with the specified icon.
     * 
     * @param icon the {@link DIcon} image to display
     */
    public DCheckBox(DIcon icon) {
        super(icon);
    }

    /**
     * Creates an initially unselected check box with the specified text and icon.
     * 
     * @param text the text of the check box
     * @param icon the {@link DIcon} image to display
     */
    public DCheckBox(String text, DIcon icon) {
        super(text, icon);
    }

    /**
     * Creates a check box where properties are taken from the Action supplied.
     * 
     * @param a the {@link Action} used to specify the new check box
     */
    public DCheckBox(Action a) {
        super(a);
    }

    /**
     * Adds an {@link ActionListener} to the check box.
     * 
     * @param listener the {@link ActionListener} to be added
     * @return this {@code DButtonCheck} instance for method chaining
     */
    public DCheckBox onAction(ActionListener listener) {
        addActionListener(listener);
        return this;
    }

    /**
     * Injects a functional action that is triggered when the check box state
     * changes.
     * 
     * @param action a {@link Consumer} accepting the {@link ItemEvent} triggered by
     *               state changes
     * @return this {@code DButtonCheck} instance for method chaining
     */
    public DCheckBox onCheck(Consumer<ItemEvent> action) {
        addItemListener(action::accept);
        return this;
    }

    /**
     * Sets the state of the check box.
     * 
     * @param b true if the check box should be selected, false otherwise
     * @return this {@code DButtonCheck} instance for method chaining
     */
    public DCheckBox selected(boolean b) {
        setSelected(b);
        return this;
    }

    /**
     * Sets the text of the check box.
     * 
     * @param text the string used to set the text
     * @return this {@code DButtonCheck} instance for method chaining
     */
    public DCheckBox text(String text) {
        setText(text);
        return this;
    }

    /**
     * Sets the default icon and the selected icon of the check box simultaneously.
     * 
     * @param defaultIcon  the {@link DIcon} to be displayed when the check box is
     *                     unselected
     * @param selectedIcon the {@link DIcon} to be displayed when the check box is
     *                     selected
     * @return this {@code DButtonCheck} instance for method chaining
     */
    public DCheckBox icon(DIcon defaultIcon, DIcon selectedIcon) {
        setIcon(defaultIcon);
        setSelectedIcon(selectedIcon);
        return this;
    }


    // --- Core UI Fluent Methods ---
    
    /**
     * Sets the background color of this component.
     * The background is only painted if the component is opaque.
     * 
     * @param bg the desired background {@link Color}
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox background(Color bg) {
        setBackground(bg);
        return this;
    }

    /**
     * Sets the foreground text color of this component.
     * This color is applied to the button's text label.
     * 
     * @param fg the desired foreground {@link Color}
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the primary {@link Font} used to render the text of this component.
     * 
     * @param font the desired {@link Font}
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Derives and applies a bold version of the component's current font.
     * Useful for dynamically emphasizing the button's text.
     * 
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox fontBold() {
        if (getFont() != null) setFont(getFont().deriveFont(Font.BOLD));
        return this;
    }

    /**
     * Derives and applies an italic version of the component's current font.
     * 
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox fontItalic() {
        if (getFont() != null) setFont(getFont().deriveFont(Font.ITALIC));
        return this;
    }

    /**
     * Derives and applies a specific size to the component's current font.
     * 
     * @param size the new font size as a float
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox fontSize(float size) {
        if (getFont() != null) setFont(getFont().deriveFont(size));
        return this;
    }

    /**
     * Sets the opacity state of this component.
     * If {@code true}, the component will paint every pixel within its bounds,
     * which is required for custom background colors to be visible on some Look and Feels.
     * 
     * @param isOpaque {@code true} if this component should be opaque
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox opaque(boolean isOpaque) {
        setOpaque(isOpaque);
        return this;
    }

    /**
     * Sets whether this component is enabled. A component that is disabled cannot be interacted with
     * and typically changes its visual appearance (e.g., greyed out).
     * 
     * @param enabled {@code true} to enable the component, {@code false} to disable it
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox enabled(boolean enabled) {
        setEnabled(enabled);
        return this;
    }

    /**
     * Sets the visibility state of this component.
     * Components that are not visible do not partake in layout calculations and cannot be interacted with.
     * 
     * @param visible {@code true} to make the component visible, {@code false} to hide it
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox visible(boolean visible) {
        setVisible(visible);
        return this;
    }
    
    /**
     * Sets whether this component can receive focus.
     * 
     * @param focusable {@code true} if this component is focusable
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox focusable(boolean focusable) {
        setFocusable(focusable);
        return this;
    }

    /**
     * Sets the tooltip text that displays when the user hovers the mouse over the component.
     * 
     * @param hint the tooltip string to display
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox hint(String hint) {
        setToolTipText(hint);
        return this;
    }

    /**
     * Directly sets the bounding box (position and size) of the component within its parent.
     * Note that layout managers might override this value.
     * 
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     * @param width the new width
     * @param height the new height
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox bounds(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        return this;
    }

    /**
     * Directly sets the location of the component within its parent.
     * Note that layout managers might override this value.
     * 
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    /**
     * Directly sets the absolute size of the component.
     * Note that layout managers might override this value.
     * 
     * @param width the new width
     * @param height the new height
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox size(int width, int height) {
        setSize(width, height);
        return this;
    }

    /**
     * Sets the preferred size of this component, which acts as a strong hint to layout managers.
     * 
     * @param width the preferred width
     * @param height the preferred height
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox preferredSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the minimum size of this component, preventing layout managers from shrinking it further.
     * 
     * @param width the minimum width
     * @param height the minimum height
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox minimumSize(int width, int height) {
        setMinimumSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the maximum size of this component, preventing layout managers from expanding it further.
     * 
     * @param width the maximum width
     * @param height the maximum height
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox maximumSize(int width, int height) {
        setMaximumSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the space (margin) between the button's border and its internal label/icon.
     * 
     * @param top the top margin in pixels
     * @param left the left margin in pixels
     * @param bottom the bottom margin in pixels
     * @param right the right margin in pixels
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox margin(int top, int left, int bottom, int right) {
        setMargin(new Insets(top, left, bottom, right));
        return this;
    }

    /**
     * An alias for {@link #margin(int, int, int, int)}. Sets the internal padding space 
     * between the button's border and its content.
     * 
     * @param top the top padding in pixels
     * @param left the left padding in pixels
     * @param bottom the bottom padding in pixels
     * @param right the right padding in pixels
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox padding(int top, int left, int bottom, int right) {
        return margin(top, left, bottom, right);
    }

    /**
     * Sets a custom {@link Border} for this component.
     * 
     * @param border the {@link Border} to be rendered
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox border(Border border) {
        setBorder(border);
        return this;
    }

    /**
     * Applies an empty, transparent border around the button, effectively acting as an external spacer.
     * 
     * @param top    the top border size
     * @param left   the left border size
     * @param bottom the bottom border size
     * @param right  the right border size
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox borderEmpty(int top, int left, int bottom, int right) {
        setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return this;
    }

    /**
     * Applies a simple 1-pixel solid line border around the button.
     * 
     * @param color the {@link Color} of the line border
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox borderLine(Color color) {
        setBorder(BorderFactory.createLineBorder(color));
        return this;
    }

    /**
     * Sets whether the button's border should be painted visually.
     * Setting this to {@code false} is useful for icon-only buttons or flat UI designs.
     * 
     * @param b {@code true} to paint the border, {@code false} to hide it
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox borderPainted(boolean b) {
        setBorderPainted(b);
        return this;
    }

    /**
     * Sets whether the visual focus indicator should be painted when this button has focus.
     * Setting this to {@code false} provides a cleaner look on modern UI designs.
     * 
     * @param b {@code true} to paint the focus indicator, {@code false} to hide it
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox focusPainted(boolean b) {
        setFocusPainted(b);
        return this;
    }

    /**
     * Sets whether the button's interior (content area) should be filled with color.
     * Set this to {@code false} to make the button's background fully transparent while keeping the border and text.
     * 
     * @param b {@code true} to fill the content area, {@code false} for transparency
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox contentAreaFilled(boolean b) {
        setContentAreaFilled(b);
        return this;
    }

    /**
     * Sets the mouse cursor that displays when the user hovers over this component.
     * 
     * @param cursorType the integer constant from {@link Cursor} (e.g., {@code Cursor.HAND_CURSOR})
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox cursor(int cursorType) {
        setCursor(new Cursor(cursorType));
        return this;
    }

    /**
     * Instantly configures the mouse cursor to change to a pointing hand when hovering over the button.
     * This provides excellent affordance for clickable elements.
     * 
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox cursorHand() {
        return cursor(Cursor.HAND_CURSOR);
    }

    /**
     * Sets the horizontal alignment of the icon and text relative to the button's internal boundaries.
     * 
     * @param alignment a constant from {@link SwingConstants} (e.g., {@code SwingConstants.LEFT})
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox horizontalAlignment(int alignment) {
        setHorizontalAlignment(alignment);
        return this;
    }

    /**
     * Sets the vertical alignment of the icon and text relative to the button's internal boundaries.
     * 
     * @param alignment a constant from {@link SwingConstants} (e.g., {@code SwingConstants.TOP})
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox verticalAlignment(int alignment) {
        setVerticalAlignment(alignment);
        return this;
    }

    /**
     * Sets the horizontal position of the text relative to the button's icon.
     * 
     * @param textPosition a constant from {@link SwingConstants}
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox horizontalTextPosition(int textPosition) {
        setHorizontalTextPosition(textPosition);
        return this;
    }

    /**
     * Sets the vertical position of the text relative to the button's icon.
     * 
     * @param textPosition a constant from {@link SwingConstants}
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox verticalTextPosition(int textPosition) {
        setVerticalTextPosition(textPosition);
        return this;
    }

    /**
     * Aligns the entire content (icon and text) to the left side of the button.
     * 
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox alignLeft() {
        return horizontalAlignment(SwingConstants.LEFT);
    }

    /**
     * Aligns the entire content (icon and text) to the right side of the button.
     * 
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox alignRight() {
        return horizontalAlignment(SwingConstants.RIGHT);
    }

    /**
     * Aligns the entire content (icon and text) to the center of the button.
     * 
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox alignCenter() {
        return horizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Automatically positions the text directly beneath the icon and centers both.
     * 
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox textUnderIcon() {
        verticalTextPosition(SwingConstants.BOTTOM);
        horizontalTextPosition(SwingConstants.CENTER);
        return this;
    }

    /**
     * Automatically positions the text directly to the right of the icon and centers both vertically.
     * 
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox textRightOfIcon() {
        verticalTextPosition(SwingConstants.CENTER);
        horizontalTextPosition(SwingConstants.RIGHT);
        return this;
    }

    /**
     * Automatically positions the text directly to the left of the icon and centers both vertically.
     * 
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox textLeftOfIcon() {
        verticalTextPosition(SwingConstants.CENTER);
        horizontalTextPosition(SwingConstants.LEFT);
        return this;
    }

    /**
     * Automatically positions the text directly above the icon and centers both horizontally.
     * 
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox textAboveIcon() {
        verticalTextPosition(SwingConstants.TOP);
        horizontalTextPosition(SwingConstants.CENTER);
        return this;
    }

    /**
     * Injects a functional action that is triggered exactly when the mouse enters the component's visible area.
     * 
     * @param action a {@link Consumer} accepting the {@link MouseEvent}
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox onMouseEnter(Consumer<MouseEvent> action) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                action.accept(e);
            }
        });
        return this;
    }

    /**
     * Injects a functional action that is triggered exactly when the mouse exits the component's visible area.
     * 
     * @param action a {@link Consumer} accepting the {@link MouseEvent}
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox onMouseExit(Consumer<MouseEvent> action) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                action.accept(e);
            }
        });
        return this;
    }

    /**
     * Injects a functional action that is triggered when the mouse clicks (press and release) the component.
     * Note: For standard button functionality, prefer {@link #onAction} when available, as it handles keyboard activation as well.
     * 
     * @param action a {@link Consumer} accepting the {@link MouseEvent}
     * @return this {@code DCheckBox} instance to allow for method chaining
     */
    public DCheckBox onMouseClick(Consumer<MouseEvent> action) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.accept(e);
            }
        });
        return this;
    }

}
