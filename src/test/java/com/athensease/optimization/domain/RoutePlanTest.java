package com.athensease.optimization.domain;

import com.athensease.sights.Sight;
import org.junit.jupiter.api.Test;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RoutePlan class.
 */
class RoutePlanTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Sight sight1 = new Sight("Sight1", "Location1", 10.0, 60, "1", false);
        Sight sight2 = new Sight("Sight2", "Location2", 15.0, 45, "2", false);
        List<Sight> sightsList = Arrays.asList(sight1, sight2);
        double budget = 100.0;

        // Act
        RoutePlan routePlan = new RoutePlan(sightsList, budget);

        // Assert
        assertEquals(sightsList, routePlan.getSightsList(), "Sights list should match the one provided in the constructor.");
        assertEquals(budget, routePlan.getBudget(), "Budget should match the one provided in the constructor.");
    }

    @Test
    void testSetters() {
        // Arrange
        RoutePlan routePlan = new RoutePlan();
        Sight sight1 = new Sight("Sight1", "Location1", 10.0, 60, "1", false);
        Sight sight2 = new Sight("Sight2", "Location2", 15.0, 45, "2", false);
        List<Sight> sightsList = Arrays.asList(sight1, sight2);
        double budget = 200.0;
        HardSoftScore score = HardSoftScore.of(10, -5);

        // Act
        routePlan.setSightsList(sightsList);
        routePlan.setBudget(budget);
        routePlan.setScore(score);

        // Assert
        assertEquals(sightsList, routePlan.getSightsList(), "Sights list should match the one set.");
        assertEquals(budget, routePlan.getBudget(), "Budget should match the one set.");
        assertEquals(score, routePlan.getScore(), "Score should match the one set.");
    }

    @Test
    void testGetVisitOrderRange() {
        // Arrange
        Sight sight1 = new Sight("Sight1", "Location1", 10.0, 60, "1", false);
        Sight sight2 = new Sight("Sight2", "Location2", 15.0, 45, "2", false);
        Sight sight3 = new Sight("Sight3", "Location3", 20.0, 30, "3", false);
        List<Sight> sightsList = Arrays.asList(sight1, sight2, sight3);
        RoutePlan routePlan = new RoutePlan(sightsList, 150.0);

        // Act
        List<Integer> visitOrderRange = routePlan.getVisitOrderRange();

        // Assert
        assertEquals(Arrays.asList(1, 2), visitOrderRange, "Visit order range should be a sequential list of integers starting from 1 up to size - 1.");
    }

    @Test
    void testToString() {
        // Arrange
        Sight sight1 = new Sight("Sight1", "Location1", 10.0, 60, "1", false);
        Sight sight2 = new Sight("Sight2", "Location2", 15.0, 45, "2", false);
        List<Sight> sightsList = Arrays.asList(sight1, sight2);
        RoutePlan routePlan = new RoutePlan(sightsList, 100.0);
        routePlan.setScore(HardSoftScore.of(10, -5));

        // Act
        String toStringResult = routePlan.toString();

        // Assert
        assertTrue(toStringResult.contains("Sight1"), "toString should include the first sight.");
        assertTrue(toStringResult.contains("Sight2"), "toString should include the second sight.");
        assertTrue(toStringResult.contains("score=10hard/-5soft"), "toString should include the score.");
    }
}
