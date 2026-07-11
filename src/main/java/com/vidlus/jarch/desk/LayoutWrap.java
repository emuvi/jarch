package com.vidlus.jarch.desk;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * A layout manager that extends {@link FlowLayout} and allows components to wrap
 * to the next line when the container width is insufficient. Unlike standard
 * {@code FlowLayout}, {@code WrapLayout} accurately calculates its preferred and
 * minimum sizes based on the wrapped components, making it suitable for use inside
 * components like {@link javax.swing.JScrollPane}.
 */
public class LayoutWrap extends FlowLayout {
    
    private Dimension preferredLayoutSize;

    /**
     * Constructs a new {@code WrapLayout} with a centered alignment and a default
     * 5-unit horizontal and vertical gap.
     */
    public LayoutWrap() {
        super();
    }

    /**
     * Constructs a new {@code WrapLayout} with the specified alignment and a default
     * 5-unit horizontal and vertical gap.
     *
     * @param align the alignment value
     */
    public LayoutWrap(int align) {
        super(align);
    }

    /**
     * Creates a new {@code WrapLayout} with the specified alignment and the specified
     * horizontal and vertical gaps.
     *
     * @param align the alignment value
     * @param hgap  the horizontal gap between components and between the components and the borders of the {@code Container}
     * @param vgap  the vertical gap between components and between the components and the borders of the {@code Container}
     */
    public LayoutWrap(int align, int hgap, int vgap) {
        super(align, hgap, vgap);
    }

    /**
     * Returns the preferred dimensions for this layout given the components
     * in the specified target container.
     *
     * @param target the container which needs to be laid out
     * @return the preferred dimensions to lay out the subcomponents of the specified container
     */
    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    /**
     * Returns the minimum dimensions needed to layout the components
     * contained in the specified target container.
     *
     * @param target the container which needs to be laid out
     * @return the minimum dimensions to lay out the subcomponents of the specified container
     */
    @Override
    public Dimension minimumLayoutSize(Container target) {
        Dimension minimum = layoutSize(target, false);
        minimum.width -= (getHgap() + 1);
        return minimum;
    }

    /**
     * Calculates the layout size based on the target container's width and
     * whether to calculate preferred or minimum size.
     *
     * @param target    the container which needs to be laid out
     * @param preferred whether to calculate the preferred size (true) or minimum size (false)
     * @return the layout dimensions
     */
    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth = target.getSize().width;
            Container container = target;

            while (container.getSize().width == 0 && container.getParent() != null) {
                container = container.getParent();
            }

            targetWidth = container.getSize().width;

            if (targetWidth == 0)
                targetWidth = Integer.MAX_VALUE;

            int hgap = getHgap();
            int vgap = getVgap();
            Insets insets = target.getInsets();
            int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
            int maxWidth = targetWidth - horizontalInsetsAndGap;

            Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            int nmembers = target.getComponentCount();

            for (int i = 0; i < nmembers; i++) {
                Component m = target.getComponent(i);

                if (m.isVisible()) {
                    Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                    if (rowWidth + d.width > maxWidth) {
                        addRow(dim, rowWidth, rowHeight);
                        rowWidth = 0;
                        rowHeight = 0;
                    }

                    if (rowWidth != 0) {
                        rowWidth += hgap;
                    }

                    rowWidth += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                }
            }

            addRow(dim, rowWidth, rowHeight);

            dim.width += horizontalInsetsAndGap;
            dim.height += insets.top + insets.bottom + vgap * 2;

            Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);

            if (scrollPane != null && target.isValid()) {
                dim.width -= (hgap + 1);
            }

            return dim;
        }
    }

    /**
     * Adds the current row's dimensions to the total layout dimensions.
     *
     * @param dim       the total layout dimensions to be updated
     * @param rowWidth  the width of the current row
     * @param rowHeight the height of the current row
     */
    private void addRow(Dimension dim, int rowWidth, int rowHeight) {
        dim.width = Math.max(dim.width, rowWidth);

        if (dim.height > 0) {
            dim.height += getVgap();
        }

        dim.height += rowHeight;
    }
}
