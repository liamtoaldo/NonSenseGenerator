package com.swe.nonsense;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import org.json.JSONObject;

public class APIClientTest {
    private APIClient client;

    @BeforeEach
    public void setUp() {
        this.client = new APIClient();
    }

    @Test
    public void get() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        Map<String, String> headers = Map.of();
        String response = client.get(url, headers);
        //System.out.println("GET response: " + response);
        assert response != null : "GET request should return a response";
        assert !response.isEmpty() : "GET response should not be empty";
    }

    @Test
    public void post() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        Map<String, String> headers = Map.of("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        String response = client.post(url, headers, body);
        //System.out.println("POST response: " + response);
        assert response != null : "POST request should return a response";
        assert !response.isEmpty() : "POST response should not be empty";
    }
}
