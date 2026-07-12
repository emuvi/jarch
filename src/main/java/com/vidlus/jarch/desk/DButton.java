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

import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;

/**
 * A fluent API wrapper for JButton to easily create and configure buttons.
 */
public class DButton extends JButton {
    
    /**
     * Constructs a new {@code DButton} with no set text or icon.
     * This is the default constructor.
     */
    public DButton() {
        super();
    }

    /**
     * Constructs a new {@code DButton} with the specified text label.
     * 
     * @param text the text to display on the button
     */
    public DButton(String text) {
        super(text);
    }

    /**
     * Constructs a new {@code DButton} whose properties are taken from the supplied {@link Action}.
     * 
     * @param a the {@link Action} used to specify the new button
     */
    public DButton(Action a) {
        super(a);
    }

    /**
     * Constructs a new {@code DButton} with the specified icon.
     * 
     * @param icon the {@link DIcon} image to display on the button
     */
    public DButton(DIcon icon) {
        super(icon);
    }

    /**
     * Constructs a new {@code DButton} with both initial text and an icon.
     * 
     * @param text the text to display on the button
     * @param icon the {@link DIcon} image to display on the button
     */
    public DButton(String text, DIcon icon) {
        super(text, icon);
    }

    /**
     * Attaches an {@link ActionListener} to this component.
     * The listener is invoked whenever the user triggers the primary action (e.g., clicking).
     * 
     * @param listener the {@link ActionListener} to be attached
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton onAction(ActionListener listener) {
        addActionListener(listener);
        return this;
    }

    /**
     * Sets the {@link Action} for the button.
     * This automatically configures the button's properties (text, icon, enabled state) from the action.
     * 
     * @param a the {@link Action} for the button
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton action(Action a) {
        setAction(a);
        return this;
    }

    /**
     * Attaches a {@link DPopup} to this button, which will be automatically shown when the button is clicked.
     * The popup is displayed directly underneath the button.
     * 
     * @param popup the {@link DPopup} to display
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton popup(DPopup popup) {
        addActionListener(e -> popup.show(this, 0, getHeight()));
        return this;
    }

    /**
     * Sets the text label of the button.
     * 
     * @param text the string to display
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton text(String text) {
        setText(text);
        return this;
    }

    /**
     * Sets the primary icon of the button.
     * 
     * @param icon the {@link DIcon} to display
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton icon(DIcon icon) {
        setIcon(icon);
        return this;
    }

    /**
     * Sets the pressed icon for the button.
     * 
     * @param icon the icon used to set the pressed icon
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton pressedIcon(DIcon icon) {
        setPressedIcon(icon);
        return this;
    }

    /**
     * Sets the selected icon for the button.
     * 
     * @param icon the icon used to set the selected icon
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton selectedIcon(DIcon icon) {
        setSelectedIcon(icon);
        return this;
    }

    /**
     * Sets the rollover icon for the button.
     * 
     * @param icon the icon used to set the rollover icon
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton rolloverIcon(DIcon icon) {
        setRolloverIcon(icon);
        return this;
    }

    /**
     * Sets the disabled icon for the button.
     * 
     * @param icon the icon used to set the disabled icon
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton disabledIcon(DIcon icon) {
        setDisabledIcon(icon);
        return this;
    }

    /**
     * Sets the disabled selection icon for the button.
     * 
     * @param icon the icon used to set the disabled selection icon
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton disabledSelectedIcon(DIcon icon) {
        setDisabledSelectedIcon(icon);
        return this;
    }

    /**
     * Sets the rollover selection icon for the button.
     * 
     * @param icon the icon used to set the rollover selection icon
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton rolloverSelectedIcon(DIcon icon) {
        setRolloverSelectedIcon(icon);
        return this;
    }

    /**
     * Sets the keyboard mnemonic on the current model.
     * 
     * @param mnemonic the key code which represents the mnemonic
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton mnemonic(int mnemonic) {
        setMnemonic(mnemonic);
        return this;
    }
    
    /**
     * Sets the keyboard mnemonic on the current model.
     * 
     * @param mnemonic the char which represents the mnemonic
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton mnemonic(char mnemonic) {
        setMnemonic(mnemonic);
        return this;
    }

    /**
     * Provides a hint to the look and feel as to which character in the text should be decorated to represent the mnemonic.
     * 
     * @param index the index into the String to display as the mnemonic
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton displayedMnemonicIndex(int index) {
        setDisplayedMnemonicIndex(index);
        return this;
    }

    /**
     * Sets the action command for this button.
     * 
     * @param actionCommand the action command for this button
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton actionCommand(String actionCommand) {
        setActionCommand(actionCommand);
        return this;
    }

    /**
     * Sets the border of this component.
     * 
     * @param border the border to be rendered for this component
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton border(Border border) {
        setBorder(border);
        return this;
    }

    /**
     * Sets whether the border should be painted.
     * 
     * @param b if true, the border is painted
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton borderPainted(boolean b) {
        setBorderPainted(b);
        return this;
    }

    /**
     * Sets whether focus should be painted.
     * 
     * @param b if true, the focus state is painted
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton focusPainted(boolean b) {
        setFocusPainted(b);
        return this;
    }

    /**
     * Sets the contentAreaFilled property.
     * 
     * @param b if true, the content are is filled; if false the content area is not filled
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton contentAreaFilled(boolean b) {
        setContentAreaFilled(b);
        return this;
    }

    /**
     * Sets the rolloverEnabled property.
     * 
     * @param b if true, rollover effects should be painted
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton rolloverEnabled(boolean b) {
        setRolloverEnabled(b);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton background(Color bg) {
        setBackground(bg);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired {@link Font}
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets space for margin between the button's border and the label.
     * 
     * @param m the space between the border and the label
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton margin(Insets m) {
        setMargin(m);
        return this;
    }
    
    /**
     * Sets space for margin between the button's border and the label.
     * 
     * @param top the top margin
     * @param left the left margin
     * @param bottom the bottom margin
     * @param right the right margin
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton margin(int top, int left, int bottom, int right) {
        setMargin(new Insets(top, left, bottom, right));
        return this;
    }

    /**
     * Enables or disables this component.
     * 
     * @param enabled true to enable, false to disable
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton enabled(boolean enabled) {
        setEnabled(enabled);
        return this;
    }

    /**
     * Sets the focusable state of this Component.
     * 
     * @param focusable indicates whether this Component is focusable
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton focusable(boolean focusable) {
        setFocusable(focusable);
        return this;
    }

    /**
     * Shows or hides this component.
     * 
     * @param visible true to make the component visible; false to make it invisible
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton visible(boolean visible) {
        setVisible(visible);
        return this;
    }
    
    /**
     * If true the component paints every pixel within its bounds.
     * 
     * @param isOpaque true if this component should be opaque
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton opaque(boolean isOpaque) {
        setOpaque(isOpaque);
        return this;
    }

    /**
     * Sets the preferred size of this component.
     * 
     * @param preferredSize the new preferred size, or null
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton preferredSize(Dimension preferredSize) {
        setPreferredSize(preferredSize);
        return this;
    }
    
    /**
     * Sets the preferred size of this component.
     * 
     * @param width the preferred width
     * @param height the preferred height
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton preferredSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the minimum size of this component.
     * 
     * @param minimumSize the new minimum size of this component
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton minimumSize(Dimension minimumSize) {
        setMinimumSize(minimumSize);
        return this;
    }
    
    /**
     * Sets the minimum size of this component.
     * 
     * @param width the minimum width
     * @param height the minimum height
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton minimumSize(int width, int height) {
        setMinimumSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the maximum size of this component.
     * 
     * @param maximumSize the new maximum size of this component
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton maximumSize(Dimension maximumSize) {
        setMaximumSize(maximumSize);
        return this;
    }
    
    /**
     * Sets the maximum size of this component.
     * 
     * @param width the maximum width
     * @param height the maximum height
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton maximumSize(int width, int height) {
        setMaximumSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the horizontal alignment of the icon and text.
     * 
     * @param alignment one of the following constants: SwingConstants.RIGHT, SwingConstants.LEFT, SwingConstants.CENTER, SwingConstants.LEADING or SwingConstants.TRAILING
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton horizontalAlignment(int alignment) {
        setHorizontalAlignment(alignment);
        return this;
    }

    /**
     * Sets the vertical alignment of the icon and text.
     * 
     * @param alignment one of the following constants: SwingConstants.CENTER, SwingConstants.TOP, SwingConstants.BOTTOM
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton verticalAlignment(int alignment) {
        setVerticalAlignment(alignment);
        return this;
    }

    /**
     * Sets the horizontal position of the text relative to the icon.
     * 
     * @param textPosition one of the following constants: SwingConstants.RIGHT, SwingConstants.LEFT, SwingConstants.CENTER, SwingConstants.LEADING or SwingConstants.TRAILING
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton horizontalTextPosition(int textPosition) {
        setHorizontalTextPosition(textPosition);
        return this;
    }

    /**
     * Sets the vertical position of the text relative to the icon.
     * 
     * @param textPosition one of the following constants: SwingConstants.CENTER, SwingConstants.TOP, SwingConstants.BOTTOM
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton verticalTextPosition(int textPosition) {
        setVerticalTextPosition(textPosition);
        return this;
    }

    /**
     * If both the icon and text properties are set, this property defines the space between them.
     * 
     * @param iconTextGap the space between icon and text
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton iconTextGap(int iconTextGap) {
        setIconTextGap(iconTextGap);
        return this;
    }

    /**
     * Sets the amount of time (in milliseconds) required between mouse press events for the button to generate the corresponding action events.
     * 
     * @param threshhold the amount of time required between mouse press events
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton multiClickThreshhold(long threshhold) {
        setMultiClickThreshhold(threshhold);
        return this;
    }

    /**
     * Gets the name of the component.
     * 
     * @return the name of the component
     */
    public String name() {
        return getName();
    }

    /**
     * Sets the name of the component.
     * 
     * @param name the string that is to be this component's name
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton name(String name) {
        setName(name);
        return this;
    }

    /**
     * Sets the tooltip text.
     * 
     * @param hint the string to display
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton hint(String hint) {
        setToolTipText(hint);
        return this;
    }

    /**
     * Gets the tooltip text.
     * 
     * @return the tooltip text
     */
    public String hint() {
        return getToolTipText();
    }

    /**
     * Sets the hideActionText property, which determines whether the button displays text from the Action.
     * 
     * @param hide if true, the button's text is not updated from the {@link Action}
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton hideActionText(boolean hide) {
        setHideActionText(hide);
        return this;
    }

    // --- Advanced Formatting & Border Helpers ---

    /**
     * Sets an empty border around the button, effectively acting as padding.
     * 
     * @param size the padding size for all four sides
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton borderEmpty(int size) {
        setBorder(BorderFactory.createEmptyBorder(size, size, size, size));
        return this;
    }

    /**
     * Sets an empty border around the button with specific sizes for each side.
     * 
     * @param top    the top padding
     * @param left   the left padding
     * @param bottom the bottom padding
     * @param right  the right padding
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton borderEmpty(int top, int left, int bottom, int right) {
        setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return this;
    }

    /**
     * Sets a simple 1px solid line border around the button.
     * 
     * @param color the color of the line border
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton borderLine(Color color) {
        setBorder(BorderFactory.createLineBorder(color));
        return this;
    }

    /**
     * Sets a solid line border around the button with a specific thickness.
     * 
     * @param color     the color of the line border
     * @param thickness the thickness of the border in pixels
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton borderLine(Color color, int thickness) {
        setBorder(BorderFactory.createLineBorder(color, thickness));
        return this;
    }

    /**
     * Sets the mouse cursor that displays when hovering over this button.
     * 
     * @param cursorType the integer constant from {@link Cursor}
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton cursor(int cursorType) {
        setCursor(new Cursor(cursorType));
        return this;
    }

    /**
     * Instantly sets the cursor to a Hand cursor when hovering over the button.
     * 
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton cursorHand() {
        return cursor(Cursor.HAND_CURSOR);
    }

    // --- Font Styling Helpers ---

    /**
     * Derives and applies a bold version of the current font.
     * 
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton fontBold() {
        if (getFont() != null) setFont(getFont().deriveFont(Font.BOLD));
        return this;
    }

    /**
     * Derives and applies an italic version of the current font.
     * 
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton fontItalic() {
        if (getFont() != null) setFont(getFont().deriveFont(Font.ITALIC));
        return this;
    }

    /**
     * Derives and applies a specific size to the current font.
     * 
     * @param size the new font size as a float
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton fontSize(float size) {
        if (getFont() != null) setFont(getFont().deriveFont(size));
        return this;
    }

    // --- Alignment Semantic Helpers ---

    /**
     * Aligns the content of the button to the left.
     * 
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton alignLeft() {
        return horizontalAlignment(SwingConstants.LEFT);
    }

    /**
     * Aligns the content of the button to the right.
     * 
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton alignRight() {
        return horizontalAlignment(SwingConstants.RIGHT);
    }

    /**
     * Aligns the content of the button to the center.
     * 
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton alignCenter() {
        return horizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Automatically positions the text directly beneath the icon.
     * 
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton textUnderIcon() {
        verticalTextPosition(SwingConstants.BOTTOM);
        horizontalTextPosition(SwingConstants.CENTER);
        return this;
    }

    /**
     * Automatically positions the text directly to the right of the icon.
     * 
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton textRightOfIcon() {
        verticalTextPosition(SwingConstants.CENTER);
        horizontalTextPosition(SwingConstants.RIGHT);
        return this;
    }

    /**
     * Automatically positions the text directly to the left of the icon.
     * 
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton textLeftOfIcon() {
        verticalTextPosition(SwingConstants.CENTER);
        horizontalTextPosition(SwingConstants.LEFT);
        return this;
    }

    /**
     * Automatically positions the text directly above the icon.
     * 
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton textAboveIcon() {
        verticalTextPosition(SwingConstants.TOP);
        horizontalTextPosition(SwingConstants.CENTER);
        return this;
    }

    // --- Event Listener Helpers ---

    /**
     * Injects a functional action that is triggered when the mouse enters the button area.
     * 
     * @param action the Consumer accepting the {@link MouseEvent}
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton onMouseEnter(Consumer<MouseEvent> action) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                action.accept(e);
            }
        });
        return this;
    }

    /**
     * Injects a functional action that is triggered when the mouse exits the button area.
     * 
     * @param action the Consumer accepting the {@link MouseEvent}
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton onMouseExit(Consumer<MouseEvent> action) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                action.accept(e);
            }
        });
        return this;
    }

    /**
     * Injects a functional action that is triggered when the mouse clicks the button.
     * 
     * @param action the Consumer accepting the {@link MouseEvent}
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton onMouseClick(Consumer<MouseEvent> action) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.accept(e);
            }
        });
        return this;
    }

    /**
     * Injects a functional action that is triggered when the mouse is double-clicked on the button.
     * 
     * @param action the Consumer accepting the {@link MouseEvent}
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton onDoubleClick(Consumer<MouseEvent> action) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                    action.accept(e);
                }
            }
        });
        return this;
    }

    /**
     * Injects a functional action that is triggered when the mouse is right-clicked on the button.
     * 
     * @param action the Consumer accepting the {@link MouseEvent}
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton onRightClick(Consumer<MouseEvent> action) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
                    action.accept(e);
                }
            }
        });
        return this;
    }

    /**
     * Sets the bounds of the button.
     * 
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     * @param width the new width
     * @param height the new height
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton bounds(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        return this;
    }

    /**
     * Sets the location of the button.
     * 
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton location(int x, int y) {
        setLocation(x, y);
        return this;
    }

    /**
     * Sets the size of the button.
     * 
     * @param width the new width
     * @param height the new height
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton size(int width, int height) {
        setSize(width, height);
        return this;
    }

    /**
     * Strips the button of its background, border, and content area fill, making it fully transparent.
     * Useful for image-only buttons or link-style buttons.
     * 
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton transparent() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        return this;
    }

    /**
     * Alias for {@link #margin(int, int, int, int)}. Sets the space between the button's border and the label.
     * 
     * @param top the top padding
     * @param left the left padding
     * @param bottom the bottom padding
     * @param right the right padding
     * @return this {@code DButton} instance to allow for method chaining
     */
    public DButton padding(int top, int left, int bottom, int right) {
        setMargin(new Insets(top, left, bottom, right));
        return this;
    }
}
