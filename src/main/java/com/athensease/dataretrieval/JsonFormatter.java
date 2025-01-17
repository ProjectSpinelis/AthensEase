/**
 * The {@code JsonFormatter} class provides utilities to process JSON responses from the Google Maps Distance Matrix API.
 * It formats the API responses, extracts relevant data, and saves the formatted results to a JSON file.
 */
package com.athensease.dataretrieval;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFormatter {

    /**
     * Represents the distance and duration between an origin and a destination.
     */
    static class AttractionDistance {
        String origin;
        String destination;
        String distance;
        String duration;
        String status;

        /**
         * Constructs an {@code AttractionDistance} object.
         *
         * @param origin      The origin address.
         * @param destination The destination address.
         * @param distance    The distance between the origin and destination.
         * @param duration    The travel duration between the origin and destination.
         * @param status      The status of the API response for this pair.
         */
        AttractionDistance(String origin, String destination, String distance, String duration, String status) {
            this.origin = origin;
            this.destination = destination;
            this.distance = distance;
            this.duration = duration;
            this.status = status;
        }
    }

    /**
     * Represents the full structure of the JSON response from the Google Maps Distance Matrix API.
     */
    static class ApiResponse {
        List<String> destination_addresses;  // List of destination addresses.
        List<String> origin_addresses;  // List of origin addresses.
        List<Row> rows;  // List of rows containing distance and duration data.

        static class Row {
            List<Element> elements;  // List of elements containing distance and duration data.

            static class Element {
                Distance distance;  // Distance information.
                Duration duration;  // Duration information.
                String status;  // Status of the API response for this element.

                static class Distance {
                    String text;  // Distance as a human-readable string (e.g., "347 km").
                    int value;  // Distance in meters.
                }

                static class Duration {
                    String text;  // Duration as a human-readable string (e.g., "9 hours 34 minutes").
                    int value;  // Duration in seconds.
                }
            }
        }
    }

    /**
     * Formats the JSON response, processes it to extract distances and durations, 
     * and saves the formatted data to a JSON file.
     *
     * @param jsonResponse The raw JSON response from the Google Maps API.
     */
    public static void formatJson(String jsonResponse) {
        // List to store the extracted results.
        List<AttractionDistance> attractionDistances = new ArrayList<>();
        System.out.println("JSON Response: " + jsonResponse);  // Debug print.

        // Create a Gson object for JSON parsing.
        Gson gson = new Gson();

        // Convert JSON response to ApiResponse object.
        ApiResponse apiResponse = gson.fromJson(jsonResponse, ApiResponse.class);

        // Process the API response to extract data.
        if (apiResponse.rows != null && !apiResponse.rows.isEmpty()) {
            for (int i = 0; i < apiResponse.origin_addresses.size(); i++) {
                String origin = apiResponse.origin_addresses.get(i);
                for (int j = 0; j < apiResponse.destination_addresses.size(); j++) {
                    String destination = apiResponse.destination_addresses.get(j);
                    ApiResponse.Row.Element element = apiResponse.rows.get(i).elements.get(j);

                    String distance = element.distance != null ? element.distance.text : "N/A";
                    String duration = element.duration != null ? element.duration.text : "N/A";
                    String status = element.status != null ? element.status : "N/A";

                    // Store the results in the list.
                    AttractionDistance attractionDistance = new AttractionDistance(origin, destination, distance, duration, status);
                    attractionDistances.add(attractionDistance);
                }
            }

            // Save the extracted data to a file.
            saveToFile(attractionDistances, "data/distances.json");

        } else {
            System.out.println("No distances found.");
        }
    }

    /**
     * Saves the list of {@code AttractionDistance} objects to a file in JSON format.
     *
     * @param attractionDistances The list of attraction distances to save.
     * @param filePath            The file path where the JSON data will be saved.
     */
    public static void saveToFile(List<AttractionDistance> attractionDistances, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();  // Enable pretty printing.
        try (FileWriter writer = new FileWriter(filePath, true)) {
            gson.toJson(attractionDistances, writer);
            writer.write(System.lineSeparator());  // Add a newline for readability.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
