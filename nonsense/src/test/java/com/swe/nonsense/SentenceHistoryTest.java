package com.swe.nonsense;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SentenceHistoryTest {

    private SentenceHistory sentenceHistory;
    private ArrayList<Sentence> savedSentences;
    private Sentence sentence1;
    private Sentence sentence2;
    private ArrayList<Word> words1;
    private ArrayList<Word> words2;
    private Word word1;
    private Word word2;
    private Word word3;
    private Word word4;
    
    @BeforeEach
    void setUp() {
        //Creazione della prima sentence
        word1 = new Word("Testing");
        word2 = new Word("SentenceHistory");
        words1 = new ArrayList<>();
        words1.add(word1);
        words1.add(word2);
        sentence1 = new Sentence(words1);

        //Creazione della seconda sentence
        word3 = new Word("Another");
        word4 = new Word("Test");
        words2 = new ArrayList<>();
        words2.add(word3);
        words2.add(word4);
        sentence2 = new Sentence(words2);

        //Inizializzazione di savedSentences con le sentences create
        savedSentences = new ArrayList<>();
        savedSentences.add(sentence1);
        savedSentences.add(sentence2);
        
        sentenceHistory = SentenceHistory.getInstance();
        sentenceHistory.clearData();
        sentenceHistory.save(sentence1);
        sentenceHistory.save(sentence2);
    }

    @Test
    void testConstructorWithSavedSentences() {
        assert sentenceHistory != null : "Constructor with saved sentences should create a non-null SentenceHistory object";
    }

    @Test
    void testDefaultConstructor() {
        assert sentenceHistory != null : "Default constructor should create a non-null SentenceHistory object";
    }

    //Test per i getters
    @Test
    void testGetInstance() {
        SentenceHistory instance = SentenceHistory.getInstance();
        assert instance != null : "getInstance should return a non-null SentenceHistory instance";
    }

    @Test
    void testGetSentences() {
        ArrayList<Sentence> sentences = sentenceHistory.getSentences();
        assert sentences != null : "getSentences should return a non-null list of Sentences";

    }

    @Test
    void testGetLastSentence() {
        ArrayList<Sentence> lastSentences = sentenceHistory.getLastSentences(1);
        assert lastSentences.get(lastSentences.size() - 1).equals(sentence2) : "getLastSentence should return the correct number of sentences";
    }

    //Test per metodo save()
    @Test
    void testSaveOneSentence() {
        sentenceHistory.save(sentence1);
        ArrayList<Sentence> lastSentences = sentenceHistory.getLastSentences(1);
        assert lastSentences.get(lastSentences.size() - 1).equals(sentence1) : "save should store the sentence in the SentenceHistory object";
    }

    //Secondo test per metodo save()
    @Test
    void testSaveMoreSentences() {
        
        sentenceHistory.save(sentence1);
        sentenceHistory.save(sentence2);
        ArrayList<Sentence> newLastSentences = sentenceHistory.getLastSentences(2);
        assert newLastSentences.get(newLastSentences.size() - 1).equals(sentence1) : "getLastSentence should return the most recent sentence first";
        assert newLastSentences.get(newLastSentences.size() - 2).equals(sentence2) : "getLastSentence should return the second most recent sentence second";
    }

    @AfterEach
    void tearDown() {
        // Pulisce i dati dalla cronologia dopo ogni test per garantire l'isolamento
        sentenceHistory.clearData();
    }
}