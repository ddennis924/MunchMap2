package ui.tools;

import model.Restaurant;
import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveRestaurantTool extends Tool {

    public RemoveRestaurantTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Restaurant selectedR = editor.getSelectedR();
                editor.getMainList().removeRestaurant(selectedR.getName());
                editor.getSlist().remove(editor.printRestaurantMinimal(selectedR), selectedR);
                editor.getRlistModel().removeElement(editor.printRestaurantMinimal(selectedR));
            }
        });
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Remove");
        button.setPreferredSize(new Dimension(100, 50));
        button.setMargin(new Insets(5,5,5,5));
        addToParent(parent);
    }
}
