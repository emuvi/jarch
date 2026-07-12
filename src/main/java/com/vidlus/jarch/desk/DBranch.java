package com.vidlus.jarch.desk;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.tree.TreePath;

/**
 * A fluent wrapper for {@link TreePath}, representing a path to a node in a {@link DTree}.
 * This class provides strongly-typed navigation methods for paths constructed of {@link DNode}s.
 */
public class DBranch extends TreePath implements Iterable<DNode> {

    /** Serialization version identifier. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a path from an array of Objects, uniquely identifying the path from the root of the tree to a specific node.
     * 
     * @param path an array of Objects representing the path to a node
     */
    public DBranch(Object[] path) {
        super(path);
    }

    /**
     * Constructs a TreePath containing only a single element.
     * 
     * @param singlePath an Object representing the path to a node
     */
    public DBranch(Object singlePath) {
        super(singlePath);
    }

    /**
     * Constructs a new TreePath, which is the path identified by parent ending in lastElement.
     * 
     * @param parent the parent DBranch or TreePath
     * @param lastElement the end element
     */
    public DBranch(TreePath parent, Object lastElement) {
        super(parent, lastElement);
    }

    /**
     * Returns a path containing all the elements of this object, plus child.
     * 
     * @param child the Object to add to the path
     * @return a new DBranch containing all the elements of this path, plus child
     */
    @Override
    public DBranch pathByAddingChild(Object child) {
        return new DBranch(this, child);
    }

    /**
     * Returns the path to the parent of the elements in this DBranch.
     * 
     * @return the path to the parent, or null if this path has no parent
     */
    @Override
    public DBranch getParentPath() {
        TreePath parent = super.getParentPath();
        if (parent == null) {
            return null;
        }
        return new DBranch(parent.getPath());
    }

    /**
     * Returns the last component of this path, cast to a DNode.
     * 
     * @return the last component of the path as a DNode, or null if it's not a DNode
     */
    public DNode lastNode() {
        Object last = getLastPathComponent();
        if (last instanceof DNode) {
            return (DNode) last;
        }
        return null;
    }

    /**
     * Returns the path component at the specified index, cast to a DNode.
     * 
     * @param index the index of the element requested
     * @return the path component at the specified index as a DNode, or null if not a DNode
     */
    public DNode nodeAt(int index) {
        Object comp = getPathComponent(index);
        if (comp instanceof DNode) {
            return (DNode) comp;
        }
        return null;
    }

    /**
     * Returns the root component of this path, cast to a DNode.
     * 
     * @return the root component of the path as a DNode, or null if it's not a DNode
     */
    public DNode rootNode() {
        return nodeAt(0);
    }

    /**
     * Returns the path to the parent of the elements in this DBranch.
     * Alias for {@link #getParentPath()}.
     * 
     * @return the path to the parent, or null if this path has no parent
     */
    public DBranch parent() {
        return getParentPath();
    }

    /**
     * Returns the number of elements in the path.
     * Alias for {@link #getPathCount()}.
     * 
     * @return the number of elements in the path
     */
    public int length() {
        return getPathCount();
    }

    /**
     * Returns an array of DNodes representing all elements in this path.
     * Elements that are not DNodes are omitted from the resulting array, so the length may be shorter than the path count.
     * 
     * @return an array of DNodes
     */
    public DNode[] nodes() {
        List<DNode> list = nodeList();
        return list.toArray(new DNode[0]);
    }

    /**
     * Returns a List of DNodes representing all elements in this path.
     * Elements that are not DNodes are omitted from the list.
     * 
     * @return a List of DNodes
     */
    public List<DNode> nodeList() {
        List<DNode> nodes = new ArrayList<>();
        Object[] path = getPath();
        for (Object comp : path) {
            if (comp instanceof DNode) {
                nodes.add((DNode) comp);
            }
        }
        return nodes;
    }

    /**
     * Returns a Stream of DNodes representing all elements in this path.
     * Elements that are not DNodes are filtered out.
     * 
     * @return a Stream of DNodes
     */
    public Stream<DNode> nodeStream() {
        return nodeList().stream();
    }

    /**
     * Checks if the specified DNode is contained within this branch's path.
     * 
     * @param node the DNode to search for
     * @return true if the node is in the path, false otherwise
     */
    public boolean contains(DNode node) {
        Object[] path = getPath();
        for (Object comp : path) {
            if (comp == node || (comp != null && comp.equals(node))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given branch is a descendant of this branch.
     * 
     * @param branch the branch to check
     * @return true if the given branch is a descendant of this branch
     */
    public boolean isDescendant(DBranch branch) {
        return super.isDescendant(branch);
    }

    /**
     * Checks if the given branch is an ancestor of this branch.
     * 
     * @param branch the branch to check
     * @return true if the given branch is an ancestor of this branch
     */
    public boolean isAncestor(DBranch branch) {
        return branch != null && branch.isDescendant(this);
    }

    /**
     * Returns the longest common ancestor branch between this branch and another branch.
     * 
     * @param branch the other branch
     * @return the common ancestor DBranch, or null if there is no common ancestor
     */
    public DBranch commonAncestor(DBranch branch) {
        if (branch == null) return null;
        Object[] path1 = this.getPath();
        Object[] path2 = branch.getPath();
        int min = Math.min(path1.length, path2.length);
        int commonCount = 0;
        // Iterate through paths from the root, counting matching nodes
        for (int i = 0; i < min; i++) {
            if (path1[i] == path2[i] || (path1[i] != null && path1[i].equals(path2[i]))) {
                commonCount++;
            } else {
                break;
            }
        }
        
        if (commonCount == 0) return null;
        Object[] commonPath = new Object[commonCount];
        System.arraycopy(path1, 0, commonPath, 0, commonCount);
        return new DBranch(commonPath);
    }

    /**
     * Creates a new branch representing a subpath from the root up to the specified exclusive index.
     * 
     * @param toIndex the exclusive end index
     * @return a new DBranch, or null if the index is invalid
     */
    public DBranch subpath(int toIndex) {
        if (toIndex <= 0 || toIndex > getPathCount()) return null;
        Object[] sub = new Object[toIndex];
        System.arraycopy(getPath(), 0, sub, 0, toIndex);
        return new DBranch(sub);
    }

    /**
     * Joins the string representation of all nodes in this branch using the specified delimiter.
     * 
     * @param delimiter the delimiter to use between node strings
     * @return the joined string
     */
    public String join(String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter);
        for (Object comp : getPath()) {
            joiner.add(comp == null ? "null" : comp.toString());
        }
        return joiner.toString();
    }

    /**
     * Checks if this branch ends with the specified node.
     * 
     * @param node the node to check
     * @return true if the last node in this branch equals the specified node
     */
    public boolean endsWith(DNode node) {
        DNode last = lastNode();
        return last == node || (last != null && last.equals(node));
    }

    /**
     * Checks if this branch starts with the specified node.
     * 
     * @param node the node to check
     * @return true if the root node in this branch equals the specified node
     */
    public boolean startsWith(DNode node) {
        DNode root = rootNode();
        return root == node || (root != null && root.equals(node));
    }

    /**
     * Returns the index of the specified node in this branch.
     * 
     * @param node the node to find
     * @return the index of the node, or -1 if not found
     */
    public int indexOf(DNode node) {
        Object[] path = getPath();
        for (int i = 0; i < path.length; i++) {
            if (path[i] == node || (path[i] != null && path[i].equals(node))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if the branch path contains a node with the specified user object.
     * 
     * @param obj the user object to search for
     * @return true if a node with the given user object is in the path
     */
    public boolean containsUserObject(Object obj) {
        for (DNode node : this) {
            if (node.getUserObject() != null && node.getUserObject().equals(obj)) {
                return true;
            } else if (node.getUserObject() == obj) {
                return true;
            }
        }
        return false;
    }

    /**
     * Maps all nodes in the path to a List of generic type T using the provided mapper function.
     * 
     * @param <T> the target type
     * @param mapper the Function to apply to each DNode
     * @return a List of mapped values
     */
    public <T> List<T> map(Function<DNode, T> mapper) {
        return nodeStream().map(mapper).collect(Collectors.toList());
    }

    /**
     * Returns a List of DNodes representing the path reversed (from leaf up to the root).
     * 
     * @return a reversed List of DNodes
     */
    public List<DNode> reverse() {
        List<DNode> list = nodeList();
        Collections.reverse(list);
        return list;
    }

    /**
     * Checks if this branch only contains the root node (i.e., its length is 1).
     * 
     * @return true if this path is just the root node
     */
    public boolean isRootOnly() {
        return length() == 1;
    }

    /**
     * Returns a new DBranch with the given node added to the end of this path.
     * 
     * @param node the DNode to append
     * @return a new DBranch including the appended node
     */
    public DBranch append(DNode node) {
        return pathByAddingChild(node);
    }

    /**
     * Returns a new DBranch with the last N nodes removed from the end of the path.
     * 
     * @param countFromEnd the number of nodes to trim from the end
     * @return a new trimmed DBranch, or null if trimmed to length 0 or invalid count
     */
    public DBranch trim(int countFromEnd) {
        int newLen = getPathCount() - countFromEnd;
        if (newLen <= 0 || countFromEnd < 0) return null;
        return subpath(newLen);
    }

    /**
     * Returns an iterator over the DNodes in this branch, from root to leaf.
     * 
     * @return an iterator of DNodes
     */
    @Override
    public Iterator<DNode> iterator() {
        return nodeList().iterator();
    }
}
