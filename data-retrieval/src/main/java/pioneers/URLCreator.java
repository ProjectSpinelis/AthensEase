package pioneers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class URLCreator {
    private String url;

    // Generates URL with information from ListCreator
    public void generateUrl() {
        
           ListCreator listCreator= new ListCreator();
           listCreator.createList();
        // Check if the list of coordinates is empty or not
        List<String> coordinates = ListCreator.getCoordinates();
        System.out.println("Coordinates: " + coordinates);
        
        if (coordinates.isEmpty()) {
            System.out.println("Error: No coordinates found in the attractions list.");
            return;
        }

        // Join coordinates for origins and destinations (using the same list for both)
        String origins = String.join("|", coordinates);
        String destinations = String.join("|", coordinates);

        // Print raw origins and destinations before encoding
        System.out.println("Raw Origins: " + origins);
        System.out.println("Raw Destinations: " + destinations);

        // Encoding the coordinates to be URL-safe
        origins = URLEncoder.encode(origins, StandardCharsets.UTF_8);
        destinations = URLEncoder.encode(destinations, StandardCharsets.UTF_8);

        // Print encoded origins and destinations
        System.out.println("Encoded Origins: " + origins);
        System.out.println("Encoded Destinations: " + destinations);

        // Print list of attractions to verify correct data
        System.out.println("Attractions: " + ListCreator.getAttractions());

        // Create the URL for the Google Maps Distance Matrix API
        this.url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origins +
                "&destinations=" + destinations +
                "&key=AIzaSyDits1cNQEJkWEK_FSF_crxVCeV-brw3rE";
        
        // Print the final URL to check its validity
        System.out.println("Generated URL: " + this.url);
    }

    public String getUrl() {
        return this.url;
    }

    // Main method for testing the URL creation and checking the output
    public static void main(String[] args) {
        URLCreator urlCreator = new URLCreator();
        urlCreator.generateUrl();
    }
}
