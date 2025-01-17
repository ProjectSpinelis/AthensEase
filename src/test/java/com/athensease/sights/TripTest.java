package com.athensease.sights;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.athensease.dataretrieval.ApiHandler;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class TripTest {

    private Trip trip;
    private ApiHandler mockApiHandler;

    @BeforeEach
    void setUp() {
        // Initialize Trip and mock ApiHandler before each test
        trip = new Trip();
        mockApiHandler = Mockito.mock(ApiHandler.class);
        trip.setAddress1("Patision 76");
    }

    // Test constructor and initialization
    @Test
    void testTripInitialization() {
        assertNotNull(trip.getChosenSights());
        assertEquals(4, trip.getChosenCategories().length);
        assertEquals(0, trip.getChosenSights().size());
    }

    // Test prepTrip method
    
    // Test getTotalCost method
    @Test
    void testGetTotalCost() {
        // Create a few sights with known prices
        Sight sight1 = new Sight("Sight1", "Location1", 10.0, 20, "30", false);
        Sight sight2 = new Sight("Sight2", "Location2", 15.0, 25, "35", false);
        trip.setChosenSights(List.of(sight1, sight2));

        // Test if the total cost is calculated correctly
        double totalCost = trip.getTotalCost();
        assertEquals(25.0, totalCost);
    }

    // Test getMinVisitTime method
    @Test
    void testGetMinVisitTime() {
        // Create a few sights with known visit times
        Sight sight1 = new Sight("Sight1", "Location1", 10.0, 5, "30", false);
        Sight sight2 = new Sight("Sight2", "Location2", 15.0, 7, "35", true);
        trip.setChosenSights(List.of(sight1, sight2));

        // Test if the minimum visit time is calculated correctly
        int minVisitTime = trip.getMinVisitTime();
        assertEquals(12, minVisitTime);  // 5 + 7 minutes
    }

    // Test tripCalculations method


    // Test setter and getter methods
    @Test
    void testSetterAndGetter() {
        trip.setDuration(120);
        trip.setBudget(300.0);
        trip.setAddress1("Athens");

        assertEquals(120, trip.getDuration());
        assertEquals(300.0, trip.getBudget());
        assertEquals("Athens", trip.getAddress1());
    }

    // Test edge case with empty chosenSights
    @Test
    void testEmptyChosenSights() {
        trip.setChosenSights(List.of());

        // Ensure the total cost and minimum visit time return 0 in case of no sights
        assertEquals(0.0, trip.getTotalCost());
        assertEquals(0, trip.getMinVisitTime());
    }
}
