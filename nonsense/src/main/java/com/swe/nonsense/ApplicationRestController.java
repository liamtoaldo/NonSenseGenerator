package com.swe.nonsense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/nonsense")
public class ApplicationRestController {
    private final ApplicationController app;

    @Autowired
    public ApplicationRestController(ApplicationController app) {
        this.app = app;
    }

    @GetMapping("/syntax-tree")
    public SyntaxTree getSyntaxTree(@RequestParam String sentenceText) {
        return app.getSyntaxTreeFromString(sentenceText);
    }

    @GetMapping("/generateSentenceInput")
    public Sentence generateSentenceInput(@RequestParam String sentenceText,
                                          @RequestParam Template template,
                                          @RequestParam Tense tense) {
        return app.generateNonSenseSentence(sentenceText, template, tense);
    }

}
