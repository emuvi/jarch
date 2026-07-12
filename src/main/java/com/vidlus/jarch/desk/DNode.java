package com.vidlus.jarch.desk;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * A fluent API wrapper for {@link DefaultMutableTreeNode} to easily build and configure tree nodes for {@link DTree}.
 * This class provides chainable methods for rapidly building complex tree hierarchies.
 */
public class DNode extends DefaultMutableTreeNode {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a tree node that has no parent and no children, but which allows children.
     */
    public DNode() {
        super();
    }

    /**
     * Creates a tree node with no parent, no children, but which allows children, and initializes it with the specified user object.
     * 
     * @param userObject an Object provided by the user that constitutes the node's data
     */
    public DNode(Object userObject) {
        super(userObject);
    }

    /**
     * Creates a tree node with no parent, no children, initialized with the specified user object, and that allows children only if specified.
     * 
     * @param userObject an Object provided by the user that constitutes the node's data
     * @param allowsChildren if true, the node is allowed to have child nodes
     */
    public DNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
    }

    /**
     * Sets the user object for this node.
     * 
     * @param userObject the Object that constitutes this node's user-specified data
     * @return This DNode instance for fluent chaining.
     */
    public DNode userObject(Object userObject) {
        setUserObject(userObject);
        return this;
    }

    /**
     * Determines whether or not this node is allowed to have children.
     * 
     * @param allows true if this node is allowed to have children
     * @return This DNode instance for fluent chaining.
     */
    public DNode allowsChildren(boolean allows) {
        setAllowsChildren(allows);
        return this;
    }

    /**
     * Appends a child node to the end of this node's child array.
     * 
     * @param newChild the DNode to append
     * @return This DNode instance (the parent) for fluent chaining.
     */
    public DNode addNode(DNode newChild) {
        super.add(newChild);
        return this;
    }

    /**
     * Creates a new DNode with the given user object and appends it to this node.
     * This is a rapid helper for building leaf nodes sequentially.
     * 
     * @param childUserObject the user object for the new child node
     * @return This DNode instance (the parent) for fluent chaining.
     */
    public DNode addChild(Object childUserObject) {
        super.add(new DNode(childUserObject));
        return this;
    }

    /**
     * Creates a new DNode with the given user object, appends it to this node, and returns the NEW child node.
     * This allows diving down into a child for further building.
     * 
     * @param childUserObject the user object for the new child node
     * @return The newly created child DNode instance.
     */
    public DNode dive(Object childUserObject) {
        DNode child = new DNode(childUserObject);
        super.add(child);
        return child;
    }

    /**
     * Inserts a child node at the specified index.
     * 
     * @param newChild the node to insert
     * @param childIndex the index where the new child will be inserted
     * @return This DNode instance for fluent chaining.
     */
    public DNode insertNode(DNode newChild, int childIndex) {
        super.insert(newChild, childIndex);
        return this;
    }

    /**
     * Removes the child node at the specified index.
     * 
     * @param childIndex the index of the child to remove
     * @return This DNode instance for fluent chaining.
     */
    public DNode removeNode(int childIndex) {
        super.remove(childIndex);
        return this;
    }

    /**
     * Removes the specified child node from this node.
     * 
     * @param aChild the child node to remove
     * @return This DNode instance for fluent chaining.
     */
    public DNode removeNode(DNode aChild) {
        super.remove(aChild);
        return this;
    }

    /**
     * Removes all of this node's children, setting their parents to null.
     * 
     * @return This DNode instance for fluent chaining.
     */
    public DNode removeAllNodes() {
        super.removeAllChildren();
        return this;
    }

    /**
     * Removes this node from its parent.
     * 
     * @return This DNode instance for fluent chaining.
     */
    public DNode removeFromParentNode() {
        super.removeFromParent();
        return this;
    }
}
