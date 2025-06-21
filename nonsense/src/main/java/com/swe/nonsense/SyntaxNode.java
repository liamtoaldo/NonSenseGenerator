package com.swe.nonsense;

import java.util.ArrayList;

/**
 * Class that represents a syntax node. A syntax node is a part of a syntax tree.
 */
public class SyntaxNode {
    /**
     * Word object contained in the syntax node
     */
    private Word word;
    /**
     * ArrayList of SyntaxNode that contains the children of a particular syntax node
     */
    private ArrayList<SyntaxNode> children;
    /**
     * String that indicates the syntactic dependency relation between this node and its parent
     */
    private String dependencyLabel;

    /**
     * Default constructor that initializes an empty syntax node with no dependency label
     */
    public SyntaxNode() {
        this.word = null;
        this.children = new ArrayList<>();
        this.dependencyLabel = "";
    }

    /**
     * Constructor that initializes a syntax node with a given Word object but with no dependency label
     * 
     * @param word The Word object contained in the syntax node
     */
    public SyntaxNode(Word word) {
        this.word = word;
        this.children = new ArrayList<>();
        this.dependencyLabel = "";
    }

    /**
     * Constructor that initializes a syntax node with a given Word object and a dependency label
     * 
     * @param word The Word object contained in the syntax node
     * @param dependencyLabel The syntactic dependency relation label between this node and its parent
     */
    public SyntaxNode(Word word, String dependencyLabel) {
        this.word = word;
        this.children = new ArrayList<>();
        this.dependencyLabel = dependencyLabel;
    }

    /**
     * Method that returns the Word object contained in the syntax node
     * 
     * @return The Word object contained in the syntax node
     */
    public Word getWord() {
        return word;
    }

    /**
     * Method that sets the Word object contained in the syntax node with the one given in the parameter
     * 
     * @param word The Word object to be set in the syntax node
     */
    public void setWord(Word word) {
        this.word = word;
    }

    /**
     * Method that returns the syntactic dependency relation label between this node and its parent
     * 
     * @return The syntactic dependency relation label between this node and its parent
     */
    public String getDependencyLabel() {
        return dependencyLabel;
    }

    /**
     * Method that sets the syntactic dependency relation label between this node and its parent
     * 
     * @param dependencyLabel The syntactic dependency relation label to be set between this node and its parent
     */
    public void setDependencyLabel(String dependencyLabel) {
        this.dependencyLabel = dependencyLabel;
    }

    /**
     * Method that returns all the children of a particular syntax node
     * 
     * @return An ArrayList of SyntaxNode that contains the children of a particular syntax node
     */
    public ArrayList<SyntaxNode> getChildren() {
        return children;
    }

    /**
     * Method that adds a child to the syntax node
     * 
     * @param child The SyntaxNode to be added as a child of the current syntax node
     */
    public void addChild(SyntaxNode child) {
        this.children.add(child);
    }

    /**
     * Method that sets the children of the syntax node with the ones given in the parameter
     * 
     * @param children An ArrayList of SyntaxNode that contains the children to be set for the current syntax node
     */
    public void setChildren(ArrayList<SyntaxNode> children) {
        this.children = children;
    }
}
