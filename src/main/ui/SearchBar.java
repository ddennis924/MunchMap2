package ui;

import model.Restaurant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchBar implements ActionListener {
    protected JTextField textField;
    protected JComponent parent;
    protected MainFrame editor;

    public SearchBar(MainFrame m, JComponent p) {
        textField = new JTextField(20);
        textField.addActionListener(this);
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
