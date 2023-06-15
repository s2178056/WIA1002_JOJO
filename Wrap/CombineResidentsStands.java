import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombineResidentsStands {
    public static void main(String[] args) {

        List<Resident> residents = new ArrayList<>();

        try(BufferedReader readResident = new BufferedReader(new FileReader("residents.csv"))){
            String line;

            while((line = readResident.readLine()) != null){
                String[] split = line.split(",");

                String parents = null;
                if(split.length==5){parents = split[4];}
                else if(split.length==6){parents = split[4]+" & "+split[5];}

                Resident resident = new Resident(split[0], split[1], split[2], split[3], parents);
                residents.add(resident);
            }

            readResident.close();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found!");
        } catch (IOException e){
            System.out.println("Error reading from file.");
        }

        Map<String, Stands> stands = new HashMap<>();
        try(BufferedReader readStands = new BufferedReader(new FileReader("stands.csv"))){
            String line;

            while((line = readStands.readLine()) != null){
                String[] split = line.split(",");
                Stands stand = new Stands(split[0], split[1], split[2], split[3], split[4], split[5], split[6], split[7]);
                stands.put(stand.getStandUser(), stand);
            }

            readStands.close();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found!");
        } catch (IOException e){
            System.out.println("Error reading from file.");
        }

        List<ResidentWithStand> residentsWithStands = new ArrayList<>();
        for(Resident resident : residents){
            Stands stand = stands.get(resident.getName());
            if (stand != null) {
                residentsWithStands.add(new ResidentWithStand(resident, stand));
            }
            else residentsWithStands.add(new ResidentWithStand(resident));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("combinedRS.csv"))) {
            writer.write("Name,Age,Gender,Residential Area,Parents,Stand,Destructive Power,Speed,Range,Stamina,Precision,Development Potential\n");

            for (ResidentWithStand residentWithStand : residentsWithStands) {
                Resident resident = residentWithStand.getResident();
                Stands stand = residentWithStand.getStand();

                if(residentWithStand.getStand() == null){
                    writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                            resident.getName(), resident.getAge(), resident.getGender(), resident.getResidentialArea(), resident.getParents(),
                            "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A"));
                }

                else if(residentWithStand.getStand() != null){
                    writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                            resident.getName(), resident.getAge(), resident.getGender(), resident.getResidentialArea(), resident.getParents(),
                            stand.getStand(), stand.getDestructivePower(), stand.getSpeed(), stand.getRange(), stand.getStamina(), stand.getPrecision(), stand.getDevelopmentPotential()));
                }
            }
        } catch (IOException e) {
            System.out.println("Problem with file output");
        }

    }
}


class Stands{
    private String stand;
    private String standUser;
    private String destructivePower;
    private String speed;
    private String range;
    private String stamina;
    private String precision;
    private String developmentPotential;

    public Stands(String stand, String standUser, String destructivePower, String speed, String range, String stamina, String precision, String developmentPotential) {
        this.stand = stand;
        this.standUser = standUser;
        this.destructivePower = destructivePower;
        this.speed = speed;
        this.range = range;
        this.stamina = stamina;
        this.precision = precision;
        this.developmentPotential = developmentPotential;
    }

    public String getStand() {
        return stand;
    }

    public String getStandUser() {
        return standUser;
    }

    public String getDestructivePower() {
        return destructivePower;
    }

    public String getSpeed() {
        return speed;
    }

    public String getRange() {
        return range;
    }

    public String getStamina() {
        return stamina;
    }

    public String getPrecision() {
        return precision;
    }

    public String getDevelopmentPotential() {
        return developmentPotential;
    }
}

class ResidentWithStand {
    private Resident resident;
    private Stands stand;

    public ResidentWithStand(Resident resident, Stands stand) {
        this.resident = resident;
        this.stand = stand;
    }

    ResidentWithStand(Resident resident) {
        this.resident = resident;
    }

    public Resident getResident() {
        return resident;
    }

    public Stands getStand() {
        return stand;
    }
}

    class Resident {
        private String name;
        private String age;
        private String gender;
        private String residentialArea;
        private String parents;
        private List<String> restaurantHistory;
        private List<String> foodHistory;
        private Map<String, Integer> foodFrequency;

        public Resident(String name) {
            this.name = name;
            restaurantHistory = new ArrayList<>();
            foodHistory = new ArrayList<>();
            foodFrequency = new HashMap<>();
        }
        public Resident(String name, String age, String gender, String residentialArea, String parents) {
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.residentialArea = residentialArea;
            this.parents = parents;
        }

        public String getName() {
            return name;
        }

        public String getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }

        public String getResidentialArea() {
            return residentialArea;
        }

        public String getParents() {
            return parents;
        }

        public void addRestaurantToHistory(String restaurantName) {
            restaurantHistory.add(restaurantName);
        }

        public List<String> getRestaurantHistory() {
            return restaurantHistory;
        }

        public void addFoodToHistory(String foodName) {
            foodHistory.add(foodName);
            foodFrequency.put(foodName, foodFrequency.getOrDefault(foodName, 0) + 1);
        }

        public List<String> getFoodHistory() {
            return foodHistory;
        }

        public Map<String, Integer> getFoodFrequency() {
            return foodFrequency;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Resident: ").append(name).append("\n");
            sb.append("Restaurant History: ").append(restaurantHistory).append("\n");
            sb.append("Food History: ").append(foodHistory).append("\n");
            sb.append("Food Frequency: ").append(foodFrequency).append("\n");
            return sb.toString();
        }
    }




