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
    void testGetTemplate() {
        assert template.getTemplate().equals("Sample template") : "getTemplate should return the correct template text";
    }
    
    @Test
    void testSetTemplate() {
        template.setTemplate("New template text");
        assert template.getTemplate().equals("New template text") : "setTemplate should update the template text";
    }


    
}
