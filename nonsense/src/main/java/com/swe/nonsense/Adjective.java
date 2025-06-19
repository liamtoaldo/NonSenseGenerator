package com.swe.nonsense;

/**
 * Class that manages the creation of Adjectives which are trated as Words
 */
public class Adjective  extends Word {

    // Costruttori

    /**
     * Default constructor that creates an empty Adjective
     */
    public Adjective() {
        super();
    }

    /**
     * Constructor that creates an Adjective which contains the text specified in the parameter
     * 
     * @param text The content of the Adjective
     */
    public Adjective(String text) {
        super(text);
    }
}
