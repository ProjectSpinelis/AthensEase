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
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.List;

public class DynamicHtmlCreator {

    private static Trip trip;
    private Stage stage;
    private List<Sight> sightsToShow;

    public DynamicHtmlCreator(Stage stage, List<Sight> sightsToShow) {
        this.stage = stage;
        this.sightsToShow = sightsToShow;
    }

    public static void setTrip(Trip trip) {
        DynamicHtmlCreator.trip = trip;
    }

    public Scene createScene() {
        RouteDataFetcher routeDataFetcher = new RouteDataFetcher(sightsToShow);
        RouteData routeData = routeDataFetcher.fetchRouteData();

        if (routeData != null) {
            String origin = routeData.getOrigin();
            String destination = routeData.getDestination();
            String polyline = routeData.getOverviewPolyline();
            ArrayList<String> waypoints = routeData.getWaypoints();

            String htmlContent = generateDynamicHTML(origin, destination, waypoints, polyline);

            // Create WebView to display HTML content
            WebView webView = new WebView();
            webView.getEngine().loadContent(htmlContent);

            Button backButton = new Button("Back");
            backButton.getStyleClass().add("big-button");
            backButton.setOnAction(e -> goToResultsScreen());

            VBox box = new VBox();
            box.getChildren().add(webView);
            box.getChildren().add(backButton);
            StackPane root = new StackPane();
            root.getChildren().add(box);

            // Create Scene and return it
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            return scene;
        } else {
            System.err.println("Failed to fetch route data.");
            return null;
        }
    }

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

    // Function to switch to the results screen
    public void goToResultsScreen() {
        ResultScreen screen3 = new ResultScreen(stage);
        ResultScreen.setTrip(trip); // Pass the trip
        Scene resultsScene = screen3.createScene();
        stage.setScene(resultsScene);
    }
}
