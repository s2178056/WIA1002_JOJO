import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class MilagroMan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean quit = false;
        String foodModify = "";
        double foodPriceModify = 0.00;
        int startDay = 0;
        int endDay = 0;
        while (!quit) {
            System.out.println("Restaurant: " + jojoLand.getCurrentLocation() + " (Milagro Man Mode)");
            System.out.println("[1] Modify Food Prices");
            System.out.println("[2] View Sales Information");
            System.out.println("[3] Exit Milagro Man");
            System.out.print("Select: ");
            String input = sc.nextLine();
            switch (input) {
                case "1":
                    System.out.print("Enter Food Name:");
                    foodModify = sc.nextLine();
                    System.out.print("Enter new Price: $");
                    foodPriceModify = sc.nextDouble();
                    System.out.print("Enter Start Day: ");
                    startDay = sc.nextInt();
                    System.out.print("Enter End Day: ");
                    endDay = sc.nextInt();
                    sc.nextLine();
                    System.out.println("==============================================================================");
                    break;
                case "2":
                    milagroMoodyBlue(foodModify, foodPriceModify, startDay, endDay);
                    System.out.println("=================================================================================");
                    break;
                case "3":
                    System.out.println("==============================================================================");
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid Input");
                    System.out.println("==============================================================================");
                    break;
            }
        }
    }

    public static void milagroMoodyBlue(String foodModify, double foodPriceModify, int modifyStartDay, int modifyEndDay) {
        Scanner scanner = new Scanner(System.in);
        int linesToSkip = 2;
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("foodHistory.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (linesToSkip > 0) {
                    linesToSkip--;
                    continue;
                }
                String[] values = line.split(",");
                records.add(values);
            }
        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("==============================================================================");
            System.out.println("Restaurant: " + jojoLand.getCurrentLocation());
            System.out.println("Sales Information");
            System.out.println("[1] View Sales");
            System.out.println("[2] View Aggregated Information");
            System.out.println("    [A] Minimum Sales");
            System.out.println("    [B] Maximum Sales");
            System.out.println("    [C] Top k Highest Sales");
            System.out.println("    [D] Total and Average Sales");
            System.out.println("[3] Exit");
            System.out.print("Select: ");
            String selection2 = input.nextLine();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            switch (selection2) {
                case "1" -> {
                    Map<String, Integer> foodQuantity = new HashMap<>();
                    Map<String, Double> foodPrice = new HashMap<>();
                    Map<String, Integer> modifyFoodQuantity = new HashMap<>();
                    Map<String, Double> modifyFoodPrice = new HashMap<>();
                    ArrayList<String> foodMenu= new ArrayList<>();
                    System.out.print("Enter the day: ");
                    int selectedDay = scanner.nextInt();
                    scanner.nextLine();
                    if (selectedDay > jojoLand.getDayCount() + 1) {
                        System.out.println("Invalid Day");
                        break;
                    }
                    System.out.println("==============================================================================");
                    System.out.println("Restaurant: " + jojoLand.getCurrentLocation());
                    System.out.println("Day " + selectedDay + " Sales");
                    System.out.println("+-------------------------------------+------------+-----------------+");
                    System.out.println("|Food                                 |  Quantity  |    Total Price  |");
                    System.out.println("+-------------------------------------+------------+-----------------+");
                    double totalDaySales = 0.0;
                    for (String[] record : records) {
                        String restaurant = record[4];
                        int day = Integer.parseInt(record[0]);
                        String foodName = record[5];
                        double price = Double.parseDouble(record[6]);
                        if (selectedDay >= modifyStartDay && selectedDay <= modifyEndDay) {
                            if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == selectedDay) {
                                if(!foodMenu.contains(foodName)){
                                    foodMenu.add(foodName);
                                }
                                if (foodName.equalsIgnoreCase(foodModify)) {
                                    modifyFoodQuantity.put(foodName, modifyFoodQuantity.getOrDefault(foodName, 0) + 1);
                                    modifyFoodPrice.put(foodName, foodPriceModify);
                                    totalDaySales += foodPriceModify;
                                } else {
                                    foodQuantity.put(foodName, foodQuantity.getOrDefault(foodName, 0) + 1);
                                    foodPrice.put(foodName, price);
                                    totalDaySales += price;
                                }
                            }
                        } else {
                            if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == selectedDay) {
                                if(!foodMenu.contains(foodName)){
                                    foodMenu.add(foodName);
                                }
                                foodQuantity.put(foodName, foodQuantity.getOrDefault(foodName, 0) + 1);
                                foodPrice.put(foodName, price);
                                totalDaySales += price;
                            }
                        }
                    }
                    for (String food : foodMenu) {
                        double totalPrice = foodPrice.getOrDefault(food,0.00) * foodQuantity.getOrDefault(food,0) + (modifyFoodPrice.getOrDefault(food,0.00) * modifyFoodQuantity.getOrDefault(food,0));
                        System.out.printf("|%-37s|  %-10d|  $%-14s|\n", food, foodQuantity.getOrDefault(food,0)+modifyFoodQuantity.getOrDefault(food,0), decimalFormat.format(totalPrice));
                    }
                    System.out.println("+--------------------------------------------------------------------+");
                    System.out.printf("|Total Sales                                       |  $%-14s|\n", decimalFormat.format(totalDaySales));
                    System.out.println("+--------------------------------------------------------------------+");

                }
                case "2A" -> {
                    System.out.print("Enter the start day: ");
                    int startDay = scanner.nextInt();
                    System.out.print("Enter the end day: ");
                    int endDay = scanner.nextInt();
                    scanner.nextLine();
                    if (endDay > jojoLand.getDayCount() + 1 || startDay <= 0) {
                        System.out.println("Invalid Day");
                        break;
                    }
                    Map<Integer, Double> sales = new HashMap<>();
                    Map<String, Integer> foodQuantity = new HashMap<>();
                    Map<String, Double> foodPrice = new HashMap<>();
                    Map<String, Integer> modifyFoodQuantity = new HashMap<>();
                    Map<String, Double> modifyFoodPrice = new HashMap<>();
                    ArrayList<String> foodMenu= new ArrayList<>();
                    double minSales = Double.MAX_VALUE;
                    int minDay = 0;
                    for (int i = startDay; i <= endDay; i++) {
                        double compareTotalDaySales = 0.00;
                        if (i <= modifyEndDay & i >= modifyStartDay) {
                            for (String[] record : records) {
                                String restaurant = record[4];
                                int day = Integer.parseInt(record[0]);
                                String foodName = record[5];
                                double price = Double.parseDouble(record[6]);
                                if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == i) {
                                    if (foodName.equalsIgnoreCase(foodModify)) {
                                        compareTotalDaySales += foodPriceModify;
                                    } else {
                                        compareTotalDaySales += price;
                                    }
                                }
                            }
                        } else {
                            for (String[] record : records) {
                                String restaurant = record[4];
                                int day = Integer.parseInt(record[0]);
                                String foodName = record[5];
                                double price = Double.parseDouble(record[6]);
                                if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == i) {
                                    compareTotalDaySales += price;
                                }
                            }
                        }
                        sales.put(i, compareTotalDaySales);
                    }
                    for (int days : sales.keySet()) {
                        if (sales.get(days) < minSales) {
                            minSales = sales.get(days);
                            minDay = days;
                        }
                    }
                    double totalDaySales = 0.0;
                    for (String[] record : records) {
                        String restaurant = record[4];
                        int day = Integer.parseInt(record[0]);
                        String foodName = record[5];
                        double price = Double.parseDouble(record[6]);
                        if (minDay >= modifyStartDay && minDay <= modifyEndDay) {
                            if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == minDay) {
                                if(!foodMenu.contains(foodName)){
                                    foodMenu.add(foodName);
                                }
                                if (foodName.equalsIgnoreCase(foodModify)) {
                                    modifyFoodQuantity.put(foodName, modifyFoodQuantity.getOrDefault(foodName, 0) + 1);
                                    modifyFoodPrice.put(foodName, foodPriceModify);
                                    totalDaySales += foodPriceModify;
                                } else {
                                    foodQuantity.put(foodName, foodQuantity.getOrDefault(foodName, 0) + 1);
                                    foodPrice.put(foodName, price);
                                    totalDaySales += price;
                                }
                            }
                        } else {
                            if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == minDay) {
                                if(!foodMenu.contains(foodName)){
                                    foodMenu.add(foodName);
                                }
                                foodQuantity.put(foodName, foodQuantity.getOrDefault(foodName, 0) + 1);
                                foodPrice.put(foodName, price);
                                totalDaySales += price;
                            }
                        }
                    }
                    System.out.println("==============================================================================");
                    System.out.println("Minimum Sales: Day " + minDay);
                    System.out.println("+-------------------------------------+------------+-----------------+");
                    System.out.println("|Food                                 |  Quantity  |    Total Price  |");
                    System.out.println("+-------------------------------------+------------+-----------------+");
                    for (String food : foodMenu) {
                        double totalPrice = foodPrice.getOrDefault(food,0.00) * foodQuantity.getOrDefault(food,0) + (modifyFoodPrice.getOrDefault(food,0.00) * modifyFoodQuantity.getOrDefault(food,0));
                        System.out.printf("|%-37s|  %-10d|  $%-14s|\n", food, foodQuantity.getOrDefault(food,0)+modifyFoodQuantity.getOrDefault(food,0), decimalFormat.format(totalPrice));
                    }
                    System.out.println("+--------------------------------------------------------------------+");
                    System.out.printf("|Total Sales                                       |  $%-14s|\n", decimalFormat.format(totalDaySales));
                    System.out.println("+--------------------------------------------------------------------+");
                }

                case "2B" -> {
                    System.out.print("Enter The Start Day: ");
                    int startDay = scanner.nextInt();
                    System.out.print("Enter The End Day: ");
                    int endDay = scanner.nextInt();
                    scanner.nextLine();
                    if (endDay > jojoLand.getDayCount() + 1 || startDay <= 0) {
                        System.out.println("Invalid Day");
                        break;
                    }
                    Map<Integer, Double> sales = new HashMap<>();
                    Map<String, Integer> foodQuantity = new HashMap<>();
                    Map<String, Double> foodPrice = new HashMap<>();
                    Map<String, Integer> modifyFoodQuantity = new HashMap<>();
                    Map<String, Double> modifyFoodPrice = new HashMap<>();
                    ArrayList<String> foodMenu= new ArrayList<>();
                    double maxSales = 0.00;
                    int maxDay = 0;
                    for (int i = startDay; i <= endDay; i++) {
                        double compareTotalDaySales = 0.00;
                        if (i <= modifyEndDay && i >= modifyStartDay) {
                            for (String[] record : records) {
                                String restaurant = record[4];
                                int day = Integer.parseInt(record[0]);
                                String foodName = record[5];
                                double price = Double.parseDouble(record[6]);
                                if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == i) {
                                    if (foodName.equalsIgnoreCase(foodModify)) {
                                        compareTotalDaySales += foodPriceModify;
                                    } else {
                                        compareTotalDaySales += price;
                                    }
                                }
                            }
                        } else {
                            for (String[] record : records) {
                                String restaurant = record[4];
                                int day = Integer.parseInt(record[0]);
                                String foodName = record[5];
                                double price = Double.parseDouble(record[6]);
                                if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == i) {
                                    compareTotalDaySales += price;
                                }
                            }
                        }
                        sales.put(i, compareTotalDaySales);
                    }
                    for (int days : sales.keySet()) {
                        if (sales.get(days) > maxSales) {
                            maxSales = sales.get(days);
                            maxDay = days;
                        }
                    }
                    double totalDaySales = 0.0;
                    for (String[] record : records) {
                        String restaurant = record[4];
                        int day = Integer.parseInt(record[0]);
                        String foodName = record[5];
                        double price = Double.parseDouble(record[6]);
                        if (maxDay >= modifyStartDay && maxDay <= modifyEndDay) {
                            if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == maxDay) {
                                if(!foodMenu.contains(foodName)){
                                    foodMenu.add(foodName);
                                }
                                if (foodName.equalsIgnoreCase(foodModify)) {
                                    modifyFoodQuantity.put(foodName, modifyFoodQuantity.getOrDefault(foodName, 0) + 1);
                                    modifyFoodPrice.put(foodName, foodPriceModify);
                                    totalDaySales += foodPriceModify;
                                } else {
                                    foodQuantity.put(foodName, foodQuantity.getOrDefault(foodName, 0) + 1);
                                    foodPrice.put(foodName, price);
                                    totalDaySales += price;
                                }
                            }
                        } else {
                            if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == maxDay) {
                                if(!foodMenu.contains(foodName)){
                                    foodMenu.add(foodName);
                                }
                                foodQuantity.put(foodName, foodQuantity.getOrDefault(foodName, 0) + 1);
                                foodPrice.put(foodName, price);
                                totalDaySales += price;
                            }
                        }
                    }
                    System.out.println("==============================================================================");
                    System.out.println("Maximum sales: Day " + maxDay);
                    System.out.println("+-------------------------------------+------------+-----------------+");
                    System.out.println("|Food                                 |  Quantity  |    Total Price  |");
                    System.out.println("+-------------------------------------+------------+-----------------+");
                    for (String food : foodMenu) {
                        double totalPrice = foodPrice.getOrDefault(food,0.00) * foodQuantity.getOrDefault(food,0) + (modifyFoodPrice.getOrDefault(food,0.00) * modifyFoodQuantity.getOrDefault(food,0));
                        System.out.printf("|%-37s|  %-10d|  $%-14s|\n", food, foodQuantity.getOrDefault(food,0)+modifyFoodQuantity.getOrDefault(food,0), decimalFormat.format(totalPrice));
                    }
                    System.out.println("+--------------------------------------------------------------------+");
                    System.out.printf("|Total Sales                                       |  $%-14s|\n", decimalFormat.format(totalDaySales));
                    System.out.println("+--------------------------------------------------------------------+");
                }

                case "2C" -> {
                    System.out.print("Enter The Start Day: ");
                    int startDay = scanner.nextInt();
                    System.out.print("Enter The End Day: ");
                    int endDay = scanner.nextInt();
                    scanner.nextLine();
                    if (endDay > jojoLand.getDayCount() + 1 || startDay <= 0) {
                        System.out.println("Invalid Day");
                        break;
                    }
                    Map<String, Integer> foodQuantity = new HashMap<>();
                    Map<String, Double> foodPrice = new HashMap<>();
                    Map<String, Integer> modifyFoodQuantity = new HashMap<>();
                    Map<String, Double> modifyFoodPrice = new HashMap<>();
                    ArrayList<String> foodMenu= new ArrayList<>();
                    int kFoodQuantity = 0;
                    String kFoodQuantityName = "";
                    double kFoodPrice = 0.00;
                    String kFoodPriceName = "";
                    for (int i = startDay; i <= endDay; i++) {
                        if (i <= modifyEndDay && i >= modifyStartDay) {
                            for (String[] record : records) {
                                String restaurant = record[4];
                                int day = Integer.parseInt(record[0]);
                                String foodName = record[5];
                                double price = Double.parseDouble(record[6]);
                                if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == i) {
                                    if(!foodMenu.contains(foodName)){
                                        foodMenu.add(foodName);
                                    }
                                    if (foodName.equalsIgnoreCase(foodModify)) {
                                        modifyFoodQuantity.put(foodName, modifyFoodQuantity.getOrDefault(foodName, 0) + 1);
                                        modifyFoodPrice.put(foodName, foodPriceModify);
                                    } else {
                                        foodQuantity.put(foodName, foodQuantity.getOrDefault(foodName, 0) + 1);
                                        foodPrice.put(foodName, price);
                                    }
                                }
                            }
                        } else {
                            for (String[] record : records) {
                                String restaurant = record[4];
                                int day = Integer.parseInt(record[0]);
                                String foodName = record[5];
                                double price = Double.parseDouble(record[6]);
                                if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == i) {
                                    if(!foodMenu.contains(foodName)){
                                        foodMenu.add(foodName);
                                    }
                                    foodQuantity.put(foodName, foodQuantity.getOrDefault(foodName, 0) + 1);
                                    foodPrice.put(foodName, price);
                                }
                            }
                        }
                    }
                    for (String food : foodMenu) {
                        if (foodQuantity.getOrDefault(food,0) + modifyFoodQuantity.getOrDefault(food,0) > kFoodQuantity) {
                            kFoodQuantity = foodQuantity.getOrDefault(food,0) + modifyFoodQuantity.getOrDefault(food,0);
                            kFoodQuantityName = food;
                        }
                    }
                    for (String food : foodMenu) {
                        double totalPrice = foodPrice.getOrDefault(food,0.00) * foodQuantity.getOrDefault(food,0) + (modifyFoodPrice.getOrDefault(food,0.00) * modifyFoodQuantity.getOrDefault(food,0));
                        if (totalPrice > kFoodPrice) {
                            kFoodPrice = totalPrice;
                            kFoodPriceName = food;
                        }
                    }
                    System.out.println("==============================================================================");
                    System.out.println("Food With Highest Quantity:");
                    System.out.println("+-------------------------------------+------------+");
                    System.out.println("|Food                                 |  Quantity  |");
                    System.out.println("+-------------------------------------+------------+");
                    System.out.printf("|%-37s|  %-10d|\n", kFoodQuantityName, foodQuantity.getOrDefault(kFoodQuantityName,0) + modifyFoodQuantity.getOrDefault(kFoodQuantityName,0));
                    System.out.println("+--------------------------------------------------+");
                    System.out.println("");
                    System.out.println("Food With Highest Total Price:");
                    System.out.println("+-------------------------------------+------------+------------------+");
                    System.out.println("|Food                                 |  Quantity  |    Total Price   |");
                    System.out.println("+-------------------------------------+------------+------------------+");
                    System.out.printf("|%-37s|  %-10d|  $%-15s|\n", kFoodPriceName, foodQuantity.getOrDefault(kFoodPriceName,0) + modifyFoodQuantity.getOrDefault(kFoodPriceName,0), decimalFormat.format(kFoodPrice));
                    System.out.println("+-------------------------------------+------------+------------------+");
                }

                case "2D" -> {
                    System.out.print("Enter The Start Day: ");
                    int startDay = scanner.nextInt();
                    System.out.print("Enter The End Day: ");
                    int endDay = scanner.nextInt();
                    scanner.nextLine();
                    if (endDay > jojoLand.getDayCount() + 1 || startDay <= 0) {
                        System.out.println("Invalid Day");
                        break;
                    }
                    Map<String, Integer> foodQuantity = new HashMap<>();
                    Map<String, Double> foodPrice = new HashMap<>();
                    Map<String, Integer> modifyFoodQuantity = new HashMap<>();
                    Map<String, Double> modifyFoodPrice = new HashMap<>();
                    ArrayList<String> foodMenu= new ArrayList<>();
                    double totalSales = 0.00;
                    for (int i = startDay; i <= endDay; i++) {
                        if (i <= modifyEndDay && i >= modifyStartDay) {
                            for (String[] record : records) {
                                String restaurant = record[4];
                                int day = Integer.parseInt(record[0]);
                                String foodName = record[5];
                                double price = Double.parseDouble(record[6]);
                                if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == i) {
                                    if (!foodMenu.contains(foodName)){
                                        foodMenu.add(foodName);
                                    }
                                    if (foodName.equalsIgnoreCase(foodModify)) {
                                        modifyFoodQuantity.put(foodName, modifyFoodQuantity.getOrDefault(foodName, 0) + 1);
                                        modifyFoodPrice.put(foodName, foodPriceModify);
                                    } else {
                                        foodQuantity.put(foodName, foodQuantity.getOrDefault(foodName, 0) + 1);
                                        foodPrice.put(foodName, price);
                                    }
                                }
                            }
                        } else {
                            for (String[] record : records) {
                                String restaurant = record[4];
                                int day = Integer.parseInt(record[0]);
                                String foodName = record[5];
                                double price = Double.parseDouble(record[6]);
                                if (restaurant.equalsIgnoreCase(jojoLand.getCurrentLocation()) && day == i) {
                                    if (!foodMenu.contains(foodName)){
                                        foodMenu.add(foodName);
                                    }
                                    foodQuantity.put(foodName, foodQuantity.getOrDefault(foodName, 0) + 1);
                                    foodPrice.put(foodName, price);
                                }
                            }
                        }
                    }
                    System.out.println("==============================================================================");
                    System.out.println("Total and Average Sales: (Day " + startDay + " - " + endDay + ")");
                    System.out.println("+-------------------------------------+------------+-----------------+");
                    System.out.println("|Food                                 |  Quantity  |    Total Price  |");
                    System.out.println("+-------------------------------------+------------+-----------------+");
                    for (String food : foodMenu) {
                        double totalPrice = foodPrice.getOrDefault(food,0.00) * foodQuantity.getOrDefault(food,0) + (modifyFoodPrice.getOrDefault(food,0.00) * modifyFoodQuantity.getOrDefault(food,0));
                        System.out.printf("|%-37s|  %-10d|  $%-14s|\n", food, foodQuantity.getOrDefault(food,0)+modifyFoodQuantity.getOrDefault(food,0), decimalFormat.format(totalPrice));
                        totalSales += totalPrice;
                    }
                    double averageSales = totalSales / (endDay - startDay + 1);
                    System.out.println("+--------------------------------------------------------------------+");
                    System.out.printf("|Total Sales                                       |  $%-14s|\n", decimalFormat.format(totalSales));
                    System.out.printf("|Average Sales                                     |  $%-14s|\n", decimalFormat.format(averageSales));
                    System.out.println("+--------------------------------------------------------------------+");
                }
                case "3" -> {
                    exit = true;
                }
                default -> {
                    System.out.println("Invalid Input");
                }
            }
        }
    }
}



