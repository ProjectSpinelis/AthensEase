package com.athensease.sights;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SightsFileHandlerTest {

    private SightsFileHandler sightsFileHandler;

    @BeforeEach
    void setUp() {
        // Assuming the files are placed under src/test/resources/data/
        sightsFileHandler = new SightsFileHandler();
    }

    @Test
    void testGetSights() {
        List<Sight> sights = sightsFileHandler.getSights();
        assertNotNull(sights, "Sights list should not be null.");
        assertTrue(sights.size() > 0, "The sights list should have some elements.");
    }

    @Test
    void testGetLocations() {
        List<String> locations = sightsFileHandler.getLocations();
        assertNotNull(locations, "Locations list should not be null.");
        assertFalse(locations.isEmpty(), "Locations list should not be empty.");
        assertTrue(locations.contains("Athens"), "Locations list should contain 'Athens'.");
    }

    @Test
    void testFilterSightsByCategory() {
        int category = 1; // Assuming category 1 exists in the data
        List<Sight> filteredSights = sightsFileHandler.filterSightsByCategory(category);

        assertNotNull(filteredSights, "Filtered sights should not be null.");
        assertTrue(filteredSights.size() > 0, "There should be at least one sight matching the category.");
        assertTrue(filteredSights.stream().allMatch(s -> Integer.parseInt(s.getCategory()) == category),
                "All sights in the filtered list should match the given category.");
    }

    @Test
    void testFilterSightsByCategoryMustSee() {
        int category = 0; // Category 0 is for "Must See" sights
        List<Sight> filteredSights = sightsFileHandler.filterSightsByCategory(category);

        assertNotNull(filteredSights, "Filtered sights should not be null.");
        assertTrue(filteredSights.size() > 0, "There should be at least one 'must see' sight.");
        assertTrue(filteredSights.stream().allMatch(Sight::isMustSee),
                "All sights in the filtered list should be marked as must-see.");
    }

    @Test
    void testGetDistanceFromJson() {
        String origin = "Sight A";
        String destination = "Sight B";
        
        // Assuming distances.json contains the distance between "Sight A" and "Sight B"
        double distance = sightsFileHandler.getDistanceFromJson(origin, destination);
        
        assertNotEquals(-1, distance, "The distance should be a positive number.");
        assertTrue(distance > 0, "Distance should be greater than 0.");
    }

    @Test
    void testGetDurationFromJson() {
        String origin = "Sight A";
        String destination = "Sight B";
        
        // Assuming distances.json contains the duration between "Sight A" and "Sight B"
        double duration = sightsFileHandler.getDurationFromJson(origin, destination);

        assertNotEquals(-1, duration, "The duration should be a positive number.");
        assertTrue(duration > 0, "Duration should be greater than 0.");
    }

    @Test
    void testGetDistanceFromJsonNoMatch() {
        String origin = "Sight X";  // Non-existent sight
        String destination = "Sight Y";  // Non-existent sight

        double distance = sightsFileHandler.getDistanceFromJson(origin, destination);
        
        // Should return -1 if no match is found
        assertEquals(-1, distance, "The distance should be -1 for non-existent sights.");
    }

    @Test
    void testGetDurationFromJsonNoMatch() {
        String origin = "Sight X";  // Non-existent sight
        String destination = "Sight Y";  // Non-existent sight

        double duration = sightsFileHandler.getDurationFromJson(origin, destination);
        
        // Should return -1 if no match is found
        assertEquals(-1, duration, "The duration should be -1 for non-existent sights.");
    }

    @Test
    void testFileNotFound() {
        // Simulate a missing file scenario for sights.json
        String invalidFilePath = "invalid/path/to/sights.json";
        File file = new File(invalidFilePath);
        assertFalse(file.exists(), "The file should not exist at the given path.");
    }

    @Test
    void testInvalidCategoryInJson() {
        // Assuming there is an invalid category in the sights data
        List<Sight> filteredSights = sightsFileHandler.filterSightsByCategory(-1);  // Invalid category
        assertNotNull(filteredSights, "Filtered sights should not be null.");
        assertTrue(filteredSights.isEmpty(), "The filtered sights list should be empty for an invalid category.");
    }
}
