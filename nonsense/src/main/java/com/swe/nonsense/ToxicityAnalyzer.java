package com.swe.nonsense;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ToxicityAnalyzer {
  static final String GOOGLE_MODERATION_API_URL = "https://language.googleapis.com/v1/documents:moderateText";
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
    if (sentence == null || sentence.toString().isEmpty()) {
      throw new IllegalArgumentException("Sentence cannot be null or empty");
    }

    String response = apiClient.post(GOOGLE_MODERATION_API_URL, null, new JSONObject()
        .put("document", new JSONObject()
            .put("type", "PLAIN_TEXT")
            .put("content", sentence.toString()))
        .put("encodingType", "UTF8"));

    if (response == null || response.isEmpty() || !response.contains("moderationCategories")) {
      throw new RuntimeException("Failed to get a valid response from the API");

    }

    JSONObject responseObject = new JSONObject(response);
    JSONArray moderationCategories = responseObject.getJSONArray("moderationCategories");

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