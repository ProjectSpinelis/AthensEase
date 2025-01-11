package com.athensease.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.athensease.sights.Sight;
import com.athensease.sights.SightsFileHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles user input for configuring trip details, including trip duration, trailheads,
 * budget, preferred categories, and chosen sights.
 */
public class UserInputHandler {

    Scanner s = new Scanner(System.in);
    private int daysOfTheTrip = 0; // Default value
    private List<Integer> TrailHeadDays= new ArrayList<>();

    /**
     * Gathers the duration of the trip in days from the user.
     *
     * @return The number of days for the trip.
     */
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
        this.daysOfTheTrip = days;
        return days;
    }

    /**
     * Collects trailhead locations and associated trailhead change days from the user.
     *
     * @return A list of trailhead locations.
     */
    public List<String> gatherTrailheads() {
        System.out.println("Please enter your trailhead."); //adds first trailhead
        List<String> trailhead = new ArrayList<>();
        
        while (true) {
            trailhead.add(s.nextLine().trim()); // Read input and trim spaces
            if (trailhead.size() > 0) {
                break; // Exit loop if input is valid
            } else {
                System.err.println("Invalid input. Please enter a non-null, non-empty trailhead.");
            }
        }

        System.out.println("Do you have additional trailhead? Enter 1 for Yes or 2 for No.");
        int answer1 = s.nextInt();
        int dayOfFirstChange = 0; // Default value
        s.nextLine(); // Clear buffer
        if (answer1 == 1) {
            System.out.println("Please enter your additional trailhead"); // adds second trailhead 
            while (true) {
                trailhead.add(s.nextLine().trim()); // Read input and trim spaces
                if (trailhead.size() > 1) {
                    break; // Exit loop if input is valid
                } else {
                    System.err.println("Invalid input. Please enter a non-null, non-empty trailhead.");
                }
            }
            System.out.println("Please give the day of the trip that you change trailhead: ");
            dayOfFirstChange = s.nextInt();
        }

        int answer2 = 0; // Default value
        int dayOfSecondChange = 0; // Default value
        if (answer1 == 1) {
            System.out.println("Do you have additional trailhead? Enter 1 for Yes or 2 for No.");
            answer2 = s.nextInt();
            s.nextLine(); // Clear buffer
            if (answer2 == 1) {
                System.out.println("Please enter your additional trailhead"); // adds third trailhead 
                while (true) {
                    trailhead.add(s.nextLine().trim()); // Read input and trim spaces
                    if (trailhead.size() > 2) {
                        break; // Exit loop if input is valid
                    } else {
                        System.err.println("Invalid input. Please enter a non-null, non-empty trailhead.");
                    }
                }
                System.out.println("Please give the day of the trip that you change trailhead: ");
                dayOfSecondChange = s.nextInt();
            }
        }

        List<Integer> daysOfChange = new ArrayList<>();
        if (answer1 == 1) {
            for (int i = 0; i < dayOfFirstChange - 1; i++) {
                daysOfChange.add(1);
            }
            daysOfChange.add(2);
            if (answer2 == 1) {
                for (int i = dayOfFirstChange; i < dayOfSecondChange - 1; i++) {
                    daysOfChange.add(2);
                }
                daysOfChange.add(3);
                for (int i = dayOfSecondChange; i < this.daysOfTheTrip - 1; i++) {
                    daysOfChange.add(3);
                }
            } else {
                for (int i = dayOfFirstChange; i < this.daysOfTheTrip - 1; i++) {
                    daysOfChange.add(2);
                }
            }
        } else {
            for (int i = 0; i < this.daysOfTheTrip - 1; i++) {
                daysOfChange.add(1);
            }
        }

        this.TrailHeadDays = daysOfChange;

        return trailhead;
    }

    /**
     * Retrieves the trailhead days for the trip.
     *
     * @return List of trailhead days.
     */
    public List<Integer> getTrailHeadDays() {
        return TrailHeadDays;
    }

    /**
     * Sets the trailhead days for the trip.
     *
     * @param trailHeadDays List of trailhead days.
     */
    public void setTrailHeadDays(List<Integer> trailHeadDays) {
        this.TrailHeadDays = trailHeadDays;
    }

    /**
     * Retrieves the number of days for the trip.
     *
     * @return Number of days for the trip.
     */
    public int getDaysOfTheTrip() {
        return daysOfTheTrip;
    }

    /**
     * Sets the number of days for the trip.
     *
     * @param daysOfTheTrip Number of days for the trip.
     */
    public void setDaysOfTheTrip(int daysOfTheTrip) {
        this.daysOfTheTrip = daysOfTheTrip;
    }

    /**
     * Gathers the budget for the trip from the user.
     *
     * @return The budget in euros.
     */
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

    /**
     * Allows the user to select preferred categories of attractions.
     *
     * @return List of selected category indices.
     */
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

    /**
     * Allows the user to select specific sights from available categories.
     *
     * @param chosenCategories List of chosen categories.
     * @return List of selected sights.
     */
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

    /**
     * Parses user input for category or sight selection.
     *
     * @param input User input as a comma-separated string.
     * @param maxCategoryIndex Maximum index allowed for selection.
     * @return List of selected indices.
     */
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

    /**
     * Allows the user to choose their optimization objective.
     *
     * @return True if the objective is to minimize total travel distance, false for minimizing duration.
     */
    public boolean chooseObjective() {
        System.out.println("Choose your objective:");
        System.out.println("1. Minimize total travel distance");
        System.out.println("2. Minimize total duration time");
        System.out.println("Enter 1 or 2:");
        int choice = 0;
        while (true) {
            try {
                choice = s.nextInt();
                s.nextLine(); // Clear buffer
                if (choice == 1) {
                    return true;
                } else if (choice == 2) {
                    return false;
                } else {
                    System.err.println("Invalid input. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                s.nextLine(); // Clear buffer
            }
        }
    }
}
