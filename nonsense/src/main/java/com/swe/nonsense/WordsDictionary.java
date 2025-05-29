package com.swe.nonsense;

import java.util.ArrayList;

public class WordsDictionary {
    private static WordsDictionary instance;
    private ArrayList<Noun> nouns;
    private ArrayList<Adjective> adjectives;
    private ArrayList<Verb> verbs;
    private ArrayList<Template> templates;

    WordsDictionary() {
        if (instance != null) {
            throw new IllegalStateException("WordsDictionary is a singleton and cannot be instantiated multiple times");
        }
        instance = this;
        nouns = new ArrayList<>();
        adjectives = new ArrayList<>();
        verbs = new ArrayList<>();
        templates = new ArrayList<>();
    }
    
}
