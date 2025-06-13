package com.swe.nonsense;

import java.util.ArrayList;
import java.util.Random;

public class SentenceGenerator {
    private WordsDictionary dictionary;

    // Costruttore
    public SentenceGenerator(WordsDictionary dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("Dictionary cannot be null");
        }
        this.dictionary = dictionary;
    }

    Sentence generateSentence(Sentence input, Template template, Tense tense) {
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
                int choice = (input != null && !input.getText().isEmpty() && random.nextInt(2) == 0) ? 2 : 1;

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

    Sentence generateRandomSentence(Sentence sentence, Tense tense) {
        if (dictionary.getTemplates().isEmpty()) {
            throw new IllegalStateException("No templates available in the dictionary");
        }
        Template randomTemplate = dictionary.getRandomTemplate();
        return generateSentence(sentence, randomTemplate, tense);
    }

}
