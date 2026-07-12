package com.vidlus.jarch.desk;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;

/**
 * An adapter class that absorbs all common JTree listener events.
 * Extend this class and override the methods you care about to avoid
 * having to implement all methods of multiple listener interfaces.
 */
public class DBranching implements TreeSelectionListener, 
                                        TreeExpansionListener, 
                                        TreeWillExpandListener, 
                                        TreeModelListener {
    // --------------------------------------------------------
    // TreeSelectionListener
    // --------------------------------------------------------
    
    /**
     * Called whenever the value of the tree selection changes.
     * Override this method to react to node selection clicks.
     * 
     * @param e the event that characterizes the selection change.
     */
    @Override
    public void valueChanged(TreeSelectionEvent e) {}

    // --------------------------------------------------------
    // TreeExpansionListener
    // --------------------------------------------------------
    
    /**
     * Called whenever a node in the tree has successfully been expanded.
     * 
     * @param event the event that characterizes the expansion.
     */
    @Override
    public void treeExpanded(TreeExpansionEvent event) {}

    /**
     * Called whenever a node in the tree has successfully been collapsed.
     * 
     * @param event the event that characterizes the collapse.
     */
    @Override
    public void treeCollapsed(TreeExpansionEvent event) {}

    // --------------------------------------------------------
    // TreeWillExpandListener
    // --------------------------------------------------------
    
    /**
     * Invoked whenever a node in the tree is about to be expanded.
     * Throw an ExpandVetoException inside your override to actively prevent the expansion.
     * 
     * @param event the event that characterizes the pending expansion.
     * @throws ExpandVetoException to forcefully stop the expansion from rendering.
     */
    @Override
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {}

    /**
     * Invoked whenever a node in the tree is about to be collapsed.
     * Throw an ExpandVetoException inside your override to actively prevent the collapse.
     * 
     * @param event the event that characterizes the pending collapse.
     * @throws ExpandVetoException to forcefully stop the collapse from rendering.
     */
    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {}

    // --------------------------------------------------------
    // TreeModelListener
    // --------------------------------------------------------
    
    /**
     * Invoked after a node (or a set of siblings) has had its data or presentation changed.
     * 
     * @param e the event detailing the node changes.
     */
    @Override
    public void treeNodesChanged(TreeModelEvent e) {}

    /**
     * Invoked after new nodes have been natively inserted into the tree model.
     * 
     * @param e the event detailing the node insertions.
     */
    @Override
    public void treeNodesInserted(TreeModelEvent e) {}

    /**
     * Invoked after nodes have been cleanly removed from the tree model.
     * 
     * @param e the event detailing the node removals.
     */
    @Override
    public void treeNodesRemoved(TreeModelEvent e) {}

    /**
     * Invoked after the tree has drastically changed structure from a given node down.
     * E.g., when the entire tree data model is replaced or resorted.
     * 
     * @param e the event detailing the mass structural change.
     */
    @Override
    public void treeStructureChanged(TreeModelEvent e) {}
}
