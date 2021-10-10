package model;

import java.util.ArrayList;
import java.util.Comparator;

// Represents a list of VisitedRestaurants
public class RestaurantList {
    private final ArrayList<Restaurant> restaurants;


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
        Restaurant restaurant = null;
        for (Restaurant r : restaurants) {
            if (r.getName().equals(nm)) {
                restaurant = r;
            }
        }
        return restaurant;
    }

    // REQUIRES: restaurant is in restaurants
    // EFFECTS: returns restaurant in given index position
    public Restaurant get(int i) {
        return restaurants.get(i);
    }

    // EFFECTS: returns true if restaurant with name nm is in restaurants
    public boolean containsRestaurant(String nm) {
        boolean found = false;
        for (Restaurant r : restaurants) {
            if (r.getName().equals(nm)) {
                found = true;
                break;
            }
        }
        return found;
    }

    // EFFECTS: returns size of restaurants
    public int sizeOf() {
        return restaurants.size();
    }

    // EFFECTS: returns index position of restaurant r
    public int indexOf(Restaurant r) {
        return restaurants.indexOf(r);
    }

    // REQUIRES: restaurants is not empty
    // EFFECTS: returns next visited restaurant at front of restaurants
    public Restaurant nextRestaurant() {
        return restaurants.get(0);
    }

    // MODIFIES: this
    // EFFECTS: Adds a restaurant restaurants unless already in restaurants, then does nothing
    public void addRestaurant(Restaurant r) {
        if (!restaurants.contains(r)) {
            restaurants.add(r);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes restaurant with String nm from restaurants returns true if restaurant removed,
    //          else returns false
    public boolean removeRestaurant(String nm) {
        if (containsRestaurant(nm)) {
            restaurants.remove(getRestaurant(nm));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: returns a sorted list of restaurants by cuisine ethnicity
    public RestaurantList sortByCuisine(String nm) {
        RestaurantList sortedList = new RestaurantList();
        for (Restaurant r : restaurants) {
            if (r.getCuisine().getEthnicity().equals(nm)) {
                sortedList.addRestaurant(r);
            }
        }
        for (Restaurant r : restaurants) {
            sortedList.addRestaurant(r);
        }
        return sortedList;
    }

    // MODIFIES: this
    // EFFECTS: returns a sorted list of visited restaurants from the highest rating to lowest
    public RestaurantList sortByRating() {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: sorts a sorted list of visited restaurants from the most expensive to cheapest
    public RestaurantList sortByLuxury() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sorts a sorted list of visited restaurants from the cheapest to most expensive
    public RestaurantList sortByCheapest() {
        return null;
    }

    // EFFECTS: returns only restaurants in a specific area
    public RestaurantList sortInLocation(String nm) {
        RestaurantList sortedList = new RestaurantList();
        for (Restaurant r : restaurants) {
            for (Location l : r.getLocations()) {
                if (l.getArea().equals(nm)) {
                    sortedList.addRestaurant(r);
                }
            }
        }
        for (Restaurant r : restaurants) {
            sortedList.addRestaurant(r);
        }
        return sortedList;
    }

    // EFFECTS: returns a RestaurantList of only visited restaurants from most visited to least
    public RestaurantList sortByVisited() {
        return null;
    }

    // EFFECTS: returns a RestaurantList of only unvisited restaurants
    public RestaurantList sortByWishlist() {
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
