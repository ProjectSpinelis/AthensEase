package com.athensease;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ListCreator {

   private static List<Map<String, Object>> attractions = new ArrayList<>();
    
    public void createList() {
        // Δημιουργία λίστας με αξιοθέατα
        

        // Προσθήκη δεδομένων για κάθε αξιοθέατο
        Map<String, Object> attraction1 = new HashMap<>();
        attraction1.put("name", "Acropolis museum");
        attraction1.put("location", "37.9686567332862, 23.72852410682988");
        attraction1.put("price", 15);
        attraction1.put("visit time", 120);
        attraction1.put("category", 1);
        attraction1.put("mustsee", "true");
        attractions.add(attraction1);

        Map<String, Object> attraction2 = new HashMap<>();
        attraction2.put("name", "Acropolis, Parthenon, Erecthion & Theater of Dionysus");
        attraction2.put("location", "37.97168916843448, 23.726695138046782");
        attraction2.put("price", 15);
        attraction2.put("visit time", 105);
        attraction2.put("category", 1);
        attraction2.put("mustsee", "true");
        attractions.add(attraction2);

        Map<String, Object> attraction3 = new HashMap<>();
        attraction3.put("name", "National Archeological Museum");
        attraction3.put("location", "37.989166618755476, 23.73278135339155");
        attraction3.put("price",9 );
        attraction3.put("visit time",105 );
        attraction3.put("category", 1);
        attraction3.put("mustsee", "true");
        attractions.add(attraction3);

        Map<String, Object> attraction4 = new HashMap<>();
        attraction4.put("name", "Temple of Poseidon");
        attraction4.put("location", "37.650349880189076, 24.02452232268601");
        attraction4.put("price", 7.5);
        attraction4.put("visit time", 52.5);
        attraction4.put("category", 1);
        attraction4.put("mustsee", "false");
        attractions.add(attraction4);

        Map<String, Object> attraction5 = new HashMap<>();
        attraction5.put("name", "Panathenaic Σtadium(Kalimarmaro)");
        attraction5.put("location", "37.96848641735808, 23.74111179571859");
        attraction5.put("price", 10);
        attraction5.put("visit time", 52.5);
        attraction5.put("category", 1);
        attraction5.put("mustsee", "true");
        attractions.add(attraction5);

        Map<String, Object> attraction6 = new HashMap<>();
        attraction6.put("name", "Temple of Hephaestus");
        attraction6.put("location", "37.97578103122385, 23.72137699386706");
        attraction6.put("price", 10);
        attraction6.put("visit time", 75);
        attraction6.put("category", 1);
        attraction6.put("mustsee", "false");
        attractions.add(attraction5);

        Map<String, Object> attraction7 = new HashMap<>();
        attraction7.put("name", "Byzantine and Christian Museum");
        attraction7.put("location", "37.97552480371863, 23.744584580374983");
        attraction7.put("price", 9);
        attraction7.put("visit time", 105);
        attraction7.put("category", 1);
        attraction7.put("mustsee", "false");
        attractions.add(attraction7);

        Map<String, Object> attraction8 = new HashMap<>();
        attraction8.put("name", "Temple of Olympian Zeus");
        attraction8.put("location", "37.96952833694226, 23.73305708037478");
        attraction8.put("price", 6);
        attraction8.put("visit time", 40);
        attraction8.put("category", 1);
        attraction8.put("mustsee", "false");
        attractions.add(attraction8);

        Map<String, Object> attraction9 = new HashMap<>();
        attraction9.put("name","Zappeion Megaro");
        attraction9.put("location", "37.971742673381975, 23.736675759210872");
        attraction9.put("price", 0);
        attraction9.put("visit time", 45);
        attraction9.put("category", 1);
        attraction9.put("mustsee", "false");
        attractions.add(attraction9);

        Map<String, Object> attraction10 = new HashMap<>();
        attraction10.put("name","Archaeological Museum of Kerameikos");
        attraction10.put("location", "37.97817145424204, 23.71721107640699");
        attraction10.put("price", 6);
        attraction10.put("visit time", 75);
        attraction10.put("category", 1);
        attraction10.put("mustsee", "false");
        attractions.add(attraction10);

        Map<String, Object> attraction11 = new HashMap<>();
        attraction11.put("name","Library of Andrianus");
        attraction11.put("location", "37.97569658869841, 23.726030396060338");
        attraction11.put("price", 4.5);
        attraction11.put("visit time", 45);
        attraction11.put("category", 1);
        attraction11.put("mustsee", "false");
        attractions.add(attraction11);

        Map<String, Object> attraction12 = new HashMap<>();
        attraction12.put("name","Roman Agora of Athens");
        attraction12.put("location", "37.97443513463531, 23.726062909552088");
        attraction12.put("price", 6);
        attraction12.put("visit time", 30);
        attraction12.put("category", 1);
        attraction12.put("mustsee", "true");
        attractions.add(attraction12);

        Map<String, Object> attraction13 = new HashMap<>();
        attraction13.put("name","Goulandris Museum of Modern Art");
        attraction13.put("location", "37.96994469552465, 23.74264195561404");
        attraction13.put("price", 12);
        attraction13.put("visit time", 105);
        attraction13.put("category", 2);
        attraction13.put("mustsee", "false");
        attractions.add(attraction13);

        Map<String, Object> attraction14 = new HashMap<>();
        attraction14.put("name","Hellenic Air Force Museum");
        attraction14.put("location", "38.10498637617618, 23.777800967230487");
        attraction14.put("price", 0);
        attraction14.put("visit time", 75);
        attraction14.put("category", 2);
        attraction14.put("mustsee", "false");
        attractions.add(attraction14);

        Map<String, Object> attraction15 = new HashMap<>();
        attraction15.put("name","Museum of Eleftherios Venizelos");
        attraction15.put("location", "37.94819478368696, 23.945879068305512");
        attraction15.put("price", 0);
        attraction15.put("visit time", 45);
        attraction15.put("category", 2);
        attraction15.put("mustsee", "false");
        attractions.add(attraction15);

        Map<String, Object> attraction16 = new HashMap<>();
        attraction16.put("name","Museum of Folk Art and Tradition <<Aggeliki Hatzimihalis>>");
        attraction16.put("location", "37.972771631433936, 23.73119538179638");
        attraction16.put("price", 0);
        attraction16.put("visit time",40);
        attraction16.put("category", 2);
        attraction16.put("mustsee", "false");
        attractions.add(attraction16);
    
        Map<String, Object> attraction17 = new HashMap<>();
        attraction17.put("name","National Sculpture Gallery - Alexandros Soutsos Museum");
        attraction17.put("location", "37.98717363462195, 23.776906684655764");
        attraction17.put("price", 7.5);
        attraction17.put("visit time", 90);
        attraction17.put("category", 2);
        attraction17.put("mustsee", "false");
        attractions.add(attraction17);

        Map<String, Object> attraction18 = new HashMap<>();
        attraction18.put("name","Benaki Museum of Islamic Art");
        attraction18.put("location", "37.979359093373084, 23.72036903068839");
        attraction18.put("price", 6);
        attraction18.put("visit time", 105);
        attraction18.put("category", 2);
        attraction18.put("mustsee", "false");
        attractions.add(attraction18);

        Map<String, Object> attraction19 = new HashMap<>();
        attraction19.put("name", "National Gallery");
        attraction19.put("location", "37.97641415390103, 23.74903624360797");
        attraction19.put("price", 9);
        attraction19.put("visit time", 180);
        attraction19.put("category", 2);
        attraction19.put("mustsee", "true");
        attractions.add(attraction19);

        Map<String, Object> attraction20 = new HashMap<>();
        attraction20.put("name", "Benaki Museum -The Ghika Gallery");
        attraction20.put("location", "37.97792746362709, 23.736344701301405");
        attraction20.put("price", 9);
        attraction20.put("visit time", 90);
        attraction20.put("category", 2);
        attraction20.put("mustsee", "false");
        attractions.add(attraction20);
        
        Map<String, Object> attraction21 = new HashMap<>();
        attraction21.put("name", "National Library");
        attraction21.put("location", "37.98175468670241, 23.732832438622903");
        attraction21.put("price", 0);
        attraction21.put("visit time", 45);
        attraction21.put("category", 2);
        attraction21.put("mustsee", "false");
        attractions.add(attraction21);

        Map<String, Object> attraction22 = new HashMap<>();
        attraction22.put("name", "Stauros Niarchos Foundation Cultural Center");
        attraction22.put("location", "37.94007636716364, 23.692227484419195");
        attraction22.put("price", 0);
        attraction22.put("visit time", 75);
        attraction22.put("category", 2);
        attraction22.put("mustsee", "false");
        attractions.add(attraction22);

        Map<String, Object> attraction23 = new HashMap<>();
        attraction23.put("name", "Museum of Greek Folk Musical Instruments");
        attraction23.put("location", "37.974596847301484, 23.727638051880025");
        attraction23.put("price", 5);
        attraction23.put("visit time", 50);
        attraction23.put("category", 2);
        attraction23.put("mustsee", "false");
        attractions.add(attraction23);

        Map<String, Object> attraction24 = new HashMap<>();
        attraction24.put("name", "Museum of Traditional Pottery");
        attraction24.put("location", "37.978632810151055, 23.720274038388332");
        attraction24.put("price", 4);
        attraction24.put("visit time", 45);
        attraction23.put("category", 2);
        attraction24.put("mustsee", "false");
        attractions.add(attraction24);

        Map<String, Object> attraction25 = new HashMap<>();
        attraction25.put("name", "Ilias Lalaounis Jewelry Museum");
        attraction25.put("location", "37.969451511139816, 23.72668151140426");
        attraction25.put("price", 5);
        attraction25.put("visit time", 75);
        attraction25.put("category", 2);
        attraction25.put("mustsee", "false");
        attractions.add(attraction25);

        Map<String, Object> attraction26 = new HashMap<>();
        attraction26.put("name", "Anafiotika");
        attraction26.put("location", "37.97257597841888, 23.728123207437143");
        attraction26.put("price", 0);
        attraction26.put("visit time", 45);
        attraction26.put("category", 2);
        attraction26.put("mustsee", "true");
        attractions.add(attraction26);

        Map<String, Object> attraction27 = new HashMap<>();
        attraction27.put("name", "National Garden");
        attraction27.put("location", "37.97289466063027, 23.737628010700018");
        attraction27.put("price", 0);
        attraction27.put("visit time", 40);
        attraction27.put("category", 3);
        attraction27.put("mustsee", "true");
        attractions.add(attraction27);

        Map<String, Object> attraction28 = new HashMap<>();
        attraction28.put("name", "Mount Lycabettus");
        attraction28.put("location", "37.982630499920724, 23.74305643697589");
        attraction28.put("price", 0);
        attraction28.put("visit time", 45);
        attraction28.put("category", 3);
        attraction28.put("mustsee", "true");
        attractions.add(attraction28);

        Map<String, Object> attraction29 = new HashMap<>();
        attraction29.put("name", "Filopappou Hill");
        attraction29.put("location", "37.96738818537706, 23.721681126748322");
        attraction29.put("price", 0);
        attraction29.put("visit time", 75);
        attraction29.put("category", 3);
        attraction29.put("mustsee", "false");
        attractions.add(attraction29);

        Map<String, Object> attraction30 = new HashMap<>();
        attraction30.put("name", "Plaka");
        attraction30.put("location", "37.97285449516104, 23.73033687569889");
        attraction30.put("price", 0);
        attraction30.put("visit time", 75);
        attraction30.put("category", 3);
        attraction30.put("mustsee", "true");
        attractions.add(attraction30);

        Map<String, Object> attraction31 = new HashMap<>();
        attraction31.put("name", "Change of The Guard Ceremony");
        attraction31.put("location", "37.975474505489395, 23.736257842629655");
        attraction31.put("price", 0);
        attraction31.put("visit time", 15);
        attraction31.put("category", 3);
        attraction31.put("mustsee", "false");
        attractions.add(attraction31);

        Map<String, Object> attraction32 = new HashMap<>();
        attraction32.put("name", "Syntagma Square");
        attraction32.put("location", "37.975710178676046, 23.734905442759306");
        attraction32.put("price", 0);
        attraction32.put("visit time", 15);
        attraction32.put("category", 3);
        attraction32.put("mustsee", "false");
        attractions.add(attraction32);
        
        Map<String, Object> attraction33 = new HashMap<>();
        attraction33.put("name","Holy Monastery of Kaisariani");
        attraction33.put("location", "37.96099877393549, 23.79817592489568");
        attraction33.put("price", 0);
        attraction33.put("visit time", 45);
        attraction33.put("category", 3);
        attraction33.put("mustsee", "false");
        attractions.add(attraction33);
    
    }
    public static List<Map<String, Object>> getAttractions(){
        return attractions;
    }

    // Δημιουργία λίστας μόνο με συντεταγμένες
    public List<String> getCoordinates() {  
        List<String> coordinates = new ArrayList<>();

        for (Map<String, Object> map : ListCreator.getAttractions()) {
            String coordinate = (String) map.get("location");
            coordinates.add(coordinate);
        }
        return coordinates;
        
    
    }

    public static int value = 4;

    public static List<Map<String, Object>> filterAttractionsByCategories(List<Map<String, Object>> attractions, List<Integer> categories) {
        return attractions.stream()
                .filter(attraction -> {
                    // Έλεγχος αν το "mustsee" είναι true
                    Object mustsee = attraction.get("mustsee");
                    boolean isMustsee = mustsee instanceof Boolean && (Boolean) mustsee;

                    // Έλεγχος αν η κατηγορία είναι στη λίστα
                    Object category = attraction.get("category");
                    boolean inCategories = category instanceof Integer && categories.contains((Integer) category);

                    // Επιστροφή true αν και οι δύο συνθήκες ισχύουν
                    return isMustsee && inCategories;
                })
                .collect(Collectors.toList());
    }
        
}  
