package com.swe.nonsense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ToxicityAnalyzerTest {

    private ToxicityAnalyzer toxicityAnalyzer;

    @BeforeEach
    void setUp() {
        toxicityAnalyzer = new ToxicityAnalyzer(); 
    }

    @Test
    void testConstructor() {
        assert toxicityAnalyzer != null : "ToxicityAnalyzer should be instantiated successfully";
    }

    @Test
    void testAnalyzeToxicity() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("this"));
        words.add(new Word("is"));
        words.add(new Word("a"));
        words.add(new Word("test"));
        Sentence sentence = new Sentence(words);

        ModerationResult result = toxicityAnalyzer.analyzeToxicity(sentence);

        assert result != null : "analyzeToxicity should return a ModerationResult not null";
        assert result.getCategories() != null : "Categories should not be null in the ModerationResult";
        assert !result.getCategories().isEmpty() : "Categories should not be empty in the ModerationResult";
    }
    
}