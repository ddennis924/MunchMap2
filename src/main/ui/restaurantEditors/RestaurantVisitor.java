package ui.restaurantEditors;

import ui.MainFrame;

import java.awt.event.ActionEvent;

public class RestaurantVisitor extends RestaurantEditor {
    private double rating;
    private double price;

    public RestaurantVisitor(MainFrame e) {
        super(e);
    }

    @Override
    protected void setStartingPrompt() {
        title.setText("What was the rating this time?");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (sequence == 0) {
            updateRating();
        } else if (sequence == 1) {
            updatePrice();
        } else if (sequence == 2) {
            restaurant.visit(rating,price);
            editor.updateRestaurantList();
            editor.setRestaurant(restaurant);
            dispose();
        }
    }

    private void updatePrice() {
        price = Double.parseDouble(getString());
        sequence = 2;
        actionPerformed(new ActionEvent(this,  ActionEvent.ACTION_PERFORMED, null));
    }

    private void updateRating() {
        rating = Double.parseDouble(getString());
        advanceSequence("What did you pay this time?");
    }




}
