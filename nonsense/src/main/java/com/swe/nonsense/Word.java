package com.swe.nonsense;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Word {
    private String text;

    // Costruttori
    public Word() {
        this.text = "";
    }

    public Word(String text) {
        this.text = text;
    }

    // Getters e Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // Override di equals
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
    @JsonProperty("wordClassType") // Il nome che apparirà nel JSON
    public String getWordClassType() {
        return this.getClass().getSimpleName(); // Restituisce "Noun", "Verb", "Word", ecc.
    }
}
