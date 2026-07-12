package com.vidlus.jarch.desk;


import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * A fluent API wrapper for {@link DefaultMutableTreeNode} to easily build and configure tree nodes for {@link DTree}.
 * This class provides chainable methods for rapidly building complex tree hierarchies.
 */
public class DNode extends DefaultMutableTreeNode {

    /** Serialization version identifier. */
    private static final long serialVersionUID = 1L;

    /** An optional specific editor for this node, overriding the tree's default. */
    private DEdit<?> editor;

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
     * Sets a specific editor for this node, overriding the tree's default editor.
     * 
     * @param editor the specific DEdit to use for this node
     * @return This DNode instance for fluent chaining.
     */
    public DNode editor(DEdit<?> editor) {
        this.editor = editor;
        return this;
    }

    /**
     * Retrieves the specific editor set for this node.
     * 
     * @return the specific DEdit, or null if none is set
     */
    public DEdit<?> editor() {
        return this.editor;
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

    /**
     * Returns the child at the specified index in this node's child array, cast to DNode.
     * 
     * @param index an index into this node's child array
     * @return the DNode in this node's child array at the specified index
     */
    public DNode childAt(int index) {
        return (DNode) super.getChildAt(index);
    }

    /**
     * Returns this node's first child, cast to DNode.
     * 
     * @return the first child of this node
     */
    public DNode firstChild() {
        return (DNode) super.getFirstChild();
    }

    /**
     * Returns this node's last child, cast to DNode.
     * 
     * @return the last child of this node
     */
    public DNode lastChild() {
        return (DNode) super.getLastChild();
    }

    /**
     * Returns this node's parent, cast to DNode. Returns null if this node has no parent.
     * 
     * @return this node's parent DNode, or null if this node has no parent
     */
    public DNode parent() {
        return (DNode) super.getParent();
    }

    /**
     * Returns the next sibling of this node in the parent's children array, cast to DNode.
     * 
     * @return the sibling of this node that immediately follows it, or null
     */
    public DNode nextSibling() {
        return (DNode) super.getNextSibling();
    }

    /**
     * Returns the previous sibling of this node in the parent's children array, cast to DNode.
     * 
     * @return the sibling of this node that immediately precedes it, or null
     */
    public DNode previousSibling() {
        return (DNode) super.getPreviousSibling();
    }

    /**
     * Returns the root of the tree that contains this node, cast to DNode.
     * 
     * @return the root of the tree that contains this node
     */
    public DNode root() {
        return (DNode) super.getRoot();
    }

    /**
     * Returns the number of levels above this node -- the distance from the root to this node.
     * 
     * @return the number of levels above this node
     */
    public int level() {
        return super.getLevel();
    }

    /**
     * Returns the depth of the tree rooted at this node -- the longest distance from this node to a leaf.
     * 
     * @return the depth of the tree rooted at this node
     */
    public int depth() {
        return super.getDepth();
    }

    /**
     * Returns true if this node has children (is not a leaf).
     * 
     * @return true if this node has children
     */
    public boolean hasChildren() {
        return !super.isLeaf();
    }

    /**
     * Returns a stream of this node's children, cast to DNode.
     * 
     * @return a Stream of DNode children
     */
    @SuppressWarnings("unchecked")
    public Stream<DNode> childrenStream() {
        // Convert legacy Enumeration to a modern Java Stream
        Enumeration<TreeNode> e = super.children();
        Iterator<DNode> iter = new Iterator<DNode>() {
            public boolean hasNext() { return e.hasMoreElements(); }
            public DNode next() { return (DNode) e.nextElement(); }
        };
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iter, Spliterator.ORDERED), false);
    }

    /**
     * Searches the immediate children of this node for the first child that matches the specified user object.
     * 
     * @param userObject the user object to search for
     * @return the first DNode whose user object equals the specified object, or null if not found
     */
    public DNode findChild(Object userObject) {
        int count = getChildCount();
        // Iterate over children and check for both object equality and identity
        for (int i = 0; i < count; i++) {
            DNode child = childAt(i);
            if (child.getUserObject() != null && child.getUserObject().equals(userObject)) {
                return child;
            } else if (child.getUserObject() == userObject) {
                return child;
            }
        }
        return null;
    }

    /**
     * Deep searches the subtree (preorder) to find the first node matching the custom predicate condition.
     * 
     * @param filter the Predicate to evaluate nodes against
     * @return the first matching DNode, or null if none is found
     */
    public DNode findNode(Predicate<DNode> filter) {
        return preorderStream().filter(filter).findFirst().orElse(null);
    }

    /**
     * Retrieves a List of all descendant nodes matching the custom predicate condition.
     * 
     * @param filter the Predicate to evaluate nodes against
     * @return a List of all matching DNode descendants
     */
    public List<DNode> findAll(Predicate<DNode> filter) {
        return preorderStream().filter(filter).collect(Collectors.toList());
    }

    /**
     * Sorts the immediate children of this node using the provided comparator.
     * 
     * @param comparator the Comparator to sort children by
     * @return This DNode instance for fluent chaining.
     */
    @SuppressWarnings("unchecked")
    public DNode sortChildren(Comparator<DNode> comparator) {
        if (children != null) {
            children.sort((o1, o2) -> comparator.compare((DNode) o1, (DNode) o2));
        }
        return this;
    }

    /**
     * Recursively sorts all children in the entire subtree rooted at this node.
     * 
     * @param comparator the Comparator to sort by
     * @return This DNode instance for fluent chaining.
     */
    public DNode sortTree(Comparator<DNode> comparator) {
        sortChildren(comparator);
        if (children != null) {
            for (Object child : children) {
                ((DNode) child).sortTree(comparator);
            }
        }
        return this;
    }

    /**
     * Moves this node from its current parent to a new parent at a specific index.
     * 
     * @param newParent the new parent DNode
     * @param index the insertion index in the new parent
     * @return This DNode instance for fluent chaining.
     */
    public DNode moveTo(DNode newParent, int index) {
        removeFromParentNode();
        newParent.insertNode(this, index);
        return this;
    }

    /**
     * Swaps this node with a new one in its parent, transferring all children to the new node.
     * 
     * @param newNode the replacement DNode
     * @return This DNode instance (the original, now detached node)
     */
    public DNode replaceWith(DNode newNode) {
        DNode p = parent();
        if (p != null) {
            int index = p.getIndex(this);
            p.removeNode(this);
            
            // Transfer children
            if (children != null) {
                for (Object childObj : new ArrayList<>(children)) {
                    DNode child = (DNode) childObj;
                    child.removeFromParentNode();
                    newNode.addNode(child);
                }
            }
            
            p.insertNode(newNode, index);
        }
        return this;
    }

    /**
     * Deep copies this node and all of its descendants, maintaining the same user objects.
     * 
     * @return a deep clone of the DNode tree
     */
    public DNode cloneTree() {
        DNode clone = new DNode(getUserObject(), getAllowsChildren());
        clone.editor(this.editor);
        if (children != null) {
            for (Object childObj : children) {
                DNode child = (DNode) childObj;
                clone.addNode(child.cloneTree());
            }
        }
        return clone;
    }

    /**
     * Returns a joined string of the path from the root to this node, using the string representation of the user objects.
     * 
     * @param delimiter the string used to join the objects
     * @return the joined path string
     */
    public String pathString(String delimiter) {
        return branch().join(delimiter);
    }

    /**
     * Builds and returns a DBranch representing the path from the root of the tree to this node.
     * This bridges DNode directly back to DBranch and DTree visibility methods.
     * 
     * @return a DBranch from the root to this node
     */
    public DBranch branch() {
        return new DBranch(super.getPath());
    }

    /**
     * Builds and returns an array of objects representing the path from the root to this node,
     * using the user objects of the nodes.
     * 
     * @return an array of Objects, where the first Object is the root's user object and the last is this node's user object
     */
    public Object[] branchObjects() {
        return super.getUserObjectPath();
    }

    /**
     * Returns true if this node is the root of the tree.
     * 
     * @return true if this node is the root
     */
    public boolean isRootNode() {
        return super.isRoot();
    }

    /**
     * Returns true if this node has no children.
     * 
     * @return true if this node is a leaf
     */
    public boolean isLeafNode() {
        return super.isLeaf();
    }

    /**
     * Returns true if anotherNode is an ancestor of this node.
     * 
     * @param anotherNode node to test as an ancestor of this node
     * @return true if this node is a descendant of anotherNode
     */
    public boolean isDescendantOf(DNode anotherNode) {
        return super.isNodeAncestor(anotherNode);
    }

    /**
     * Returns true if anotherNode is a descendant of this node.
     * 
     * @param anotherNode node to test as descendant of this node
     * @return true if this node is an ancestor of anotherNode
     */
    public boolean isAncestorOf(DNode anotherNode) {
        return super.isNodeDescendant(anotherNode);
    }

    /**
     * Returns true if aNode is a child of this node.
     * 
     * @param aNode node to test as a child of this node
     * @return true if aNode is a child of this node
     */
    public boolean isChildOfThis(DNode aNode) {
        return super.isNodeChild(aNode);
    }

    /**
     * Returns true if anotherNode is a sibling of (has the same parent as) this node.
     * 
     * @param anotherNode node to test as sibling of this node
     * @return true if anotherNode is a sibling of this node
     */
    public boolean isSiblingOf(DNode anotherNode) {
        return super.isNodeSibling(anotherNode);
    }

    /**
     * Returns true if and only if aNode is in the same tree as this node.
     * 
     * @param aNode the node to test
     * @return true if aNode is in the same tree as this node
     */
    public boolean isRelatedTo(DNode aNode) {
        return super.isNodeRelated(aNode);
    }

    /**
     * Returns the nearest common ancestor to this node and aNode.
     * 
     * @param aNode node to find common ancestor with
     * @return nearest ancestor common to this node and aNode, or null if none
     */
    public DNode sharedAncestor(DNode aNode) {
        return (DNode) super.getSharedAncestor(aNode);
    }

    /**
     * Returns the node that follows this node in a preorder traversal of this node's tree.
     * 
     * @return the node that follows this node in a preorder traversal, or null if this node is the last
     */
    public DNode nextNode() {
        return (DNode) super.getNextNode();
    }

    /**
     * Returns the node that precedes this node in a preorder traversal of this node's tree.
     * 
     * @return the node that precedes this node in a preorder traversal, or null if this node is the first
     */
    public DNode previousNode() {
        return (DNode) super.getPreviousNode();
    }

    /**
     * Returns the child in this node's child array that immediately follows aChild.
     * 
     * @param aChild a child node
     * @return the child of this node that immediately follows aChild
     */
    public DNode childAfter(DNode aChild) {
        return (DNode) super.getChildAfter(aChild);
    }

    /**
     * Returns the child in this node's child array that immediately precedes aChild.
     * 
     * @param aChild a child node
     * @return the child of this node that immediately precedes aChild
     */
    public DNode childBefore(DNode aChild) {
        return (DNode) super.getChildBefore(aChild);
    }

    /**
     * Finds and returns the first leaf that is a descendant of this node.
     * 
     * @return the first leaf in the subtree rooted at this node
     */
    public DNode firstLeaf() {
        return (DNode) super.getFirstLeaf();
    }

    /**
     * Finds and returns the last leaf that is a descendant of this node.
     * 
     * @return the last leaf in the subtree rooted at this node
     */
    public DNode lastLeaf() {
        return (DNode) super.getLastLeaf();
    }

    /**
     * Returns the leaf after this node or null if this node is the last leaf in the tree.
     * 
     * @return returns the next leaf past this node
     */
    public DNode nextLeaf() {
        return (DNode) super.getNextLeaf();
    }

    /**
     * Returns the leaf before this node or null if this node is the first leaf in the tree.
     * 
     * @return returns the leaf before this node
     */
    public DNode previousLeaf() {
        return (DNode) super.getPreviousLeaf();
    }

    /**
     * Helper to wrap legacy tree enumerations into modern Streams.
     */
    @SuppressWarnings("unchecked")
    private Stream<DNode> streamEnumeration(Enumeration<?> e) {
        // Adapt the legacy Enumeration to an Iterator, then to a Stream
        Iterator<DNode> iter = new Iterator<DNode>() {
            public boolean hasNext() { return e.hasMoreElements(); }
            public DNode next() { return (DNode) e.nextElement(); }
        };
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iter, Spliterator.ORDERED), false);
    }

    /**
     * Creates and returns a stream that traverses the subtree rooted at this node in preorder.
     * 
     * @return a Stream of DNodes
     */
    public Stream<DNode> preorderStream() {
        return streamEnumeration(super.preorderEnumeration());
    }

    /**
     * Creates and returns a stream that traverses the subtree rooted at this node in postorder.
     * 
     * @return a Stream of DNodes
     */
    public Stream<DNode> postorderStream() {
        return streamEnumeration(super.postorderEnumeration());
    }

    /**
     * Creates and returns a stream that traverses the subtree rooted at this node in breadth-first order.
     * 
     * @return a Stream of DNodes
     */
    public Stream<DNode> breadthFirstStream() {
        return streamEnumeration(super.breadthFirstEnumeration());
    }

    /**
     * Creates and returns a stream that traverses the subtree rooted at this node in depth-first order.
     * 
     * @return a Stream of DNodes
     */
    public Stream<DNode> depthFirstStream() {
        return streamEnumeration(super.depthFirstEnumeration());
    }
}
