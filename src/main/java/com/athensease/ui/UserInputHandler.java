package com.athensease.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.athensease.sights.Sight;
import com.athensease.sights.SightsFileHandler;

import java.util.ArrayList;
import java.util.List;

public class UserInputHandler {

    Scanner s = new Scanner(System.in);
    
    public int gatherDuration() {
        //flag variables
        boolean validAnswer = false; 
        int correct = 0;
        int days = 0;
        //do as long as they want to change sth
        do {
            System.out.println("How many days are you spending in Athens? Please enter an integer.");
            //do as long as it's not valid
            do {
                try {
                    days = s.nextInt();
                    validAnswer = true;
                } 
                catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please try again.");
                    s.nextLine();
                }
            } while (validAnswer == false) ;

            validAnswer = false;
            do {
                System.out.println("You entered " + days + " days for the duration of your holiday. Is that correct?");
                System.out.println("Press 1. if it is correct or 2. if you would like to change it.");
                try {
                    correct = s.nextInt();
                    if (correct == 1 || correct == 2) {
                        validAnswer = true;
                    } else {
                        System.out.println("Invalid input.");
                    }
                } 
                catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please try again.");
                    s.nextLine();
                }
                
            } while (!validAnswer) ;
            //assumption as to correct's value
            assert (correct == 1) | (correct == 2); 
            //needs protection from exceptions but it's gonna be fixed with interface
        } while (correct != 1);
        return days;
    }

    public String gatherTrailhead() {
        System.out.println("Please enter your trailhead.");
        
        String trailhead = "";
        
        // Loop until the user provides a valid non-null, non-empty string
        while (true) {
            try {
                trailhead = s.nextLine().trim(); // Read the input and trim leading/trailing spaces
                
                // Check if the input is not null or empty
                if (trailhead != null && !trailhead.isEmpty()) {
                    break; // Exit the loop if input is valid
                } else {
                    System.err.println("Invalid input. Please enter a non-null, non-empty trailhead.");
                }
            } catch (InputMismatchException e) {
                // Handle the InputMismatchException if the user enters invalid input type
                System.err.println("Invalid input type. Please enter a valid trailhead.");
                s.nextLine(); // Clear the invalid input from the scanner buffer
            }
        }
        System.out.println("You entered: " + trailhead);
        return trailhead;
    }


    public int gatherBudget() {
        System.out.println("Are you travelling on a budget? Enter 1. if Yes or 2. if No.");
        boolean validAnswer = false;
        int onAbudget = 0;
        do {
            try {
                onAbudget = s.nextInt();
                if (onAbudget == 1 || onAbudget == 2) {
                    validAnswer = true;
                } else {
                    System.err.println("Invalid input. Please enter 1. or 2.");
                    s.nextLine();
                }
            } 
            catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                s.nextLine();
            }
        } while (!validAnswer && onAbudget != 1 & onAbudget != 2 ) ;
        
        int budget = 0;
        if (onAbudget == 1) {
            System.out.println("What is your budget in euros? Please enter an integer.");
            //can add feature of entering other currencies and we turn it to euros
            validAnswer = false;
            do {
                try {
                    budget = s.nextInt();
                    validAnswer = true;
                } 
                catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please try again.");
                    s.nextLine();
                }
            } while (!validAnswer) ;
            System.out.println("Your budget is " + budget + "€ . Great! Let's continue.");
            //System.out.println("Do you want to change it? Enter 1. if Yes or 2. if No.");
            //asks if it's ok to continue or go back to change sth using a do-while loop or back-button using interface
        } else {
            System.out.println("Great! Let's continue.");
            //keep a large budget so that we can compare anyway and not write a million lines of code
            budget = 50000;
        }
        return budget;
    }

    public List<Integer> chooseCategories() {
        
        System.out.println("It's time for you to choose the categories of attractions you prefer to see.");
        System.out.println("Choose by typing their numbers separated by commas:");
        System.out.println("0. Must See");
        System.out.println("1. History");
        System.out.println("2. Art");
        System.out.println("3. Nature & Outdoors");

        List<Integer> selectedCategories = new ArrayList<>();

         // Get and validate input
         while (true) {
             System.out.print("Enter the category numbers (e.g., 0,2,3): ");
             String input = s.nextLine().trim();
             try {
                 // Parse input into a list of integers
                 selectedCategories = parseSelection(input, 4);
                 break; // Exit loop if input is valid
             } catch (IllegalArgumentException e) {
                 System.err.println(e.getMessage());
             }
         }
         return selectedCategories;
    }


    public List<Sight> chooseSights(List<Integer> chosenCategories) {

        List<Sight> availableSights = new ArrayList<>();
        // Display available sights
        SightsFileHandler sightsHandler = new SightsFileHandler();
        int counter = 1;
        for (int i : chosenCategories) {
           System.out.println("Category " + i + ":");
           // Get available sights for given category 
           availableSights = sightsHandler.filterSightsByCategory(i);
           for (Sight sight : availableSights) {
               System.out.println(counter + ". " + sight);
               counter++;
           }
        }

        List<Sight> selectedSights = new ArrayList<>();

         // Get and validate input
         List<Integer> sightNumbers = new ArrayList<>();
         while (true) {
             System.out.print("Enter the sight numbers (e.g., 1,7,10,15,30): ");
             String input = s.nextLine().trim();
             try {
                 // Parse input into a list of integers
                 sightNumbers = parseSelection(input, counter);
                 break; // Exit loop if input is valid
             } catch (IllegalArgumentException e) {
                 System.err.println(e.getMessage());
             }
         }
         selectedSights = availableSights;
         return selectedSights;
    }

    private List<Integer> parseSelection(String input, int maxCategoryIndex) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty. Please try again.");
        }
 
        String[] tokens = input.split(",");
        List<Integer> choices = new ArrayList<>();
 
        for (String token : tokens) {
            try {
                int category = Integer.parseInt(token.trim());
                if (category < 0 || category >= maxCategoryIndex) {
                    throw new IllegalArgumentException("Invalid category number: " + category);
                }
                if (!choices.contains(category)) {
                    choices.add(category); // Avoid duplicates
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid input format. Use only numbers separated by commas.");
            }
        }
 
        return choices;
    }
}
