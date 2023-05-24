import java.util.*;
import java.util.List;

class Restaurant {
    private String name;
    private List<Food> menu;

    public Restaurant(String name) {
        this.name = name;
        menu = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addFoodToMenu(Food food) {
        menu.add(food);
    }

    public List<Food> getMenu() {
        return menu;
    }
    
    public Food getFoodByName(String foodName) {
        for (Food food : menu) {
            if (food.getName().equals(foodName)) {
                return food;
            }
        }
        return null;
    }
}