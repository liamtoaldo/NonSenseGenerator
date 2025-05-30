package com.swe.nonsense;

import java.util.ArrayList;

public class SentenceHistory {
    private static SentenceHistory instance;
    private ArrayList<Sentence> savedSentences;

    //Constructors
    private SentenceHistory() {
        if (instance != null) {
            throw new IllegalStateException("SentenceHistory is a singleton and cannot be instantiated multiple times");
        }
        instance = this;
        this.savedSentences = new ArrayList<Sentence>();
    }

    private SentenceHistory(ArrayList<Sentence> savedSentences) {
        if (instance != null) {
            throw new IllegalStateException("SentenceHistory is a singleton and cannot be instantiated multiple times");
        }
        instance = this;
        this.savedSentences = savedSentences;
    }

    public static SentenceHistory getInstance() {
        return instance;
    }

    public void save(Sentence sentence) {
        savedSentences.add(sentence);
    }

    public ArrayList<Sentence> getLastSentence(int numberOfSentences) {
        ArrayList<Sentence> lastSentences = new ArrayList<>();
        if (numberOfSentences <= 0) {
            throw new IllegalArgumentException("Number of sentences visualized must be greater than 0");
        }
        for (int i = 0; i < numberOfSentences; i++) {
            lastSentences.add(savedSentences.get((savedSentences.size() - 1) - i));
        }
        return lastSentences;
    }
}