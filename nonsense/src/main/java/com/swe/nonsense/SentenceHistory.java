package com.swe.nonsense;

import java.util.ArrayList;

public class SentenceHistory {
    private static SentenceHistory instance;
    private ArrayList<Sentence> savedSentences;

    //Costruttore
    private SentenceHistory() {
        if (instance != null) {
            throw new IllegalStateException("SentenceHistory is a singleton and cannot be instantiated multiple times");
        }
        instance = this;
        this.savedSentences = new ArrayList<Sentence>();
    }

    //Getters
    public static SentenceHistory getInstance() {
        if (instance == null) {
            instance = new SentenceHistory();
        }
        return instance;
    }

    //Dato savedSentences[sentence_n,..., sentence2, sentence2, sentence1]
    //Le sentences in lastSentence saranno visualizzate nel seguente ordine: 
    //lastSentences[sentence1, sentence2, ..., sentence_n]
    public ArrayList<Sentence> getLastSentence(int numberOfSentences) {
        ArrayList<Sentence> lastSentences = new ArrayList<>();
        if ((numberOfSentences <= 0) || (savedSentences.size() < numberOfSentences)) {
            throw new IllegalArgumentException("Number of sentences visualized must be greater than 0 and less than or equal to the number of saved sentences.");
        }
        for (int i = 0; i < numberOfSentences; i++) {
            lastSentences.add(savedSentences.get((savedSentences.size() - 1) - i));
        }
        return lastSentences;
    }

    //Metodo per i dati
    public void save(Sentence sentence) {
        savedSentences.add(sentence);
    }
}