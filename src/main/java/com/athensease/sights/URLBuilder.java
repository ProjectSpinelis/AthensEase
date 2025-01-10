package com.athensease.sights;

import java.util.Arrays;
import java.util.List;

public class URLBuilder {

    private List<Sight> optimizedSights;
    private String apiKey;  // Store the API key

    // Constructor accepts the API key and generates mock data for the sights
    public URLBuilder(String apiKey) {
        this.apiKey = "AIzaSyDits1cNQEJkWEK_FSF_crxVCeV-brw3rE";
        
        // Creating mock Sight objects with predefined data

        Sight sight1 =new Sight("Parthenon", "Anafiotika 11, Athina 105 58, Greece", 12.50, 60, "Historical", true);
        Sight sight2 =  new Sight("Acropolis Museum", "Dionysiou Areopagitou 15, Athina 117 42, Greece", 10.00, 90, "Museum", true);
        Sight sight3 =   new Sight("Temple of Olympian Zeus", "Leoforos Vasilissis Olgas, Athina 105 57, Greece", 8.00, 45, "Historical", false);
        Sight sight4 = new Sight("Syntagma Square", "Syntagma Square, Athina 105 63, Greece", 0.00, 30, "Public", true);
        Sight sight5 =  new Sight("Monastiraki Flea Market", "Monastiraki, Athina 105 55, Greece", 5.00, 60, "Market", false);
        // Add the mock sights to the list
        this.optimizedSights = Arrays.asList(sight1, sight2, sight3, sight4, sight5);
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
        urlBuilder.append("&key=").append(apiKey);

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
        URLBuilder urlBuilder = new URLBuilder("AIzaSyDits1cNQEJkWEK_FSF_crxVCeV-brw3rE");

        // Generate and print the API request URL
        String apiUrl = urlBuilder.generateApiRequestUrl();
        if (apiUrl != null) {
            System.out.println("Generated API Request URL: " + apiUrl);
        }
        
    }
    public static String getUrl() {
        // Example: Create URLBuilder instance with a mock API key
        URLBuilder urlBuilder = new URLBuilder("AIzaSyDits1cNQEJkWEK_FSF_crxVCeV-brw3rE");

        // Generate and print the API request URL
        String apiUrl = urlBuilder.generateApiRequestUrl();
        return apiUrl;
    
}
}