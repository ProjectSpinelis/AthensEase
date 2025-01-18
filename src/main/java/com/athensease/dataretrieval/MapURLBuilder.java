package com.athensease.dataretrieval;

import java.util.List;

import com.athensease.sights.Sight;

/**
 * The MapURLBuilder class is responsible for generating a URL for the Google Directions API.
 * The URL is built based on a list of sights (tourist locations), representing a route starting
 * from the first sight and ending at the last sight, with waypoints for all intermediate sights.
 * This URL can be used to request directions between the sights using the Google Maps API.
 */
public class MapURLBuilder {

    // List of optimized sights (tourist locations) to be used for route generation.
    private List<Sight> optimizedSights;

    // The API key for accessing the Google Directions API.
    private final String API_KEY = "AIzaSyDits1cNQEJkWEK_FSF_crxVCeV-brw3rE"; 

    /**
     * Constructor that initializes the MapURLBuilder with a list of optimized sights.
     * 
     * @param optimizedSights The list of sights to be used for generating the route.
     */
    public MapURLBuilder(List<Sight> optimizedSights) {
        this.optimizedSights = optimizedSights;
    }

    /**
     * Generates the API request URL for the Google Directions API using the list of optimized sights.
     * 
     * @return The generated URL as a string, or null if the number of sights is insufficient.
     */
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
        return urlBuilder.toString();
    }

    /**
     * Utility method to encode URL parameters (e.g., replacing spaces with "%20").
     * 
     * @param value The value to be URL-encoded.
     * @return The URL-encoded string.
     */
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

    /**
     * Main method to test the URL generation and print the result.
     * 
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        // Example: Create URLBuilder instance with a mock API key
        MapURLBuilder urlBuilder = new MapURLBuilder(null);

        // Generate and print the API request URL
        String apiUrl = urlBuilder.generateApiRequestUrl();
        if (apiUrl != null) {
            System.out.println("Generated API Request URL: " + apiUrl);
        }
    }

    /**
     * Gets the generated API request URL as a string.
     * 
     * @return The generated API request URL as a string.
     */
    public String getUrl() {
        // Generate and return the API request URL
        String apiUrl = generateApiRequestUrl();
        return apiUrl;
    }
}
