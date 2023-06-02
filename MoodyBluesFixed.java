/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package moodybluesfixed;
import java.util.*;

/**
 *
 * @author mrifq
 */
public class MoodyBluesFixed {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int selection = input.nextInt();

        if (selection == 4) {
            System.out.println("======================================================================");
            System.out.println("Restaurant: ");
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
            while (!"3".equals(selection2)) {
                switch (selection2) {
                    case "1" -> {
                        System.out.println("Enter Day: ");
                        int day = input.nextInt();
                        System.out.println("======================================================================");
                        System.out.println("Restaurant: ");
                        System.out.println("Day " + day + " Sales");
                        System.out.println("+-------------------------------------+----------+-------------+");

                        List<Order> ordersOfDay = OrderSystem.ordersByDay(day);
                        if (ordersOfDay != null && !ordersOfDay.isEmpty()) {
                            for (Order order : ordersOfDay) {
                                System.out.printf("| %-30s | %-9d | $%-10.2f |\n", order.getFood(), order.getQuantity(), order.getPrice());
                            }
                        } else {
                            System.out.println("| No orders found.                                             |");
                        }

                        System.out.println("+---------------------------------+----------------+----------------+");
                    }

                    case "2A" -> {
                        System.out.println("======================================================================");
                        double lowestSales = OrderSystem.getLowestSales();
                        if (lowestSales != -1) {
                            System.out.println("Day with Lowest Sale: Day " + OrderSystem.getDayWithLowestSale());
                            System.out.println("Total Sales: " + lowestSales);
                        } else {
                            System.out.println("No sales found.");
                        }
                        System.out.println("======================================================================");
                    }

                    case "2B" -> {
                        System.out.println("======================================================================");
                        double highestSales = OrderSystem.getHighestSales();
                        if (highestSales != -1) {
                            System.out.println("Day with Highest Sale: Day " + OrderSystem.getDayWithHighestSale());
                            System.out.println("Total Sales: " + highestSales);
                        } else {
                            System.out.println("No sales found.");
                        }
                        System.out.println("======================================================================");
                    }

                    case "2C" -> {
                        System.out.println("======================================================================");
                        System.out.println("Enter Day: ");
                        int salesDay = input.nextInt();
                        int k = 3; // Define the value of k

                        List<Map.Entry<String, Double>> topSales = OrderSystem.getTopKSales(salesDay, k);

                        System.out.println("Top " + k + " Sales for Day " + salesDay + ":");
                        System.out.println("+----------------+----------------+");
                        System.out.println("| Food           | Total Sales    |");
                        System.out.println("+----------------+----------------+");

                        if (!topSales.isEmpty()) {
                            for (Map.Entry<String, Double> entry : topSales) {
                                String food = entry.getKey();
                                double totalSales = entry.getValue();
                                System.out.printf("| %-14s | $%-14.2f |\n", food, totalSales);
                            }
                        } else {
                            System.out.println("| No sales found.                  |");
                        }

                        System.out.println("+----------------+----------------+");
                        System.out.println("======================================================================");
                    }

                    case "2D" -> {
                        System.out.println("======================================================================");
                        System.out.println("Enter Start Day: ");
                        int startDay = input.nextInt();
                        System.out.println("Enter End Day: ");
                        int endDay = input.nextInt();

                        double totalSalesInRange = OrderSystem.getTotalSalesInRange(startDay, endDay);

                        System.out.println("+--------------------------------------------+----------+-------------+");
                        System.out.println("| Food                                       | Quantity | Total Price |");
                        System.out.println("+--------------------------------------------+----------+-------------+");

                        List<Order> ordersInRange = OrderSystem.getOrdersInRange(startDay, endDay);
                        for (Order order : ordersInRange) {
                            String food = order.getFood();
                            int quantity = order.getQuantity();
                            double price = order.getPrice();
                            double total = quantity * price;
                            System.out.printf("| %-42s | %-8d | $%-9.2f |\n", food, quantity, total);
                        }

                        System.out.println("+--------------------------------------------+----------+-------------+");
                        System.out.printf("| %-42s | %-8s | $%-9.2f |\n", "Total Sales", "", totalSalesInRange);
                        System.out.println("+--------------------------------------------+----------+-------------+");
                        System.out.println("=======================================================================");
                    }
                }
                selection2 = input.nextLine();
            }
        }
    }
}

class Resident {
    private String name;
    private List<String> restaurantHistory;
    private List<Order> orderHistory;

    public Resident(String name) {
        this.name = name;
        restaurantHistory = new ArrayList<>();
        orderHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addRestaurantToHistory(String restaurantName) {
        restaurantHistory.add(restaurantName);
    }

    public List<String> getRestaurantHistory() {
        return restaurantHistory;
    }

    public void addOrderToHistory(Order order) {
        orderHistory.add(order);
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resident: ").append(name).append("\n");
        sb.append("Restaurant History: ").append(restaurantHistory).append("\n");
        sb.append("Order History: ").append(orderHistory).append("\n");
        return sb.toString();
    }
}

class Order {
    private String food;
    private int quantity;
    private double price;

    public Order(String food, int quantity, double price) {
        this.food = food;
        this.quantity = quantity;
        this.price = price;
    }

    public String getFood() {
        return food;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}

class OrderSystem {
    private static List<Order> orders;

    public OrderSystem() {
        orders = new ArrayList<>();
    }

    public static void addOrder(Order order) {
        orders.add(order);
    }

    public static List<Order> ordersByDay(int day) {
        List<Order> ordersByDayList = new ArrayList<>();

        for (Order order : orders) {
            if (order.getDay() == day) {
                ordersByDayList.add(order);
            }
        }

        return ordersByDayList;
    }

    public static List<Order> getOrdersInRange(int startDay, int endDay) {
        List<Order> ordersInRange = new ArrayList<>();

        for (Order order : orders) {
            int day = order.getDay();
            if (day >= startDay && day <= endDay) {
                ordersInRange.add(order);
            }
        }

        return ordersInRange;
    }

    public static double getTotalSalesInRange(int startDay, int endDay) {
        double totalSales = 0.0;

        List<Order> ordersInRange = getOrdersInRange(startDay, endDay);

        for (Order order : ordersInRange) {
            int quantity = order.getQuantity();
            double price = order.getPrice();
            totalSales += quantity * price;
        }

        return totalSales;
    }
    
    public static int getLowestSales() {
    Map<Integer, Double> salesByDay = new HashMap<>();

    for (Order order : orders) {
        int day = order.getDay();
        double price = order.getPrice();

        salesByDay.put(day, salesByDay.getOrDefault(day, 0.0) + price);
    }

    int lowestSalesDay = -1;
    double lowestSales = Double.MAX_VALUE;

    for (Map.Entry<Integer, Double> entry : salesByDay.entrySet()) {
        int day = entry.getKey();
        double totalSales = entry.getValue();

        if (totalSales < lowestSales) {
            lowestSales = totalSales;
            lowestSalesDay = day;
        }
    }

    return lowestSalesDay;
}
    
    public static int getHighestSales() {
    Map<Integer, Double> salesByDay = new HashMap<>();

    for (Order order : orders) {
        int day = order.getDay();
        double price = order.getPrice();

        salesByDay.put(day, salesByDay.getOrDefault(day, 0.0) + price);
    }

    int highestSalesDay = -1;
    double highestSales = 0.0;

    for (Map.Entry<Integer, Double> entry : salesByDay.entrySet()) {
        int day = entry.getKey();
        double totalSales = entry.getValue();

        if (totalSales > highestSales) {
            highestSales = totalSales;
            highestSalesDay = day;
        }
    }

    return highestSalesDay;
}
}




