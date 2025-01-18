package com.athensease.sights;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * SightsFileHandler is responsible for reading and handling data related to sights and distances.
 * It parses JSON files containing information about sights and distances, providing methods to filter
 * sights by category, retrieve sight locations, and calculate distances and durations between sights.
 */
public class SightsFileHandler {
    
    private List<Sight> sights;

    /**
     * Constructs a SightsFileHandler instance and initializes the list of sights by reading from the
     * "data/sights.json" file in the classpath.
     * 
     * @throws IOException If an error occurs while reading the JSON files or parsing the data.
     */
    public SightsFileHandler() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Access the JSON file from the classpath (resources/data/sights.json)
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/sights.json");

            if (inputStream == null) {
                throw new IOException("Resource not found: data/sights.json");
            }

            // Read the JSON file into a List of Sight objects
            sights = mapper.readValue(inputStream, new TypeReference<List<Sight>>() {});
        } catch (IOException e) {
            System.err.println("Error reading sights.json: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns the list of Sight objects.
     * 
     * @return A list of Sight objects loaded from the "sights.json" file.
     */
    public List<Sight> getSights() {
        return sights;
    }

    /**
     * Returns a list of locations of all the sights.
     * 
     * @return A list of locations as strings extracted from the list of sights.
     */
    public List<String> getLocations() {
        List<String> locations = new ArrayList<>();
        
        for (Sight i : sights) {
            locations.add(i.getLocation());
        }
        return locations;
    }

    /**
     * Filters the sights by category and returns a list of matching sights.
     * 
     * @param category The category to filter by. If category is 0, only "must-see" sights are returned.
     * @return A list of Sight objects matching the specified category.
     */
    public List<Sight> filterSightsByCategory(int category) {
        List<Sight> filteredSights = new ArrayList<>();
    
        for (Sight sight : sights) {
            // Include must-see sights if category is 0
            if (category == 0 && sight.isMustSee()) {
                filteredSights.add(sight);
            }
            // Include sights with the matching category
            else {
                try {
                    int sightCategory = Integer.parseInt(sight.getCategory());
                    if (sightCategory == category) {
                        filteredSights.add(sight);
                    }
                } catch (NumberFormatException e) {
                    // Handle cases where the category is not numeric
                    System.err.println("Invalid category format for sight: " + sight.getName());
                }
            }
        }
        return filteredSights;
    }

    /**
     * Retrieves the distance between two locations from the "data/distances.json" file.
     * 
     * @param origin The origin location.
     * @param destination The destination location.
     * @return The distance between the two locations, or -1 if not found or if an error occurs.
     */
    public double getDistanceFromJson(String origin, String destination) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/distances.json")) {
            if (inputStream == null) {
                throw new IOException("Resource not found: data/distances.json");
            }

            // Parse the JSON file into a List of Maps
            List<Map<String, String>> distances = mapper.readValue(inputStream, new TypeReference<List<Map<String, String>>>() {});

            // Iterate through the list to find a matching origin and destination
            for (Map<String, String> entry : distances) {
                if (entry.get("origin").equals(origin) && entry.get("destination").equals(destination)) {
                    String distanceStr = entry.get("distance").split(" ")[0]; // Extract numeric value
                    return Double.parseDouble(distanceStr); // Convert to double
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return -1; // Return -1 if no match or parsing error occurs
    }

    /**
     * Retrieves the duration between two locations from the "data/distances.json" file.
     * 
     * @param origin The origin location.
     * @param destination The destination location.
     * @return The duration between the two locations in minutes, or -1 if not found or if an error occurs.
     */
    public double getDurationFromJson(String origin, String destination) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/distances.json")) {
            if (inputStream == null) {
                throw new IOException("Resource not found: data/distances.json");
            }

            // Parse the JSON file into a List of Maps
            List<Map<String, String>> durations = mapper.readValue(inputStream, new TypeReference<List<Map<String, String>>>() {});

            // Iterate through the list to find a matching origin and destination
            for (Map<String, String> entry : durations) {
                if (entry.get("origin").equals(origin) && entry.get("destination").equals(destination)) {
                    String durationStr = entry.get("duration").split(" ")[0]; // Extract numeric value
                    return Double.parseDouble(durationStr); // Convert to double
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no match or parsing error occurs
    }
}
