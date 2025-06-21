package com.swe.nonsense;

import java.util.ArrayList;

public class SyntaxTree {
    private ArrayList<SyntaxNode> roots;

    public SyntaxTree() {
        this.roots = new ArrayList<>();
    }

    public SyntaxTree(ArrayList<SyntaxNode> roots) {
        this.roots = roots;
    }

    public ArrayList<SyntaxNode> getRoots() {
        return roots;
    }

    public void setRoots(ArrayList<SyntaxNode> roots) {
        this.roots = roots;
    }

    public void addRoot(SyntaxNode root) {
        if (root != null) {
            this.roots.add(root);
        }
    }

    public ArrayList<SyntaxNode> getAllNodes() {
        ArrayList<SyntaxNode> orderedNodes = new ArrayList<>();
        if (this.roots != null) {
            for (SyntaxNode root : this.roots) {
                collectNodesPreOrder(root, orderedNodes);
            }
        }
        return orderedNodes;
    }

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
