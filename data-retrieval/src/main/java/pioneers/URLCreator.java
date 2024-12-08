package pioneers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class URLCreator {

    // Stores the list of generated URLs
    private List<String> urls = new ArrayList<>();

    // Generates URLs with information from ListCreator
    public void generateUrls() {
        // Create and retrieve coordinates from ListCreator
        ListCreator listCreator = new ListCreator();
        listCreator.createList();

        // Get the list of coordinates
        List<String> coordinates = ListCreator.getCoordinates();
        System.out.println("There are " + coordinates.size() + " coordinates.");

        if (coordinates.isEmpty()) {
            System.out.println("Error: No coordinates found in the attractions list.");
            return;
        }

        // Loop through each coordinate and generate the URL for each as origin
        for (int i = 0; i < 1; i++) {  
            String origin = coordinates.get(i); // Set current origin

            // Create the destinations list, excluding the current origin
            List<String> destinationsList = new ArrayList<>();
            destinationsList.addAll(coordinates.subList(0, i)); // All before the current origin
            destinationsList.addAll(coordinates.subList(i + 1, coordinates.size())); // All after the current origin

            // Join the destinations into a single string with '|' separator
            String destinations = String.join("|", destinationsList);

            try {
                // Encoding the coordinates to be URL-safe
                String encodedOrigin = URLEncoder.encode(origin, StandardCharsets.UTF_8);
                String encodedDestinations = URLEncoder.encode(destinations, StandardCharsets.UTF_8);

                // Construct the final URL
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + encodedOrigin +
                        "&destinations=" + encodedDestinations +
                        "&key=AIzaSyDits1cNQEJkWEK_FSF_crxVCeV-brw3rE";

                // Add the generated URL to the list
                urls.add(url);

                // Print the final URL to check its validity
                System.out.println("Generated URL: " + url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Get the list of generated URLs
    public List<String> getUrls() {
        return this.urls;
    }

    // Main method for testing the URL creation and checking the output
    public static void main(String[] args) {
        // Create an instance of URLCreator and generate URLs
        URLCreator urlCreator = new URLCreator();
        urlCreator.generateUrls();

        // Retrieve and print all the generated URLs
        
    }
}
