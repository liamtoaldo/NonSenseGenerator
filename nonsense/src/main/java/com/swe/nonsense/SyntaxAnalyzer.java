package com.swe.nonsense;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class that analyzes the syntax of a sentence using Google NLP API.
 * This class builds a syntax tree representing the grammatical structure of the sentence.
 */
public class SyntaxAnalyzer {
    /**
     * URL of the Google NLP API used
     */
    static final String GOOGLE_NLP_API_URL = "https://language.googleapis.com/v1/documents:analyzeSyntax";
    /**
     * URL of the Google NLP API used
     */
    APIClient apiClient;

    /**
     * Constructor that initializes the API client
     */
    public SyntaxAnalyzer() {
        this.apiClient = new APIClient();
    }

    /**
     * Method that analyzes the syntax of a sentence and returns its syntax tree
     * 
     * @param sentence the sentence to analyze
     * @return The syntax tree of the sentence
     * @throws IllegalArgumentException If the sentence is null or empty
     * @throws RuntimeException If the API response is invalid or empty
     * @throws IllegalStateException If no root node is found in the response or if the head token index is out of range
     */
    public SyntaxTree analyzeSyntax(Sentence sentence) {
        if (sentence == null || sentence.getText() == null || sentence.getText().isEmpty()) {
            throw new IllegalArgumentException("Sentence cannot be null or empty");
        }

        String response = apiClient.post(GOOGLE_NLP_API_URL, null, new JSONObject()
                .put("document", new JSONObject()
                        .put("type", "PLAIN_TEXT")
                        .put("content", sentence.toString()))
                .put("encodingType", "UTF8"));
        
        if (response == null || response.isEmpty() || !response.contains("tokens")) {
            throw new RuntimeException("Failed to get a valid response from the API");
        }

        JSONObject responseObject = new JSONObject(response);
        JSONArray tokensArray = responseObject.getJSONArray("tokens");

        if(tokensArray == null || tokensArray.length() == 0) {
            return new SyntaxTree(); // Restituisce un albero vuoto se non ci sono token
        }

        ArrayList<SyntaxNode> tempNodes = new ArrayList<>();

        // Crea un SyntaxNode per ogni token e lo aggiunge a una lista temporanea.
        for (int i = 0; i < tokensArray.length(); i++) {
            JSONObject currentToken = tokensArray.getJSONObject(i);
            String wordText = currentToken.getJSONObject("text").getString("content");
            JSONObject dependencyEdge = currentToken.getJSONObject("dependencyEdge");
            JSONObject partOfSpeech = currentToken.getJSONObject("partOfSpeech");
            String depLabel = dependencyEdge.getString("label");

            Word word;
            switch (partOfSpeech.getString("tag")) {
                case "NOUN":
                    word = new Noun(wordText);
                    break;
                case "VERB":
                    // Usa optString per gestire in sicurezza il caso in cui "tense" non sia presente.
                    String tense = partOfSpeech.optString("tense", "UNKNOWN");
                    switch (tense) {
                        case "PAST":
                            word = new Verb(wordText, Tense.PAST);
                            break;
                        case "PRESENT":
                            word = new Verb(wordText, Tense.PRESENT);
                            break;
                        case "FUTURE":
                            word = new Verb(wordText, Tense.FUTURE);
                            break;
                        default:
                            word = new Verb(wordText, Tense.UNKNOWN);
                            break;
                    }
                    break;
                case "ADJ":
                    word = new Adjective(wordText);
                    break;
                default:
                    word = new Word(wordText);
                    break;
            }

            SyntaxNode currentNode = new SyntaxNode(word, depLabel);
            tempNodes.add(currentNode);
        }

        // Costruisce le dipendenze tra i nodi e identifica tutte le radici.
        ArrayList<SyntaxNode> rootNodes = new ArrayList<>();
        for (int i = 0; i < tokensArray.length(); i++) {
            JSONObject dependencyEdge = tokensArray.getJSONObject(i).getJSONObject("dependencyEdge");
            String label = dependencyEdge.getString("label");

            // I nodi con etichetta "ROOT" vengono aggiunti alla lista delle radici.
            if (label.equals("ROOT")) {
                rootNodes.add(tempNodes.get(i));
            } else {
                // Altrimenti, il nodo è un figlio di un altro nodo.
                int headTokenIndex = dependencyEdge.getInt("headTokenIndex");
                if (headTokenIndex >= 0 && headTokenIndex < tempNodes.size()) {
                    SyntaxNode parentNode = tempNodes.get(headTokenIndex);
                    SyntaxNode childNode = tempNodes.get(i);
                    parentNode.addChild(childNode);
                } else {
                    throw new IllegalStateException("Token head Index is out of range: " + headTokenIndex);
                }
            }
        }

        if (rootNodes.isEmpty()) {
            throw new IllegalStateException("No root nodes found in the syntax analysis response.");
        }

        // Crea un SyntaxTree con i nodi radice trovati.
        SyntaxTree syntaxTree = new SyntaxTree(rootNodes);
        // Imposta le radici nell'albero di sintassi.
        syntaxTree.setRoots(rootNodes);

        return syntaxTree;
    }
}
