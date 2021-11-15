package ui.rEditors;

import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RandomRestaurantSelector extends RestaurantEditor {
    String location;

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
            add(options, BorderLayout.SOUTH);
            initRandom(options, "I have a specific dish", "What is the dish?", 1);
            initRandom(options, "I have a cuisine", "What is the cuisine?", 2);
            initRandom(options, "By rating", "What is the rating?", 3);
            initRandom(options, "By Price", "What is the price?", 4);
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

    private void doRandomPrice() {
        double price = Double.parseDouble(getString());
        editor.setRestaurant(editor.getMainList().randomRestaurantPrice(price, location));
        dispose();
    }

    private void doRandomRating() {
        double rating = Double.parseDouble(getString());
        editor.setRestaurant(editor.getMainList().randomRestaurantRating(rating, location));
        dispose();
    }

    private void doRandomCuisine() {
        String cuisine = getString();
        editor.setRestaurant(editor.getMainList().randomRestaurantCuisine(cuisine, location));
        dispose();
    }

    private void doRandomDish() {
        String dish = getString();
        editor.setRestaurant(editor.getMainList().randomRestaurantDish(dish, location));
        dispose();
    }

    private void initRandom(JPanel options, String s, String s2, int i) {
        JButton price = new JButton(s);
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
