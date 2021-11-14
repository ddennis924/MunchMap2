package ui.tools;

import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class RandomRestaurantTool extends Tool {

    public RandomRestaurantTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
        button.setMnemonic(KeyEvent.VK_R);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Recommend a Restaurant!");
        addToParent(parent);
    }
}
