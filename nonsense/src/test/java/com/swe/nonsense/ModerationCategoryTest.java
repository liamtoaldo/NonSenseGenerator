package com.swe.nonsense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModerationCategoryTest {
    private ModerationCategory category;

    @BeforeEach
    void setUp() {
        category = new ModerationCategory("test", 0.5);
    }

    @Test
    void testGetName() {
        assert category.getName().equals("test") : "getName should return the correct name";
    }

    @Test
    void testGetConfidence() {
        assert category.getConfidence() == 0.5 : "getConfidence should return the correct confidence";
    }

    @Test
    void testSetName() {
        category.setName("newTest");
        assert category.getName().equals("newTest") : "setName should set the name correctly";
    }

    @Test
    void testSetConfidence() {
        category.setConfidence(0.8);
        assert category.getConfidence() == 0.8 : "setConfidence should set the confidence correctly";
    }
}
