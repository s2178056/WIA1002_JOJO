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

    public List<Edge> findMinimumSpanningTree() {
        List<Edge> minimumSpanningTree = new ArrayList<>();
        List<Edge> allEdges = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        // Collect all edges from the graph
        for (List<Edge> edges : graph.values()) {
            allEdges.addAll(edges);
        }

        // Sort edges in non-decreasing order of their weights
        allEdges.sort(Comparator.comparingInt(edge -> edge.distance));

        // Apply Kruskal's algorithm
        for (Edge edge : allEdges) {
            if (!visited.contains(edge.source) || !visited.contains(edge.destination)) {
                minimumSpanningTree.add(edge);
                visited.add(edge.source);
                visited.add(edge.destination);
            }
        }

        return minimumSpanningTree;
    }

    public void displayMinimumSpanningTree(List<Edge> minimumSpanningTree) {
        System.out.println("Necessary Power Cables to be Upgraded:");
        int totalDistance = 0;
        for (Edge edge : minimumSpanningTree) {
            System.out.printf("%s - %s (%d km)%n", edge.source, edge.destination, edge.distance);
            totalDistance += edge.distance;
        }
        System.out.println("Total Length: " + totalDistance + " km");
        System.out.println("=================================================================================");
    }

    public static List<Edge> Kruskal(List<Edge> edges) { //implement kruskal's logic

        // Sort edges by weight
        Collections.sort(edges);


        List<String> vertices = new ArrayList<>(); //to store vertices
        List<Integer> parent = new ArrayList<>();// to store parent of vertex

        //to store the edges of the graph
        List<Edge> kruskal = new ArrayList<>();

        for (Edge edge : edges) {
            String vertex1 = edge.source; //source
            String vertex2 = edge.destination; //destination

            int index1 = getIndex(vertices, vertex1);
            int index2 = getIndex(vertices, vertex2);

            //if vertex not found in the list
            if (index1 == -1) {
                vertices.add(vertex1);
                parent.add(vertices.size() - 1);//adds to parent list as well
                index1 = vertices.size() - 1;
            }

            if (index2 == -1) {
                vertices.add(vertex2);
                parent.add(vertices.size() - 1);
                index2 = vertices.size() - 1;
            }

            //check if vertices belong to other vertices
            int parent1 = find(parent, index1);
            int parent2 = find(parent, index2);
            if (parent1 != parent2) {
                kruskal.add(edge);

                //merge components
                union(parent, parent1, parent2);
            }
        }

        return kruskal;
    }

    public static List<Edge> nonKruskal(List<Edge> edges) {
        // Sort edges by weight
        Collections.sort(edges, Collections.reverseOrder());

        List<String> vertices = new ArrayList<>(); // to store vertices
        List<Integer> parent = new ArrayList<>();// to store parent of vertex
        List<Edge> longestPath = new ArrayList<>();


        List<Edge> kruskal = new ArrayList<>(); // to store the edges of the minimum spanning tree
        List<Edge> nonMSTEdges = new ArrayList<>(); // to store the non-MST edges
        Set<String> visitedEdges = new HashSet<>(); // to track visited edges

        for (Edge edge : edges) {
            String vertex1 = edge.source; // source
            String vertex2 = edge.destination; // destination

            int index1 = getIndex(vertices, vertex1);
            int index2 = getIndex(vertices, vertex2);

            // If vertex not found in the list
            if (index1 == -1) {
                vertices.add(vertex1);
                parent.add(vertices.size() - 1); // adds to parent list as well
                index1 = vertices.size() - 1;
            }

            if (index2 == -1) {
                vertices.add(vertex2);
                parent.add(vertices.size() - 1);
                index2 = vertices.size() - 1;
            }

            // Check if vertices belong to other vertices
            int parent1 = find(parent, index1);
            int parent2 = find(parent, index2);
            if (parent1 != parent2) {
                longestPath.add(edge);

                // merge components
                union(parent, parent1, parent2);
                visitedEdges.add(edge.source + "-" + edge.destination); // Mark the edge as visited
            } else {
                boolean reverseEdgeExists = false;
                for (Edge nonMSTEdge : nonMSTEdges) {
                    if (nonMSTEdge.source.equals(edge.destination) && nonMSTEdge.destination.equals(edge.source)) {
                        reverseEdgeExists = true;
                        break;
                    }
                }
                // Check if the reverse edge has been visited
                if (!visitedEdges.contains(edge.destination + "-" + edge.source)&&!reverseEdgeExists) {
                    nonMSTEdges.add(edge);
                }
            }
        }
        return nonMSTEdges;
    }

    public static int getIndex(List<String> vertices, String vertex) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    public static int find(List<Integer> parent, int vertex) {
        //find the parent
        if (parent.get(vertex) != vertex) { //check if have parent different from itself
            parent.set(vertex, find(parent, parent.get(vertex))); //find all parents recursively
        }
        return parent.get(vertex);
    }

    public static void union(List<Integer> parent, int x, int y) {
        parent.set(x, y); //set parent of vertex x to be vertex y
    }

    public void displayKruskal(){
        List<Edge> edges=new ArrayList<>();
        for (List<Edge> alledges : graph.values()) {
            edges.addAll(alledges);
        }
        System.out.println("Necessary Power Cables to be Upgraded: ");
        List<Edge> kruskal = Kruskal(edges);

        int totalLength = 0 ;

        for (int i = 0; i < kruskal.size(); i++) {
            Edge edge = kruskal.get(i);
            totalLength += edge.distance;
            System.out.println((i + 1) + ". " + edge.source + " -- " + edge.destination + "(" + edge.distance + "km)");
        }
        System.out.println();
        System.out.println("Total length: " +totalLength +"km");

    }
    public void displayNonKruskal(){
        List<Edge> edges=new ArrayList<>();
        for (List<Edge> alledges : graph.values()) {
            edges.addAll(alledges);
        }
        System.out.println("Unnecessary Water Connections: ");
        List<Edge> kruskal = nonKruskal(edges);

        int totalLength = 0 ;

        for (int i = 0; i < kruskal.size(); i++) {
            Edge edge = kruskal.get(i);
            totalLength += edge.distance;
            System.out.println((i + 1) + ". " + edge.source + " -- " + edge.destination + "(" + edge.distance + "km)");
        }
        System.out.println();
        System.out.println("Total length: " +totalLength +"km");

    }



    protected static class Edge implements Comparable<Edge> {
        String source;
        String destination;
        int distance;

        public Edge(String source, String destination, int distance) {
            this.source = source;
            this.destination = destination;
            this.distance = distance;
        }

@Override
        public int compareTo(Edge other) {
            return this.distance - other.distance;
        }
    }
    }


