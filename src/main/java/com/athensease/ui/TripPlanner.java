package com.athensease.ui;

import javafx.application.Application;

/**
 * The TripPlanner class is the entry point for launching the Trip Planner application.
 * It invokes the launch method of the JavaFX Application class, which initializes and starts the application.
 */
public class TripPlanner {

    /**
     * The main method that serves as the entry point for the Trip Planner application.
     * It launches the JavaFX application by calling the launch method of the App class.
     * 
     * @param args Command-line arguments passed to the application at startup.
     */
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}


