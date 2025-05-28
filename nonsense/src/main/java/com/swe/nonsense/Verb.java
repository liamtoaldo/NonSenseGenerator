package com.swe.nonsense;

public class Verb extends Word {
    private Tense tense;

    // Costruttori
    public Verb() {
        super();
        this.tense = Tense.PRESENT; // Abbiamo posto di default presente
    }
    public Verb(String text) {
        super(text);
        this.tense = Tense.PRESENT;
    }
    public Verb(String text, Tense tense) {
        super(text);
        this.tense = tense;
    }

    // Getters e Setters
    public Tense getTense() {
        return tense;
    }
    public void setTense(Tense tense) {
        this.tense = tense;
    }
    
}
