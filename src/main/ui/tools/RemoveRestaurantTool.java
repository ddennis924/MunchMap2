package ui.tools;

import model.Restaurant;
import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents gui class that removes Restaurant from mainList in Mainframe
public class RemoveRestaurantTool extends Tool {

    // EFFECTS: constructs a removeRestaurantTool that removes a restaurant from editor's mainList
    public RemoveRestaurantTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editor.getSelectedR() != null) {
                    Restaurant selectedR = editor.getSelectedR();
                    editor.getMainList().removeRestaurant(selectedR.getName());
                    editor.getSlist().remove(editor.printRestaurantMinimal(selectedR), selectedR);
                    editor.getRlistModel().removeElement(editor.printRestaurantMinimal(selectedR));
                } else {
                    editor.getMain().setText("Please select a restaurant");
                }
            }
        });
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Remove");
        button.setPreferredSize(new Dimension(100, 50));
        addToParent(parent);
    }
}
