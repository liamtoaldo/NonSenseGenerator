package com.swe.nonsense;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that manages Words in Sentences
 */
public class Word {
    /**
     * String that represents the content of a Word
     */
    private String text;

    // Costruttori

    /**
     * Default constructor that creates a Word which contains an empty String
     */
    public Word() {
        this.text = "";
    }

    /**
     * Constructor that creates a Word which contains the text specified in the parameter
     * 
     * @param text The content of the Word
     */
    public Word(String text) {
        this.text = text;
    }

    // Getters e Setters

    /**
     * Method that return the content of the Word
     * 
     * @return The content inside the Word
     */
    public String getText() {
        return text;
    }

    /**
     * Method that changes the content of the Word by the specified parameter
     * 
     * @param text The new content of the Word
     */
    public void setText(String text) {
        this.text = text;
    }

    // Override di equals
    /**
     * Override of the equals method to compare two Word objects based on their text content
     * 
     * @param obj The object to compare with
     * @return true if the text content is the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Word))
            return false;
        Word other = (Word) obj;
        return this.text.equals(other.text);
    }

    // Metodo per esporre il tipo della classe a Jackson
    /**
     * Method to expose the class type to Jackson for JSON serialization
     * 
     * @return The simple name of the class (e.g., "Noun", "Verb", etc.)
     */
    @JsonProperty("wordClassType") // Il nome che apparirà nel JSON
    public String getWordClassType() {
        return this.getClass().getSimpleName(); // Restituisce "Noun", "Verb", "Word", ecc.
    }
}