import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

public class AssignFood {
    static List<MenuItem> jotaroMenu = new ArrayList<>();
    static List<MenuItem> checkMenu=new ArrayList<>();
    static ArrayList<String> allMenu=new ArrayList<>();
    static int restaurantCounter = 0;
    static Stack<String> giornoLastDish=new Stack<>();
    static Stack<String> jolnyeLastRestaurant=new Stack<>();
    static Stack<String> jotaroSaturdayRestaurant=new Stack<>();
    static ArrayList<String> josephFoodList = new ArrayList<>();
    static ArrayList<String> jonathanFoodList = new ArrayList<>();
    static double josukeWeeklyBudget=100;
    static int jotaroRestaurantIndex = 0;


    static final String [] restaurants= {
            "Savage Garden",
            "Libeccio",
            "Jade Garden",
            "Trattoria Trussardi",
            "Cafe Deux Magots",
    };

    public static void main(String[] args) throws IOException, FileNotFoundException {
        int day=jojoLand.getDayCount()+1;  //to amend (can increase the day to test)
        if(day==1){
            BufferedWriter writer = new BufferedWriter(new FileWriter("foodHistory.csv"));
            writer.close();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("foodHistory.csv",true));
        // writer.write("Day,Name,Age,Gender,Restaurant,Food,Price\n");
        String line;
        menuRunThrough();
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
            Random random = new Random();
            int hour = random.nextInt(24);
            int min = random.nextInt(60);
            int sec = random.nextInt(60);
            LocalTime time = LocalTime.of(hour,min,sec);
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
                            writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%tT\n", day, name, age, gender, restaurant, foodName, price,time));
                            menuSatisfiesFrequency = true;
                        }
                        if(!menuCheck.isEmpty()) {
                            menuCheck.remove(randomMenu.getName());
                        }
                        else{
                            jonathanFoodList.clear();;
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
                            writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%tT\n", day, name, age, gender, restaurant, foodName, price,time));
                            menuSatisfies = true;
                        }
                        if (!menuCheck.isEmpty()) {
                            menuCheck.remove(foodName);
                        }
                        else{
                            josephFoodList.clear();;
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
                    writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%tT\n", day, name, age, gender, getIndexRestaurant(jotaroRestaurantIndex), foodName, price,time));
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
                    if(day%7==1){
                        josukeWeeklyBudget=100;
                    }
                    do{
                        String restaurant = getRandomRestaurant();
                        List<MenuItem>menu = MenuItem.getRestaurantMenu(restaurant);
                        MenuItem randomMenu = (MenuItem.getRandomMenu(menu));
                        foodName=randomMenu.getName();
                        price = randomMenu.getPrice();
                        if(josukeWeeklyBudget>price||menuCheck.isEmpty()) {
                            writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%tT\n", day, name, age, gender, restaurant, foodName, price,time));
                            josukeWeeklyBudget-=price;
                            josukeSatisfy=true;
                        }
                        if(!menuCheck.isEmpty()) {
                            menuCheck.remove(foodName);
                        }
                    }while(!josukeSatisfy);
                    break;
                case "Giorno Giovanna":
                    boolean giornoSatisfy = false;
                    if (day % 7 == 0) {
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
                                writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%tT\n", day, name, age, gender, restaurant, foodName, price,time));
                                giornoSatisfy = true;
                            }
                        } while (!giornoSatisfy);
                    } while (!giornoSatisfy);
                    break;
                case "Jolyne Cujoh":
                    boolean jolyneSatisfy=false;
                    if (day%7==6){
                        List<MenuItem> scMenu = MenuItem.getRestaurantMenu(getIndexRestaurant(jotaroRestaurantIndex));
                        MenuItem randomMenu = MenuItem.getRandomMenu(scMenu);
                        foodName=randomMenu.getName();
                        price = randomMenu.getPrice();
                        jolnyeLastRestaurant.push(getIndexRestaurant(jotaroRestaurantIndex));
                        writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%tT\n",day, name, age, gender, getIndexRestaurant(jotaroRestaurantIndex), foodName, price,time));
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
                                writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%tT\n",day, name, age, gender, restaurant, foodName, price,time));
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
                    writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%tT\n", day, name, age, gender, restaurant, foodName, price,time));
            }
        }
        br.close();
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

    public static void viewRestaurantMenu(String restaurant){
        Scanner sc = new Scanner(System.in);
        boolean exit=false;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        while(!exit) {
            List<MenuItem> availableMenu = MenuItem.getRestaurantMenu(restaurant);
            int i = 1;
            System.out.println(restaurant + " Menu");
            System.out.println("+-------------------------------------+------------+");
            System.out.println("|Food                                 | Price      |");
            System.out.println("+-------------------------------------+------------+");
            for (MenuItem menuItem : availableMenu) {
                System.out.printf("|%-37s|  %-10s|\n", menuItem.getName(), decimalFormat.format(menuItem.getPrice()));
            }
            System.out.println("+--------------------------------------------------+");
            System.out.println("=================================================================================");
            System.out.println("[1] Add Food");
            System.out.println("[2] Delete Food");
            System.out.println("[3] Modify Food");
            System.out.println("[4] Exit");
            String input =sc.nextLine();
            switch (input){
                case "1":
                    System.out.print("Food Name: ");
                    String foodName= sc.nextLine();
                    System.out.print("Food Price: ");
                    String foodPrice= sc.nextLine();
                    MenuItem.addMenu(foodName,foodPrice,restaurant);
                    System.out.println("=================================================================================");
                    break;
                case "2":
                    System.out.print("Remove Food Name: ");
                    String removeFoodName= sc.nextLine();
                    MenuItem.removeFood(removeFoodName);
                    System.out.println("=================================================================================");
                    break;
                case"3":
                    System.out.print("Food Name: ");
                    String modifyFoodName= sc.nextLine();
                    System.out.print("Food Price: ");
                    String modifyFoodPrice= sc.nextLine();
                    MenuItem.modifyFoodPrice(modifyFoodName,Double.parseDouble(modifyFoodPrice));
                    System.out.println("=================================================================================");
                    break;
                case "4":
                    System.out.println("=================================================================================");
                    exit=true;
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
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

        if (newFoodFreq >= maxFreq || maxFreq - minFreq > 1) {
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

    public static List<MenuItem> getRestaurantMenu(String restaurant){
        List<MenuItem> menu = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("MenuItem.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] word= line.split(",");
                if(word[0].equalsIgnoreCase(restaurant)){
                    menu.add(new MenuItem(word[1], Double.parseDouble(word[2])));
                }
            }
            br.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("CSV file not found: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            return menu;
    }

    public static void createMenu(){
        String filePath = "MenuItem.csv";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            // Write the data rows
            writer.write("Jade Garden,Braised Chicken in Black Bean Sauce,15.00\n");
            writer.write("Jade Garden,Braised Goose Web with Vermicelli,21.00\n");
            writer.write("Jade Garden,Deep-fried Hiroshima Oysters,17.00\n");
            writer.write("Jade Garden,Poached Tofu with Dried Shrimps,12.00\n");
            writer.write("Jade Garden,Scrambled Egg White with Milk,10.00\n");
            writer.write("Cafe Deux Magots,Sampling Matured Cheese Platter,23.00\n");
            writer.write("Cafe Deux Magots,Spring Lobster Salad,35.00\n");
            writer.write("Cafe Deux Magots,Spring Organic Omelette,23.00\n");
            writer.write("Cafe Deux Magots,Truffle-flavoured Poultry Supreme,34.00\n");
            writer.write("Cafe Deux Magots,White Asparagus,26.00\n");
            writer.write("Trattoria Trussardi,Caprese Salad,10.00\n");
            writer.write("Trattoria Trussardi,Creme caramel,6.50\n");
            writer.write("Trattoria Trussardi,Lamb Chops with Apple Sauce,25.00\n");
            writer.write("Trattoria Trussardi,Spaghetti alla Puttanesca,15.00\n");
            writer.write("Libeccio,Formaggio,12.50\n");
            writer.write("Libeccio,Ghiaccio,1.01\n");
            writer.write("Libeccio,Melone,5.20\n");
            writer.write("Libeccio,Prosciutto and Pesci,20.23\n");
            writer.write("Libeccio,Risotto,13.14\n");
            writer.write("Libeccio,Zucchero and Sale,0.60\n");
            writer.write("Savage Garden,Abbacchio’s Tea,1.00\n");
            writer.write("Savage Garden,DIO’s Bread,36.14\n");
            writer.write("Savage Garden,Giorno’s Donuts,6.66\n");
            writer.write("Savage Garden,Joseph’s Tequila,35.00\n");
            writer.write("Savage Garden,Kakyoin’s Cherry,3.50\n");
            writer.write("Savage Garden,Kakyoin’s Porridge,4.44\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMenu(String newFood, String newPrice,String location) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("MenuItem.csv", true)); // Append mode
            // Write additional data rows
            writer.write(location+","+newFood+","+newPrice+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeFood(String foodItemToRemove) {
        boolean found=false;
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("MenuItem.csv"))) {
            String line;
            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                String[] word=line.split(",");
                // Check if the line contains the food item to remove
                if (!word[1].equalsIgnoreCase(foodItemToRemove)) {
                    fileContent.add(line);
                    found=true;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: ");
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: ");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("MenuItem.csv"))) {
            // Write the updated content back to the CSV file
            for (String line : fileContent) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file:");
        }
        if(!found){
            System.out.println("Food Not Found!!!");
        }
    }

    public static void modifyFoodPrice(String foodItemToUpdate, double newPrice) {
        boolean found=false;
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("MenuItem.csv"))) {
            String line;
            // Read the file line by line
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the food item to update
                String[] word= line.split(",");
                if (word[1].equalsIgnoreCase(foodItemToUpdate)) {
                    // Update the price of the food item
                    word[2] = String.valueOf(newPrice);
                    // Join the values back into a line
                    line =word[0]+","+word[1]+","+word[2];
                    found=true;
                }
                fileContent.add(line);
            }
            if(!found){
                System.out.println("Food Not Found!!!");
                return;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: ");
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: ");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("MenuItem.csv"))) {
            // Write the updated content back to the CSV file
            for (String line : fileContent) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: ");
        }
    }
}




