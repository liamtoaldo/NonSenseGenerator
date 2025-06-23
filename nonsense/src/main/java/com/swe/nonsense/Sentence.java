package com.swe.nonsense;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that manages Sentences, which are composed of Words
 */
public class Sentence {
    /**
     * ArrayList of Words that composes the Sentence
     */
    private ArrayList<Word> words;
    /**
     * LocalDateTime that represents the date and time when the Sentence was generated
     */
    private LocalDateTime generationDate;

    /*
     * 
     * COSTRUTTORI
     * 
     */

    /**
     * Default constructor that creates an empty Sentence with the current date and time
     */
    public Sentence() {
        this.words = new ArrayList<>();
        this.generationDate = LocalDateTime.now();
    }

    /**
     * Constructor that creates a Sentence with the specified words and the current date and time
     * 
     * @param words The ArrayList of Words that composes the Sentence
     */
    public Sentence(ArrayList<Word> words) {
        this.words = words;
        this.generationDate = LocalDateTime.now();
    }

    /**
     * Constructor that creates a Sentence from a String, parsing it into Words
     * 
     * @param sentence The string to parse into Words
     */
    public Sentence(String sentence) {
        this.words = new ArrayList<>();
        if (sentence != null && !sentence.isEmpty()) {
            Pattern pattern = Pattern.compile("\\[.*?\\]|\\w+|[^\\w\\s]");
            Matcher matcher = pattern.matcher(sentence);
            while (matcher.find()) {
                this.words.add(new Word(matcher.group()));
            }
        }
        this.generationDate = LocalDateTime.now();
    }

    /*
     * 
     * GETTERS E SETTERS
     * 
     */

    /**
     * Method that return the words that compose the sentence
     * 
     * @return An ArrayList of Words that composes the sentence
     */
    public ArrayList<Word> getText() {
        return words;
    }

    /**
     * Method that returns the date and time when the Sentence was generated
     * 
     * @return LocalDateTime representing the generation date and time of the Sentence
     */
    public LocalDateTime getGenerationDate() {
        return generationDate;
    }

    /**
     * Method that sets the date and time when the Sentence was generated
     * 
     * @param generationDate LocalDateTime representing the new generation date and time of the Sentence
     */
    public void setGenerationDate(LocalDateTime generationDate) {
        this.generationDate = generationDate;
    }

    /**
     * Method that return a random Noun from the Sentence
     * 
     * @return A random Noun from the Sentence, or null if there are no Nouns
     */
    public Noun getRandomNoun() {
        ArrayList<Word> wordsTmp = new ArrayList<>();
        for (Word word : words) {
            if (word instanceof Noun) {
                wordsTmp.add(word);
            }
        }
        if (!wordsTmp.isEmpty()) {
            int randomIndex = (int) (Math.random() * wordsTmp.size());
            return (Noun) wordsTmp.get(randomIndex);
        }
        return null;
    }

    /**
     * Method that returns a random Adjective from the Sentence
     * 
     * @return A random Adjective from the Sentence, or null if there are no Adjectives
     */
    public Adjective getRandomAdjective() {
        ArrayList<Word> wordsTmp = new ArrayList<>();
        for (Word word : words) {
            if (word instanceof Adjective) {
                wordsTmp.add(word);
            }
        }
        if (!wordsTmp.isEmpty()) {
            int randomIndex = (int) (Math.random() * wordsTmp.size());
            return (Adjective) wordsTmp.get(randomIndex);
        }
        return null;
    }

    /**
     * Method that returns a random Verb from the Sentence that matches the specified tense
     * @param tense The tense to match
     * @return A random Verb from the Sentence that matches the specified tense, or null if there are no Verbs
     */
    public Verb getRandomVerb(Tense tense) {
        ArrayList<Word> wordsTmp = new ArrayList<>();
        for (Word word : words) {
            if (word instanceof Verb && ((Verb) word).getTense() == tense) {
                wordsTmp.add(word);
            }
        }
        if (!wordsTmp.isEmpty()) {
            int randomIndex = (int) (Math.random() * wordsTmp.size());
            return (Verb) wordsTmp.get(randomIndex);
        }
        return null;
    }

    /*
     * 
     * METODI
     * 
     */

    /**
     * Override of the equals method to compare two Sentence objects based on their words
     * 
     * @param obj The object to compare with
     * @return true if the words are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this.toString() == obj.toString())
            return true;
        if (!(obj instanceof Sentence))
            return false;
        Sentence other = (Sentence) obj;
        return words.equals(other.words);
    }

    /**
     * Override of the toString method to return the Sentence as a String
     * 
     * @return The Sentence as a String, with words separated by spaces and punctuation handled correctly
     */
    @Override
    public String toString() {
        if (words == null || words.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(words.get(0).getText());

        // Aggiungi uno spazio tra le parole, ma non tra la prima parola e il segno di
        // punteggiatura di apertura, altrimenti
        // metterebbe spazi dove non andrebbero messi (es. prima di un punto).
        for (int i = 1; i < words.size(); i++) {
            String previousWordText = words.get(i - 1).getText();
            String currentWordText = words.get(i).getText();

            if (!isOpeningPunctuation(previousWordText) && !isClosingPunctuation(currentWordText)) {
                sb.append(" ");
            }
            sb.append(currentWordText);
        }
        return sb.toString();
    }

    /**
     * Method that checks if the text is an opening punctuation mark
     * e.g. '(', '[', '{', etc.
     *
     * @param text The text to check
     * @return true if it is an opening punctuation mark, false otherwise
     */
    private boolean isOpeningPunctuation(String text) {
        if (text == null || text.length() != 1) {
            return false;
        }
        char c = text.charAt(0);
        return c == '(' || c == '[' || c == '{' || c == '<' || c == '“' || c == '‘';
    }

    /**
     * Method that checks if the text is a closing punctuation mark
     * e.g. '.', ',', '!', ')', etc.
     *
     * @param text The text to check.
     * @return true if it is a closing punctuation mark, false otherwise.
     */
    private boolean isClosingPunctuation(String text) {
        if (text == null || text.length() != 1) {
            return false;
        }
        char c = text.charAt(0);
        return c == '.' || c == ',' || c == '!' || c == '?' || c == ';' || c == ':' ||
                c == ')' || c == ']' || c == '}' || c == '>' || c == '”' || c == '’';
    }

}