package com.athensease.ui;

import com.athensease.sights.RouteDataFetcher;
import com.athensease.sights.RouteDataFetcher.RouteData;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DynamicHtmlCreator extends Application {

    @Override
    public void start(Stage primaryStage) {
        RouteDataFetcher routeDataFetcher = new RouteDataFetcher();
        RouteDataFetcher.RouteData routeData = routeDataFetcher.fetchRouteData();

        if (routeData != null) {
            String origin = routeData.getOrigin();
            String destination = routeData.getDestination();
            String polyline = routeData.getOverviewPolyline();
            ArrayList<String> waypoints = routeData.getWaypoints();

            String htmlContent = generateDynamicHTML(origin, destination, waypoints, polyline);

            // Pass the generated HTML content to the WebViewer
            WebViewer webViewer = new WebViewer(primaryStage);
            webViewer.displayMap(htmlContent);
        } else {
            System.err.println("Failed to fetch route data.");
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
        "        mapTypeId: google.maps.MapTypeId.ROADMAP" + // Default map view
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

    public static void main(String[] args) {
        launch(args);
    }
}

