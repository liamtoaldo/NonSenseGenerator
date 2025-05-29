package com.swe.nonsense;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sentence {
    private ArrayList<Word> words;
    private LocalDateTime generationDate;

    // Costruttori
    public Sentence() {
        this.words = new ArrayList<>();
        this.generationDate = LocalDateTime.now();
    }
    public Sentence(ArrayList<Word> words) {
        this.words = words;
        this.generationDate = LocalDateTime.now();
    }
    
    public Sentence(String sentence) {
        this.words = new ArrayList<>();
        if (sentence != null && !sentence.isEmpty()) {
            String[] parts = sentence.split(" ");
            for (String part : parts) {
                if (!part.isEmpty()) {
                    this.words.add(new Word(part));
                }
            }
        }
        this.generationDate = LocalDateTime.now();
    }

    // Getters e Setters
    public ArrayList<Word> getText() {
        return words;
    }
    public LocalDateTime getGenerationDate() {
        return generationDate;
    }
    public void setGenerationDate(LocalDateTime generationDate) {
        this.generationDate = generationDate;
    }

    // Metodi
    @Override
    public String toString() {
        String sentence = "";
        for (Word word : words) {
            sentence += word.getText() + " ";
        }
        return sentence.trim();
    }

    
}
