package com.athensease.dataretrieval;

import com.athensease.sights.Sight;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapURLBuilderTest {

    @Test
    void testGenerateApiRequestUrl_withValidSights() {
        // Prepare a valid list of real Athens addresses
        List<Sight> sights = Arrays.asList(
                new Sight("Starting Point", "Panathinaiko Stadio, Athina 116 36, Greece", 0, 0, "", false),
                new Sight("Waypoint", "Plaka, Athina 105 56, Greece", 0, 0, "", false),
                new Sight("Destination", "Acropolis Museum, Dionysiou Areopagitou 15, Athina 117 42, Greece", 0, 0, "", false)
        );

        // Create an instance of MapURLBuilder
        MapURLBuilder mapURLBuilder = new MapURLBuilder(sights);

        // Generate the API request URL
        String url = mapURLBuilder.generateApiRequestUrl();

        // Validate the generated URL
        assertNotNull(url, "URL should not be null");
        assertTrue(url.contains("origin=Panathinaiko%20Stadio%2C%20Athina%20116%2036%2C%20Greece"),
                "Origin should match the first address. URL: " + url);
        assertTrue(url.contains("destination=Acropolis%20Museum%2C%20Dionysiou%20Areopagitou%2015%2C%20Athina%20117%2042%2C%20Greece"),
                "Destination should match the last address. URL: " + url);
        assertTrue(url.contains("waypoints=Plaka%2C%20Athina%20105%2056%2C%20Greece"),
                "Waypoints should match the intermediate address. URL: " + url);
        assertTrue(url.contains("&key="), "API key should be included in the URL. URL: " + url);
    }

    @Test
    void testGenerateApiRequestUrl_withInsufficientSights() {
        // Prepare a list with only one address
        List<Sight> sights = Arrays.asList(
                new Sight("Only Point", "Monastiraki Square, Athina 105 55, Greece", 0, 0, "", false)
        );

        // Create an instance of MapURLBuilder
        MapURLBuilder mapURLBuilder = new MapURLBuilder(sights);

        // Generate the API request URL
        String url = mapURLBuilder.generateApiRequestUrl();

        // Validate that the URL is null
        assertNull(url, "URL should be null for insufficient sights");
    }

    @Test
    void testGenerateApiRequestUrl_withSpecialCharacters() {
        // Prepare a list with addresses containing special characters
        List<Sight> sights = Arrays.asList(
                new Sight("Place A", "Omonoia Square, Athina 104 31, Greece", 0, 0, "", false),
                new Sight("Place B", "Syntagma Square, Athina 105 63, Greece", 0, 0, "", false),
                new Sight("Place C", "Acropolis Hill: Dionysiou Areopagitou, Athina 117 42, Greece", 0, 0, "", false)
        );

        // Create an instance of MapURLBuilder
        MapURLBuilder mapURLBuilder = new MapURLBuilder(sights);

        // Generate the API request URL
        String url = mapURLBuilder.generateApiRequestUrl();

        // Validate the URL encoding of special characters
        assertNotNull(url, "URL should not be null");
        assertTrue(url.contains("origin=Omonoia%20Square%2C%20Athina%20104%2031%2C%20Greece"),
                "Origin should match with properly encoded special characters. URL: " + url);
        assertTrue(url.contains("destination=Acropolis%20Hill%3A%20Dionysiou%20Areopagitou%2C%20Athina%20117%2042%2C%20Greece"),
                "Destination should match with properly encoded special characters. URL: " + url);
        assertTrue(url.contains("waypoints=Syntagma%20Square%2C%20Athina%20105%2063%2C%20Greece"),
                "Waypoints should match with properly encoded special characters. URL: " + url);
    }

    @Test
    void testGenerateApiRequestUrl_withNullList() {
        // Create an instance of MapURLBuilder with null sights
        MapURLBuilder mapURLBuilder = new MapURLBuilder(null);

        // Generate the API request URL
        String url = mapURLBuilder.generateApiRequestUrl();

        // Validate that the URL is null
        assertNull(url, "URL should be null when sights list is null");
    }
}
