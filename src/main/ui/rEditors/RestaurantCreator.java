package ui.rEditors;

import model.Cuisine;
import model.Location;
import model.Restaurant;
import ui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RestaurantCreator extends RestaurantEditor {
    private String area;
    private String cuisine;

    public RestaurantCreator(MainFrame e) {
        super(e);
        restaurant = new Restaurant("", new ArrayList<>(), null, 0);
        area = "";
        cuisine = "";
    }

    @Override
    protected void setStartingPrompt() {
        title.setText("What is the name of the Restaurant?");
    }

    public void updateName(Restaurant r) {
        String name = getString();
        r.setName(name);
        advanceSequence("What is the area?");
    }

    private String updateArea() {
        String area = getString();
        advanceSequence("What is the address?");
        return area;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (sequence == 0) {
            updateName(restaurant);
        } else if (sequence == 1) {
            area = updateArea();
        } else if (sequence == 2) {
            updateAddress(restaurant, area);
        } else if (sequence == 3) {
            cuisine = updateCuisine();
        } else if (sequence == 4) {
            updateDish(restaurant, cuisine);
        } else if (sequence == 5) {
            updateVisit(restaurant);
        } else if (sequence == 6) {
            updateRating(restaurant);
        } else if (sequence == 7) {
            updatePrice(restaurant);
        } else if (sequence == 8) {
            updateReview(restaurant);
        } else if (sequence == 9) {
            title.setText("New Restaurant added");
            editor.addRestaurant(restaurant);
            dispose();
        }
    }

    private void updateReview(Restaurant newRestaurant) {
        String review = getString();
        newRestaurant.setReview(review);
        sequence = 9;
        actionPerformed(new ActionEvent(this,  ActionEvent.ACTION_PERFORMED, null));
    }

    private void updatePrice(Restaurant newRestaurant) {
        double price = Double.parseDouble(getString());
        newRestaurant.setPrice(price);
        advanceSequence("Any review?");
    }

    private void updateRating(Restaurant newRestaurant) {
        double rating = Double.parseDouble(getString());
        newRestaurant.setRating(rating);
        advanceSequence("How much money did you pay?");
    }

    private void updateVisit(Restaurant r) {
        int visit = Integer.parseInt(getString());
        if (visit == 0) {
            sequence = 9;
            actionPerformed(new ActionEvent(this,  ActionEvent.ACTION_PERFORMED, null));
        } else {
            r.setVisited(visit);
            advanceSequence("What is the rating out of 10?");
        }
    }

    private void updateDish(Restaurant newRestaurant, String cuisine) {
        String dishes = getString();
        String dish = "";
        ArrayList<String> dishlist = new ArrayList<>();
        for (int i = 0; i < dishes.length(); i++) {
            if (dishes.charAt(i) != ',') {
                dish = dish + dishes.charAt(i);
            } else {
                dishlist.add(dish);
                dish = "";
            }
        }
        newRestaurant.setCuisine(new Cuisine(cuisine, dishlist));
        advanceSequence("How many times have you visited?");
    }

    private String updateCuisine() {
        String cuisine = getString();
        advanceSequence("Please enter the dishes you ate, separated by commas");
        return cuisine;
    }

    private void updateAddress(Restaurant newRestaurant, String area) {
        String address = getString();
        Location location = new Location(area, address);
        newRestaurant.addLocation(location);
        advanceSequence("What is the Cuisine?");
    }
}
