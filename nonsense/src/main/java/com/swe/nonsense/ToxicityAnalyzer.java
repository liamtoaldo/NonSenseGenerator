package com.swe.nonsense;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class that analyzes the toxicity of a sentence using Google Moderation API
 */
public class ToxicityAnalyzer {
  /**
   * URL of the Google Moderation API used
   */
  private static final String GOOGLE_MODERATION_API_URL = "https://language.googleapis.com/v1/documents:moderateText";
  
  /**
   * API client used for making requests to the Google Moderation API 
   */
  private APIClient apiClient;

  // Costruttore
  /**
   * Constructor that initializes the API client
   */
  public ToxicityAnalyzer() {
    this.apiClient = new APIClient();
  }

  /**
   * Analyzes the toxicity of a sentence by sending it to the Google Moderation API.
   * The response is parsed to extract the relevant toxicity categories and their confidence scores.
   *
   * @param sentence The sentence to analyze
   * @return The result of the analysis, containing the score of the relevant toxicity category
   * @throws IllegalArgumentException If the sentence is null or empty
   * @throws RuntimeException If the API response is invalid or empty
   */
  public ModerationResult analyzeToxicity(Sentence sentence) {
    if (sentence == null || sentence.toString().isEmpty()) {
      throw new IllegalArgumentException("Sentence cannot be null or empty");
    }

    String response = apiClient.post(GOOGLE_MODERATION_API_URL, null, new JSONObject()
        .put("document", new JSONObject()
            .put("type", "PLAIN_TEXT")
            .put("content", sentence.toString())));

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

}