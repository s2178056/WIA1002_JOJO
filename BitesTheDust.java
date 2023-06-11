import java.util.*;

public class BitesTheDust {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Yoshikage Kira's path: ");
        String path = sc.nextLine();

        String[] pathArray = path.split(" >");

        String longestRepeatedPath = "";
        int maxLength = 0;
        Set<String> visitedPath = new HashSet<>();
        boolean hasRepeat = false;

        for (int i = 0; i < pathArray.length - 1; i++) {
            StringBuilder currPath = new StringBuilder(pathArray[i]);
            for (int j = i + 1; j < pathArray.length; j++) {
                currPath.append(" >").append(pathArray[j]);
                if (!visitedPath.contains(currPath.toString())) {
                    visitedPath.add(currPath.toString());
                } else {
                    hasRepeat = true;
                    if (currPath.length() > maxLength) {
                        longestRepeatedPath = currPath.toString();
                        maxLength = currPath.length();
                    }
                }
            }
        }
        if (hasRepeat){
            System.out.println("======================================================================");
            System.out.println("Bites the Dust is most likely to be activated when Kira passed through" + longestRepeatedPath);
            System.out.println("======================================================================");
        } else {
            System.out.println("======================================================================");
            System.out.println("Bites the Dust is not activated.");
            System.out.println("======================================================================");
        }
    }
}
