import java.io.*;
import java.util.Scanner;

public class Run {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        jojoLand jojoLand = null;
        System.out.println("Welcome, to the fantastical realm of JOJOLands.");
        System.out.println("[1] Start Game");
        System.out.println("[2] Load Game");
        System.out.println("[3] Exit");
        System.out.print("Select: ");
        String menuChoice = scanner.nextLine();
        System.out.println("=".repeat(70));
        if (menuChoice.equalsIgnoreCase("1")) {
            String mapChoice = displayMapSelectionMenu(scanner);
            switch (mapChoice) {
                case "1":
                    jojoLand = new jojoLand(new DefaultMap().getMap());
                    jojoLand.mapChoice=1;
                    break;
                case "2":
                    jojoLand = new jojoLand(new ParallelMap().getMap());
                    jojoLand.mapChoice=2;
                    break;
                case "3":
                    jojoLand = new jojoLand(new AlternateMap().getMap());
                    jojoLand.mapChoice=3;
                    break;
                default:
                    System.out.println("Invalid map choice. Exiting...");
                    System.exit(0);
            }
            jojoLand.run();
        } else if (menuChoice.equalsIgnoreCase("2")) {
            SaveLoad.loadGame();
        } else if (menuChoice.equalsIgnoreCase("3")) {
            System.out.println("Exiting...");
            System.exit(0);
        } else {
            System.out.println("Invalid menu choice. Exiting...");
            System.exit(0);
        }
        scanner.close();
    }


    public static String displayMapSelectionMenu(Scanner scanner) {
        System.out.println("Select a map:");
        System.out.println("[1] Default Map");
        System.out.println("[2] Parallel Map");
        System.out.println("[3] Alternate Map");
        System.out.print("Select: ");
        String input=scanner.nextLine();
        System.out.println("=================================================================================");
        return input;
    }

}