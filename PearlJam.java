import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PearlJam {

    public static List<Resident> loadResidents() {
        List<Resident> residents = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("combinedRS.csv"))) {
            int linesToSkip = 2;
            String line;
            while ((line = br.readLine()) != null) {
                if (linesToSkip > 0) {
                    linesToSkip--;
                    continue; // Skip the specified number of lines
                }
                String[] data = line.split(",");
                String name = data[0].trim();
                String age = data[1].trim();
                String gender = data[2].trim();

                residents.add(new Resident(name, age, gender));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return residents;
    }

    static class MenuItem {
        private String name;
        private double price;

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
    }

    public static Map<String, List<Resident>> assignFoodAndRestaurant(List<Resident> residents) {
        Map<String, List<Resident>> waitingLists = new HashMap<>();
        for (Resident resident : residents) {
            String restaurant = getRandomRestaurant();
            resident.setRestaurant(restaurant);
            List<MenuItem> menu = getRestaurantMenu(restaurant);
            MenuItem randomMenu = getRandomMenu(menu);
            resident.setMenu(randomMenu);
            List<Resident> waitingList = waitingLists.getOrDefault(restaurant, new ArrayList<>());
            waitingList.add(resident);
            waitingLists.put(restaurant, waitingList);
        }
        return waitingLists;
    }

    // Declare the Resident object as an instance variable
    private static Resident resident;

    public static List<String[]> assignFoodAndRestaurantResident(String residentName, int dayCount) {
        if (resident == null || !resident.getName().equals(residentName)) {
            // Create a new resident if it doesn't exist or the name is different
            resident = new Resident(residentName);
        }
        while(Resident.foodCount <=dayCount) {
            String restaurant = getRandomRestaurant();
            resident.setRestaurant(restaurant);
            List<MenuItem> menu = getRestaurantMenu(restaurant);
            MenuItem randomMenu = getRandomMenu(menu);
            // Increment the day count and assign the corresponding food based on the day count
            int assignedFoodIndex = Resident.foodCount % menu.size();
            MenuItem assignedFood = menu.get(assignedFoodIndex);
            // Add resident's assigned food to the resident's order history
            String[] order = {Integer.toString(Resident.foodCount + 1), assignedFood.getName(), resident.getRestaurant()};
            resident.addToOrderHistory(order);
            Resident.foodCount++;
        }
        return resident.getOrderHistory();
    }

    public static List<String[]> assignFoodAndRestaurantJonathanJoestar(String residentName,int dayCount){
        if (resident == null || !resident.getName().equals(residentName)) {
            // Create a new resident if it doesn't exist or the name is different
            resident = new Resident(residentName);
        }
        while(resident.foodCount<=dayCount) {
            String restaurant = getRandomRestaurant();
            resident.setRestaurant(restaurant);
            List<MenuItem> menu = getRestaurantMenu(restaurant);
            MenuItem randomMenu = getRandomMenu(menu);
            // Increment the day count and assign the corresponding food based on the day count
            int assignedFoodIndex = dayCount % menu.size();
            MenuItem assignedFood = menu.get(assignedFoodIndex);
            // Add resident's assigned food to the resident's order history
            String[] order = {Integer.toString(resident.foodCount + 1), assignedFood.getName(), resident.getRestaurant()};
            resident.addToOrderHistory(order);
            resident.foodCount++;
        }
        return resident.getOrderHistory();
    }

    private static MenuItem findMenuItemByName(String foodName, List<MenuItem> menu) {
        for (MenuItem food : menu) {
            if (food.getName().equalsIgnoreCase(foodName)) {
                return food;
            }
        }
        return null;  // Return null if the food item is not found
    }


    public static void displayOrderHistory(List<String[]> orderHistoryList) {
        System.out.println("Order History for Resident");
        System.out.println("+-----+-------------------------------------+---------------------------+");
        System.out.println("| Day | Food                                | Restaurant                |");
        System.out.println("+-----+-------------------------------------+---------------------------+");
        for (String[] orderEntry : orderHistoryList) {
            System.out.printf("| %-3s | %-35s | %-25s |\n", orderEntry[0], orderEntry[1], orderEntry[2]);
        }
        System.out.println("+-----+-------------------------------------+---------------------------+");
    }


    private static MenuItem getRandomMenu(List<MenuItem> menu) {
        if (menu.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * menu.size());
        return menu.get(randomIndex);
    }

    private static List<MenuItem> getRestaurantMenu(String restaurant) {
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

    public static List<Resident> getRestaurantWaitingList(Map<String, List<Resident>> waitingLists, String restaurant) {
        return waitingLists.getOrDefault(restaurant, new ArrayList<>());
    }

    public static List<Resident> getResidentWaitingList(Map<String, List<Resident>> waitingLists, String resident) {
        return waitingLists.getOrDefault(resident, new ArrayList<>());
    }

    public static List<Resident> generateOrderProcessingList(List<Resident> waitingList, String restaurant) {

        List<Resident> orderProcessingList = new ArrayList<>();

        switch (restaurant) {
            case "Jade Garden":
                for (int i = 0; i < waitingList.size() / 2; i++) {
                    orderProcessingList.add(waitingList.get(i));
                    orderProcessingList.add(waitingList.get(waitingList.size() - 1 - i));
                }
                if (waitingList.size() % 2 != 0) {
                    orderProcessingList.add(waitingList.get(waitingList.size() / 2));
                }
                break;
            case "Cafe Deux Magots":
                List<Resident> sortedList = new ArrayList<>(waitingList);
                sortedList.removeIf(resident -> resident.getAge().equals("N/A"));
                sortedList.sort((c1, c2) -> {
                    int age1 = Integer.parseInt(c1.getAge());
                    int age2 = Integer.parseInt(c2.getAge());
                    return Integer.compare(age1, age2);
                });

                List<Resident> processingList = new ArrayList<>();

                int startIndex = 0;
                int endIndex = sortedList.size() - 1;

                while (startIndex <= endIndex) {
                    Resident oldest = sortedList.get(endIndex);
                    Resident youngest = sortedList.get(startIndex);

                    processingList.add(oldest);
                    if (startIndex != endIndex) {
                        processingList.add(youngest);
                    }

                    startIndex++;
                    endIndex--;
                }

                // Add residents with unknown ages ("N/A") to the end of the processing list
                for (Resident resident : waitingList) {
                    if (resident.getAge().equals("N/A")) {
                        processingList.add(resident);
                    }
                }

                orderProcessingList.addAll(processingList);
                break;

            case "Trattoria Trussardi":
                List<Resident> males = new ArrayList<>();
                List<Resident> females = new ArrayList<>();
                List<Resident> naAges = new ArrayList<>();

                for (Resident resident : waitingList) {
                    if (resident.getGender().equals("Male")) {
                        males.add(resident);
                    } else {
                        females.add(resident);
                    }
                }
                males.sort(Comparator.comparingInt((Resident r) -> {
                    if (r.getAge().equals("N/A")) {
                        return Integer.MAX_VALUE; // Assign a high value for "N/A" ages
                    } else {
                        return Integer.parseInt(r.getAge());
                    }
                }));
                females.sort(Comparator.comparingInt((Resident r) -> {
                    if (r.getAge().equals("N/A")) {
                        return Integer.MAX_VALUE; // Assign a high value for "N/A" ages
                    } else {
                        return Integer.parseInt(r.getAge());
                    }
                }).reversed());

                // Add residents with "N/A" age to the naAges list for each gender
                for (Resident resident : males) {
                    if (resident.getAge().equals("N/A")) {
                        naAges.add(resident);
                    }
                }
                males.removeAll(naAges);

                for (Resident resident : females) {
                    if (resident.getAge().equals("N/A")) {
                        naAges.add(resident);
                    }
                }
                females.removeAll(naAges);

                while (!males.isEmpty() || !females.isEmpty()) {
                    if (!males.isEmpty()) {
                        orderProcessingList.add(males.remove(0));
                    }
                    if (!females.isEmpty()) {
                        orderProcessingList.add(females.remove(0));
                    }
                    if (!males.isEmpty()) {
                        orderProcessingList.add(males.remove(males.size()-1));
                    }
                    if (!females.isEmpty()) {
                        orderProcessingList.add(females.remove(females.size()-1));
                    }
                }
                // Add the naAges list to the end of the orderProcessingList
                orderProcessingList.addAll(naAges);
                break;
            case "Libeccio":
                List<Resident> waitingListCopy = new ArrayList<>(waitingList);
                int dayNumber = getCurrentDay();
                int index = 0;
                int count = 1;

                while (!waitingListCopy.isEmpty()) {
                    if (count % dayNumber == 0) {
                        Resident resident = waitingListCopy.remove(index);
                        orderProcessingList.add(resident);
                        index--;
                    }
                    count++;
                    index++;
                    if (index >= waitingListCopy.size()) {
                        index = 0;
                    }
                }
                Collections.reverse(orderProcessingList);
                break;
            case "Savage Garden":
                List<Resident> waitingListCopy2 = new ArrayList<>(waitingList);
                dayNumber = getCurrentDay();

                index = dayNumber - 1;
                while (!waitingListCopy2.isEmpty()) {
                    index %= waitingListCopy2.size();
                    orderProcessingList.add(waitingListCopy2.remove(index));
                    index += dayNumber-1;
                }
                break;
            default:
                // Handle unknown restaurant
                break;
        }
        return orderProcessingList;
    }

    private static int getCurrentDay() {
        Random random = new Random();
        return random.nextInt(10)+1;
    }

    private static String getRestaurant(String restaurant) {
        return restaurant;
    }

    public static void printWaitingList(List<Resident> waitingList, String restaurant) {
        System.out.println("Restaurant: " + restaurant +"\n");
        System.out.println("Waiting List");
        System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        System.out.println("| No | Name                    | Age | Gender | Order                                    |");
        System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        int count = 0;
        for (Resident resident : waitingList) {
            if (resident.getRestaurant().equals(restaurant)) {
                count++;
                System.out.printf("| %-2d | %-24s| %-3s | %-6s | %-40s |\n", count, resident.getName(), resident.getAge(), resident.getGender(), resident.getMenu().getName());
            }
        }

        if (count == 0) {
            System.out.println("| No residents in the waiting list for " + restaurant);
            System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        } else {
            System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        }
    }

    public static void printOrderProcessingList(List<Resident> orderProcessingList, String restaurant) {
        System.out.println("\nOrder Processing List");
        System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        System.out.println("| No | Name                    | Age | Gender | Order                                    |");
        System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        int count = 0;
        for (Resident resident : orderProcessingList) {
            if (resident != null && restaurant.equals(resident.getRestaurant())) {
                count++;
                MenuItem menu = resident.getMenu();
                String menuName = (menu != null) ? menu.getName() : "Unknown";
                System.out.printf("| %-2d | %-24s| %-3s | %-6s | %-40s |\n", count, resident.getName(), resident.getAge(), resident.getGender(), menuName);
            }
        }
        if (count == 0) {
            System.out.println("| No residents in the order processing list for " + restaurant);
            System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        } else {
            System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        }
        System.out.println("=====================================================================================================");
    }

    static class Resident {
        private String name;
        private String age;
        private String gender;
        private double budget;
        private String restaurant;
        private MenuItem menu;
        private List<String[]> orderHistory;
        private Map<String, Integer> foodCounters;
        private String lastRestaurant;
        private static int foodCount;


        public Resident(String name, String age, String gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public Resident(String name) {
            this.name = name;
            this.orderHistory = new ArrayList<>();
            this.lastRestaurant="";
            this.foodCounters=new HashMap<>();
            foodCount=0;
        }

        public Resident(String name,double budget) {
            this.name = name;
            this.budget=budget;
        }

        public String getName() {
            return name;
        }

        public String getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }

        public String getRestaurant() {
            return restaurant;
        }

        public void setRestaurant(String restaurant) {
            this.restaurant = restaurant;
        }

        public MenuItem getMenu() {
            return menu;
        }

        public void setMenu(MenuItem menuItem) {
            this.menu = menuItem;
        }

        public void addToOrderHistory(String[] order) {
            orderHistory.add(order);
        }

        public List<String[]> getOrderHistory() {
            return orderHistory;
        }

        public String getLastRestaurant() {
            return lastRestaurant;
        }

        public void setLastRestaurant(String lastRestaurant) {
            this.lastRestaurant = lastRestaurant;
        }
        public void incrementFoodCounter(String food) {
            foodCounters.put(food, foodCounters.getOrDefault(food, 0) + 1);
        }

        public int getFoodCounter(String food) {
            return foodCounters.getOrDefault(food, 0);
        }


        public void removeOrderHistoryEntry(String day) {
            Iterator<String[]> iterator = orderHistory.iterator();
            while (iterator.hasNext()) {
                String[] entry = iterator.next();
                if (entry[0].equals(day)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }
}
