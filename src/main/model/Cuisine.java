package model;

import java.util.ArrayList;

public class Cuisine {
    private String ethnicity;
    private ArrayList<String> dishes;

    // EFFECTS: Constructs a cuisine with an ethnicity and specific dishes
    public Cuisine(String ethnicity, ArrayList<String> dishes) {
        this.ethnicity = ethnicity;
        this.dishes = dishes;
    }

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
    // EFFECTS: adds dish to list of dishes
    public void addDish(String dish) {
    } // stub

    // MODIFIES: this
    // EFFECTS: removes dish from list of dishes, returns true if dish is removed, false if there is no such dish
    public boolean removeDish(String dish) {
        return false; // stub
    }

    // EFFECTS: returns true if dish in dishes, false if not
    public boolean containsDish(String dish) {
        return false; // stub
    }



}
