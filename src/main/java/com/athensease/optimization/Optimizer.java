package com.athensease.optimization;

import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;


import com.athensease.optimization.domain.RoutePlan;
import com.athensease.sights.Sight;
import com.athensease.sights.Trip;
import com.athensease.optimization.solver.RoutePlanConstraintProvider;
import com.athensease.optimization.solver.RoutePlanConstraintProviderForDuration;

/**
 * The Optimizer class provides methods to optimize a trip using OptaPlanner.
 * It supports objectives of minimizing either total travel distance or total travel duration.
 */
public class Optimizer {

    /**
     * Optimizes a trip based on the specified objective.
     *
     * @param trip The trip to be optimized, containing chosen sights and budget.
     * @param objective The optimization objective: true for minimizing total distance, false for minimizing total duration.
     */
    public static void optimizeTrip(Trip trip) {
        
        SolverConfig solverConfig;
        
        if (trip.getOptmizeFor() == 1) { // If the objective is to minimize the total travel distance
            solverConfig = new SolverConfig()
                    .withSolutionClass(RoutePlan.class)
                    .withEntityClasses(Sight.class)
                    .withConstraintProviderClass(RoutePlanConstraintProvider.class)
                    // The solver runs only for 5 seconds on this small dataset.
                    // It's recommended to run for at least 5 minutes ("5m") otherwise.
                    .withTerminationSpentLimit(Duration.ofSeconds(5));

        } else { // If the objective is to minimize the total travel duration
            solverConfig = new SolverConfig()
                    .withSolutionClass(RoutePlan.class)
                    .withEntityClasses(Sight.class)
                    .withConstraintProviderClass(RoutePlanConstraintProviderForDuration.class)
                    .withTerminationSpentLimit(Duration.ofSeconds(5));
            
        }

        SolverFactory<RoutePlan> solverFactory = SolverFactory.create(solverConfig);

        // Load the problem
        RoutePlan problem = generateData(trip);

        // Solve the problem
        Solver<RoutePlan> solver = solverFactory.buildSolver();
        RoutePlan solution = solver.solve(problem);

        // Optimize Trip Instance
        trip.setOptimizedSights(solution.getSightsList());
        trip.setOptimizationScore(solution.getScore());
    }

    /**
     * Generates data for the trip to create a RoutePlan.
     *
     * @param trip The trip containing chosen sights and budget.
     * @return A RoutePlan object initialized with the trip's data.
     */
    public static RoutePlan generateData(Trip trip) {

        List<Sight> sights = new ArrayList<>();
        sights = trip.getChosenSights();
        double budget = trip.getBudget();

        RoutePlan route = new RoutePlan(sights, budget);
        return route;
    }
}
 