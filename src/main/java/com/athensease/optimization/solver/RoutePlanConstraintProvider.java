package com.athensease.optimization.solver;

import com.athensease.sights.Sight;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

/**
 * Provides constraints for optimizing a route plan in an OptaPlanner solution.
 * This class defines the rules used to calculate the optimization score.
 */
public class RoutePlanConstraintProvider implements ConstraintProvider {

    /**
     * Defines all constraints for the route plan optimization.
     *
     * @param constraintFactory Factory for creating constraints.
     * @return An array of constraints for the optimization problem.
     */
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                uniqueVisitOrders(constraintFactory),
                minimizeTotalDistance(constraintFactory),

               
        };
    }
    
    /**
     * Minimizes the total distance traveled in the route.
     *
     * @param constraintFactory Factory for creating constraints.
     * @return A constraint that penalizes longer travel distances.
     */
    private Constraint minimizeTotalDistance(ConstraintFactory constraintFactory) {
    return constraintFactory.forEachUniquePair(Sight.class)

            .filter((sight1, sight2) -> Math.abs(sight1.getVisitOrder() - sight2.getVisitOrder()) == 1)
            .penalize(HardSoftScore.ONE_SOFT, (sight1, sight2) -> {
                // Ensure sight1 is always the first sight in the pair (smallest visitOrder)
                Sight sight = sight1.getVisitOrder() < sight2.getVisitOrder() ? sight1 : sight2;
                Sight nextSight = sight1.getVisitOrder() < sight2.getVisitOrder() ? sight2 : sight1;
        
                double totalDistance = 0;
            
                totalDistance += sight.calculateDistanceToSight(nextSight);

                // If this is the first sight, calculate the distance to the starting point
                if (sight.getVisitOrder() == 1) {
                    totalDistance += sight.getDistanceFromStartingPoint();
                }

                
                if (nextSight.getVisitOrder() == Sight.getMaxVisitOrder()) {
                    totalDistance += nextSight.getDistanceToStartingPoint();
                }
                

                return (int) totalDistance;
            })
            .asConstraint("Minimize total travel distance");
    }
    
    /**
     * Ensures that each sight has a unique visit order in the route.
     *
     * @param constraintFactory Factory for creating constraints.
     * @return A constraint that penalizes duplicate visit orders.
     */
    private Constraint uniqueVisitOrders(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Sight.class)  // Iterate over all unique pairs of sights
                .filter((sight1, sight2) -> sight1.getVisitOrder() == sight2.getVisitOrder())  // Check if visitOrder is the same
                .penalize(HardSoftScore.ONE_HARD, (sight1, sight2) -> 1)  // Apply a hard penalty when visitOrders are the same
                .asConstraint("Unique visitOrder for all sights");
    }
}
