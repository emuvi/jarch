package com.vidlus.jarch.desk;

import javax.swing.JCheckBox;

/**
 * A fluent API wrapper for {@link JCheckBox}, extending {@link DEdit} for handling boolean inputs.
 */
public class DEditCheck extends DEdit<Boolean> {

    /**
     * Constructs a new DEditCheck with an unselected state.
     */
    public DEditCheck() {
        this(false);
    }

    /**
     * Constructs a new DEditCheck with the specified initial state.
     * 
     * @param value the initial selection state
     */
    public DEditCheck(Boolean value) {
        super(new JCheckBox());
        setValue(value);
    }

    /**
     * Returns the underlying JCheckBox component.
     * 
     * @return the JCheckBox component
     */
    @Override
    public JCheckBox comp() {
        return (JCheckBox) super.comp();
    }

    /**
     * Gets the current value of the checkbox.
     * 
     * @return true if selected, false otherwise
     */
    @Override
    public Boolean getValue() {
        return comp().isSelected();
    }

    /**
     * Sets the value of the checkbox.
     * 
     * @param value true to select, false to unselect (null is treated as false)
     */
    @Override
    public void setValue(Boolean value) {
        comp().setSelected(value != null && value);
    }

    /**
     * Returns whether the checkbox is selected.
     * 
     * @return true if selected
     */
    public boolean selected() {
        return comp().isSelected();
    }

    /**
     * Sets whether the checkbox is selected.
     * 
     * @param selected true to select, false to unselect
     * @return this DEditCheck instance
     */
    public DEditCheck selected(boolean selected) {
        comp().setSelected(selected);
        return this;
    }

    /**
     * Returns the horizontal alignment of the text.
     * 
     * @return the horizontal alignment
     */
    public int horizontalAlignment() {
        return comp().getHorizontalAlignment();
    }

    /**
     * Sets the horizontal alignment of the text.
     * 
     * @param alignment the alignment (e.g. SwingConstants.LEFT)
     * @return this DEditCheck instance
     */
    public DEditCheck horizontalAlignment(int alignment) {
        comp().setHorizontalAlignment(alignment);
        return this;
    }

    /**
     * Returns the vertical alignment of the text.
     * 
     * @return the vertical alignment
     */
    public int verticalAlignment() {
        return comp().getVerticalAlignment();
    }

    /**
     * Sets the vertical alignment of the text.
     * 
     * @param alignment the alignment (e.g. SwingConstants.CENTER)
     * @return this DEditCheck instance
     */
    public DEditCheck verticalAlignment(int alignment) {
        comp().setVerticalAlignment(alignment);
        return this;
    }

    /**
     * Returns the horizontal position of the text relative to the icon.
     * 
     * @return the horizontal text position
     */
    public int horizontalTextPosition() {
        return comp().getHorizontalTextPosition();
    }

    /**
     * Sets the horizontal position of the text relative to the icon.
     * 
     * @param position the horizontal text position
     * @return this DEditCheck instance
     */
    public DEditCheck horizontalTextPosition(int position) {
        comp().setHorizontalTextPosition(position);
        return this;
    }

    /**
     * Returns the vertical position of the text relative to the icon.
     * 
     * @return the vertical text position
     */
    public int verticalTextPosition() {
        return comp().getVerticalTextPosition();
    }

    /**
     * Sets the vertical position of the text relative to the icon.
     * 
     * @param position the vertical text position
     * @return this DEditCheck instance
     */
    public DEditCheck verticalTextPosition(int position) {
        comp().setVerticalTextPosition(position);
        return this;
    }

    /**
     * Returns the default icon for this checkbox.
     * 
     * @return the default icon
     */
    public javax.swing.Icon icon() {
        return comp().getIcon();
    }

    /**
     * Sets the default icon for this checkbox.
     * 
     * @param icon the default icon
     * @return this DEditCheck instance
     */
    public DEditCheck icon(javax.swing.Icon icon) {
        comp().setIcon(icon);
        return this;
    }

    /**
     * Returns the selected icon for this checkbox.
     * 
     * @return the selected icon
     */
    public javax.swing.Icon selectedIcon() {
        return comp().getSelectedIcon();
    }

    /**
     * Sets the selected icon for this checkbox.
     * 
     * @param icon the selected icon
     * @return this DEditCheck instance
     */
    public DEditCheck selectedIcon(javax.swing.Icon icon) {
        comp().setSelectedIcon(icon);
        return this;
    }

    /**
     * Returns whether the border should be painted flat.
     * 
     * @return true if border is painted flat
     */
    public boolean borderPaintedFlat() {
        return comp().isBorderPaintedFlat();
    }

    /**
     * Sets whether the border should be painted flat.
     * 
     * @param b true to paint border flat
     * @return this DEditCheck instance
     */
    public DEditCheck borderPaintedFlat(boolean b) {
        comp().setBorderPaintedFlat(b);
        return this;
    }

    /**
     * Returns the gap between the icon and text.
     * 
     * @return the gap in pixels
     */
    public int iconTextGap() {
        return comp().getIconTextGap();
    }

    /**
     * Sets the gap between the icon and text.
     * 
     * @param gap the gap in pixels
     * @return this DEditCheck instance
     */
    public DEditCheck iconTextGap(int gap) {
        comp().setIconTextGap(gap);
        return this;
    }

    /**
     * Returns the text of the checkbox.
     * 
     * @return the text
     */
    public String text() {
        return comp().getText();
    }

    /**
     * Sets the text of the checkbox.
     * 
     * @param text the new text
     * @return this DEditCheck instance
     */
    public DEditCheck text(String text) {
        comp().setText(text);
        return this;
    }

}
