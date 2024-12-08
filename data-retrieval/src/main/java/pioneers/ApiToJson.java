package pioneers;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class ApiToJson {

    // Inner classes for AttractionDistance and ApiResponse remain unchanged
    static class AttractionDistance {
        String origin;
        String destination;
        String distance;
        String duration;
        String status;

        AttractionDistance(String origin, String destination, String distance, String duration, String status) {
            this.origin = origin;
            this.destination = destination;
            this.distance = distance;
            this.duration = duration;
            this.status = status;
        }
    }

    static class ApiResponse {
        List<String> destination_addresses;
        List<String> origin_addresses;
        List<Row> rows;

        static class Row {
            List<Element> elements;

            static class Element {
                Distance distance;
                Duration duration;
                String status;

                static class Distance {
                    String text;
                    int value;
                }

                static class Duration {
                    String text;
                    int value;
                }
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        URLCreator urlCreator = new URLCreator();
        urlCreator.generateUrls(); // Generate URLs and store in a list
        List<String> urls = urlCreator.getUrls(); // Retrieve the list of URLs

        // Process each URL
        for (int i = 0; i < urls.size(); i++) {
            String currentUrl = urls.get(i);
            String filename = "JsonFromApi_" + (i + 1) + ".json";

            // Call the reusable method to process each URL
            processUrl(currentUrl, filename);
        }
    }

    // Method to process a single URL and save the result
    public static void processUrl(String url, String filename) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        try {
            // Create and send HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();

                // Parse JSON response
                ApiResponse apiResponse = gson.fromJson(jsonResponse, ApiResponse.class);

                // Extract results
                List<AttractionDistance> attractionDistances = new ArrayList<>();
                for (int i = 0; i < apiResponse.origin_addresses.size(); i++) {
                    String origin = apiResponse.origin_addresses.get(i);
                    for (int j = 0; j < apiResponse.destination_addresses.size(); j++) {
                        String destination = apiResponse.destination_addresses.get(j);
                        ApiResponse.Row.Element element = apiResponse.rows.get(i).elements.get(j);

                        String distance = element.distance != null ? element.distance.text : "N/A";
                        String duration = element.duration != null ? element.duration.text : "N/A";
                        String status = element.status != null ? element.status : "N/A";

                        attractionDistances.add(new AttractionDistance(origin, destination, distance, duration, status));
                    }
                }

                // Save results to a JSON file
                saveToFile(attractionDistances, filename);
                System.out.println("Saved: " + filename);
            } else {
                System.out.println("Error: " + response.statusCode() + " for URL: " + url);
            }
        } catch (Exception e) {
            System.out.println("Error processing URL: " + url);
            e.printStackTrace();
        }
    }

    // Save results to a JSON file
    public static void saveToFile(List<AttractionDistance> attractionDistances, String filename) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(attractionDistances, writer);
        } catch (IOException e) {
            System.out.println("Error saving file: " + filename);
            e.printStackTrace();
        }
    }
}
