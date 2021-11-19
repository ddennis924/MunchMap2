package ui.editors;

import model.Location;
import ui.MainFrame;

import java.awt.event.ActionEvent;

public class RestaurantLocationAdder extends RestaurantEditor {
    String area;

    // EFFECTS: Constructs a restaurantLocationAdder to add a location to editor's selectedR
    public RestaurantLocationAdder(MainFrame e) {
        super(e);
        area = "";
    }

    @Override
    protected void setStartingPrompt() {
        title.setText("What is the Area?");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (sequence == 0) {
            updateArea();
        } else if (sequence == 1) {
            updateAddress();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a location to editor's selectedR
    private void updateAddress() {
        String address = getString();
        Location location = new Location(address, area);
        editor.getSelectedR().addLocation(location);
        editor.setRestaurant(restaurant);
        dispose();
    }

    // MODIFIES: this
    // EFFECTS: sets area to value in textField
    private void updateArea() {
        area = getString();
        advanceSequence("What is the Address?");
    }
}
