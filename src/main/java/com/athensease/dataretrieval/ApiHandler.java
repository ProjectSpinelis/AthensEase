package com.athensease.dataretrieval;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ApiHandler {
    
    private List<String> originsList;
    private List<String> destinationsList;


    public ApiHandler(List<String> originsList, List<String> destinationsList) {
        this.originsList = originsList;
        this.destinationsList = destinationsList;
    }

    public String createURL() {
        // Create the origin and destination strings
        String origins = String.join("|", originsList);
        String destinations = String.join("|", destinationsList);

        // Encoding the coordinates to be URL-safe
        origins = URLEncoder.encode(origins, StandardCharsets.UTF_8);
        destinations = URLEncoder.encode(destinations, StandardCharsets.UTF_8);

        // Create the URL for the Google Maps Distance Matrix API
        return "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origins +
            "&destinations=" + destinations +
            "&key=AIzaSyDzZ6MAs-g0-afqLsZrkYMm_DkmHjvjxO4";
    }
    
    public String getResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        }
    }

    public double extractField(String jsonResponse, String field) {
        try {
            // Parse the JSON response
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray rows = json.getJSONArray("rows");
            JSONObject elements = rows.getJSONObject(0).getJSONArray("elements").getJSONObject(0);
    
            // Check the status of the response
            if (!elements.getString("status").equals("OK")) {
                throw new IllegalArgumentException("API response status: " + elements.getString("status"));
            }
    
            // Extract the requested field (either "distance" or "duration")
            JSONObject fieldObject = elements.getJSONObject(field);
            String fieldText = fieldObject.getString("text");
    
            // Remove units (e.g., "km" or "m") and commas, then convert to double
            String numericPart = fieldText.split(" ")[0].replace(",", "");
            return Double.parseDouble(numericPart);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract " + field + " from response", e);
        }
    }
    
}
