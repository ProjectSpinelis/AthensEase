package com.example.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@PlanningSolution
public class RoutePlan {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<City> cityList;  // Πόλεις (Problem Facts)
    
    @PlanningEntityCollectionProperty
    private List<Visit> visitList; // Επισκέψεις (Planning Entities)
    
    @PlanningScore
    private HardSoftScore score;

    public RoutePlan() {
        
    }

    public RoutePlan(List<City> cityList, List<Visit> visitList) {
        this.cityList = cityList;
        this.visitList = visitList;
    }

    // Getters και Setters
    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
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
                "cityList=" + cityList +
                ", visitList=" + visitList +
                ", score=" + score +
                '}';
    }
}
