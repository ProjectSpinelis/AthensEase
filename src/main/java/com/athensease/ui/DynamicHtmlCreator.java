package com.athensease.ui;

import com.athensease.dataretrieval.RouteDataFetcher;
import com.athensease.dataretrieval.RouteDataFetcher.RouteData;
import com.athensease.sights.Sight;
import com.athensease.sights.Trip;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The DynamicHtmlCreator class is responsible for generating dynamic HTML content based on trip route data
 * and displaying the content in a web browser. It also provides navigation for the user interface to go back
 * to a results screen.
 */
public class DynamicHtmlCreator {

    private static Trip trip;
    private Stage stage;
    private List<Sight> sightsToShow;

    /**
     * Constructs a DynamicHtmlCreator object with a given stage and a list of sights to display.
     *
     * @param stage The stage to display the resulting scene.
     * @param sightsToShow The list of sights to be used for route data retrieval.
     */
    public DynamicHtmlCreator(Stage stage, List<Sight> sightsToShow) {
        this.stage = stage;
        this.sightsToShow = sightsToShow;
    }

    /**
     * Sets the trip object that will be used in generating the route data.
     *
     * @param trip The Trip object to be used for route data generation.
     */
    public static void setTrip(Trip trip) {
        DynamicHtmlCreator.trip = trip;
    }

    /**
     * Creates a scene with a button to go back and navigates to the results screen.
     * This method fetches route data for the given sights and generates a dynamic HTML
     * map that is opened in the user's default web browser.
     *
     * @return The Scene object with a back button to navigate to the results screen.
     */
    public Scene createScene() {
        RouteDataFetcher routeDataFetcher = new RouteDataFetcher(sightsToShow);
        RouteData routeData = routeDataFetcher.fetchRouteData();

        if (routeData != null) {
            String origin = routeData.getOrigin();
            String destination = routeData.getDestination();
            String polyline = routeData.getOverviewPolyline();
            ArrayList<String> waypoints = routeData.getWaypoints();

            // Generate dynamic HTML content for the route map
            String htmlContent = generateDynamicHTML(origin, destination, waypoints, polyline);

            // Open the generated HTML content in the default web browser
            openInBrowser(htmlContent);

            // Add a back button to return to the results screen
            Button backButton = new Button("Back");
            backButton.getStyleClass().add("big-button");
            backButton.setOnAction(e -> goToResultsScreen());

            VBox box = new VBox();
            box.getChildren().add(backButton);
            StackPane root = new StackPane();
            root.getChildren().add(box);

            // Create and return the scene with the back button
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            return scene;
        } else {
            System.err.println("Failed to fetch route data.");
            return null;
        }
    }

    /**
     * Generates the dynamic HTML content for a route map, including origin, destination, and waypoints.
     * The generated HTML uses the Google Maps API to display the route on the map.
     *
     * @param origin The starting point of the route.
     * @param destination The destination of the route.
     * @param waypoints The list of waypoints for the route.
     * @param polyline The polyline representation of the route.
     * @return The generated HTML content as a String.
     */
    public static String generateDynamicHTML(String origin, String destination, ArrayList<String> waypoints, String polyline) {
        StringBuilder waypointScript = new StringBuilder();
        for (String waypoint : waypoints) {
            waypointScript.append("'").append(waypoint).append("',");
        }
        if (waypointScript.length() > 0) {
            waypointScript.setLength(waypointScript.length() - 1);
        }

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "  <title>Route Map</title>" +
                "  <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyDits1cNQEJkWEK_FSF_crxVCeV-brw3rE&libraries=geometry&callback=initMap\" async defer></script>" +
                "  <script>" +
                "    function initMap() {" +
                "      var origin = '" + origin + "';" +
                "      var destination = '" + destination + "';" +
                "      var waypoints = [" + waypointScript.toString() + "];" +
                "      var map = new google.maps.Map(document.getElementById('map'), {" +
                "        zoom: 12," +
                "        center: { lat: parseFloat(origin.split(',')[0]), lng: parseFloat(origin.split(',')[1]) }," +
                "        mapTypeId: google.maps.MapTypeId.ROADMAP" +
                "      });" +
                "      var directionsService = new google.maps.DirectionsService();" +
                "      var directionsRenderer = new google.maps.DirectionsRenderer();" +
                "      directionsRenderer.setMap(map);" +
                "      var request = {" +
                "        origin: origin," +
                "        destination: destination," +
                "        waypoints: waypoints.map(function(waypoint) { return { location: waypoint, stopover: true }; })," +
                "        travelMode: google.maps.TravelMode.DRIVING" +
                "      };" +
                "      directionsService.route(request, function(response, status) {" +
                "        if (status == 'OK') {" +
                "          directionsRenderer.setDirections(response);" +
                "        } else {" +
                "          alert('Directions request failed due to ' + status);" +
                "        }" +
                "      });" +
                "    }" +
                "  </script>" +
                "</head>" +
                "<body>" +
                "  <h1>Route Map</h1>" +
                "  <div id=\"map\" style=\"height: 600px; width: 100%;\"></div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Opens the generated HTML content in the user's default web browser.
     *
     * @param htmlContent The HTML content to be opened.
     */
    private void openInBrowser(String htmlContent) {
        try {
            File tempFile = File.createTempFile("route_map", ".html");
            tempFile.deleteOnExit();

            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(htmlContent);
            }

            Desktop.getDesktop().browse(tempFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the results screen, passing the current trip object.
     */
    public void goToResultsScreen() {
        ResultScreen screen3 = new ResultScreen(stage);
        ResultScreen.setTrip(trip); // Pass the trip
        Scene resultsScene = screen3.createScene();
        stage.setScene(resultsScene);
    }
}
