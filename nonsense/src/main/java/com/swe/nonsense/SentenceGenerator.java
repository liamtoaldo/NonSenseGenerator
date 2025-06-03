package com.swe.nonsense;

import java.util.ArrayList;

public class SentenceGenerator {
    private WordsDictionary dictionary;

    // Costruttore
    public SentenceGenerator(WordsDictionary dictionary) {
        if (dictionary == null) {
            throw new IllegalArgumentException("Dictionary cannot be null");
        }
        this.dictionary = dictionary;
    }

    Sentence generateSentence(Template template, Tense tense) {
        if (template == null) {
            throw new IllegalArgumentException("Template cannot be null");
        }

        Sentence sentence = new Sentence(template.getTemplate());
        for (Word word : sentence.getText()) {
            String wordText = word.getText();
            if (wordText.startsWith("[") && wordText.endsWith("]")) {
                String placeholder = wordText.substring(1, wordText.length() - 1);
                String replacement = "";
                switch (placeholder) {
                    case "noun":
                        Noun noun = dictionary.getRandomNoun();
                        if (noun == null) {
                            throw new IllegalStateException("No nouns available in the dictionary");
                        }
                        replacement = noun.getText();
                        break;
                    case "adjective":
                        Adjective adjective = dictionary.getRandomAdjective();
                        if (adjective == null) {
                            throw new IllegalStateException("No adjectives available in the dictionary");
                        }
                        replacement = adjective.getText();
                        break;
                    case "verb":
                        Verb verb;
                        if (tense != null)
                            verb = dictionary.getRandomVerb(tense);
                        else
                            verb = dictionary.getRandomVerb();
                        if (verb == null) {
                            throw new IllegalStateException("No verbs available in the dictionary");
                        }
                        replacement = verb.getText();
                        break;
                    default:
                        break;
                }
                word.setText(replacement);
            }
        }
        ArrayList<Word> newSentence = new ArrayList<>();
        for (String part : sentence.toString().split(" ")) {
            newSentence.add(new Word(part));
        }
        return new Sentence(newSentence);
    }

    Sentence generateRandomSentence(Tense tense) {
        if (dictionary.getTemplates().isEmpty()) {
            throw new IllegalStateException("No templates available in the dictionary");
        }
        Template randomTemplate = dictionary.getRandomTemplate();
        return generateSentence(randomTemplate, tense);
    }

}
