package pioneers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
public class URLCreator {
    private String url;
    // Generates Url with information from ListCreator
    public void generateUrl() {
        ListCreator listcreator = new ListCreator();
        System.out.println(listcreator.getCoordinates());
        String origins = String.join("|", listcreator.getCoordinates());
        String destinations = String.join("|", listcreator.getCoordinates());
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
