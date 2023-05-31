

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AssignFood {
    public static void main(String[] args) throws IOException, FileNotFoundException {

        BufferedReader br = new BufferedReader(new FileReader("combinedRS.csv"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("foodHistory.csv"));

        writer.write("Day,Name,Age,Gender,Restaurant,Food,Price\n");
        int linesToSkip = 2;
        String line;
        while ((line = br.readLine()) != null) {
            if (linesToSkip > 0) {
                linesToSkip--;
                continue;
            }
            String[] data = line.split(",");
            String name = data[0].trim();
            String age = data[1].trim();
            String gender = data[2].trim();
            String restaurant = getRandomRestaurant();
            List<MenuItem> menu = MenuItem.getRestaurantMenu(restaurant);
            String randomMenu = (MenuItem.getRandomMenu(menu).getName());
            double price = MenuItem.getPrice();

            writer.write(String.format("%d,%s,%s,%s,%s,%s,%s\n", jojoLand.getDayCount(), name, age, gender, restaurant, randomMenu, price));
        }
        br.close();
        writer.close();

    }


    private static String getRandomRestaurant() {
        String[] restaurants = {
                "Jade Garden",
                "Cafe Deux Magots",
                "Trattoria Trussardi",
                "Libeccio",
                "Savage Garden"
        };

        int randomIndex = (int) (Math.random() * restaurants.length);
        return restaurants[randomIndex];
    }
}
   class MenuItem {
        String name;
        static double price;

        public MenuItem(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public static double getPrice() {
            return price;
        }
    public static MenuItem getRandomMenu(List<MenuItem> menu) {
        if (menu.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * menu.size());
        return menu.get(randomIndex);
    }


    public static List<MenuItem> getRestaurantMenu(String restaurant) {
        List<MenuItem> menu = new ArrayList<>();

        switch (restaurant) {
            case "Jade Garden":
                // Add menu items for Jade Garden
                menu.add(new MenuItem("Braised Chicken in Black Bean Sauce", 15.00));
                menu.add(new MenuItem("Braised Goose Web with Vermicelli", 21.00));
                menu.add(new MenuItem("Deep-fried Hiroshima Oysters", 17.00));
                menu.add(new MenuItem("Poached Tofu with Dried Shrimps", 12.00));
                menu.add(new MenuItem("Scrambled Egg White with Milk", 10.00));
                break;
            case "Cafe Deux Magots":
                // Add menu items for Cafe Deux Magots
                menu.add(new MenuItem("Sampling Matured Cheese Platter", 23.00));
                menu.add(new MenuItem("Spring Lobster Salad", 35.00));
                menu.add(new MenuItem("Spring Organic Omelette", 23.00));
                menu.add(new MenuItem("Truffle-flavoured Poultry Supreme", 34.00));
                menu.add(new MenuItem("White Asparagus", 26.00));
                break;
            case "Trattoria Trussardi":
                menu.add(new MenuItem("Caprese Salad", 10.00));
                menu.add(new MenuItem("Creme caramel", 6.50));
                menu.add(new MenuItem("Lamb Chops with Apple Sauce", 25.00));
                menu.add(new MenuItem("Spaghetti alla Puttanesca", 15.00));
                break;
            case "Libeccio":
                menu.add(new MenuItem("Formaggio", 12.50));
                menu.add(new MenuItem("Ghiaccio", 1.01));
                menu.add(new MenuItem("Melone", 5.20));
                menu.add(new MenuItem("Prosciutto and Pesci", 20.23));
                menu.add(new MenuItem("Risotto", 13.14));
                menu.add(new MenuItem("Zucchero and Sale", 0.60));
                break;
            case "Savage Garden":
                menu.add(new MenuItem("Abbacchio’s Tea", 1.00));
                menu.add(new MenuItem("DIO’s Bread", 36.14));
                menu.add(new MenuItem("Giorno’s Donuts", 6.66));
                menu.add(new MenuItem("Joseph’s Tequila", 35.00));
                menu.add(new MenuItem("Kakyoin’s Cherry", 3.50));
                menu.add(new MenuItem("Kakyoin’s Porridge", 4.44));
                break;
        }
        return menu;
    }

}


