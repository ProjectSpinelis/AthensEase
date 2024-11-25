//import gr.aueb.dmst.AthensEase;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class QuestionnaireNew {

    //public Questionnaire(){};
    Scanner s = new Scanner(System.in);
    
    public int gatherHolidayDuration() {
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


    public void gatherTrailheads() {
        boolean validAnswer = false;
        int numTrail = 0;
        do {
            do {
                //e.g Peiraus on Day 1, Petralona on Day 5...
                System.out.println("How many trailheads are you going to have? Enter 1. if it is one or 2. if it is more.");
                try {
                    numTrail = s.nextInt(); //number of trailheads
                    s.nextLine();
                    validAnswer = true;
                } 
                catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please try again.");
                    s.nextLine();
                }
            } while (!validAnswer) ;
    
            //needs to ask if the trailhead = finish point through interface
            assert (numTrail == 1) | (numTrail == 2);
            if (numTrail == 1) {
                //needs protection from exceptions (is it in ath) but it's gonna be fixed with interface
                //do as long as it's not valid
                String trailhead = gatherONETrailhead();
                System.out.println("You entered: " + trailhead);
                System.out.println("You have completed entering the trailhead successfully! ");
            } else if (numTrail == 2) { //numTrail == 2
                //create list for each user's trailheads and days they change
                ArrayList <Integer> changeDays = gatherChangeDays();
                ArrayList <String> trailheads = gatherMANYTrailheads(changeDays);
                ArrayList <String> finishPointsList = gatherFinishPoints(trailheads);
                System.out.println("You have completed entering the changing days, trailheads and the finish points successfully! ");
            //asks if it's ok to continue or go back to change sth using a do-while loop or back-button using interface
            } else {
                System.out.println("Invalid input. Please enter 1. or 2.");
            }
        } while (numTrail != 1 && numTrail != 2);
    }

    public String gatherONETrailhead() {
        System.out.println("Please enter your trailhead.");
        //flag variable
        boolean validAnswer = false;
        String oneTrailhead = "";
        do {
            try {
                oneTrailhead  = s.nextLine();
                //trailheads.add(oneTrailhead);
                validAnswer = true;
            } 
            catch (InputMismatchException e) {
                System.err.println("Invalid input. Please try again.");
                s.nextLine();
            }
        } while (!validAnswer) ;
        return oneTrailhead;
    }
        
    public ArrayList <Integer> gatherChangeDays() {
        ArrayList <Integer> changeDays = new ArrayList<>();
        int times = 0;
        System.out.println("How many times are you going to change trailheads during your holiday.?");
        System.out.println("Tip: Try to count the days that you are going to start the route from a different point!");
        //do as long as it's not valid
        boolean validAnswer = false;
        do {
            try {
                times = s.nextInt();
                validAnswer = true;
            } 
            catch (InputMismatchException e) {
                System.err.println("Invalid input. Please try again.");
                s.nextLine();
            }
        } while (!validAnswer);
        //first time they go to a trailhead is day 1
        changeDays.add(1);
        //e.g Airport on Day 1
        for (int i=0; i<times; i++ ) {
            //i=1 bc trailheads(0) is filled
            System.out.println("On what day of your holiday are you changing trailheads? For example, Day 3 and Day 7. Please enter an integer representing the day.");
            validAnswer = false;
            int x = 8;
            do {
                try {
                    x = (s.nextInt()); 
                    if (x != 1) {
                        changeDays.add(x);
                        validAnswer = true;
                    } else {
                        System.out.println("We have already added Day 1 for you! Please continue with the rest.");
                    }
                } 
                catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please enter an integer representing the day you change trailhead.");
                    s.nextLine();
                }
            } while (!validAnswer) ;  
        }
        System.out.println(changeDays);
        return changeDays;
    }

    public ArrayList <String> gatherMANYTrailheads(ArrayList <Integer> changeDays) {
        //needs protection from exceptions but it's gonna be fixed with interface
        ArrayList<String> trailheads = new ArrayList<>();
        int ChDaysSize = changeDays.size();
        s.nextLine();
        System.out.println("What is your trailhead on Day 1?");
        boolean validAnswer = false;
        do {
            try {
                trailheads.add(s.nextLine());
                validAnswer = true;
            } 
            catch (InputMismatchException e) {
                System.err.println("Invalid input. Please try again.");
                s.nextLine();
            }
        } while (!validAnswer) ; 

        
        for (int i = 1 ; i < ChDaysSize ; i++) {
            System.out.println("Where is your trailhead going to be on day " + changeDays.get(i) + " of your holiday?");
            validAnswer = false;
            do {
                try {
                    trailheads.add(s.nextLine());
                    validAnswer = true;
                } 
                catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please try again.");
                    s.nextLine();
                }
            } while (!validAnswer) ;
              
        }
        System.out.println(trailheads);
        return trailheads;
    }

    public ArrayList <String> gatherFinishPoints(ArrayList <String> trailheads) {
        ArrayList<String> finishPointsList = new ArrayList<>();
        int x = trailheads.size();
        int same = 8;
        boolean validAnswer1, validAnswer2;
        String finishPoint = "";
        for (int i=0; i<x ; i++) {
            validAnswer1 = false;
            do {
                System.out.println("For trailhead: "+ trailheads.get(i)+ " are you going to have the same finish point? If Yes press 1. If No press 2.");
                try {
                    same = s.nextInt();
                    if (same == 1) {
                        validAnswer1 = true;
                        finishPointsList.add(trailheads.get(i));
                        System.out.println("Added finish point (same as trailhead): " + trailheads.get(i)); // Debug
                    } else if (same == 2) {
                        validAnswer1 = true;
                        validAnswer2 = false;
                        s.nextLine();
                        do {
                            System.out.println("What is your finish point going to be for trailhead "+ trailheads.get(i) + " ?");
                            try {
                                finishPoint = s.nextLine();
                                validAnswer2 = true;
                                System.out.println("Captured finish point: " + finishPoint); // Debug
                            } 
                            catch (InputMismatchException e) {
                                System.err.println("Invalid input. Please try again.");
                                s.nextLine();
                            }
                         } while (!validAnswer2) ;
                         System.out.println("For trailhead: "+ trailheads.get(i)+ " you are going to have finish point: " + finishPoint);
                         finishPointsList.add(finishPoint);
                    } else {
                        System.err.println("Invalid input. Please enter 1. or 2.");
                    }
                }
                catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please try again.");
                    s.nextLine();
                }
            } while (!validAnswer1) ;
        }
        //could calculate the cost of moving from s.p. of day x to s.p. of day x+1
        System.out.println(finishPointsList);
        return finishPointsList;
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

    public ArrayList<Integer> chooseCategories() {
        
        System.out.println("It's time for you to choose the categories of attractions you prefer to see.");
        System.out.println("Here are the categories: \n 1.Archaeology/History \n 2.Art \n 3.Nature \n 4.Science");
        //do it with only History bc it takes TOO MUCH TIME and with interface it's gonna be too easy
        ArrayList<Integer> ListOfCategories = new ArrayList<>();
        ArrayList<Integer> ListOfAttractions = new ArrayList<>();
        //loop needed to check if they want to choose more categories
        boolean validAnswer = false;
        int choice = 0;
            do {
                try {
                    System.out.println("Please enter an integer for a category you like to see the attractions.");
                    choice = s.nextInt();
                    if (choice != 1 && choice != 2 && choice !=3 && choice != 4) {
                        System.out.println("Invalid input. Please choose between 1-4.");
                        s.nextLine();
                    } else {
                        validAnswer = true;
                    }
                } 
                catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please try again.");
                    s.nextLine();
                }
            } while (!validAnswer) ;
        ListOfCategories.add(choice);
        displayAttractions(ListOfCategories);
        //flag
        int stop = 8;
        //int sumTickets = 0;
        do {
            validAnswer = false;
            do {
                try {
                    //needs to check if they have already checked an attraction
                    System.out.println("Please enter an integer for an attraction you want to see.");
                    choice = (s.nextInt());
                    if (choice !=1 && choice != 2 && choice !=3) {
                        //bc we have only added 3 attractions to cat 1
                        System.out.println("Invalid input. Please enter an integer between 1-3.");
                        s.nextLine();
                        //choice = (s.nextInt());
                    } else {
                        validAnswer = true;
                    }
                } 
                catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please try again.");
                    s.nextLine();
                }
            } while (!validAnswer) ;
            ListOfAttractions.add(choice);

            validAnswer = false;
            do {
                try {
                    System.out.println("Do you want to see more attractions? Please enter 1 if Yes or 0 if No.");
                    stop = s.nextInt();
                    if (stop == 1 || stop == 0){
                        validAnswer = true;
                    } else {
                        System.err.println("Invalid input. Please try again.");
                    }
                } 
                catch (InputMismatchException e) {
                    System.err.println("Invalid input. Please try again.");
                    s.nextLine();
                }
            } while (!validAnswer) ;
            
        } while (stop != 0); //or attractions have finished
        //class enum needed do it prints Attractions instead of List positions
        System.out.println("You have chosen: " + ListOfAttractions);
        return ListOfAttractions;
    } 

    //we need ARRAYS GAMW
    public void displayAttractions(ArrayList<Integer> choiceOfCategories) {
        int x = choiceOfCategories.size();
        for (int i=0; i<x; i++) {
            if (choiceOfCategories.get(i) == 1) {
                System.out.println("Here are the attractions for 1.Archaeology/History.");
                System.out.println("1.Acropolis  15€ \n2.Acropolis Museum    12€ \n3.Ancient Agora   0€");
            
            }
        }
    }
    //needs to sum all the tickets
}