package com.swe.nonsense;

/**
 * Class that manages Verbs which are treated as words
 */
public class Verb extends Word {
    /**
     * Tense of the Verb
     */
    private Tense tense;

    // Costruttori

    /**
     * Default constructor that creates an empty Verb. The Tense of the Verb is set to Present
     */
    public Verb() {
        super();
        this.tense = Tense.PRESENT; // Abbiamo posto di default presente
    }

    /**
     * Constructor that creates a Verb which contains the text specified in the parameter. 
     * The Tense of the Verb is set to Present
     * 
     * @param text The content of the Verb
     */
    public Verb(String text) {
        super(text);
        this.tense = Tense.PRESENT;
    }

    /**
     * Constructor that creates a Verb which contains the text specified in the parameter. 
     * The Tense of the Verb ia also specified in the parameter.
     * 
     * @param text The content of the Verb
     * @param tense The Tense of the Verb
     */
    public Verb(String text, Tense tense) {
        super(text);
        this.tense = tense;
    }

    // Getters e Setters

    /**
     * Method that returns the Tense of the Verb
     * 
     * @return the Tense of the Verb
     */
    public Tense getTense() {
        return tense;
    }

    /**
     * Method that changes the Tense of the Verb
     * 
     * @param tense The new Tense of the Verb
     */
    public void setTense(Tense tense) {
        this.tense = tense;
    }
}
