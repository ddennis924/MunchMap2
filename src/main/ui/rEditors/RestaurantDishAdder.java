package ui.rEditors;

import model.Restaurant;
import ui.MainFrame;

import java.awt.event.ActionEvent;

public class RestaurantDishAdder extends RestaurantEditor {

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
