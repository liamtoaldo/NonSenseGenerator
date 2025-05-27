package com.swe.nonsense;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SyntaxNodeTest {
    private Word testWord;

    @BeforeAll
    void setUp() {
        testWord = new Word("word");
    }

    @Test
    void testGetWord() {
        SyntaxNode node = new SyntaxNode(testWord);
        assert node.getWord().equals(testWord) : "getWord should return the correct Word object";
    }

    @Test
    void testSetWord() {
        SyntaxNode node = new SyntaxNode();
        node.setWord(testWord);
        assert node.getWord().equals(testWord) : "setWord should set the Word object correctly";
    }

    @Test
    void testGetChildren() {
        SyntaxNode node = new SyntaxNode();
        assert node.getChildren() != null : "getChildren should return a non-null ArrayList";
        assert node.getChildren().isEmpty() : "getChildren should return an empty ArrayList initially";
    }

    @Test
    void testAddChild() {
        SyntaxNode parent = new SyntaxNode();
        SyntaxNode child = new SyntaxNode(new Word("figlio"));
        parent.addChild(child);
        assert parent.getChildren().size() == 1 : "addChild should add a child to the children list";
        assert parent.getChildren().get(0).equals(child) : "The added child should be retrievable from the children list";
    }

    @Test
    void testSetChildren() {
        SyntaxNode node = new SyntaxNode();
        ArrayList<SyntaxNode> children = new ArrayList<>();
        children.add(new SyntaxNode(new Word("figlio1")));
        children.add(new SyntaxNode(new Word("figlio2")));
        node.setChildren(children);
        assert node.getChildren().size() == 2 : "setChildren should set the children list correctly";
        assert node.getChildren().get(0).getWord().equals(new Word("figlio1")) : "First child should be figlio1";
        assert node.getChildren().get(1).getWord().equals(new Word("figlio2")) : "Second child should be figlio2";
    }
}
