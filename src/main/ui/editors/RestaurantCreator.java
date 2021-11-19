package ui.editors;

import model.Cuisine;
import model.Location;
import model.Restaurant;
import ui.MainFrame;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RestaurantCreator extends RestaurantEditor {
    private String area;
    private String cuisine;

    // EFFECTS: Constructs a RestaurantCreator gui that adds a restaurant to editor's mainList
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

    // MODIFIES: this, r
    // EFFECTS: sets r's name to the value in textField and advances sequence
    public void updateName(Restaurant r) {
        String name = getString();
        r.setName(name);
        advanceSequence("What is the area?");
    }

    // EFFECTS: returns the value in textField;
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

    // MODIFIES: this, newRestaurant
    // EFFECTS: sets newRestaurant's review to the value in textField and advances sequence
    private void updateReview(Restaurant newRestaurant) {
        String review = getString();
        newRestaurant.setReview(review);
        sequence = 9;
        actionPerformed(new ActionEvent(this,  ActionEvent.ACTION_PERFORMED, null));
    }

    // MODIFIES: newRestaurant, this
    // EFFECTS: sets newRestaurant's price to value in textField and advances sequence
    private void updatePrice(Restaurant newRestaurant) {
        double price = Double.parseDouble(getString());
        newRestaurant.setPrice(price);
        advanceSequence("Any review?");
    }

    // MODIFIES: newRestaurant, this
    // EFFECTS: sets newRestaurant's rating to value in textField and advances sequence
    private void updateRating(Restaurant newRestaurant) {
        double rating = Double.parseDouble(getString());
        newRestaurant.setRating(rating);
        advanceSequence("How much money did you pay?");
    }

    // MODIFIES: this, r
    // EFFECTS: sets r's visit count to value in textField and advances sequence
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

    // MODIFIES: this, newRestaurant
    // EFFECTS: sets newRestaurant's cuisine dishes to value in textField
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

    // EFFECTS: returns the value in textField and advances sequence
    private String updateCuisine() {
        String cuisine = getString();
        advanceSequence("Please enter the dishes you ate, separated by commas");
        return cuisine;
    }

    // MODIFIES: this, newRestaurant
    // EFFECTS: sets newRestaurant's location with area and address and advances sequence
    private void updateAddress(Restaurant newRestaurant, String area) {
        String address = getString();
        Location location = new Location(address, area);
        newRestaurant.addLocation(location);
        advanceSequence("What is the Cuisine?");
    }
}
