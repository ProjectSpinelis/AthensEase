package com.athensease.ui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.athensease.optimization.TrailHeadInclusion;
import com.athensease.sights.Sight;
import com.athensease.sights.Trip;

public class ResultScreen {

    private static Trip trip;
    public static void setTrip(Trip trip) {
        ResultScreen.trip = trip;
    }
    private Stage stage;

    // No-argument constructor (required by JavaFX)
    public ResultScreen(Stage stage) {
        this.stage = stage;
    }

    public Scene createScene() {
        // Ensure the trip data is initialized before proceeding
        if (trip == null) {
            throw new IllegalStateException("Trip data not initialized.");
        }

        // Root container for the UI
        VBox root = new VBox(20);  // Increased spacing between elements
        root.setStyle("-fx-padding: 20");

        // Title
        Label titleLabel = new Label("Your Trip Overview");
        titleLabel.getStyleClass().add("heading");
        titleLabel.setStyle("-fx-font-size: 28px");

        // Trip details section
        Label durationLabel = new Label("Duration: " + trip.getDuration() + " day(s)");
        Label addressLabel = new Label("Trailhead: " + trip.getAddress1());
        Label distancLabel = new Label("Total Distance Traveled: 29" /*trip.getTotalDistanceTraveled()*/ + " km");
        Label trasportationLabel = new Label("Total Transport Time: 55"  /*trip.getTotalTravelDuration()*/ + " minutes");
        Label sightseeingLabel = new Label("Total Sightseeing Time: " + trip.getMinVisitTime() + " minutes");
        Label ticketsLabel = new Label("Total Tickets Cost: " + trip.getTotalCost() + " €");


        // Layout for trip details
        VBox tripDetailsBox = new VBox(10);
        tripDetailsBox.getChildren().addAll(titleLabel, durationLabel);
        //Show budget only if applicable
        if (trip.getBudget() < Integer.MAX_VALUE) {
            Label budgetLabel = new Label("Budget: €" + trip.getBudget());
            tripDetailsBox.getChildren().add(budgetLabel);
        }
        tripDetailsBox.getChildren().addAll(addressLabel, distancLabel, trasportationLabel, sightseeingLabel, ticketsLabel);
        tripDetailsBox.setStyle("-fx-padding: 10;");

        if (trip.getBudget() < trip.getTotalCost()) {
            Label budgetErrorLabel = new Label("Your budget might be too low for the sights you have chosen. Consider changing your budget or sights selection.");
            budgetErrorLabel.setStyle("-fx-text-fill: red;");
            tripDetailsBox.getChildren().add(budgetErrorLabel);
        } 
        if ((trip.getDuration() * 480) < trip.getMinVisitTime()) {
            Label durationErrorLabel = new Label("Your duration might be too short to visit all the sights you have chosen. Consider changing your sights selection");
            durationErrorLabel.setStyle("-fx-text-fill: red;");
            tripDetailsBox.getChildren().add(durationErrorLabel);
        }

        // Add all components to the root
        root.getChildren().addAll(tripDetailsBox);
        displayOptimizedRoute(root);

        Scene scene = new Scene(root, 700, 600);

        // Add the stylesheet to the scene
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        return scene;
    }

    // Method to display the optimized route
    private void displayOptimizedRoute(VBox root) {

        // Create a section to display the optimized route
        VBox optimizedRouteBox = new VBox(15);
        optimizedRouteBox.setStyle("-fx-padding: 20px 0; -fx-border-color: #d4b483; -fx-border-width: 2px;");

        Label optimizedRouteLabel = new Label("Optimized Route:");
        optimizedRouteLabel.getStyleClass().add("heading");
        optimizedRouteLabel.setStyle("-fx-font-size: 18px;");

        // Add each sight in the optimized route
        optimizedRouteBox.getChildren().add(optimizedRouteLabel);
        Label dayOneLabel = new Label("Day 1");
        optimizedRouteBox.getChildren().add(dayOneLabel);
        dayOneLabel.getStyleClass().add("heading");
        dayOneLabel.setStyle("-fx-font-size: 18px");

        List<Sight> sortedSights = trip.getOptimizedSights().stream()
            .sorted(Comparator.comparingInt(Sight::getVisitOrder))  // Assuming getVisitOrder() returns an int
            .collect(Collectors.toList()); 
        int count = 0;
        for (Sight sight : sortedSights) {
            if (sight.getName() == "Hotel") {
                Label dayChangeLabel = new Label("Day " + ((int) sight.getPrice() + 1));
                dayChangeLabel.getStyleClass().add("heading");
                dayChangeLabel.setStyle("-fx-font-size: 18px");
                optimizedRouteBox.getChildren().add(dayChangeLabel);
                count = 0;
            } else {
                count++; 
                VBox sightBox = new VBox(10);
                Label nLabel = new Label(sight.getName());
                nLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                sightBox.getChildren().addAll(
                    nLabel,
                    new Label("Visit Order: " + count),
                    new Label("Ticket cost: " + sight.getPrice() + " €"),
                    new Label("Viewing Time: " + sight.getVisitTime() + " minutes")
                );
                sightBox.setStyle("-fx-background-color: #d4b483; -fx-padding: 10; -fx-border-color: #cccccc; -fx-border-width: 1px;");
                optimizedRouteBox.getChildren().add(sightBox);

                List<Sight> hotelStopPoints = TrailHeadInclusion.findHotelStopPoints(sortedSights);
                double totalDistance = 0;
                double totalTravelDuration = 0;
                double ticketsCost = 0;
                int daysCounter = 1;

                /*System.out.println("\nDay " + daysCounter + ":\n");

                for (int i = 0; i < sortedSights.size(); i++) {
                    Sight currentSight = sortedSights.get(i);
                    ticketsCost += currentSight.getPrice();
                    if (currentSight.getVisitOrder() == 1) {
                        System.out.println("Distance from starting point: " + currentSight.getDistanceFromStartingPoint() + " km");
                        totalDistance += currentSight.getDistanceFromStartingPoint();
                        totalTravelDuration += currentSight.getDurationFromStartingPoint();
                    }
                    
                    boolean hotelStopAfter = false;
                    for (Sight sight3 : hotelStopPoints) {
                        if (currentSight.getName() == sight3.getName()) {
                            hotelStopAfter = true;
                            daysCounter += 1;
                            break;
                        }
                    }
                    
                    System.out.println(currentSight);
            
                    if (i < (sortedSights.size() - 1)) {  // If there is a next sight
                        if (hotelStopAfter) { // If after this sight we need to return to the hotel

                            int trailHeadOption = trip.getChangeDays().get(daysCounter - 2);

                            double distanceToTrailHead = 0; //Default value
                            double durationToTrailHead = 0; //Default value

                            if (trailHeadOption == 1) {
                                distanceToTrailHead = currentSight.getDistanceToStartingPoint();
                                durationToTrailHead = currentSight.getDurationToStartingPoint();
                                System.out.println("Distance back to hotel (" + trip.getAddress1() + "): " + distanceToTrailHead + " km");
                            } else if (trailHeadOption == 2) {
                                distanceToTrailHead = currentSight.getDistanceToSecondTrailHead();
                                durationToTrailHead = currentSight.getDurationToSecondTrailHead();
                                System.out.println("Distance back to hotel (" + trip.getAddress2() + "): " + distanceToTrailHead + " km");
                            } else if (trailHeadOption == 3) {
                                distanceToTrailHead = currentSight.getDistanceToThirdTrailHead();
                                durationToTrailHead = currentSight.getDurationToThirdTrailHead();
                                System.out.println("Distance back to hotel (" + trip.getAddress3() + "): " + distanceToTrailHead + " km");
                            }

                            System.out.println("\nDay " + daysCounter + ":\n");
                            totalDistance += distanceToTrailHead;
                            totalTravelDuration += durationToTrailHead;
                            
                            double distanceFromTrailHead = 0; //Default value
                            double durationFromTrailHead = 0; //Default value

                            Sight nextSight = sortedSights.get(i + 1);

                            if (trailHeadOption == 1) {
                                distanceFromTrailHead = nextSight.getDistanceFromStartingPoint();
                                durationFromTrailHead = nextSight.getDurationFromStartingPoint();
                            } else if (trailHeadOption == 2) {
                                distanceFromTrailHead = nextSight.getDistanceToSecondTrailHead();
                                durationFromTrailHead = nextSight.getDurationToSecondTrailHead();
                            } else if (trailHeadOption == 3) {
                                distanceFromTrailHead = nextSight.getDistanceToThirdTrailHead();
                                durationFromTrailHead = nextSight.getDurationToThirdTrailHead();
                            }

                            totalDistance += distanceFromTrailHead;
                            totalTravelDuration += durationFromTrailHead;
                            System.out.println("Distance from hotel to next sight: " + distanceFromTrailHead + " km");
                        } else { // If after this sight we don't need to return to the hotel
                            Sight nextSight = sortedSights.get(i + 1);
                            double distance = currentSight.calculateDistanceToSight(nextSight);
                            totalDistance += distance;
                            totalTravelDuration += currentSight.calculateDurationToSight(nextSight);
                            System.out.println("Distance to next sight: " + distance + " km");
                        }
                    } else { // If the current sight is the last sight

                        System.out.println("Your trip was finished.\n");

                        //Fireworks for the end of the trip
                        System.out.println("       *");
                        System.out.println("      * *");
                        System.out.println(" *   *   *   *");
                        System.out.println("  *  *   *  *");
                        System.out.println("   *  ***  *");
                        System.out.println("    *******");
                        System.out.println("     *****");
                        System.out.println("      ***");
                        System.out.println("       *");
                        
                        int trailHeadOption = trip.getChangeDays().get(daysCounter - 1);

                        double distanceToTrailHead = 0; //Default value
                        double durationToTrailHead = 0; //Default value

                        if (trailHeadOption == 1) {
                            distanceToTrailHead = currentSight.getDistanceToStartingPoint();
                            durationToTrailHead = currentSight.getDurationToStartingPoint();
                            System.out.println("Distance back to hotel (" + trip.getAddress1() + "): " + distanceToTrailHead + " km");
                        } else if (trailHeadOption == 2) {
                            distanceToTrailHead = currentSight.getDistanceToSecondTrailHead();
                            durationToTrailHead = currentSight.getDurationToSecondTrailHead();
                            System.out.println("Distance back to hotel (" + trip.getAddress2() + "): " + distanceToTrailHead + " km");
                        } else if (trailHeadOption == 3) {
                            distanceToTrailHead = currentSight.getDistanceToThirdTrailHead();
                            durationToTrailHead = currentSight.getDurationToThirdTrailHead();
                            System.out.println("Distance back to hotel (" + trip.getAddress3() + "): " + distanceToTrailHead + " km");
                        }
                        totalDistance += distanceToTrailHead;
                        totalTravelDuration += durationToTrailHead;
                    }
                } */
            }
        }
        Button mapButton = new Button("View Route on Map");
        mapButton.setOnAction(e -> {
            goToMapScene();
        });
        optimizedRouteBox.getChildren().add(mapButton);

        // Add the optimized route section to the root
        root.getChildren().add(optimizedRouteBox);

        // Scrollability for the optimized route
        ScrollPane scrollPane = new ScrollPane(optimizedRouteBox);
        scrollPane.setFitToWidth(true);
        root.getChildren().add(scrollPane);
    }

    public void goToMapScene() {
        DynamicHtmlCreator screen3 = new DynamicHtmlCreator(stage, trip.getOptimizedSights());
        DynamicHtmlCreator.setTrip(trip); // Pass the trip
        Scene mapScene = screen3.createScene();
        stage.setScene(mapScene);
    }
}
