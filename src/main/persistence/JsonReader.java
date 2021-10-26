package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
// Code modeled after JsonWriter in JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads RestaurantList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RestaurantList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRestaurantList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses RestaurantList from JSON object and returns it
    private RestaurantList parseRestaurantList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        RestaurantList rl = new RestaurantList(name);
        addRestaurants(rl, jsonObject);
        return rl;
    }

    // MODIFIES: rl
    // EFFECTS: parses Restaurants from JSONobject and adds them to rl
    private void addRestaurants(RestaurantList rl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("restaurants");
        for (Object json : jsonArray) {
            JSONObject nextRestaurant = (JSONObject) json;
            addRestaurant(rl, nextRestaurant);
        }
    }

    // MODIFIES: rl
    // EFFECTS: parses Restaurant from JSON object and adds it to rl
    private void addRestaurant(RestaurantList rl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");

        JSONArray locationsArray = jsonObject.getJSONArray("locations");
        ArrayList<Location> locations = new ArrayList<>();
        addLocations(locationsArray, locations);

        JSONArray dishesArray = jsonObject.getJSONArray("dishes");
        ArrayList<String> dishes = new ArrayList<>();

        addDishes(dishesArray, dishes);
        String ethnicity = jsonObject.getString("ethnicity");
        Cuisine cuisine = new Cuisine(ethnicity,dishes);

        int visits = jsonObject.getInt("visits");
        Restaurant restaurant = new Restaurant(name, locations,cuisine,visits);
        if (visits > 0) {
            double price = jsonObject.getDouble("price");
            double rating = jsonObject.getDouble("rating");
            String review = jsonObject.getString("review");

            restaurant.setPrice(price);
            restaurant.setRating(rating);
            restaurant.setReview(review);
        }
        rl.addRestaurant(restaurant);
    }

    // MODIFIES: dishes
    // EFFECTS: parses dishes from dishesArray and adds it to dishes
    private void addDishes(JSONArray dishesArray, ArrayList<String> dishes) {
        for (Object json : dishesArray) {
            JSONObject dishObject = (JSONObject) json;
            String dish = ((JSONObject) json).getString("dish");
            dishes.add(dish);
        }
    }

    // MODIFIES: locations
    // EFFECTS: parses locations from JSONArray and adds it to locations
    private void addLocations(JSONArray locationsArray, ArrayList<Location> locations) {
        for (Object json : locationsArray) {
            JSONObject location = (JSONObject) json;
            addLocation(location, locations);
        }
    }

    // MODIFIES: rl
    // EFFECTS: parses location from JSONObject and adds it to locations
    private void addLocation(JSONObject location, ArrayList<Location> locations) {
        String address = location.getString("address");
        String area = location.getString("area");
        Location l = new Location(address,area);
        locations.add(l);
    }
}
