package com.athensease.dataretrieval;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;   
import java.io.IOException; 
 

public class JsonFormatter {

    // Δημιουργία κλάσης για τα αποτελέσματα αποστάσεων και διάρκειας
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

    // Δημιουργία κλάσης για το JSON
    static class ApiResponse {
        List<String> destination_addresses;  // Λίστα διευθύνσεων προορισμών
        List<String> origin_addresses;  // Λίστα διευθύνσεων αρχικών σημείων
        List<Row> rows;  // Λίστα με τα αποτελέσματα

        static class Row {
            List<Element> elements;  // Λίστα από στοιχεία με αποστάσεις και διάρκειες

            static class Element {
                Distance distance;  // Απόσταση
                Duration duration;  // Διάρκεια
                String status;  // Κατάσταση

                static class Distance {
                    String text;  // Απόσταση σε κείμενο (π.χ., "347 χλμ")
                    int value;  // Απόσταση σε μέτρα
                }

                static class Duration {
                    String text;  // Χρόνος σε κείμενο (π.χ., "9 ώρες 34 λεπτά")
                    int value;  // Χρόνος σε δευτερόλεπτα
                }
            }
        }
    }

    // This is the important method!
    public static void formatJson(String jsonResponse) {
    
        // Λίστα για να αποθηκεύσουμε τα αποτελέσματα
        List<AttractionDistance> attractionDistances = new ArrayList<>();
        System.out.println("Απάντηση JSON: " + jsonResponse);  // Εκτύπωση για έλεγχο
    
        // Δημιουργία Gson αντικειμένου
        Gson gson = new Gson();
    
        // Μετατροπή JSON σε αντικείμενο ApiResponse
        ApiResponse apiResponse = gson.fromJson(jsonResponse, ApiResponse.class);
    
        // Διαχείριση των δεδομένων της απόκρισης
        if (apiResponse.rows != null && !apiResponse.rows.isEmpty()) {
            for (int i = 0; i < apiResponse.origin_addresses.size(); i++) {
                String origin = apiResponse.origin_addresses.get(i);
                for (int j = 0; j < apiResponse.destination_addresses.size(); j++) {
                    String destination = apiResponse.destination_addresses.get(j);
                    ApiResponse.Row.Element element = apiResponse.rows.get(i).elements.get(j);
    
                    String distance = element.distance != null ? element.distance.text : "N/A";
                    String duration = element.duration != null ? element.duration.text : "N/A";
                    String status = element.status != null ? element.status : "N/A";
    
                    // Αποθήκευση των αποτελεσμάτων στη λίστα
                    AttractionDistance attractionDistance = new AttractionDistance(origin, destination, distance, duration, status);
                    attractionDistances.add(attractionDistance);
                }
            }

            // Save to file
            saveToFile(attractionDistances, "data/distances.json");
    
        } else {
            System.out.println("Δεν βρέθηκαν αποστάσεις.");
        }
    }

    public static void saveToFile(List<AttractionDistance> attractionDistances, String jsonfromapi) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();  // Enable pretty printing
        try (FileWriter writer = new FileWriter(jsonfromapi, true)) {
            gson.toJson(attractionDistances, writer);
            writer.write(System.lineSeparator());  // Convert the list to formatted JSON and save to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
