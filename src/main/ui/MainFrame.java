package ui;

import model.Location;
import model.Restaurant;
import model.RestaurantList;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tools.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    public static final int WIDTH = 450;
    public static final int HEIGHT = 550;
    public static final Color MAIN_COLOR = new Color(47,49,54);
    public static final Color SECOND_COLOR = new Color(55,57,63);
    public static final Color TEXT_COLOR = new Color(198,199,201);
    private static final String JSON_STORE = "./data/RestaurantList.json";
    private RestaurantList mainList; // main list of all restaurants
    private RestaurantList filteredList; // list that stores restaurants based on filter
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DefaultListModel<String> rlistModel;
    private JTextArea main;
    private Map<String, Restaurant> slist;
    private Restaurant selectedR;

    // MODIFIES: this
    // EFFECTS: constructs a GUI for MunchMap with save/load functionality
    public MainFrame(String title) {
        super(title);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        rlistModel = new DefaultListModel();
        slist = new HashMap<>();
        selectedR = null;
        init();
        doLoadRestaurantList();
    }

    // MODIFIES: this
    // EFFECTS: initializes graphics for this
    private void init() {
        setVisible(true);
        setLayout(new BorderLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(400, 450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // getters and setters
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

    // MODIFIES: this
    // EFFECTS: retrieves mainList from file at JSON_STORE
    private void doLoadRestaurantList() {
        JPanel loadPanel = new JPanel(new GridBagLayout());
        add(loadPanel);
        loadPanel.setPreferredSize(new Dimension(WIDTH, 100));
        loadPanel.setBackground(new Color(18, 19, 21));
        loadPanel.setForeground(TEXT_COLOR);
        initLoadTools(loadPanel);
    }

    // MODIFIES: this, loadPanel
    // EFFECTS: initializes screen to choose between loading save file and new file
    private void initLoadTools(JPanel loadPanel) {
        addFiller(loadPanel);
        initLoadTool(loadPanel);
        initNewListTool(loadPanel);
        revalidate();
        repaint();
    }

    // MODIFIES: this, loadPanel
    // EFFECTS: creates new file button
    private void initNewListTool(JPanel loadPanel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 1;
        c.weightx = 1;
        JButton newList = new JButton("New List");
        loadPanel.add(newList, c);
        newList.setForeground(new Color(224, 166, 166));
        newList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainList = new RestaurantList("NewList");
                initializeGraphics(loadPanel);
            }
        });
    }

    // MODIFIES: this, loadPanel
    // EFFECTS: create load file button
    private void initLoadTool(JPanel loadPanel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.weighty = 1;
        c.weightx = 1;
        JButton load = new JButton("Load Restaurants");
        loadPanel.add(load, c);
        load.setForeground(new Color(185, 213, 186));
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                try {
                    mainList = jsonReader.read();
                    System.out.println("Loaded " + mainList.getName() + " from file: " + JSON_STORE);
                    initializeGraphics(loadPanel);
                } catch (IOException e) {
                    System.out.println("Unable to load Munch Map from file: " + JSON_STORE);
                }
            }
        });
    }

    // MODIFIES: loadPanel
    // EFFECTS: adds image to loadPanel
    private void addFiller(JPanel loadPanel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        try {
            BufferedImage imageB = ImageIO.read(new File("./src/munchmapimage2.png"));
            JLabel image =
                    new JLabel(new ImageIcon(imageB.getScaledInstance(WIDTH, HEIGHT - 100, Image.SCALE_DEFAULT)));
            image.setPreferredSize(new Dimension(WIDTH - 100, HEIGHT - 100));
            loadPanel.add(image, c);
        } catch (IOException e) {
            System.out.println("Image not found");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes main RestaurantList panel and editor
    private void initializeGraphics(JComponent previous) {
        remove(previous);
        addTopMenu();
        addMainPanel();
        addListTools();
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: adds menu bar
    private void addTopMenu() {
        new MenuBar(this);
    }


    // MODIFIES: this
    // EFFECTS: adds Restaurant display panel
    private void addMainPanel() {
        initializeMain();
        JScrollPane p = new JScrollPane(main);
        p.setBackground(MAIN_COLOR);
        printRestaurant();
        JPanel mainPane = new JPanel(new BorderLayout());
        mainPane.setBackground(MAIN_COLOR);
        add(mainPane, BorderLayout.CENTER);
        addList(mainPane);
        mainPane.add(p, BorderLayout.CENTER);
        addSelectionTools(mainPane);
    }

    // MODIFIES: this
    // EFFECTS: initializes main
    private void initializeMain() {
        main = new JTextArea();
        main.setEditable(false);
        main.setBackground(SECOND_COLOR);
        main.setForeground(TEXT_COLOR);
    }

    // MODIFIES: mainPane
    // EFFECTS: adds selection tools to mainPane
    private void addSelectionTools(JPanel mainPane) {
        JPanel toolArea = new JPanel(new GridLayout(4,1));
        toolArea.setBackground(MAIN_COLOR);
        mainPane.add(toolArea, BorderLayout.EAST);
        new RemoveRestaurantTool(this, toolArea);
        new VisitTool(this, toolArea);
        new AddLocationTool(this, toolArea);
        new AddDishTool(this, toolArea);
    }

    // MODIFIES: this
    // EFFECTS: if selectedR is null, displays no restaurants exist, otherwise displays the restaurant in main
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

    // MODIFIES: mainPane
    // EFFECTS: adds mainList to mainPane and initializes list display
    private void addList(JPanel mainPane) {
        JList rlist = new JList(printRestaurants(mainList));
        rlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rlist.setSelectedIndex(0);
        rlist.setVisibleRowCount(5);
        rlist.setBackground(SECOND_COLOR);
        rlist.setForeground(TEXT_COLOR);
        JScrollPane listScrollPane = new JScrollPane(rlist);
        listScrollPane.setBackground(MAIN_COLOR);
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

    // MODIFIES: this
    // EFFECTS: clears rlistModel and fills it with Restaurants in list, then returns it
    public ListModel printRestaurants(RestaurantList list) {
        rlistModel.clear();
        for (Restaurant r : list.getRestaurants()) {
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

    // MODIFIES: this
    // EFFECTS: adds list tools to this
    private void addListTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0,1));
        toolArea.setPreferredSize(new Dimension(WIDTH, 150));

        toolArea.setBackground(MAIN_COLOR);
        add(toolArea, BorderLayout.SOUTH);
        new SearchBar(this,toolArea);
        new AddRestaurantTool(this, toolArea);
        new RandomRestaurantTool(this, toolArea);
        new SaveTool(this, toolArea);
    }

    // MODIFIES: this
    // EFFECTS: sets selectedR to r
    public void setRestaurant(Restaurant r) {
        selectedR = r;
        printRestaurant();
    }

    // MODIFIES: this
    // EFFECTS: adds r to mainList
    public void addRestaurant(Restaurant r) {
        mainList.addRestaurant(r);
        String des = printRestaurantMinimal(r);
        slist.put(des, r);
        rlistModel.addElement(des);
    }

    // MODIFIES: this
    // EFFECTS: reprints mainList
    public void updateRestaurantList() {
        rlistModel.clear();
        printRestaurants(mainList);
    }

    // EFFECTS: saves mainList to file
    public void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(mainList);
            jsonWriter.close();
            main.setText("Saved " + mainList.getName() + " to file, have a great meal!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file:");
        }
    }

    public static void main(String[] args) {
        new MainFrame("MunchMap");
    }
}
