package com.swe.nonsense;

public class ModerationCategory {
    private String name;
    private double confidence;

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

    public String getName() {
        return name;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public void setConfidence(double confidence) throws IllegalArgumentException {
        if (confidence < 0 || confidence > 1) {
            throw new IllegalArgumentException("Confidence must be between 0 and 1");
        }
        this.confidence = confidence;
    }
}
