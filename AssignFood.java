import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AssignFood {
    static List<MenuItem> jotaroMenu = new ArrayList<>();
    static List<MenuItem> checkMenu=new ArrayList<>();
    static ArrayList<String> allMenu=new ArrayList<>();
    static int restaurantCounter = 0;
    static Stack<String> giornoLastDish=new Stack<>();
    static Stack<String> jolnyeLastRestaurant=new Stack<>();
    static Stack<String> jotaroSaturdayRestaurant=new Stack<>();
    static final String [] restaurants= {
                "Savage Garden",
                "Libeccio",
                "Jade Garden",
                "Trattoria Trussardi",
                "Cafe Deux Magots",
    };

    public static void main(String[] args) throws IOException, FileNotFoundException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("foodHistory.csv",true));
        // writer.write("Day,Name,Age,Gender,Restaurant,Food,Price\n");
        String line;
        ArrayList<String> jonathanFoodList = new ArrayList<>();
        ArrayList<String> josephFoodList = new ArrayList<>();
        double josukeWeeklyBudget=100;
        jotaroMenu.clear();
        menuRunThrough();
        int day=1;  //to amend (can increase the day to test)
        int jotaroRestaurantIndex = 0;
        for (int i=0;i<=1;i++) {
            BufferedReader br = new BufferedReader(new FileReader("combinedRS.csv"));
            int linesToSkip = 2;
            while ((line = br.readLine()) != null) {
                ArrayList<String> menuCheck=allMenu;
                if (linesToSkip > 0) {
                    linesToSkip--;
                    continue;
                }
                String[] data = line.split(",");
                String name = data[0].trim();
                String age = data[1].trim();
                String gender = data[2].trim();

                switch (name) {
                    case "Jonathan Joestar":
                        menuCheck = new ArrayList<>(allMenu); // Initialize menuCheck with allMenu at the beginning of each iteration
                        boolean menuSatisfiesFrequency = false;
                        do {
                            String restaurant = getRandomRestaurant();
                            List<MenuItem> menu = MenuItem.getRestaurantMenu(restaurant);
                            MenuItem randomMenu = MenuItem.getRandomMenu(menu);
                            String foodName=randomMenu.getName();
                            double price = randomMenu.getPrice();
                            if (checkBalanceFrequency(jonathanFoodList, foodName)||menuCheck.isEmpty()) {
                                jonathanFoodList.add(foodName);
                                writer.write(String.format("%d,%s,%s,%s,%s,%s,%s\n", day, name, age, gender, restaurant, foodName, price));
                                menuSatisfiesFrequency = true;
                            }
                            if(!menuCheck.isEmpty()) {
                                menuCheck.remove(randomMenu.getName());
                            }
                        } while (!menuSatisfiesFrequency);
                        break;
                    case "Joseph Joestar":
                        menuCheck = new ArrayList<>(allMenu); // Initialize menuCheck with allMenu at the beginning of each iteration
                        boolean menuSatisfies = false;
                        do {
                            String restaurant = getRandomRestaurant();
                            List<MenuItem> menu = MenuItem.getRestaurantMenu(restaurant);
                            MenuItem randomMenu = MenuItem.getRandomMenu(menu);
                            String foodName = randomMenu.getName();
                            double price = randomMenu.getPrice();
                            if (checkRepeat(josephFoodList, foodName) || menuCheck.isEmpty()) {
                                josephFoodList.add(foodName);
                                writer.write(String.format("%d,%s,%s,%s,%s,%s,%s\n", day, name, age, gender, restaurant, foodName, price));
                                menuSatisfies = true;
                            }
                            if (!menuCheck.isEmpty()) {
                                menuCheck.remove(foodName);
                            } else {
                                josephFoodList.clear();
                            }
                        } while (!menuSatisfies);
                        break;
                    case "Jotaro Kujo":
                        if (jotaroMenu.isEmpty()) {
                            jotaroMenu = MenuItem.getRestaurantMenu(getIndexRestaurant(jotaroRestaurantIndex));
                        }
                        MenuItem randomMenuItem = MenuItem.getRandomMenu(jotaroMenu);
                        String foodName = randomMenuItem.getName();
                        double price = randomMenuItem.getPrice();
                        writer.write(String.format("%d,%s,%s,%s,%s,%s,%s\n", day, name, age, gender, getIndexRestaurant(jotaroRestaurantIndex), foodName, price));
                        jotaroMenu.remove(randomMenuItem); // Remove the selected menu item from the list
                        if (jotaroMenu.isEmpty()) {
                            jotaroRestaurantIndex++;
                            if (jotaroRestaurantIndex > 4) {
                                jotaroRestaurantIndex = 0;
                            }
                            jotaroMenu = MenuItem.getRestaurantMenu(getIndexRestaurant(jotaroRestaurantIndex));
                        }
                        break;
                    case "Josuke Higashikata":
                        menuCheck = new ArrayList<>(allMenu); // Initialize menuCheck with allMenu at the beginning of each iteration
                        boolean josukeSatisfy = false;
                        if(i%7==0){
                            josukeWeeklyBudget=100;
                        }
                        do{
                        String restaurant = getRandomRestaurant();
                        List<MenuItem>menu = MenuItem.getRestaurantMenu(restaurant);
                        MenuItem randomMenu = (MenuItem.getRandomMenu(menu));
                        foodName=randomMenu.getName();
                        price = randomMenu.getPrice();
                        if(josukeWeeklyBudget>price||menuCheck.isEmpty()) {
                            if(!menuCheck.isEmpty()) {
                                menuCheck.remove(foodName);
                            }
                            writer.write(String.format("%d,%s,%s,%s,%s,%s,%s\n", day, name, age, gender, restaurant, foodName, price));
                            josukeWeeklyBudget-=price;
                            josukeSatisfy=true;
                        }
                            if(!menuCheck.isEmpty()) {
                                menuCheck.remove(randomMenu.getName());
                            }
                        }while(!josukeSatisfy);
                        break;
                    case "Giorno Giovanna":
                        boolean giornoSatisfy = false;
                        if (i % 7 == 0) {
                            restaurantCounter = 0;
                        }
                        do {
                            String restaurant = getRandomRestaurant();
                            if (restaurant.equals("Trattoria Trussardi")) {
                                restaurantCounter++;
                                if (restaurantCounter > 2) {
                                    continue; // Skip this iteration if Trattoria Trussardi has already been visited twice
                                }
                            } else {
                                if (restaurantCounter < 2) {
                                    continue; // Skip this iteration if Trattoria Trussardi has not been visited twice yet
                                }
                            }
                            do {
                                List<MenuItem> menu = MenuItem.getRestaurantMenu(restaurant);
                                MenuItem randomMenu = MenuItem.getRandomMenu(menu);
                                foodName=randomMenu.getName();
                                price = randomMenu.getPrice();
                                if (giornoLastDish.isEmpty() || !randomMenu.equals(giornoLastDish.peek())) {
                                    giornoLastDish.push(foodName);
                                    writer.write(String.format("%d,%s,%s,%s,%s,%s,%s\n", day, name, age, gender, restaurant, foodName, price));
                                    giornoSatisfy = true;
                                }
                            } while (!giornoSatisfy);
                        } while (!giornoSatisfy);
                        break;
                    case "Jolyne Cujoh":
                        boolean jolyneSatisfy=false;
                        if (i%7==6){
                            List<MenuItem> scMenu = MenuItem.getRestaurantMenu(getIndexRestaurant(jotaroRestaurantIndex));
                            MenuItem randomMenu = MenuItem.getRandomMenu(scMenu);
                            foodName=randomMenu.getName();
                            price = randomMenu.getPrice();
                                jolnyeLastRestaurant.push(getIndexRestaurant(jotaroRestaurantIndex));
                                writer.write(String.format("%d,%s,%s,%s,%s,%s,%s\n",day, name, age, gender, getIndexRestaurant(jotaroRestaurantIndex), foodName, price));
                        }
                        else {
                            do {
                                String restaurant = getRandomRestaurant();
                                if (jolnyeLastRestaurant.isEmpty() || !jolnyeLastRestaurant.peek().equals(restaurant)) {
                                    jolnyeLastRestaurant.push(restaurant);
                                    List<MenuItem> menu = MenuItem.getRestaurantMenu(restaurant);
                                    MenuItem randomMenu = MenuItem.getRandomMenu(menu);
                                    foodName=randomMenu.getName();
                                    price = randomMenu.getPrice();
                                    writer.write(String.format("%d,%s,%s,%s,%s,%s,%s\n",day, name, age, gender, restaurant, foodName, price));
                                    jolyneSatisfy = true;
                                }
                            } while (!jolyneSatisfy);
                        }
                        break;
                    default:
                        String restaurant = getRandomRestaurant();
                        List<MenuItem>menu = MenuItem.getRestaurantMenu(restaurant);
                        MenuItem randomMenu = MenuItem.getRandomMenu(menu);
                        foodName=randomMenu.getName();
                        price = randomMenu.getPrice();
                        writer.write(String.format("%d,%s,%s,%s,%s,%s,%s\n", day, name, age, gender, restaurant, foodName, price));
                }
            }
            br.close();
        }
        day++;
        writer.close();
    }


    private static String getRandomRestaurant() {
        Random rng = new Random();
        int randomIndex = rng.nextInt(restaurants.length);
        return restaurants[randomIndex];
    }

    private static String getIndexRestaurant(int num) {
        return restaurants[num];
    }

    private static void menuRunThrough(){
        allMenu.clear();
        for (String restaurant: restaurants) {
            List<MenuItem> availableMenu = MenuItem.getRestaurantMenu(restaurant);
            for(MenuItem menus:availableMenu){
                allMenu.add(menus.getName());
            }
        }
    }

    public static boolean checkBalanceFrequency(ArrayList<String> foods, String newFood) {
        // Step 1: Count the frequency of each food
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String food : foods) {
            Integer count = frequencyMap.getOrDefault(food, 0);
            frequencyMap.put(food, count + 1);
        }
        // Step 2: Check if adding the new food will exceed the frequency difference limit
        int newFoodFreq = frequencyMap.getOrDefault(newFood, 0);
        if (!frequencyMap.containsKey(newFood)) {
            return true; // No existing foods, so the frequency is balanced
        }

        int maxFreq = Collections.max(frequencyMap.values());
        int minFreq = Collections.min(frequencyMap.values());

        if (newFoodFreq > maxFreq || maxFreq - minFreq > 1) {
            return false; // Frequency exceeds limit
        } else {
            return true; // Frequency does not exceed limit
        }
    }

    public static boolean checkRepeat(ArrayList<String> foods, String newFood) {
        for (String food : foods) {
            if (newFood.equals(food)) {
                return false; // Found a repeat within the limit
            }
        }
        return true;
    }
}

    class MenuItem {
    String name;
     double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public static MenuItem getRandomMenu(List<MenuItem> menu) {
        Random rng = new Random();
        int randomIndex = rng.nextInt(menu.size());
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

