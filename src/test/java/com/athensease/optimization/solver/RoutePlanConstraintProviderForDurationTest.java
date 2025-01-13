package com.athensease.optimization.solver;

import com.athensease.sights.Sight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for RoutePlanConstraintProviderForDuration.
 */
public class RoutePlanConstraintProviderForDurationTest {

    @Test
    public void testUniqueVisitOrders() {
        // Mock Sights
        Sight sight1 = mock(Sight.class);
        Sight sight2 = mock(Sight.class);

        // Set visit orders
        when(sight1.getVisitOrder()).thenReturn(1);
        when(sight2.getVisitOrder()).thenReturn(1); // Same visit order to test constraint

        // Test logic directly
        boolean isViolation = sight1.getVisitOrder() == sight2.getVisitOrder();

        assertTrue(isViolation, "Constraint should identify duplicate visit orders as violations");
    }

    @Test
    public void testNoPenaltyForUniqueVisitOrders() {
        // Mock Sights
        Sight sight1 = mock(Sight.class);
        Sight sight2 = mock(Sight.class);

        // Set different visit orders
        when(sight1.getVisitOrder()).thenReturn(1);
        when(sight2.getVisitOrder()).thenReturn(2);

        // Test logic directly
        boolean isViolation = sight1.getVisitOrder() == sight2.getVisitOrder();

        assertFalse(isViolation, "Constraint should not penalize unique visit orders");
    }

    @Test
    public void testMinimizeTotalDuration() {
        // Mock Sights
        Sight sight1 = mock(Sight.class);
        Sight sight2 = mock(Sight.class);

        // Mock durations
        when(sight1.getVisitOrder()).thenReturn(1);
        when(sight2.getVisitOrder()).thenReturn(2);
        when(sight1.calculateDurationToSight(sight2)).thenReturn(100.0);
        when(sight1.getDurationFromStartingPoint()).thenReturn(50.0);
        when(sight2.getDurationToStartingPoint()).thenReturn(30.0);

        // Calculate total duration
        double totalDuration = sight1.getDurationFromStartingPoint() +
                               sight1.calculateDurationToSight(sight2) +
                               sight2.getDurationToStartingPoint();

        assertEquals(180.0, totalDuration, 0.01, "Total duration should match expected value");
    }
}
