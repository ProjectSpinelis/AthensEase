package pioneers;

import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProperListCreation {

    public static void main5(String[] args) throws Exception {

        // Χάρτης που αντιστοιχεί το κανονικό όνομα με την πλήρη διεύθυνση
        Map<String, String> attractionsMap = new HashMap<>();
attractionsMap.put("Acropolis Museum", "Dionysiou Areopagitou 15, Athina 117 42, Greece");
attractionsMap.put("Acropolis", "Akropolē, Athina 105 57, Greece");
attractionsMap.put("Parthenon", "Dionysiou Areopagitou 15, Athina 117 42, Greece");
attractionsMap.put("Erecthion & Theater of Dionysus", "Odos Hvris Onomasia, Athina 105 57, Greece");
attractionsMap.put("National Archaeological Museum", "28is Oktovriou 44, Athina 106 82, Greece");
attractionsMap.put("Temple of Poseidon", "M22F+3R, Laurium 195 00, Greece");
attractionsMap.put("Panathenaic Stadium (Kalimarmaro)", "Panathinaiko Stadio, Athina 116 36, Greece");
attractionsMap.put("Byzantine and Christian Museum", "Βυζαντινο Μουσειο - Ρηγιλλης, Athina 106 75, Greece");
attractionsMap.put("Temple of Olympian Zeus", "Akropolē, Athina 105 57, Greece");
attractionsMap.put("Zappeion Megaro", "Leof. Vasilissis Sofias 894, Athina 106 76, Greece");
attractionsMap.put("Archaeological Museum of Kerameikos", "Odos Hvris Onomasia, Athina 105 53, Greece");
attractionsMap.put("Library of Andrianus", "Ermou 148, Athina 105 53, Greece");
attractionsMap.put("Roman Agora of Athens", "Pl. Agiou Spiridonos 2, Athina 116 35, Greece");
attractionsMap.put("Goulandris Museum of Modern Art", "Valaoritou 7, Athina 106 71, Greece");
attractionsMap.put("Hellenic Air Force Museum", "Epaminonda 2, Athina 105 55, Greece");
attractionsMap.put("Museum of Eleftherios Venizelos", "Odos Hvris Onomasia, Acharnes 136 72, Greece");
attractionsMap.put("Museum of Folk Art and Tradition (Aggeliki Hatzimihalis)", "Aggelikis Chatzimichali 4, Athina 105 58, Greece");
attractionsMap.put("National Sculpture Gallery - Alexandros Soutsos Museum", "Aggelikis Chatzimichali 4, Athina 105 58, Greece");
attractionsMap.put("Benaki Museum of Islamic Art", "Areos 3, Athina 105 55, Greece");
attractionsMap.put("National Gallery", "Odos Hvris Onomasia, Athina 115 27, Greece");
attractionsMap.put("Benaki Museum - The Ghika Gallery", "Aggelikis Chatzimichali 4, Athina 105 58, Greece");
attractionsMap.put("National Library", "Leof. Vasilissis Sofias 894, Athina 106 76, Greece");
attractionsMap.put("Stavros Niarchos Foundation Cultural Center", "Stamouli, Spata Artemida 190 04, Greece");
attractionsMap.put("Museum of Greek Folk Musical Instruments", "Ag. Asomaton 24, Athina 105 53, Greece");
attractionsMap.put("Museum of Traditional Pottery", "Odos Hvris Onomasia, Athina 105 57, Greece");
attractionsMap.put("Ilias Lalaounis Jewelry Museum", "Odos Hvris Onomasia, Athina 105 57, Greece");


        // Επιλογές χρηστών (οι περιοχές που ενδιαφέρουν τον χρήστη)
        List<String> userSelectedLocations = List.of("Ακρόπολη", "Μετέωρα", "Δελφοί");

        // Δημιουργία λίστας για αποθήκευση των αποτελεσμάτων
        List<AttractionDistance> filteredDistances = new ArrayList<>();

        // Ανάγνωση του μεγάλου JSON αρχείου με JsonReader
        try (JsonReader reader = new JsonReader(new FileReader("CombinedJson.json"))) {
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

        return new AttractionDistance(origin, destination, distance, duration, status);
    }

    // Μοντέλο για τα δεδομένα
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


