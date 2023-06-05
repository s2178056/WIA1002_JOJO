/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package newmoodyblues;

import java.io.*;
import java.text.*;
import java.util.*;


public class NewMoodyBlues {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        List<String[]> records = new ArrayList<>();
         try (BufferedReader br = new BufferedReader(new FileReader("foodHistory.csv"))) {
             String line;
         
         while ((line = br.readLine()) != null) {
             String[] values = line.split(",");
             records.add(values);
            }
    }
         catch (FileNotFoundException e) {
             System.out.println("CSV file not found: " + e.getMessage());
             return;
}

         Scanner input = new Scanner(System.in);
         int selection = input.nextInt();
         System.out.print("Enter the name of the restaurant: ");
         String restaurantName = scanner.nextLine();
        

         if (selection == 4) {
            System.out.println("======================================================================");
            System.out.println("Restaurant: " + restaurantName);
            System.out.println("Sales Information");
            System.out.println("[1] View Sales");
            System.out.println("[2] View Aggregated Information");
            System.out.println("    [A] Minimum Sales");
            System.out.println("    [B] Maximum Sales");
            System.out.println("    [C] Top k Highest Sales");
            System.out.println("    [D] Total and Average Sales");
            System.out.println("[3] Exit");
            System.out.println("Select: ");

            String selection2 = input.nextLine();
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            
             
            while (!"3".equals(selection2)) 
                switch (selection2) {
                
                    case "1" -> {
                        System.out.print("Enter the day: ");
                        int selectedDay = scanner.nextInt();
                        scanner.nextLine(); 
                
                        System.out.println("-------------------------------------+------------+------------------+");
                        System.out.println("Food                                                         |  Quantity    |    Total Price        |");
                        System.out.println("-------------------------------------+------------+------------------+");

                        double totalDaySales = 0.0;
                
                        for (String[] record : records) {
                            String restaurant = record[1];
                            int day = Integer.parseInt(record[0]);
                            int quantity = Integer.parseInt(record[2]);
                            double price = Double.parseDouble(record[3]);

                            if (restaurant.equalsIgnoreCase(restaurantName) && day == selectedDay) {
                                double totalPrice = quantity * price;
                                totalDaySales += totalPrice;
                                System.out.printf("%-60s|  %-12d|  $%-20s|\n", restaurant, quantity, decimalFormat.format(totalPrice));
                            }
                        }

                        System.out.println("----------------------------------------------------------------------+");
                        System.out.printf("Total                                                       |              |  $%-20s|\n", decimalFormat.format(totalDaySales));
                        System.out.println("--------------------------------------------------------------------------");
                }
                
                    case "2A" -> {
                        System.out.print("Enter the start day: ");
                        int startDay = scanner.nextInt();
                        System.out.print("Enter the end day: ");
                        int endDay = scanner.nextInt();
                        scanner.nextLine(); 
                        
                        Map<String, Double> foodSalesMap = new HashMap<>();
                        double totalSales = 0.0;
                        double minimumSales = Double.MAX_VALUE;
                        int dayWithMinimumSales = -1;
                        
                        for (String[] record : records) {
                            String restaurant = record[1];
                            int day = Integer.parseInt(record[0]);
                            int quantity = Integer.parseInt(record[3]);
                            double price = Double.parseDouble(record[4]);
                            
                            if (restaurant.equalsIgnoreCase(restaurantName) && day >= startDay && day <= endDay) {
                                double totalPrice = quantity * price;
                                totalSales += totalPrice;
                                
                                if (foodSalesMap.containsKey(restaurant)) {
                                    double currentSales = foodSalesMap.get(restaurant);
                                    foodSalesMap.put(restaurant, currentSales + totalPrice);
                                } else {
                                    foodSalesMap.put(restaurant, totalPrice);
                                }
                                
                                if (totalPrice < minimumSales) {
                                    minimumSales = totalPrice;
                                    dayWithMinimumSales = day;
                                }
                            }
                        }
                        
                        System.out.println("Minimum Sales: $" + decimalFormat.format(minimumSales));
                        
                        System.out.println("-------------------------------------+------------+------------------+");
                        System.out.println("Food                                                         |  Quantity    |    Total Price        |");
                        System.out.println("-------------------------------------+------------+------------------+");
                        
                        for (String[] record : records) {
                            String restaurant = record[1];
                            int day = Integer.parseInt(record[0]);
                            int quantity = Integer.parseInt(record[3]);
                            double price = Double.parseDouble(record[4]);
                            
                            if (restaurant.equalsIgnoreCase(restaurantName) && dayWithMinimumSales == day) {
                                double totalPrice = quantity * price;
                                System.out.printf("%-60s|  %-12d|  $%-20s|\n", record[2], quantity, decimalFormat.format(totalPrice));
                            }
                        }
                        
                        System.out.println("----------------------------------------------------------------------+");
                        System.out.printf("Total                                                       |              |  $%-20s|\n", decimalFormat.format(totalSales));
                        System.out.println("--------------------------------------------------------------------------");
                    }
                    
                case "2B" -> {
                    System.out.print("Enter the start day: ");
                    int startDay = scanner.nextInt();
                    System.out.print("Enter the end day: ");
                    int endDay = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    Map<String, Double> foodSalesMap = new HashMap<>();
                    double totalSales = 0.0;
                    double maximumSales = Double.MIN_VALUE;
                    int dayWithMaximumSales = -1;
                    
                    for (String[] record : records) {
                        String restaurant = record[1];
                        int day = Integer.parseInt(record[0]);
                        int quantity = Integer.parseInt(record[3]);
                        double price = Double.parseDouble(record[4]);
                        
                        if (restaurant.equalsIgnoreCase(restaurantName) && day >= startDay && day <= endDay) {
                            double totalPrice = quantity * price;
                            totalSales += totalPrice;
                            
                            if (foodSalesMap.containsKey(restaurant)) {
                                double currentSales = foodSalesMap.get(restaurant);
                                foodSalesMap.put(restaurant, currentSales + totalPrice);
                            } else {
                                foodSalesMap.put(restaurant, totalPrice);
                            }
                            
                            if (totalPrice > maximumSales) {
                                maximumSales = totalPrice;
                                dayWithMaximumSales = day;
                            }
                        }
                    }
                    
                    System.out.println("Maximum Sales: $" + decimalFormat.format(maximumSales));
                    
                    System.out.println("-------------------------------------+------------+------------------+");
                    System.out.println("Food                                                         |  Quantity    |    Total Price        |");
                    System.out.println("-------------------------------------+------------+------------------+");
                    
                    for (String[] record : records) {
                        String restaurant = record[1];
                        int day = Integer.parseInt(record[0]);
                        int quantity = Integer.parseInt(record[3]);
                        double price = Double.parseDouble(record[4]);
                        
                        if (restaurant.equalsIgnoreCase(restaurantName) && dayWithMaximumSales == day) {
                            double totalPrice = quantity * price;
                            System.out.printf("%-60s|  %-12d|  $%-20s|\n", record[2], quantity, decimalFormat.format(totalPrice));
                        }
                    }
                    
                    System.out.println("----------------------------------------------------------------------+");
                    System.out.printf("Total                                                       |              |  $%-20s|\n", decimalFormat.format(totalSales));
                    System.out.println("--------------------------------------------------------------------------");
                    }
                case "2C" -> {
                    System.out.print("Enter the start day: ");
                    int startDay = scanner.nextInt();
                    System.out.print("Enter the end day: ");
                    int endDay = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    System.out.print("Enter the value of k: ");
                    int k = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    Map<String, Double> foodSalesMap = new HashMap<>();
                    
                    for (String[] record : records) {
                        String restaurant = record[1];
                        int day = Integer.parseInt(record[0]);
                        int quantity = Integer.parseInt(record[3]);
                        double price = Double.parseDouble(record[4]);
                        
                        if (restaurant.equalsIgnoreCase(restaurantName) && day >= startDay && day <= endDay) {
                            double totalPrice = quantity * price;
                            
                            if (foodSalesMap.containsKey(record[2])) {
                                double currentSales = foodSalesMap.get(record[2]);
                                foodSalesMap.put(record[2], currentSales + totalPrice);
                            } else {
                                foodSalesMap.put(record[2], totalPrice);
                            }
                        }
                    }
                    
                    List<Map.Entry<String, Double>> topKSalesList = new ArrayList<>(foodSalesMap.entrySet());
                    topKSalesList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
                    
                    System.out.println("Top " + k + " Highest Sales:");
                    System.out.println("+-------------------------------------+----------+-------------+");
                    System.out.println("| Food                                | Quantity | Total Price |");
                    System.out.println("+-------------------------------------+----------+-------------+");
                    
                    int count = 0;
                    for (Map.Entry<String, Double> entry : topKSalesList) {
                        if (count >= k) {
                            break;
                        }
                        
                        String food = entry.getKey();
                        double totalPrice = entry.getValue();
                        int quantity = (int) (totalPrice / Double.parseDouble(records.get(0)[4]));
                        
                        System.out.printf("| %-35s | %8d | $%9s |\n", food, quantity, decimalFormat.format(totalPrice));
                        count++;
                    }
                    
                    System.out.println("+-------------------------------------+----------+-------------+");
                    }
                case "2D" -> {
                    System.out.print("Enter the start day: ");
                    int startDay = scanner.nextInt();
                    System.out.print("Enter the end day: ");
                    int endDay = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    
                    Map<String, Double> foodSalesMap = new HashMap<>();
                    double totalSales = 0.0;
                    int totalItems = 0;
                   
                    for (String[] record : records) {
                        String restaurant = record[1];
                        int day = Integer.parseInt(record[0]);
                        int quantity = Integer.parseInt(record[3]);
                        double price = Double.parseDouble(record[4]);
                        
                        if (restaurant.equalsIgnoreCase(restaurantName) && day >= startDay && day <= endDay) {
                            double totalPrice = quantity * price;
                            totalSales += totalPrice;
                            totalItems += quantity;
                            
                            if (foodSalesMap.containsKey(record[2])) {
                                double currentSales = foodSalesMap.get(record[2]);
                                foodSalesMap.put(record[2], currentSales + totalPrice);
                            } else {
                                foodSalesMap.put(record[2], totalPrice);
                            }
                        }
                    }
                    
                    double averageSales = totalSales / totalItems;
                    
                    // Print the table format
                    System.out.println("+-------------------------------------+----------+-------------+");
                    System.out.println("| Food                                | Quantity | Total Price |");
                    System.out.println("+-------------------------------------+----------+-------------+");
                    
                    for (Map.Entry<String, Double> entry : foodSalesMap.entrySet()) {
                        String food = entry.getKey();
                        double totalPrice = entry.getValue();
                        int quantity = (int) (totalPrice / Double.parseDouble(records.get(0)[4]));
                        
                        System.out.printf("| %-35s | %8d | $%9s |\n", food, quantity, decimalFormat.format(totalPrice));
                    }
                    
                    System.out.println("+-------------------------------------+----------+-------------+");
                    System.out.printf("| Total Sales                         |          | $%9s |\n", decimalFormat.format(totalSales));
                    System.out.printf("| Average Sales                       |          | $%9s |\n", decimalFormat.format(averageSales));
                    System.out.println("+-------------------------------------+----------+-------------+");
                    System.out.println("======================================================================");
                    }
            }
         }
    }
}
            
               
                    


        
    
    

