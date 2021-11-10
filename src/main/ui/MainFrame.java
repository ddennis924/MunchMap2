package ui;

import model.Location;
import model.Restaurant;
import model.RestaurantList;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tools.AddRestaurantTool;
import ui.tools.RemoveRestaurantTool;
import ui.tools.Tool;
import ui.tools.VisitTool;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private static final String JSON_STORE = "./data/RestaurantList.json";
    private RestaurantList mainList; // main list of all restaurants
    private RestaurantList filteredList; // list that stores restaurants based on filter
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DefaultListModel<String> rlistModel;
    private JTextArea main;
    private Map<String, Restaurant> slist;
    private Restaurant selectedR;

    public MainFrame(String title) {
        super(title);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        rlistModel = new DefaultListModel();
        slist = new HashMap<>();
        doLoadRestaurantList();
        initializeGraphics();
    }

    public RestaurantList getMainList() {
        return mainList;
    }

    public RestaurantList getFilteredList() {
        return filteredList;
    }

    public JsonWriter getJsonWriter() {
        return jsonWriter;
    }

    public JsonReader getJsonReader() {
        return jsonReader;
    }

    public DefaultListModel<String> getRlistModel() {
        return rlistModel;
    }

    public JTextArea getMain() {
        return main;
    }

    public Map<String, Restaurant> getSlist() {
        return slist;
    }

    public Restaurant getSelectedR() {
        return selectedR;
    }

    public void setMainList(RestaurantList mainList) {
        this.mainList = mainList;
    }

    public void setFilteredList(RestaurantList filteredList) {
        this.filteredList = filteredList;
    }

    public void setJsonWriter(JsonWriter jsonWriter) {
        this.jsonWriter = jsonWriter;
    }

    public void setJsonReader(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }

    public void setRlistModel(DefaultListModel<String> rlistModel) {
        this.rlistModel = rlistModel;
    }

    public void setMain(JTextArea main) {
        this.main = main;
    }

    public void setSlist(Map<String, Restaurant> slist) {
        this.slist = slist;
    }

    public void setSelectedR(Restaurant selectedR) {
        this.selectedR = selectedR;
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
        setVisible(true);
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(100, 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMainPanel();
        addListTools();
    }


    private void addMainPanel() {
        main = new JTextArea();
        main.setMargin(new Insets(5,5,5,5));
        main.setEditable(false);
        main.setBackground(new Color(241, 255, 224));
        main.setPreferredSize(new Dimension(main.getWidth(), 450));
        main.setSize(new Dimension(500, 800));
        JScrollPane p = new JScrollPane(main);
        p.setMinimumSize(new Dimension(300, 800));
        selectedR = null;
        printRestaurant();
        JPanel mainPane = new JPanel(new BorderLayout());
        add(mainPane, BorderLayout.CENTER);
        addList(mainPane);
        mainPane.add(p, BorderLayout.CENTER);
        addSelectionTools(mainPane);
    }

    private void addSelectionTools(JPanel mainPane) {
        JPanel toolArea = new JPanel(new GridLayout(4,1));
        mainPane.add(toolArea, BorderLayout.EAST);
        RemoveRestaurantTool removeRestaurantTool = new RemoveRestaurantTool(this, toolArea);
        VisitTool visitTool = new VisitTool(this, toolArea);

    }

    private void printRestaurant() {
        main.setText(null);
        if (selectedR == null) {
            main.append("No Restaurants exist :(");
        } else {
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
            } else {
                main.append("\nYou've not been yet, hopefully soon!");
            }
        }
    }

    private void addList(JPanel mainPane) {
        JList rlist = new JList(printRestaurants());
        rlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rlist.setSelectedIndex(0);
        rlist.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(rlist);
        mainPane.add(listScrollPane, BorderLayout.SOUTH);
        listScrollPane.setPreferredSize(new Dimension(listScrollPane.getWidth(), 150));
        rlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                    String selection = (String) rlist.getSelectedValue();
                    setRestaurant(slist.get(selection));
            }
        });
    }

    private ListModel printRestaurants() {
        for (Restaurant r : mainList.getRestaurants()) {
            String description = printRestaurantMinimal(r);
            slist.put(description, r);
            if (!rlistModel.contains(description)) {
                rlistModel.addElement(description);
            }
        }
        return rlistModel;
    }

    // EFFECTS: prints out minimal info on restaurant: name, area, ethnicity, rating, and price
    public String printRestaurantMinimal(Restaurant r) {
        String des;
        if (r.isVisited()) {
            des = r.getName() + " | Rating: " + r.getRating() + " | Price: " + r.getPrice();
        } else {
            des = r.getName() + " | Have not been.";
        }
        return des;
    }

    private void addListTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0,1));
        toolArea.setVisible(true);
        add(toolArea, BorderLayout.SOUTH);
        SearchBar search = new SearchBar(this,toolArea);
        AddRestaurantTool b1 = new AddRestaurantTool(this, toolArea);
        JButton b2 = new JButton("pee");
        toolArea.add(b2);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println();
            }
        });
    }

    public void setRestaurant(Restaurant r) {
        selectedR = r;
        printRestaurant();
    }

    public void addRestaurant(Restaurant r) {
        mainList.addRestaurant(r);
        String des = printRestaurantMinimal(r);
        slist.put(des, r);
        rlistModel.addElement(des);
    }

    public void updateRestaurantList() {
        rlistModel.clear();
        printRestaurants();
    }

    public static void main(String[] args) {
        new MainFrame("MunchMap");
    }
}
