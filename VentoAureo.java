import java.util.*;

class VentoAureo {
    private static Map<String, List<String>> districts = new HashMap<>();
    private static List<String> uncategorized = new ArrayList<>();
    private static Map<String, Map<String, Integer>> roads = new HashMap<>();
    private static int districtCount = 1;

    public static void main(String[] args) {
        initializeGraph();
        Scanner scanner = new Scanner(System.in);

        while (!uncategorized.isEmpty() || districts.size() > 1) {
            System.out.print("Combine: ");
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase(" exit") || line.equalsIgnoreCase("exit ") ) {
                System.out.println("=============================================================================================================");
                break;
            }
            String[] arr = line.split(" & ");
            String location1 = arr[0];
            String location2 = arr[1];
            combineLocations(location1, location2);
            System.out.println("=============================================================================================================");
        }
    }

    private static void initializeGraph() {
        addTown("Town Hall");
        addTown("Cafe Deux Magots");
        addTown("Jade Garden");
        addTown("Morioh Grand Hotel");
        addTown("Polnareff Land");
        addTown("Savage Garden");
        addTown("Joestar Mansion");

        addRoad("Town Hall", "Cafe Deux Magots", 4);
        addRoad("Town Hall", "Jade Garden", 5);
        addRoad("Town Hall", "Morioh Grand Hotel", 5);
        addRoad("Morioh Grand Hotel", "Jade Garden", 3);
        addRoad("Cafe Deux Magots", "Polnareff Land", 4);
        addRoad("Cafe Deux Magots", "Jade Garden", 3);
        addRoad("Cafe Deux Magots", "Savage Garden", 4);
        addRoad("Jade Garden", "Joestar Mansion", 2);
        addRoad("Savage Garden", "Joestar Mansion", 4);
        addRoad("Polnareff Land", "Savage Garden", 6);
    }

    private static void addTown(String town) {
        districts.put(town, new ArrayList<>());
        uncategorized.add(town);
    }

    private static void addRoad(String town1, String town2, int distance) {
        roads.computeIfAbsent(town1, k -> new HashMap<>()).put(town2, distance);
        roads.computeIfAbsent(town2, k -> new HashMap<>()).put(town1, distance);
    }

    private static void combineLocations(String location1, String location2) {

        if (!roads.containsKey(location1) || !roads.containsKey(location2)) {
            System.out.println("Invalid input: One or both locations do not match any town.");
            return;
        }

        if (!areConnected(location1, location2)) {
            System.out.println(location1 + " and " + location2 + " are not connected by a road!");
            return;
        }

        String district1 = findDistrict(location1);
        String district2 = findDistrict(location2);

        if (district1 == null && district2 == null) {
            createNewDistrict(location1, location2);
        } else if (district1 != null && district2 == null) {
            addToExistingDistrict(district1, location2);
        } else if (district1 == null && district2 != null) {
            addToExistingDistrict(district2, location1);
        } else {
            mergeDistricts(district1, district2);
        }

        printDistrictsAndUncategorized();
    }

    private static boolean areConnected(String location1, String location2) {
        return roads.containsKey(location1) && roads.get(location1).containsKey(location2);
    }

    private static String findDistrict(String location) {
        for (Map.Entry<String, List<String>> entry : districts.entrySet()) {
            if (entry.getValue().contains(location)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static void createNewDistrict(String location1, String location2) {
        List<String> newDistrict = new ArrayList<>();
        newDistrict.add(location1);
        newDistrict.add(location2);
        districts.put("District " + districtCount, newDistrict);
        uncategorized.remove(location1);
        uncategorized.remove(location2);
        districtCount++;
    }

    private static void addToExistingDistrict(String district, String newLocation) {
        List<String> districtLocations = districts.getOrDefault(district, new ArrayList<>());
        districtLocations.add(newLocation);
        districts.put(newLocation, districtLocations);
        uncategorized.remove(newLocation);
        districts.remove(district);
    }

    private static void mergeDistricts(String district1, String district2) {
        List<String> mergedDistrict = new ArrayList<>(districts.get(district2));
        mergedDistrict.addAll(districts.get(district1));
        districts.put(district2, mergedDistrict);
        districts.remove(district1);
    }

    private static void printDistrictsAndUncategorized() {
        int districtIndex = 1;
        for (Map.Entry<String, List<String>> entry : districts.entrySet()) {
            List<String> district = entry.getValue();
            if (!district.isEmpty()) {
                System.out.println("District " + districtIndex + ": " + district + " (" + district.size() + " locations)");
                districtIndex++;
            }
        }
        if ( uncategorized.size() > 0) {
            System.out.println("Uncategorised: " + uncategorized + " (" + uncategorized.size() + " locations)");
        }

        if(uncategorized.size() == 0 && (districtIndex-1) == 1) {
            System.out.println("=============================================================================================================");
            System.exit(0);
        }
    }
}
