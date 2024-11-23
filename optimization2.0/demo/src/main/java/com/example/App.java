package com.example;

import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

import com.example.solver.RoutePlanConstraintProvider;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;

import com.example.domain.City;
import com.example.domain.Visit;
import com.example.domain.RoutePlan;

public class App {

    public static void main(String[] args) {
        SolverConfig solverConfig = new SolverConfig()
                .withSolutionClass(RoutePlan.class)
                .withEntityClasses(Visit.class)
                .withConstraintProviderClass(RoutePlanConstraintProvider.class)
                // The solver runs only for 5 seconds on this small dataset.
                // It's recommended to run for at least 5 minutes ("5m") otherwise.
                .withTerminationSpentLimit(Duration.ofSeconds(5));

        SolverFactory<RoutePlan> solverFactory = SolverFactory.create(solverConfig); 


        // Load the problem
        RoutePlan problem = generateDemoData();

        // Solve the problem
        Solver<RoutePlan> solver = solverFactory.buildSolver();
        RoutePlan solution = solver.solve(problem);

        // Visualize the solution
        printRoutePlan(solution);

        // System.out.println("Score: " + solution.getScore()); // Prints the Score, but its hidden for the being
    }

    public static RoutePlan generateDemoData() {
        List<City> cityList= new ArrayList<>(9);
        cityList.add(new City("thessaloniki", 40.6403167, 22.9352716));
        cityList.add(new City("patra", 38.2462420, 21.7350847));
        cityList.add(new City("irakleio", 35.3390800, 25.1332843));
        cityList.add(new City("ioannina", 39.6639818, 20.8522784));

        cityList.add(new City("kavala", 40.9369224, 24.4122766));
        cityList.add(new City("volos", 39.3639, 22.9422));
        cityList.add(new City("trikala", 39.5560869, 21.7678844));
        cityList.add(new City("pylos", 36.9137968, 21.6963888));
        cityList.add(new City("larisa", 39.6383092, 22.4160706));

        List<Visit> visitList = new ArrayList<>();

        /*
        visitList.add(new Visit(1, cityList.get(0)));
        visitList.add(new Visit(2, cityList.get(1)));
        visitList.add(new Visit(3, cityList.get(2)));
        visitList.add(new Visit(4, cityList.get(3)));
        visitList.add(new Visit(5, cityList.get(4)));
        visitList.add(new Visit(6, cityList.get(5)));
        visitList.add(new Visit(7, cityList.get(6)));
        visitList.add(new Visit(8, cityList.get(7)));
        visitList.add(new Visit(9, cityList.get(8)));
        */

        for(int i=1; i<=cityList.size(); i++) {
            visitList.add(new Visit(i, cityList.get(i-1)));
        }

        return new RoutePlan(cityList, visitList);
    }

    private static void printRoutePlan(RoutePlan routePlan) {
        double totalDistance = 0;
        for (int i = 0; i < routePlan.getVisitList().size(); i++) {
            System.out.print("Visit Order: " + routePlan.getVisitList().get(i).getVisitOrder()
             + " City Name: " + routePlan.getVisitList().get(i).getCity().getName());
            if (i > 0) {
                System.out.println(" Distance Traveled: " + routePlan.getVisitList().get(i).calculateDistanceToVisit(routePlan.getVisitList().get(i-1)));
                totalDistance += routePlan.getVisitList().get(i).calculateDistanceToVisit(routePlan.getVisitList().get(i-1));
            } else {
                System.out.println(" Distance Traveled: " 
                + routePlan.getVisitList().get(0).getCity().calculateDistanceToCity(new City("athens - starting position", 37.9755648, 23.7348324)));
                totalDistance += routePlan.getVisitList().get(0).getCity().calculateDistanceToCity(new City("athens - starting position", 37.9755648, 23.7348324));
            }
        }
        System.out.println("Total Distance: " + totalDistance);
    } 
}
