import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class HeavensDoor {

    //Method for sorting and printing the table
    public static void sort(String sortingOrder, String residentialArea) {
        String split[] = sortingOrder.split("; ");
        String[][] ary = residentsArray(residentialArea);
        int index = 0;
        int categories[] = new int[split.length];
        int orders[] = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            String fieldOrder[] = split[i].split(" \\(");

            // To find category index in the array for each sorting according to the sorting priority
            int category = findCategoryIndex(fieldOrder[0]);
            // To find whether it is asc or desc for each sorting
            int order = compareOrder(fieldOrder[1]);

            if (category >= 0 && order != 0) {
                categories[index] = category;
                orders[index] = order;
                index++;
            } else if (category < 0) {
                System.out.println("Invalid Field's Name > " + split[i]);
            } else if (order == 0) {
                System.out.println("Invalid Order's String > " + split[i]);
            }
        }
        // Swap for the first field
        swapFirstField(ary, categories[0], orders[0]);
        // Swap for the rest of the field
        if (index > 0) {
            for (int i = 1; i < split.length; i++) {
                swapOtherField(ary, categories[i - 1], categories[i], orders[i]);
            }
        }
        System.out.println("----Sorted----");
        printResidentInfo(ary, residentialArea);
    }


    //Method to print resident infomation
    public static void printResidentInfo (String[][] residentsArray, String residentialArea){

        System.out.println("Resident Information in " + residentialArea);
        System.out.println("+----+---------------------------+-----+--------+------------------------+-------------------+----------+----------+----------+-----------+-----------------------+");
        System.out.println("| No | Name                      | Age | Gender | Stand                  | Destructive Power | Speed    | Range    | Stamina  | Precision | Development Potential |");
        System.out.println("+----+---------------------------+-----+--------+------------------------+-------------------+----------+----------+----------+-----------+-----------------------+");

        for(int row=0; row<residentsArray.length; row++)
            System.out.printf("| %2d | %-25s | %-3s | %-6s | %-22s | %-17s | %-8s | %-8s | %-8s | %-9s | %-21s |\n",
                    (row+1), residentsArray[row][0], residentsArray[row][1], residentsArray[row][2], residentsArray[row][3], residentsArray[row][4], residentsArray[row][5], residentsArray[row][6], residentsArray[row][7], residentsArray[row][8], residentsArray[row][9]);
        System.out.println("+----+---------------------------+-----+--------+------------------------+-------------------+----------+----------+----------+-----------+-----------------------+");
    }

    //Method to print resident profile
    public static void printResidentProfile(String name){
        boolean found=false;
        try(BufferedReader read = new BufferedReader(new FileReader("combinedRS.csv"))){
            String line;
            while((line = read.readLine()) != null){
                String[] split = line.split(",");
                if(split[0].equalsIgnoreCase(name)){
                    System.out.println("======================================================");
                    System.out.println(name + "'s Profile");
                    System.out.println("======================================================");
                    System.out.printf("Name%18s:%s\n", " ", split[0]);
                    System.out.printf("Age%19s:%s\n", " ", split[1]);
                    System.out.printf("Gender%16s:%s\n", " ", split[2]);
                    System.out.printf("Parents%15s:%s\n", " ", split[4]);
                    System.out.printf("Stand%17s:%s\n", " ", split[5]);
                    System.out.printf("Destructive Power%5s:%s\n", " ", split[6]);
                    System.out.printf("Speed%17s:%s\n", " ", split[7]);
                    System.out.printf("Range%17s:%s\n", " ", split[8]);
                    System.out.printf("Stamina%15s:%s\n", " ", split[9]);
                    System.out.printf("Precision%13s:%s\n", " ", split[10]);
                    System.out.printf("Development Potential%1s:%s\n", " ", split[11]);
                    System.out.println("======================================================");
                    found=true;
                    read.close();
                    break;
                }
            }
            if (!found){
                System.out.println("Resident Not Found");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found!");
        } catch (IOException e){
            System.out.println("Error reading from file.");
        }
    }

    public  void assignRF(String name){
        try (BufferedReader reader = new BufferedReader(new FileReader("foodHistory.csv"))) {
            String line="";
            System.out.println("Order History for Resident");
            System.out.println("+-----+-------------------------------------+---------------------------+");
            System.out.println("| Day | Food                                | Restaurant                |");
            System.out.println("+-----+-------------------------------------+---------------------------+");
            while ((line = reader.readLine()) != null) {
                // Process each line of the CSV file
                String[] info = line.split(",");
                if (info[1].equals(name)){
                    String count=info[0];
                    String food=info[5];
                    String restaurant=info[4];
                    String[]order={count,food,restaurant};
                    System.out.printf("| %-3s | %-35s | %-25s |\n", order[0], order[1], order[2]);
                }
            }
            System.out.println("+-----+-------------------------------------+---------------------------+");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //To compare other field's value in one column and swap the whole rows according to the column
    private static String[][] swapOtherField(String[][] array, int fieldBefore, int currentField, int order) {
        int rows = array.length;

        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < rows - i - 1; j++) {
                String value1 = array[j][fieldBefore];
                String value2 = array[j + 1][fieldBefore];

                // Check if the values of the field before are the same
                if (value1.equals(value2)) {
                    String currentFieldValue1 = array[j][currentField];
                    String currentFieldValue2 = array[j + 1][currentField];
                    int comparisonResult;

                    // Compare based on the column category
                    if(currentField==1){comparisonResult = comparableAge(currentFieldValue1, currentFieldValue2);
                    } else if(currentField==0 || currentField==2 || currentField==3){comparisonResult = comparable(currentFieldValue1, currentFieldValue2);
                    } else if(currentField>=4 && currentField<=9){ comparisonResult = comparableStand(currentFieldValue1, currentFieldValue2);
                    } else comparisonResult = 0;

                    // Swap rows based on the comparison and order
                    if (comparisonResult * order > 0) {
                        String[] temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
            }
        }
        return array;
    }


    //To compare first field's value in one column and swap the whole rows according to the column
    private static String[][] swapFirstField(String[][] array, int column, int order) {
        int rows = array.length;

        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < rows - i - 1; j++) {
                String value1 = array[j][column];
                String value2 = array[j + 1][column];
                int comparisonResult;

                // Compare based on the column category
                if(column==1){comparisonResult = comparableAge(value1, value2);
                } else if(column==0 || column==2 || column==3){comparisonResult = comparable(value1, value2);
                } else if(column>=4 && column<=9){ comparisonResult = comparableStand(value1, value2);
                } else comparisonResult = 0;

                // Swap rows based on the comparison and order
                if (comparisonResult * order > 0) {
                    String[] temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    //To compare two string
    private static int comparable(String value1, String value2) {
      
      // Null or N/A has the lowest priority
        if (value1.equalsIgnoreCase("n/a") || value1.equalsIgnoreCase("null")) {
            if (value2.equalsIgnoreCase("n/a") || value2.equalsIgnoreCase("null")) {
                return 0; 
            } else {
                return 1; 
            }
        } else if (value2.equalsIgnoreCase("n/a") || value2.equalsIgnoreCase("null")) {
            return -1;
        } else {
            return value1.compareToIgnoreCase(value2); 
        }
    }


    //To compare Age parameter
    private static int comparableAge(String age1, String age2){
        if(age1.equalsIgnoreCase("n/a")){return 1;}
        else if(age2.equalsIgnoreCase("n/a")){return -1;}
        else if(Integer.parseInt(age1) > Integer.parseInt(age2)){return 1;}
        else if(Integer.parseInt(age1) < Integer.parseInt(age2)){return -1;}
        else return 0;
    }

    //To compare Stand parameter
    private static int comparableStand(String stand1, String stand2){
        if (numeriseStand(stand1) > numeriseStand(stand2)){return 1;}
        else if (numeriseStand(stand1) < numeriseStand(stand2)){return -1;}
        else return 0;
    }

    //To numerise Stand parameter according to the order > Infinity, A, B, C, D, E, ?, and Null
    private static int numeriseStand(String stand){
        switch (stand.toLowerCase()) {
            case "infinity" -> {return 0;}
            case "a" -> {return 1;}
            case "b" -> {return 2;}
            case "c" -> {return 3;}
            case "d" -> {return 4;}
            case "e" -> {return 5;}
            case "?" -> {return 6;}
            case "null" -> {return 7;}
            case "n/a" -> {return 7;}

            default -> {return -1;}
        }
    }

    //Find category index
    private static int findCategoryIndex(String category){
        switch (category.toLowerCase()) {
            case "name" -> {return 0;}
            case "age" -> {return 1;}
            case "gender" -> {return 2;}
            case "stand" -> {return 3;}
            case "destructive power" -> {return 4;}
            case "speed" -> {return 5;}
            case "range" -> {return 6;}
            case "stamina" -> {return 7;}
            case "precision" -> {return 8;}
            case "development potential" -> {return 9;}

            default -> {return -1;}
        }
    }

    //Method to find whether is ascending and descending
    private static int compareOrder(String order){
        order = order.toLowerCase();
        if (order.contains("asc")){return 1;}
        else if (order.contains("desc")){return -1;}
        else return 0;
    }

    //Method to count total row for residents in a specific area
    private static int getResidentRow(String residentialArea){
        int rowSize = 0;

        try(BufferedReader read = new BufferedReader(new FileReader("combinedRS.csv"))){

            //find row size
            String rowLine;

            while((rowLine = read.readLine()) != null){
                String[] split = rowLine.split(",");

                if(split[3].equalsIgnoreCase(residentialArea)){rowSize++;}
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found!");
        } catch (IOException e){
            System.out.println("Error reading from file.");
        }
        return rowSize;
    }

    //Method to get 2D-array of residents in the same residentialArea
    public static String[][] residentsArray(String residentialArea){
        int rowSize = getResidentRow(residentialArea);
        String[][] residentsArray = new String[rowSize][10];

        try(BufferedReader read = new BufferedReader(new FileReader("combinedRS.csv"))){
            String line;
            int row = 0;

            while((line = read.readLine()) != null){

                String[] split = line.split(",");
                int col = 0;

                if(split[3].equalsIgnoreCase(residentialArea)){
                    for(int j=0; j<12; j++){
                        if(j==3 || j==4) {continue;}

                        residentsArray[row][col] = split[j];
                        col++;
                    }
                    row++;
                }
            }

            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found!");
        } catch (IOException e){
            System.out.println("Error reading from file.");
        }
        return residentsArray;
    }



}

