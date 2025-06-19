package com.swe.nonsense;

/**
 * Class that manages the creation of Nouns which are treated as Words
 */
public class Noun extends Word {

    // Costruttori

    /**
     * Default constructor that creates an empty Noun
     */
    public Noun() {
        super();
    }

    /**
     * Constructor that creates a Noun which contains the text specified in the parameter
     * 
     * @param text The content of the Noun
     */
    public Noun(String text) {
        super(text);
    }
}
