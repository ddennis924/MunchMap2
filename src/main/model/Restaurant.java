package model;

import java.util.ArrayList;

// Represents a Restaurant having a name, locations, rating, price, cuisine, and number of times visited
public class Restaurant {
    private String name;
    private ArrayList<Location> locations;
    private Cuisine cuisine;
    private double rating;
    private double price;
    private int visited;
    private String review;

    // REQUIRES: visited and price must be greater than 0
    // MODIFIES: this
    // EFFECTS: creates a new restaurant with a given name, locations, rating, price, cuisine, and times visited
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

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds locations to list of locations
    public void addLocation(Location location) {
    }

    // REQUIRES: location address must be in list of locations
    // MODIFIES: this
    // EFFECTS: removes location with given address from list of locations
    public void removeLocation(String address) {
    }

    // EFFECTS: returns true if restaurant has a location in the area nm
    public boolean locationIn(String nm) {
        return false;
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

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setReview(String r) {
        this.review = r;
    }

    // EFFECTS: returns true if visits is > 0
    public boolean isVisited() {
        return false;
    }

    // REQUIRES rating and price must be greater than 0
    // MODIFIES: this
    // EFFECTS: adjusts rating and price of restaurant and increases number of times visited
    public Restaurant visit(double rating, double price) {
        return null;
    }


}
