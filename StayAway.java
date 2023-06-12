import java.util.*;

class Town {
    private String name;
    private List<Road> roads;

    public Town(String name) {
        this.name = name;
        this.roads = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void addRoad(Town destination, int distance) {
        roads.add(new Road(destination, distance));
    }
}

class Road {
    private Town destination;
    private int distance;

    public Road(Town destination, int distance) {
        this.destination = destination;
        this.distance = distance;
    }

    public Town getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }
}

class Map {
    private HashMap<String, Town> towns;

    public Map() {
        towns = new HashMap<>();
    }

    public void addTown(String name) {
        towns.put(name, new Town(name));
    }

    public void addRoad(String townName1, String townName2, int distance) {
        Town town1 = towns.get(townName1);
        Town town2 = towns.get(townName2);
        town1.addRoad(town2, distance);
        town2.addRoad(town1, distance);
    }

    public Town getTown(String name) {
        return towns.get(name);
    }
}

class Path {
    private List<Town> towns;
    private int distance;
    private int activationCount;

    public Path() {
        this.towns = new ArrayList<>();
        this.distance = 0;
        this.activationCount = 0;
    }

    public Path(Path path) {
        this.towns = new ArrayList<>(path.towns);
        this.distance = path.distance;
        this.activationCount = path.activationCount;
    }

    public List<Town> getTowns() {
        return towns;
    }

    public void addTown(Town town) {
        towns.add(town);
    }

    public void addDistance(int distance) {
        this.distance += distance;
    }

    public void incrementActivationCount() {
        activationCount++;
    }

    public int getDistance() {
        return distance;
    }

    public int getActivationCount() {
        return activationCount;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Town town : towns) {
            sb.append(town.getName()).append(" > ");
        }
        sb.delete(sb.length() - 3, sb.length()); // Remove the last " > "
        sb.append(" (").append(distance).append(" km)");
        return sb.toString();
    }
}

class ShortestPathFinder {
    private Map map;
    private List<Path> paths;
    private Path shortestPath;

    public ShortestPathFinder(Map map) {
        this.map = map;
        paths = new ArrayList<>();
        shortestPath = null;
    }

    public void findShortestPath(Town source, Town destination, List<Town> identifiedLocations) {
        explorePaths(source, destination, identifiedLocations, new Path());
        if (shortestPath != null) {
            System.out.println("Optimal Path:");
            System.out.println(shortestPath);
            if (shortestPath.getActivationCount() > 0) {
                System.out.println("Number of Activation: " + shortestPath.getActivationCount());
            }
            System.out.println("==============================================================================================================");
        } else {
            System.out.println("No path found from " + source.getName() + " to " + destination.getName());
            System.out.println("==============================================================================================================");
        }
    }

    private void explorePaths(Town currentTown, Town destination, List<Town> identifiedLocations, Path currentPath) {
        currentPath.addTown(currentTown);

        if (currentTown == destination) {
            if (shortestPath == null || currentPath.getActivationCount() < shortestPath.getActivationCount()
                    || (currentPath.getActivationCount() == shortestPath.getActivationCount()
                    && currentPath.getDistance() < shortestPath.getDistance())) {
                shortestPath = currentPath;
            }
            return;
        }

        for (Road road : currentTown.getRoads()) {
            Town nextTown = road.getDestination();
            int distance = road.getDistance();
            boolean isIdentifiedLocation = identifiedLocations.contains(nextTown);

            if (!currentPath.getTowns().contains(nextTown)) {
                Path newPath = new Path(currentPath);
                newPath.addDistance(distance);

                if (isIdentifiedLocation) {
                    newPath.incrementActivationCount();
                }

                explorePaths(nextTown, destination, identifiedLocations, newPath);
            }
        }
    }
}

public class StayAway {
    public static void main(String[] args) {
        Map map = new Map();
        map.addTown("Town Hall");
        map.addTown("Cafe Deux Magots");
        map.addTown("Jade Garden");
        map.addTown("Morioh Grand Hotel");
        map.addTown("Trattoria Trussardi");
        map.addTown("Green Dolphin Street Prison");
        map.addTown("Polnareff Land");
        map.addTown("San Giorgio Maggiore");
        map.addTown("Libeccio");
        map.addTown("Savage Garden");
        map.addTown("Joestar Mansion");
        map.addTown("DIO's Mansion");
        map.addTown("Angelo Rock");
        map.addTown("Vineyard");
        map.addRoad("Town Hall", "Cafe Deux Magots", 4);
        map.addRoad("Town Hall", "Jade Garden", 5);
        map.addRoad("Town Hall", "Morioh Grand Hotel", 5);
        map.addRoad("Morioh Grand Hotel", "Trattoria Trussardi", 6);
        map.addRoad("Morioh Grand Hotel", "Jade Garden", 3);
        map.addRoad("Trattoria Trussardi", "San Giorgio Maggiore", 3);
        map.addRoad("Trattoria Trussardi", "Green Dolphin Street Prison", 6);
        map.addRoad("Cafe Deux Magots", "Polnareff Land", 4);
        map.addRoad("Cafe Deux Magots", "Jade Garden", 3);
        map.addRoad("Cafe Deux Magots", "Savage Garden", 4);
        map.addRoad("Jade Garden", "San Giorgio Maggiore", 4);
        map.addRoad("Jade Garden", "Joestar Mansion", 2);
        map.addRoad("San Giorgio Maggiore", "Libeccio", 4);
        map.addRoad("Libeccio", "Green Dolphin Street Prison", 3);
        map.addRoad("Libeccio", "Joestar Mansion", 6);
        map.addRoad("Libeccio", "Vineyard", 6);
        map.addRoad("Libeccio", "DIO's Mansion", 2);
        map.addRoad("Green Dolphin Street Prison", "Angelo Rock", 2);
        map.addRoad("Polnareff Land", "Savage Garden", 6);
        map.addRoad("Savage Garden", "Joestar Mansion", 4);
        map.addRoad("Savage Garden", "Vineyard", 8);
        map.addRoad("Vineyard", "DIO's Mansion", 3);
        map.addRoad("DIO's Mansion", "Angelo Rock", 2);

        Scanner scanner = new Scanner(System.in);

        System.out.print("Source: ");
        String sourceName = scanner.nextLine();
        System.out.print("Destination: ");
        String destinationName = scanner.nextLine();

        System.out.print("Identified Locations: ");
        String identifiedLocationsStr = scanner.nextLine();
        System.out.println("==============================================================================================================");
        List<Town> identifiedLocations = new ArrayList<>();
        for (String location : identifiedLocationsStr.split(",")) {
            identifiedLocations.add(map.getTown(location.trim()));
        }

        Town source = map.getTown(sourceName);
        Town destination = map.getTown(destinationName);

        ShortestPathFinder pathFinder = new ShortestPathFinder(map);
        pathFinder.findShortestPath(source, destination, identifiedLocations);

        scanner.close();
    }
}
