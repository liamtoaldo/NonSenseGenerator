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

        // Analizza la sintassi per ottenere l'albero con le parole tipizzate.
        Sentence sentenceForAnalysis = new Sentence(sentenceText); 
        SyntaxTree syntaxTree = this.syntaxAnalyzer.analyzeSyntax(sentenceForAnalysis);

        ArrayList<SyntaxNode> availableTypedNodes = new ArrayList<>(syntaxTree.getAllNodes());

        Sentence originalOrderSentence = new Sentence(sentenceText);
        ArrayList<Word> originalWordsInOrder = originalOrderSentence.getText();

        ArrayList<Word> orderedTypedWords = new ArrayList<>();

        // Per ogni parola/token della frase originale, trova la corrispondente Word tipizzata
        for (Word originalWord : originalWordsInOrder) {
            String targetText = originalWord.getText();
            SyntaxNode matchedNode = null;
            int matchedNodeIndex = -1;

            for (int i = 0; i < availableTypedNodes.size(); i++) {
                SyntaxNode candidateNode = availableTypedNodes.get(i);
                if (candidateNode.getWord() != null && candidateNode.getWord().getText().equals(targetText)) {
                    matchedNode = candidateNode;
                    matchedNodeIndex = i;
                    break; // Trovato il primo nodo corrispondente al testo
                }
            }

            if (matchedNode != null) {
                orderedTypedWords.add(matchedNode.getWord());
                availableTypedNodes.remove(matchedNodeIndex); 
            } else {
                // Fallback: se non si trova una parola tipizzata, si utilizza la parola originale
                orderedTypedWords.add(originalWord);
            }
        }

        return new Sentence(orderedTypedWords);
    }

    public SyntaxTree getSyntaxTreeFromString(String sentenceText) {
        Sentence sentence = new Sentence(sentenceText);
        return syntaxAnalyzer.analyzeSyntax(sentence);
    }

    public Sentence generateNonSenseSentence(String input, Template template, Tense tense) {
        // TODO: Implement logic to generate a non-sense sentence based on the input,
        // template, and tense.
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
        //TODO: capire se ha senso salvare il dizionario ogni volta che viene modificato
        // Per ora, salviamo il dizionario ogni volta che viene modificato
        // Questo potrebbe essere ottimizzato per salvare solo quando necessario
        // ma per ora lo facciamo per semplicità.
        storageManager.saveDictionary(wordsDictionary);
    }
}
