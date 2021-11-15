package ui.tools;

import model.Restaurant;
import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class SearchBar implements ActionListener {
    protected JTextField textField;
    protected JComponent parent;
    protected MainFrame editor;

    // EFFECTS: Constructs a searchBar with a textField to search through a RestaurantList
    public SearchBar(MainFrame m, JComponent p) {
        textField = new JTextField("Search...");
        textField.setForeground(new Color(145,145,145));
        textField.addActionListener(this);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setText("");
                textField.setForeground(MainFrame.TEXT_COLOR);
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setText("Search...");
                textField.setForeground(new Color(145,145,145));
            }
        });
        textField.setBackground(MainFrame.SECOND_COLOR);
        this.editor = m;
        this.parent = p;
        p.add(textField);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        boolean found = false;
        String text = textField.getText();
        textField.selectAll();
        for (Restaurant r : editor.getMainList().getRestaurants()) {
            if (text.equals(r.getName())) {
                editor.setRestaurant(r);
                found = true;
            }
        }
        if (!found) {
            editor.setRestaurant(null);
        }
    }
}
