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

public class Optimizer {

    public static void optimizeTrip(Trip trip) {
        
        SolverConfig solverConfig = new SolverConfig()
                .withSolutionClass(RoutePlan.class)
                .withEntityClasses(Sight.class)
                .withConstraintProviderClass(RoutePlanConstraintProvider.class)
                // The solver runs only for 5 seconds on this small dataset.
                // It's recommended to run for at least 5 minutes ("5m") otherwise.
                .withTerminationSpentLimit(Duration.ofSeconds(5));

        SolverFactory<RoutePlan> solverFactory = SolverFactory.create(solverConfig); 


        // Load the problem
        RoutePlan problem = generateDemoData(trip);

        // Solve the problem
        Solver<RoutePlan> solver = solverFactory.buildSolver();
        RoutePlan solution = solver.solve(problem);

        // Optimize Trip Instance
        trip.setOptimizedSights(solution.getSightsList());
        trip.setOptimizationScore(solution.getScore());
    }

    public static RoutePlan generateDemoData(Trip trip) {

        List<Sight> sights = new ArrayList<>();
        sights = trip.getChosenSights();

        RoutePlan route = new RoutePlan(sights);
        return route;
    }
}
 