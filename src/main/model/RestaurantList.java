package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents a list of VisitedRestaurants
public class RestaurantList implements Writable {
    private final ArrayList<Restaurant> restaurants; // list of restaurants
    String name;


    // EFFECTS: Constructs an empty Restaurant list with a name
    public RestaurantList(String name) {
        restaurants = new ArrayList<>();
        this.name = name;
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

    public String getName() {
        return name;
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
        EventLog.getInstance().logEvent(new Event("Restaurant " + r.getName() + " added"));
    }

    // MODIFIES: this
    // EFFECTS: removes restaurant with String nm from restaurants returns true if restaurant removed,
    //          else returns false
    public boolean removeRestaurant(String nm) {
        if (containsRestaurant(nm)) {
            restaurants.remove(getRestaurant(nm));
            EventLog.getInstance().logEvent(new Event("Restaurant " + nm + " removed"));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: returns a list of restaurants with the Cuisine ethnicity nm
    public RestaurantList sortInCuisine(String nm) {
        RestaurantList sortedList = new RestaurantList(name);
        for (Restaurant r : restaurants) {
            if (r.getCuisine().getEthnicity().equals(nm)) {
                sortedList.addRestaurant(r);
            }
        }
        return sortedList;
    }

    // MODIFIES: this
    // EFFECTS: returns a sorted list of visited restaurants from the highest rating to lowest
    public RestaurantList sortByRating() {
        SortedSet<Double> sortedRatings = new TreeSet<>(Comparator.reverseOrder());
        for (Restaurant r : restaurants) {
            sortedRatings.add(r.getRating());
        }
        RestaurantList listToSort = sortByVisited();
        RestaurantList sortedList = new RestaurantList(name);
        for (double d : sortedRatings) {
            for (Restaurant r : listToSort.restaurants) {
                if (r.getRating() == d) {
                    sortedList.addRestaurant(r);
                }
            }
        }
        return sortedList;
    }

    // MODIFIES: this
    // EFFECTS: sorts a sorted list of visited restaurants from the most expensive to cheapest
    public RestaurantList sortByLuxury() {
        SortedSet<Double> sortedPrice = new TreeSet<>(Comparator.reverseOrder());
        for (Restaurant r : restaurants) {
            sortedPrice.add(r.getPrice());
        }
        RestaurantList listToSort = sortByVisited();
        RestaurantList sortedList = new RestaurantList(name);
        for (double d : sortedPrice) {
            for (Restaurant r : listToSort.restaurants) {
                if (r.getPrice() == d) {
                    sortedList.addRestaurant(r);
                }
            }
        }
        return sortedList;
    }

    // MODIFIES: this
    // EFFECTS: sorts a sorted list of visited restaurants from the cheapest to most expensive
    public RestaurantList sortByCheapest() {
        SortedSet<Double> sortedPrice = new TreeSet<>();
        for (Restaurant r : restaurants) {
            sortedPrice.add(r.getPrice());
        }
        RestaurantList listToSort = sortByVisited();
        RestaurantList sortedList = new RestaurantList(name);
        for (double d : sortedPrice) {
            for (Restaurant r : listToSort.restaurants) {
                if (r.getPrice() == d) {
                    sortedList.addRestaurant(r);
                }
            }
        }
        return sortedList;
    }

    // EFFECTS: returns only restaurants in a specific area
    public RestaurantList sortInLocation(String nm) {
        RestaurantList sortedList = new RestaurantList(name);
        for (Restaurant r : restaurants) {
            for (Location l : r.getLocations()) {
                if (l.getArea().equals(nm)) {
                    sortedList.addRestaurant(r);
                }
            }
        }
        return sortedList;
    }

    // EFFECTS: returns a RestaurantList of only visited restaurants from most visited to least
    public RestaurantList sortByVisited() {
        RestaurantList visitedList = new RestaurantList(name);
        for (Restaurant r : restaurants) {
            if (r.isVisited()) {
                visitedList.addRestaurant(r);
            }
        }
        return visitedList;
    }

    // EFFECTS: returns a RestaurantList of only unvisited restaurants
    public RestaurantList sortByWishlist() {
        RestaurantList wishList = new RestaurantList(name);
        for (Restaurant r : restaurants) {
            if (!r.isVisited()) {
                wishList.addRestaurant(r);
            }
        }
        return wishList;
    }

    // REQUIRES: r must be a double between 0 and 10,
    //           at least one restaurant must exist at or above the specified rating and location
    // EFFECTS: returns a random visited restaurant above rating r in the area l,
    //          or returns null if no restaurant is found
    public Restaurant randomRestaurantRating(double rating, String l) {
        RestaurantList sortedList = sortInLocation(l);
        RestaurantList newSortedList = new RestaurantList(name);
        for (Restaurant r : sortedList.getRestaurants()) {
            if (r.getRating() >= rating) {
                newSortedList.addRestaurant(r);
            }
        }
        RestaurantList newNewSortedList = newSortedList.sortByVisited();
        if (sortedList.sizeOf() == 0) {
            return null;
        } else {
            Random random = new Random();
            return newNewSortedList.get(random.nextInt(newNewSortedList.sizeOf()));
        }
    }

    // REQUIRES: p must be greater than or equal to 0,
    //           at least one restaurant must exist at or below the specified price and location
    // EFFECTS: returns a random visited restaurant below the price p in the area l,
    //          or returns null if not restaurant is found
    public Restaurant randomRestaurantPrice(double p, String l) {
        RestaurantList sortedList = sortInLocation(l);
        RestaurantList newSortedList = new RestaurantList(name);
        for (Restaurant r : sortedList.getRestaurants()) {
            if (r.getPrice() <= p) {
                newSortedList.addRestaurant(r);
            }
        }
        RestaurantList newNewSortedList = newSortedList.sortByVisited();
        if (sortedList.sizeOf() == 0) {
            return null;
        } else {
            Random random = new Random();
            return newNewSortedList.get(random.nextInt(newNewSortedList.sizeOf()));
        }
    }

    // REQUIRES: at least one restaurant must exist with the specified Cuisine ethnicity and location
    // MODIFIES: this
    // EFFECTS: returns a random restaurant with the ethnicity c in the area l, if none can be found, returns null
    public Restaurant randomRestaurantCuisine(String c, String l) {
        RestaurantList sortedList = sortInCuisine(c).sortInLocation(l);
        if (sortedList.sizeOf() == 0) {
            return null;
        } else {
            Random random = new Random();
            return sortedList.get(random.nextInt(sortedList.sizeOf()));
        }
    }

    // REQUIRES: at least one restaurant must exist that contains the specified dish at the location
    // MODIFIES: this
    // EFFECTS: returns a random restaurant with the given dish in the area l, returns null if non can be found
    public Restaurant randomRestaurantDish(String d, String l) {
        RestaurantList sortedList = sortInLocation(l);
        RestaurantList newSortedList = new RestaurantList(name);
        for (Restaurant r : sortedList.getRestaurants()) {
            if (r.getCuisine().getDishes().contains(d)) {
                newSortedList.addRestaurant(r);
            }
        }
        if (newSortedList.sizeOf() == 0) {
            return null;
        } else {
            Random random = new Random();
            return newSortedList.get(random.nextInt(newSortedList.sizeOf()));
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("restaurants", restaurantsToJson());
        return json;
    }

    // EFFECTS: returns restaurants in RestaurantList as JSONArray of restaurants
    private JSONArray restaurantsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Restaurant r : restaurants) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }

    public void printRestaurantLog(EventLog elog) {
        for (Event e : elog) {
            System.out.println(e.getDescription() + " on " + e.getDate());
        }
    }
}
