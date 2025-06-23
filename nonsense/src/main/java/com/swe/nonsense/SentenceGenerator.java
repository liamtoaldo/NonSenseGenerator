package com.swe.nonsense;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that generates sentences based on a given dictionary of words, an optional template and a given tense
 */
public class SentenceGenerator {
    /**
     * The dictionary containing words used to generate sentences
     */
    private WordsDictionary dictionary;

    // Costruttore
    /**
     * Constructor that initializes the SentenceGenerator with a given dictionary
     * 
     * @param dictionary The dictionary containing words used to generate sentences
     * @throws IllegalArgumentException If the dictionary is null
     */
    public SentenceGenerator(WordsDictionary dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("Dictionary cannot be null");
        }
        this.dictionary = dictionary;
    }

    /**
     * Method that generates a sentence based on a given sentence, template, and tense
     * 
     * @param input The given sentence used as a base for generating the new sentence
     * @param template The template used for generating the sentence
     * @param tense The tense used for verbs in the generated sentence
     * @return A Sentence object generated based on the input sentence, template, and tense
     * @throws IllegalArgumentException If the template is null
     * @throws IllegalStateException If no nouns, adjectives, or verbs are available in the dictionary or input sentence
     */
    public Sentence generateSentence(Sentence input, Template template, Tense tense) {
        if (template == null) {
            throw new IllegalArgumentException("Template cannot be null");
        }

        Sentence templateSentence = new Sentence(template.getTemplate());
        ArrayList<Word> processedWords = new ArrayList<>();

        for (Word currentWordFromTemplate : templateSentence.getText()) {
            String wordText = currentWordFromTemplate.getText();
            if (wordText.startsWith("[") && wordText.endsWith("]")) {
                String placeholderType = wordText.substring(1, wordText.length() - 1).toLowerCase();
                Word actualReplacement = null;
                Random random = new Random();
                // Scelta se usare input o dizionario
                int choice = (input != null && !input.getText().isEmpty() && random.nextInt(10) < 7) ? 2 : 1;

                switch (placeholderType) {
                    case "noun":
                        Noun noun = (choice == 2 && input != null) ? input.getRandomNoun() : dictionary.getRandomNoun();
                        if (noun == null)
                            noun = dictionary.getRandomNoun();
                        if (noun == null) {
                            throw new IllegalStateException("No nouns available in the dictionary or sentence");
                        }
                        actualReplacement = noun;
                        break;
                    case "adjective":
                        Adjective adjective = (choice == 2 && input != null) ? input.getRandomAdjective()
                                : dictionary.getRandomAdjective();
                        if (adjective == null)
                            adjective = dictionary.getRandomAdjective();
                        if (adjective == null) {
                            throw new IllegalStateException("No adjectives available in the dictionary or sentence");
                        }
                        actualReplacement = adjective;
                        break;
                    case "verb":
                        Verb verb = (tense != null) ? dictionary.getRandomVerb(tense) : dictionary.getRandomVerb();
                        if (verb == null) {
                            throw new IllegalStateException("No verbs available in the dictionary");
                        }
                        actualReplacement = verb;
                        break;
                    default:
                        actualReplacement = new Word(wordText);
                        break;
                }
                if (actualReplacement != null) {
                    processedWords.add(actualReplacement);
                }
            } else {
                processedWords.add(currentWordFromTemplate);
            }
        }
        return new Sentence(processedWords);
    }

    /**
     * Method that generates a random sentence based on a given sentence and tense.
     * this method uses a random template from the dictionary to create a new sentence.
     * 
     * @param sentence The given sentence used as a base for generating the new sentence
     * @param tense The tense used for verbs in the generated sentence
     * @return A Sentence object randomly generated based on the input sentence and tense
     * @throws IllegalStateException If no templates are available in the dictionary
     */
    public Sentence generateRandomSentence(Sentence sentence, Tense tense) {
        if (dictionary.getTemplates().isEmpty()) {
            throw new IllegalStateException("No templates available in the dictionary");
        }
        Template randomTemplate = dictionary.getRandomTemplate();
        return generateSentence(sentence, randomTemplate, tense);
    }

}
