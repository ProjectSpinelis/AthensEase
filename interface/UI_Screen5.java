import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UI_Screen5 extends Application {

    // Μεταβλητή για αποθήκευση του budget
    private int budget;

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(15); // Κάθετο layout με απόσταση 15px μεταξύ των στοιχείων
        vbox.setStyle("-fx-padding: 20;");

        // Ερώτηση
        Label question = new Label("What is your budget going to be? \nPlease enter an integer for the amount in euros.");
        question.setStyle("-fx-font-size: 14px;");

        // Πεδίο εισαγωγής
        TextField budgetInput = new TextField();
        budgetInput.setPromptText("Enter your budget (in euros)");

        // Κουμπί υποβολής
        Button submitButton = new Button("Submit");
        Label feedbackLabel = new Label(); // Για εμφάνιση feedback στον χρήστη

        // Μέθοδος για την υποβολή δεδομένων
        Runnable submitAction = () -> {
            String input = budgetInput.getText().trim(); // Παίρνει την είσοδο
            try {
                // Ελέγχει αν είναι ακέραιος
                budget = Integer.parseInt(input);
                feedbackLabel.setText("Budget set to: " + budget + " euros.");
                feedbackLabel.setStyle("-fx-text-fill: green;");
                System.out.println("Budget set to: " + budget); // Για debugging ή χρήση στη λογική
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid integer amount.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        };

        // Σύνδεση κουμπιού με τη μέθοδο υποβολής
        submitButton.setOnAction(e -> submitAction.run());

        // Σύνδεση πλήκτρου Enter με τη μέθοδο υποβολής
        budgetInput.setOnAction(e -> submitAction.run());

        // Δημιουργία κουμπιού Continue
        Button continueButton = new Button("Continue");

        // Κάνει το κείμενο του κουμπιού Continue bold
        continueButton.setStyle("-fx-font-weight: bold;");

        // Δημιουργία HBox για την τοποθέτηση του κουμπιού Continue κάτω δεξιά
        HBox continueBox = new HBox();
        continueBox.setStyle("-fx-alignment: center-right; -fx-padding: 10;");
        continueBox.getChildren().add(continueButton);

        // Προσθήκη των στοιχείων στο VBox
        vbox.getChildren().addAll(question, budgetInput, submitButton, feedbackLabel, continueBox);

        // Δημιουργία και ρύθμιση σκηνής
        Scene scene = new Scene(vbox, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Budget Input (Screen 5)");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
