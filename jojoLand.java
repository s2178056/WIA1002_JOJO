import java.util.*;

public class jojoLand {
    private Graph map;
    protected static Stack<String> historyStack = new Stack<>();
    protected static Stack<String> forwardStack= new Stack<>();
    private List<String> choices = new ArrayList<>();
    private static String currentLocation = "Town Hall";
    private static int dayCount = 0;
    private HeavensDoor heavensDoor=new HeavensDoor();
    private PearlJam pearlJam=new PearlJam();


    public jojoLand(Graph map) {
       this.map=map;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        daysOfWeek();
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
                    if(!forwardStack.empty()){
                        System.out.println("[6] Forward ("+viewForward()+")");
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
                    System.out.println("[4] Back (" + viewBack() + ")");
                    System.out.println("[5] Back to Town Hall");
                    if(!forwardStack.empty()){
                        System.out.println("[6] Forward ("+viewForward()+")");
                    }
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            viewResidentInfo(currentLocation);
                            break;
                        case "3":
                            System.out.println("Enter the locations: ");
                            Scanner scanner = new Scanner(System.in);
                            String[] locations = scanner.nextLine().split(", ");
                            List<String> shortestPath = map.findShortestPathThroughALl(Arrays.asList(locations));
                            if (shortestPath.isEmpty()) {
                                System.out.println("No path found passing through all the locations.");
                            } else {
                                int distance = map.calculatePathDistance(shortestPath);
                                System.out.println("Shortest Path:");
                                for (int i = 0; i < shortestPath.size() - 1; i++) {
                                    System.out.print(shortestPath.get(i) + " > ");
                                }
                                System.out.print(shortestPath.get(shortestPath.size() - 1));
                                System.out.printf(" (%d km)%n", distance);
                            }
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
                case "Angelo Rock":
                    choices = map.getChoices(currentLocation);
                    System.out.println("Current Location: " + currentLocation);
                    locationDisplay(choices);
                    System.out.println();
                    System.out.println("[2] View Resident Information");
                    System.out.println("[3] Red Hot Chili Pepper");
                    System.out.println("[4] Back (" + viewBack() + ")");
                    System.out.println("[5] Back to Town Hall");
                    if(!forwardStack.empty()){
                        System.out.println("[6] Forward ("+viewForward()+")");
                    }
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            viewResidentInfo(currentLocation);
                            break;
                        case "3":
                            //Red Hot Chili Pepper
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
                    if(!forwardStack.empty()){
                        System.out.println("[6] Forward ("+viewForward()+")");
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
                    if(!forwardStack.empty()){
                        System.out.println("[8] Forward ("+viewForward()+")");
                    }
                    System.out.print("Select: ");
                    input = sc.nextLine();
                    System.out.println("=================================================================================");
                    switch (input) {
                        case "2":
                            List<PearlJam.Resident> residents = pearlJam.loadResidents();
                            // Randomly assign a food and restaurant to each resident
                            Map<String, List<PearlJam.Resident>> waitingLists = pearlJam.assignFoodAndRestaurant(residents);
                            // Prompt for restaurant selection
                            List<PearlJam.Resident> waitingList = pearlJam.getWaitingList(waitingLists, currentLocation);
                            List<PearlJam.Resident> orderProcessingList = pearlJam.generateOrderProcessingList(waitingList, currentLocation);
                            pearlJam.printWaitingList(waitingList, currentLocation);
                            pearlJam.printOrderProcessingList(orderProcessingList, currentLocation);
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
                case "Polnareff Land":
                case "Joestar Mansion":
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
                    if(!forwardStack.empty()){
                        System.out.println("[5] Forward ("+viewForward()+")");
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
                        case"5":
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
                    if(!forwardStack.empty()){
                        System.out.println("[4] Forward ("+viewForward()+")");
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
}





