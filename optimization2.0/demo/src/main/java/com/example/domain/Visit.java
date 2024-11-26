package com.example.domain;

//import com.example.domain.City;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
//import org.optaplanner.core.api.domain.variable.PlanningVariableGraphType;

@PlanningEntity
public class Visit {
    
    @PlanningVariable
    private City city;

    private int visitOrder;

    public Visit() {
        
    }

    public Visit(int visitOrder, City city) {
        this.visitOrder = visitOrder;
        this.city = city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }
    
    public int getVisitOrder() {
        return visitOrder;
    } 

    public double calculateDistanceToVisit(Visit previousVisit) {
        return this.getCity().calculateDistanceToCity(previousVisit.getCity());
    }
}
