package ui.editors;

import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents frame that randomly selects a Restaurant from the MainFrame
public class RandomRestaurantSelector extends RestaurantEditor {
    String location;

    // EFFECTS: Constructs a RandomRestaurantSelector gui to randomly select from editor's mainList
    public RandomRestaurantSelector(MainFrame e) {
        super(e);
    }

    @Override
    protected void setStartingPrompt() {
        title.setText("What area are we in today?");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (sequence == 0) {
            location = getString();
            title.setText("What are we feeling like today?");
            textField.setVisible(false);
            JPanel options = new JPanel(new GridLayout(0, 4));
            options.setBackground(MainFrame.MAIN_COLOR);
            add(options, BorderLayout.SOUTH);
            initRandom(options, "Dish", "What is the dish?", 1);
            initRandom(options, "Cuisine", "What is the cuisine?", 2);
            initRandom(options, "Rating", "What is the rating?", 3);
            initRandom(options, "Price", "What is the price?", 4);
        } else if (sequence == 1) {
            doRandomDish();
        } else if (sequence == 2) {
            doRandomCuisine();
        } else if (sequence == 3) {
            doRandomRating();
        } else if (sequence == 4) {
            doRandomPrice();
        }
    }

    // MODIFIES: this
    // EFFECTS: selects a random restaurant from editor by price and sets it as editor's selectedR
    private void doRandomPrice() {
        double price = Double.parseDouble(getString());
        editor.setRestaurant(editor.getMainList().randomRestaurantPrice(price, location));
        dispose();
    }

    // MODIFIES: this
    // EFFECTS: selects a random restaurant from editor by rating and sets it as editor's selectedR
    private void doRandomRating() {
        double rating = Double.parseDouble(getString());
        editor.setRestaurant(editor.getMainList().randomRestaurantRating(rating, location));
        dispose();
    }

    // MODIFIES: this
    // EFFECTS: selects a random restaurant from editor by cuisine and sets it as editor's selectedR
    private void doRandomCuisine() {
        String cuisine = getString();
        editor.setRestaurant(editor.getMainList().randomRestaurantCuisine(cuisine, location));
        dispose();
    }

    // MODIFIES: this
    // EFFECTS: selects a random restaurant from editor by dish and sets it as editor's selectedR
    private void doRandomDish() {
        String dish = getString();
        editor.setRestaurant(editor.getMainList().randomRestaurantDish(dish, location));
        dispose();
    }

    // MODIFIES: this
    // EFFECTS: displays all the possible options to randomly select by
    private void initRandom(JPanel options, String s, String s2, int i) {
        JButton price = new JButton(s);
        price.setForeground(MainFrame.TEXT_COLOR);
        options.add(price);
        price.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title.setText(s2);
                remove(options);
                textField.setVisible(true);
                sequence = i;
            }
        });
    }
}
