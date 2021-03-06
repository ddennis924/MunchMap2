package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

// Represents a Cuisine with an ethnicity and a list of dishes
public class Cuisine {
    private String ethnicity;
    private ArrayList<String> dishes;

    // REQUIRES: ethnicity and dishes must be non-zero length strings
    // MODIFIES: this
    // EFFECTS: Constructs a cuisine with an ethnicity and specific dishes
    public Cuisine(String ethnicity, ArrayList<String> dishes) {
        this.ethnicity = ethnicity;
        this.dishes = dishes;
    }

    // setters and getters
    public String getEthnicity() {
        return ethnicity;
    }

    public ArrayList<String> getDishes() {
        return dishes;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    // MODIFIES: this
    // EFFECTS: adds dish to list of dishes unless dish is already in dishes, then does nothing
    public void addDish(String dish) {
        if (!dishes.contains(dish)) {
            this.dishes.add(dish);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes dish from dishes, returns true if dish is removed,
    //          false if dish not found
    public boolean removeDish(String dish) {
        boolean found = false;
        ArrayList<String> newDishes = new ArrayList<>();
        for (String d : dishes) {
            if (Objects.equals(dish, d)) {
                found = true;
            } else {
                newDishes.add(d);
            }
        }
        this.dishes = newDishes;
        return found;
    }

    // EFFECTS: returns true if dish in dishes, false if not
    public boolean containsDish(String dish) {
        return dishes.contains(dish);
    }

}
