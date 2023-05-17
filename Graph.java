import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

public class Graph {
    private Map<String, List<Edge>> graph;

    public Graph() {
        graph = new HashMap<>();
    }

    public void addTown(String town) {
        graph.put(town, new ArrayList<>());
    }

    public void addRoad(String source, String destination, int distance) {
        Edge road = new Edge(source, destination, distance);
//        Edge road2 = new Edge(destination, source, distance);
        graph.get(source).add(road);
        graph.get(destination).add(road); // since the road is bidirectional
    }

    public List<String> getChoices(String location) {
        List<String> choices = new ArrayList<>();
        List<Edge> roads = graph.get(location);
        for (Edge road : roads) {
            if (road.source.equals(location)) {
                choices.add(road.destination);
            } else if (road.destination.equals(location)) { // add this to handle bidirectional edges
                choices.add(road.source);
            }
        }
        return choices;
    }

    public List<Edge> getRoads(String town) {
        return graph.get(town);
    }

    private class Edge {
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

