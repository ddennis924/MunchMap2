package model;

import java.util.ArrayList;

// Represents a list of VisitedRestaurants
public class RestaurantList {
    private ArrayList<Restaurant> restaurants;

    // EFFECTS: Constructs an empty restaurant list
    public RestaurantList() {
        restaurants = new ArrayList<>();
    }


    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    // REQUIRES: restaurant is in restaurants
    // EFFECTS: returns a restaurant with given name
    public Restaurant getRestaurant(String nm) {
        return null;
    }

    // REQUIRES: restaurant is in restaurants
    // EFFECTS: returns restaurant in given index position
    public Restaurant get(int i) {
        return restaurants.get(i);
    }

    // EFFECTS: returns true if restaurant is in restaurants
    public boolean containsRestaurant(String nm) {
        return false;
    }

    // EFFECTS: returns size of restaurants
    public int sizeOf() {
        return restaurants.size();
    }

    // EFFECTS: returns index position of restaurant r
    public int indexOf(Restaurant r) {
        return restaurants.indexOf(r);
    }

    // REQUIRES: visited and unvisited restaurants are not empty
    // EFFECTS: returns next visited restaurant at front of restaurants
    public Restaurant nextRestaurant() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: Adds a restaurant restaurants
    public void addRestaurant(Restaurant r) {
    }

    // MODIFIES: this
    // EFFECTS: removes restaurant with String nm from restaurants returns true if restaurant removed,
    //          else returns false
    public boolean removeRestaurant(String nm) {
        return false;
    }

    // MODIFIES: this
    // EFFECTS: sorts lists of restaurants by cuisine ethnicity
    public RestaurantList sortByCuisine(String nm) {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sorts visited restaurants from the highest rating to lowest
    public RestaurantList sortByRating() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sorts visited restaurants from the most expensive to cheapest
    public RestaurantList sortByLuxury() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sorts visited restaurants from the cheapest to most expensive
    public RestaurantList sortByCheapest() {
        return null;
    }

    // REQUIRES: r must be a double between 0 and 10
    // EFFECTS: returns a random visited restaurant above rating r in the area l
    public Restaurant randomRestaurantRating(double r, String l) {
        return null;
    }

    // REQUIRES: p must be greater than 0
    // EFFECTS: returns a random visited restaurant below the price p in the area l
    public Restaurant randomRestaurantPrice(double p, String l) {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: returns a random restaurant with the ethnicity c in the area l
    public Restaurant randomRestaurantCuisine(String c, String l) {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: returns a random visited restaurant with the given dish in the area l
    public Restaurant randomRestaurantDish(String d, String l) {
        return null;
    }

}
