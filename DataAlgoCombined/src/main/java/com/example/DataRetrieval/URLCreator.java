package com.example.DataRetrieval;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URLCreator {
    private String url;

    public void generateUrl() {
        // Δημιουργία λίστας με αξιοθέατα
        List<Map<String, Object>> attractions = new ArrayList<>();

        // Προσθήκη δεδομένων για κάθε αξιοθέατο
        Map<String, Object> attraction1 = new HashMap<>();
        attraction1.put("name", "Ακρόπολη");
        attraction1.put("location", "37.971974089432265,23.72611695268535");
        attraction1.put("price", 20);
        attractions.add(attraction1);

        Map<String, Object> attraction2 = new HashMap<>();
        attraction2.put("name", "Παλάτι Κνωσού");
        attraction2.put("location", "35.30103910371449,25.164165200528323");
        attraction2.put("price", 15);
        attractions.add(attraction2);

        Map<String, Object> attraction3 = new HashMap<>();
        attraction3.put("name", "Λευκός Πύργος");
        attraction3.put("location", "40.62661720836193,22.94857587865187");
        attraction3.put("price", 10);
        attractions.add(attraction3);

        Map<String, Object> attraction4 = new HashMap<>();
        attraction4.put("name", "Μετέωρα");
        attraction4.put("location", "39.721844658226665,21.630643239709507");
        attraction4.put("price", 12);
        attractions.add(attraction4);

        Map<String, Object> attraction5 = new HashMap<>();
        attraction5.put("name", "Δελφοί");
        attraction5.put("location", "38.483745699564246,22.49808719205221");
        attraction5.put("price", 18);
        attractions.add(attraction5);

        // Δημιουργία λίστας μόνο με συντεταγμένες
        List<String> coordinates = new ArrayList<>();

        for (Map<String, Object> map : attractions) {
            String coordinate = (String) map.get("location");
            coordinates.add(coordinate);
        }

        System.out.println(coordinates);
        String origins = String.join("|", coordinates);
        String destinations = String.join("|", coordinates);

        //encoding
                origins = URLEncoder.encode(origins, StandardCharsets.UTF_8);
        destinations = URLEncoder.encode(destinations, StandardCharsets.UTF_8);


        // Δημιουργία του URL
        this.url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origins +
                "&destinations=" + destinations +
                "&key=AIzaSyDits1cNQEJkWEK_FSF_crxVCeV-brw3rE";

        
    }

    public String getUrl() {
        return this.url;
    }

    public static void main(String[] args) {
        // Δημιουργία instance της JsonCreator
        URLCreator creator = new URLCreator();
        creator.generateUrl();
        System.out.println("Final URL: " + creator.getUrl());
    }
}
