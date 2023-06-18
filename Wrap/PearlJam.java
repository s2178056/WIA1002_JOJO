import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class PearlJam {

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
                List<Resident> naAgesM = new ArrayList<>();
                List<Resident> naAgesF = new ArrayList<>();

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
                        naAgesM.add(resident);
                    }
                }
                males.removeAll(naAgesM);

                for (Resident resident : females) {
                    if (resident.getAge().equals("N/A")) {
                        naAgesF.add(resident);
                    }
                }
                females.removeAll(naAgesF);

                while (!males.isEmpty() || !females.isEmpty() || !naAgesM.isEmpty() || !naAgesF.isEmpty()) {
                    if(males.isEmpty() && !naAgesM.isEmpty()){
                        orderProcessingList.add(naAgesM.remove(0));
                    }
                    if(females.isEmpty() && !naAgesF.isEmpty()){
                        orderProcessingList.add(naAgesF.remove(0));
                    }
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
                int direction = 1; // 1 represents forward direction, -1 represents reverse direction
                while (!waitingListCopy2.isEmpty()) {
                    index %= waitingListCopy2.size();

                    if (index >= 0 && index < waitingListCopy2.size()) {
                        orderProcessingList.add(waitingListCopy2.remove(index));
                    }

                    if (direction == 1) {
                        index += dayNumber -1;
                    } else {
                        index -= dayNumber;
                    }

                    // Change direction when reaching the end of the queue
                    if (index >= waitingListCopy2.size()) {
                        direction = -1;
                        index = waitingListCopy2.size() - dayNumber; // Start from the second-to-last person
                    } else if (index < 0){
                        direction = 1;
                        index = dayNumber - 1;
                    }
                }
                break;
            default:
                break;
        }
        return orderProcessingList;
    }

    private static int getDayCount() {   //*** to amend
        return jojoLand.getDayCount() + 1;
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
        System.out.println("=================================================================================");
    }

    static class Resident {

        private String name;
        private String age;
        private String gender;
        private String restaurant;
        private String order;
        LocalTime time;

        public Resident(String name, String age, String gender,String order,LocalTime time) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.order = order;
            this.time = time;
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

        public String getOrder(){return order;}

        public LocalTime getTime(){return time;}

    }

}