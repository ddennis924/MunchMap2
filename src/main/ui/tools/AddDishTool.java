package ui.tools;

import ui.MainFrame;
import ui.editors.RestaurantDishAdder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class AddDishTool extends Tool {

    // EFFECTS: Constructs an AddDishTool that calls RestaurantDishAdder
    public AddDishTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
        button.setMnemonic(KeyEvent.VK_D);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editor.getSelectedR() != null) {
                    new RestaurantDishAdder(editor);
                } else {
                    editor.getMain().setText("Please select a restaurant");
                }
            }
        });
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add Dishes");
        addToParent(parent);
    }
}
