package com.athensease.dataretrieval;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JsonFormatterTest {

    @Test
    void testFormatJson() {
        String mockJson = """
        {
            "destination_addresses": ["Rome, IT"],
            "origin_addresses": ["Athens, GR"],
            "rows": [{
                "elements": [{
                    "distance": { "text": "1200 km" },
                    "duration": { "text": "14 hours" },
                    "status": "OK"
                }]
            }]
        }
        """;

        // Test that no exceptions occur during parsing
        JsonFormatter.formatJson(mockJson);
        // Further assertions can be made by inspecting the saved file or mocking the save method
    }

    @Test
    void testSaveToFile() {
        List<JsonFormatter.AttractionDistance> mockData = List.of(
            new JsonFormatter.AttractionDistance("Athens", "Rome", "1200 km", "14 hours", "OK")
        );

        assertDoesNotThrow(() -> JsonFormatter.saveToFile(mockData, "test.json"));
    }
}

