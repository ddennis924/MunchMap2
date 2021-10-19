package persistence;

import model.Cuisine;
import model.Location;
import model.Restaurant;
import model.RestaurantList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JsonWriterTest {
    RestaurantList rl = new RestaurantList("Dennis");
    JsonWriter writer;

    @Test
    public void testWriterNoFile() {
        try {
            writer = new JsonWriter("./data/\0ittel1213*&.json");
            writer.open();
            fail("IOException Excepted");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmpty() {
        JsonReader reader = new JsonReader("./data/testJsonWriterEmpty.json");
        try {
            RestaurantList restaurantList = reader.read();
            assertEquals("Dennis", restaurantList.getName());
            assertEquals(0, restaurantList.sizeOf());

            RestaurantList newList = new RestaurantList("Donald");
            writer = new JsonWriter("./data/testJsonWriterEmpty.json");
            writer.open();
            writer.write(newList);
            writer.close();

            RestaurantList newRead = reader.read();
            assertEquals("Donald", newRead.getName());
            assertEquals(0,newRead.sizeOf());

            RestaurantList newNewList = new RestaurantList("Dennis");
            writer.open();
            writer.write(newNewList);
            writer.close();

            RestaurantList newNewRead = reader.read();

            assertEquals("Dennis", newNewRead.getName());
            assertEquals(0, newNewRead.sizeOf());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testWriterMany() {
        ArrayList<Location> mcLocations = new ArrayList<>();
        mcLocations.add(new Location("1100 main mall", "UBC"));
        mcLocations.add(new Location("2314 no 2 road", "Richmond"));

        Cuisine mcCuisine = new Cuisine("FastFood", new ArrayList<>());
        mcCuisine.addDish("Burgers");
        mcCuisine.addDish("Fries");

        Restaurant r1 = new Restaurant("McDonald's", mcLocations, mcCuisine, 0);

        ArrayList<Location> samLocations = new ArrayList<>();
        samLocations.add(new Location("1111 no 2 road", "Richmond"));

        Restaurant r2 = new Restaurant("Samsoonie", samLocations, new Cuisine("Korean", new ArrayList<>()),
                5);

        r2.setVisited(6);
        r2.setRating(7);
        r2.setPrice(10);
        r2.setReview("good food");
        r2.getCuisine().addDish("Kimchi");

        ArrayList<Location> sakuLocations = new ArrayList<>();
        sakuLocations.add(new Location("123 west ave", "Vancouver"));

        Cuisine sakuCuisine = new Cuisine("Japanese", new ArrayList<>());
        sakuCuisine.addDish("Bulgogi");
        sakuCuisine.addDish("Pancakes");

        Restaurant r3 = new Restaurant("Saku", sakuLocations, sakuCuisine, 0);

        ArrayList<Location> suraLocations = new ArrayList<>();
        suraLocations.add(new Location("6 road", "Vancouver"));
        suraLocations.add(new Location("parliament", "Victoria"));

        Restaurant r4 = new Restaurant("Sura", suraLocations,
                new Cuisine("Korean", new ArrayList<>()), 0);
        rl.addRestaurant(r1);
        rl.addRestaurant(r2);
        rl.addRestaurant(r3);
        rl.addRestaurant(r4);
        try {
            JsonWriter writer = new JsonWriter("./data/testJsonWriterMany.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterMany.json");
            RestaurantList restaurantList = reader.read();
            assertEquals("Dennis", restaurantList.getName());
            assertEquals(4, restaurantList.sizeOf());
            assertTrue(restaurantList.containsRestaurant("Sura"));
            assertTrue(restaurantList.containsRestaurant("Samsoonie"));
            assertTrue(restaurantList.containsRestaurant("McDonald's"));
            assertFalse(restaurantList.getRestaurant("McDonald's").isVisited());
            assertTrue(restaurantList.containsRestaurant("Saku"));
        } catch (IOException e) {
            fail();
        }
    }
}
