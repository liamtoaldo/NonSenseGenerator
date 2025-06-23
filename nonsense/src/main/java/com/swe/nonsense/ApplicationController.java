package com.swe.nonsense;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
/**
 * Class that manages the logic of the application, using various components 
 * such as the storage manager, sentence generator, syntax analyzer, and toxicity analyzer
 */
public class ApplicationController {
    /**
     * StorageManager is responsible for loading and saving the dictionary and sentence history
     */
    private StorageManager storageManager;
    /**
     * SentenceGenerator is responsible for generating sentences based on input and templates
     */
    private SentenceGenerator sentenceGenerator;
    /**
     * SyntaxAnalyzer is responsible for analyzing the syntax of sentences and generating syntax trees
     */
    private SyntaxAnalyzer syntaxAnalyzer;
    /**
     * ToxicityAnalyzer is responsible for analyzing the toxicity of sentences
     */
    private ToxicityAnalyzer toxicityAnalyzer;

    /**
     * Constructor that initializes the ApplicationController with the necessary components 
     * (storage manager, sentence generator, syntax analyzer, and toxicity analyzer)
     */
    public ApplicationController() {
        this.storageManager = new StorageManager();
        storageManager.loadDictionary(); // Carica il dizionario all'avvio dell'applicazione
        this.sentenceGenerator = new SentenceGenerator(WordsDictionary.getInstance());
        this.syntaxAnalyzer = new SyntaxAnalyzer();
        this.toxicityAnalyzer = new ToxicityAnalyzer();
    }

    public ApplicationController(String nounsFilePath, String adjectivesFilePath, String verbsFilePath, String templatesFilePath, String sentencesFilePath) {
        this.storageManager = new StorageManager(nounsFilePath, adjectivesFilePath, verbsFilePath, templatesFilePath, sentencesFilePath);
        storageManager.loadDictionary(); // Carica il dizionario all'avvio dell'applicazione
        this.sentenceGenerator = new SentenceGenerator(WordsDictionary.getInstance());
        this.syntaxAnalyzer = new SyntaxAnalyzer();
        this.toxicityAnalyzer = new ToxicityAnalyzer();
    }

    /**
     * Method that converts a String object into a Sentence object
     * 
     * @param sentenceText The text of the sentence to be converted into a Sentence object
     * @return A Sentence object containing the words of the sentence with their types
     * @throws IllegalArgumentException If the input string is null or empty    
     */
    public Sentence convertStringToSentence(String sentenceText) {
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

        // Per ogni parola/token della frase originale, trova la corrispondente Word
        // tipizzata
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
                // Fallback: se non si trova una parola tipizzata, si utilizza la parola
                // originale
                orderedTypedWords.add(originalWord);
            }
        }

        return new Sentence(orderedTypedWords);
    }

    /**
     * Method that generates a SyntaxTree from a given sentence text
     * 
     * @param sentenceText String containing the text of the sentence to be analyzed
     * @return SyntaxTree object representing the syntax structure of the sentence
     */
    public SyntaxTree getSyntaxTreeFromString(String sentenceText) {
        Sentence sentence = new Sentence(sentenceText);
        return syntaxAnalyzer.analyzeSyntax(sentence);
    }

    /**
     * Method that generates a nonsense sentence based on the given input and saves the terms from the input and the generated sentence in the dictionary.
     *  
     * @param input The input sentence text to be used as a base for generating the nonsense sentence
     * @param template The template to be used for generating the nonsense sentence (it can be empty)
     * @param tense The tense of the verbs that will be in the nonsense sentence
     * @return A Sentence object containing the generated nonsense sentence
     * @throws IllegalArgumentException If the input is null or empty, or if the tense is null
     * @throws IllegalStateException If the generated sentence is null or empty
     */
    public Sentence generateNonSenseSentence(String input, Template template, Tense tense) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        if (tense == null) {
            throw new IllegalArgumentException("Tense cannot be null");
        }
        Sentence inputSentence = convertStringToSentence(input);
        Sentence generatedSentence = null;
        if (template == null || template.getTemplate() == "") {
            generatedSentence = sentenceGenerator.generateRandomSentence(inputSentence, tense);

        } else
            generatedSentence = sentenceGenerator.generateSentence(inputSentence, template, tense);
        if (generatedSentence == null || generatedSentence.getText() == null || generatedSentence.getText().isEmpty()) {
            throw new IllegalStateException("Generated sentence cannot be null or empty");
        }
        saveGeneratedSentence(generatedSentence);
        addTermsToDictionaryFromInput(inputSentence);
        return generatedSentence;
    }

    /**
     * Method that analyzes the toxicity of a given sentence
     * 
     * @param sentence The sentence to be analyzed for toxicity
     * @return A ModerationResult object containing the result of the toxicity analysis
     * @throws IllegalArgumentException If the sentence is null or empty
     */
    public ModerationResult getSentenceToxicity(Sentence sentence) {
        if (sentence == null || sentence.getText() == null || sentence.getText().isEmpty()) {
            throw new IllegalArgumentException("Sentence cannot be null or empty");
        }
        return toxicityAnalyzer.analyzeToxicity(sentence);
    }

    /**
     * Method that saves a generated sentence in the history and then saves the history in the storage
     * 
     * @param sentence The sentence to be saved in the history
     * @throws IllegalArgumentException If the sentence is null or empty
     */
    public void saveGeneratedSentence(Sentence sentence) {
        if (sentence == null || sentence.getText() == null || sentence.getText().isEmpty()) {
            throw new IllegalArgumentException("Sentence cannot be null or empty");
        }

        SentenceHistory sentenceHistory = SentenceHistory.getInstance();
        sentenceHistory.save(sentence);
        storageManager.saveHistory(sentenceHistory);
    }

    /**
     * Method that returns the last n sentences from the history.
     * Since it uses getLastSentences from SentenceHistory, the sentences will be returned in reverse order.
     * 
     * @param n The number of last sentences to retrieve from the history
     * @return An ArrayList of Sentence objects containing the last n sentences from the history
     * @throws IllegalArgumentException If n is less than or equal to 0
     */
    public ArrayList<Sentence> getLastSentences(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number of sentences must be greater than zero");
        }

        SentenceHistory sentenceHistory = SentenceHistory.getInstance();
        return sentenceHistory.getLastSentences(n);
    }

    /**
     * Method that adds terms from a given input sentence to the dictionary and then saves the dictionary in the storage
     * 
     * @param input The sentence from which terms will be extracted and added to the dictionary
     * @throws IllegalArgumentException If the input sentence is null or empty
     */
    public void addTermsToDictionaryFromInput(Sentence input) {
        if (input == null || input.getText() == null || input.getText().isEmpty()) {
            throw new IllegalArgumentException("Input sentence cannot be null or empty");
        }

        WordsDictionary wordsDictionary = WordsDictionary.getInstance();
        wordsDictionary.saveTerms(input.getText());
        storageManager.saveDictionary(wordsDictionary);
    }

    /**
     * Method that returns all templates from the dictionary
     * 
     * @return An ArrayList of Template objects containing all templates inside the dictionary
     */
    public ArrayList<Template> getTemplates() {
        WordsDictionary wordsDictionary = WordsDictionary.getInstance();
        return wordsDictionary.getTemplates();
    }
}
