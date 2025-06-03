package com.swe.nonsense;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sentence {
    private ArrayList<Word> words;
    private LocalDateTime generationDate;

    /*
    
     COSTRUTTORI

    */

    public Sentence() {
        this.words = new ArrayList<>();
        this.generationDate = LocalDateTime.now();
    }

    public Sentence(ArrayList<Word> words) {
        this.words = words;
        this.generationDate = LocalDateTime.now();
    }

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

     GETTERS E SETTERS

    */

    public ArrayList<Word> getText() {
        return words;
    }

    public LocalDateTime getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(LocalDateTime generationDate) {
        this.generationDate = generationDate;
    }

    /*

     METODI

    */

    @Override
    public String toString() {
        if (words == null || words.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(words.get(0).getText());

        // Aggiungi uno spazio tra le parole, ma non tra la prima parola e il segno di punteggiatura di apertura, altrimenti
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
     * Verifica se il testo è un segno di punteggiatura di apertura 
     * es. '(', '[', '{'...
     * 
     * @param text Il testo da controllare.
     * @return true se è un segno di punteggiatura di apertura, false altrimenti.
     */
    private boolean isOpeningPunctuation(String text) {
        if (text == null || text.length() != 1) {
            return false;
        }
        char c = text.charAt(0);
        return c == '(' || c == '[' || c == '{' || c == '<' || c == '“' || c == '‘';
    }

    /**
     * Verifica se il testo è un segno di punteggiatura di chiusura
     * es. '.', ',', '!', ')'...
     * 
     * @param text Il testo da controllare.
     * @return true se è un segno di punteggiatura di chiusura, false altrimenti.
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
