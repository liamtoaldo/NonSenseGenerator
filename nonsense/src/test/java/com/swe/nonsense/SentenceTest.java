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
    void testDefaultConstructor() {
        Sentence defaultSentence = new Sentence();
        assert defaultSentence != null : "Default constructor should create a non-null Sentence object";
        assert defaultSentence.getText().isEmpty() : "Default constructor should create an empty sentence";
        assert defaultSentence.getGenerationDate() != null
                : "Default constructor should set a non-null generation date";
    }

    @Test
    void testConstructorWithWords() {
        assert sentence != null : "Constructor with words should create a non-null Sentence object";
        assert sentence.getText().equals(words)
                : "Constructor with words should initialize the sentence with the provided words";
        assert sentence.getGenerationDate() != null : "Constructor with words should set a non-null generation date";
    }

    @Test
    void testConstructorWithString() {
        String sentenceText = "This is a test sentence";
        Sentence stringSentence = new Sentence(sentenceText);
        assert stringSentence != null : "Constructor with string should create a non-null Sentence object";
        assert stringSentence.getText().size() > 0 : "Constructor with string should create a sentence with words";
        assert stringSentence.getGenerationDate() != null
                : "Constructor with string should set a non-null generation date";
    }

    @Test
    void testGetText() {
        assert sentence.getText().equals(words) : "getText should return the initial words of the sentence from setUp";
    }

    @Test
    void testGetRandomNoun() {
        sentence.getText().add(new Noun("cat"));
        Noun randomNoun = sentence.getRandomNoun();
        assert randomNoun != null : "getRandomNoun should return a non-null Noun from the sentence";
        assert words.contains(randomNoun) : "getRandomNoun should return a noun that is part of the sentence's words";
    }

    @Test
    void testGetRandomAdjective() {
        sentence.getText().add(new Adjective("beautiful"));
        Adjective randomAdjective = sentence.getRandomAdjective();
        assert randomAdjective != null : "getRandomAdjective should return a non-null Adjective from the sentence";
        assert words.contains(randomAdjective) : "getRandomAdjective should return an adjective that is part of the sentence's words";
    }

    @Test
    void testGetGenerationDate() {
        assert sentence.getGenerationDate() != null
                : "getGenerationDate should return a non-null date for the sentence from setUp";
    }

    @Test
    void testSetGenerationDate() {
        LocalDateTime newDate = LocalDateTime.of(2024, 9, 11, 12, 0);
        sentence.setGenerationDate(newDate);
        assert sentence.getGenerationDate().equals(newDate)
                : "setGenerationDate should update the generation date of the sentence from setUp";
    }

    @Test
    void testToString() {
        String expectedString = "Hello World";
        assert sentence.toString().equals(expectedString)
                : "toString should return the correct string representation of the sentence from setUp";
    }
}