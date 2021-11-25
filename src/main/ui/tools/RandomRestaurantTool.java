package ui.tools;

import ui.MainFrame;
import ui.editors.RandomRestaurantSelector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

// Represents gui class that accesses RandomRestaurantSelector
public class RandomRestaurantTool extends Tool {

    // EFFECTS: Constructs a RandomRestaurantTool that calls RandomRestaurantSelector
    public RandomRestaurantTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
        button.setMnemonic(KeyEvent.VK_R);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RandomRestaurantSelector(editor);
            }
        });
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Recommend a Restaurant!");
        addToParent(parent);
    }
}
