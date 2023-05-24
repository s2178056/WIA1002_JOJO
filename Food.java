class Food {
    private String name;
    private String restaurant;
    private double price;

    public Food(String name, String restaurant, double price) {
        this.name = name;
        this.restaurant = restaurant;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public double getPrice() {
        return price;
    }
}

