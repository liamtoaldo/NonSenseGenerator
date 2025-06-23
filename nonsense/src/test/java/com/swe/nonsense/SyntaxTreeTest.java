package com.swe.nonsense;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class SyntaxTreeTest {

    @Test
    void testConstructorAndGetRoots() {
        SyntaxNode rootNode = new SyntaxNode(new Word("root"));
        ArrayList<SyntaxNode> roots = new ArrayList<>();
        roots.add(rootNode);
        SyntaxTree tree = new SyntaxTree(roots);
        
        assertNotNull(tree.getRoots(), "getRoots should not return null");
        assertEquals(1, tree.getRoots().size(), "There should be one root node");
        assertEquals(rootNode, tree.getRoots().get(0), "getRoots should return the correct root node");
    }

    @Test
    void testSetRoots() {
        SyntaxNode newRootNode1 = new SyntaxNode(new Word("newRoot1"));
        SyntaxNode newRootNode2 = new SyntaxNode(new Word("newRoot2"));
        ArrayList<SyntaxNode> newRoots = new ArrayList<>(Arrays.asList(newRootNode1, newRootNode2));
        
        SyntaxTree tree = new SyntaxTree();
        tree.setRoots(newRoots);
        
        assertEquals(newRoots, tree.getRoots(), "setRoots should set the root nodes correctly");
        assertEquals(2, tree.getRoots().size(), "There should be two root nodes after setting them");
    }

    @Test
    void testAddRoot() {
        SyntaxTree tree = new SyntaxTree();
        assertTrue(tree.getRoots().isEmpty(), "A new tree should have no roots.");

        SyntaxNode root1 = new SyntaxNode(new Word("root1"));
        tree.addRoot(root1);
        assertEquals(1, tree.getRoots().size());
        assertEquals(root1, tree.getRoots().get(0));

        SyntaxNode root2 = new SyntaxNode(new Word("root2"));
        tree.addRoot(root2);
        assertEquals(2, tree.getRoots().size());
        assertTrue(tree.getRoots().containsAll(Arrays.asList(root1, root2)), "Tree should contain all added roots");
    }

    @Test
    void testGetAllNodes() {
        SyntaxNode root1 = new SyntaxNode(new Word("root1"));
        SyntaxNode child1 = new SyntaxNode(new Word("child1"));
        root1.addChild(child1);
        
        SyntaxNode root2 = new SyntaxNode(new Word("root2"));
        SyntaxNode child2 = new SyntaxNode(new Word("child2"));
        root2.addChild(child2);
        
        ArrayList<SyntaxNode> roots = new ArrayList<>(Arrays.asList(root1, root2));
        SyntaxTree tree = new SyntaxTree(roots);
        
        ArrayList<SyntaxNode> allNodes = tree.getAllNodes();
        
        assertNotNull(allNodes, "getAllNodes should not return null");
        assertEquals(4, allNodes.size(), "There should be four nodes in total (two roots and two children)");
        assertTrue(allNodes.containsAll(Arrays.asList(root1, child1, root2, child2)), "All nodes should be present in the list");
    }
    
}
