package com.athensease.dataretrieval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.athensease.sights.SightsFileHandler;

/**
 * The {@code Main} class is the entry point for the application. It manages the main functionality of the program,
 * which includes retrieving a list of locations, splitting them into chunks, and processing each chunk to obtain
 * distance and duration data between all combinations of origin-destination pairs using the Google Maps Distance Matrix API.
 * The data is then formatted and saved to a JSON file for further use.
 * 
 * The main steps performed by this class are:
 * <ol>
 *     <li>Retrieving a list of locations from the {@link SightsFileHandler} class.</li>
 *     <li>Splitting the list of locations into smaller chunks for more manageable processing.</li>
 *     <li>Making API calls to the {@link ApiHandler} class to get distance and duration information between each origin-destination pair.</li>
 *     <li>Formatting the API response and saving it to a JSON file using the {@link JsonFormatter} class.</li>
 * </ol>
 */
public class Main {

    /**
     * The entry point of the application. This method coordinates the overall process of obtaining location data,
     * splitting the locations into chunks, making API calls for distance and duration data, and saving the formatted data.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {

        // Instantiate SightsFileHandler to retrieve locations
        SightsFileHandler sightsHanlder = new SightsFileHandler();
        List<String> locations = sightsHanlder.getLocations();

        // Define the maximum size for a chunk
        int chunkSize = 10;

        // Split the list into smaller chunks to prevent overloading the API
        List<List<String>> locationsChunks = splitIntoChunks(locations, chunkSize);

        // Iterate over each combination of origin and destination chunks
        for (int i = 0; i < locationsChunks.size(); i++) {
            List<String> originsChunk = locationsChunks.get(i);

            for (int j = 0; j < locationsChunks.size(); j++) {
                List<String> destinationsChunk = locationsChunks.get(j);
                
                // Initialize the ApiHandler to manage the API calls
                ApiHandler handler = new ApiHandler(originsChunk, destinationsChunk);
                
                // Attempt to fetch and process data from the API
                try {
                    // Generate the URL for the API request
                    String url = handler.createURL();
                    
                    // Get the API response containing distance and duration data
                    String distances = handler.getResponse(url);

                    // Format and save the response data into a JSON file
                    JsonFormatter.formatJson(distances);

                    System.out.println("API response saved to distances.json");
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Splits a list into smaller chunks of a specified size.
     * 
     * @param list the list to be split into chunks
     * @param chunkSize the maximum size of each chunk
     * @return a list of chunks, each containing a sublist of the original list
     */
    public static List<List<String>> splitIntoChunks(List<String> list, int chunkSize) {
        List<List<String>> chunks = new ArrayList<>();
        
        // Iterate through the original list, creating sublists of the specified chunk size
        for (int i = 0; i < list.size(); i += chunkSize) {
            chunks.add(list.subList(i, Math.min(i + chunkSize, list.size())));
        }
        
        return chunks;
    }
}
