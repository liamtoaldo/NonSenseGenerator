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

    // Override di equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Word)) return false;
        Word other = (Word) obj;
        return this.text.equals(other.text);
    }
}
