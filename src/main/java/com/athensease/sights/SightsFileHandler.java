package com.athensease.sights;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SightsFileHandler {
    private List<Sight> sights;

    // Constructor reads from json and initialises sights list
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

    public List<Sight> getSights() {
        return sights;
    }

    // Is used to create distances.json file from sight addresses
    public List<String> getLocations() {
        List<String> locations = new ArrayList<>();
        
        for (Sight i : sights) {
            locations.add(i.getLocation());
        }
        return locations;
    }

    // Is used by interface to show available sights to user
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
                    // Handle cases where the category is not numeric, if needed
                    System.err.println("Invalid category format for sight: " + sight.getName());
                }
            }
        }
        return filteredSights;
    }

    // Is used to calculate distance between Sight objects
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

    // Is used to calculate duration between Sight objects
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
                    // String durationStr = entry.get("duration").split(" ")[0]; // Extract numeric value

                    // Split the duration into parts and calculate the total duration in minutes
                    double totalMinutes = 0;
                    String[] parts = entry.get("duration").split(" ");
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].equalsIgnoreCase("hour") || parts[i].equalsIgnoreCase("hours")) {
                            // Convert hours to minutes
                            double hours = Double.parseDouble(parts[i - 1]);
                            totalMinutes += hours * 60;
                        } else if (parts[i].equalsIgnoreCase("min") || parts[i].equalsIgnoreCase("mins")) {
                            // Add minutes
                            double minutes = Double.parseDouble(parts[i - 1]);
                            totalMinutes += minutes;
                        }
                    }
                    return totalMinutes;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
