package com.athensease.optimization;

import com.athensease.optimization.domain.RoutePlan;
import com.athensease.sights.Sight;
import com.athensease.sights.Trip;
import org.junit.jupiter.api.Test;
import com.athensease.optimization.Optimizer;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the Optimizer class.
 */
public class OptimizerTest {

    @Test
    public void testOptimizeTripMinimizeDistance() {
        // Create real Sight objects
        Sight sight1 = new Sight();
        sight1.setName("Sight1");
    
        Sight sight2 = new Sight();
        sight2.setName("Sight2");
    
        // Create a real Trip object
        Trip trip = mock(Trip.class);
        when(trip.getChosenSights()).thenReturn(Arrays.asList(sight1, sight2));
        when(trip.getBudget()).thenReturn(100.0);
    
        // Call optimizeTrip
        try {
            Optimizer.optimizeTrip(trip, true);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    
        // Verify Trip updates
        verify(trip, atLeastOnce()).setOptimizedSights(anyList());
        verify(trip, atLeastOnce()).setOptimizationScore(any());
    }

    @Test
    public void testOptimizeTripMinimizeDuration() {
        // Create real Sight objects
        Sight sight1 = new Sight();
        sight1.setName("Sight1");
    
        Sight sight2 = new Sight();
        sight2.setName("Sight2");
    
        // Create a real Trip object
        Trip trip = mock(Trip.class);
        when(trip.getChosenSights()).thenReturn(Arrays.asList(sight1, sight2));
        when(trip.getBudget()).thenReturn(100.0);
    
        // Call optimizeTrip
        try {
            Optimizer.optimizeTrip(trip, true);
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    
        // Verify Trip updates
        verify(trip, atLeastOnce()).setOptimizedSights(anyList());
        verify(trip, atLeastOnce()).setOptimizationScore(any());
    }

    @Test
    public void testGenerateData() {
        // Mock Trip
        Trip trip = mock(Trip.class);

        // Mock Sights
        Sight sight1 = mock(Sight.class);
        Sight sight2 = mock(Sight.class);
        when(trip.getChosenSights()).thenReturn(Arrays.asList(sight1, sight2));
        when(trip.getBudget()).thenReturn(100.0);

        // Call generateData
        RoutePlan routePlan = Optimizer.generateData(trip);

        // Verify RoutePlan content
        assertNotNull(routePlan, "RoutePlan should not be null");
        assertEquals(2, routePlan.getSightsList().size(), "RoutePlan should contain two sights");
        assertEquals(100.0, routePlan.getBudget(), "RoutePlan budget should match the trip's budget");
    }
}
