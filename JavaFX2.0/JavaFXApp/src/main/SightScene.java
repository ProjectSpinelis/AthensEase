import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.athensease.sights.Sight;

import java.awt.Desktop;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SightScene extends Scene {
    public SightScene(BorderPane root) {
        super(root, 600, 600);
        //  δημιουργια του vbox και του scrollPane
        VBox vbox = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToHeight(true);
        root.setCenter(scrollPane);
        root.setPadding(new Insets(15));

        // Για κάθε sight 
        for (Sight sight : SightsFileHandler.getSights()) {
            boolean selectedCat = selectedCategories[Integer.getInteger(sight.getCategory()) - 1];
            if (!sight.isMustSee() && !selectedCat) {
                continue;
            }
            final CheckBox checkbox = new CheckBox(sight.getName());
            // Ορισμός του listener για το checkbox
            checkbox.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                        sight.setIsSelected(new_val);
                    });
            Label priceLabel = new Label("Price: " + sight.getPrice());

            Hyperlink link = new Hyperlink(sight.getLink());
            link.setText("trip advisor");
            //ορισμος του event handler για το link
            link.setOnAction(e -> {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI(sight.getLink()));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            //καθε sight ειναι ενα HBox
            HBox hbox = new HBox(checkbox, priceLabel, link);
            vbox.getChildren().add(hbox);
        }

        MyButton clearAll = new MyButton("Clear All");
        clearAll.setOnAction(e -> {
            vbox.getChildren().forEach(node -> {
                CheckBox cb = (CheckBox) ((HBox) node).getChildren().get(0);
                cb.setSelected(false);
            });
        });
        MyButton nextButton = new MyButton("Next");
        nextButton.setOnAction(e -> {
           // ΠΡΕΠΕΙ ΝΑ ΠΑΕΙ ΣΤΗΝ ΕΠΟΜΕΝΗ ΣΕΛΙΔΑ
        });

        MyButton selectAll = new MyButton("Select All");
        selectAll.setOnAction(e -> {
            vbox.getChildren().forEach(node -> {
                CheckBox cb = (CheckBox) ((HBox) node).getChildren().get(0);
                cb.setSelected(true);
            });
        });
        HBox lastHbox = new HBox(20);
        lastHbox.getChildren().addAll(clearAll, selectAll, nextButton);
        vbox.getChildren().add(lastHbox);
    }
}
