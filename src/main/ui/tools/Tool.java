package ui.tools;

import ui.MainFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;

public abstract class Tool {
    protected JButton button;
    protected MainFrame editor;

    public Tool(MainFrame editor, JComponent parent) {
        this.editor = editor;
        createButton(parent);
        addToParent(parent);
        addListener();
    }

    // MODIFIES: this
    // EFFECTS:  customizes the button used for this tool
    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    protected abstract void addListener();

    protected void addToParent(JComponent parent) {
        parent.add(this.button);   
    }

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
