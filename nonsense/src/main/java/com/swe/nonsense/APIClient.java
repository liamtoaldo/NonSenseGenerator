package com.swe.nonsense;

import java.net.http.HttpClient;
import java.util.Map;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;

public class APIClient {
    private String googleNlpApiKey;
    private final HttpClient httpClient;

    public APIClient() {
        Dotenv dotenv = Dotenv.load();
        this.googleNlpApiKey = dotenv.get("GOOGLE_NLP_API_KEY");
        if (this.googleNlpApiKey == null || this.googleNlpApiKey.isEmpty()) {
            throw new IllegalStateException("GOOGLE_NLP_API_KEY environment variable is not set.");
        }
        this.httpClient = HttpClient.newHttpClient();
    }

    public String get(String url, Map<String, String> headers) {
        try {
            java.net.http.HttpRequest.Builder requestBuilder = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(url))
                    .GET();
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    requestBuilder.header(entry.getKey(), entry.getValue());
                }
            }
            java.net.http.HttpRequest request = requestBuilder.build();
            java.net.http.HttpResponse<String> response = httpClient.send(request,
                    java.net.http.HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String post(String url, Map<String, String> headers, JSONObject body) {
        url = url + "?key=" + googleNlpApiKey; // Append API key to the URL
        try {
            java.net.http.HttpRequest.Builder requestBuilder = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(url))
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(body != null ? body.toString() : ""));
            // Assicura che Content-Type sia impostato se c'è un corpo
            if (body != null) {
                boolean contentTypeSetInHeaders = false;
                if (headers != null) {
                    for (String key : headers.keySet()) {
                        if ("Content-Type".equalsIgnoreCase(key)) {
                            contentTypeSetInHeaders = true;
                            break;
                        }
                    }
                }
                if (!contentTypeSetInHeaders) {
                    requestBuilder.header("Content-Type", "application/json");
                }
            }

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    // Evita di sovrascrivere Content-Type se già impostato manualmente e aggiunto sopra
                    if (!("Content-Type".equalsIgnoreCase(entry.getKey()) && body != null)) {
                        requestBuilder.header(entry.getKey(), entry.getValue());
                    }
                }
            }

            String jsonPayloadString = body != null ? body.toString() : "";
            System.out.println("Sending JSON payload: " + jsonPayloadString);
            // ... poi usa jsonPayloadString in BodyPublishers.ofString(...)
            java.net.http.HttpRequest request = requestBuilder.build();
            java.net.http.HttpResponse<String> response = httpClient.send(request,
                    java.net.http.HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
