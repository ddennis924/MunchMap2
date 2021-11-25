package ui.tools;

import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents gui class that saves mainframeList to file
public class SaveTool extends Tool {

    // EFFECTS: constructs a save button that saves editor's mainList
    public SaveTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
    }

    @Override
    protected void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.save();
            }
        });
    }

    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Save");
        button.setPreferredSize(new Dimension(100, 50));
        addToParent(parent);
    }
}
