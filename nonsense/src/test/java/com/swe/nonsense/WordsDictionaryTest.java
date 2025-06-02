package com.swe.nonsense;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WordsDictionaryTest {

    private WordsDictionary wordsDictionary;
    private ArrayList<Noun> nouns;
    private ArrayList<Adjective> adjectives;
    private ArrayList<Verb> verbs;
    private ArrayList<Template> templates;
    private Noun noun1;
    private Noun noun2;
    private Adjective adjective1;
    private Adjective adjective2;
    private Verb verb1;
    private Verb verb2;
    private Template template1;
    private Template template2;

    @BeforeEach
    void setUp() {
        /*nouns = new ArrayList<>();
        adjectives = new ArrayList<>();
        verbs = new ArrayList<>();
        templates = new ArrayList<>();*/

        // Creazione di Noun
        noun1 = new Noun("cat");
        noun2 = new Noun("dog");
        //nouns.add(noun1);
        //nouns.add(noun2);

        // Creazione di Adjective
        adjective1 = new Adjective("intelligent");
        adjective2 = new Adjective("tall");
        //adjectives.add(adjective1);
        //adjectives.add(adjective2);

        // Creazione di Verb (diversi Tense per testare il metodo getRandomVerb(Tense tense))
        verb1 = new Verb("jumps", Tense.PRESENT);
        verb2 = new Verb("runs", Tense.PAST);
        //verbs.add(verb1);
        //verbs.add(verb2);

        // Creazione di Template
        templates = new ArrayList<>();
        template1 = new Template("First template");
        template2 = new Template("Second template");
        templates.add(template1);
        templates.add(template2);

        //Inizializzazione di WordsDictionary
        //wordsDictionary = new WordsDictionary(nouns, adjectives, verbs, templates);
        wordsDictionary = WordsDictionary.getInstance();

        ArrayList<Word> words = new ArrayList<>();
        words.add(noun1);
        words.add(noun2);
        words.add(adjective1);
        words.add(adjective2);
        words.add(verb1);
        words.add(verb2);
        wordsDictionary.saveTerms(words);
        wordsDictionary.saveTemplates(templates);
    }

    @Test
    void testConstructorWithSavedWords() {
        assert wordsDictionary != null : "Constructor with saved words should create a non-null WordsDictionary object";
    }

    /*@Test
    void testDefaultConstructor() {
        wordsDictionary.clearAllData();
        wordsDictionary.getInstance(); // This will initialize the singleton instance
        assert wordsDictionary != null : "Default constructor should create a non-null WordsDictionary object";
    }*/

    @Test
    void testGetInstance() {
        WordsDictionary instance = WordsDictionary.getInstance();
        assert instance != null : "getInstance should return a non-null WordsDictionary instance";
    }   

    @Test
    void testGetRandomNoun() {
        Noun randomNoun = wordsDictionary.getRandomNoun();
        assert randomNoun != null : "getRandomNoun should return a non-null Noun from the wordsDictionary";
    }

    @Test
    void testGetRandomAdjective() {
        Adjective randomAdjective = wordsDictionary.getRandomAdjective();
        assert randomAdjective != null : "getRandomAdjective should return a non-null Adjective from the wordsDictionary";
    }

    @Test
    void testGetRandomVerb() {
        Verb randomVerb = wordsDictionary.getRandomVerb();
        assert randomVerb != null : "getRandomVerb should return a non-null Verb from the wordsDictionary";
    }

    @Test
    void testGetRandomVerbWithTense() {
        Verb newRandomVerb = wordsDictionary.getRandomVerb(Tense.PRESENT);
        assert newRandomVerb != null : "getRandomVerb should return a non-null Verb from the wordsDictionary";
        assert newRandomVerb.getTense() == Tense.PRESENT : "getRandomVerb (when given a Tense) should return a Verb with the correct tense";
    }

    @Test
    void testGetRandomTemplate() {
        Template randomTemplate = wordsDictionary.getRandomTemplate();
        assert randomTemplate != null : "getRandomTemplate should return a non-null Template from the wordsDictionary";
    }

    @Test
    void testGetNouns() {
        nouns = wordsDictionary.getNouns();
        assert nouns != null : "getNouns should return a non-null list of Nouns";
    }

    @Test
    void testGetAdjectives() {
        adjectives = wordsDictionary.getAdjectives();
        assert adjectives != null : "getAdjectives should return a non-null list of Adjectives";
    }

    @Test
    void testGetVerbs() {
        verbs = wordsDictionary.getVerbs();
        assert verbs != null : "getVerbs should return a non-null list of Verbs";
    }

    @Test
    void testGetTemplates() {
        templates = wordsDictionary.getTemplates();
        assert templates != null : "getTemplates should return a non-null list of Templates";
    }

    @Test
    void testSaveTerms() {
        wordsDictionary.clearAllData();
        ArrayList<Word> words = new ArrayList<>();
        words.add(noun1);
        words.add(adjective1);
        words.add(verb1);
        wordsDictionary.saveTerms(words);
        assert wordsDictionary.getRandomNoun().equals(noun1) : "saveTerms should save the Noun correctly in the wordsDictionary";
        assert wordsDictionary.getRandomAdjective().equals(adjective1) : "saveTerms should save the Adjective correctly in the wordsDictionary";
        assert wordsDictionary.getRandomVerb().equals(verb1) : "saveTerms should save the Verb correctly in the wordsDictionary";
    }

    @Test
    void testSaveTemplates() {
        wordsDictionary.clearAllData();
        ArrayList<Template> savedTemplates = new ArrayList<>();
        savedTemplates.add(template1);
        wordsDictionary.saveTemplates(savedTemplates);
        assert wordsDictionary.getRandomTemplate().equals(template1) : "saveTemplates should save all Templates correctly in the wordsDictionary";
    }

    @Test
    void testClearAllData() {
        wordsDictionary.clearAllData();
        assert wordsDictionary.getRandomNoun() == null : "clearAllData should clear all Nouns from the wordsDictionary";
        assert wordsDictionary.getRandomAdjective() == null : "clearAllData should clear all Adjectives from the wordsDictionary";
        assert wordsDictionary.getRandomVerb() == null : "clearAllData should clear all Verbs from the wordsDictionary";
        assert wordsDictionary.getRandomTemplate() == null : "clearAllData should clear all Templates from the wordsDictionary";
    }
}