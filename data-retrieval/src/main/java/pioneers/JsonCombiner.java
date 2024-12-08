package pioneers;
import com.google.gson.stream.JsonReader;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonCombiner {

    public static void main(String[] args) throws Exception {
        // List of JSON files to be combined
        List<String> jsonFiles = List.of(
             "JsonFromApi_1.json"
            
            
            
             // Add as many files as you need
        );

        // List to store combined data
        List<ProperListCreation.AttractionDistance> combinedData = new ArrayList<>();

        // Read and combine the data from each JSON file
        for (String jsonFile : jsonFiles) {
            try (JsonReader reader = new JsonReader(new FileReader(jsonFile))) {
                reader.beginArray(); // We assume the file is a list of objects
                while (reader.hasNext()) {
                    // Read the next AttractionDistance object
                    ProperListCreation.AttractionDistance attraction = readAttraction(reader);
                    combinedData.add(attraction);
                }
                reader.endArray();
            }
        }

        // Save the combined data to a new JSON file
        saveToFile(combinedData, "CombinedJson.json");

        // Output the results for verification
        if (combinedData.isEmpty()) {
            System.out.println("No data found to combine.");
        } else {
            System.out.println("Combined data saved successfully.");
        }
    }

    // Method to read an AttractionDistance object from JsonReader
    private static ProperListCreation.AttractionDistance readAttraction(JsonReader reader) throws Exception {
        String origin = null, destination = null, distance = null, duration = null, status = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "origin": 
                    origin = reader.nextString(); 
                    break;
                case "destination": 
                    destination = reader.nextString(); 
                    break;
                case "distance": 
                    distance = reader.nextString(); 
                    break;
                case "duration": 
                    duration = reader.nextString(); 
                    break;
                case "status": 
                    status = reader.nextString(); 
                    break;
                default: 
                    reader.skipValue(); // Ignore unknown fields
            }
        }
        reader.endObject();

        return new ProperListCreation.AttractionDistance(origin, destination, distance, duration, status);
    }

    // Method to save the combined data to a file
    private static void saveToFile(List<ProperListCreation.AttractionDistance> data, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            Gson gson = new Gson();
            gson.toJson(data, writer); // Write the combined data to the file
            System.out.println("Combined data saved to: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

