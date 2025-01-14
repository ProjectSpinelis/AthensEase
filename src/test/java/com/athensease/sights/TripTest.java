package com.athensease.sights;

import com.athensease.dataretrieval.ApiHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TripTest {

    private Trip trip;
    private ApiHandler mockApiHandler;

    @BeforeEach
    void setUp() {
        // Initialize Trip and mock ApiHandler before each test
        trip = new Trip();
        mockApiHandler = Mockito.mock(ApiHandler.class);
    }

    // Test constructor and initialization
    @Test
    void testTripInitialization() {
        assertNotNull(trip.getChosenSights());
        assertEquals(4, trip.getChosenCategories().length);
        assertEquals(0, trip.getChosenSights().size());
    }

    // Test prepTrip method
    @Test
    void testPrepTrip() {
        // Prepare mock data for API call
        Mockito.when(mockApiHandler.getResponse(Mockito.anyString())).thenReturn("response");
        Mockito.when(mockApiHandler.extractField(Mockito.anyString(), Mockito.eq("distance"))).thenReturn(5.0);
        Mockito.when(mockApiHandler.extractField(Mockito.anyString(), Mockito.eq("duration"))).thenReturn(10.0);

        // Create a trip with one sight
        Sight sight = new Sight("Sight1", "Location1", 10, 20, "category", false);
        sight.setVisitTime(5);
        trip.setAddress("Athens");
        trip.setChosenSights(List.of(sight));

        // Call prepTrip to process distances and durations
        trip.prepTrip();

        // Assertions to check if the distances and durations are properly set
        assertEquals(5.0, sight.getDistanceToStartingPoint());
        assertEquals(10.0, sight.getDurationToStartingPoint());
    }

    // Test callApiHandler for success
    @Test
    void testCallApiHandlerSuccess() {
        // Set up mock ApiHandler
        Mockito.when(mockApiHandler.getResponse(Mockito.anyString())).thenReturn("response");
        Mockito.when(mockApiHandler.extractField(Mockito.anyString(), Mockito.eq("distance"))).thenReturn(5.0);
        Mockito.when(mockApiHandler.extractField(Mockito.anyString(), Mockito.eq("duration"))).thenReturn(10.0);

        List<Double> results = trip.callApiHandler(List.of("Location1"), List.of("Athens"));

        // Assertions to check if the method returns correct data
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(5.0, results.get(0));
        assertEquals(10.0, results.get(1));
    }

    // Test callApiHandler for failure (IOException simulation)
    @Test
    void testCallApiHandlerFailure() {
        // Simulate IOException in ApiHandler
        Mockito.when(mockApiHandler.getResponse(Mockito.anyString())).thenThrow(new RuntimeException("API Error"));

        List<Double> results = trip.callApiHandler(List.of("Location1"), List.of("Athens"));

        // Assertions to check if the method returns default error values (-1.0, -1.0)
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(-1.0, results.get(0));
        assertEquals(-1.0, results.get(1));
    }

    // Test getTotalCost method
    @Test
    void testGetTotalCost() {
        // Create a few sights with known prices
        Sight sight1 = new Sight("Sight1", "Location1", 10, 20, "category", false);
        sight1.setPrice(10);
        Sight sight2 = new Sight("Sight2", "Location2", 15, 25, "category", false);
        sight2.setPrice(15);
        trip.setChosenSights(List.of(sight1, sight2));

        // Test if the total cost is calculated correctly
        double totalCost = trip.getTotalCost();
        assertEquals(25.0, totalCost); // 10 + 15
    }

    // Test getMinVisitTime method
    @Test
    void testGetMinVisitTime() {
        // Create a few sights with known visit times
        Sight sight1 = new Sight("Sight1", "Location1", 10, 20, "category", false);
        sight1.setVisitTime(5);
        Sight sight2 = new Sight("Sight2", "Location2", 15, 25, "category", false);
        sight2.setVisitTime(7);
        trip.setChosenSights(List.of(sight1, sight2));

        // Test if the minimum visit time is calculated correctly
        int minVisitTime = trip.getMinVisitTime();
        assertEquals(12, minVisitTime);  // 5 + 7 minutes
    }

    // Test tripCalculations method
    @Test
    void testTripCalculations() {
        // Set up mock sights with relevant data
        Sight sight1 = new Sight("Sight1", "Location1", 10, 20, "category", false);
        sight1.setDistanceFromStartingPoint(5);
        sight1.setDurationFromStartingPoint(10);
        Sight sight2 = new Sight("Sight2", "Location2", 15, 25, "category", false);
        sight2.setDistanceFromStartingPoint(5);
        sight2.setDurationFromStartingPoint(10);
        trip.setChosenSights(List.of(sight1, sight2));

        // Manually invoke tripCalculations to ensure output is correctly processed
        trip.tripCalculations();

        // Since this method prints output, you can capture stdout or just check the total distance
        assertEquals(10, trip.getTotalDistanceTraveled());  // 5 + 5 (round trip)
        assertEquals(20, trip.getTotalTravelDuration());    // 10 + 10 (round trip)
    }

    // Test setter and getter methods
    @Test
    void testSetterAndGetter() {
        trip.setDuration(120);
        trip.setBudget(300.0);
        trip.setAddress("Athens");

        assertEquals(120, trip.getDuration());
        assertEquals(300.0, trip.getBudget());
        assertEquals("Athens", trip.getAddress());
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
