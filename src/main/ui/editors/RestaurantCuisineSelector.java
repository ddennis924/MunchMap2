package ui.editors;

import ui.MainFrame;

import java.awt.event.ActionEvent;

public class RestaurantCuisineSelector extends RestaurantEditor {

    // EFFECTS: Constructs a RestaurantCuisineSelector gui that sorts editor's mainList by cuisine
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

    // EFFECTS: sorts editor's mainList by cuisine
    private void sortCuisine() {
        String cuisine = textField.getText();
        editor.setFilteredList(editor.getMainList().sortInCuisine(cuisine));
        editor.printRestaurants(editor.getFilteredList());
    }
}
