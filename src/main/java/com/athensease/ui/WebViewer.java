package com.athensease.ui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * The WebViewer class is responsible for displaying HTML content (such as a map) in a WebView within a JavaFX window.
 * It allows the user to view dynamic content rendered from HTML passed into the displayMap method.
 */
public class WebViewer {

    private Stage stage;

    /**
     * Constructs a WebViewer object with the given stage for displaying the map content.
     * 
     * @param stage The stage where the WebView will be displayed.
     */
    public WebViewer(Stage stage) {
        this.stage = stage;
    }

    /**
     * Displays an HTML map (or any HTML content) inside a WebView in a new scene on the provided stage.
     * The HTML content is temporarily written to a file, which is then loaded into the WebView.
     *
     * @param htmlContent The HTML content to be displayed in the WebView.
     */
    public void displayMap(String htmlContent) {
        try {
            // Write the HTML content to a temporary file
            File tempFile = File.createTempFile("map_", ".html");
            tempFile.deleteOnExit(); // Ensure the file is deleted when the application exits
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
            e.printStackTrace();  // Handle any IO exceptions that may occur while writing or reading files
        }
    }
}
