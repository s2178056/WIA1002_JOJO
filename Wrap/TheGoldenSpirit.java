import java.util.*;

public class TheGoldenSpirit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the player to enter the names of two Joestars
        System.out.print("Enter the name of the first Joestar: ");
        String name1 = scanner.nextLine();

        System.out.print("Enter the name of the second Joestar: ");
        String name2 = scanner.nextLine();

        if(name1 != null && name2 != null){
            Joestar joestar1 = findJoestar(name1, joestarFamilyArray());
            Joestar joestar2 = findJoestar(name2, joestarFamilyArray());

            System.out.println("======================================================================");
            System.out.printf("Lowest Common Ancestor of %s and %s:\n", name1, name2);
            List<String> printLowestCommonAncestor = lowestCommonAncestor(joestar1, joestar2);

            if(!printLowestCommonAncestor.isEmpty()){
                for(String joestar : printLowestCommonAncestor){
                    System.out.println(joestar);
                }
            }
            else {System.out.printf("%s and %s have no common ancestor.\n", name1, name2);}

            System.out.println("======================================================================");
        }else {
            System.out.println("Invalid Joestar names entered.");
        }
    }

    public static void printAncestor(String name){
        System.out.println("======================================================================");
        Joestar joestar = findJoestar(name, joestarFamilyArray());
        if (joestar != null){
            System.out.printf("Ancestors of %s\n", name);
            System.out.println("======================================================================");
            printFamilyTree(joestar, 0);
            System.out.println("======================================================================");
        }else {
            System.out.println("Invalid Joestar names entered.");
        }
    }
    private static Joestar[] joestarFamilyArray(){
        Joestar erinaPendleton = new Joestar("Erina Pendleton");
        Joestar georgeJoestarII = new Joestar("George Joestar II");
        Joestar giornoGiovanna = new Joestar("Giorno Giovanna");
        Joestar holyKujo = new Joestar("Holy Kujo");
        Joestar jolyneCujoh = new Joestar("Jolyne Cujoh");
        Joestar jonathanJoestar = new Joestar("Jonathan Joestar");
        Joestar josephJoestar = new Joestar("Joseph Joestar");
        Joestar josukeHigashikata = new Joestar("Josuke Higashikata");
        Joestar jotaroKujo = new Joestar("Jotaro Kujo");
        Joestar lisaLisa = new Joestar("Lisa Lisa");
        Joestar suziQ = new Joestar("Suzi Q.");
        Joestar tomokoHigashikata = new Joestar("Tomoko Higashikata");
        Joestar erinaJoestar = new Joestar("Erina Joestar");
        Joestar dio = new Joestar("DIO");
        Joestar georgeJoestarI = new Joestar("George Joestar I");
        Joestar maryJoestar = new Joestar("Mary Joestar");
        Joestar sadaoKujo = new Joestar("Sadao Kujo");

        georgeJoestarII.addParent(erinaJoestar);
        georgeJoestarII.addParent(jonathanJoestar);
        giornoGiovanna.addParent(dio);
        giornoGiovanna.addParent(jonathanJoestar);
        holyKujo.addParent(josephJoestar);
        holyKujo.addParent(suziQ);
        jolyneCujoh.addParent(jotaroKujo);
        jonathanJoestar.addParent(georgeJoestarI);
        jonathanJoestar.addParent(maryJoestar);
        josephJoestar.addParent(georgeJoestarII);
        josephJoestar.addParent(lisaLisa);
        josukeHigashikata.addParent(josephJoestar);
        josukeHigashikata.addParent(tomokoHigashikata);
        jotaroKujo.addParent(holyKujo);
        jotaroKujo.addParent(sadaoKujo);

        Joestar[] joestarsArray = {erinaPendleton, georgeJoestarII, giornoGiovanna, holyKujo, jolyneCujoh, jonathanJoestar,
                josephJoestar, josukeHigashikata, jotaroKujo, lisaLisa, suziQ, tomokoHigashikata, erinaJoestar,
                dio, georgeJoestarI, maryJoestar, sadaoKujo};

        return joestarsArray;
    }

    private static void printFamilyTree(Joestar person, int level) {

        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("\t");
        }

        System.out.println(indent + "" + (level) + " → " + person.getName());
        for (Joestar parent : person.getParents()) {
            printFamilyTree(parent, level + 1);
        }
    }

    private static boolean isCommonAncestor(Joestar ancestor1, Joestar ancestor2, Joestar joestar1, Joestar joestar2){
        if (!(ancestor1.getName().equals(joestar1.getName())) && !(ancestor1.getName().equals(joestar2.getName()))
                && !(ancestor2.getName().equals(joestar1.getName())) && !(ancestor2.getName().equals(joestar2.getName()))
                && ancestor1.getName().equals(ancestor2.getName())){
            return true;
        }
        return false;
    }
    private static List<String> lowestCommonAncestor(Joestar joestar1, Joestar joestar2) {
        List<String> lowestCommonAncestors = new ArrayList<>();

        if (joestar1 != null && joestar2 != null) {
            Queue<AncestorData> ancestors1 = getAncestors(joestar1, 0);
            Queue<AncestorData> ancestors2 = getAncestors(joestar2, 0);
            List<String> ancestor2NameList = new ArrayList<>();

            if (!ancestors1.isEmpty() && !ancestors2.isEmpty()) {
                int level = -1;

                for (AncestorData ancestor2 : ancestors2) {
                    ancestor2NameList.add(ancestor2.getAncestor().getName());
                }

                // Find the lowest common ancestors
                outerLoop:
                for (AncestorData ancestor1 : ancestors1) {
                    for (AncestorData ancestor2 : ancestors2) {
                        if (isCommonAncestor(ancestor1.getAncestor(), ancestor2.getAncestor(), joestar1, joestar2)) {
                            level = ancestor1.getLevel();
                            lowestCommonAncestors.add(ancestor1.getAncestor().getName());
                            break outerLoop;
                        }
                    }
                }

                if (level > -1) {
                    for (AncestorData ancestor1 : ancestors1) {
                        if (ancestor1.getLevel() == level && ancestor2NameList.contains(ancestor1.getAncestor().getName())) {
                            if (!lowestCommonAncestors.contains(ancestor1.getAncestor().getName())) {
                                lowestCommonAncestors.add(ancestor1.getAncestor().getName());
                            }
                        }
                    }
                }
            }
        }
        return lowestCommonAncestors;
    }

    private static Queue<AncestorData> getAncestors(Joestar joestar, int level) {
        Queue<AncestorData> ancestors = new LinkedList<>();

        if(!joestar.getParents().isEmpty()){
            for (Joestar parent : joestar.getParents()) {
                ancestors.add(new AncestorData(parent, level));
                ancestors.addAll(getAncestors(parent, level + 1));
            }
        }

        // Sorting the ancestors list based on the level in ascending order
        List<AncestorData> sortedAncestors = new ArrayList<>(ancestors);
        sortedAncestors.sort(Comparator.comparingInt(AncestorData::getLevel));

        return new LinkedList<>(sortedAncestors);
    }

    private static Joestar findJoestar(String name, Joestar[] joestars) {
        for (Joestar joestar : joestars) {
            if (joestar.getName().equalsIgnoreCase(name)) {
                return joestar;
            }
        }

        return null;
    }
}

class Joestar {
    private String name;
    private List<Joestar> parents;

    public Joestar(String name) {
        this.name = name;
        this.parents = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public List<Joestar> getParents() {
        return parents;
    }

    public void addParent(Joestar parent) {
        parents.add(parent);
    }
}

class AncestorData {
    private Joestar ancestor;
    private int level;

    public AncestorData(Joestar ancestor, int level) {
        this.ancestor = ancestor;
        this.level = level;
    }

    public Joestar getAncestor() {
        return ancestor;
    }

    public int getLevel() {
        return level;
    }
}
