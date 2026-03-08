package br.com.pointel.jarch.desk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 * A fluent API wrapper for JButton to easily create and configure buttons.
 */
public class DButton extends JButton {
    
    /**
     * Creates a button with no set text or icon.
     */
    public DButton() {
        super();
    }

    /**
     * Creates a button with text.
     * 
     * @param text the text of the button
     */
    public DButton(String text) {
        super(text);
    }

    /**
     * Creates a button where properties are taken from the Action supplied.
     * 
     * @param a the Action used to specify the new button
     */
    public DButton(Action a) {
        super(a);
    }

    /**
     * Creates a button with an icon.
     * 
     * @param icon the Icon image to display on the button
     */
    public DButton(Icon icon) {
        super(icon);
    }

    /**
     * Creates a button with initial text and an icon.
     * 
     * @param text the text of the button
     * @param icon the Icon image to display on the button
     */
    public DButton(String text, Icon icon) {
        super(text, icon);
    }

    /**
     * Adds an ActionListener to the button.
     * 
     * @param listener the ActionListener to be added
     * @return This DButton instance.
     */
    public DButton onAction(ActionListener listener) {
        addActionListener(listener);
        return this;
    }

    /**
     * Sets the Action for the button.
     * 
     * @param a the Action for the button
     * @return This DButton instance.
     */
    public DButton action(Action a) {
        setAction(a);
        return this;
    }

    /**
     * Attaches a DPopup to this button, which will be shown on action performed.
     * The popup is shown directly under the button.
     *
     * @param popup The DPopup to show.
     * @return This DButton instance.
     */
    public DButton popup(DPopup popup) {
        addActionListener(e -> popup.show(this, 0, getHeight()));
        return this;
    }

    /**
     * Sets the button's text.
     * 
     * @param text the string used to set the text
     * @return This DButton instance.
     */
    public DButton text(String text) {
        setText(text);
        return this;
    }

    /**
     * Sets the button's default icon.
     * 
     * @param icon the icon used to set the default icon
     * @return This DButton instance.
     */
    public DButton icon(Icon icon) {
        setIcon(icon);
        return this;
    }

    /**
     * Sets the pressed icon for the button.
     * 
     * @param icon the icon used to set the pressed icon
     * @return This DButton instance.
     */
    public DButton pressedIcon(Icon icon) {
        setPressedIcon(icon);
        return this;
    }

    /**
     * Sets the selected icon for the button.
     * 
     * @param icon the icon used to set the selected icon
     * @return This DButton instance.
     */
    public DButton selectedIcon(Icon icon) {
        setSelectedIcon(icon);
        return this;
    }

    /**
     * Sets the rollover icon for the button.
     * 
     * @param icon the icon used to set the rollover icon
     * @return This DButton instance.
     */
    public DButton rolloverIcon(Icon icon) {
        setRolloverIcon(icon);
        return this;
    }

    /**
     * Sets the disabled icon for the button.
     * 
     * @param icon the icon used to set the disabled icon
     * @return This DButton instance.
     */
    public DButton disabledIcon(Icon icon) {
        setDisabledIcon(icon);
        return this;
    }

    /**
     * Sets the disabled selection icon for the button.
     * 
     * @param icon the icon used to set the disabled selection icon
     * @return This DButton instance.
     */
    public DButton disabledSelectedIcon(Icon icon) {
        setDisabledSelectedIcon(icon);
        return this;
    }

    /**
     * Sets the rollover selection icon for the button.
     * 
     * @param icon the icon used to set the rollover selection icon
     * @return This DButton instance.
     */
    public DButton rolloverSelectedIcon(Icon icon) {
        setRolloverSelectedIcon(icon);
        return this;
    }

    /**
     * Sets the keyboard mnemonic on the current model.
     * 
     * @param mnemonic the key code which represents the mnemonic
     * @return This DButton instance.
     */
    public DButton mnemonic(int mnemonic) {
        setMnemonic(mnemonic);
        return this;
    }
    
    /**
     * Sets the keyboard mnemonic on the current model.
     * 
     * @param mnemonic the char which represents the mnemonic
     * @return This DButton instance.
     */
    public DButton mnemonic(char mnemonic) {
        setMnemonic(mnemonic);
        return this;
    }

    /**
     * Provides a hint to the look and feel as to which character in the text should be decorated to represent the mnemonic.
     * 
     * @param index the index into the String to display as the mnemonic
     * @return This DButton instance.
     */
    public DButton displayedMnemonicIndex(int index) {
        setDisplayedMnemonicIndex(index);
        return this;
    }

    /**
     * Sets the action command for this button.
     * 
     * @param actionCommand the action command for this button
     * @return This DButton instance.
     */
    public DButton actionCommand(String actionCommand) {
        setActionCommand(actionCommand);
        return this;
    }

    /**
     * Sets the border of this component.
     * 
     * @param border the border to be rendered for this component
     * @return This DButton instance.
     */
    public DButton border(Border border) {
        setBorder(border);
        return this;
    }

    /**
     * Sets whether the border should be painted.
     * 
     * @param b if true, the border is painted
     * @return This DButton instance.
     */
    public DButton borderPainted(boolean b) {
        setBorderPainted(b);
        return this;
    }

    /**
     * Sets whether focus should be painted.
     * 
     * @param b if true, the focus state is painted
     * @return This DButton instance.
     */
    public DButton focusPainted(boolean b) {
        setFocusPainted(b);
        return this;
    }

    /**
     * Sets the contentAreaFilled property.
     * 
     * @param b if true, the content are is filled; if false the content area is not filled
     * @return This DButton instance.
     */
    public DButton contentAreaFilled(boolean b) {
        setContentAreaFilled(b);
        return this;
    }

    /**
     * Sets the rolloverEnabled property.
     * 
     * @param b if true, rollover effects should be painted
     * @return This DButton instance.
     */
    public DButton rolloverEnabled(boolean b) {
        setRolloverEnabled(b);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DButton instance.
     */
    public DButton background(Color bg) {
        setBackground(bg);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DButton instance.
     */
    public DButton foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font
     * @return This DButton instance.
     */
    public DButton font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets space for margin between the button's border and the label.
     * 
     * @param m the space between the border and the label
     * @return This DButton instance.
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
     * @return This DButton instance.
     */
    public DButton margin(int top, int left, int bottom, int right) {
        setMargin(new Insets(top, left, bottom, right));
        return this;
    }

    /**
     * Enables or disables this component.
     * 
     * @param enabled true to enable, false to disable
     * @return This DButton instance.
     */
    public DButton enabled(boolean enabled) {
        setEnabled(enabled);
        return this;
    }

    /**
     * Sets the focusable state of this Component.
     * 
     * @param focusable indicates whether this Component is focusable
     * @return This DButton instance.
     */
    public DButton focusable(boolean focusable) {
        setFocusable(focusable);
        return this;
    }

    /**
     * Shows or hides this component.
     * 
     * @param visible true to make the component visible; false to make it invisible
     * @return This DButton instance.
     */
    public DButton visible(boolean visible) {
        setVisible(visible);
        return this;
    }
    
    /**
     * If true the component paints every pixel within its bounds.
     * 
     * @param isOpaque true if this component should be opaque
     * @return This DButton instance.
     */
    public DButton opaque(boolean isOpaque) {
        setOpaque(isOpaque);
        return this;
    }

    /**
     * Sets the preferred size of this component.
     * 
     * @param preferredSize the new preferred size, or null
     * @return This DButton instance.
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
     * @return This DButton instance.
     */
    public DButton preferredSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the minimum size of this component.
     * 
     * @param minimumSize the new minimum size of this component
     * @return This DButton instance.
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
     * @return This DButton instance.
     */
    public DButton minimumSize(int width, int height) {
        setMinimumSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the maximum size of this component.
     * 
     * @param maximumSize the new maximum size of this component
     * @return This DButton instance.
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
     * @return This DButton instance.
     */
    public DButton maximumSize(int width, int height) {
        setMaximumSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the horizontal alignment of the icon and text.
     * 
     * @param alignment one of the following constants: SwingConstants.RIGHT, SwingConstants.LEFT, SwingConstants.CENTER, SwingConstants.LEADING or SwingConstants.TRAILING
     * @return This DButton instance.
     */
    public DButton horizontalAlignment(int alignment) {
        setHorizontalAlignment(alignment);
        return this;
    }

    /**
     * Sets the vertical alignment of the icon and text.
     * 
     * @param alignment one of the following constants: SwingConstants.CENTER, SwingConstants.TOP, SwingConstants.BOTTOM
     * @return This DButton instance.
     */
    public DButton verticalAlignment(int alignment) {
        setVerticalAlignment(alignment);
        return this;
    }

    /**
     * Sets the horizontal position of the text relative to the icon.
     * 
     * @param textPosition one of the following constants: SwingConstants.RIGHT, SwingConstants.LEFT, SwingConstants.CENTER, SwingConstants.LEADING or SwingConstants.TRAILING
     * @return This DButton instance.
     */
    public DButton horizontalTextPosition(int textPosition) {
        setHorizontalTextPosition(textPosition);
        return this;
    }

    /**
     * Sets the vertical position of the text relative to the icon.
     * 
     * @param textPosition one of the following constants: SwingConstants.CENTER, SwingConstants.TOP, SwingConstants.BOTTOM
     * @return This DButton instance.
     */
    public DButton verticalTextPosition(int textPosition) {
        setVerticalTextPosition(textPosition);
        return this;
    }

    /**
     * If both the icon and text properties are set, this property defines the space between them.
     * 
     * @param iconTextGap the space between icon and text
     * @return This DButton instance.
     */
    public DButton iconTextGap(int iconTextGap) {
        setIconTextGap(iconTextGap);
        return this;
    }

    /**
     * Sets the amount of time (in milliseconds) required between mouse press events for the button to generate the corresponding action events.
     * 
     * @param threshhold the amount of time required between mouse press events
     * @return This DButton instance.
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
     * @return This DButton instance.
     */
    public DButton name(String name) {
        setName(name);
        return this;
    }

    /**
     * Sets the tooltip text.
     * 
     * @param hint the string to display
     * @return This DButton instance.
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
    
}
