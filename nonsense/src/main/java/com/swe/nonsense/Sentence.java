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

    
}
