package com.swe.nonsense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TemplateTest {

    private Template template;

    @BeforeEach
    void setUp() {
        template = new Template("Sample template");
    }

    @Test
    void testConstructorNoText() {
        Template emptyTemplate = new Template();
        assert emptyTemplate.getTemplate().equals("") : "Constructor without text should initialize with an empty template";
    }

    @Test
    void testConstructorWithText() {
        assert template.getTemplate().equals("Sample template") : "Constructor with text should initialize with the provided template";
    }

    @Test
    void testGetTemplate() {
        assert template.getTemplate().equals("Sample template") : "getTemplate should return the correct template text";
    }
    
    @Test
    void testSetTemplate() {
        template.setTemplate("New template text");
        assert template.getTemplate().equals("New template text") : "setTemplate should update the template text";
    }


    
}
