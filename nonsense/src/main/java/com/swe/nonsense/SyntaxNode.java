package com.swe.nonsense;

import java.util.ArrayList;

public class SyntaxNode {
    private Word word;
    private ArrayList<SyntaxNode> children;
    private String dependencyLabel;

    public SyntaxNode() {
        this.word = null;
        this.children = new ArrayList<>();
        this.dependencyLabel = "";
    }

    public SyntaxNode(Word word) {
        this.word = word;
        this.children = new ArrayList<>();
        this.dependencyLabel = "";
    }

    public SyntaxNode(Word word, String dependencyLabel) {
        this.word = word;
        this.children = new ArrayList<>();
        this.dependencyLabel = dependencyLabel;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getDependencyLabel() {
        return dependencyLabel;
    }

    public void setDependencyLabel(String dependencyLabel) {
        this.dependencyLabel = dependencyLabel;
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
