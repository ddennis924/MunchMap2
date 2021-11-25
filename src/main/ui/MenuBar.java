package ui;

import javax.swing.*;
import java.awt.*;

// Represents a menu bar for MainFrame
public class MenuBar {
    JMenuBar menuBar;
    MainFrame editor;

    // EFFECTS: creates a menu bar with a given editor and restaurant sorting functionality
    public MenuBar(MainFrame editor) {
        this.editor = editor;
        init();
        menuBar.setForeground(MainFrame.TEXT_COLOR);
        menuBar.setBorderPainted(true);
    }

    // MODIFIES: this
    // EFFECTS: inserts RestaurantListSorter into menuBar
    public void init() {
        menuBar = new JMenuBar();
        new RestaurantListSorter(editor, menuBar);
        editor.add(menuBar, BorderLayout.NORTH);
    }

}
