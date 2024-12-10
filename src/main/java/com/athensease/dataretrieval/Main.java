package com.athensease.dataretrieval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.athensease.sights.SightsFileHandler;

public class Main {
    public static void main(String[] args) {

        SightsFileHandler sightsHanlder = new SightsFileHandler();
        List<String> locations = sightsHanlder.getLocations();

        // Define the maximum size for a chunk
        int chunkSize = 10;

        // Split the list into chunks
        List<List<String>> locationsChunks = splitIntoChunks(locations, chunkSize);

        // Process each combination of origin and destination chunks
        for (int i = 0; i < locationsChunks.size(); i++) {
            List<String> originsChunk = locationsChunks.get(i);

            for (int j = 0; j < locationsChunks.size(); j++) {
                List<String> destinationsChunk = locationsChunks.get(j);
                ApiHandler handler = new ApiHandler(originsChunk, destinationsChunk);
                // Make the API call
                try {
                    String url = handler.createURL();
                    String distances = handler.getResponse(url);

                    // Format and Save Json File
                    JsonFormatter.formatJson(distances);

                    System.out.println("API response saved to distances.json");
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
  

    public static List<List<String>> splitIntoChunks(List<String> list, int chunkSize) {
        List<List<String>> chunks = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            chunks.add(list.subList(i, Math.min(i + chunkSize, list.size())));
        }
        return chunks;
    }
}
