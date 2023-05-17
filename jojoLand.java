import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class jojoLand {
    private final Graph map = new Graph();
    private final Stack<String> historyStack = new Stack<>();
    List<String> choices = new ArrayList<>();
    private String currentLocation = "Town Hall";
    private int dayCount = 0;

    public jojoLand() {
        map.addTown("Town Hall");
        map.addTown("Cafe Deux Magots");
        map.addTown("Jade Garden");
        map.addTown("Morioh Grand Hotel");
        map.addTown("Trattoria Trussardi");
        map.addTown("Green Dolphin Street Prison");
        map.addTown("Polnareff Land");
        map.addTown("San Giorgio Maggiore");
        map.addTown("Libeccio");
        map.addTown("Savage Garden");
        map.addTown("Joestar Mansion");
        map.addTown("DIO's Mansion");
        map.addTown("Angelo Rock");
        map.addTown("Vineyard");
        map.addRoad("Town Hall", "Cafe Deux Magots", 4);
        map.addRoad("Town Hall", "Jade Garden", 5);
        map.addRoad("Town Hall", "Morioh Grand Hotel", 5);
        map.addRoad("Morioh Grand Hotel", "Trattoria Trussardi", 6);
        map.addRoad("Morioh Grand Hotel", "Jade Garden", 3);
        map.addRoad("Trattoria Trussardi", "San Giorgio Maggiore", 3);
        map.addRoad("Trattoria Trussardi", "Green Dolphin Street Prison", 6);
        map.addRoad("Cafe Deux Magots", "Polnareff Land", 4);
        map.addRoad("Cafe Deux Magots", "Jade Garden", 3);
        map.addRoad("Cafe Deux Magots", "Savage Garden", 4);
        map.addRoad("Jade Garden", "San Giorgio Maggiore", 4);
        map.addRoad("Jade Garden", "Joestar Mansion", 2);
        map.addRoad("San Giorgio Maggiore", "Libeccio", 4);
        map.addRoad("Libeccio", "Green Dolphin Street Prison", 3);
        map.addRoad("Libeccio", "Joestar Mansion", 6);
        map.addRoad("Libeccio", "Vineyard", 6);
        map.addRoad("Libeccio", "DIO's Mansion", 2);
        map.addRoad("Green Dolphin Street Prison", "Angelo Rock", 2);
        map.addRoad("Polnareff Land", "Savage Garden", 6);
        map.addRoad("Savage Garden", "Joestar Mansion", 4);
        map.addRoad("Savage Garden", "Vineyard", 8);
        map.addRoad("Vineyard", "DIO's Mansion", 3);
        map.addRoad("DIO's Mansion", "Angelo Rock", 2);
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("It's Day 1 (Sunday) of our journey in JOJOLands!");
        boolean quit = false;
        while (!quit) {
            switch (currentLocation) {
                case "Town Hall":
                    choices = map.getChoices(currentLocation);
                    System.out.println("Current Location: " + currentLocation);
                    locationDisplay(choices);
                    System.out.println();
                    System.out.println("[2] Advance to Next Day");
                    System.out.println("[3] Save Game");
                    System.out.println("[4] Exit");
                    if (!historyStack.isEmpty()){
                        System.out.println("[5] Back (" + viewBack() + ")");
                    }
                    System.out.print("Select: ");
                    String input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            advanceToNextDay();
                            break;
                        case "3":
                            saveGame();
                            break;
                        case "4":
                            quit = true;
                            break;
                        case "5":
                            moveBack();
                            break;
                        default:
                            locationSelect(input, choices);
                            break;
                    }
                    break;
                case "Morioh Grand Hotel":
                    choices = map.getChoices(currentLocation);
                    System.out.println("Current Location: " + currentLocation);
                    locationDisplay(choices);
                    System.out.println();
                    System.out.println("[2] View Resident Information");
                    System.out.println("[3] The Hand");
                    System.out.println("[4] Back (" + viewBack() + ")");
                    System.out.println("[5] Back to Town Hall");
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            //View Resident Information
                            break;
                        case "3":
                            //The hand
                            break;
                        case "4":
                            moveBack();
                            break;
                        case "5":
                            backToTownHall();
                            break;
                        default:
                            locationSelect(input, choices);
                            break;
                    }
                    break;
                case "Angelo Rock":
                    choices = map.getChoices(currentLocation);
                    System.out.println("Current Location: " + currentLocation);
                    locationDisplay(choices);
                    System.out.println();
                    System.out.println("[2] View Resident Information");
                    System.out.println("[3] Red Hot Chili Pepper");
                    System.out.println("[4] Back (" + viewBack() + ")");
                    System.out.println("[5] Back to Town Hall");
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            //View Resident Information
                            break;
                        case "3":
                            //Red Hot Chili Pepper
                            break;
                        case "4":
                            moveBack();
                            break;
                        case "5":
                            backToTownHall();
                        default:
                            locationSelect(input, choices);
                            break;
                    }
                    break;
                case "Jade Garden":
                case "Cafe Deux Magots":
                case "Trattoria Trussardi":
                case "Liberrio":
                case "Savage Garden":
                    choices = map.getChoices(currentLocation);
                    System.out.println("Current Location: " + currentLocation);
                    locationDisplay(choices);
                    System.out.println();
                    System.out.println("[2] View Waiting List and Order Processing List");
                    System.out.println("[3] View Menu");
                    System.out.println("[4] View Sales Information");
                    System.out.println("[5] Milagro Man");
                    System.out.println("[6] Back (" + viewBack() + ")");
                    System.out.println("[7] Back to Town Hall");
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            //View Waiting List and Order Processing List duck dick sss
                            break;
                        case "3":
                            //View Menu
                            break;
                        case "4":
                            //View Sales Information
                            break;
                        case "5":
                            //Milagro Man
                            break;
                        case "6":
                            moveBack();
                            break;
                        case "7":
                            backToTownHall();
                            break;
                        default:
                            locationSelect(input, choices);
                            break;
                    }
                    break;
                case "Polnareff Land":
                case "Joestar Mansion":
                case "DIO's Mansion":
                case "Vineyard":
                case "San Giorgio Maggiore":
                case "Green Dolphin Street Prison":
                    choices = map.getChoices(currentLocation);
                    System.out.println("Current Location: " + currentLocation);
                    locationDisplay(choices);
                    System.out.println();
                    System.out.println("[2] View Resident Information");
                    System.out.println("[3] Back (" + viewBack() + ")");
                    System.out.println("[4] Back to Town Hall");
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            //View Resident Information
                            break;
                        case "3":
                            moveBack();
                            break;
                        case "4":
                            backToTownHall();
                            break;
                        default:
                            locationSelect(input, choices);
                            break;
                    }
                    break;
                default:
                    choices = map.getChoices(currentLocation);
                    System.out.println("Current Location: " + currentLocation);
                    locationDisplay(choices);
                    System.out.println();
                    System.out.println("[2] Back (" + viewBack() + ")");
                    System.out.println("[3] Back to Town Hall");
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            moveBack();
                            break;
                        case "3":
                            backToTownHall();
                            break;
                        default:
                            locationSelect(input, choices);
                            break;
                    }
                    break;
            }

        }
    }

    public void locationDisplay(List<String> choices) {
        System.out.println("[1] Move to:");
        for (int i = 0; i < choices.size(); i++) {
            System.out.print("    [" + (char) (65 + i) + "] " + choices.get(i) + " ");
        }
    }

    public void locationSelect(String input, List<String> choices) {
        if (input.length() == 2 && input.startsWith("1")) {
            int choiceIndex = (int) input.charAt(1) - 65;
            if (choiceIndex >= 0 && choiceIndex < choices.size()) {
                move(choices.get(choiceIndex));
            } else {
                System.out.println("Invalid Input");
            }
        } else {
            System.out.println("Invalid Input.");
        }
    }

    private void advanceToNextDay() {
        dayCount++;
        daysOfWeek();
        currentLocation = "Town Hall";
        historyStack.clear();
    }

    private void move(String destination) {
        historyStack.push(currentLocation);
        currentLocation = destination;
    }

    private void saveGame() {
        // implementation of saving game
        System.out.println("Game saved.");
    }

    public void daysOfWeek() {
        int dayIndex = (dayCount) % 7;
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        System.out.println("It's Day " + (dayCount + 1) + " (" + daysOfWeek[dayIndex] + ") of our journey in JOJOLands!");
    }

    public String viewBack() {
        if (!historyStack.isEmpty()) {
            return historyStack.peek();
        } else {
            return "No history location available.";
        }
    }

    public void moveBack() {
        if (!historyStack.isEmpty()) {
            currentLocation = historyStack.pop(); // Pop the latest history location from the stack
        }
        else {
            System.out.println("Invalid Input");
        }
    }

    public void backToTownHall(){
        move("Town Hall");
    }
}


