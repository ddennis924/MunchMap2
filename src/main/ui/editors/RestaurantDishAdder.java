package ui.editors;

import model.Restaurant;
import ui.MainFrame;

import java.awt.event.ActionEvent;

// Represents frame that adds a dish to selectedR in MainFrame
public class RestaurantDishAdder extends RestaurantEditor {

    // EFFECTS: Constructs a RestaurantDishAdder that adds a dish to cuisine editor's selectedR
    public RestaurantDishAdder(MainFrame e) {
        super(e);
    }

    @Override
    protected void setStartingPrompt() {
        title.setText("Add Dishes");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String dish = getString();
        Restaurant restaurant = editor.getSelectedR();
        restaurant.getCuisine().getDishes().add(dish);
        editor.setRestaurant(restaurant);
    }
}
