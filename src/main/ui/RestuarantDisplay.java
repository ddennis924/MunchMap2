package ui;

import model.Location;
import model.Restaurant;

import javax.swing.*;

public class RestuarantDisplay extends JPanel {
    Restaurant restaurant;

    public RestuarantDisplay(Restaurant r) {
        super();
        restaurant = r;
        printRestaurant();
    }

    // EFFECTS: prints restaurant in depth, with name, locations, cuisine, as well as times visited, rating, price and
    //          review if visited
    private void printRestaurant() {
        System.out.println("Name: " + restaurant.getName());
        System.out.println("\tLocations:");
        for (Location l : restaurant.getLocations()) {
            System.out.println("\t\t" + l.getAddress() + " in " + l.getArea());
        }
        System.out.println("\tCuisine: " + restaurant.getCuisine().getEthnicity());
        for (String c : restaurant.getCuisine().getDishes()) {
            System.out.println("\t\t" + c);
        }
        if (restaurant.isVisited()) {
            System.out.println("\tTimes visited: " + restaurant.getVisited());
            System.out.println("\tRating: " + restaurant.getRating());
            System.out.println("\tPrice: " + restaurant.getPrice());
            System.out.println("\tReview: " + restaurant.getReview());
        }   else {
            System.out.println("\tYou've not been yet, hopefully soon!");
        }
    }

}
