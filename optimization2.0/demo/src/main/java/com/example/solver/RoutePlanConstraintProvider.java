package com.example.solver;

import com.example.domain.City;
import com.example.domain.Visit;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class RoutePlanConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                eachCityMustBeVisitedOnce(constraintFactory),
                penalizeDistance1(constraintFactory),
                penalizeDistance2(constraintFactory)
               
        };
    }

    private Constraint eachCityMustBeVisitedOnce(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Visit.class)
                .groupBy(Visit::getCity, ConstraintCollectors.count())
                .filter((city, count) -> count != 1)
                .penalize(HardSoftScore.ONE_HARD, (city, count) -> Math.abs(count - 1))
                .asConstraint("Each city must be visited exactly once");
    }
    
    private Constraint penalizeDistance1(ConstraintFactory constraintFactory) {
    return constraintFactory.forEach(Visit.class)
        .join(Visit.class,
            Joiners.equal(visit -> visit.getVisitOrder(), visit -> visit.getVisitOrder() + 1)) // Match each visit to its previous visit
        .penalize(HardSoftScore.ONE_SOFT, (currentVisit, previousVisit) -> (int)currentVisit.calculateDistanceToVisit(previousVisit))
        .asConstraint("Penalize distance from previous visit");
    }

    private Constraint penalizeDistance2(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Visit.class)
            .filter(visit -> visit.getVisitOrder() == 1)
            .penalize(HardSoftScore.ONE_SOFT, visit -> (int)visit.getCity().calculateDistanceToCity(new City("athens - starting position", 37.9755648, 23.7348324)))
            .asConstraint("Penalize first visit");
    }
}
