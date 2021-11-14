package ui.tools;

import ui.MainFrame;
import ui.rEditors.RestaurantVisitor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class VisitTool extends Tool {
    public VisitTool(MainFrame editor, JComponent parent) {
        super(editor, parent);
        button.setMnemonic(KeyEvent.VK_V);
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
