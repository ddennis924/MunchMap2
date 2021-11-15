package ui.tools;

import ui.MainFrame;
import ui.editors.RestaurantCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class AddRestaurantTool extends Tool {

    // EFFECTS: Constructs an AddRestaurantTool that calls RestaurantCreator
    public AddRestaurantTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
        button.setMnemonic(KeyEvent.VK_A);
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
