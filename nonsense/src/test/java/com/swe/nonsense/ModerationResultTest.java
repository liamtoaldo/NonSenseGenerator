package com.swe.nonsense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ModerationResultTest {
    ModerationResult result;
    ArrayList<ModerationCategory> categories;

    @BeforeEach
    void setUp() {
        result = new ModerationResult();
        categories = new ArrayList<>();
        categories.add(new ModerationCategory("test", 0.5));
    }
    @Test
    void testDefaultConstructor() {
        ModerationResult result = new ModerationResult();
        assert result != null : "ModerationResult should be instantiated successfully";
    }

    @Test
    void testConstructorWithCategories() {
        result = new ModerationResult(categories);
        assert result.getCategories().size() == 1 : "Constructor should initialize with provided categories";
        assert result.getCategories().get(0).getName().equals("test") : "Category name should match the provided name";
    }

    @Test
    void testGetCategories() {
        assert result.getCategories() != null : "getCategories should return a non-null list";
        assert result.getCategories().isEmpty() : "getCategories should return an empty list initially";
    }

    @Test
    void testSetCategories() {
        result.setCategories(categories);
        assert result.getCategories().size() == 1 : "setCategories should set the categories correctly";
        assert result.getCategories().get(0).getName().equals("test") : "Category name should match the set name";
    }

    @Test
    void testGetCategoryByName() {
        result.setCategories(categories);
        ModerationCategory category = result.getCategoryByName("test");
        assert category != null : "getCategoryByName should return a non-null category";
        assert category.getName().equals("test") : "getCategoryByName should return the correct category by name";
    }
}
