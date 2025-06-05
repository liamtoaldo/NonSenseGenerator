package com.swe.nonsense;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ToxicityAnalyzer {
    private APIClient apiClient;

    // Costruttore
    public ToxicityAnalyzer() {
        this.apiClient = new APIClient();
    }

    /**
     * Analizza la tossicità di una frase.
     * 
     * @param sentence La frase da analizzare.
     * @return Il risultato dell'analisi, contenente il punteggio della relativa
     *         categoria di tossicità.
     */
    public ModerationResult analyzeToxicity(Sentence sentence) {
        String mock = """
                                {
                  "moderationCategories": [
                    {
                      "name": "Toxic",
                      "confidence": 0.10
                    },
                    {
                      "name": "Insult",
                      "confidence": 0.12
                    },
                    {
                      "name": "Profanity",
                      "confidence": 0.07
                    },
                    {
                      "name": "Derogatory",
                      "confidence": 0.04
                    },
                    {
                      "name": "Sexual",
                      "confidence": 0.00
                    },
                    {
                      "name": "Death, Harm & Tragedy",
                      "confidence": 0.00
                    },
                    {
                      "name": "Violent",
                      "confidence": 0.00
                    },
                    {
                      "name": "Firearms & Weapons",
                      "confidence": 0.00
                    },
                    {
                      "name": "Public Safety",
                      "confidence": 0.01
                    },
                    {
                      "name": "Health",
                      "confidence": 0.01
                    },
                    {
                      "name": "Religion & Belief",
                      "confidence": 0.00
                    },
                    {
                      "name": "Illicit Drugs",
                      "confidence": 0.01
                    },
                    {
                      "name": "War & Conflict",
                      "confidence": 0.02
                    },
                    {
                      "name": "Politics",
                      "confidence": 0.01
                    },
                    {
                      "name": "Finance",
                      "confidence": 0.00
                    },
                    {
                      "name": "Legal",
                      "confidence": 0.00
                    }
                  ]
                }
                                """;
        JSONObject mockToxicityResponse = new JSONObject(mock);
        JSONArray moderationCategories = mockToxicityResponse.getJSONArray("moderationCategories");
        ModerationResult result = new ModerationResult();
        ArrayList<ModerationCategory> categories = new ArrayList<>();
        for (int i = 0; i < moderationCategories.length(); i++) {
            JSONObject categoryJson = moderationCategories.getJSONObject(i);
            ModerationCategory category = new ModerationCategory(
                    categoryJson.getString("name"),
                    categoryJson.getDouble("confidence"));
            categories.add(category);
        }
        result.setCategories(categories);
        return result;
    }

    /**
     * Verifica se una frase è tossica.
     * 
     * @param sentence La frase da verificare.
     * @return true se la frase è tossica, false altrimenti.
     */
    public boolean isToxic(Sentence sentence) {
        ModerationResult result = analyzeToxicity(sentence);
        for (ModerationCategory category : result.getCategories()) {
            if (category.getConfidence() > 0.5) {
                return true;
            }
        }
        return false;
    }

}
