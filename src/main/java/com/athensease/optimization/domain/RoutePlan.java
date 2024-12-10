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

@PlanningSolution
public class RoutePlan {
    
    @PlanningEntityCollectionProperty
    private List<Sight> sightsList; // Επισκέψεις (Planning Entities)
    
    @PlanningScore
    private HardSoftScore score;

    public RoutePlan() {
        
    }

    public RoutePlan(List<Sight> sightsList) {
        this.sightsList = sightsList;
    }

    // Getters και Setters

    @ValueRangeProvider
    public List<Integer> getVisitOrderRange() {
        return IntStream.range(1, this.getSightsList().size()).boxed().collect(Collectors.toList());
    }

    public List<Sight> getSightsList() {
        return sightsList;
    }

    public void setSightsList(List<Sight> sightsList) {
        this.sightsList = sightsList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    // Μέθοδος για να εμφανίσεις την πόλη που επισκέπτεται το ταξιδιώτης
    @Override
    public String toString() {
        return "RoutePlan{" +
                "sightsList=" + sightsList +
                ", score=" + score +
                '}';
    }
}
