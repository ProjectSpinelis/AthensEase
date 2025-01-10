package com.athensease.ui;


import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class WebViewer {

    private Stage stage;

    public WebViewer(Stage stage) {
        this.stage = stage;
    }

    public void displayMap(String htmlContent) {
        try {
            // Write the HTML content to a temporary file
            File tempFile = File.createTempFile("map_", ".html");
            tempFile.deleteOnExit(); // Ensure it gets deleted when the application exits
            java.nio.file.Files.write(tempFile.toPath(), htmlContent.getBytes());

            // Load the temporary HTML file into a WebView
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();

            // Allow external scripts to run in WebView
            webEngine.setJavaScriptEnabled(true);

            // Load the HTML content from the temporary file
            webEngine.load(tempFile.toURI().toString());

            // Set up the scene with the WebView
            BorderPane root = new BorderPane();
            root.setCenter(webView);
            Scene scene = new Scene(root, 1024, 768);

            // Display the scene in the current stage
            stage.setTitle("Map Viewer");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

