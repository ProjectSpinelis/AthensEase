import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UI_Screen5 extends Application {

    private boolean isBudgetYesSelected;
    private int budget;

    // Constructor
    public UI_Screen5(boolean isBudgetYesSelected) {
        this.isBudgetYesSelected = isBudgetYesSelected;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(15); // Κάθετο layout με απόσταση 15px μεταξύ των στοιχείων
        vbox.setStyle("-fx-padding: 20;");

        // Ερώτηση για το budget
        Label question = new Label("What is your budget going to be? \nPlease enter an integer for the amount in euros.");
        question.setStyle("-fx-font-size: 14px;");

        // Πεδίο εισαγωγής για το budget
        TextField budgetInput = new TextField();
        budgetInput.setPromptText("Enter your budget (in euros)");

        // Ετικέτα για ανατροφοδότηση (feedback)
        Label feedbackLabel = new Label();

        // Κουμπί Submit για την καταχώρηση του budget
        Button submitButton = new Button("Submit");

        // Λογική για την υποβολή του budget
        submitButton.setOnAction(e -> {
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
        });

        // Σύνδεση πλήκτρου Enter με τη μέθοδο υποβολής
        budgetInput.setOnAction(e -> submitButton.fire());

        // Δημιουργία κουμπιού Continue
        Button continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-weight: bold;");

        // Λογική για το Continue
        continueButton.setOnAction(e -> {
            if (isBudgetYesSelected) {
                //System.out.println("Proceeding to the next screen...");
                // Εδώ μπορείς να προχωρήσεις στην επόμενη οθόνη ή να κλείσεις το παράθυρο
                primaryStage.close(); // Κλείνουμε το παράθυρο
            } else {
                // Εμφάνιση του alert για ολοκλήρωση της διαδικασίας
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Completion");
                alert.setHeaderText(null);
                alert.setContentText("Your process has been successfully completed!");
                alert.showAndWait(); // Περιμένει να πατήσει ο χρήστης ΟΚ
                primaryStage.close(); // Κλείνουμε το παράθυρο μετά το ΟΚ
            }
        });

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

}
