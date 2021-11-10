package ui;

import model.Location;
import model.Restaurant;

import javax.swing.*;
import java.awt.*;

public class RestaurantDisplay extends JScrollPane {
    private Restaurant restaurant;
    private JTextArea main;

    public RestaurantDisplay(Restaurant r) {
        main = new JTextArea();
        restaurant = r;
        addMainPane();
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public JTextArea getMain() {
        return main;
    }

    public void setMain(JTextArea main) {
        this.main = main;
    }

    public void addMainPane() {
        main.setMargin(new Insets(5,5,5,5));
        main.setEditable(false);
        main.setBackground(new Color(241, 255, 224));
        if (restaurant != null) {
            printRestaurant();
        } else {
            main.append("Select a Restaurant!");
        }
    }

    public void printRestaurant() {
        main.setText(null);
        main.append("Name: " + restaurant.getName());
        main.append("\nLocations:");
        for (Location l : restaurant.getLocations()) {
            main.append("\n\t" + l.getAddress() + " in " + l.getArea());
        }
        main.append("\nCuisine: " + restaurant.getCuisine().getEthnicity());
        for (String c : restaurant.getCuisine().getDishes()) {
            main.append("\n\t" + c);
        }
        if (restaurant.isVisited()) {
            main.append("\nTimes visited: " + restaurant.getVisited());
            main.append("\nRating: " + restaurant);
            main.append("\nPrice: " + restaurant.getPrice());
            main.append("\nReview: " + restaurant.getReview());
        }   else {
            main.append("\nYou've not been yet, hopefully soon!");
        }
    }


}
