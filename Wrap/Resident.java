
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

