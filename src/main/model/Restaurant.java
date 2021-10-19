package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.Objects;

// Represents a Restaurant having a name, list of locations, rating, price, cuisine, review, and number of times visited
public class Restaurant implements Writable {
    private String name; // name of restaurant
    private ArrayList<Location> locations; // list of restaurant locations
    private Cuisine cuisine; // Cuisine of restaurant
    private double rating; // rating of restaurant
    private double price; // price of restaurant
    private int visited; // number of times visited
    private String review; // review of restaurant

    // REQUIRES: visited and price must be greater than 0, String must be non-zero length
    // MODIFIES: this
    // EFFECTS: creates a new restaurant with a given name, locations, cuisine and number of visits, then sets ratings
    //          and price to -1 and review to "no review found"
    public Restaurant(String name, ArrayList<Location> locations, Cuisine cuisine, int visits) {
        this.name = name;
        this.locations = locations;
        this.cuisine = cuisine;
        this.rating = -1;
        this.price = -1;
        this.visited = visits;
        this.review = "no review found";
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Location> getLocations() {
        return this.locations;
    }

    public Cuisine getCuisine() {
        return this.cuisine;
    }


    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds locations to list of locations unless already in list of locations, then does nothing
    public void addLocation(Location location) {
        if (!locations.contains(location)) {
            locations.add(location);
        }
    }


    // MODIFIES: this
    // EFFECTS: removes all location with given address from list of locations, returns true if location is removed
    //          false if no location was found
    public boolean removeLocation(String address) {
        boolean found = false;
        ArrayList<Location> newLocations = new ArrayList<>();
        for (Location l : locations) {
            if (Objects.equals(l.getAddress(), address)) {
                found = true;
            } else {
                newLocations.add(l);
            }
        }
        this.locations = newLocations;
        return found;
    }

    // EFFECTS: returns true if restaurant has a location in the area nm
    public boolean locationIn(String nm) {
        boolean found = false;
        for (Location l : locations) {
            if (Objects.equals(l.getArea(), nm)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public double getRating() {
        return this.rating;
    }

    public double getPrice() {
        return this.price;
    }

    public int getVisited() {
        return this.visited;
    }

    public String getReview() {
        return this.review;
    }

    // REQUIRES: rating must be a double between 0 and 10
    public void setRating(double rating) {
        this.rating = rating;
    }

    // REQUIRES: price must be >= 0
    public void setPrice(double price) {
        this.price = price;
    }

    public void setReview(String r) {
        this.review = r;
    }

    // EFFECTS: returns true if visits is > 0
    public boolean isVisited() {
        return visited > 0;
    }

    // REQUIRES rating must be between 0 and 10, and price must be greater than 0
    // MODIFIES: this
    // EFFECTS: adjusts rating and price of restaurant and increases number of times visited
    public void visit(double rating, double price) {
        if (isVisited()) {
            this.rating = (rating + this.rating * visited) / (visited + 1);
            this.price = (price + this.price * visited) / (visited + 1);
            visited++;
        } else {
            this.rating = rating;
            this.price = price;
            visited = 1;
        }
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("locations", locationsToJson());
        json.put("ethnicity", cuisine.getEthnicity());
        json.put("dishes", dishesToJson());
        json.put("visits", visited);
        if (isVisited()) {
            json.put("rating", rating);
            json.put("price", price);
            json.put("review", review);
        }
        return json;
    }

    private JSONArray dishesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String s : cuisine.getDishes()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dish", s);
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    private JSONArray locationsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Location l : locations) {
            jsonArray.put(l.toJson());
        }
        return  jsonArray;
    }
}
