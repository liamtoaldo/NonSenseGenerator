package com.swe.nonsense;

import java.util.ArrayList;
/**
 * Class that manages the dictionary of words and templates. It is a singleton class so it can only be instantiated once.
 * A dictionary is a collection of words and templates that can be used to generate sentences.
 */
public class WordsDictionary {

    /**
     * Singleton instance of WordsDictionary
     */
    private static WordsDictionary instance;
    
    /**
     * ArrayList of Nouns that contains the saved nouns
     */
    private ArrayList<Noun> nouns;
    
    /**
     * ArrayList of Adjectives that contains the saved adjectives
     */
    private ArrayList<Adjective> adjectives;
    
    /**
     * ArrayList of Verbs that contains the saved verbs
     */
    private ArrayList<Verb> verbs;
    
    /**
     * ArrayList of Templates that contains the saved templates
     */
    private ArrayList<Template> templates;

    //Costruttore
    /**
     * Private constructor to enforce singleton pattern
     * @throws IllegalStateException If an instance already exists
     */
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

    //Getters

    /**
     * Method that returns the singleton instance of WordsDictionary
     *
     * @return instance of WordsDictionary
     */
    public static WordsDictionary getInstance() {
        if (instance == null) {
            instance = new WordsDictionary();
        }
        return instance;
    }

    /**
     * Method that returns a random noun from the dictionary
     *
     * @return A random Noun or null if there are no nouns
     */
    public Noun getRandomNoun() {
        if (nouns.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * nouns.size());
        return nouns.get(randomIndex);
    }

    /**
     * Method that returns a random adjective from the dictionary
     *
     * @return A random Adjective or null if there are no adjectives
     */
    public Adjective getRandomAdjective() {
        if (adjectives.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * adjectives.size());
        return adjectives.get(randomIndex);
    }

    /**
     * Method that returns a random verb from the dictionary
     *
     * @return A random Verb or null if there are no verbs
     */
    public Verb getRandomVerb() {
        if (verbs.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * verbs.size());
        return verbs.get(randomIndex);
    }

    /**
     * Method that returns a random verb from the dictionary with a specific tense
     * 
     * @param tense The tense of the verb to be returned
     * @return A random Verb with the specified Tense or null if there are no verbs with that Tense
     */
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

    /**
     * Method that returns a random template from the dictionary
     *
     * @return A random Template or null if there are no templates
     */
    public Template getRandomTemplate() {
        if (templates.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * templates.size());
        return templates.get(randomIndex);
    }
    
    /**
     * Method that returns all saved nouns from the dictionary
     * 
     * @return All saved nouns
     */
    public ArrayList<Noun> getNouns() {
        return nouns;
    }

    /**
     * Method that returns all saved adjectives from the dictionary
     * 
     * @return All saved adjectives
     */
    public ArrayList<Adjective> getAdjectives() {
        return adjectives;
    }

    /**
     * Method that returns all saved verbs from the dictionary
     * 
     * @return All saved verbs
     */
    public ArrayList<Verb> getVerbs() {
        return verbs;
    }

    /**
     * Method that returns all saved templates from the dictionary
     * 
     * @return All saved templates
     */
    public ArrayList<Template> getTemplates() {
        return templates;
    }

    //Metodi per salvare e cancellare i dati

    /**
     * Method that saves a list of words in the dictionary. It checks the type of each word and adds it to the corresponding list (nouns, adjectives, or verbs).
     * It avoids adding duplicates by checking if the word is already present in the respective list
     * @param words The ArryList of words to be saved in the dictionary
     */
    public void saveTerms(ArrayList<Word> words) {
        for (Word word : words) {
            if (word instanceof Noun) {
                if (!nouns.contains(word)) {
                    nouns.add((Noun) word);
                }
            } else if (word instanceof Adjective) {
                if (!adjectives.contains(word)) {
                    adjectives.add((Adjective) word);
                }
            } else if (word instanceof Verb) {
                if (!verbs.contains(word)) {
                    verbs.add((Verb) word);
                }
            }
        }
    }

    /**
     * Method that saves a list of templates in the dictionary
     * 
     * @param templates The ArrayList of templates to be saved in the dictionary
     */
    public void saveTemplates(ArrayList<Template> templates) {
        for (Template template : templates) {
            if (!this.templates.contains(template)) {
                this.templates.add(template);
            }
        }
    }

    /**
     * Method that clears all saved nouns, adjectives, verbs, and templates from the dictionary
     */
    public void clearAllData() {
        nouns.clear();
        adjectives.clear();
        verbs.clear();
        templates.clear();
    }
}