import java.util.*;

public class Graph {
    private static Map<String, List<Edge>> graph;

    public Graph() {
        graph = new HashMap<>();
    }

    public void addTown(String town) {
        graph.put(town, new ArrayList<>());
    }

    public void addRoad(String source, String destination, int distance) {
        Edge road = new Edge(source, destination, distance);
        Edge reverseRoad = new Edge(destination, source, distance);
        graph.get(source).add(road);
        graph.get(destination).add(reverseRoad); // Add reverse road for bidirectional edges
    }

    public List<String> getChoices(String location) {
        List<String> choices = new ArrayList<>();
        List<Edge> roads = graph.get(location);
        for (Edge road : roads) {
            if (road.source.equals(location)) {
                choices.add(road.destination);
            } else if (road.destination.equals(location)) {
                choices.add(road.source);
            }
        }
        return choices;
    }

    public List<Edge> getRoads(String town) {
        return graph.get(town);
    }

    public int getRoadDistance(String source, String destination) {
        List<Edge> roads = graph.get(source);
        for (Edge road : roads) {
            if (road.destination.equals(destination)) {
                return road.distance;
            }
        }
        return -1; // Indicates no direct road exists between the source and destination
    }

    public List<String> findShortestPathThroughALl(List<String> locations) {
        List<String> shortestPath = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();
        Set<String> unvisitedLocations = new HashSet<>(locations);
        int shortestDistance = Integer.MAX_VALUE;
        // Start the backtracking with dynamic programming
        backtrack(locations.get(0), currentPath, unvisitedLocations, shortestPath, 0, shortestDistance);

        return shortestPath;
    }

    private void backtrack(String currentLocation, List<String> currentPath, Set<String> unvisitedLocations, List<String> shortestPath, int currentDistance, int shortestDistance) {

        // Base case: All locations visited
        if (unvisitedLocations.isEmpty()) {
            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                shortestPath.clear();
                shortestPath.addAll(currentPath);
            }
            return;
        }

        // Try all possible next locations
        for (String nextLocation : unvisitedLocations) {
            int distanceToNextLocation = getRoadDistance(currentLocation, nextLocation);

            // If a road exists between current and next locations
            if (distanceToNextLocation > 0) {
                currentPath.add(nextLocation);
                unvisitedLocations.remove(nextLocation);

                // Recursive call with updated parameters
                backtrack(nextLocation, currentPath, unvisitedLocations, shortestPath,
                        currentDistance + distanceToNextLocation, shortestDistance);

                // Backtrack: remove nextLocation from currentPath and add it back to unvisitedLocations
                currentPath.remove(nextLocation);
                unvisitedLocations.add(nextLocation);
            }
        }
    }

    public void displayShortestPaths(List<List<String>> paths) {
        System.out.println("Top Three Shortest Paths:");
        System.out.println("=================================================================================");
        for (int i = 0; i < paths.size(); i++) {
            List<String> path = paths.get(i);
            int distance = calculatePathDistance(path);
            System.out.printf("%d. %s (%d km)%n", i + 1, formatPathString(path), distance);
        }
        System.out.println("=================================================================================");

    }

    private static String formatPathString(List<String> path) {
        return String.join(" -> ", path);
    }

    protected static int calculatePathDistance(List<String> path) {
        int distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            List<Edge> roads = graph.get(path.get(i));
            for (Edge road : roads) {
                if (road.source.equals(path.get(i)) && road.destination.equals(path.get(i + 1))) {
                    distance += road.distance;
                    break;
                }
            }
        }
        return distance;
    }

    protected static List<List<String>> findTopShortestPaths(List<List<String>> paths) {
        List<List<String>> topPaths = new ArrayList<>(paths);
        topPaths.sort(Comparator.comparingInt(path -> calculatePathDistance(path)));

        int limit = Math.min(3, topPaths.size());
        return topPaths.subList(0, limit);
    }

    protected List<List<String>> findAllPaths(String source, String destination) {
        List<List<String>> allPaths = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        findAllPathsDFS(source, destination, currentPath, visited, allPaths);

        return allPaths;
    }

    protected void findAllPathsDFS(String current, String destination, List<String> currentPath, Set<String> visited, List<List<String>> allPaths) {
        currentPath.add(current);
        visited.add(current);

        if (current.equals(destination)) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            List<Edge> roads = graph.get(current);
            for (Edge road : roads) {
                String next = road.destination;
                if (!visited.contains(next)) {
                    findAllPathsDFS(next, destination, currentPath, visited, allPaths);
                }
            }
        }

        currentPath.remove(current);
        visited.remove(current);
    }

    protected static class Edge {
        String source;
        String destination;
        int distance;

        public Edge(String source, String destination, int distance) {
            this.source = source;
            this.destination = destination;
            this.distance = distance;
        }
    }
}
