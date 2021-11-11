package ui.rEditors;

import ui.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;

public class RestaurantCuisineSelector extends RestaurantEditor {
    public RestaurantCuisineSelector(MainFrame e) {
        super(e);
    }

    @Override
    protected void setStartingPrompt() {
        title.setText("What Cuisine?");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sortCuisine();
        dispose();
    }

    private void sortCuisine() {
        String cuisine = textField.getText();
        editor.setFilteredList(editor.getMainList().sortInCuisine(cuisine));
        editor.printRestaurants(editor.getFilteredList());
    }
}
