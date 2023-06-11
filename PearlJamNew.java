import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class PearlJamNew {

    public static List<Resident> getWaitingList(int currDay,String restaurant) {

        List<Resident> waitingList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("foodHistory.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int day = Integer.parseInt(data[0].trim());
                String rest = data[4].trim();
                if (currDay == day && restaurant.equals(rest)) {
                    String name = data[1].trim();
                    String age = data[2].trim();
                    String gender = data[3].trim();
                    String order = data[5].trim();
                    LocalTime time = LocalTime.parse(data[7].trim());
                    waitingList.add(new Resident(name, age, gender, order,time));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitingList.sort(Comparator.comparing(Resident::getTime));
        return waitingList;
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
                        orderProcessingList.add(males.remove(males.size() - 1));
                    }
                    if (!females.isEmpty()) {
                        orderProcessingList.add(females.remove(females.size() - 1));
                    }
                }
                // Add the naAges list to the end of the orderProcessingList
                orderProcessingList.addAll(naAges);
                break;
            case "Libeccio":
                List<Resident> waitingListCopy = new ArrayList<>(waitingList);
                int dayNumber = getDayCount();
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
                dayNumber = getDayCount();

                index = dayNumber - 1;
                while (!waitingListCopy2.isEmpty()) {
                    index %= waitingListCopy2.size();
                    orderProcessingList.add(waitingListCopy2.remove(index));
                    index += dayNumber - 1;
                }
                break;
            default:
                break;
        }
        return orderProcessingList;
    }

    private static int getDayCount() {   //*** to amend
        return 1;
    }


    public static void printWaitingList(List<Resident> waitingList) {

        System.out.println("Waiting List");
        System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        System.out.println("| No | Name                    | Age | Gender | Order                                    |");
        System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        int count = 0;
        for (Resident resident : waitingList) {
            count++;
            System.out.printf("| %-2d | %-24s| %-3s | %-6s | %-40s |\n", count, resident.getName(), resident.getAge(), resident.getGender(), resident.getOrder());
        }

        if (count == 0) {
            System.out.println("| No residents in the waiting list");
            System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        } else {
            System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        }
    }

    public static void printOrderProcessingList(List<Resident> orderProcessingList) {
        System.out.println("\nOrder Processing List");
        System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        System.out.println("| No | Name                    | Age | Gender | Order                                    |");
        System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        int count = 0;
        for (Resident resident : orderProcessingList) {
            count++;
            System.out.printf("| %-2d | %-24s| %-3s | %-6s | %-40s |\n", count, resident.getName(), resident.getAge(), resident.getGender(), resident.getOrder());

        }
        if (count == 0) {
            System.out.println("| No residents in the order processing list");
            System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        } else {
            System.out.println("+----+-------------------------+-----+--------+------------------------------------------+");
        }
        System.out.println("=====================================================================================================");
    }

    public static void main(String[] args) {   //*** to amend
        String restaurant = "Jade Garden";  // to amend
        int day = getDayCount();
        System.out.println("Restaurant: " + restaurant + "\n");
        List<Resident> waitingList = getWaitingList(day, restaurant);
        printWaitingList(waitingList);
        printOrderProcessingList(generateOrderProcessingList(waitingList,restaurant));
    }


    static class Resident {
//        private static List<Resident> allResidents = new ArrayList<>();
        private String name;
        private String age;
        private String gender;
        private String restaurant;
        private String order;
        LocalTime time;
//        private static List<String[]> orderHistory;
//        private Map<String, Integer> foodCounters;
//        private String lastRestaurant;
//        private int foodCount;

        public Resident(String name, String age, String gender,String order,LocalTime time) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.order = order;
            this.time = time;
        }

//        public Resident(String name) {
//            this.name = name;
//            this.orderHistory = new ArrayList<>();
//            this.lastRestaurant = "";
//            this.foodCounters = new HashMap<>();
//            foodCount = 0;
//            allResidents.add(this);
//        }
//
//        public Resident(String name, double budget) {
//            this.name = name;
//            this.budget = budget;
//        }

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

        public String getOrder(){return order;}

        public LocalTime getTime(){return time;}
//        public MenuItem getMenu() {
//            return menu;
//        }
//
//        public void setMenu(MenuItem menuItem) {
//            this.menu = menuItem;
//        }
//
//        public void addToOrderHistory(String[] order) {
//            orderHistory.add(order);
//        }
//
//        public static List<String[]> getOrderHistory() {
//            return orderHistory;
//        }
//
//        public static List<Resident> getAllResidents() {
//            return allResidents;
//        }
//
//        public static void setAllResident(List<Resident> list){
//            allResidents=list;
//        }
//
//        public String getLastRestaurant() {
//            return lastRestaurant;
//        }
//
//        public void setLastRestaurant(String lastRestaurant) {
//            this.lastRestaurant = lastRestaurant;
//        }
//
//        public void incrementFoodCounter(String food) {
//            foodCounters.put(food, foodCounters.getOrDefault(food, 0) + 1);
//        }
//
//        public int getFoodCounter(String food) {
//            return foodCounters.getOrDefault(food, 0);
//        }
//
//
//        public void removeOrderHistoryEntry(String day) {
//            Iterator<String[]> iterator = orderHistory.iterator();
//            while (iterator.hasNext()) {
//                String[] entry = iterator.next();
//                if (entry[0].equals(day)) {
//                    iterator.remove();
//                    break;
//                }
//            }
//        }
    }

}
