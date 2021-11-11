package ui.rEditors;

import model.Location;
import ui.MainFrame;

import java.awt.event.ActionEvent;

public class RestaurantLocationAdder extends RestaurantEditor {
    String area;

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

    private void updateAddress() {
        String address = getString();
        Location location = new Location(area, address);
        editor.getSelectedR().addLocation(location);
        editor.setRestaurant(restaurant);
        dispose();
    }

    private void updateArea() {
        area = getString();
        advanceSequence("What is the Address?");
    }
}
