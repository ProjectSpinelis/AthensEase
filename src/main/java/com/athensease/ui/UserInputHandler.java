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
        int days = 0;
        while (true) {
            try {
                System.out.println("How many days are you spending in Athens? Please enter an integer.");
                days = s.nextInt();
                s.nextLine(); // Clear buffer
                System.out.println("You entered " + days + " days. Is that correct?");
                System.out.println("Press 1 if correct, or 2 to re-enter.");
                int correct = s.nextInt();
                s.nextLine(); // Clear buffer
                if (correct == 1) {
                    break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please try again.");
                s.nextLine(); // Clear buffer
            }
        }
        return days;
    }

    public String gatherTrailhead() {
        System.out.println("Please enter your trailhead.");
        String trailhead = "";

        while (true) {
            trailhead = s.nextLine().trim(); // Read input and trim spaces
            if (!trailhead.isEmpty()) {
                break; // Exit loop if input is valid
            } else {
                System.err.println("Invalid input. Please enter a non-null, non-empty trailhead.");
            }
        }
        System.out.println("You entered: " + trailhead);
        return trailhead;
    }

    public int gatherBudget() {
        System.out.println("Are you travelling on a budget? Enter 1 for Yes or 2 for No.");
        int onAbudget = 0;
        while (true) {
            try {
                onAbudget = s.nextInt();
                s.nextLine(); // Clear buffer
                if (onAbudget == 1 || onAbudget == 2) {
                    break;
                } else {
                    System.err.println("Invalid input. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                s.nextLine(); // Clear buffer
            }
        }

        int budget = 0;
        if (onAbudget == 1) {
            System.out.println("What is your budget in euros? Please enter an integer.");
            while (true) {
                try {
                    budget = s.nextInt();
                    s.nextLine(); // Clear buffer
                    System.out.println("Your budget is " + budget + "€. Great! Let's continue.");
                    break;
                } catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please enter a valid integer.");
                    s.nextLine(); // Clear buffer
                }
            }
        } else {
            System.out.println("Great! Let's continue.");
            budget = 50000; // Default large budget
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
        while (true) {
            System.out.print("Enter the category numbers (e.g., 0,2,3): ");
            String input = s.nextLine().trim();
            try {
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
        SightsFileHandler sightsHandler = new SightsFileHandler();
        int counter = 1;

        for (int category : chosenCategories) {
            System.out.println("Category " + category + ":");
            availableSights = sightsHandler.filterSightsByCategory(category);
            for (Sight sight : availableSights) {
                System.out.println(counter + ". " + sight);
                counter++;
            }
        }

        List<Sight> selectedSights = new ArrayList<>();
        while (true) {
            System.out.print("Enter the sight numbers (e.g., 1,7,10,15,30): ");
            String input = s.nextLine().trim();
            try {
                List<Integer> sightNumbers = parseSelection(input, counter);
                for (int number : sightNumbers) {
                    selectedSights.add(availableSights.get(number - 1));
                }
                break; // Exit loop if input is valid
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                System.err.println("Invalid selection. Please try again.");
            }
        }
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
