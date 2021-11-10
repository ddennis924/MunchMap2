package ui.tools;

import ui.MainFrame;
import ui.restaurantEditors.RestaurantVisitor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisitTool extends Tool {
    public VisitTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RestaurantVisitor(editor);
            }
        });
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Visit");
        addToParent(parent);
    }
}
