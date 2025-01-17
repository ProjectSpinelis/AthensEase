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

/**
 * The RouteDataFetcher class is responsible for fetching route data from the Google Directions API.
 * It uses the MapURLBuilder class to generate the API request URL, sends a GET request, and processes
 * the JSON response to extract relevant route information such as the origin, destination, waypoints, 
 * and polyline for the route.
 */
public class RouteDataFetcher {

    // List of sights (tourist locations) for generating the route.
    private List<Sight> sights;

    /**
     * Constructor that initializes the RouteDataFetcher with a list of sights.
     * 
     * @param sights The list of sights to be used for generating the route.
     */
    public RouteDataFetcher(List<Sight> sights) {
        this.sights = sights;
    }

    /**
     * Fetches route data from the Google Directions API using the URL generated by the MapURLBuilder.
     * It processes the JSON response to extract the origin, destination, waypoints, and polyline data.
     * 
     * @return A RouteData object containing the origin, destination, waypoints, and overview polyline,
     *         or null if an error occurs during data retrieval.
     */
    public RouteData fetchRouteData() {
        try {
            // Fetch the URL from the MapURLBuilder class
            MapURLBuilder mapURLBuilder = new MapURLBuilder(sights);
            URL url = new URL(mapURLBuilder.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the JSON response from the API
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            Gson gson = new Gson();
            JsonObject response = gson.fromJson(reader, JsonObject.class);

            // Extract the routes data from the response
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

            // Fetching waypoints (intermediate locations)
            for (int i = 1; i < legs.size() - 1; i++) {
                JsonObject leg = legs.get(i).getAsJsonObject();
                String waypoint = leg.getAsJsonObject("end_location").get("lat").getAsString() + "," + 
                                  leg.getAsJsonObject("end_location").get("lng").getAsString();
                waypoints.add(waypoint);
            }

            // Return the route data, including origin, destination, waypoints, and polyline
            return new RouteData(origin, destination, waypoints, overviewPolyline);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Inner class to hold route data, including origin, destination, waypoints, and polyline.
     */
    public static class RouteData {
        
        // Origin point of the route
        private String origin;
        
        // Destination point of the route
        private String destination;
        
        // List of waypoints (intermediate locations) in the route
        private ArrayList<String> waypoints;
        
        // Overview polyline string representing the route
        private String overviewPolyline;

        /**
         * Constructor to initialize the RouteData object.
         * 
         * @param origin The origin point of the route.
         * @param destination The destination point of the route.
         * @param waypoints The list of waypoints (intermediate locations) along the route.
         * @param overviewPolyline The overview polyline of the route.
         */
        public RouteData(String origin, String destination, ArrayList<String> waypoints, String overviewPolyline) {
            this.origin = origin;
            this.destination = destination;
            this.waypoints = waypoints;
            this.overviewPolyline = overviewPolyline;
        }

        /**
         * Gets the origin point of the route.
         * 
         * @return The origin point as a string (latitude,longitude).
         */
        public String getOrigin() { return origin; }

        /**
         * Gets the destination point of the route.
         * 
         * @return The destination point as a string (latitude,longitude).
         */
        public String getDestination() { return destination; }

        /**
         * Gets the list of waypoints along the route.
         * 
         * @return The list of waypoints as strings (latitude,longitude).
         */
        public ArrayList<String> getWaypoints() { return waypoints; }

        /**
         * Gets the overview polyline of the route.
         * 
         * @return The overview polyline as a string.
         */
        public String getOverviewPolyline() { return overviewPolyline; }
    }
}
