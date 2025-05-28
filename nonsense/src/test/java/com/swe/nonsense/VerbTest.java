package com.swe.nonsense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VerbTest {

    private Verb verb;

    @BeforeEach
    void setUp() {
        verb = new Verb("test", Tense.PRESENT);
    }

    @Test
    void testGetTense() {
        assert verb.getTense() == Tense.PRESENT : "getTense should return the correct tense of the verb";
    }

    @Test
    void testSetTense() {
        verb.setTense(Tense.FUTURE);
        assert verb.getTense() == Tense.FUTURE : "setTense should update the tense of the verb";
    }

    @Test
    void testGetText() {
        assert verb.getText().equals("test") : "getText should return the correct text of the verb";
    }

    @Test
    void testSetText() {
        verb.setText("newTest");
        assert verb.getText().equals("newTest") : "setText should update the text of the verb";
    }



}
