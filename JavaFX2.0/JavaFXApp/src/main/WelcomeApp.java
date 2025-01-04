import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class WelcomeApp extends Application {
    // Πίνακας με τις κατηγορίες που μπορεί να επιλέξει ο χρήστης
    boolean[] selectedCategories = {false, false, false};
    // Αντικείμενο για την ανάγνωση των αξιοθέατων από το αρχείο
    SightsFileHandler sightsHanlder = new SightsFileHandler();

    @Override
    public void start(Stage primaryStage) {
        // Δημιουργία του κουμπιού
        MyButton startButton = new MyButton("Let's START :)"); // Χρήση του custom κουμπιού


        // Δράση όταν πατηθεί το κουμπί
        startButton.setOnAction(e -> {
            System.out.println("The Button was pressed"); // Δείγμα λειτουργίας
        });

        // Δημιουργία των Labels
        Label welcomeLabel = new Label("Welcome to our application!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill:rgb(26, 65, 9);");

        Label descriptionLabel = new Label("Explore Athens like never before in 3 thrilling steps:\n\n" +
        "1. Unlock your adventure: Share your trip details, address, and budget.\n\n" +
        "2. Craft your journey: Select the sights you can’t miss.\n\n" +
        "3. Maximize the magic: Get the ultimate route tailored just for you.");

        descriptionLabel.setStyle("-fx-font-size: 16px; -fx-text-fill:rgb(26, 65, 9);");

        // Δημιουργία VBox για να τοποθετηθούν τα Labels το ένα κάτω από το άλλο
        VBox vbox = new VBox(20, welcomeLabel, descriptionLabel); // 20px διάστημα μεταξύ των Labels
        vbox.setStyle("-fx-alignment: top-center;");

        // Δημιουργία Region για το κενό διάστημα μεταξύ του κειμένου και του κουμπιού
        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS); // Ορίζει το spacer να αναπτυχθεί και να γεμίσει το χώρο

        // Δημιουργία VBox για την τοποθέτηση του κουμπιού πιο κοντά στο κείμενο
        VBox mainVBox = new VBox(10, vbox, spacer, startButton);  // 10px διάστημα για μικρότερη απόσταση
        mainVBox.setStyle("-fx-alignment: center;");  // Τοποθετούμε τα στοιχεία κεντρικά

        // Ρυθμίσεις για το layout
        StackPane root = new StackPane();  // Χρησιμοποιούμε μόνο το StackPane χωρίς να τοποθετούμε άμεσα τα στοιχεία

        // Προσθέτουμε το VBox με το κουμπί και το κείμενο στο StackPane
        root.getChildren().add(mainVBox);

        root.setStyle("-fx-background-color: rgb(248, 248, 248);"); // Γκρι φόντο

        // Δημιουργία της σκηνής
        Scene scene = new Scene(root, 600, 600);  // Μέγεθος της σκηνής σε 600x600

        // Ρυθμίσεις του παραθύρου
        primaryStage.setTitle("Welcome !");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
