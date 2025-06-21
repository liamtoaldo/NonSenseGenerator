package com.swe.nonsense;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/nonsense")
public class ApplicationRestController {
    private final ApplicationController app;

    public ApplicationRestController(ApplicationController app) {
        this.app = app;
    }

    @GetMapping("/sentence/syntax")
    public SyntaxTree getSyntaxTree(@RequestParam String sentenceText) {
        return app.getSyntaxTreeFromString(sentenceText);
    }

    @GetMapping("/sentence/generate")
    public String generateSentenceInput(
        @RequestParam String text,
        @RequestParam(required = false) Template template,
        @RequestParam Tense tense) {
        return app.generateNonSenseSentence(text, template, tense).toString();
    }

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

    @GetMapping("/dictionary/templates")
    public ArrayList<Template> getTemplates() {
        return app.getTemplates();
    }

}
