import java.util.*;
public class BiteTheDust {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Yoshikage Kira's path: ");
        String path = sc.nextLine();

        String[] pathArray = path.split(" > ");
        List<String> longestPath = findLongestNonOverlappingPath(pathArray);


        if (longestPath.size()>1) {
            System.out.println("====================================================================================================");
            System.out.print("Bites the Dust is most likely to be activated when Kira passed through ");
            System.out.println(String.join(" > ", longestPath) + ".");
            System.out.println("====================================================================================================");
        } else {
            System.out.println("====================================================================================================");
            System.out.println("Bites the Dust is not activated.");
            System.out.println("====================================================================================================");
        }
    }

    public static List<String> findLongestNonOverlappingPath(String[] locations) {
        Map<String, Integer> pathCounts = new HashMap<>();

        for (int i = 0; i < locations.length - 1; i++) {
            for (int j = i + 1; j < locations.length; j++) {
                List<String> path = Arrays.asList(locations).subList(i, j + 1);

                if (isNonOverlapping(path)) {
                    String pathString = String.join(", ", path);

                    if (pathCounts.containsKey(pathString)) {
                        pathCounts.put(pathString, pathCounts.get(pathString) + 1);
                    } else {
                        pathCounts.put(pathString, 1);
                    }
                }
            }
        }

        List<String> repeatedPaths = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : pathCounts.entrySet()) {
            if (entry.getValue() > 1) {
                repeatedPaths.add(entry.getKey());
            }
        }

        if (repeatedPaths.isEmpty()) {
            return Collections.emptyList();
        }

        String longestPath = Collections.max(repeatedPaths, Comparator.comparing(String::length));
        return Arrays.asList(longestPath.split(", "));
    }

    public static boolean isNonOverlapping(List<String> path) {
        Set<String> visited = new HashSet<>();

        for (String location : path) {
            if (!visited.add(location)) {
                return false;
            }
        }

        return true;
    }
}