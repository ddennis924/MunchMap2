package ui;

import model.Cuisine;
import model.Location;
import model.Restaurant;
import model.RestaurantList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Munch Map Application
public class MunchMapApp {
    private static final String JSON_STORE = "./data/RestaurantList.json";
    private RestaurantList mainList; // main list of all restaurants
    private RestaurantList filteredList; // list that stores restaurants based on filter
    private Scanner input; // key input
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Munch Map application
    public MunchMapApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runMunchMap();
    }

    // MODIFIES: this
    // EFFECTS: processes user inputs
    private void runMunchMap() {
        boolean keepGoing = true;
        String command;
        
        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Have a good meal!");
    }

    public RestaurantList getMainList() {
        return mainList;
    }

    public RestaurantList getFilteredList() {
        return filteredList;
    }

    public Scanner getInput() {
        return input;
    }

    public JsonWriter getJsonWriter() {
        return jsonWriter;
    }

    public JsonReader getJsonReader() {
        return jsonReader;
    }

    // EFFECTS: saves current mainList to file at JSON_STORE
    private void saveMainList() {
        try {
            jsonWriter.open();
            jsonWriter.write(mainList);
            jsonWriter.close();
            System.out.println("Saved " + mainList.getName() + " to file, have a great meal!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file:");
        }


    }

    // MODIFIES: this
    // EFFECTS: initializes saved restaurants
    private void init() {
        mainList = new RestaurantList("Dennis");
        filteredList = new RestaurantList("Filter");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays main menu options to user
    private void displayMenu() {
        System.out.println("\nWelcome to MunchMap!");
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tl -> load Restaurant list");
        System.out.println("\ts -> Save Current Restaurants");
        System.out.println("\ta -> Add a Restaurant");
        System.out.println("\tv -> View all Restaurants");
        System.out.println("\tf -> Filter Restaurants");
        System.out.println("\tw -> View Wish-listed Restaurants");
        System.out.println("\tr -> I don't know where to eat, pick for me!");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user commands for main menu
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddRestaurant();
        } else if (command.equals("l")) {
            doLoadRestaurantList();
        } else if (command.equals("s")) {
            saveMainList();
        } else if (command.equals("v")) {
            doAllRestaurant();
        } else if (command.equals("f")) {
            doFilteredRestaurantMenu();
            processFilter(input.next());
        } else if (command.equals("w")) {
            doWishListedRestaurants();
        } else if (command.equals("r")) {
            doRandomRestaurantMenu();
            processRandom(input.next());
        } else {
            System.out.println("Selection not valid...");
        }
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

    // EFFECTS: displays menu to filter Restaurants
    private void doFilteredRestaurantMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tc -> Cuisine");
        System.out.println("\tl -> Location");
        System.out.println("\tr -> Rating");
        System.out.println("\tp -> Price");
        System.out.println("\tv -> Visited");
    }

    // MODIFIES: this
    // EFFECTS: processes user commands for filter menu
    private void processFilter(String command) {
        if (command.equals("c")) {
            doFilterCuisine();
        } else if (command.equals("l")) {
            doFilterLocation();
        } else if (command.equals("r")) {
            doFilterRating();
        } else if (command.equals("p")) {
            doFilterPrice();
        } else if (command.equals("v")) {
            doVisitedRestaurants();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: filters mainList by either descending and ascending price and prints out filteredList
    private void doFilterPrice() {
        System.out.println("Press c to sort by cheapest, and l to sort by most expensive");
        String command = input.next();
        if (command.equals("c")) {
            filteredList = mainList.sortByCheapest();
        } else if (command.equals("l")) {
            filteredList = mainList.sortByLuxury();
        }
        printRestaurantListMinimal(filteredList);
        doSelection();
    }

    // EFFECTS: prints out list of restaurants displaying only name, price and rating if visited
    //          and only name if unvisited
    private void printRestaurantListMinimal(RestaurantList list) {
        for (Restaurant r : list.getRestaurants()) {
            printRestaurantMinimal(r);
        }
    }

    // MODIFIES: this
    // EFFECTS: filters list by top rating and prints restaurants, then prompts user to select a restaurant
    private void doFilterRating() {
        filteredList = mainList.sortByRating();
        printRestaurantListMinimal(filteredList);
        doSelection();
    }

    // MODIFIES: this
    // EFFECTS: filters list by location and prints restaurants, then prompts user to select a restaurant
    private void doFilterLocation() {
        System.out.println("What area are we looking for?");
        filteredList = mainList.sortInLocation(input.next());
        printRestaurantListMinimal(filteredList);
        doSelection();
    }

    // MODIFIES: this
    // EFFECTS: filters list by Cuisine ethnicity and prints restaurants, then prompts user to select a restaurant
    private void doFilterCuisine() {
        System.out.println("What kind of food are we looking for?");
        filteredList = mainList.sortInCuisine(input.next());
        printRestaurantListMinimal(filteredList);
        doSelection();
    }


    // MODIFIES: this
    // EFFECTS: adds a new visited or unvisited restaurants into mainList
    private void doAddRestaurant() {
        Restaurant newRestaurant = new Restaurant("", new ArrayList<>(),null,0);
        updateName(newRestaurant);
        addArea(newRestaurant);
        updateCuisine(newRestaurant);
        System.out.println("Have you visited before? Press Y if yes, else press any key");
        if (input.next().equals("Y")) {
            updateVisit(newRestaurant);
            updateRating(newRestaurant);
            updatePrice(newRestaurant);
            System.out.println("Any Review? Press Y if yes, else press any key");
            if (input.next().equals("Y")) {
                reviewRestaurant(newRestaurant);
            }
        }
        System.out.println("Okay, Restaurant has been made,");
        printRestaurant(newRestaurant);
        System.out.println("If something is wrong, press N to cancel, else press any key");
        if (!input.next().equals("N")) {
            mainList.addRestaurant(newRestaurant);
        }

    }

    // MODIFIES: newRestaurant
    // EFFECTS: updates the number of times visited of selected restaurant
    private void updateVisit(Restaurant newRestaurant) {
        System.out.println("How many times have you visited?");
        int visited = input.nextInt();
        if (visited >= 0) {
            newRestaurant.setVisited(visited);
        } else {
            System.out.println("visited must be greater than 0, please try again");
            updateVisit(newRestaurant);
        }
    }

    // MODIFIES: newRestaurant
    // EFFECTS: updates the price of selected restaurant
    private void updatePrice(Restaurant newRestaurant) {
        System.out.println("How much did you pay? (Number)");
        double price = input.nextDouble();
        if (price >= 0) {
            newRestaurant.setPrice(price);
        } else {
            System.out.println("Price must be greater than 0, please try again");
            updatePrice(newRestaurant);
        }
    }

    // MODIFIES: newRestaurant
    // EFFECTS: updates the price of selected restaurant
    private void updateRating(Restaurant newRestaurant) {
        System.out.println("Whats the rating out of 10?");
        double rating = input.nextDouble();
        if (rating <= 10 && rating >= 0) {
            newRestaurant.setRating(rating);
        } else {
            System.out.println("Rating must be in between 0 and 10, please try again");
            updateRating(newRestaurant);
        }
    }

    // MODIFIES: newRestaurant
    // EFFECTS: updates the review of selected restaurant
    private void reviewRestaurant(Restaurant r) {
        System.out.println("Please write what you thought");
        String review = input.next();
        r.setReview(review);
    }

    // EFFECTS: prints restaurant in depth, with name, locations, cuisine, as well as times visited, rating, price and
    //          review if visited
    private void printRestaurant(Restaurant r) {
        System.out.println("Name: " + r.getName());
        System.out.println("\tLocations:");
        for (Location l : r.getLocations()) {
            System.out.println("\t\t" + l.getAddress() + " in " + l.getArea());
        }
        System.out.println("\tCuisine: " + r.getCuisine().getEthnicity());
        for (String c : r.getCuisine().getDishes()) {
            System.out.println("\t\t" + c);
        }
        if (r.isVisited()) {
            System.out.println("\tTimes visited: " + r.getVisited());
            System.out.println("\tRating: " + r.getRating());
            System.out.println("\tPrice: " + r.getPrice());
            System.out.println("\tReview: " + r.getReview());
        }   else {
            System.out.println("\tYou've not been yet, hopefully soon!");
        }
    }

    // MODIFIES: r
    // EFFECTS: updates the Cuisine of selected restaurant
    private void updateCuisine(Restaurant r) {
        System.out.println("What ethnicity of food is it?");
        String ethnicity = input.next();
        System.out.println("What kind of dishes do they have?");
        ArrayList<String> dishes = new ArrayList<>();
        String dish = input.next();
        dishes.add(dish);
        Cuisine cuisine = new Cuisine(ethnicity,dishes);
        r.setCuisine(cuisine);
        System.out.println("Any more dishes? Press Y to add more, else press any key to leave");
        if (input.next().equals("Y")) {
            doAddDish(r);
        }
    }

    // MODIFIES: r
    // EFFECTS: adds a location into locations of restaurant
    private void addArea(Restaurant r) {
        System.out.println("What is the area? (City, Region, etc)");
        String area = input.next();
        System.out.println("Address?");
        String address = input.next();
        Location location = new Location(address, area);
        r.addLocation(location);
        System.out.println("Any more? Press Y to add more, else press any key to leave");
        if (input.next().equals("Y")) {
            addArea(r);
        }
    }

    // MODIFIES: r
    // EFFECTS: updates the name of restaurant
    private void updateName(Restaurant r) {
        System.out.println("Please input Restaurant name");
        String name = input.next();
        if (name.equals("")) {
            System.out.println("Invalid name, please try again");
        } else {
            r.setName(name);
        }
    }

    // EFFECTS: displays random restaurant menu options to user
    private void doRandomRestaurantMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tp -> Random below a price");
        System.out.println("\tr -> Random above a rating");
        System.out.println("\td -> Random by a dish");
        System.out.println("\tc -> Random by a cuisine");
    }


    // EFFECTS: processes user commands for random restaurant menu
    private void processRandom(String command) {
        if (command.equals("p")) {
            doRandomPrice();
        } else if (command.equals("r")) {
            doRandomRating();
        } else if (command.equals("d")) {
            doRandomDish();
        } else if (command.equals("c")) {
            doRandomCuisine();
        } else {
            System.out.println("That's not a valid command");
        }

    }

    // EFFECTS: prints a random restaurant based on selected cuisine and area
    private void doRandomCuisine() {
        System.out.println("Where are you right now?");
        String area = input.next();
        System.out.println("What cuisine are you feeling?");
        String cuisine = input.next();
        Restaurant restaurant = mainList.randomRestaurantCuisine(cuisine, area);
        System.out.println("Here's your restaurant");
        printRestaurant(restaurant);
        System.out.println("If you don't like it, press R to roll again, or press any key to continue");
        if (input.next().equals("R")) {
            doRandomCuisine();
        }
    }

    // EFFECTS: prints a random restaurant based on selected dish and area
    private void doRandomDish() {
        System.out.println("Where are you right now?");
        String area = input.next();
        System.out.println("What dish are you feeling?");
        String dish = input.next();
        Restaurant restaurant = mainList.randomRestaurantDish(dish, area);
        System.out.println("Here's your restaurant");
        printRestaurant(restaurant);
        System.out.println("If you don't like it, press R to roll again, or press any key to continue");
        if (input.next().equals("R")) {
            doRandomDish();
        }
    }

    // EFFECTS: prints a random restaurant above a rating based on selected area
    private void doRandomRating() {
        System.out.println("Where are you right now?");
        String area = input.next();
        System.out.println("Above what rating?");
        double rating = input.nextDouble();
        Restaurant restaurant = mainList.randomRestaurantRating(rating, area);
        System.out.println("Here's your restaurant");
        printRestaurant(restaurant);
        System.out.println("If you don't like it, press R to roll again, or press any key to continue");
        if (input.next().equals("R")) {
            doRandomRating();
        }
    }

    // EFFECTS: prints a random restaurant below a price based on selected area
    private void doRandomPrice() {
        System.out.println("Where are you right now?");
        String area = input.next();
        System.out.println("How cheap are we feeling today? (below what price)");
        double price = input.nextDouble();
        Restaurant restaurant = mainList.randomRestaurantPrice(price, area);
        System.out.println("Here's your restaurant");
        printRestaurant(restaurant);
        System.out.println("If you don't like it, press R to roll again, or press any key to continue");
        if (input.next().equals("R")) {
            doRandomPrice();
        }
    }

    // EFFECTS: prints out list of unvisited restaurants
    private void doWishListedRestaurants() {
        filteredList = mainList.sortByWishlist();
        printRestaurantListMinimal(filteredList);
        doSelection();
    }

    // EFFECTS: prints out list of visited restaurants
    private void doVisitedRestaurants() {
        filteredList = mainList.sortByVisited();
        printRestaurantListMinimal(filteredList);
        doSelection();
    }

    // EFFECTS: prints out list of all restaurants
    private void doAllRestaurant() {
        printRestaurantListMinimal(mainList);
        doSelection();
    }

    // EFFECTS: prints out minimal info on restaurant: name, area, ethnicity, rating, and price
    private void printRestaurantMinimal(Restaurant r) {
        if (r.isVisited()) {
            System.out.println(r.getName() + " | Rating: " + r.getRating() + " | Price: " + r.getPrice());
        } else {
            System.out.println(r.getName() + " | Have not been.");
        }
    }

    // EFFECTS: prompts user to search for a restaurant, then prompts user to modify selected restaurant
    private void doSelection() {
        System.out.println("To Select a restaurant, please type it's name");
        String command = input.next();
        if (mainList.containsRestaurant(command)) {
            Restaurant restaurant = mainList.getRestaurant(command);
            printRestaurant(restaurant);
            System.out.println("Is this the right restaurant? Press M to modify, or press any key to leave");
            if (input.next().equals("M")) {
                modifyMenu();
                processModify(input.next(), restaurant);
            }
        } else {
            System.out.println("No such Restaurant exists, please try again from the main menu");
        }
    }

    // EFFECTS: displays menu for modifying a restaurant
    private void modifyMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tv -> Visit Restaurant");
        System.out.println("\tn -> Change Name");
        System.out.println("\tw -> Write a Review");
        System.out.println("\tcr -> Change Rating");
        System.out.println("\tcp -> Change Price");
        System.out.println("\tl -> Add Location");
        System.out.println("\td -> Add Dish");
        System.out.println("\tr -> Remove a Restaurant");
    }

    // MODIFIES: r
    // EFFECTS: processes user commands for modify menu
    private void processModify(String command, Restaurant r) {
        if (command.equals("v")) {
            doVisitRestaurant(r);
        } else if (command.equals("n")) {
            updateName(r);
        } else if (command.equals("w")) {
            reviewRestaurant(r);
        } else if (command.equals("cr")) {
            updateRating(r);
        } else if (command.equals("cp")) {
            updatePrice(r);
        } else if (command.equals("l")) {
            addArea(r);
        } else if (command.equals("d")) {
            doAddDish(r);
        } else if (command.equals("r")) {
            doRemoveRestaurant(r);
        } else {
            System.out.println("That's not a valid command");
        }
        printRestaurant(r);
        System.out.println("Here is the updated restaurant");
    }

    // REQUIRES: r must be in mainList
    // MODIFIES: this
    // EFFECTS: removes restaurant from mainList
    private void doRemoveRestaurant(Restaurant r) {
        mainList.removeRestaurant(r.getName());
        System.out.print("Your Restaurant " + r.getName() + " has been removed, if this was a mistake, "
                + "press N to cancel, else press any key to continue");
        if (input.next().equals("N")) {
            mainList.addRestaurant(r);
            System.out.println("Your Restaurant " + r.getName() + " has been re-added.");
        }
    }

    // MODIFIES: r
    // EFFECTS: increases visits of restaurant by one and modifies rating and price
    private void doVisitRestaurant(Restaurant r) {
        System.out.println("How was the rating out of 10 this time?");
        double rating = input.nextDouble();
        if (rating > 10 | rating < 0) {
            System.out.println("invalid rating, please try again");
            doVisitRestaurant(r);
        } else {
            System.out.println("How much $$$ did you pay this time?");
            double price = input.nextDouble();
            if (price < 0) {
                System.out.println("invalid price, please try again");
                doVisitRestaurant(r);
            } else {
                r.visit(rating, price);
                System.out.println("Any review? If yes press Y, else press any key to update restaurant");
                if (input.next().equals("Y")) {
                    reviewRestaurant(r);
                } else {
                    printRestaurant(r);
                }
            }
        }
    }

    // MODIFIES: r
    // EFFECTS: adds a new dish to restaurant
    private void doAddDish(Restaurant r) {
        System.out.println("What kind of dishes do they have?");
        r.getCuisine().addDish(input.next());
        System.out.println("Any more? Press Y to add more, else press any key to continue.");
        if (input.next().equals("Y")) {
            doAddDish(r);
        }
    }

    /* // MODIFIES: this
    // EFFECTS: inserts example restaurants McDonald's, Sura, and Saku into mainList
    public void insertPreloadedRestaurants() {
        ArrayList<Location> mcLocations = new ArrayList<>();
        mcLocations.add(new Location("1100 main mall", "UBC"));
        mcLocations.add(new Location("2314 no 2 road", "Richmond"));
        ArrayList<String> dishes = new ArrayList<>();
        dishes.add("Water");
        Cuisine mcCuisine = new Cuisine("Fast Food", dishes);
        Restaurant  mcDonalds = new Restaurant("McDonald's", mcLocations, mcCuisine, 0);
        mainList.addRestaurant(mcDonalds);

        ArrayList<Location> suraLocations = new ArrayList<>();
        suraLocations.add(new Location("6 road", "Vancouver"));
        suraLocations.add(new Location("123 parliament ave", "Victoria"));
        Restaurant sura = new Restaurant("Sura", suraLocations, new Cuisine("Korean", dishes), 0);
        mainList.addRestaurant(sura);

        ArrayList<Location> sakuLocations = new ArrayList<>();
        sakuLocations.add(new Location("123 west ave", "Vancouver"));
        Cuisine sakuCuisine = new Cuisine("Japanese", new ArrayList<>());
        sakuCuisine.addDish("Katsu");
        sakuCuisine.addDish("Water");
        Restaurant saku = new Restaurant("Saku", sakuLocations, sakuCuisine, 0);
        mainList.addRestaurant(saku);
    } */
}
