package com.vidlus.jarch.desk;




import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.swing.AbstractCellEditor;
import javax.swing.DropMode;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * A fluent API wrapper for JTree to easily create and configure tree
 * components.
 */
public class DTree extends JTree {

    /**
     * Constructs a DTree with a sample model.
     */
    public DTree() {
        super();
    }

    /**
     * Constructs a DTree with each element of the specified array as the child of a
     * new root node which is not displayed.
     * 
     * @param value an array of Objects
     */
    public DTree(Object[] value) {
        super(value);
    }

    /**
     * Constructs a DTree with each element of the specified Vector as the child of a
     * new root node which is not displayed.
     * 
     * @param value a Vector
     */
    public DTree(Vector<?> value) {
        super(value);
    }

    /**
     * Constructs a DTree created from a Hashtable which does not display with root.
     * 
     * @param value a Hashtable
     */
    public DTree(Hashtable<?, ?> value) {
        super(value);
    }

    /**
     * Constructs a DTree with the specified TreeNode as its root, which displays the
     * root node.
     * 
     * @param root a TreeNode object
     */
    public DTree(TreeNode root) {
        super(root);
    }

    /**
     * Constructs a DTree with the specified TreeNode as its root, which displays the
     * root node and which decides whether a node is a leaf node in the specified
     * manner.
     * 
     * @param root               a TreeNode object
     * @param asksAllowsChildren if false, any node without children is a leaf node;
     *                           if true, only nodes that do not allow children are
     *                           leaf nodes
     */
    public DTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    /**
     * Constructs an instance of DTree which displays the root node -- the tree is
     * created using the specified data model.
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
     * Sets the value of the showsRootHandles property, which specifies whether the
     * node handles should be displayed.
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
    public DTree selectionPath(DBranch path) {
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
    public DTree cellEditor(TreeCellEditor editor) {
        setCellEditor(editor);
        return this;
    }

    /**
     * Sets the cell editor dynamically using any custom DEdit component.
     * This automatically bridges the DEdit's value flow and action events into the
     * JTree.
     * 
     * @param editor the DEdit component to adapt and use as the cell editor
     * @return this DTree instance for fluent method chaining.
     */
    public DTree cellEditor(DEdit<?> editor) {
        return cellEditor(new DEditTreeCellEditorAdapter(editor));
    }

    /**
     * Determines what happens when editing is interrupted by selecting another node
     * in the tree, a change in the tree's data, or by some other means.
     * 
     * @param newValue true if the cell should automatically save when stopped
     * @return This DTree instance.
     */
    public DTree invokesStopCellEditing(boolean newValue) {
        setInvokesStopCellEditing(newValue);
        return this;
    }

    /**
     * Sets the scrollsOnExpand property, which determines whether the tree might
     * scroll to show previously hidden children.
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
     * Sets the dragEnabled property, which must be true to enable automatic drag
     * handling.
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
    public DTree dropMode(DropMode dropMode) {
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
    public DTree appendSelectionPath(DBranch path) {
        super.addSelectionPath(path);
        return this;
    }

    /**
     * Adds the paths in the array of TreePaths to the current selection.
     * 
     * @param paths an array of TreePath objects that specifies the nodes to add
     * @return This DTree instance.
     */
    public DTree appendSelectionPaths(DBranch[] paths) {
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
    public DTree selectionPaths(DBranch[] paths) {
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
     * @param tsl the TreeSelectionListener that will be notified when a node is
     *            selected or deselected
     * @return This DTree instance.
     */
    public DTree onSelection(TreeSelectionListener tsl) {
        super.addTreeSelectionListener(tsl);
        return this;
    }

    /**
     * Dynamically adds a new child node to a parent node and updates the UI model
     * automatically.
     * 
     * @param parent   the parent DNode
     * @param newChild the new child DNode to add
     * @return This DTree instance.
     */
    public DTree addNode(DNode parent, DNode newChild) {
        if (getModel() instanceof DefaultTreeModel) {
            DefaultTreeModel model = (DefaultTreeModel) getModel();
            model.insertNodeInto(newChild, parent, parent.getChildCount());
        } else {
            parent.addNode(newChild);
        }
        return this;
    }

    /**
     * Dynamically removes a node from the tree and updates the UI model
     * automatically.
     * 
     * @param node the DNode to remove
     * @return This DTree instance.
     */
    public DTree removeNode(DNode node) {
        if (getModel() instanceof DefaultTreeModel) {
            DefaultTreeModel model = (DefaultTreeModel) getModel();
            model.removeNodeFromParent(node);
        } else {
            node.removeFromParentNode();
        }
        return this;
    }

    /**
     * Dynamically updates the user object of a node and notifies the UI model to
     * redraw it.
     * 
     * @param node          the DNode to edit
     * @param newUserObject the new user object
     * @return This DTree instance.
     */
    public DTree editNode(DNode node, Object newUserObject) {
        node.userObject(newUserObject);
        if (getModel() instanceof DefaultTreeModel) {
            DefaultTreeModel model = (DefaultTreeModel) getModel();
            model.nodeChanged(node);
        }
        return this;
    }

    /**
     * Dynamically reorders a node by moving it to a new index under its current
     * parent and updates the UI.
     * 
     * @param node     the DNode to reorder
     * @param newIndex the new index position
     * @return This DTree instance.
     */
    public DTree reorderNode(DNode node, int newIndex) {
        TreeNode parentNode = node.getParent();
        if (parentNode instanceof DNode) {
            DNode parent = (DNode) parentNode;
            if (getModel() instanceof DefaultTreeModel) {
                DefaultTreeModel model = (DefaultTreeModel) getModel();
                model.removeNodeFromParent(node);
                model.insertNodeInto(node, parent, newIndex);
            } else {
                parent.removeNode(node);
                parent.insertNode(node, newIndex);
            }
        }
        return this;
    }

    /**
     * Ensures that the node identified by the specified path is expanded and
     * viewable.
     * 
     * @param path the path to expand
     * @return This DTree instance.
     */
    public DTree showPath(DBranch path) {
        super.expandPath(path);
        return this;
    }

    /**
     * Ensures that the node in the specified row is expanded and viewable.
     * 
     * @param row the row to expand
     * @return This DTree instance.
     */
    public DTree showRow(int row) {
        super.expandRow(row);
        return this;
    }

    /**
     * Ensures that the node identified by the specified path is collapsed so that
     * its children are not viewable.
     * 
     * @param path the path to collapse
     * @return This DTree instance.
     */
    public DTree hidePath(DBranch path) {
        super.collapsePath(path);
        return this;
    }

    /**
     * Ensures that the node in the specified row is collapsed so that its children
     * are not viewable.
     * 
     * @param row the row to collapse
     * @return This DTree instance.
     */
    public DTree hideRow(int row) {
        super.collapseRow(row);
        return this;
    }

    /**
     * Ensures that the node identified by path is currently viewable.
     * 
     * @param path the path to make visible
     * @return This DTree instance.
     */
    public DTree ensureVisible(DBranch path) {
        super.makeVisible(path);
        return this;
    }

    /**
     * Returns the DBranch for the specified row.
     * 
     * @param row an integer specifying a row
     * @return the DBranch to the specified node, or null
     */
    public DBranch branchForRow(int row) {
        TreePath path = super.getPathForRow(row);
        return path == null ? null : new DBranch(path.getPath());
    }

    /**
     * Returns the row that displays the node identified by the specified branch.
     * 
     * @param branch the DBranch identifying a node
     * @return an integer specifying the display row, or -1 if any of the elements
     *         in path are hidden
     */
    public int rowForBranch(DBranch branch) {
        return super.getRowForPath(branch);
    }

    /**
     * Returns the branch for the node at the specified location.
     * 
     * @param x an integer giving the number of pixels horizontally from the left
     *          edge of the display area
     * @param y an integer giving the number of pixels vertically from the top of
     *          the display area
     * @return the DBranch for the node at that location, or null
     */
    public DBranch branchForLocation(int x, int y) {
        TreePath path = super.getPathForLocation(x, y);
        return path == null ? null : new DBranch(path.getPath());
    }

    /**
     * Returns the branch to the node that is closest to x,y.
     * 
     * @param x an integer giving the number of pixels horizontally from the left
     *          edge of the display area
     * @param y an integer giving the number of pixels vertically from the top of
     *          the display area
     * @return the DBranch for the node closest to that location, or null
     */
    public DBranch closestBranchForLocation(int x, int y) {
        TreePath path = super.getClosestPathForLocation(x, y);
        return path == null ? null : new DBranch(path.getPath());
    }

    /**
     * Returns the path to the element that is currently being edited.
     * 
     * @return the DBranch for the node being edited
     */
    public DBranch editingBranch() {
        TreePath path = super.getEditingPath();
        return path == null ? null : new DBranch(path.getPath());
    }

    /**
     * Returns the path to the first selected node.
     * 
     * @return the DBranch for the first selected node, or null if nothing is
     *         currently selected
     */
    public DBranch selectionBranch() {
        TreePath path = super.getSelectionPath();
        return path == null ? null : new DBranch(path.getPath());
    }

    /**
     * Returns the paths of all selected values.
     * 
     * @return an array of DBranch objects indicating the selected nodes, or null if
     *         nothing is currently selected
     */
    public DBranch[] selectionBranches() {
        TreePath[] paths = super.getSelectionPaths();
        if (paths == null)
            return null;
        DBranch[] branches = new DBranch[paths.length];
        for (int i = 0; i < paths.length; i++) {
            branches[i] = new DBranch(paths[i].getPath());
        }
        return branches;
    }

    /**
     * Returns the Rectangle that the specified node will be drawn into.
     * 
     * @param branch the DBranch identifying the node
     * @return the bounds of the node
     */
    public Rectangle branchBounds(DBranch branch) {
        return super.getPathBounds(branch);
    }

    /**
     * Returns true if the node identified by the path is currently selected.
     * 
     * @param branch a DBranch identifying a node
     * @return true if the node is selected
     */
    public boolean isBranchSelected(DBranch branch) {
        return super.isPathSelected(branch);
    }

    /**
     * Returns true if the node identified by the path is currently expanded.
     * 
     * @param branch the DBranch specifying the node to check
     * @return true if the node is expanded
     */
    public boolean isBranchExpanded(DBranch branch) {
        return super.isExpanded(branch);
    }

    /**
     * Returns true if the node identified by the path is currently collapsed.
     * 
     * @param branch the DBranch specifying the node to check
     * @return true if the node is collapsed
     */
    public boolean isBranchCollapsed(DBranch branch) {
        return super.isCollapsed(branch);
    }

    /**
     * Returns true if the value identified by path is currently viewable.
     * 
     * @param branch the DBranch specifying the node to check
     * @return true if the node is visible
     */
    public boolean isBranchVisible(DBranch branch) {
        return super.isVisible(branch);
    }

    /**
     * Selects the node identified by the specified path and initiates editing.
     * 
     * @param branch the DBranch identifying a node
     * @return This DTree instance.
     */
    public DTree editBranch(DBranch branch) {
        super.startEditingAtPath(branch);
        return this;
    }

    /**
     * Makes sure all the path components in path are expanded (except for the last
     * path component) and scrolls so that the node identified by the path is
     * displayed.
     * 
     * @param branch the DBranch identifying a node
     * @return This DTree instance.
     */
    public DTree scrollBranchToVisible(DBranch branch) {
        super.scrollPathToVisible(branch);
        return this;
    }

    /**
     * Scrolls the item identified by row until it is displayed.
     * 
     * @param row an integer specifying a row
     * @return This DTree instance.
     */
    public DTree scrollToRow(int row) {
        super.scrollRowToVisible(row);
        return this;
    }

    /**
     * Ensures that the specified node is expanded and viewable.
     * 
     * @param node the DNode to expand
     * @return This DTree instance.
     */
    public DTree showNode(DNode node) {
        return showPath(node.branch());
    }

    /**
     * Ensures that the specified node is collapsed so that its children are not
     * viewable.
     * 
     * @param node the DNode to collapse
     * @return This DTree instance.
     */
    public DTree hideNode(DNode node) {
        return hidePath(node.branch());
    }

    /**
     * Ensures that the specified node is currently viewable.
     * 
     * @param node the DNode to make visible
     * @return This DTree instance.
     */
    public DTree ensureVisible(DNode node) {
        return ensureVisible(node.branch());
    }

    /**
     * Selects the specified node, clearing any previous selection.
     * 
     * @param node the DNode to select
     * @return This DTree instance.
     */
    public DTree selectionNode(DNode node) {
        return selectionPath(node.branch());
    }

    /**
     * Adds the specified node to the current selection.
     * 
     * @param node the DNode to add to selection
     * @return This DTree instance.
     */
    public DTree appendSelectionNode(DNode node) {
        return appendSelectionPath(node.branch());
    }

    /**
     * Returns the row that displays the specified node.
     * 
     * @param node the DNode to locate
     * @return an integer specifying the display row, or -1 if hidden
     */
    public int rowForNode(DNode node) {
        return rowForBranch(node.branch());
    }

    /**
     * Returns true if the specified node is currently selected.
     * 
     * @param node the DNode to check
     * @return true if selected
     */
    public boolean isNodeSelected(DNode node) {
        return isBranchSelected(node.branch());
    }

    /**
     * Returns true if the specified node is currently expanded.
     * 
     * @param node the DNode to check
     * @return true if expanded
     */
    public boolean isNodeExpanded(DNode node) {
        return isBranchExpanded(node.branch());
    }

    /**
     * Returns true if the specified node is currently collapsed.
     * 
     * @param node the DNode to check
     * @return true if collapsed
     */
    public boolean isNodeCollapsed(DNode node) {
        return isBranchCollapsed(node.branch());
    }

    /**
     * Returns true if the specified node is currently viewable.
     * 
     * @param node the DNode to check
     * @return true if visible
     */
    public boolean isNodeVisible(DNode node) {
        return isBranchVisible(node.branch());
    }

    /**
     * Selects the specified node and initiates UI editing on it.
     * 
     * @param node the DNode to edit
     * @return This DTree instance.
     */
    public DTree startEditingNode(DNode node) {
        return editBranch(node.branch());
    }

    /**
     * Scrolls the viewpane so that the specified node is displayed.
     * 
     * @param node the DNode to scroll to
     * @return This DTree instance.
     */
    public DTree scrollNodeToVisible(DNode node) {
        return scrollBranchToVisible(node.branch());
    }

    /**
     * Returns the DNode displayed at the specified row.
     * 
     * @param row an integer specifying a row
     * @return the DNode at that row, or null
     */
    public DNode nodeForRow(int row) {
        DBranch branch = branchForRow(row);
        return branch == null ? null : branch.lastNode();
    }

    /**
     * Returns the DNode at the specified coordinate location.
     * 
     * @param x the horizontal location
     * @param y the vertical location
     * @return the DNode at that location, or null
     */
    public DNode nodeForLocation(int x, int y) {
        DBranch branch = branchForLocation(x, y);
        return branch == null ? null : branch.lastNode();
    }

    /**
     * Returns the DNode closest to the specified coordinate location.
     * 
     * @param x the horizontal location
     * @param y the vertical location
     * @return the closest DNode, or null
     */
    public DNode closestNodeForLocation(int x, int y) {
        DBranch branch = closestBranchForLocation(x, y);
        return branch == null ? null : branch.lastNode();
    }

    /**
     * Returns the DNode that is currently being edited in the UI.
     * 
     * @return the editing DNode, or null
     */
    public DNode editingNode() {
        DBranch branch = editingBranch();
        return branch == null ? null : branch.lastNode();
    }

    /**
     * Returns the first selected DNode.
     * 
     * @return the selected DNode, or null
     */
    public DNode selectionNode() {
        DBranch branch = selectionBranch();
        return branch == null ? null : branch.lastNode();
    }

    /**
     * Returns an array of all currently selected DNodes.
     * 
     * @return an array of selected DNodes, or null if nothing is selected
     */
    public DNode[] selectionNodes() {
        DBranch[] branches = selectionBranches();
        if (branches == null)
            return null;
        DNode[] nodes = new DNode[branches.length];
        for (int i = 0; i < branches.length; i++) {
            nodes[i] = branches[i].lastNode();
        }
        return nodes;
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
     * Adds a listener for TreeExpansion events (when a node is successfully
     * expanded or collapsed).
     * 
     * @param tel the TreeExpansionListener
     * @return This DTree instance.
     */
    public DTree onExpand(TreeExpansionListener tel) {
        super.addTreeExpansionListener(tel);
        return this;
    }

    /**
     * Adds a listener for TreeWillExpand events (before a node expands or
     * collapses, which can be vetoed).
     * 
     * @param twel the TreeWillExpandListener
     * @return This DTree instance.
     */
    public DTree onWillExpand(TreeWillExpandListener twel) {
        super.addTreeWillExpandListener(twel);
        return this;
    }

    /**
     * Attaches a mouse listener that triggers the provided consumer when a node is
     * single-clicked.
     * 
     * @param action a Consumer that accepts the clicked DNode
     * @return This DTree instance.
     */
    public DTree onClickNode(Consumer<DNode> action) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    DNode node = closestNodeForLocation(e.getX(), e.getY());
                    if (node != null) {
                        // Ensure the click actually occurred on the node's visual bounds
                        Rectangle bounds = branchBounds(node.branch());
                        if (bounds != null && bounds.contains(e.getPoint())) {
                            action.accept(node);
                        }
                    }
                }
            }
        });
        return this;
    }

    /**
     * Attaches a mouse listener that triggers the provided consumer when a node is
     * double-clicked.
     * 
     * @param action a Consumer that accepts the double-clicked DNode
     * @return This DTree instance.
     */
    public DTree onDoubleClickNode(Consumer<DNode> action) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DNode node = closestNodeForLocation(e.getX(), e.getY());
                    if (node != null) {
                        Rectangle bounds = branchBounds(node.branch());
                        if (bounds != null && bounds.contains(e.getPoint())) {
                            action.accept(node);
                        }
                    }
                }
            }
        });
        return this;
    }

    /**
     * Searches the entire tree model (preorder) to find the first node matching the
     * specified user object.
     * 
     * @param userObject the user object to search for
     * @return the found DNode, or null if not found
     */
    public DNode searchNode(Object userObject) {
        TreeModel model = getModel();
        if (model != null && model.getRoot() instanceof DNode) {
            DNode root = (DNode) model.getRoot();
            Stream<DNode> stream = root.preorderStream();
            return stream.filter(n -> n.getUserObject() != null && n.getUserObject().equals(userObject))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Sets the visual line style of the JTree (specific to certain Look and Feels
     * like Metal).
     * Common values: "Angled", "Horizontal", "None".
     * 
     * @param style the line style string
     * @return This DTree instance.
     */
    public DTree lineStyle(String style) {
        putClientProperty("JTree.lineStyle", style);
        return this;
    }

    /**
     * Expands the specified node and all of its descendants recursively.
     * 
     * @param node the DNode to recursively expand
     * @return This DTree instance.
     */
    public DTree showNodeRecursively(DNode node) {
        node.preorderStream().forEach(n -> showNode(n));
        return this;
    }

    /**
     * Collapses the specified node and all of its descendants recursively.
     * 
     * @param node the DNode to recursively collapse
     * @return This DTree instance.
     */
    public DTree hideNodeRecursively(DNode node) {
        node.postorderStream().forEach(n -> hideNode(n));
        return this;
    }

    /**
     * Attaches a lightweight rendering lambda to completely customize how nodes look.
     * The provided BiConsumer receives the active DefaultTreeCellRenderer and the target DNode,
     * allowing you to easily change icons, text colors, and fonts based on node data.
     * 
     * @param action a BiConsumer that accepts the active renderer and the DNode
     * @return This DTree instance.
     */
    public DTree onRender(BiConsumer<DefaultTreeCellRenderer, DNode> action) {
        setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if (value instanceof DNode) {
                    action.accept(this, (DNode) value);
                }
                return this;
            }
        });
        return this;
    }

    /**
     * Attaches a highly-typed DNoding helper class to handle complete visual customization of nodes.
     * This acts as the official native cell renderer replacement.
     * 
     * @param noding the DNoding instance
     * @return This DTree instance.
     */
    public DTree onNoding(DNoding noding) {
        setCellRenderer(noding);
        return this;
    }

    /**
     * Expands all nodes in the tree up to the specified maximum depth level.
     * 
     * @param depth the maximum depth level to expand
     * @return This DTree instance.
     */
    public DTree expandLevel(int depth) {
        DNode root = getRoot();
        if (root != null) {
            root.preorderStream().filter(n -> n.getLevel() <= depth).forEach(n -> showPath(n.branch()));
        }
        return this;
    }

    /**
     * Ensures that the node identified by the specified branch is expanded and viewable.
     * Alias for showPath.
     * 
     * @param branch the DBranch identifying a node
     * @return This DTree instance.
     */
    public DTree expandBranch(DBranch branch) {
        super.expandPath(branch);
        return this;
    }

    /**
     * Ensures that the node identified by the specified branch is collapsed so that its children are not viewable.
     * Alias for hidePath.
     * 
     * @param branch the DBranch identifying a node
     * @return This DTree instance.
     */
    public DTree collapseBranch(DBranch branch) {
        super.collapsePath(branch);
        return this;
    }

    /**
     * Clears the tree by completely wiping the model and setting the root to null.
     * 
     * @return This DTree instance.
     */
    public DTree clear() {
        setModel(new DefaultTreeModel(null));
        return this;
    }

    /**
     * Replaces the entire tree model with a new DefaultTreeModel anchored to the
     * specified root node.
     * This is a convenient shortcut to avoid manually wrapping the root in a new
     * model.
     * 
     * @param newRoot the new root node
     * @return This DTree instance.
     */
    public DTree root(DNode newRoot) {
        setModel(new DefaultTreeModel(newRoot));
        return this;
    }

    /**
     * Retrieves the current root node of the tree, if the model is populated and
     * the root is a DNode.
     * 
     * @return the root DNode, or null if it cannot be resolved
     */
    public DNode getRoot() {
        TreeModel model = getModel();
        if (model != null && model.getRoot() instanceof DNode) {
            return (DNode) model.getRoot();
        }
        return null;
    }

    /**
     * Forces the underlying DefaultTreeModel to completely reload and refresh the
     * UI.
     * Call this if you've made deep data changes to node user-objects without using
     * structural model mutators.
     * 
     * @return This DTree instance.
     */
    public DTree refresh() {
        TreeModel model = getModel();
        if (model instanceof DefaultTreeModel) {
            ((DefaultTreeModel) model).reload();
        }
        return this;
    }

    /**
     * Attaches a mouse listener that triggers the provided consumer when a node is
     * right-clicked (popup trigger).
     * The consumer receives both the target DNode and the original MouseEvent
     * (useful for showing a JPopupMenu).
     * 
     * @param action a BiConsumer that accepts the clicked DNode and the MouseEvent
     * @return This DTree instance.
     */
    public DTree onRightClickNode(BiConsumer<DNode, MouseEvent> action) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // Support multiple OS patterns for triggering popups (Windows vs Mac)
                if (e.isPopupTrigger() || SwingUtilities.isRightMouseButton(e)) {
                    DNode node = closestNodeForLocation(e.getX(), e.getY());
                    if (node != null) {
                        Rectangle bounds = branchBounds(node.branch());
                        if (bounds != null && bounds.contains(e.getPoint())) {
                            action.accept(node, e);
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    DNode node = closestNodeForLocation(e.getX(), e.getY());
                    if (node != null) {
                        Rectangle bounds = branchBounds(node.branch());
                        if (bounds != null && bounds.contains(e.getPoint())) {
                            action.accept(node, e);
                        }
                    }
                }
            }
        });
        return this;
    }

    /**
     * Attaches a key listener that triggers the provided consumer when the user
     * presses the 'Enter' key
     * while exactly one node is selected.
     * 
     * @param action a Consumer that accepts the currently selected DNode
     * @return This DTree instance.
     */
    public DTree onKeyEnter(Consumer<DNode> action) {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    DNode node = selectionNode();
                    if (node != null) {
                        action.accept(node);
                    }
                }
            }
        });
        return this;
    }

    /**
     * Attaches a key listener that triggers the provided consumer when the user
     * presses the 'Delete' or 'Backspace' key
     * while exactly one node is selected.
     * 
     * @param action a Consumer that accepts the currently selected DNode
     * @return This DTree instance.
     */
    public DTree onKeyDelete(Consumer<DNode> action) {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    DNode node = selectionNode();
                    if (node != null) {
                        action.accept(node);
                    }
                }
            }
        });
        return this;
    }

    /**
     * Expands the node at the specified row.
     * 
     * @param row the row index to expand
     * @return This DTree instance.
     */
    public DTree expand(int row) {
        super.expandRow(row);
        return this;
    }

    /**
     * Collapses the node at the specified row.
     * 
     * @param row the row index to collapse
     * @return This DTree instance.
     */
    public DTree collapse(int row) {
        super.collapseRow(row);
        return this;
    }

    /** A list of persistent branching listeners attached to this tree. */
    private List<DBranching> branchings;

    /**
     * Sets the TreeModel that will provide the data.
     * Overridden to automatically preserve and migrate any registered DBranching
     * adapters
     * from the old model to the new model, ensuring listeners survive data changes.
     * 
     * @param newModel the new TreeModel to apply
     */
    @Override
    public void setModel(TreeModel newModel) {
        TreeModel oldModel = getModel();
        if (oldModel != null && branchings != null) {
            for (DBranching b : branchings) {
                oldModel.removeTreeModelListener(b);
            }
        }
        super.setModel(newModel);
        if (newModel != null && branchings != null) {
            for (DBranching b : branchings) {
                newModel.addTreeModelListener(b);
            }
        }
    }

    /**
     * Adds a DBranching adapter to listen for all common tree events
     * simultaneously.
     * The listener will automatically persist and re-attach even if the TreeModel
     * is replaced.
     * 
     * @param branching the DBranching adapter
     * @return This DTree instance.
     */
    public DTree onBranching(DBranching branching) {
        if (branchings == null) {
            branchings = new ArrayList<>();
        }
        if (!branchings.contains(branching)) {
            branchings.add(branching);
        }
        addTreeSelectionListener(branching);
        addTreeExpansionListener(branching);
        addTreeWillExpandListener(branching);
        TreeModel model = getModel();
        if (model != null) {
            model.addTreeModelListener(branching);
        }
        return this;
    }

    /**
     * Removes a previously added DBranching adapter from this tree.
     * 
     * @param branching the DBranching adapter to remove
     * @return This DTree instance.
     */
    public DTree offBranching(DBranching branching) {
        if (branchings != null) {
            branchings.remove(branching);
        }
        removeTreeSelectionListener(branching);
        removeTreeExpansionListener(branching);
        removeTreeWillExpandListener(branching);
        TreeModel model = getModel();
        if (model != null) {
            model.removeTreeModelListener(branching);
        }
        return this;
    }

    /**
     * Creates and applies a new DefaultTreeModel containing deep copies of nodes that match the filter.
     * Ancestor structures are preserved to keep matches visible. Note: this breaks original references.
     * 
     * @param filter the Predicate to evaluate nodes against
     * @return This DTree instance.
     */
    public DTree filterTree(Predicate<DNode> filter) {
        DNode root = getRoot();
        if (root == null) return this;
        DNode newRoot = cloneFiltered(root, filter);
        return root(newRoot);
    }
    
    private DNode cloneFiltered(DNode node, Predicate<DNode> filter) {
        boolean matches = filter.test(node);
        DNode clone = new DNode(node.getUserObject(), node.getAllowsChildren());
        clone.editor(node.editor());
        
        boolean hasMatchingDescendant = false;
        if (node.getChildCount() > 0) {
            for (int i = 0; i < node.getChildCount(); i++) {
                DNode child = node.childAt(i);
                DNode clonedChild = cloneFiltered(child, filter);
                if (clonedChild != null) {
                    clone.addNode(clonedChild);
                    hasMatchingDescendant = true;
                }
            }
        }
        
        if (matches || hasMatchingDescendant) {
            return clone;
        }
        return null;
    }

    /**
     * Automatically expands all nodes in the tree that match the given predicate.
     * 
     * @param filter the Predicate to test against
     * @return This DTree instance.
     */
    public DTree expandWhere(Predicate<DNode> filter) {
        DNode root = getRoot();
        if (root != null) {
            root.preorderStream().filter(filter).forEach(this::showNode);
        }
        return this;
    }

    /**
     * Automatically collapses all nodes in the tree that match the given predicate.
     * 
     * @param filter the Predicate to test against
     * @return This DTree instance.
     */
    public DTree collapseWhere(Predicate<DNode> filter) {
        DNode root = getRoot();
        if (root != null) {
            root.postorderStream().filter(filter).forEach(this::hideNode);
        }
        return this;
    }

    /**
     * Attaches a MouseMotionListener to trigger an action when the mouse rests over a specific node.
     * Passes null if the mouse moves off all valid nodes.
     * 
     * @param action a Consumer accepting the hovered DNode, or null
     * @return This DTree instance.
     */
    public DTree onNodeHover(Consumer<DNode> action) {
        addMouseMotionListener(new MouseMotionAdapter() {
            private DNode lastHovered = null;

            @Override
            public void mouseMoved(MouseEvent e) {
                DNode node = closestNodeForLocation(e.getX(), e.getY());
                if (node != null) {
                    Rectangle bounds = branchBounds(node.branch());
                    if (bounds != null && bounds.contains(e.getPoint())) {
                        if (node != lastHovered) {
                            lastHovered = node;
                            action.accept(node);
                        }
                    } else if (lastHovered != null) {
                        lastHovered = null;
                        action.accept(null);
                    }
                } else if (lastHovered != null) {
                    lastHovered = null;
                    action.accept(null);
                }
            }
        });
        return this;
    }

    /**
     * Locates a specific node by providing an array of user objects representing its exact path from the root.
     * 
     * @param userObjects the sequential user objects
     * @return the matching DNode, or null if the path doesn't exist
     */
    public DNode findNodeByPath(Object... userObjects) {
        if (userObjects == null || userObjects.length == 0) return null;
        DNode current = getRoot();
        if (current == null) return null;
        
        if (!objectsMatch(current.getUserObject(), userObjects[0])) {
            return null;
        }
        
        for (int i = 1; i < userObjects.length; i++) {
            DNode next = current.findChild(userObjects[i]);
            if (next == null) return null;
            current = next;
        }
        return current;
    }
    
    private boolean objectsMatch(Object o1, Object o2) {
        return (o1 == o2) || (o1 != null && o1.equals(o2));
    }

    /**
     * Selects multiple discrete nodes simultaneously.
     * 
     * @param nodes the array of nodes to select
     * @return This DTree instance.
     */
    public DTree setSelectionNodes(DNode... nodes) {
        if (nodes == null || nodes.length == 0) {
            return clearTreeSelection();
        }
        DBranch[] paths = new DBranch[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            paths[i] = nodes[i].branch();
        }
        return selectionPaths(paths);
    }

    /**
     * Adds multiple discrete nodes to the current selection.
     * 
     * @param nodes the array of nodes to add
     * @return This DTree instance.
     */
    public DTree addSelectionNodes(DNode... nodes) {
        if (nodes == null || nodes.length == 0) return this;
        DBranch[] paths = new DBranch[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            paths[i] = nodes[i].branch();
        }
        return appendSelectionPaths(paths);
    }

    /**
     * An adapter that allows any DEdit component to function as a native JTree
     * TreeCellEditor.
     * This class bridges the Swing JTree cell editing lifecycle with the custom
     * event and data
     * flow of the desk package's DEdit components.
     */
    public static class DEditTreeCellEditorAdapter extends AbstractCellEditor implements TreeCellEditor {

        /** The default DEdit component doing the actual editing. */
        protected final DEdit<?> defaultEditor;
        
        /** The DEdit component currently active for editing. */
        protected DEdit<?> activeEditor;
        
        /** A set of editors that have already had the action listener attached. */
        protected final Set<DEdit<?>> boundEditors = new HashSet<>();

        /**
         * Constructs a new tree cell editor adapter wrapping the given default DEdit component.
         * 
         * @param defaultEditor the default DEdit component to use for editing
         */
        public DEditTreeCellEditorAdapter(DEdit<?> defaultEditor) {
            this.defaultEditor = defaultEditor;
            this.activeEditor = defaultEditor;
            bindEditor(defaultEditor);
        }
        
        protected void bindEditor(DEdit<?> editor) {
            if (editor != null && !boundEditors.contains(editor)) {
                editor.onAction(e -> stopCellEditing());
                boundEditors.add(editor);
            }
        }

        /**
         * Returns the value contained in the active editor.
         * 
         * @return the current value from the active DEdit component
         */
        @Override
        public Object getCellEditorValue() {
            return activeEditor != null ? activeEditor.value() : null;
        }

        /**
         * Sets an initial value for the editor and returns the component that should be
         * added to the client's Component hierarchy.
         * 
         * @param tree       the JTree that is asking the editor to edit
         * @param value      the value of the cell to be edited
         * @param isSelected true if the cell is to be rendered with highlighting
         * @param expanded   true if the node is expanded
         * @param leaf       true if the node is a leaf node
         * @param row        the row index of the node being edited
         * @return the component for editing
         */
        @SuppressWarnings("unchecked")
        @Override
        public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
                boolean leaf, int row) {
            
            this.activeEditor = defaultEditor;
            Object actualValue = value;
            
            // Extract the actual user object to edit, bypassing the wrapper nodes
            if (value instanceof DNode) {
                DNode dNode = (DNode) value;
                actualValue = dNode.getUserObject();
                
                // Allow specific nodes to override the default cell editor
                if (dNode.editor() != null) {
                    this.activeEditor = dNode.editor();
                }
            } else if (value instanceof DefaultMutableTreeNode) {
                // For standard DefaultMutableTreeNodes, also extract the user object
                actualValue = ((DefaultMutableTreeNode) value).getUserObject();
            }
            
            if (this.activeEditor != null) {
                bindEditor(this.activeEditor);
                try {
                    ((DEdit<Object>) this.activeEditor).value(actualValue);
                } catch (Exception ex) {
                    // Ignore class cast or mismatch exceptions during initial value set.
                }
                return this.activeEditor.comp();
            }
            
            return null;
        }
    }
}
