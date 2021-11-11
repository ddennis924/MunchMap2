package ui.tools;

import ui.MainFrame;
import ui.rEditors.RestaurantCreator;
import ui.rEditors.RestaurantLocationAdder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddLocationTool extends Tool {
    public AddLocationTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RestaurantLocationAdder(editor);
            }
        });
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Add Location");
        addToParent(parent);
    }
}
