package com.swe.nonsense;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class SyntaxAnalyzer {
    static final String GOOGLE_NLP_API_URL = "https://language.googleapis.com/v1/documents:analyzeSyntax";
    APIClient apiClient;

    public SyntaxAnalyzer() {
        this.apiClient = new APIClient();
    }

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

        SyntaxTree syntaxTree = new SyntaxTree();

        SyntaxNode rootNode = null;
        ArrayList<SyntaxNode> tempNodes = new ArrayList<>();

        // Salva prima tutti i token in una lista
        for (int i = 0; i < tokensArray.length(); i++) {
            JSONObject currentToken = tokensArray.getJSONObject(i);
            String wordText = currentToken.getJSONObject("text").getString("content");
            JSONObject dependencyEdge = currentToken.getJSONObject("dependencyEdge");
            JSONObject partOfSpeech = currentToken.getJSONObject("partOfSpeech");
            String depLabel = dependencyEdge.getString("label");

            Word word;
            // Separa i casi di words
            switch (partOfSpeech.getString("tag")) {
                case "NOUN":
                    word = new Noun(wordText);
                    break;
                case "VERB":
                    // Ottieni il tense
                    String tense = partOfSpeech.getString("tense");
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
                            throw new IllegalArgumentException("Unrecognized tense: " + tense);
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

        // Poi Itera sui token per trovare quello radice.
        // Il token radice ha l'etichetta "ROOT" nella sua dependencyEdge
        for (int i = 0; i < tokensArray.length(); i++) {
            JSONObject currentToken = tokensArray.getJSONObject(i);
            JSONObject dependencyEdge = currentToken.getJSONObject("dependencyEdge");
            int headTokenIndex = dependencyEdge.getInt("headTokenIndex");
            String label = dependencyEdge.getString("label");

            SyntaxNode currentNode = tempNodes.get(i);

            // Caso speciale root
            if (label.equals("ROOT")) {
                JSONObject textObject = currentToken.getJSONObject("text");
                String wordText = textObject.getString("content");
                rootNode = currentNode;
            } else {
                // Caso base per tutti gli altri nodi
                if (headTokenIndex >= 0 && headTokenIndex < tempNodes.size()) {
                    SyntaxNode parentNode = tempNodes.get(headTokenIndex);
                    parentNode.addChild(currentNode);
                } else {
                    throw new IllegalStateException("Token head Index is out of range: " + headTokenIndex);
                }
            }
        }

        if (rootNode == null) {
            throw new IllegalStateException("No ROOT node found in the JSON response.");
        }

        syntaxTree.setRoot(rootNode);
        return syntaxTree;
    }
}
