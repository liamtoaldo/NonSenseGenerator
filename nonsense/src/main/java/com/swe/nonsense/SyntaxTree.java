package com.swe.nonsense;

import java.util.ArrayList;

/**
 * Class that represents a syntax tree. A syntax tree is a forest composed of more tree.
 * Each tree is composed of SyntaxNode objects.
 */
public class SyntaxTree {
    /**
     * The roots nodes of the syntax tree
     */
    private ArrayList<SyntaxNode> roots;

    /**
     * Default constructor that initializes an empty syntax tree
     */
    public SyntaxTree() {
        this.roots = new ArrayList<>();
    }

    /**
     * Constructor that initializes the syntax tree with given roots nodes, creating a tree for each root node
     *
     * @param roots The roots nodes of the syntax tree
     */
    public SyntaxTree(ArrayList<SyntaxNode> roots) {
        this.roots = roots;
    }

    /**
     * Method that returns the roots nodes of the syntax tree
     * 
     * @return The roots nodes of the syntax tree
     */
    public ArrayList<SyntaxNode> getRoots() {
        return roots;
    }

    /**
     * Method that sets the roots nodes of the syntax tree with the one given in the parameter
     * 
     * @param roots The new roots nodes of the syntax tree
     */
    public void setRoots(ArrayList<SyntaxNode> roots) {
        this.roots = roots;
    }

    /**
     * Method that adds a root node to the syntax tree (if it has a root). 
     * Doing so, it creates a new tree with the given root node
     * 
     * @param root The root node to be added to the syntax tree
     */
    public void addRoot(SyntaxNode root) {
        if (root != null) {
            this.roots.add(root);
        }
    }

    /**
     * Method that returns a list of all nodes in the syntax tree
     * in pre-order traversal (root, then children recursively)
     *
     * @return A list of SyntaxNode. If the tree is empty, returns an empty list
     */
    public ArrayList<SyntaxNode> getAllNodes() {
        ArrayList<SyntaxNode> orderedNodes = new ArrayList<>();
        if (this.roots != null) {
            for (SyntaxNode root : this.roots) {
                collectNodesPreOrder(root, orderedNodes);
            }
        }
        return orderedNodes;
    }

    /**
     * Recursive helper method to collect nodes in pre-order
     * 
     * @param node The current node to visit
     * @param collectedNodes The list in which to accumulate the visited nodes
     */
    private void collectNodesPreOrder(SyntaxNode node, ArrayList<SyntaxNode> collectedNodes) {
        if (node == null) {
            return;
        }
        
        collectedNodes.add(node); 
        
        if (node.getChildren() != null) { 
            for (SyntaxNode child : node.getChildren()) {
                collectNodesPreOrder(child, collectedNodes); 
            }
        }
    }
}
