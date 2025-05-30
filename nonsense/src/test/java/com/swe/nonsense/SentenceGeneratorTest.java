package com.swe.nonsense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SentenceGeneratorTest {

    private StorageManager storageManager;
    private WordsDictionary wordsDictionary;
    private SentenceGenerator sentenceGenerator;

    @BeforeEach
    void setUp() {
        storageManager = new StorageManager();
        wordsDictionary = storageManager.loadDictionary();
        wordsDictionary.clearAllData();
        sentenceGenerator = new SentenceGenerator(wordsDictionary);
    }

    @Test
    void testConstructor() {
        assertNotNull(sentenceGenerator, "SentenceGenerator should be instantiated successfully");
    }

    @Test
    void testGenerateSentence_validTemplateAndTense() {

        Template template = new Template("The [adjective] [noun] [verb].");
        Noun noun1 = new Noun("cat");
        Adjective adjective = new Adjective("good");
        Verb verb = new Verb("slept", Tense.PAST);
        ArrayList<Word> words = new ArrayList<>();
        words.add(noun1);
        words.add(adjective);
        words.add(verb);

        wordsDictionary.saveTerms(words);

        Sentence result = sentenceGenerator.generateSentence(template, Tense.PAST);
        assertNotNull(result, "Generated sentence should not be null");
        assertEquals("The good cat slept.", result.toString(),
                "Generated sentence does not match expected output");
    }

    @Test
    void testGenerateSentence_nullTemplate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sentenceGenerator.generateSentence(null, Tense.PRESENT);
        });
        assertEquals("Template cannot be null", exception.getMessage());
    }

    @Test
    void testGenerateSentence_noNouns() {

        Template template = new Template("The [noun] [verb].");
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Verb("runs", Tense.PRESENT));
        wordsDictionary.saveTerms(words);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            sentenceGenerator.generateSentence(template, Tense.PRESENT);
        });
        assertEquals("No nouns available in the dictionary", exception.getMessage());
    }

    @Test
    void testGenerateSentence_noAdjectives() {

        Template template = new Template("The [adjective] [noun].");
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Noun("cat"));
        wordsDictionary.saveTerms(words);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            sentenceGenerator.generateSentence(template, Tense.PRESENT);
        });
        assertEquals("No adjectives available in the dictionary", exception.getMessage());
    }

    @Test
    void testGenerateSentence_noVerbs() {

        Template template = new Template("The [noun] [verb].");
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Noun("cat"));
        wordsDictionary.saveTerms(words);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            sentenceGenerator.generateSentence(template, Tense.PRESENT);
        });
        assertEquals("No verbs available in the dictionary", exception.getMessage());
    }

    @Test
    void testGenerateRandomSentence_validTense() {

        Template template = new Template("A [noun] [verb].");
        Noun noun = new Noun("bird");
        Verb verb = new Verb("flies", Tense.PRESENT);

        ArrayList<Word> words = new ArrayList<>();
        words.add(noun);
        words.add(verb);
        ArrayList<Template> templates = new ArrayList<>();
        templates.add(template);

        wordsDictionary.saveTerms(words);
        wordsDictionary.saveTemplates(templates);

        Sentence result = sentenceGenerator.generateRandomSentence(Tense.PRESENT);
        assertNotNull(result, "Generated sentence should not be null");
        assertEquals("A bird flies.", result.toString(), "Generated random sentence does not match expected output");
    }

    @Test
    void testGenerateRandomSentence_noTemplates() {

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            sentenceGenerator.generateRandomSentence(Tense.PRESENT);
        });
        assertEquals("No templates available in the dictionary", exception.getMessage());
    }
}