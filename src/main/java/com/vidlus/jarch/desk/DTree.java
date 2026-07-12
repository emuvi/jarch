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
     * Sets the TreeCellEditor that will be used to edit each cell.
     * 
     * @param editor the TreeCellEditor that is to edit each cell
     * @return This DTree instance.
     */
    public DTree cellEditor(javax.swing.tree.TreeCellEditor editor) {
        setCellEditor(editor);
        return this;
    }

    /**
     * Sets the cell editor dynamically using any custom DEdit component.
     * This automatically bridges the DEdit's value flow and action events into the JTree.
     * 
     * @param editor the DEdit component to adapt and use as the cell editor
     * @return this DTree instance for fluent method chaining.
     */
    public DTree cellEditor(DEdit<?> editor) {
        return cellEditor(new DEditTreeCellEditorAdapter(editor));
    }

    /**
     * Determines what happens when editing is interrupted by selecting another node in the tree, a change in the tree's data, or by some other means.
     * 
     * @param newValue true if the cell should automatically save when stopped
     * @return This DTree instance.
     */
    public DTree invokesStopCellEditing(boolean newValue) {
        setInvokesStopCellEditing(newValue);
        return this;
    }

    /**
     * Sets the scrollsOnExpand property, which determines whether the tree might scroll to show previously hidden children.
     * 
     * @param newValue false to disable scrolling on expand
     * @return This DTree instance.
     */
    public DTree scrollsOnExpand(boolean newValue) {
        setScrollsOnExpand(newValue);
        return this;
    }

    /**
     * Sets the number of mouse clicks before a node will expand or close.
     * 
     * @param clickCount the number of clicks required to expand/collapse
     * @return This DTree instance.
     */
    public DTree toggleClickCount(int clickCount) {
        setToggleClickCount(clickCount);
        return this;
    }

    /**
     * Sets the number of rows that are to be displayed.
     * 
     * @param newCount the number of rows to display
     * @return This DTree instance.
     */
    public DTree visibleRowCount(int newCount) {
        setVisibleRowCount(newCount);
        return this;
    }

    /**
     * Specifies whether the UI should use a large model.
     * 
     * @param newValue true to suggest a large model to the UI
     * @return This DTree instance.
     */
    public DTree largeModel(boolean newValue) {
        setLargeModel(newValue);
        return this;
    }

    /**
     * Sets the dragEnabled property, which must be true to enable automatic drag handling.
     * 
     * @param b whether or not to enable automatic drag handling
     * @return this DTree instance for fluent method chaining.
     */
    public DTree dragEnabled(boolean b) {
        setDragEnabled(b);
        return this;
    }

    /**
     * Sets the drop mode for this component.
     * 
     * @param dropMode the drop mode to use
     * @return this DTree instance for fluent method chaining.
     */
    public DTree dropMode(javax.swing.DropMode dropMode) {
        setDropMode(dropMode);
        return this;
    }

    /**
     * Sets the tree's selection mode (e.g., SINGLE_TREE_SELECTION).
     * 
     * @param mode the selection mode (from TreeSelectionModel)
     * @return This DTree instance.
     */
    public DTree selectionMode(int mode) {
        getSelectionModel().setSelectionMode(mode);
        return this;
    }

    /**
     * Clears the selection.
     * 
     * @return This DTree instance.
     */
    public DTree clearTreeSelection() {
        clearSelection();
        return this;
    }

    /**
     * Adds the node at the specified row to the current selection.
     * 
     * @param row the row to select
     * @return This DTree instance.
     */
    public DTree appendSelectionRow(int row) {
        super.addSelectionRow(row);
        return this;
    }

    /**
     * Adds the nodes at the specified rows to the current selection.
     * 
     * @param rows an array of ints specifying the rows to add
     * @return This DTree instance.
     */
    public DTree appendSelectionRows(int[] rows) {
        super.addSelectionRows(rows);
        return this;
    }

    /**
     * Adds the node identified by the specified TreePath to the current selection.
     * 
     * @param path the path to select
     * @return This DTree instance.
     */
    public DTree appendSelectionPath(TreePath path) {
        super.addSelectionPath(path);
        return this;
    }

    /**
     * Adds the paths in the array of TreePaths to the current selection.
     * 
     * @param paths an array of TreePath objects that specifies the nodes to add
     * @return This DTree instance.
     */
    public DTree appendSelectionPaths(TreePath[] paths) {
        super.addSelectionPaths(paths);
        return this;
    }

    /**
     * Selects the nodes at the specified rows.
     * 
     * @param rows an array of ints specifying the rows to select
     * @return This DTree instance.
     */
    public DTree selectionRows(int[] rows) {
        setSelectionRows(rows);
        return this;
    }

    /**
     * Selects the nodes identified by the specified array of paths.
     * 
     * @param paths an array of TreePath objects that specifies the nodes to select
     * @return This DTree instance.
     */
    public DTree selectionPaths(TreePath[] paths) {
        setSelectionPaths(paths);
        return this;
    }

    /**
     * Configures the expandsSelectedPaths property.
     * 
     * @param newValue true to expand paths automatically when selected
     * @return This DTree instance.
     */
    public DTree expandsSelectedPaths(boolean newValue) {
        setExpandsSelectedPaths(newValue);
        return this;
    }

    /**
     * Adds a listener for TreeSelection events.
     * 
     * @param tsl the TreeSelectionListener that will be notified when a node is selected or deselected
     * @return This DTree instance.
     */
    public DTree onSelection(javax.swing.event.TreeSelectionListener tsl) {
        super.addTreeSelectionListener(tsl);
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

    /**
     * An adapter that allows any DEdit component to function as a native JTree TreeCellEditor.
     * This class bridges the Swing JTree cell editing lifecycle with the custom event and data 
     * flow of the desk package's DEdit components.
     */
    public static class DEditTreeCellEditorAdapter extends javax.swing.AbstractCellEditor implements javax.swing.tree.TreeCellEditor {
        
        /** The underlying DEdit component doing the actual editing. */
        protected final DEdit<?> editor;

        /**
         * Constructs a new tree cell editor adapter wrapping the given DEdit component.
         * 
         * @param editor the DEdit component to use for editing
         */
        public DEditTreeCellEditorAdapter(DEdit<?> editor) {
            this.editor = editor;
            // Hook into DEdit's custom action pipeline to gracefully stop editing
            editor.onAction(e -> stopCellEditing());
        }

        /**
         * Returns the value contained in the editor.
         * 
         * @return the current value from the DEdit component
         */
        @Override
        public Object getCellEditorValue() {
            return editor.value();
        }

        /**
         * Sets an initial value for the editor and returns the component that should be added to the client's Component hierarchy.
         * 
         * @param tree the JTree that is asking the editor to edit
         * @param value the value of the cell to be edited
         * @param isSelected true if the cell is to be rendered with highlighting
         * @param expanded true if the node is expanded
         * @param leaf true if the node is a leaf node
         * @param row the row index of the node being edited
         * @return the component for editing
         */
        @SuppressWarnings("unchecked")
        @Override
        public java.awt.Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
            try {
                // In JTree, the value is often a DefaultMutableTreeNode containing the user object.
                // It's safer to extract the user object if possible to feed the correct data to DEdit.
                Object actualValue = value;
                if (value instanceof javax.swing.tree.DefaultMutableTreeNode) {
                    actualValue = ((javax.swing.tree.DefaultMutableTreeNode) value).getUserObject();
                }
                ((DEdit<Object>) editor).value(actualValue);
            } catch (Exception ex) {
                // Ignore class cast or mismatch exceptions during initial value set.
            }
            
            return editor.comp();
        }
    }
}
