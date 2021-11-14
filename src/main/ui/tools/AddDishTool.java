package ui.tools;

import ui.MainFrame;
import ui.rEditors.RestaurantCreator;
import ui.rEditors.RestaurantDishAdder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class AddDishTool extends Tool {

    public AddDishTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
        button.setMnemonic(KeyEvent.VK_D);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RestaurantDishAdder(editor);
            }
        });
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add Dishes");
        addToParent(parent);
    }
}
