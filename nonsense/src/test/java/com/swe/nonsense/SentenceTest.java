package com.swe.nonsense;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SentenceTest {
    private Sentence sentence; 
    private ArrayList<Word> words;

    @BeforeEach
    void setUp() {
        words = new ArrayList<>();
        words.add(new Word("Hello"));
        words.add(new Word("World"));
        sentence = new Sentence(words);
    }

    @Test
    void testGetText() {
        assert sentence.getText().equals(words) : "getText should return the initial words of the sentence from setUp";
    }

    @Test
    void testGetGenerationDate() {
        assert sentence.getGenerationDate() != null : "getGenerationDate should return a non-null date for the sentence from setUp";
    }

    @Test
    void testSetGenerationDate() {
        LocalDateTime newDate = LocalDateTime.of(2023, 10, 1, 12, 0);
        sentence.setGenerationDate(newDate);
        assert sentence.getGenerationDate().equals(newDate) : "setGenerationDate should update the generation date of the sentence from setUp";
    }
}