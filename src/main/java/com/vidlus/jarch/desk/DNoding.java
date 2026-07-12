package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * A highly typed helper class to treat and customize all DNode rendering
 * functionality.
 * Extend this class and override {@link #render} to easily inject custom icons,
 * fonts,
 * and colors into specific nodes in a DTree.
 */
public class DNoding extends DefaultTreeCellRenderer {

    /** Serialization version identifier. */
    private static final long serialVersionUID = 1L;

    /**
     * Intercepts the native JTree rendering pipeline, casting the tree and node to
     * their native D-types,
     * and forwards them to the highly-typed render method.
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
            int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        // Cast tree and value to our custom DTree and DNode types if applicable
        // and delegate to the user-overrideable render method.
        if (tree instanceof DTree && value instanceof DNode) {
            render((DTree) tree, (DNode) value, sel, expanded, leaf, row, hasFocus);
        }

        return this;
    }

    /**
     * Override this method to customize the visual appearance of a specific DNode.
     * Use native DefaultTreeCellRenderer methods (like setIcon, setForeground)
     * directly on `this` instance.
     * 
     * @param tree       the DTree containing the node
     * @param node       the DNode being rendered
     * @param isSelected true if the node is currently selected
     * @param isExpanded true if the node is currently expanded
     * @param isLeaf     true if the node is a leaf (has no children)
     * @param row        the display row index of the node
     * @param hasFocus   true if the node currently has focus
     */
    public void render(DTree tree, DNode node, boolean isSelected, boolean isExpanded, boolean isLeaf, int row,
            boolean hasFocus) {
        // Default implementation does nothing.
        // Users should override this to apply custom styles to 'this'.
    }

    /**
     * Sets the icon used to represent non-leaf nodes that are expanded.
     * 
     * @param newIcon the new icon
     * @return This DNoding instance.
     */
    public DNoding openIcon(DIcon newIcon) {
        setOpenIcon(newIcon);
        return this;
    }

    /**
     * Sets the icon used to represent non-leaf nodes that are not expanded.
     * 
     * @param newIcon the new icon
     * @return This DNoding instance.
     */
    public DNoding closedIcon(DIcon newIcon) {
        setClosedIcon(newIcon);
        return this;
    }

    /**
     * Sets the icon used to represent leaf nodes.
     * 
     * @param newIcon the new icon
     * @return This DNoding instance.
     */
    public DNoding leafIcon(DIcon newIcon) {
        setLeafIcon(newIcon);
        return this;
    }

    /**
     * Sets the color the text is drawn with when the node is selected.
     * 
     * @param newColor the new color
     * @return This DNoding instance.
     */
    public DNoding textSelectionColor(Color newColor) {
        setTextSelectionColor(newColor);
        return this;
    }

    /**
     * Sets the color the text is drawn with when the node isn't selected.
     * 
     * @param newColor the new color
     * @return This DNoding instance.
     */
    public DNoding textNonSelectionColor(Color newColor) {
        setTextNonSelectionColor(newColor);
        return this;
    }

    /**
     * Sets the color to use for the background if node is selected.
     * 
     * @param newColor the new color
     * @return This DNoding instance.
     */
    public DNoding backgroundSelectionColor(Color newColor) {
        setBackgroundSelectionColor(newColor);
        return this;
    }

    /**
     * Sets the background color to be used for non selected nodes.
     * 
     * @param newColor the new color
     * @return This DNoding instance.
     */
    public DNoding backgroundNonSelectionColor(Color newColor) {
        setBackgroundNonSelectionColor(newColor);
        return this;
    }

    /**
     * Sets the color to use for the border.
     * 
     * @param newColor the new color
     * @return This DNoding instance.
     */
    public DNoding borderSelectionColor(Color newColor) {
        setBorderSelectionColor(newColor);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font for this component
     * @return This DNoding instance.
     */
    public DNoding font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DNoding instance.
     */
    public DNoding background(Color bg) {
        setBackground(bg);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DNoding instance.
     */
    public DNoding foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * If true the component paints every pixel within its bounds.
     * 
     * @param isOpaque true if this component should be opaque
     * @return This DNoding instance.
     */
    public DNoding opaque(boolean isOpaque) {
        setOpaque(isOpaque);
        return this;
    }

    /**
     * Defines the icon this component will display.
     * 
     * @param icon the desired Icon
     * @return This DNoding instance.
     */
    public DNoding icon(DIcon icon) {
        setIcon(icon);
        return this;
    }

    /**
     * Defines the single line of text this component will display.
     * 
     * @param text the text
     * @return This DNoding instance.
     */
    public DNoding text(String text) {
        setText(text);
        return this;
    }

    /**
     * Registers the text to display in a tool tip.
     * 
     * @param text the string to display
     * @return This DNoding instance.
     */
    public DNoding toolTipText(String text) {
        setToolTipText(text);
        return this;
    }

    /**
     * Sets the alignment of the label's contents along the X axis.
     * 
     * @param alignment the alignment
     * @return This DNoding instance.
     */
    public DNoding horizontalAlignment(int alignment) {
        setHorizontalAlignment(alignment);
        return this;
    }

    /**
     * Sets the alignment of the label's contents along the Y axis.
     * 
     * @param alignment the alignment
     * @return This DNoding instance.
     */
    public DNoding verticalAlignment(int alignment) {
        setVerticalAlignment(alignment);
        return this;
    }

    /**
     * Sets the horizontal position of the label's text, relative to its image.
     * 
     * @param textPosition the position
     * @return This DNoding instance.
     */
    public DNoding horizontalTextPosition(int textPosition) {
        setHorizontalTextPosition(textPosition);
        return this;
    }

    /**
     * Sets the vertical position of the label's text, relative to its image.
     * 
     * @param textPosition the position
     * @return This DNoding instance.
     */
    public DNoding verticalTextPosition(int textPosition) {
        setVerticalTextPosition(textPosition);
        return this;
    }

    /**
     * If both the icon and text properties are set, this property defines the space
     * between them.
     * 
     * @param iconTextGap the space in pixels
     * @return This DNoding instance.
     */
    public DNoding iconTextGap(int iconTextGap) {
        setIconTextGap(iconTextGap);
        return this;
    }

    /**
     * Changes the font style to bold.
     * 
     * @return This DNoding instance.
     */
    public DNoding bold() {
        Font f = getFont();
        if (f != null) {
            setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        }
        return this;
    }

    /**
     * Changes the font style to italic.
     * 
     * @return This DNoding instance.
     */
    public DNoding italic() {
        Font f = getFont();
        if (f != null) {
            setFont(f.deriveFont(f.getStyle() | Font.ITALIC));
        }
        return this;
    }

    /**
     * Adds an empty border around the cell content to act as padding.
     * 
     * @param top the top padding
     * @param left the left padding
     * @param bottom the bottom padding
     * @param right the right padding
     * @return This DNoding instance.
     */
    public DNoding padding(int top, int left, int bottom, int right) {
        setBorder(new EmptyBorder(top, left, bottom, right));
        return this;
    }

    /**
     * Sets the background to transparent (not opaque).
     * 
     * @return This DNoding instance.
     */
    public DNoding transparent() {
        setOpaque(false);
        return this;
    }
}
