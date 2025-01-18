/**
 * The {@code MainDataHandler} class has a main method for initializing the distances.json file anytime there is a change to the program data.
 * It is responsible for:
 * - Loading location data.
 * - Splitting the locations into chunks to avoid API request limits.
 * - Creating and executing API calls to the Google Maps Distance Matrix API.
 * - Formatting and saving the resulting distance data into a JSON file.
 */
package com.athensease.dataretrieval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.athensease.sights.SightsFileHandler;

public class MainDataHandler {
    
    /**
     * The main method that runs the program to process location data and make API calls.
     * 
     * @param args The command-line arguments (unused).
     */
    public static void main(String[] args) {

        // Initialize SightsFileHandler to load locations
        SightsFileHandler sightsHanlder = new SightsFileHandler();
        List<String> locations = sightsHanlder.getLocations();

        // Define the maximum size for a chunk
        int chunkSize = 10;

        // Split the list of locations into chunks of the specified size
        List<List<String>> locationsChunks = splitIntoChunks(locations, chunkSize);

        // Process each combination of origin and destination chunks
        for (int i = 0; i < locationsChunks.size(); i++) {
            List<String> originsChunk = locationsChunks.get(i);

            for (int j = 0; j < locationsChunks.size(); j++) {
                List<String> destinationsChunk = locationsChunks.get(j);
                ApiHandler handler = new ApiHandler(originsChunk, destinationsChunk);

                // Make the API call to get distances
                try {
                    String url = handler.createURL();
                    String distances = handler.getResponse(url);

                    // Format the response and save to a JSON file
                    JsonFormatter.formatJson(distances);

                    System.out.println("API response saved to distances.json");
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Splits the given list into smaller chunks of a specified size.
     * 
     * @param list The list to split.
     * @param chunkSize The maximum size of each chunk.
     * @return A list of lists containing the chunks.
     */
    public static List<List<String>> splitIntoChunks(List<String> list, int chunkSize) {
        List<List<String>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            chunks.add(list.subList(i, Math.min(i + chunkSize, list.size())));
        }
        return chunks;
    }
}
