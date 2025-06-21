package com.swe.nonsense;

/**
 * Class that represents a moderation category with a name and confidence score
 */
public class ModerationCategory {
    /**
     * String that represents the name of the moderation category
     */
    private String name;
    /**
     * Double that represents the confidence score of the moderation category, it must be between 0 and 1
     */
    private double confidence;

    /**
     * Constructor that initializes the moderation category with a name and confidence score
     * 
     * @param name The name of the moderation category
     * @param confidence The confidence score of the moderation category
     * @throws IllegalArgumentException If the name is null or empty, or if the confidence score is not between 0 and 1
     */
    public ModerationCategory(String name, double confidence) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (confidence < 0 || confidence > 1) {
            throw new IllegalArgumentException("Confidence must be between 0 and 1");
        }

        this.name = name;
        this.confidence = confidence;
    }

    /**
     * Method that returns the name of the moderation category
     * 
     * @return The name of the moderation category
     */
    public String getName() {
        return name;
    }

    /**
     * Method that returns the confidence score of the moderation category
     * 
     * @return The confidence score of the moderation category
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * Method that sets the name of the moderation category
     * 
     * @param name The new name to be set of the moderation category
     * @throws IllegalArgumentException If the name is null or empty
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Method that sets the confidence score of the moderation category
     * 
     * @param confidence The new confidence score to be set of the moderation category
     * @throws IllegalArgumentException If the confidence score is not between 0 and 1
     */
    public void setConfidence(double confidence) throws IllegalArgumentException {
        if (confidence < 0 || confidence > 1) {
            throw new IllegalArgumentException("Confidence must be between 0 and 1");
        }
        this.confidence = confidence;
    }
}
