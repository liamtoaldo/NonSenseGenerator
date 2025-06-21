package com.swe.nonsense;

/**
 * Class that manages Templates for sentences.
 * Templates are used to help creating simple English sentences.
 * e.g. "The [adjective] [noun] [verb] the [noun]."
 */
public class Template {
    /**
     * Template needed to create and/or change a Template Object 
     */    
    private String template;

    // Costruttori

    /**
     * Default constructor that creates a Template which contains an empty String
     */
    public Template() {
        this.template = "";
    }

    /**
     * Constructor that creates a Template based on the parameter of this method
     * 
     * @param template The effective Template
     */
    public Template(String template) {
        this.template = template;
    }

    // Getters e Setters

    /**
     * Method that returns the Template
     * 
     * @return The Template contained in the Template Object
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Method that changes the Template based on the parameter of this method
     * 
     * @param template The new content of the Template Object
     */
    public void setTemplate(String template) {
        this.template = template;
    }
}
