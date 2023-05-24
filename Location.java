import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class Location {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        jojoLand jojoLand = null;

        System.out.println("Welcome, to the fantastical realm of JOJOLands.");
        System.out.println("[1] Start Game");
        System.out.println("[2] Load Game");
        System.out.println("[3] Exit");
        System.out.print("Select: ");
        int menuChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (menuChoice == 1) {
            int mapChoice = displayMapSelectionMenu(scanner);
            switch (mapChoice) {
                case 1:
                    jojoLand = new jojoLand(new DefaultMap().getMap());
                    break;
                case 2:
                    jojoLand = new jojoLand(new ParallelMap().getMap());
                    break;
                case 3:
                    jojoLand = new jojoLand(new AlternateMap().getMap());
                    break;
                default:
                    System.out.println("Invalid map choice. Exiting...");
                    System.exit(0);
            }
            jojoLand.run();
        } else if (menuChoice == 2) {
            System.out.print("Enter the path of your save file: ");
            String savePath = scanner.nextLine();
            // Load the game using the provided save file path
            // jojoLand = loadGame(savePath);
        } else if (menuChoice == 3) {
            System.out.println("Exiting...");
            System.exit(0);
        } else {
            System.out.println("Invalid menu choice. Exiting...");
            System.exit(0);
        }

        scanner.close();
    }


    public static int displayMapSelectionMenu(Scanner scanner) {
        System.out.println("Select a map:");
        System.out.println("[1] Default Map");
        System.out.println("[2] Parallel Map");
        System.out.println("[3] Alternate Map");
        System.out.print("Select: ");
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return input;
    }

    public void saveGame(String savePath) {
        try {
            FileWriter fileWriter = new FileWriter(savePath);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Save the game data to the file
            printWriter.println(jojoLand.getDayCount());
            printWriter.println(jojoLand.getCurrentLocation());

            // Save the history stack
            printWriter.println(jojoLand.getHistoryStack().size());
            for (String location : jojoLand.getHistoryStack()) {
                printWriter.println(location);
            }

            // Save any additional game data you have

            printWriter.close();
            System.out.println("Game saved to: " + savePath);
        } catch (IOException e) {
            System.out.println("Failed to save the game. Error: " + e.getMessage());
        }
    }
//    public static jojoLand loadGame(String savePath) {
//        jojoLand game = null;
//
//        try {
//            // Code to load the game data from the file
//            FileReader fileReader = new FileReader(savePath);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//            // Read the game data and assign it to the appropriate variables
//            int dayCount = Integer.parseInt(bufferedReader.readLine());
//            String currentLocation = bufferedReader.readLine();
//
//            // Read the history stack and restore its contents
//            int historyStackSize = Integer.parseInt(bufferedReader.readLine());
//            Stack<String> historyStack = new Stack<>();
//            for (int i = 0; i < historyStackSize; i++) {
//                String location = bufferedReader.readLine();
//                historyStack.push(location);
//            }
//
//            // Create the game object and set its data
//            game = new jojoLand(new DefaultMap().getMap());
//            game.dayCount = dayCount;
//            game.currentLocation = currentLocation;
//            game.historyStack = historyStack;
//
//            // Load any additional game data you have
//
//            bufferedReader.close();
//            System.out.println("Game loaded from: " + savePath);
//        } catch (IOException e) {
//            System.out.println("Failed to load the game. Error: " + e.getMessage());
//        }
//
//        return game;
//    }
}