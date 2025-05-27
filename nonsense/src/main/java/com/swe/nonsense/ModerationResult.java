package com.swe.nonsense;

import java.util.ArrayList;

public class ModerationResult {
    private ArrayList<ModerationCategory> categories;

    public ModerationResult() {
        this.categories = new ArrayList<>();
    }

    public ModerationResult(ArrayList<ModerationCategory> categories) throws IllegalArgumentException {
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Categories cannot be null or empty");
        }
        this.categories = categories;
    }

    public ArrayList<ModerationCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<ModerationCategory> categories) throws IllegalArgumentException {
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Categories cannot be null or empty");
        }
        this.categories = categories;
    }

    public ModerationCategory getCategoryByName(String name) {
        for (ModerationCategory category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null; // or throw an exception if preferred
    }

}
