package ui.restaurantEditors;

import model.Restaurant;
import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class RestaurantEditor extends JFrame implements ActionListener {
    protected MainFrame editor;
    protected JTextField textField;
    protected JTextArea title;
    protected Restaurant restaurant;
    protected int sequence;

    public RestaurantEditor(MainFrame e) {
        this.editor = e;
        restaurant = editor.getSelectedR();
        sequence = 0;
        initializeGraphics();
    }

    protected void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(400, 100));
        setVisible(true);
        setDefaultCloseOperation(RestaurantCreator.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setLocationRelativeTo(editor.getMain());
        addPrompts();
    }

    protected void addPrompts() {
        title = new JTextArea();
        title.setVisible(true);
        title.setEditable(false);
        title.setBackground(new Color(207, 238, 250));
        textField = new JTextField();
        textField.setVisible(true);
        add(title, BorderLayout.NORTH);
        add(textField, BorderLayout.CENTER);
        textField.addActionListener(this);
        setStartingPrompt();
    }

    protected String getString() {
        String text = textField.getText();
        textField.setText("");
        return text;
    }

    protected int advanceSequence(String text) {
        title.setText(text);
        return sequence++;
    }

    protected abstract void setStartingPrompt();
}
