package com.swe.nonsense;

import java.util.Map;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;

public class APIClient {
    private String googleNlpApiKey;

    public APIClient() {
        Dotenv dotenv = Dotenv.load();
        this.googleNlpApiKey = dotenv.get("GOOGLE_NLP_API_KEY");
        if (this.googleNlpApiKey == null || this.googleNlpApiKey.isEmpty()) {
            throw new IllegalStateException("GOOGLE_NLP_API_KEY environment variable is not set.");
        }
    }

    public String get(String url, Map<String, String> headers) {
        try {
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest.Builder requestBuilder = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(url))
                    .GET();
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
            }
            java.net.http.HttpRequest request = requestBuilder.build();
            java.net.http.HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String post(String url, Map<String, String> headers, JSONObject body) {
        try {
            java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest.Builder requestBuilder = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(url))
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(body != null ? body.toString() : ""));
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
            }
            java.net.http.HttpRequest request = requestBuilder.build();
            java.net.http.HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
