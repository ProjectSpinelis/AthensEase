package com.athensease.optimization;

import com.athensease.sights.Sight;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the TrailHeadInclusion class.
 */
public class TrailHeadInclusionTest {

    @Test
    public void testFindHotelStopPointsWithSingleDayTrip() {
        // Mock Sight objects
        Sight sight1 = mock(Sight.class);
        Sight sight2 = mock(Sight.class);

        // Mock travel and visit times
        when(sight1.getDurationFromStartingPoint()).thenReturn(30.0);
        when(sight1.getVisitTime()).thenReturn(120);
        when(sight1.getDurationToStartingPoint()).thenReturn(30.0);

        when(sight2.calculateDurationToSight(sight1)).thenReturn(20.0);
        when(sight2.getVisitTime()).thenReturn(180);
        when(sight2.getDurationToStartingPoint()).thenReturn(40.0);

        List<Sight> sights = Arrays.asList(sight1, sight2);

        // Call method
        List<Sight> stopPoints = TrailHeadInclusion.findHotelStopPoints(sights);

        // Verify result
        assertTrue(stopPoints.isEmpty(), "There should be no hotel stop points for a single-day trip.");
    }

    @Test
    public void testFindHotelStopPointsWithMultipleDays() {
        // Mock Sight objects
        Sight sight1 = mock(Sight.class);
        Sight sight2 = mock(Sight.class);
        Sight sight3 = mock(Sight.class);

        when(sight1.getName()).thenReturn("Sight1");
        when(sight2.getName()).thenReturn("Sight2");
        when(sight3.getName()).thenReturn("Sight3");
        // Mock travel and visit times
        when(sight1.getDurationFromStartingPoint()).thenReturn(30.0);
        when(sight1.getVisitTime()).thenReturn(120);
        when(sight1.getDurationToStartingPoint()).thenReturn(30.0);
        when(sight1.calculateDurationToSight(sight2)).thenReturn(20.0);

        when(sight2.calculateDurationToSight(sight1)).thenReturn(20.0);
        when(sight2.getVisitTime()).thenReturn(240);
        when(sight2.getDurationToStartingPoint()).thenReturn(40.0);

        when(sight3.calculateDurationToSight(sight2)).thenReturn(30.0);
        when(sight3.getVisitTime()).thenReturn(300);
        when(sight3.getDurationToStartingPoint()).thenReturn(60.0);

        List<Sight> sights = Arrays.asList(sight1, sight2, sight3);

        // Call method
        List<Sight> stopPoints = TrailHeadInclusion.findHotelStopPoints(sights);

        // Verify result
        assertEquals(1, stopPoints.size(), "There should be one hotel stop point.");
        assertEquals("Sight2", stopPoints.get(0).getName(), "The stop point should be sight2.");
    }

    @Test
    public void testFindHotelStopPointsWithTwoStopPoints() {
        // Mock Sight objects
        Sight sight1 = mock(Sight.class);
        Sight sight2 = mock(Sight.class);
        Sight sight3 = mock(Sight.class);

        when(sight1.getName()).thenReturn("Sight1");
        when(sight2.getName()).thenReturn("Sight2");
        when(sight3.getName()).thenReturn("Sight3");
        // Mock travel and visit times
        when(sight1.getDurationFromStartingPoint()).thenReturn(50.0);
        when(sight1.getVisitTime()).thenReturn(300);
        when(sight1.getDurationToStartingPoint()).thenReturn(50.0);
        when(sight1.calculateDurationToSight(sight2)).thenReturn(20.0);
        
        when(sight2.calculateDurationToSight(sight1)).thenReturn(60.0);
        when(sight2.getVisitTime()).thenReturn(300);
        when(sight2.getDurationToStartingPoint()).thenReturn(60.0);

        when(sight3.calculateDurationToSight(sight2)).thenReturn(70.0);
        when(sight3.getVisitTime()).thenReturn(300);
        when(sight3.getDurationToStartingPoint()).thenReturn(70.0);

        List<Sight> sights = Arrays.asList(sight1, sight2, sight3);

        // Call method
        List<Sight> stopPoints = TrailHeadInclusion.findHotelStopPoints(sights);

        // Verify result
        assertEquals(2, stopPoints.size(), "There should be two hotel stop points.");
        assertEquals("Sight1", stopPoints.get(0).getName(), "The first stop point should be sight1.");
        assertEquals("Sight2", stopPoints.get(1).getName(), "The second stop point should be sight2.");
    }
}
