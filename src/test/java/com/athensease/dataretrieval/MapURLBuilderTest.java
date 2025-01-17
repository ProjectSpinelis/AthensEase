package com.athensease.dataretrieval;

import com.athensease.sights.Sight;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapURLBuilderTest {

    @Test
    void testGenerateApiRequestUrl_withValidSights() {
        // Prepare mock data
        List<Sight> sights = Arrays.asList(
                new Sight("Place A", "12.34,56.78", 0, 0, "", false),
                new Sight("Place B", "23.45,67.89", 0, 0, "", false),
                new Sight("Place C", "34.56,78.90", 0, 0, "", false)
        );

        // Create instance of MapURLBuilder
        MapURLBuilder mapURLBuilder = new MapURLBuilder(sights);

        // Generate the API request URL
        String url = mapURLBuilder.generateApiRequestUrl();

        // Validate the URL
        assertNotNull(url, "URL should not be null");
        assertTrue(url.contains("origin=12.34,56.78"), "Origin should match");
        assertTrue(url.contains("destination=34.56,78.90"), "Destination should match");
        assertTrue(url.contains("waypoints=23.45,67.89"), "Waypoints should match");
        assertTrue(url.contains("&key="), "API key should be included");
    }

    @Test
    void testGenerateApiRequestUrl_withInsufficientSights() {
        // Prepare mock data with insufficient sights
        List<Sight> sights = Arrays.asList(
                new Sight("Place A", "12.34,56.78", 0, 0, "", false)
        );

        // Create instance of MapURLBuilder
        MapURLBuilder mapURLBuilder = new MapURLBuilder(sights);

        // Generate the API request URL
        String url = mapURLBuilder.generateApiRequestUrl();

        // Validate that the URL is null
        assertNull(url, "URL should be null for insufficient sights");
    }
}