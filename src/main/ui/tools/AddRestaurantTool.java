package ui.tools;

import ui.MainFrame;
import ui.restaurantEditors.RestaurantCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRestaurantTool extends Tool {

    public AddRestaurantTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RestaurantCreator(editor);
            }
        });
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add a Restaurant");
        addToParent(parent);
    }

}
