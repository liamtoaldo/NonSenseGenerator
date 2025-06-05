package com.swe.nonsense;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class ApplicationController {
    private StorageManager storageManager;
    private SentenceGenerator sentenceGenerator;
    private SyntaxAnalyzer syntaxAnalyzer;
    private ToxicityAnalyzer toxicityAnalyzer;

    public ApplicationController() {
        this.storageManager = new StorageManager();
        this.sentenceGenerator = new SentenceGenerator(WordsDictionary.getInstance());
        this.syntaxAnalyzer = new SyntaxAnalyzer();
        this.toxicityAnalyzer = new ToxicityAnalyzer();
    }

    private Sentence convertStringToSentence(String sentenceText) {
        if (sentenceText == null || sentenceText.isEmpty()) {
            throw new IllegalArgumentException("Sentence text cannot be null or empty");
        }

        // Usa SyntaxAnalyzer per ottenere i tipi di parole
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer();
        SyntaxTree syntaxTree = syntaxAnalyzer.analyzeSyntax(new Sentence(sentenceText));

        ArrayList<Word> words = new ArrayList<>();
        for (SyntaxNode node : syntaxTree.getAllNodesInOrder()) {
            words.add(node.getWord());
        }

        return new Sentence(words);
    }

    public SyntaxTree getSyntaxTreeFromString(String sentenceText) {
        Sentence sentence = new Sentence(sentenceText);
        return syntaxAnalyzer.analyzeSyntax(sentence);
    }

    public Sentence generateNonSenseSentence(String input, Template template, Tense tense) {
        // TODO: Implement logic to generate a non-sense sentence based on the input, template, and tense.
        return new Sentence();
    }

    public ModerationResult getSentenceToxicity(Sentence sentence) {
        if (sentence == null || sentence.getText() == null || sentence.getText().isEmpty()) {
            throw new IllegalArgumentException("Sentence cannot be null or empty");
        }
        return toxicityAnalyzer.analyzeToxicity(sentence);
    }

    public void saveGeneratedSentence(Sentence sentence) {
        if (sentence == null || sentence.getText() == null || sentence.getText().isEmpty()) {
            throw new IllegalArgumentException("Sentence cannot be null or empty");
        }

        SentenceHistory sentenceHistory = SentenceHistory.getInstance();
        sentenceHistory.save(sentence);
        storageManager.saveHistory(sentenceHistory);
    }

    public ArrayList<Sentence> getLastSentences(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number of sentences must be greater than zero");
        }

        SentenceHistory sentenceHistory = SentenceHistory.getInstance();
        return sentenceHistory.getLastSentences(n);
    }

    public void addTermsToDictionaryFromInput(Sentence input) {
        if (input == null || input.getText() == null || input.getText().isEmpty()) {
            throw new IllegalArgumentException("Input sentence cannot be null or empty");
        }

        WordsDictionary wordsDictionary = WordsDictionary.getInstance();
        wordsDictionary.saveTerms(input.getText());
    }
}
