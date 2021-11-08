package ui;

import model.Location;
import model.Restaurant;
import model.RestaurantList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class MainFrame extends JFrame {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 400;
    private static final String JSON_STORE = "./data/RestaurantList.json";
    private RestaurantList mainList; // main list of all restaurants
    private RestaurantList filteredList; // list that stores restaurants based on filter
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    DefaultListModel<String> rlistModel;
    JTextArea main;
    Map<String, Restaurant> slist;
    Restaurant selectedR;

    public MainFrame(String title) {
        super(title);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        doLoadRestaurantList();
        initializeGraphics();
    }

    // EFFECTS: retrieves mainList from file at JSON_STORE
    private void doLoadRestaurantList() {
        try {
            mainList = jsonReader.read();
            System.out.println("Loaded " + mainList.getName() + " from file: " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to load Munch Map from file: " + JSON_STORE);
        }
    }

    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addList();
        addMainPane();
        addTools();
    }

    private void addMainPane() {
        main = new JTextArea();
        main.setMargin(new Insets(5,5,5,5));
        main.setEditable(false);
        main.setBackground(new Color(241, 255, 224));
        add(main, BorderLayout.NORTH);
        selectedR = mainList.get(0);
        if (selectedR != null) {
            printRestuarant();
        } else {
            main.append("Select a Restaurant!");
        }
    }

    private void printRestuarant() {
        main.append("Name: " + selectedR.getName());
        main.append("\nLocations:");
        for (Location l : selectedR.getLocations()) {
            main.append("\n\t" + l.getAddress() + " in " + l.getArea());
        }
        main.append("\nCuisine: " + selectedR.getCuisine().getEthnicity());
        for (String c : selectedR.getCuisine().getDishes()) {
            main.append("\n\t" + c);
        }
        if (selectedR.isVisited()) {
            main.append("\nTimes visited: " + selectedR.getVisited());
            main.append("\nRating: " + selectedR.getRating());
            main.append("\nPrice: " + selectedR.getPrice());
            main.append("\nReview: " + selectedR.getReview());
        }   else {
            main.append("\nYou've not been yet, hopefully soon!");
        }
    }

    private void addList() {
        JList rlist = new JList(addRestaurants());
        rlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rlist.setSelectedIndex(0);
        rlist.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(rlist);
        add(listScrollPane, BorderLayout.CENTER);
    }

    private ListModel addRestaurants() {
        rlistModel = new DefaultListModel();
        slist = new TreeMap<>();
        for (Restaurant r : mainList.getRestaurants()) {
            String description = printRestaurantMinimal(r);
            slist.put(description, r);
            rlistModel.addElement(description);
        }
        return rlistModel;
    }

    // EFFECTS: prints out minimal info on restaurant: name, area, ethnicity, rating, and price
    private String printRestaurantMinimal(Restaurant r) {
        String des;
        if (r.isVisited()) {
            des = r.getName() + " | Rating: " + r.getRating() + " | Price: " + r.getPrice();
        } else {
            des = r.getName() + " | Have not been.";
        }
        return des;
    }

    private void addTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0,1));
        toolArea.setSize(new Dimension(0, 0));
        toolArea.setVisible(true);
        add(toolArea, BorderLayout.SOUTH);
        JButton b1 = new JButton("poo");
        b1.setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame("MunchMap");
    }
}
