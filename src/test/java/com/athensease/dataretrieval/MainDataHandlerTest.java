package com.athensease.dataretrieval;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class MainDataHandlerTest {

    @Test
    void testSplitIntoChunks() {
        List<String> locations = Arrays.asList("A", "B", "C", "D", "E");
        List<List<String>> chunks = MainDataHandler.splitIntoChunks(locations, 2);

        assertEquals(3, chunks.size());
        assertEquals(Arrays.asList("A", "B"), chunks.get(0));
        assertEquals(Arrays.asList("C", "D"), chunks.get(1));
        assertEquals(Arrays.asList("E"), chunks.get(2));
    }
}
