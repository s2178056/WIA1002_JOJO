/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package milagroman;

import java.util.*;

/**
 *
 * @author mrifq
 */
public class MilagroMan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
        int selection = input.nextInt();

          System.out.println("======================================================================");
            System.out.println("Current Location: Jade Garden" );
            System.out.println("""
                               [1] Move to:
                               [A] Cafe Deux Magots [B] Joestar Mansion
                               [C] Morioh Grand Hotel [D] San Giorgio Maggiore
                               [E] Town Hall""");
            System.out.println("[2] View Waiting List and Order Processing List");
            System.out.println("[3] View Menu");
            System.out.println("[4] View Sales Information");
            System.out.println("[5] Milagro Man");
            System.out.println("[6] Back (Town Hall)");
            System.out.println("[7] Back to Town Hall");
            
            System.out.println("Select: ");
            
        if (selection == 5) {
            System.out.println("Restaurant: " + "Location" + " (Milagro Man Mode)");
            System.out.println("[1] Modify Food Prices");
            System.out.println("[2] View Sales Information");
            System.out.println("[3] Exit Milagro Man");
            System.out.println("Select: ");
            
            int selection2 = input.nextInt();
            if (selection2 == 1) {
                System.out.println("Enter food name: ");
                String foodName = input.nextLine();
                System.out.println("Enter new price: ");
                double newPrice = input.nextDouble();
                System.out.println("Enter Start Day: ");
                int startDay = input.nextInt();
                System.out.println("Enter End Day: ");
                int endDay = input.nextInt();
                
                
            }
        }
          
    }
    

    public void createFood(String name, double price, String description) {
        Food newFood = new Food(name, price, description);
        menu.add(newFood);
        System.out.println("New food item created: " + newFood.getName());
    }

    public void modifyFood(String foodName, double newPrice, String newDescription) {
        Food food = getFoodByName(foodName);
        if (food != null) {
            food.setPrice(newPrice);
            food.setDescription(newDescription);
            System.out.println("Food item modified: " + food.getName());
        } else {
            System.out.println("Food item not found: " + foodName);
        }
    }

    public void removeFood(String foodName) {
        Iterator<Food> iterator = menu.iterator();
        while (iterator.hasNext()) {
            Food food = iterator.next();
            if (food.getName().equals(foodName)) {
                iterator.remove();
                System.out.println("Food item removed: " + food.getName());
                return;
            }
        }
        System.out.println("Food item not found: " + foodName);
    }
    
}
