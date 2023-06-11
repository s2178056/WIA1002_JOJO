import java.io.IOException;
import java.util.*;


public class jojoLand {
    private static Graph map;
    public int mapChoice;
    protected static Stack<String> historyStack = new Stack<>();
    protected static Stack<String> forwardStack = new Stack<>();
    private List<String> choices = new ArrayList<>();
    private static String currentLocation = "Town Hall";
    private static int dayCount = 0;
    private HeavensDoor heavensDoor = new HeavensDoor();
    private PearlJam pearlJam = new PearlJam();
    private AssignFood assignFood= new AssignFood();
    public static int foodCount=0;


    public jojoLand(Graph map) {
        this.map = map;
    }

    public void run() throws IOException {
        Scanner sc = new Scanner(System.in);
        daysOfWeek();
        boolean quit = false;
        while (!quit) {
            AssignFood.main(new String[]{});
            switch (currentLocation) {
                case "Town Hall":
                    choices = map.getChoices(currentLocation);
                    System.out.println("Current Location: " + currentLocation);
                    locationDisplay(choices);
                    System.out.println();
                    System.out.println("[2] Advance to Next Day");
                    System.out.println("[3] Save Game");
                    System.out.println("[4] Exit");
                    if (!historyStack.isEmpty()) {
                        System.out.println("[5] Back (" + viewBack() + ")");
                    }
                    if (!forwardStack.empty()) {
                        System.out.println("[6] Forward (" + viewForward() + ")");
                    }
                    System.out.print("Select: ");
                    String input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            advanceToNextDay();
                            break;
                        case "3":
                            SaveLoad.saveGame(mapChoice, dayCount);
                            quit = true;
                            break;
                        case "4":
                            quit = true;
                            break;
                        case "5":
                            moveBack();
                            break;
                        case "6":
                            moveForward();
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
                    System.out.println("[4] Thus Spoke Rohan Kishibe");
                    System.out.println("[5] Back (" + viewBack() + ")");
                    System.out.println("[6] Back to Town Hall");
                    if (!forwardStack.empty()) {
                        System.out.println("[7] Forward (" + viewForward() + ")");
                    }
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            viewResidentInfo(currentLocation);
                            break;
                        case "3":
                            map.displayNonKruskal();
                            break;
                        case "4":
                            System.out.print("Enter the locations: ");
                            List<String> locations = new ArrayList<>();
                            locations.add("Jade Garden");
                            locations.add("Libeccio");
                            locations.add("Vineyard");
                            List<List<String>> allPaths = map.findAllPaths("Source", locations);
                            List<List<String>> topShortestPaths = map.findTopShortestPaths(allPaths);

                            map.displayShortestPaths(topShortestPaths);
                            break;
                        case "5":
                            moveBack();
                            break;
                        case "6":
                            backToTownHall();
                            break;
                        case "7":
                            moveForward();
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
                    if (!forwardStack.empty()) {
                        System.out.println("[6] Forward (" + viewForward() + ")");
                    }
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            viewResidentInfo(currentLocation);
                            break;
                        case "3":
                            map.displayKruskal();
                            break;
                        case "4":
                            moveBack();
                            break;
                        case "5":
                            backToTownHall();
                            break;
                        case "6":
                            moveForward();
                            break;
                        default:
                            locationSelect(input, choices);
                            break;
                    }
                    break;
                case "Green Dolphin Street Prison":
                    choices = map.getChoices(currentLocation);
                    System.out.println("Current Location: " + currentLocation);
                    locationDisplay(choices);
                    System.out.println();
                    System.out.println("[2] View Resident Information");
                    System.out.println("[3] Dirty Deeds Done Dirt Cheap");
                    System.out.println("[4] Back (" + viewBack() + ")");
                    System.out.println("[5] Back to Town Hall");
                    if (!forwardStack.empty()) {
                        System.out.println("[6] Forward (" + viewForward() + ")");
                    }
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            viewResidentInfo(currentLocation);
                            break;
                        case "3":
                            dirtyDeedsDoneDirtCheap();
                            break;
                        case "4":
                            moveBack();
                            break;
                        case "5":
                            backToTownHall();
                            break;
                        case "6":
                            moveForward();
                            break;
                        default:
                            locationSelect(input, choices);
                            break;
                    }
                    break;
                case "Jade Garden":
                case "Cafe Deux Magots":
                case "Trattoria Trussardi":
                case "Libeccio":
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
                    if (!forwardStack.empty()) {
                        System.out.println("[8] Forward (" + viewForward() + ")");
                    }
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            pearlJam(currentLocation);
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
                        case "8":
                            moveForward();
                            break;
                        default:
                            locationSelect(input, choices);
                            break;
                    }
                    break;
                case "Joestar Mansion":
                    choices = map.getChoices(currentLocation);
                    System.out.println("Current Location: " + currentLocation);
                    locationDisplay(choices);
                    System.out.println();
                    System.out.println("[2] View Resident Information");
                    System.out.println("[3] The Golden Spirit");
                    System.out.println("[4] Joestar Ancestor");
                    System.out.println("[5] Back (" + viewBack() + ")");
                    System.out.println("[6] Back to Town Hall");
                    if (!forwardStack.empty()) {
                        System.out.println("[7] Forward (" + viewForward() + ")");
                    }
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            viewResidentInfo(currentLocation);
                            break;
                        case"3":
                            TheGoldenSpirit.main(new String[]{});
                            break;
                        case "4":
                            joestarAncestor();
                            break;
                        case "5":
                            moveBack();
                            break;
                        case "6":
                            backToTownHall();
                            break;
                        case "7":
                            moveForward();
                            break;
                        default:
                            locationSelect(input, choices);
                            break;
                    }
                    break;

                case "Polnareff Land":
                case "DIO's Mansion":
                case "Vineyard":
                case "San Giorgio Maggiore":
                    choices = map.getChoices(currentLocation);
                    System.out.println("Current Location: " + currentLocation);
                    locationDisplay(choices);
                    System.out.println();
                    System.out.println("[2] View Resident Information");
                    System.out.println("[3] Back (" + viewBack() + ")");
                    System.out.println("[4] Back to Town Hall");
                    if (!forwardStack.empty()) {
                        System.out.println("[5] Forward (" + viewForward() + ")");
                    }
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            viewResidentInfo(currentLocation);
                            break;
                        case "3":
                            moveBack();
                            break;
                        case "4":
                            backToTownHall();
                            break;
                        case "5":
                            moveForward();
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
                    if (!forwardStack.empty()) {
                        System.out.println("[4] Forward (" + viewForward() + ")");
                    }
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
                        case "4":
                            moveForward();
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
        forwardStack.clear();
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

    public String viewForward(){
        if (!forwardStack.isEmpty()) {
            return forwardStack.peek();
        } else {
            return "No history location available.";
        }
    }

    public void moveForward() {
        if (!forwardStack.isEmpty()) {
            currentLocation = forwardStack.pop(); // Pop the latest forward location from the stack
        }
        else {
            System.out.println("Invalid Input");
        }
    }

    public void moveBack() {
        forwardStack.push(currentLocation);
        if (!historyStack.isEmpty()) {
            currentLocation = historyStack.pop(); // Pop the latest history location from the stack
        }
        else {
            System.out.println("Invalid Input");
        }
    }

    public void backToTownHall(){
        move("Town Hall");
        forwardStack.clear();
    }

    public void viewResidentInfo(String currentLocation){
        Scanner sc = new Scanner(System.in);
        boolean exit=false;
        heavensDoor.printResidentInfo(HeavensDoor.residentsArray(currentLocation), currentLocation);
        while(!exit) {
            System.out.println("[1] View Resident's Profile");
            System.out.println("[2] Sort");
            System.out.println("[3] Exit");
            System.out.print("Select: ");
            String input=sc.nextLine();
            System.out.println("=================================================================================");
            switch (input){
                case "1":
                    System.out.print("Enter the resident's name: ");
                    String name=sc.nextLine();
                    heavensDoor.printResidentProfile(name);
                    PearlJam.assignRF(name);
                    break;
                case "2":
                    System.out.print("Enter the sorting order: ");
                    try {
                        String sortingOrder = sc.nextLine();
                        heavensDoor.sort(sortingOrder, currentLocation);
                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid Input");
                    }
                    break;
                case "3":
                    exit=true;
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        }
    }

    public void pearlJam(String currentLocation){
        List<PearlJam.Resident> residents = pearlJam.loadResidents();
        // Randomly assign a food and restaurant to each resident
        Map<String, List<PearlJam.Resident>> waitingLists = pearlJam.assignFoodAndRestaurant(residents,dayCount);
        // Prompt for restaurant selection
        List<PearlJam.Resident> waitingList = pearlJam.getRestaurantWaitingList(waitingLists, currentLocation);
        List<PearlJam.Resident> orderProcessingList = pearlJam.generateOrderProcessingList(waitingList, currentLocation);
        pearlJam.printWaitingList(waitingList, currentLocation);
        pearlJam.printOrderProcessingList(orderProcessingList, currentLocation);
    }

    public void dirtyDeedsDoneDirtCheap() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the source: ");
            String source = sc.nextLine();
            System.out.println("Enter the destination: ");
            String destination = sc.nextLine();
            List<List<String>> allPaths = map.findAllPaths(source, destination);
            List<List<String>> shortestPaths = map.findTopShortestPaths(allPaths);

            if (shortestPaths.isEmpty()) {
                System.out.println("No path found to the destination.");
            } else {
                map.displayShortestPaths(shortestPaths);
            }
        } catch (NullPointerException e) {
            System.out.println("Invalid Input");
        }
    }

    public static int getDayCount(){
        return dayCount;
    }

    public static String getCurrentLocation(){
        return currentLocation;
    }

    public static Stack<String> getHistoryStack(){
        Stack<String> saveHistoryStack = historyStack;
        return saveHistoryStack;
    }

    public void setDayCount(int dayCount) {
        jojoLand.dayCount = dayCount;
    }

    public void joestarAncestor(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of Joestar to print his/her ancestor: ");
        String joestarName=sc.nextLine();
        TheGoldenSpirit.printAncestor(joestarName);
    }
}






