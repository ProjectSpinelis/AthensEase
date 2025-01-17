package com.athensease.optimization.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import com.athensease.sights.Sight;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a route plan that includes a list of sights to visit and a score for optimization.
 * The class is used within an OptaPlanner optimization context.
 */
@PlanningSolution
public class RoutePlan {
    
    /**
     * List of sights to visit (Planning Entities).
     */
    @PlanningEntityCollectionProperty
    private List<Sight> sightsList; // Επισκέψεις (Planning Entities)
    
    /**
     * Optimization score of the route plan.
     */
    @PlanningScore
    private HardSoftScore score;

    /**
     * Budget available for the route plan.
     */
    private double budget;

    /**
     * Default constructor.
     */
    public RoutePlan() {     
    }

    /**
     * Constructs a RoutePlan with the specified list of sights and budget.
     *
     * @param sightsList List of sights to visit.
     * @param budget Budget available for the route plan.
     */
    public RoutePlan(List<Sight> sightsList, double budget) {
        this.sightsList = sightsList;
        this.budget = budget;
    }

    // Getters και Setters

    /**
     * Provides the range of visit orders for the sights.
     *
     * @return A list of integers representing possible visit orders.
     */
    @ValueRangeProvider
    public List<Integer> getVisitOrderRange() {
        return IntStream.range(1, this.getSightsList().size()).boxed().collect(Collectors.toList());
    }

    /**
     * Retrieves the list of sights to visit.
     *
     * @return List of sights.
     */
    public List<Sight> getSightsList() {
        return sightsList;
    }

    /**
     * Sets the list of sights to visit.
     *
     * @param sightsList List of sights.
     */
    public void setSightsList(List<Sight> sightsList) {
        this.sightsList = sightsList;
    }

    /**
     * Retrieves the budget for the route plan.
     *
     * @return Budget as a double.
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Sets the budget for the route plan.
     *
     * @param budget Budget as a double.
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Retrieves the optimization score for the route plan.
     *
     * @return The optimization score.
     */
    public HardSoftScore getScore() {
        return score;
    }

    /**
     * Sets the optimization score for the route plan.
     *
     * @param score The optimization score.
     */
    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    // Μέθοδος για να εμφανίσεις την πόλη που επισκέπτεται το ταξιδιώτης

    /**
     * Provides a string representation of the RoutePlan object.
     *
     * @return A string representation of the object, including sights list and score.
     */
    @Override
    public String toString() {
        return "RoutePlan{" +
                "sightsList=" + sightsList +
                ", score=" + score +
                '}';
    }
}
