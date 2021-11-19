package ui;

import ui.editors.RestaurantCuisineSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class RestaurantListSorter implements ItemListener {
    JMenu menu;
    MainFrame editor;
    List<JCheckBoxMenuItem> tools;

    // EFFECTS: Creates a gui for sorting restaurants with a list of tools
    public RestaurantListSorter(MainFrame editor, JMenuBar parent) {
        this.editor = editor;
        menu = new JMenu("Sort Menu");
        parent.add(menu);
        tools = new ArrayList<>();
        addTools();
    }

    // MODIFIES: this
    // EFFECTS: adds all possible methods of sorting restaurants
    private void addTools() {
        addMenuItem("Price (highest)");

        addMenuItem("Price (lowest)");

        addMenuItem("Rating");

        addMenuItem("Cuisine");

        addMenuItem("Visited");

        addMenuItem("Wish-listed");
    }

    // MODIFIES: this
    // EFFECTS: adds a checkBoxItem with the given string into menu
    private void addMenuItem(String s) {
        JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(s);
        menuItem.addItemListener(this);
        menuItem.setBorderPainted(true);
        menu.add(menuItem);
        tools.add(menuItem);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            JMenuItem source = (JCheckBoxMenuItem) e.getSource();
            String command = source.getText();
            deselectAllExcept(command);
            if (command.equals("Price (highest)")) {
                editor.setFilteredList(editor.getMainList().sortByLuxury());
            } else if (command.equals("Price (lowest)")) {
                editor.setFilteredList(editor.getMainList().sortByCheapest());
            } else if (command.equals("Rating")) {
                editor.setFilteredList(editor.getMainList().sortByRating());
            } else if (command.equals("Cuisine")) {
                new RestaurantCuisineSelector(editor);
            } else if (command.equals("Visited")) {
                editor.setFilteredList(editor.getMainList().sortByVisited());
            } else if (command.equals("Wish-listed")) {
                editor.setFilteredList(editor.getMainList().sortByWishlist());
            }
            editor.printRestaurants(editor.getFilteredList());
        } else {
            editor.printRestaurants(editor.getMainList());
        }
    }

    // MODIFIES: this
    // EFFECTS: deselects all checkboxes expect the one with the givne text s
    private void deselectAllExcept(String s) {
        for (JCheckBoxMenuItem i : tools) {
            if (!i.getText().equals(s)) {
                i.setSelected(false);
            }
        }
    }
}
