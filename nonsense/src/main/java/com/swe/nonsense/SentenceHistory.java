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
     * @throws IllegalStateException If an instance already exists
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
     * @return An ArrayList containing all saved sentences
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
     * @param numberOfSentences The number of sentences to be returned from the history
     * @return A number of sentences saved in the history equal to numberOfSentences parameter
     * @throws IllegalArgumentException If numberOfSentences is less than or equal to 0
     */
    public ArrayList<Sentence> getLastSentences(int numberOfSentences) {
        ArrayList<Sentence> lastSentences = new ArrayList<>();
        if ((numberOfSentences <= 0)) {
            throw new IllegalArgumentException("Number of sentences visualized must be greater than 0.");
        }
        //  If the number of sentences requested is greater than the number of saved sentences, we limit it to the number of saved sentences
        if (savedSentences.size() < numberOfSentences) {
            numberOfSentences = savedSentences.size();
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
     * Method that removes all sentences from the history.
     *
     */
    public void clearData() {
        savedSentences.clear();
    }
}