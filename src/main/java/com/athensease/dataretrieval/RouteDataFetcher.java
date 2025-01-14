package com.athensease.dataretrieval;
import com.athensease.sights.Sight;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RouteDataFetcher {

    private List<Sight> sights;

    public RouteDataFetcher(List<Sight> sights) {
        this.sights = sights;
    }

    // Method to fetch route data from the API
    public RouteData fetchRouteData() {
        try {
            // Fetch the URL from the URLBuilder
            MapURLBuilder mapURLBuilder = new MapURLBuilder(sights);
            URL url = new URL(mapURLBuilder.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the JSON response from the API
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            Gson gson = new Gson();
            JsonObject response = gson.fromJson(reader, JsonObject.class);

            // Fetch the routes data
            JsonObject route = response.getAsJsonArray("routes").get(0).getAsJsonObject();
            String overviewPolyline = route.getAsJsonObject("overview_polyline").get("points").getAsString();
            JsonArray legs = route.getAsJsonArray("legs");
            
            // Lists to store origin, destination, and waypoints
            ArrayList<String> waypoints = new ArrayList<>();
            
            // Fetching origin
            String origin = legs.get(0).getAsJsonObject().getAsJsonObject("start_location")
                                 .get("lat").getAsString() + "," + 
                                 legs.get(0).getAsJsonObject().getAsJsonObject("start_location")
                                 .get("lng").getAsString();
            
            // Fetching destination
            String destination = legs.get(legs.size() - 1).getAsJsonObject().getAsJsonObject("end_location")
                                      .get("lat").getAsString() + "," + 
                                      legs.get(legs.size() - 1).getAsJsonObject().getAsJsonObject("end_location")
                                      .get("lng").getAsString();

            // Fetching waypoints
            // We add waypoints if there are intermediate steps (legs in between start and destination)
            for (int i = 1; i < legs.size() - 1; i++) {
                JsonObject leg = legs.get(i).getAsJsonObject();
                String waypoint = leg.getAsJsonObject("end_location").get("lat").getAsString() + "," + 
                                  leg.getAsJsonObject("end_location").get("lng").getAsString();
                waypoints.add(waypoint);
            }

            // Return the route data including origin, destination, waypoints, and polyline
            return new RouteData(origin, destination, waypoints, overviewPolyline);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Inner class to hold the route data (origin, destination, waypoints, polyline)
    public static class RouteData {
        private String origin;
        private String destination;
        private ArrayList<String> waypoints;
        private String overviewPolyline;

        public RouteData(String origin, String destination, ArrayList<String> waypoints, String overviewPolyline) {
            this.origin = origin;
            this.destination = destination;
            this.waypoints = waypoints;
            this.overviewPolyline = overviewPolyline;
        }

        // Getters for data
        public String getOrigin() { return origin; }
        public String getDestination() { return destination; }
        public ArrayList<String> getWaypoints() { return waypoints; }
        public String getOverviewPolyline() { return overviewPolyline; }
    }
}

