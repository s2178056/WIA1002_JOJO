import java.util.*;
import java.util.stream.Collectors;

class JoestarRestaurantSystem {
    private Map<String, Restaurant> restaurants;
    private Map<String, Resident> residents;
    private Map<String, Double> residentBudgets;
    private Map<String, Restaurant> previousRestaurants;

    public JoestarRestaurantSystem() {
        initializeRestaurants();
        initializeResidents();
        initializeResidentBudgets();
        previousRestaurants = new HashMap<>();
    }

    private void initializeRestaurants() {
        restaurants = new HashMap<>();

        Restaurant jadeGarden = new Restaurant("Jade Garden");
        jadeGarden.addFoodToMenu(new Food("Braised Chicken in Black Bean Sauce", "Jade Garden", 15.00));
        jadeGarden.addFoodToMenu(new Food("Braised Goose Web with Vermicelli", "Jade Garden", 21.00));
        jadeGarden.addFoodToMenu(new Food("Deep-fried Hiroshima Oysters", "Jade Garden", 17.00));
        jadeGarden.addFoodToMenu(new Food("Poached Tofu with Dried Shrimps", "Jade Garden", 12.00));
        jadeGarden.addFoodToMenu(new Food("Scrambled Egg White with Milk", "Jade Garden", 10.00));

        Restaurant cafeDeuxMagots = new Restaurant("Cafe Deux Magots");
        cafeDeuxMagots.addFoodToMenu(new Food("Sampling Matured Cheese Platter", "Cafe Deux Magots", 23.00));
        cafeDeuxMagots.addFoodToMenu(new Food("Spring Lobster Salad", "Cafe Deux Magots", 35.00));
        cafeDeuxMagots.addFoodToMenu(new Food("Spring Organic Omelette", "Cafe Deux Magots", 23.00));
        cafeDeuxMagots.addFoodToMenu(new Food("Truffle-flavoured Poultry Supreme", "Cafe Deux Magots", 34.00));
        cafeDeuxMagots.addFoodToMenu(new Food("White Asparagus", "Cafe Deux Magots", 26.00));

        Restaurant trattoriaTrussardi = new Restaurant("Trattoria Trussardi");
        trattoriaTrussardi.addFoodToMenu(new Food("Caprese Salad", "Trattoria Trussardi", 10.00));
        trattoriaTrussardi.addFoodToMenu(new Food("Creme caramel", "Trattoria Trussardi", 6.50));
        trattoriaTrussardi.addFoodToMenu(new Food("Lamb Chops with Apple Sauce", "Trattoria Trussardi", 25.00));
        trattoriaTrussardi.addFoodToMenu(new Food("Spaghetti alla Puttanesca", "Trattoria Trussardi", 15.00));

        Restaurant liberrio = new Restaurant("Liberrio");
        liberrio.addFoodToMenu(new Food("Formaggio", "Liberrio", 12.50));
        liberrio.addFoodToMenu(new Food("Ghiaccio", "Liberrio", 1.01));
        liberrio.addFoodToMenu(new Food("Melone", "Liberrio", 5.20));
        liberrio.addFoodToMenu(new Food("Prosciutto and Pesci", "Liberrio", 20.23));
        liberrio.addFoodToMenu(new Food("Risotto", "Liberrio", 13.14));
        liberrio.addFoodToMenu(new Food("Zucchero and Sale", "Liberrio", 0.50));

        Restaurant savageGarden = new Restaurant("Savage Garden");
        savageGarden.addFoodToMenu(new Food("Abbacchio’s Tea", "Savage Garden", 1.00));
        savageGarden.addFoodToMenu(new Food("DIO’s Bread", "Savage Garden", 36.14));
        savageGarden.addFoodToMenu(new Food("Giorno’s Donuts", "Savage Garden", 6.66));
        savageGarden.addFoodToMenu(new Food("Joseph’s Tequila", "Savage Garden", 35.00));
        savageGarden.addFoodToMenu(new Food("Kakyoin’s Cherry", "Savage Garden", 3.50));
        savageGarden.addFoodToMenu(new Food("Kakyoin’s Porridge", "Savage Garden", 4.44));

        restaurants.put(jadeGarden.getName(), jadeGarden);
        restaurants.put(cafeDeuxMagots.getName(), cafeDeuxMagots);
        restaurants.put(trattoriaTrussardi.getName(), trattoriaTrussardi);
        restaurants.put(liberrio.getName(), liberrio);
        restaurants.put(savageGarden.getName(), savageGarden);
    }

    private void initializeResidents() {
        residents = new HashMap<>();

        Resident jonathan = new Resident("Jonathan Joestar");
        residents.put(jonathan.getName(), jonathan);

        Resident joseph = new Resident("Joseph Joestar");
        residents.put(joseph.getName(), joseph);

        Resident jotaro = new Resident("Jotaro Kujo");
        residents.put(jotaro.getName(), jotaro);

        Resident josuke = new Resident("Josuke Higashikata");
        residents.put(josuke.getName(), josuke);

        Resident giorno = new Resident("Giorno Giovanna");
        residents.put(giorno.getName(), giorno);

        Resident jolyne = new Resident("Jolyne Cujoh");
        residents.put(jolyne.getName(), jolyne);
    }

    private void initializeResidentBudgets() {
        residentBudgets = new HashMap<>();
        residentBudgets.put("Jonathan Joestar", 0.0);
        residentBudgets.put("Joseph Joestar", 0.0);
        residentBudgets.put("Jotaro Kujo", 0.0);
        residentBudgets.put("Josuke Higashikata", 100.0);
        residentBudgets.put("Giorno Giovanna", 100.0);
        residentBudgets.put("Jolyne Cujoh", 0.0);
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        String currentLocation = "Joestar Mansion";

        while (true) {
            System.out.println("Current Location: " + currentLocation);
            System.out.println("[1] Move to:");
            System.out.println("[A] Jade Garden   [B] Libeccio   [C] Savage Garden   [D] Vineyard");
            System.out.println("[2] View Resident Information");
            System.out.println("[3] Back (Savage Garden)");
            System.out.println("[4] Back to Town Hall");
            System.out.print("Select: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Not related.");
                    break;
                case "2":
                    viewResidentInformation(scanner);
                    break;
                case "3":
                    currentLocation = "Savage Garden";
                    break;
                case "4":
                    System.out.println("Exiting the system...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            System.out.println();
        }
    }

    private void viewResidentInformation(Scanner scanner) {
        System.out.println("======================================================================");
        System.out.println("Resident Information in Joestar Mansion");
        System.out.println("+----+----------------+-----+--------+------------------------------+-");
        System.out.println("| No | Name           | Age | Gender | Parents                      |");
        System.out.println("+----+----------------+-----+--------+------------------------------+-");
        System.out.println("| 7  | Joseph Joestar | 18  | Male   | George Joestar II, Lisa Lisa |");
        System.out.println("+----+----------------+-----+--------+------------------------------+-");
        System.out.println("-+---------------+ ...");
        System.out.println("| Stand         | ...");
        System.out.println("-+---------------+ ...");
        System.out.println("[1] View Resident's Profile");
        System.out.println("[2] Sort");
        System.out.println("[3] Exit");
        System.out.print("Select: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                String residentName = getResidentName(scanner);
                Resident resident = residents.get(residentName);
                if (resident != null) {
                    viewResidentProfile(resident);
                } else {
                    System.out.println("Resident not found.");
                }
                break;
            case "2":
                System.out.println("Not implemented yet.");
                break;
            case "3":
                System.out.println("Exiting the system...");
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private String getResidentName(Scanner scanner) {
        System.out.print("Enter the resident's name: ");
        return scanner.nextLine();
    }

    private void viewResidentProfile(Resident resident) {
        System.out.println("======================================================================");
        System.out.println(resident.getName() + "'s Profile");
        System.out.println("Name : " + resident.getName());
        System.out.println("Age : 18"); // Assuming the age is always 18 for simplicity
        System.out.println("Gender : Male"); // Assuming the gender is always Male for simplicity
        System.out.println("Parents : George Joestar II, Lisa Lisa"); // Assuming the parents are always the same for simplicity
        System.out.println("Stand : Hermit Purple"); // Assuming the Stand is always Hermit Purple for simplicity
        System.out.println("Destructive Power : D"); // Assuming the Stand stats are always the same for simplicity
        System.out.println("Speed : C");
        System.out.println("Range : D");
        System.out.println("Stamina : A");
        System.out.println("Precision : D");
        System.out.println("Development Potential : E");
        System.out.println("\nOrder History");
        System.out.println("+-----+---------------------------------+---------------------+");
        System.out.println("| Day | Food                            | Restaurant          |");
        System.out.println("+-----+---------------------------------+---------------------+");

        List<String> foodHistory = resident.getFoodHistory();
        List<String> restaurantHistory = resident.getRestaurantHistory();
        int historySize = Math.min(foodHistory.size(), restaurantHistory.size());

        for (int i = 0; i < historySize; i++) {
            String food = foodHistory.get(i);
            String restaurant = restaurantHistory.get(i);
            System.out.printf("| %-3d | %-31s | %-19s |%n", i + 1, food, restaurant);
        }

        System.out.println("+-----+---------------------------------+---------------------+");

    }

    private void selectRandomFoodAndRestaurantForResidents() {
        for (Resident resident : residents.values()) {
            //Random random = new Random();
            //int randomIndex = random.nextInt(restaurants.size());
            //Restaurant restaurant = (Restaurant) restaurants.values().toArray()[randomIndex];
            //List<Food> menu = restaurant.getMenu();
            //Food food = menu.get(random.nextInt(menu.size()));
            switch (resident.getName()) {
                case "Jonathan Joestar":
                    selectFoodAndRestaurantForJonathan(resident);
                    break;
                case "Joseph Joestar":
                    selectFoodAndRestaurantForJoseph(resident);
                    break;
                case "Jotaro Kujo":
                    selectFoodAndRestaurantForJotaro(resident);
                    break;
                case "Josuke Higashikata":
                    selectFoodAndRestaurantForJosuke(resident);
                    break;
                case "Giorno Giovanna":
                    selectFoodAndRestaurantForGiorno(resident);
                    break;
                case "Jolyne Cujoh":
                    selectFoodAndRestaurantForJolyne(resident);
                    break;
                default:
                    selectRandomFoodAndRestaurantForResident(resident);
            }
        }
    }

    private void selectFoodAndRestaurantForJonathan(Resident resident) {
        Map<String, Integer> foodFrequency = new HashMap<>();
        List<Food> availableFood = new ArrayList<>();
        List<Food> menu = new ArrayList<>();
        for (Food food : menu) {
            int frequency = foodFrequency.getOrDefault(food.getName(), 0);
            if (frequency == 0) {
                availableFood.add(food);
            }
        }

        if (!availableFood.isEmpty()) {
            Food selectedFood = availableFood.get(new Random().nextInt(availableFood.size()));
            resident.addRestaurantToHistory(selectedFood.getRestaurant());
            resident.addFoodToHistory(selectedFood.getName());
            System.out.println("Food selected for " + resident.getName() + ": " + selectedFood.getName()
                    + " from " + selectedFood.getRestaurant());
        } else {
            // Restart the food selection process for Jonathan since he has eaten all available food
            foodFrequency.clear();
            selectFoodAndRestaurantForJonathan(resident);
    }
    }

    /*private Food selectFoodFairly(List<Food> menu, Map<String, Integer> foodFrequency) {
        int minFrequency = Integer.MAX_VALUE;
        int maxFrequency = Integer.MIN_VALUE;

        for (Food food : menu) {
            int frequency = foodFrequency.getOrDefault(food.getName(), 0);
            if (frequency < minFrequency) {
                minFrequency = frequency;
            }
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
            }
        }

        List<Food> availableFood = new ArrayList<>();
        for (Food food : menu) {
            int frequency = foodFrequency.getOrDefault(food.getName(), 0);
            if (frequency == minFrequency || (frequency == maxFrequency && maxFrequency - minFrequency <= 1)) {
                availableFood.add(food);
            }
        }

        if (!availableFood.isEmpty()) {
            return availableFood.get(new Random().nextInt(availableFood.size()));
        }

        return null;
    }*/

    private List<Restaurant> getUntriedRestaurants(Resident resident) {
        List<Restaurant> untriedRestaurants = new ArrayList<>(restaurants.values());
        untriedRestaurants.removeAll(resident.getRestaurantHistory().stream()
                .map(restaurants::get)
                .collect(Collectors.toList()));
        return untriedRestaurants;
    }

    private void selectFoodAndRestaurantForJoseph(Resident resident) {
        List<Food> untriedFood = new ArrayList<>();
        for (Restaurant restaurant : restaurants.values()) {
            for (Food food : restaurant.getMenu()) {
                if (!resident.getFoodHistory().contains(food.getName())) {
                    untriedFood.add(food);
                }
            }
        }

        if (!untriedFood.isEmpty()) {
            Food selectedFood = untriedFood.get(new Random().nextInt(untriedFood.size()));
            resident.addFoodToHistory(selectedFood.getName());
            System.out.println(resident.getName() + ": Food selected: " + selectedFood.getName());
        } else {
            System.out.println("No available untried food for " + resident.getName());
        }
    }

    private void selectFoodAndRestaurantForJotaro(Resident resident) {
        List<Restaurant> untriedRestaurants = getUntriedRestaurants(resident);

        if (!untriedRestaurants.isEmpty()) {
            Restaurant selectedRestaurant = untriedRestaurants.get(0);
            List<Food> menu = selectedRestaurant.getMenu();

            if (!menu.isEmpty()) {
                for (Food food : menu) {
                    resident.addFoodToHistory(food.getName());
                }
                resident.addRestaurantToHistory(selectedRestaurant.getName());
                System.out.println("Food selected for " + resident.getName() + ": All dishes from "
                        + selectedRestaurant.getName());
            } else {
                System.out.println("No available food for " + resident.getName());
            }
        } else {
            System.out.println("No available untried restaurants for " + resident.getName());
        }
    }

    private void selectFoodAndRestaurantForJosuke(Resident resident) {
        double remainingBudget = 100 - resident.getFoodHistory().stream()
                .mapToDouble(foodName -> getFoodByName(foodName).getPrice())
                .sum();

        List<Food> affordableFood = new ArrayList<>();
        for (Restaurant restaurant : restaurants.values()) {
            for (Food food : restaurant.getMenu()) {
                if (food.getPrice() <= remainingBudget) {
                    affordableFood.add(food);
                }
            }
        }

        if (!affordableFood.isEmpty()) {
            Food selectedFood = affordableFood.get(new Random().nextInt(affordableFood.size()));
            resident.addFoodToHistory(selectedFood.getName());
            System.out.println("Food selected for " + resident.getName() + ": " + selectedFood.getName());
        } else {
            System.out.println("No affordable food for " + resident.getName());
        }
    }

    private Food getFoodByName(String foodName) {
        for (Restaurant restaurant : restaurants.values()) {
            for (Food food : restaurant.getMenu()) {
                if (food.getName().equals(foodName)) {
                    return food;
                }
            }
        }
        return null; //food not found
    }

    private void selectFoodAndRestaurantForGiorno(Resident resident) {
        Restaurant trussardiRestaurant = restaurants.get("Trattoria Trussardi");
        if (trussardiRestaurant == null) {
            System.out.println("Restaurant 'Trattoria Trussardi' not found for " + resident.getName());
            return;
        }

        List<Food> availableFood = new ArrayList<>();
        for (Food food : trussardiRestaurant.getMenu()) {
            if (!resident.getFoodHistory().contains(food.getName()) || trussardiRestaurant.getMenu().size() == 1) {
                availableFood.add(food);
            }
        }

        if (!availableFood.isEmpty()) {
            Food selectedFood = availableFood.get(new Random().nextInt(availableFood.size()));
            resident.addFoodToHistory(selectedFood.getName());
            resident.addRestaurantToHistory(selectedFood.getRestaurant());
            System.out.println("Food selected for " + resident.getName() + ": " + selectedFood.getName()
                    + " from Trattoria Trussardi");
        } else {
            System.out.println("No available untried food for " + resident.getName() + " at Trattoria Trussardi");
        }
    }

    private void selectFoodAndRestaurantForJolyne(Resident resident) {
        if (resident.getRestaurantHistory().size() >= 1) {
            String lastRestaurant = resident.getRestaurantHistory().get(resident.getRestaurantHistory().size() - 1);
            List<Restaurant> availableRestaurants = new ArrayList<>();
            for (Restaurant restaurant : restaurants.values()) {
                if (!restaurant.getName().equals(lastRestaurant)) {
                    availableRestaurants.add(restaurant);
                }
            }

            if (!availableRestaurants.isEmpty()) {
                Restaurant selectedRestaurant = availableRestaurants.get(new Random().nextInt(availableRestaurants.size()));
                List<Food> menu = selectedRestaurant.getMenu();

                if (!menu.isEmpty()) {
                    Food selectedFood = menu.get(new Random().nextInt(menu.size()));
                    resident.addFoodToHistory(selectedFood.getName());
                    resident.addRestaurantToHistory(selectedRestaurant.getName());
                    System.out.println("Food selected for " + resident.getName() + ": " + selectedFood.getName()
                            + " from " + selectedRestaurant.getName());
                } else {
                    System.out.println("No available food for " + resident.getName());
                }
            } else {
                System.out.println("No available restaurants for " + resident.getName());
            }
        } else {
            System.out.println("No previous restaurant history found for " + resident.getName());
        }
    }

    private void selectRandomFoodAndRestaurantForResident(Resident resident) {
        Random random = new Random();
        int randomIndex = random.nextInt(restaurants.size());
        Restaurant restaurant = (Restaurant) restaurants.values().toArray()[randomIndex];
        List<Food> menu = restaurant.getMenu();
        Food food = menu.get(random.nextInt(menu.size()));

        resident.addFoodToHistory(food.getName());
        resident.addRestaurantToHistory(food.getRestaurant());
    }
    /*private void selectRandomFoodAndRestaurantForResident(Resident resident) {
        List<Restaurant> availableRestaurants = new ArrayList<>(restaurants.values());
        Restaurant previousRestaurant = previousRestaurants.get(resident.getName());

        if (previousRestaurant != null && availableRestaurants.contains(previousRestaurant)) {
            availableRestaurants.remove(previousRestaurant);
        }

        if (!availableRestaurants.isEmpty()) {
            Restaurant selectedRestaurant = availableRestaurants.get(new Random().nextInt(availableRestaurants.size()));
            List<Food> menu = selectedRestaurant.getMenu();

            if (!menu.isEmpty()) {
                Food selectedFood = menu.get(new Random().nextInt(menu.size()));
                resident.addRestaurantToHistory(selectedRestaurant.getName());
                resident.addFoodToHistory(selectedFood.getName());
                previousRestaurants.put(resident.getName(), selectedRestaurant);
                System.out.println("Food selected for " + resident.getName() + ": " + selectedFood.getName()
                        + " from " + selectedRestaurant.getName());
            } else {
                System.out.println("No available food for " + resident.getName());
            }
        } else {
            System.out.println("No available restaurants for " + resident.getName());
        }
    }*/

    void start() {
        while (true) {
            for (Resident resident : residents.values()) {
                selectRandomFoodAndRestaurantForResident(resident);
            }
            run();
            System.out.println("[1] Continue to Next Day");
            System.out.println("[2] Exit");
            System.out.print("Select: ");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            if (choice.equals("2")) {
                break;
            }
        }
    }
}
