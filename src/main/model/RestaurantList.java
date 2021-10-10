package model;

import java.util.*;

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
    // EFFECTS: returns a list of restaurants with the Cuisine ethnicity nm
    public RestaurantList sortInCuisine(String nm) {
        RestaurantList sortedList = new RestaurantList();
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
        RestaurantList sortedList = new RestaurantList();
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
        RestaurantList sortedList = new RestaurantList();
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
        RestaurantList sortedList = new RestaurantList();
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
        RestaurantList sortedList = new RestaurantList();
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
        RestaurantList visitedList = new RestaurantList();
        for (Restaurant r : restaurants) {
            if (r.isVisited()) {
                visitedList.addRestaurant(r);
            }
        }
        return visitedList;
    }

    // EFFECTS: returns a RestaurantList of only unvisited restaurants
    public RestaurantList sortByWishlist() {
        RestaurantList wishList = new RestaurantList();
        for (Restaurant r : restaurants) {
            if (!r.isVisited()) {
                wishList.addRestaurant(r);
            }
        }
        return wishList;
    }



    // REQUIRES: r must be a double between 0 and 10
    // EFFECTS: returns a random visited restaurant above rating r in the area l,
    //          or returns null if no restaurant is found
    public Restaurant randomRestaurantRating(double rating, String l) {
        RestaurantList sortedList = sortInLocation(l);
        RestaurantList newSortedList = new RestaurantList();
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

    // REQUIRES: p must be greater than 0
    // EFFECTS: returns a random visited restaurant below the price p in the area l,
    //          or returns null if not restaurant is found
    public Restaurant randomRestaurantPrice(double p, String l) {
        RestaurantList sortedList = sortInLocation(l);
        RestaurantList newSortedList = new RestaurantList();
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

    // MODIFIES: this
    // EFFECTS: returns a random restaurant with the given dish in the area l
    public Restaurant randomRestaurantDish(String d, String l) {
        RestaurantList sortedList = sortInLocation(l);
        RestaurantList newSortedList = new RestaurantList();
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

}
