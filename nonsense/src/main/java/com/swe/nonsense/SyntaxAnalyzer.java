package com.swe.nonsense;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class SyntaxAnalyzer {
    APIClient apiClient;

    public SyntaxAnalyzer() {
        this.apiClient = new APIClient();
    }

    public SyntaxTree analyzeSyntax(Sentence sentence) {
        if(sentence == null || sentence.getText() == null || sentence.getText().isEmpty()) {
            throw new IllegalArgumentException("Sentence cannot be null or empty");
        }
        // Per il momento usiamo un mock JSONObject per simulare la risposta dell'API
        String mockJsonResponse = """
                                {
                          "sentences": [
                            {
                              "text": {
                                "content": "Google, headquartered in Mountain View, unveiled the new Android phone at the Consumer Electronic Show.",
                                "beginOffset": -1
                              }
                            },
                          ],
                          "tokens": [
                            {
                              "text": {
                                "content": "Google",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "NOUN",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "SINGULAR",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 7,
                                "label": "NSUBJ"
                              },
                              "lemma": "Google"
                            },
                            {
                              "text": {
                                "content": ",",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "PUNCT",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "NUMBER_UNKNOWN",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER_UNKNOWN",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 0,
                                "label": "P"
                              },
                              "lemma": ","
                            },
                            {
                              "text": {
                                "content": "headquartered",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "VERB",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "NUMBER_UNKNOWN",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER_UNKNOWN",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "PAST",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 0,
                                "label": "VMOD"
                              },
                              "lemma": "headquarter"
                            },
                            {
                              "text": {
                                "content": "in",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "ADP",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "NUMBER_UNKNOWN",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER_UNKNOWN",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 2,
                                "label": "PREP"
                              },
                              "lemma": "in"
                            },
                            {
                              "text": {
                                "content": "Mountain",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "NOUN",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "SINGULAR",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 5,
                                "label": "NN"
                              },
                              "lemma": "Mountain"
                            },
                            {
                              "text": {
                                "content": "View",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "NOUN",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "SINGULAR",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 3,
                                "label": "POBJ"
                              },
                              "lemma": "View"
                            },
                            {
                              "text": {
                                "content": ",",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "PUNCT",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "NUMBER_UNKNOWN",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER_UNKNOWN",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 0,
                                "label": "P"
                              },
                              "lemma": ","
                            },
                            {
                              "text": {
                                "content": "unveiled",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "VERB",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "INDICATIVE",
                                "number": "NUMBER_UNKNOWN",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER_UNKNOWN",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "PAST",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 7,
                                "label": "ROOT"
                              },
                              "lemma": "unveil"
                            },
                            {
                              "text": {
                                "content": "the",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "DET",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "NUMBER_UNKNOWN",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER_UNKNOWN",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 11,
                                "label": "DET"
                              },
                              "lemma": "the"
                            },
                            {
                              "text": {
                                "content": "new",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "ADJ",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "NUMBER_UNKNOWN",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER_UNKNOWN",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 11,
                                "label": "AMOD"
                              },
                              "lemma": "new"
                            },
                            {
                              "text": {
                                "content": "Android",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "NOUN",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "SINGULAR",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 11,
                                "label": "NN"
                              },
                              "lemma": "Android"
                            },
                            {
                              "text": {
                                "content": "phone",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "NOUN",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "SINGULAR",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER_UNKNOWN",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 7,
                                "label": "DOBJ"
                              },
                              "lemma": "phone"
                            },
                            {
                              "text": {
                                "content": "at",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "ADP",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "NUMBER_UNKNOWN",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER_UNKNOWN",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 7,
                                "label": "PREP"
                              },
                              "lemma": "at"
                            },
                            {
                              "text": {
                                "content": "the",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "DET",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "NUMBER_UNKNOWN",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER_UNKNOWN",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 16,
                                "label": "DET"
                              },
                              "lemma": "the"
                            },
                            {
                              "text": {
                                "content": "Consumer",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "NOUN",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "SINGULAR",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 16,
                                "label": "NN"
                              },
                              "lemma": "Consumer"
                            },
                            {
                              "text": {
                                "content": "Electronic",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "NOUN",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "SINGULAR",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 16,
                                "label": "NN"
                              },
                              "lemma": "Electronic"
                            },
                            {
                              "text": {
                                "content": "Show",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "NOUN",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "SINGULAR",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 12,
                                "label": "POBJ"
                              },
                              "lemma": "Show"
                            },
                            {
                              "text": {
                                "content": ".",
                                "beginOffset": -1
                              },
                              "partOfSpeech": {
                                "tag": "PUNCT",
                                "aspect": "ASPECT_UNKNOWN",
                                "case": "CASE_UNKNOWN",
                                "form": "FORM_UNKNOWN",
                                "gender": "GENDER_UNKNOWN",
                                "mood": "MOOD_UNKNOWN",
                                "number": "NUMBER_UNKNOWN",
                                "person": "PERSON_UNKNOWN",
                                "proper": "PROPER_UNKNOWN",
                                "reciprocity": "RECIPROCITY_UNKNOWN",
                                "tense": "TENSE_UNKNOWN",
                                "voice": "VOICE_UNKNOWN"
                              },
                              "dependencyEdge": {
                                "headTokenIndex": 7,
                                "label": "P"
                              },
                              "lemma": "."
                            }
                          ],
                          "language": "en"
                        }
                """;
        JSONObject mockResponse = new JSONObject(mockJsonResponse);
        JSONArray tokensArray = mockResponse.getJSONArray("tokens");

        SyntaxTree syntaxTree = new SyntaxTree();

        SyntaxNode rootNode = null;
        int rootHeadTokenIndex = -1;
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
