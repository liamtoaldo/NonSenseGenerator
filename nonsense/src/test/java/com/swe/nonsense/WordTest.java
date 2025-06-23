package com.swe.nonsense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WordTest {

    private Word word;

    @BeforeEach
    void setUp() {
        word = new Word("test");
    }

    @Test
    void testWordCreation() {
        assert word != null : "Word should be created successfully";
    }

    @Test
    void testWordCreationNoText() {
        Word emptyWord = new Word();
        assert emptyWord.getText().equals("") : "Word should be created with an empty string when no text is provided";
    }

    @Test
    void testGetText() {
        assert word.getText().equals("test")
                : "getText should return the correct text of the word when constructed with text";
    }

    @Test
    void testSetText() {
        word.setText("newTest");
        assert word.getText().equals("newTest") : "setText should update the text of the word";
    }

    @Test
    void testAdjectiveCreation() {
        Adjective adjective = new Adjective("beautiful");
        assert adjective.getText().equals("beautiful") : "Adjective should be created with the correct text";
    }

    @Test
    void testNounCreation() {
        Noun noun = new Noun("dog");
        assert noun.getText().equals("dog") : "Noun should be created with the correct text";
    }

    @Test
    void testEquals() {
        Word anotherWord = new Word("test");
        assert word.equals(anotherWord) : "Two words with the same text should be equal";

        Word differentWord = new Word("different");
        assert !word.equals(differentWord) : "Two words with different text should not be equal";
    }

}