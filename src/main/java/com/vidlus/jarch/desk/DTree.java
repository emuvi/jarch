package com.vidlus.jarch.desk;

import java.awt.Color;
import java.awt.Font;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * A fluent API wrapper for JTree to easily create and configure tree components.
 */
public class DTree extends JTree {

    /**
     * Returns a JTree with a sample model.
     */
    public DTree() {
        super();
    }

    /**
     * Returns a JTree with each element of the specified array as the child of a new root node which is not displayed.
     * 
     * @param value an array of Objects
     */
    public DTree(Object[] value) {
        super(value);
    }

    /**
     * Returns a JTree with each element of the specified Vector as the child of a new root node which is not displayed.
     * 
     * @param value a Vector
     */
    public DTree(Vector<?> value) {
        super(value);
    }

    /**
     * Returns a JTree created from a Hashtable which does not display with root.
     * 
     * @param value a Hashtable
     */
    public DTree(Hashtable<?, ?> value) {
        super(value);
    }

    /**
     * Returns a JTree with the specified TreeNode as its root, which displays the root node.
     * 
     * @param root a TreeNode object
     */
    public DTree(TreeNode root) {
        super(root);
    }

    /**
     * Returns a JTree with the specified TreeNode as its root, which displays the root node and which decides whether a node is a leaf node in the specified manner.
     * 
     * @param root a TreeNode object
     * @param asksAllowsChildren if false, any node without children is a leaf node; if true, only nodes that do not allow children are leaf nodes
     */
    public DTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    /**
     * Returns an instance of JTree which displays the root node -- the tree is created using the specified data model.
     * 
     * @param newModel the TreeModel to use as the data model
     */
    public DTree(TreeModel newModel) {
        super(newModel);
    }

    /**
     * Sets the TreeModel that will provide the data.
     * 
     * @param newModel the TreeModel that is to provide the data
     * @return This DTree instance.
     */
    public DTree model(TreeModel newModel) {
        setModel(newModel);
        return this;
    }

    /**
     * Determines whether or not the root node from the TreeModel is visible.
     * 
     * @param rootVisible true if the root node of the tree is to be displayed
     * @return This DTree instance.
     */
    public DTree rootVisible(boolean rootVisible) {
        setRootVisible(rootVisible);
        return this;
    }

    /**
     * Sets the value of the showsRootHandles property, which specifies whether the node handles should be displayed.
     * 
     * @param showsRootHandles true if root handles should be displayed
     * @return This DTree instance.
     */
    public DTree showsRootHandles(boolean showsRootHandles) {
        setShowsRootHandles(showsRootHandles);
        return this;
    }

    /**
     * Sets the height of each cell, in pixels.
     * 
     * @param rowHeight the height of each cell, in pixels
     * @return This DTree instance.
     */
    public DTree rowHeight(int rowHeight) {
        setRowHeight(rowHeight);
        return this;
    }

    /**
     * Determines whether the tree is editable.
     * 
     * @param flag a boolean value, true if the tree is editable
     * @return This DTree instance.
     */
    public DTree editable(boolean flag) {
        setEditable(flag);
        return this;
    }

    /**
     * Sets the tree's selection model.
     * 
     * @param selectionModel the TreeSelectionModel to use.
     * @return This DTree instance.
     */
    public DTree selectionModel(TreeSelectionModel selectionModel) {
        setSelectionModel(selectionModel);
        return this;
    }

    /**
     * Sets the TreeCellRenderer that will be used to draw each cell.
     * 
     * @param x the TreeCellRenderer that is to render each cell
     * @return This DTree instance.
     */
    public DTree cellRenderer(TreeCellRenderer x) {
        setCellRenderer(x);
        return this;
    }

    /**
     * Selects the node at the specified row.
     * 
     * @param row the row to select, where 0 is the first row in the display
     * @return This DTree instance.
     */
    public DTree selectionRow(int row) {
        setSelectionRow(row);
        return this;
    }

    /**
     * Selects the node identified by the specified path.
     * 
     * @param path the TreePath specifying the node to select
     * @return This DTree instance.
     */
    public DTree selectionPath(TreePath path) {
        setSelectionPath(path);
        return this;
    }

    /**
     * Sets the font of this component.
     * 
     * @param font the desired Font for this component
     * @return This DTree instance.
     */
    public DTree font(Font font) {
        setFont(font);
        return this;
    }

    /**
     * Sets the foreground color of this component.
     * 
     * @param fg the desired foreground Color
     * @return This DTree instance.
     */
    public DTree foreground(Color fg) {
        setForeground(fg);
        return this;
    }

    /**
     * Sets the background color of this component.
     * 
     * @param bg the desired background Color
     * @return This DTree instance.
     */
    public DTree background(Color bg) {
        setBackground(bg);
        return this;
    }

    /**
     * Expands all nodes in the tree.
     * 
     * @return This DTree instance.
     */
    public DTree expandAll() {
        for (int i = 0; i < getRowCount(); i++) {
            expandRow(i);
        }
        return this;
    }

    /**
     * Collapses all nodes in the tree.
     * 
     * @return This DTree instance.
     */
    public DTree collapseAll() {
        for (int i = getRowCount() - 1; i >= 0; i--) {
            collapseRow(i);
        }
        return this;
    }
}
