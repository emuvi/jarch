package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import java.awt.Dimension;
import javax.swing.plaf.SplitPaneUI;

/**
 * A fluent API wrapper for JSplitPane to easily create and configure split panes.
 */
public class DSplitter extends JSplitPane {

    /**
     * Creates a new DSplitter configured to arrange the child components side-by-side
     * horizontally with no continuous layout.
     */
    public DSplitter() {
        super();
    }

    /**
     * Creates a new DSplitter configured with the specified components.
     * 
     * @param leftComponent the component to appear on the left
     * @param rightComponent the component to appear on the right
     */
    public DSplitter(Component leftComponent, Component rightComponent) {
        super(JSplitPane.HORIZONTAL_SPLIT, leftComponent, rightComponent);
    }

    /**
     * Creates a new DSplitter configured with the specified orientation.
     * 
     * @param orientation JSplitPane.HORIZONTAL_SPLIT or JSplitPane.VERTICAL_SPLIT
     */
    public DSplitter(int orientation) {
        super(orientation);
    }

    /**
     * Creates a new DSplitter with the specified orientation and continuous layout.
     * 
     * @param orientation JSplitPane.HORIZONTAL_SPLIT or JSplitPane.VERTICAL_SPLIT
     * @param continuousLayout a boolean, true for the components to redraw continuously
     */
    public DSplitter(int orientation, boolean continuousLayout) {
        super(orientation, continuousLayout);
    }

    /**
     * Creates a new DSplitter with the specified orientation and components.
     * 
     * @param orientation JSplitPane.HORIZONTAL_SPLIT or JSplitPane.VERTICAL_SPLIT
     * @param leftComponent the component to appear on the left/top
     * @param rightComponent the component to appear on the right/bottom
     */
    public DSplitter(int orientation, Component leftComponent, Component rightComponent) {
        super(orientation, leftComponent, rightComponent);
    }

    /**
     * Creates a new DSplitter with the specified orientation, layout, and components.
     * 
     * @param orientation JSplitPane.HORIZONTAL_SPLIT or JSplitPane.VERTICAL_SPLIT
     * @param continuousLayout a boolean, true for the components to redraw continuously
     * @param leftComponent the component to appear on the left/top
     * @param rightComponent the component to appear on the right/bottom
     */
    public DSplitter(int orientation, boolean continuousLayout, Component leftComponent, Component rightComponent) {
        super(orientation, continuousLayout, leftComponent, rightComponent);
    }

    /**
     * Sets the orientation to horizontal.
     * 
     * @return This DSplitter instance.
     */
    public DSplitter horizontal() {
        setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        return this;
    }

    /**
     * Sets the orientation to vertical.
     * 
     * @return This DSplitter instance.
     */
    public DSplitter vertical() {
        setOrientation(JSplitPane.VERTICAL_SPLIT);
        return this;
    }

    /**
     * Sets the component to the left of the divider.
     * 
     * @param comp the component
     * @return This DSplitter instance.
     */
    public DSplitter left(Component comp) {
        setLeftComponent(comp);
        return this;
    }

    /**
     * Sets the component to the left of the divider using a DEdit.
     * 
     * @param edit the DEdit component
     * @return This DSplitter instance.
     */
    public DSplitter left(DEdit<?> edit) {
        return left(edit.comp());
    }

    /**
     * Sets the component to the right of the divider.
     * 
     * @param comp the component
     * @return This DSplitter instance.
     */
    public DSplitter right(Component comp) {
        setRightComponent(comp);
        return this;
    }

    /**
     * Sets the component to the right of the divider using a DEdit.
     * 
     * @param edit the DEdit component
     * @return This DSplitter instance.
     */
    public DSplitter right(DEdit<?> edit) {
        return right(edit.comp());
    }

    /**
     * Sets the component above the divider.
     * 
     * @param comp the component
     * @return This DSplitter instance.
     */
    public DSplitter top(Component comp) {
        setTopComponent(comp);
        return this;
    }

    /**
     * Sets the component above the divider using a DEdit.
     * 
     * @param edit the DEdit component
     * @return This DSplitter instance.
     */
    public DSplitter top(DEdit<?> edit) {
        return top(edit.comp());
    }

    /**
     * Sets the component below the divider.
     * 
     * @param comp the component
     * @return This DSplitter instance.
     */
    public DSplitter bottom(Component comp) {
        setBottomComponent(comp);
        return this;
    }

    /**
     * Sets the component below the divider using a DEdit.
     * 
     * @param edit the DEdit component
     * @return This DSplitter instance.
     */
    public DSplitter bottom(DEdit<?> edit) {
        return bottom(edit.comp());
    }

    /**
     * Sets the location of the divider.
     * 
     * @param location an integer specifying a UI-specific value
     * @return This DSplitter instance.
     */
    public DSplitter divider(int location) {
        setDividerLocation(location);
        return this;
    }

    /**
     * Sets the proportional location of the divider.
     * 
     * @param proportionalLocation a double-precision floating point value between 0.0 and 1.0
     * @return This DSplitter instance.
     */
    public DSplitter divider(double proportionalLocation) {
        setDividerLocation(proportionalLocation);
        return this;
    }

    /**
     * Sets the size of the divider.
     * 
     * @param size an integer giving the size of the divider in pixels
     * @return This DSplitter instance.
     */
    public DSplitter dividerSize(int size) {
        setDividerSize(size);
        return this;
    }

    /**
     * Specifies how to distribute extra space when the size of the split pane changes.
     * 
     * @param value a double between 0.0 and 1.0
     * @return This DSplitter instance.
     */
    public DSplitter resizeWeight(double value) {
        setResizeWeight(value);
        return this;
    }

    /**
     * Sets whether the child components are continuously redrawn and laid out as the user resizes.
     * 
     * @param continuousLayout true if the components should continuously be redrawn
     * @return This DSplitter instance.
     */
    public DSplitter continuousLayout(boolean continuousLayout) {
        setContinuousLayout(continuousLayout);
        return this;
    }

    /**
     * Sets whether the split pane provides a UI widget to quickly expand/collapse the divider.
     * 
     * @param newValue true to provide a widget
     * @return This DSplitter instance.
     */
    public DSplitter oneTouchExpandable(boolean newValue) {
        setOneTouchExpandable(newValue);
        return this;
    }

    /**
     * Sets the border of this component.
     * 
     * @param border the border
     * @return This DSplitter instance.
     */
    public DSplitter border(Border border) {
        setBorder(border);
        return this;
    }

    /**
     * Sets an empty border of the specified size.
     * 
     * @param size the size for all sides
     * @return This DSplitter instance.
     */
    public DSplitter borderEmpty(int size) {
        setBorder(BorderFactory.createEmptyBorder(size, size, size, size));
        return this;
    }

    /**
     * Sets an empty border with the specified insets.
     * 
     * @param top the top inset
     * @param left the left inset
     * @param bottom the bottom inset
     * @param right the right inset
     * @return This DSplitter instance.
     */
    public DSplitter borderEmpty(int top, int left, int bottom, int right) {
        setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return this;
    }

    /**
     * Sets a line border with the specified color.
     * 
     * @param color the color of the border
     * @return This DSplitter instance.
     */
    public DSplitter borderLine(Color color) {
        setBorder(BorderFactory.createLineBorder(color));
        return this;
    }

    /**
     * Sets a line border with the specified color and thickness.
     * 
     * @param color the color of the border
     * @param thickness the thickness of the border
     * @return This DSplitter instance.
     */
    public DSplitter borderLine(Color color, int thickness) {
        setBorder(BorderFactory.createLineBorder(color, thickness));
        return this;
    }

    /**
     * Sets a line border with the specified color, thickness, and corner shape.
     * 
     * @param color the color of the border
     * @param thickness the thickness of the border
     * @param rounded whether or not border corners should be round
     * @return This DSplitter instance.
     */
    public DSplitter borderLine(Color color, int thickness, boolean rounded) {
        setBorder(BorderFactory.createLineBorder(color, thickness, rounded));
        return this;
    }

    /**
     * Sets an etched border.
     * 
     * @return This DSplitter instance.
     */
    public DSplitter borderEtched() {
        setBorder(BorderFactory.createEtchedBorder());
        return this;
    }

    /**
     * Sets an etched border with the specified type.
     * 
     * @param type EtchedBorder.RAISED or EtchedBorder.LOWERED
     * @return This DSplitter instance.
     */
    public DSplitter borderEtched(int type) {
        setBorder(BorderFactory.createEtchedBorder(type));
        return this;
    }

    /**
     * Sets an etched border with the specified highlight and shadow colors.
     * 
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return This DSplitter instance.
     */
    public DSplitter borderEtched(Color highlight, Color shadow) {
        setBorder(BorderFactory.createEtchedBorder(highlight, shadow));
        return this;
    }

    /**
     * Sets an etched border with the specified type, highlight, and shadow colors.
     * 
     * @param type EtchedBorder.RAISED or EtchedBorder.LOWERED
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return This DSplitter instance.
     */
    public DSplitter borderEtched(int type, Color highlight, Color shadow) {
        setBorder(BorderFactory.createEtchedBorder(type, highlight, shadow));
        return this;
    }

    /**
     * Sets a raised bevel border.
     * 
     * @return This DSplitter instance.
     */
    public DSplitter borderBevelRaised() {
        setBorder(BorderFactory.createRaisedBevelBorder());
        return this;
    }

    /**
     * Sets a lowered bevel border.
     * 
     * @return This DSplitter instance.
     */
    public DSplitter borderBevelLowered() {
        setBorder(BorderFactory.createLoweredBevelBorder());
        return this;
    }

    /**
     * Sets a bevel border with the specified type.
     * 
     * @param type BevelBorder.RAISED or BevelBorder.LOWERED
     * @return This DSplitter instance.
     */
    public DSplitter borderBevel(int type) {
        setBorder(BorderFactory.createBevelBorder(type));
        return this;
    }

    /**
     * Sets a bevel border with the specified type, highlight, and shadow colors.
     * 
     * @param type BevelBorder.RAISED or BevelBorder.LOWERED
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return This DSplitter instance.
     */
    public DSplitter borderBevel(int type, Color highlight, Color shadow) {
        setBorder(BorderFactory.createBevelBorder(type, highlight, shadow));
        return this;
    }

    /**
     * Sets a bevel border with the specified type and colors.
     * 
     * @param type BevelBorder.RAISED or BevelBorder.LOWERED
     * @param highlightOuter the color to use for the bevel outer highlight
     * @param highlightInner the color to use for the bevel inner highlight
     * @param shadowOuter the color to use for the bevel outer shadow
     * @param shadowInner the color to use for the bevel inner shadow
     * @return This DSplitter instance.
     */
    public DSplitter borderBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter, Color shadowInner) {
        setBorder(BorderFactory.createBevelBorder(type, highlightOuter, highlightInner, shadowOuter, shadowInner));
        return this;
    }

    /**
     * Sets a soft bevel border with the specified type.
     * 
     * @param type BevelBorder.RAISED or BevelBorder.LOWERED
     * @return This DSplitter instance.
     */
    public DSplitter borderSoftBevel(int type) {
        setBorder(BorderFactory.createSoftBevelBorder(type));
        return this;
    }

    /**
     * Sets a soft bevel border with the specified type, highlight, and shadow colors.
     * 
     * @param type BevelBorder.RAISED or BevelBorder.LOWERED
     * @param highlight the highlight color
     * @param shadow the shadow color
     * @return This DSplitter instance.
     */
    public DSplitter borderSoftBevel(int type, Color highlight, Color shadow) {
        setBorder(BorderFactory.createSoftBevelBorder(type, highlight, shadow));
        return this;
    }

    /**
     * Sets a soft bevel border with the specified type and colors.
     * 
     * @param type BevelBorder.RAISED or BevelBorder.LOWERED
     * @param highlightOuter the color to use for the bevel outer highlight
     * @param highlightInner the color to use for the bevel inner highlight
     * @param shadowOuter the color to use for the bevel outer shadow
     * @param shadowInner the color to use for the bevel inner shadow
     * @return This DSplitter instance.
     */
    public DSplitter borderSoftBevel(int type, Color highlightOuter, Color highlightInner, Color shadowOuter, Color shadowInner) {
        setBorder(BorderFactory.createSoftBevelBorder(type, highlightOuter, highlightInner, shadowOuter, shadowInner));
        return this;
    }

    /**
     * Sets a titled border with the specified title.
     * 
     * @param title the title the border should display
     * @return This DSplitter instance.
     */
    public DSplitter borderTitled(String title) {
        setBorder(BorderFactory.createTitledBorder(title));
        return this;
    }

    /**
     * Sets a titled border around an existing border.
     * 
     * @param border the border
     * @return This DSplitter instance.
     */
    public DSplitter borderTitled(Border border) {
        setBorder(BorderFactory.createTitledBorder(border));
        return this;
    }

    /**
     * Sets a titled border around an existing border with the specified title.
     * 
     * @param border the border
     * @param title the title the border should display
     * @return This DSplitter instance.
     */
    public DSplitter borderTitled(Border border, String title) {
        setBorder(BorderFactory.createTitledBorder(border, title));
        return this;
    }

    /**
     * Sets a titled border with the specified title, justification, and position.
     * 
     * @param title the title the border should display
     * @param justification the justification for the title
     * @param position the position for the title
     * @return This DSplitter instance.
     */
    public DSplitter borderTitled(String title, int justification, int position) {
        setBorder(BorderFactory.createTitledBorder(null, title, justification, position));
        return this;
    }

    /**
     * Sets a titled border with the specified title, justification, position, and font.
     * 
     * @param title the title the border should display
     * @param justification the justification for the title
     * @param position the position for the title
     * @param font the font for the title
     * @return This DSplitter instance.
     */
    public DSplitter borderTitled(String title, int justification, int position, Font font) {
        setBorder(BorderFactory.createTitledBorder(null, title, justification, position, font));
        return this;
    }

    /**
     * Sets a titled border with the specified title, justification, position, font, and color.
     * 
     * @param title the title the border should display
     * @param justification the justification for the title
     * @param position the position for the title
     * @param font the font for the title
     * @param color the color of the title
     * @return This DSplitter instance.
     */
    public DSplitter borderTitled(String title, int justification, int position, Font font, Color color) {
        setBorder(BorderFactory.createTitledBorder(null, title, justification, position, font, color));
        return this;
    }

    /**
     * Sets a titled border with the specified border, title, justification, position, font, and color.
     * 
     * @param border the border
     * @param title the title the border should display
     * @param justification the justification for the title
     * @param position the position for the title
     * @param font the font for the title
     * @param color the color of the title
     * @return This DSplitter instance.
     */
    public DSplitter borderTitled(Border border, String title, int justification, int position, Font font, Color color) {
        setBorder(BorderFactory.createTitledBorder(border, title, justification, position, font, color));
        return this;
    }

    /**
     * Sets a matte border.
     * 
     * @param top the top inset
     * @param left the left inset
     * @param bottom the bottom inset
     * @param right the right inset
     * @param color the color of the border
     * @return This DSplitter instance.
     */
    public DSplitter borderMatte(int top, int left, int bottom, int right, Color color) {
        setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, color));
        return this;
    }

    /**
     * Sets a matte border with an icon.
     * 
     * @param top the top inset
     * @param left the left inset
     * @param bottom the bottom inset
     * @param right the right inset
     * @param tileIcon the icon to be used for tiling the border
     * @return This DSplitter instance.
     */
    public DSplitter borderMatte(int top, int left, int bottom, int right, Icon tileIcon) {
        setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, tileIcon));
        return this;
    }

    /**
     * Sets a compound border.
     * 
     * @param outside the outside border
     * @param inside the inside border
     * @return This DSplitter instance.
     */
    public DSplitter borderCompound(Border outside, Border inside) {
        setBorder(BorderFactory.createCompoundBorder(outside, inside));
        return this;
    }

    /**
     * Sets the UI object which implements the L&F for this component.
     * 
     * @param ui the SplitPaneUI L&F object
     * @return This DSplitter instance.
     */
    public DSplitter ui(SplitPaneUI ui) {
        setUI(ui);
        return this;
    }

    /**
     * Sets whether this component is enabled.
     * 
     * @param b true to enable, false to disable
     * @return This DSplitter instance.
     */
    public DSplitter enabled(boolean b) {
        setEnabled(b);
        return this;
    }

    /**
     * Sets whether this component is visible.
     * 
     * @param b true to make visible, false to hide
     * @return This DSplitter instance.
     */
    public DSplitter visible(boolean b) {
        setVisible(b);
        return this;
    }

    /**
     * Sets whether this component is opaque.
     * 
     * @param b true to make opaque, false to make transparent
     * @return This DSplitter instance.
     */
    public DSplitter opaque(boolean b) {
        setOpaque(b);
        return this;
    }

    /**
     * Sets the bounds of this component.
     * 
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     * @param width the new width
     * @param height the new height
     * @return This DSplitter instance.
     */
    public DSplitter bounds(int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        return this;
    }

    /**
     * Sets the size of this component.
     * 
     * @param width the new width
     * @param height the new height
     * @return This DSplitter instance.
     */
    public DSplitter size(int width, int height) {
        setSize(width, height);
        return this;
    }

    /**
     * Sets the size of this component.
     * 
     * @param d the new size
     * @return This DSplitter instance.
     */
    public DSplitter size(Dimension d) {
        setSize(d);
        return this;
    }

    /**
     * Sets the preferred size of this component.
     * 
     * @param width the new preferred width
     * @param height the new preferred height
     * @return This DSplitter instance.
     */
    public DSplitter preferredSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        return this;
    }

    /**
     * Sets the preferred size of this component.
     * 
     * @param d the new preferred size
     * @return This DSplitter instance.
     */
    public DSplitter preferredSize(Dimension d) {
        setPreferredSize(d);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font
     * @return This DSplitter instance.
     */
    public DSplitter font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DSplitter instance.
     */
    public DSplitter foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DSplitter instance.
     */
    public DSplitter background(Color bg) {
        setBackground(bg);
        return this;
    }

    /**
     * Gets the name of this component.
     * 
     * @return the name
     */
    public String name() {
        return getName();
    }

    /**
     * Sets the name of this component.
     * 
     * @param name the name to set
     * @return This DSplitter instance.
     */
    public DSplitter name(String name) {
        setName(name);
        return this;
    }

    /**
     * Registers the text to display in a tool tip.
     * 
     * @param hint the string to display
     * @return This DSplitter instance.
     */
    public DSplitter hint(String hint) {
        setToolTipText(hint);
        return this;
    }

    /**
     * Gets the tool tip text.
     * 
     * @return the tool tip text
     */
    public String hint() {
        return getToolTipText();
    }

}
