import java.util.*;
public class VentoAureo {
    private static Map<String, List<String>> districts = new HashMap<>();
    private static List<String> uncategorized = new ArrayList<>();
    private static Map<String, Map<String, Integer>> roads = new HashMap<>();
    private static int districtCount = 1;
    private static boolean shouldReturn = false;
    private static boolean shouldEnd = false;
    private static boolean flag = false;

    public static void main(String[] args,Graph jojoMap) {
        Graph map=jojoMap;
        List<String> town=map.getAllTowns();
        List<Graph.Edge> road=map.getAllRoads();
        if(!flag)
            initializeGraph(town,road);
        Scanner scanner = new Scanner(System.in);
        while (!uncategorized.isEmpty() || districts.size() > 1) {
            System.out.print("Combine: ");
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase(" exit") || line.equalsIgnoreCase("exit ") ) {
                System.out.println("=================================================================================");
                shouldReturn = true;
                break;
            }
            if(!line.contains("&")){
                System.out.println("Invalid Input");
                continue;
            }
            String[] arr = line.split(" & ");
            String location1 = arr[0];
            String location2 = arr[1];
            combineLocations(location1, location2);
            System.out.println("=================================================================================");

            if(shouldEnd)
                return;
        }
    }

    private static void initializeGraph(List<String> towns,List<Graph.Edge> roads) {
       for (String town:towns){
           addTown(town);
       }
       for (Graph.Edge road:roads){
           addRoad(road.getSource(),road.getDestination(),road.getDistance());
       }
       flag = true;
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
            shouldEnd = true;
        }
    }

    public static boolean shouldReturn() {
        return shouldReturn;
    }
    public static boolean shouldEnd() {
        return shouldEnd;
    }
}

