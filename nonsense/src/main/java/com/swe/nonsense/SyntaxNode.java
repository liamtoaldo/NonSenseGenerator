package com.swe.nonsense;

import java.util.ArrayList;

public class SyntaxNode {
    private Word word;
    private ArrayList<SyntaxNode> children;

    public SyntaxNode() {
        this.word = null;
        this.children = new ArrayList<>();
    }

    public SyntaxNode(Word word) {
        this.word = word;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public ArrayList<SyntaxNode> getChildren() {
        return children;
    }

    public void addChild(SyntaxNode child) {
        this.children.add(child);
    }

    public void setChildren(ArrayList<SyntaxNode> children) {
        this.children = children;
    }
}
