package ui.tools;

import ui.MainFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;

public abstract class Tool {
    protected JButton button;
    protected MainFrame editor;

    // EFFECTS: constructs a tool with a MainFram, parent component, and button
    public Tool(MainFrame editor, JComponent parent) {
        this.editor = editor;
        createButton(parent);
        addToParent(parent);
        customizeButton(button);
        addListener();
    }

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        button.setForeground(MainFrame.TEXT_COLOR);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: adds a listener for button
    protected abstract void addListener();

    // MODIFIES: parent
    // EFFECTS: adds button to parent
    protected void addToParent(JComponent parent) {
        parent.add(this.button);   
    }

    // MODIFIES this, parent
    // EFFECTS: creates a button with a given parent
    protected abstract void createButton(JComponent parent);

    // EFFECTS: default behaviour does nothing
    public void mousePressedInDrawingArea(MouseEvent e) {}

    // EFFECTS: default behaviour does nothing
    public void mouseReleasedInDrawingArea(MouseEvent e) {}

    // EFFECTS: default behaviour does nothing
    public void mouseClickedInDrawingArea(MouseEvent e) {}

    // EFFECTS: default behaviour does nothing
    public void mouseDraggedInDrawingArea(MouseEvent e) {}
    
}
