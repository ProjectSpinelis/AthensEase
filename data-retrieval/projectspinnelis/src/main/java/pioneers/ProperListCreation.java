package pioneers;

import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProperListCreation {

    public static void main(String[] args) throws Exception {

        // Χάρτης που αντιστοιχεί το κανονικό όνομα με την πλήρη διεύθυνση
        Map<String, String> attractionsMap = new HashMap<>();
        attractionsMap.put("Ακρόπολη", "Anafiotika 11, Athina 105 58, Greece");
        attractionsMap.put("Μετέωρα", "Epar.Od. Kalampaka 1, Trikala 421 00, Greece");
        attractionsMap.put("Λευκός Πύργος", "Str. Tsirogianni 2, Thessaloniki 546 21, Greece");
        attractionsMap.put("Παλάτι Κνωσού", "Metageni 15, Knosos 714 09, Greece");
        attractionsMap.put("Δελφοί", "Θησαυρός των Αθηναίων, Delfi 330 54, Greece");

        // Επιλογές χρηστών (οι περιοχές που ενδιαφέρουν τον χρήστη)
        List<String> userSelectedLocations = List.of("Ακρόπολη", "Μετέωρα", "Δελφοί");

        // Δημιουργία λίστας για αποθήκευση των αποτελεσμάτων
        List<AttractionDistance> filteredDistances = new ArrayList<>();

        // Ανάγνωση του μεγάλου JSON αρχείου με JsonReader
        try (JsonReader reader = new JsonReader(new FileReader("JsonFromApi.json"))) {
            reader.beginArray(); // Υποθέτουμε ότι το αρχείο είναι λίστα αντικειμένων
            while (reader.hasNext()) {
                // Διαβάζουμε το επόμενο αντικείμενο AttractionDistance
                AttractionDistance attraction = readAttraction(reader);

                // Εκτύπωση για έλεγχο αν τα δεδομένα διαβάστηκαν σωστά
                System.out.println("Ανάγνωση διεύθυνσης:");
                System.out.println("Origin: " + attraction.origin);
                System.out.println("Destination: " + attraction.destination);
                System.out.println("Distance: " + attraction.distance);
                System.out.println("Duration: " + attraction.duration);
                System.out.println("Status: " + attraction.status);

                // Δημιουργία των ονομάτων από τις διευθύνσεις
                String originName = getAttractionNameFromAddress(attraction.origin, attractionsMap);
                String destinationName = getAttractionNameFromAddress(attraction.destination, attractionsMap);

                // Έλεγχος αν και τα δύο origin και destination ανήκουν στις περιοχές του χρήστη
                if (originName != null && userSelectedLocations.contains(originName) && 
                    destinationName != null && userSelectedLocations.contains(destinationName)) {
                    filteredDistances.add(attraction);
                }
            }
            reader.endArray();
        }

        // Εκτύπωση αποτελεσμάτων για επαλήθευση
        if (filteredDistances.isEmpty()) {
            System.out.println("Δεν βρέθηκαν φιλτραρισμένα αποτελέσματα.");
        } else {
            System.out.println("Φιλτραρισμένα αποτελέσματα:");
            for (AttractionDistance ad : filteredDistances) {
                System.out.println("Από: " + ad.origin + ", Προς: " + ad.destination);
                System.out.println("Απόσταση: " + ad.distance + ", Διάρκεια: " + ad.duration);
                System.out.println("------------");
            }
        }
    }

    // Μέθοδος για ανάγνωση ενός AttractionDistance αντικειμένου από JsonReader
    private static AttractionDistance readAttraction(JsonReader reader) throws Exception {
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
                    reader.skipValue(); // Αγνόησε άγνωστα πεδία
            }
        }
        reader.endObject();

        return new AttractionDistance(origin, destination, extractNumber(distance), extractNumber(duration), status);
    }

    // Μοντέλο για τα δεδομένα
    static class AttractionDistance {
        String origin;
        String destination;
        int distance;
        int duration;
        String status;

        AttractionDistance(String origin, String destination, int distance, int duration, String status) {
            this.origin = origin;
            this.destination = destination;
            this.distance = distance;
            this.duration = duration;
            this.status = status;
        }
    }

    public static int extractNumber(String input) {
        // Use a StringBuilder to accumulate numeric characters
        StringBuilder numberBuilder = new StringBuilder();

        // Loop through each character in the string
        for (char c : input.toCharArray()) {
            // Check if the character is a digit
            if (Character.isDigit(c)) {
                numberBuilder.append(c);
            }
        }

        // If no digits were found, throw an exception
        if (numberBuilder.length() == 0) {
            throw new IllegalArgumentException("No numbers found in the input string");
        }

        // Convert the accumulated digits to an integer
        return Integer.parseInt(numberBuilder.toString());
    }

    // Μέθοδος για να εξάγουμε το όνομα του αξιοθέατου από την πλήρη διεύθυνση
    private static String getAttractionNameFromAddress(String fullAddress, Map<String, String> attractionsMap) {
        for (Map.Entry<String, String> entry : attractionsMap.entrySet()) {
            if (entry.getValue().equals(fullAddress)) {
                return entry.getKey();
            }
        }
        return null; // Αν δεν βρεθεί, επιστρέφει null
    }
}


