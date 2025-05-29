package com.swe.nonsense;

import java.util.ArrayList;

public class WordsDictionary {
    private static WordsDictionary instance;
    private ArrayList<Noun> nouns;
    private ArrayList<Adjective> adjectives;
    private ArrayList<Verb> verbs;
    private ArrayList<Template> templates;

    //Costruttori
    private WordsDictionary() {
        if (instance != null) {
            throw new IllegalStateException("WordsDictionary is a singleton and cannot be instantiated multiple times");
        }
        instance = this;
        nouns = new ArrayList<>();
        adjectives = new ArrayList<>();
        verbs = new ArrayList<>();
        templates = new ArrayList<>();
    }

    private WordsDictionary(ArrayList<Noun> nouns, ArrayList<Adjective> adjectives, ArrayList<Verb> verbs, ArrayList<Template> templates) {
        if (instance != null) {
            throw new IllegalStateException("WordsDictionary is a singleton and cannot be instantiated multiple times");
        }
        instance = this;
        this.nouns = nouns;
        this.adjectives = adjectives;
        this.verbs = verbs;
        this.templates = templates;
    }

    public static WordsDictionary getInstance() {
        return instance;
    }

    public Noun getRandomNoun() {
        if (nouns.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * nouns.size());
        return nouns.get(randomIndex);
    }
    
    public Adjective getRandomAdjective() {
        if (adjectives.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * adjectives.size());
        return adjectives.get(randomIndex);
    }

    public Verb getRandomVerb() {
        if (verbs.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * verbs.size());
        return verbs.get(randomIndex);
    }

    public Verb getRandomVerb(Tense tense) {
        if (verbs.isEmpty()) {
            return null;
        }

        ArrayList<Verb> correctVerbs = new ArrayList<>();
        for (Verb correctVerb : verbs) {
            if (correctVerb.getTense().equals(tense)) {
                correctVerbs.add(correctVerb);
            }
        }

        if (correctVerbs.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * correctVerbs.size());
        Verb verb = correctVerbs.get(randomIndex);
        return verb;
    }

    public Template getRandomTemplate() {
        if (templates.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * templates.size());
        return templates.get(randomIndex);
    }

    public ArrayList<Template> getTemplates() {
        return templates;
    }

    public void saveTerms(ArrayList<Word> words) {
        for (Word word : words) {
            if (word instanceof Noun noun) {
                nouns.add(noun);
            } else if (word instanceof Adjective adjective) {
                adjectives.add(adjective);
            } else if (word instanceof Verb verb) {
                verbs.add(verb);
            }
        }
    }
}   