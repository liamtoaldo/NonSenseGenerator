package com.swe.nonsense;

public class SyntaxTree {
    private SyntaxNode root;

    public SyntaxTree() {
        this.root = null;
    }

    public SyntaxTree(SyntaxNode root) {
        this.root = root;
    }

    public SyntaxNode getRoot() {
        return root;
    }

    public void setRoot(SyntaxNode root) {
        this.root = root;
    }
}
