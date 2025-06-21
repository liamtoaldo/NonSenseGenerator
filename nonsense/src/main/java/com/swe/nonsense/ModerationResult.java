package com.swe.nonsense;

import java.util.ArrayList;

/**
 * Class that represents a moderation result of a moderation category
 */
public class ModerationResult {
    /**
     * ArrayList that contains the moderation categories
     */
    private ArrayList<ModerationCategory> categories;

    /**
     * Default constructor that initializes the moderation result with an empty list of categories
     */
    public ModerationResult() {
        this.categories = new ArrayList<>();
    }

    /**
     * Constructor that initializes the moderation result with a list of categories specified in the parameter
     * 
     * @param categories ArrayList of ModerationCategory objects that contains the categories 
     * @throws IllegalArgumentException If the categories list is null or empty
     */
    public ModerationResult(ArrayList<ModerationCategory> categories) throws IllegalArgumentException {
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Categories cannot be null or empty");
        }
        this.categories = categories;
    }

    /**
     * Method that returns the list of moderation categories
     * 
     * @return An ArrayList of ModerationCategory objects that contains the categories
     */
    public ArrayList<ModerationCategory> getCategories() {
        return categories;
    }

    /**
     * Method that sets the list of moderation categories
     * 
     * @param categories The new list of ModerationCategory objects to be set
     * @throws IllegalArgumentException If the new categories list is null or empty
     */
    public void setCategories(ArrayList<ModerationCategory> categories) throws IllegalArgumentException {
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Categories cannot be null or empty");
        }
        this.categories = categories;
    }

    /**
     * Method that returns the categories by searching through the list by name
     * 
     * @param name The name of the category to search for
     * @return The ModerationCategory object that matches the name, or null if not found
     */
    public ModerationCategory getCategoryByName(String name) {
        for (ModerationCategory category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null; // or throw an exception if preferred
    }

}
