package com.swe.nonsense;

import java.util.ArrayList;

/**
 * Class that manages the history of sentences. It is a singleton class so it can only be instantiated once.
 */
public class SentenceHistory {

    /**
     * Singleton instance of SentenceHistory
     */
    private static SentenceHistory instance;

    /**
     * ArrayList of Sentences that contains the saved sentences
     */
    private ArrayList<Sentence> savedSentences;

    //Costruttore
    /**
     * Private constructor to enforce singleton pattern
     * @throws IllegalStateException if an instance already exists
     */
    private SentenceHistory() {
        if (instance != null) {
            throw new IllegalStateException("SentenceHistory is a singleton and cannot be instantiated multiple times");
        }
        instance = this;
        this.savedSentences = new ArrayList<Sentence>();
    }

    //Getters

    /**
     * Method that returns the singleton instance of SentenceHistory
     * 
     * @return instance of SentenceHistory
     */
    public static SentenceHistory getInstance() {
        if (instance == null) {
            instance = new SentenceHistory();
        }
        return instance;
    }

    /**
     * Method that returns all saved sentences
     *
     * @return an ArrayList containing all saved sentences
     */
    public ArrayList<Sentence> getSentences() {
        return savedSentences;
    }

    /*
     * Dato savedSentences[sentence_n,..., sentence2, sentence1]
     * Le sentences in lastSentence saranno visualizzate nel seguente ordine: 
     * lastSentences[sentence1, sentence2, ..., sentence_n]
     */
    /**
     * Method that returns a number of saved sentences from the history.
     * This method returns the last sentences saved in the history, in reverse order.
     * 
     * @param numberOfSentences
     * @return a number of sentences saved in the history equal to numberOfSentences parameter
     * @throws IllegalArgumentException if numberOfSentences is less than or equal to 0 or greater than the number of saved sentences
     */
    public ArrayList<Sentence> getLastSentences(int numberOfSentences) {
        ArrayList<Sentence> lastSentences = new ArrayList<>();
        if ((numberOfSentences <= 0) || (savedSentences.size() < numberOfSentences)) {
            throw new IllegalArgumentException("Number of sentences visualized must be greater than 0 and less than or equal to the number of saved sentences.");
        }
        for (int i = 0; i < numberOfSentences; i++) {
            lastSentences.add(savedSentences.get((savedSentences.size() - 1) - i));
        }
        return lastSentences;
    }

    //Metodi per aggiungere e togliere i dati

    /**
     * Method that saves a sentence in the history
     *
     * @param sentence The sentence to be saved
     */
    public void save(Sentence sentence) {
        savedSentences.add(sentence);
    }

    /**
     * Method that removes a sentence from the history
     *
     * @param sentence The sentence to be removed
     */
    public void clearData() {
        savedSentences.clear();
    }
}