package ui.editors;

import model.Restaurant;
import ui.MainFrame;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class RestaurantEditor extends JFrame implements ActionListener {
    protected MainFrame editor;
    protected JTextField textField;
    protected JTextPane title;
    protected Restaurant restaurant;
    protected int sequence;

    // REQUIRES: editor.getSelectedR != null
    // EFFECTS: constructs a RestaurantEditor gui with a given MainFrame editor that operates on editor's mainList
    public RestaurantEditor(MainFrame e) {
        this.editor = e;
        restaurant = editor.getSelectedR();
        sequence = 0;
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initializes the gui
    protected void initializeGraphics() {
        setLayout(new BorderLayout());
        JPanel main = new JPanel(new BorderLayout());
        add(main, BorderLayout.CENTER);
        main.setBackground(MainFrame.MAIN_COLOR);
        setSize(new Dimension(400, 85));
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(RestaurantCreator.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setLocationRelativeTo(editor.getMain());
        addPrompts(main);
    }

    // MODIFIES: this
    // EFFECTS: display the first prompt message and layout
    protected void addPrompts(JPanel main) {
        initializeTitle(main);
        initializeTextField(main);
        setStartingPrompt();
    }

    // MODIFIES: this
    // EFFECTS: initializes textField
    private void initializeTextField(JPanel main) {
        textField = new JTextField();
        textField.addActionListener(this);
        textField.setBackground(new Color(99, 101, 104));
        main.add(textField, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes title
    private void initializeTitle(JPanel main) {
        title = new JTextPane();
        title.setVisible(true);
        title.setEditable(false);
        title.setBackground(MainFrame.MAIN_COLOR);
        title.setForeground(MainFrame.TEXT_COLOR);
        title.setPreferredSize(new Dimension(400, 25));
        StyledDocument doc = title.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        main.add(title, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: returns the current text in textField and removes it from textField
    protected String getString() {
        String text = textField.getText();
        textField.setText("");
        return text;
    }

    // MODIFIES: this
    // EFFECTS: increases sequence by one and returns it
    protected int advanceSequence(String text) {
        title.setText(text);
        return sequence++;
    }

    protected abstract void setStartingPrompt();
}
