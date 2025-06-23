package com.swe.nonsense;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST controller that provides endpoints for various functionalities of the application
 */
@RestController
@RequestMapping("/api/v1/nonsense")
public class ApplicationRestController {
    /**
      * ApplicationController object that manages the logic of the application
      */
    private final ApplicationController app;

    /**
     * Constructor that initializes the ApplicationRestController with the ApplicationController
     * 
     * @param app The ApplicationController object that manages the logic of the application
     */
    public ApplicationRestController(ApplicationController app) {
        this.app = app;
    }

    /**
     * Method that returns the SyntaxTree from a given sentence text
     * 
     * @param sentenceText The text of the sentence to be analyzed
     * @return SyntaxTree object representing the syntax structure of the sentence
     */
    @GetMapping("/sentence/syntax")
    
    public SyntaxTree getSyntaxTree(@RequestParam String sentenceText) {
        return app.getSyntaxTreeFromString(sentenceText);
    }

    /**
     * Method that generates a nonsense sentence based on the given input
     * 
     * @param text The sentence text to be used as a base for generating the nonsense sentence
     * @param template The template to be used for generating the nonsense sentence 
     * (though it's not required)
     * @param tense The tense of the verbs that will be in the nonsense sentence
     * @return A String representation of the generated sentence
     */
    @GetMapping("/sentence/generate")
    public String generateSentenceInput(
        @RequestParam String text,
        @RequestParam(required = false) Template template,
        @RequestParam Tense tense) {
        return app.generateNonSenseSentence(text, template, tense).toString();
    }

    
    /**
     * Method that analyzes the toxicity of a given sentence text
     * 
     * @param text The text of the sentence to be analyzed for toxicity
     * @return A JSON representation of the ModerationResult object containing the result of the toxicity analysis
     * @throws RuntimeException If the conversion of ModerationResult to JSON fails
     */
    @GetMapping("/sentence/toxicity")
    public String getSentenceToxicity(@RequestParam String text) {
        Sentence sentence = app.convertStringToSentence(text);
        ModerationResult result = app.getSentenceToxicity(sentence);
        // JSON conversion
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        try {
            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert ModerationResult to JSON", e);
        }
    }

    /**
     * Method that retrieves a specified amount of sentences from history
     * 
     * @param amount The amount of sentences to be retrieved from history
     * @return A JSON representation of the ArrayList<Sentence> object containing the requested amount of sentences from history
     * @throws RuntimeException If the retrieval or the conversion of ModerationResult to JSON fail
     */
    @GetMapping("/history/generated/{amount}")
    public String getLastGeneratedSentences(@PathVariable int amount) {
        ArrayList<Sentence> sentences = new ArrayList<Sentence>();
        try {
            sentences = app.getLastSentences(amount);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid amount specified: " + amount, e);
        }
        ArrayList<Object> sentenceObjects = new ArrayList<>();
        for (Sentence sentence : sentences) {
            java.util.HashMap<String, Object> map = new java.util.HashMap<>();
            map.put("text", sentence.toString());
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            map.put("date", sentence.getGenerationDate().format(formatter));
            sentenceObjects.add(map);
        }
        // JSON conversion
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        try {
            return mapper.writeValueAsString(sentenceObjects);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert ModerationResult to JSON", e);
        }
    }

    /**
     * Method that retrieves all templates from the application
     * 
     * @return An ArrayList of Template objects containing all templates in the application
     */
    @GetMapping("/dictionary/templates")
    public ArrayList<Template> getTemplates() {
        return app.getTemplates();
    }

}
