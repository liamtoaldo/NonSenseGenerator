package com.swe.nonsense;

import org.junit.jupiter.api.Test;

public class SyntaxTreeTest {

    @Test
    void testGetRoot() {
        SyntaxNode rootNode = new SyntaxNode(new Word("root"));
        SyntaxTree tree = new SyntaxTree(rootNode);
        assert tree.getRoot().equals(rootNode) : "getRoot should return the correct root node";
    }

    @Test
    void testSetRoot() {
        SyntaxNode newRootNode = new SyntaxNode(new Word("newRoot"));
        SyntaxTree tree = new SyntaxTree();
        tree.setRoot(newRootNode);
        assert tree.getRoot().equals(newRootNode) : "setRoot should set the root node correctly";
    }

}
