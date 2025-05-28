package com.swe.nonsense;

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
}
