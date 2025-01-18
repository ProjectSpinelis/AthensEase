/**
 * The {@code ApiHandler} class handles interactions with the Google Maps Distance Matrix API.
 * It generates the API URL, sends requests, and extracts relevant data from the API response.
 */
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

    /**
     * Constructs an {@code ApiHandler} object with specified origins and destinations.
     *
     * @param originsList     A list of origin locations (latitude and longitude or addresses).
     * @param destinationsList A list of destination locations (latitude and longitude or addresses).
     */
    public ApiHandler(List<String> originsList, List<String> destinationsList) {
        this.originsList = originsList;
        this.destinationsList = destinationsList;
    }

    /**
     * Creates a URL for the Google Maps Distance Matrix API using the provided origins and destinations.
     *
     * @return The fully constructed API URL as a {@code String}.
     */
    public String createURL() {
        String origins = String.join("|", originsList);
        String destinations = String.join("|", destinationsList);

        // Encoding the coordinates to be URL-safe
        origins = URLEncoder.encode(origins, StandardCharsets.UTF_8);
        destinations = URLEncoder.encode(destinations, StandardCharsets.UTF_8);

        // Return the API URL
        return "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origins +
            "&destinations=" + destinations +
            "&key=AIzaSyDzZ6MAs-g0-afqLsZrkYMm_DkmHjvjxO4";
    }

    /**
     * Sends a GET request to the specified URL and retrieves the response as a {@code String}.
     *
     * @param url The API URL to send the request to.
     * @return The API response as a {@code String}.
     * @throws IOException If an I/O error occurs while sending the request or reading the response.
     */
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

    /**
     * Extracts a numeric field (e.g., "distance" or "duration") from the JSON response.
     *
     * @param jsonResponse The API response in JSON format as a {@code String}.
     * @param field        The field to extract, either "distance" or "duration".
     * @return The numeric value of the specified field as a {@code double}.
     * @throws RuntimeException If the field cannot be extracted or the response status is not "OK".
     */
    public double extractField(String jsonResponse, String field) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray rows = json.getJSONArray("rows");
            JSONObject elements = rows.getJSONObject(0).getJSONArray("elements").getJSONObject(0);

            // Check the status of the response
            if (!elements.getString("status").equals("OK")) {
                throw new IllegalArgumentException("API response status: " + elements.getString("status"));
            }

            // Extract the requested field
            JSONObject fieldObject = elements.getJSONObject(field);
            String fieldText = fieldObject.getString("text");

            // Remove units and commas, then convert to double
            String numericPart = fieldText.split(" ")[0].replace(",", "");
            return Double.parseDouble(numericPart);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract " + field + " from response", e);
        }
    }
}
