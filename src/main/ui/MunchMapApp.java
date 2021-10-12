package ui;

import model.Cuisine;
import model.Location;
import model.Restaurant;
import model.RestaurantList;

import java.util.ArrayList;
import java.util.Scanner;

// Munch Map Application
public class MunchMapApp {
    private RestaurantList mainList;
    private RestaurantList filteredList;
    private Scanner input;

    // EFFECTS: runs the Munch Map application
    public MunchMapApp() {
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

    // MODIFIES: this
    // EFFECTS: initializes saved restaurants;
    private void init() {
        mainList = new RestaurantList();
        filteredList = new RestaurantList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to MunchMap!");
        System.out.println("\nWhat would you like to do?");
        System.out.println("\ta -> Add a Restaurant");
        System.out.println("\ts -> View all Restaurants");
        System.out.println("\tv -> Filter Restaurants");
        System.out.println("\tw -> View Wish-listed Restaurants");
        System.out.println("\tr -> I don't know where to eat, pick for me!");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddRestaurant();
        } else if (command.equals("s")) {
            doAllRestaurant();
        } else if (command.equals("v")) {
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

    private void doFilteredRestaurantMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tc -> Cuisine");
        System.out.println("\tl -> Location");
        System.out.println("\tr -> Rating");
        System.out.println("\tp -> Price");
        System.out.println("\tv -> Visited");
    }

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

    private void doFilterPrice() {
        System.out.println("Press c to sort by cheapest, and l to sort by most expensive");
        if (input.next().equals("c")) {
            filteredList = mainList.sortByCheapest();
        } else if (input.next().equals("l")) {
            filteredList = mainList.sortByLuxury();
        }
        printRestaurantListMinimal(filteredList);
        doSelection();
    }

    private void printRestaurantListMinimal(RestaurantList list) {
        for (Restaurant r : list.getRestaurants()) {
            printRestaurantMinimal(r);
        }
    }

    private void doFilterRating() {
        filteredList = mainList.sortByRating();
        printRestaurantListMinimal(filteredList);
        doSelection();
    }

    private void doFilterLocation() {
        System.out.println("What area are we looking for?");
        filteredList = mainList.sortInLocation(input.next());
        printRestaurantListMinimal(filteredList);
        doSelection();
    }

    private void doFilterCuisine() {
        System.out.println("What kind of food are we looking for?");
        filteredList = mainList.sortInCuisine(input.next());
        printRestaurantListMinimal(filteredList);
        doSelection();
    }


    private void doAddRestaurant() {
        Restaurant newRestaurant = new Restaurant("", new ArrayList<>(),null,0);
        updateName(newRestaurant);
        updateArea(newRestaurant);
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

    private void updateVisit(Restaurant newRestaurant) {
        System.out.println("How many times have you visited?");
        int visited = input.nextInt();
        newRestaurant.setVisited(visited);
    }

    private void updatePrice(Restaurant newRestaurant) {
        System.out.println("Whats the price?");
        double price = input.nextDouble();
        newRestaurant.setPrice(price);
    }

    private void updateRating(Restaurant newRestaurant) {
        System.out.println("Whats the rating?");
        double rating = input.nextDouble();
        newRestaurant.setRating(rating);
    }

    private void reviewRestaurant(Restaurant r) {
        System.out.println("Please write what you thought");
        String review = input.next();
        r.setReview(review);
    }

    private void printRestaurant(Restaurant r) {
        System.out.println("Name: " + r.getName());
        System.out.println("\tLocations:");
        for (Location l : r.getLocations()) {
            System.out.println("\t\t" + l.getAddress() + " in " + l.getArea());
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

    private void updateCuisine(Restaurant r) {
        System.out.println("What kind of food is it?");
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

    private void updateArea(Restaurant r) {
        System.out.println("What is the area?");
        String area = input.next();
        System.out.println("Address?");
        String address = input.next();
        Location location = new Location(address, area);
        r.addLocation(location);
        System.out.println("Any more? Press Y to add more, else press any key to leave");
        if (input.next().equals("Y")) {
            updateArea(r);
        }
    }

    private void updateName(Restaurant r) {
        System.out.println("Please input Restaurant name");
        String name = input.next();
        r.setName(name);
    }

    private void doRandomRestaurantMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tp -> Random below a price");
        System.out.println("\tr -> Random above a rating");
        System.out.println("\td -> Random by a dish");
        System.out.println("\tc -> Random by a cuisine");
    }

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

    private void doRandomCuisine() {
        System.out.println("Where are you right now?");
        String area = input.next();
        System.out.println("What cuisine are you feeling?");
        Restaurant restaurant = mainList.randomRestaurantCuisine(input.next(), area);
        System.out.println("Here's your restaurant");
        printRestaurant(restaurant);
        System.out.println("If you don't like it, press R to roll again, or press any key to continue");
        if (input.next().equals("R")) {
            doRandomCuisine();
        }
    }

    private void doRandomDish() {
        System.out.println("Where are you right now?");
        String area = input.next();
        System.out.println("What dish are you feeling?");
        Restaurant restaurant = mainList.randomRestaurantDish(input.next(), area);
        System.out.println("Here's your restaurant");
        printRestaurant(restaurant);
        System.out.println("If you don't like it, press R to roll again, or press any key to continue");
        if (input.next().equals("R")) {
            doRandomDish();
        }
    }

    private void doRandomRating() {
        System.out.println("Where are you right now?");
        String area = input.next();
        System.out.println("Above what rating?");
        Restaurant restaurant = mainList.randomRestaurantRating(input.nextInt(), area);
        System.out.println("Here's your restaurant");
        printRestaurant(restaurant);
        System.out.println("If you don't like it, press R to roll again, or press any key to continue");
        if (input.next().equals("R")) {
            doRandomRating();
        }
    }

    private void doRandomPrice() {
        System.out.println("Where are you right now?");
        String area = input.next();
        System.out.println("How cheap are we feeling today?");
        Restaurant restaurant = mainList.randomRestaurantPrice(input.nextInt(), area);
        System.out.println("Here's your restaurant");
        printRestaurant(restaurant);
        System.out.println("If you don't like it, press R to roll again, or press any key to continue");
        if (input.next().equals("R")) {
            doRandomPrice();
        }
    }

    private void doWishListedRestaurants() {
        filteredList = mainList.sortByVisited();
        printRestaurantListMinimal(filteredList);
        doSelection();
    }

    private void doVisitedRestaurants() {
        filteredList = mainList.sortByVisited();
        printRestaurantListMinimal(filteredList);
        doSelection();
    }

    private void doAllRestaurant() {
        printRestaurantListMinimal(mainList);
        doSelection();
    }

    private void printRestaurantMinimal(Restaurant r) {
        if (r.isVisited()) {
            System.out.println(r.getName() + " | Rating: " + r.getRating() + " | Price: " + r.getPrice());
        } else {
            System.out.println(r.getName() + " | Have not been.");
        }
    }

    private void doSelection() {
        System.out.println("To Select a restaurant, please type it's name");
        if (mainList.containsRestaurant(input.next())) {
            Restaurant restaurant = mainList.getRestaurant(input.next());
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

    private void modifyMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\tv -> Visit Restaurant");
        System.out.println("\tn -> Change Name");
        System.out.println("\tw -> Write a Review");
        System.out.println("\tr -> Change Rating");
        System.out.println("\tp -> Change Price");
        System.out.println("\tl -> Add Location");
        System.out.println("\td -> Add Dish");
    }

    private void processModify(String command, Restaurant r) {
        if (command.equals("v")) {
            doAddRestaurant();
        } else if (command.equals("n")) {
            updateName(r);
        } else if (command.equals("w")) {
            reviewRestaurant(r);
        } else if (command.equals("r")) {
            updateRating(r);
        } else if (command.equals("p")) {
            updatePrice(r);
        } else if (command.equals("d")) {
            doAddDish(r);
        } else {
            System.out.println("That's not a valid command");
        }
        System.out.println("You're Restaurant is");
        printRestaurant(r);
        System.out.println("Press M to modify again, or press any key to continue");
        if (input.next().equals("M")) {
            modifyMenu();
            processModify(input.next(), r);
        }
    }

    private void doAddDish(Restaurant r) {
        System.out.println("What kind of dishes do they have?");
        r.getCuisine().addDish(input.next());
        System.out.println("Any more? Press Y to add more, else press any key to continue.");
        if (input.next().equals("Y")) {
            doAddDish(r);
        }
    }


}
