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

        public List<String> getAllTowns() {
            return new ArrayList<>(graph.keySet());
        }

        public List<Edge> getAllRoads() {
            List<Edge> allRoads = new ArrayList<>();
            for (List<Edge> edges : graph.values()) {
                allRoads.addAll(edges);
            }
            return allRoads;
        }


        protected static String formatPathString(List<String> path) {
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



        public static List<Edge> Kruskal(List<Edge> edges) { //implement kruskal's logic
            edges.sort(Comparator.comparingInt(edge -> edge.distance)); // Sort edges by weight
            List<Edge> minimumSpanningTree = new ArrayList<>(); // to store the edges of the minimum spanning tree
            Map<String, Integer> parent = new HashMap<>(); // to store parent of vertex
            for (Edge edge : edges) {
                String vertex1 = edge.source;
                String vertex2 = edge.destination;
                if (!parent.containsKey(vertex1)) {
                    parent.put(vertex1, parent.size());
                }
                if (!parent.containsKey(vertex2)) {
                    parent.put(vertex2, parent.size());
                }
                int parent1 = parent.get(vertex1);
                int parent2 = parent.get(vertex2);
                if (parent1 != parent2) {
                    minimumSpanningTree.add(edge);
                    for (String vertex : parent.keySet()) {
                        if (parent.get(vertex) == parent1) {
                            parent.put(vertex, parent2);
                        }
                    }
                }
            }
            return minimumSpanningTree;
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
                    if (!visitedEdges.contains(edge.destination + "-" + edge.source) && !reverseEdgeExists) {
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

        public int getRoadDistance(String source, String destination) {
            List<Edge> roads = graph.get(source);
            for (Edge road : roads) {
                if (road.destination.equals(destination)) {
                    return road.distance;
                }
            }
            return -1; // Indicates no direct road exists between the source and destination
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

        public void displayStayOptimalPath(List<String> path){
            System.out.println("Optimal Path:");
            System.out.println("=================================================================================");
            int distance = calculatePathDistance(path);
            System.out.printf(" %s (%d km)%n", formatPathString(path), distance);
            System.out.println("=================================================================================");
        }

        public void displayRohanShortestPaths(List<String> locations) {
            String source = "Morioh Grand Hotel";
            System.out.println("Shortest Path:");
            System.out.println("======================================================================");
                int distance = calculatePathDistance(locations);
                System.out.printf("%s (%d km)%n", formatPathString(locations), distance);

            System.out.println("======================================================================");
        }

        public  List<String> stayAway(List<List<String>> dfs,List<String> arr) {
            List<List<String>> topPaths = new ArrayList<>(dfs);
            topPaths.sort(Comparator.comparingInt(path -> calculatePathDistance(path)));
            Map<List<String>, Integer> identifiedNum = new LinkedHashMap<>();
            for (List<String> stringList : topPaths) {
                for (String path : stringList) {
                    for (String identified : arr) {
                        if (identified.equalsIgnoreCase(path)) {
                            identifiedNum.put(stringList, identifiedNum.getOrDefault(stringList, 0) + 1);
                        }
                    }
                }
            }
            int leastAmount=Integer.MAX_VALUE;
            List<String> optimalPath=new ArrayList<>();
            for (List<String> ls : topPaths) {
                int identifiedCount = identifiedNum.getOrDefault(ls, 0);
                if (identifiedCount < leastAmount) {
                    leastAmount = identifiedCount;
                    optimalPath = ls;
                }
            }
            return optimalPath;
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

        public void displayKruskal() {
            List<Edge> edges = new ArrayList<>();
            for (List<Edge> alledges : graph.values()) {
                edges.addAll(alledges);
            }
            System.out.println("Necessary Power Cables to be Upgraded: ");
            List<Edge> kruskal = Kruskal(edges);

            int totalLength = 0;

            for (int i = 0; i < kruskal.size(); i++) {
                Edge edge = kruskal.get(i);
                totalLength += edge.distance;
                System.out.println((i + 1) + ". " + edge.source + " -- " + edge.destination + "(" + edge.distance + "km)");
            }
            System.out.println();
            System.out.println("Total length: " + totalLength + "km");
            System.out.println("=".repeat(70));
        }

        public void displayNonKruskal() {
            List<Edge> edges = new ArrayList<>();
            for (List<Edge> alledges : graph.values()) {
                edges.addAll(alledges);
            }
            System.out.println("Unnecessary Water Connections: ");
            List<Edge> kruskal = nonKruskal(edges);

            int totalLength = 0;

            for (int i = 0; i < kruskal.size(); i++) {
                Edge edge = kruskal.get(i);
                totalLength += edge.distance;
                System.out.println((i + 1) + ". " + edge.source + " -- " + edge.destination + "(" + edge.distance + "km)");
            }
            System.out.println();
            System.out.println("Total length: " + totalLength + "km");
            System.out.println("=================================================================================");
        }


        public static class Edge implements Comparable<Edge> {
            String source;
            String destination;
            int distance;

            public Edge(String source, String destination, int distance) {
                this.source = source;
                this.destination = destination;
                this.distance = distance;
            }

            public String getSource() {
                return source;
            }

            public String getDestination() {
                return destination;
            }

            public int getDistance() {
                return distance;
            }

            @Override
            public int compareTo(Edge other) {
                return this.distance - other.distance;
            }
        }


    }



