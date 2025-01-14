package com.athensease.dataretrieval;

import java.util.List;

import com.athensease.sights.Sight;

public class MapURLBuilder {

    private List<Sight> optimizedSights;
    private final String API_KEY = "AIzaSyDits1cNQEJkWEK_FSF_crxVCeV-brw3rE"; // Store the API key

    // Constructor accepts the API key and generates mock data for the sights
    public MapURLBuilder(List<Sight> optimizedSights) {
        this.optimizedSights = optimizedSights;
    }

    // Method to generate the API request URL for Google Directions API
    public String generateApiRequestUrl() {
        if (optimizedSights == null || optimizedSights.size() < 2) {
            System.out.println("Error: Insufficient number of sights to create a route.");
            return null;
        }

        // Start building the API request URL
        StringBuilder urlBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");

        // Add the origin (starting point)
        String origin = optimizedSights.get(0).getLocation();
        urlBuilder.append("origin=")
                  .append(encodeURIComponent(origin));

        // Add the destination (last sight)
        String destination = optimizedSights.get(optimizedSights.size() - 1).getLocation();
        urlBuilder.append("&destination=")
                  .append(encodeURIComponent(destination));

        // Add the waypoints (all sights in the middle)
        StringBuilder waypoints = new StringBuilder();
        for (int i = 1; i < optimizedSights.size() - 1; i++) {
            Sight sight = optimizedSights.get(i);
            waypoints.append(encodeURIComponent(sight.getLocation()));
            if (i < optimizedSights.size() - 2) {
                waypoints.append("|");
            }
        }
        if (waypoints.length() > 0) {
            urlBuilder.append("&waypoints=").append(waypoints);
        }

        // Optional: Mode of transportation (driving, walking, etc.)
        urlBuilder.append("&mode=driving");

        // Append the API key to the request URL
        urlBuilder.append("&key=").append(API_KEY);

        // Return the complete API request URL
        System.out.println("Generated API Request URL: " + urlBuilder.toString());
        return urlBuilder.toString();
    }

    // Utility method to encode the URL parameters (e.g., spaces to %20)
    private String encodeURIComponent(String value) {
        return value.replace(" ", "%20")
                    .replace(":", "%3A")
                    .replace("/", "%2F")
                    .replace("?", "%3F")
                    .replace("&", "%26")
                    .replace("=", "%3D")
                    .replace("+", "%2B")
                    .replace(",", "%2C");
    }

    // Main method to test the URL generation and return the URL as a String
    public static void main(String[] args) {
        // Example: Create URLBuilder instance with a mock API key
        MapURLBuilder urlBuilder = new MapURLBuilder(null);

        // Generate and print the API request URL
        String apiUrl = urlBuilder.generateApiRequestUrl();
        if (apiUrl != null) {
            System.out.println("Generated API Request URL: " + apiUrl);
        }
        
    }
    public String getUrl() {
        // Generate and print the API request URL
        String apiUrl = generateApiRequestUrl();
        return apiUrl;
    }
}