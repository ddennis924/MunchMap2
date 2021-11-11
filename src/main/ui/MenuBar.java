package ui;

import javax.swing.*;
import java.awt.*;

public class MenuBar {
    JMenuBar menuBar;
    MainFrame editor;

    public MenuBar(MainFrame editor) {
        this.editor = editor;
        init();
        menuBar.setForeground(MainFrame.TEXT_COLOR);
        menuBar.setBorderPainted(true);
    }

    public void init() {
        menuBar = new JMenuBar();
        new RestaurantListSorter(editor, menuBar);
        editor.add(menuBar, BorderLayout.NORTH);
    }

}
