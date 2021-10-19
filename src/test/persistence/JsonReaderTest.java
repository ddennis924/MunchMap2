package persistence;

import model.*;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class JsonReaderTest {
    JsonReader reader;

    @Test
    public void testReaderNonExistentFile() {
        reader = new JsonReader("./data/DoesNotExist.json");
        try {
            RestaurantList restaurantList = reader.read();
            fail("JSONException expected");
        } catch (IOException e) {
            // pass;
        }
    }

    @Test
    public void testReaderEmptyName() {
        reader = new JsonReader("./data/testJsonReaderBlank.json");
        try {
            RestaurantList restaurantList = reader.read();
            assertEquals("123Wig", restaurantList.getName());
            assertEquals(0, restaurantList.sizeOf());
        } catch (IOException e) {
            fail();
        }
    }
    @Test
    public void testReaderEmpty() {
        reader = new JsonReader("./data/testJsonReaderEmpty.json");
        try {
            RestaurantList restaurantList = reader.read();
            assertEquals("Dennis", restaurantList.getName());
            assertEquals(0, restaurantList.sizeOf());
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    @Test
    public void testReaderMany() {
        reader = new JsonReader("./data/testJsonReaderMany.json");
        try {
            RestaurantList restaurantList = reader.read();
            assertEquals("Dennis", restaurantList.getName());
            assertEquals(2, restaurantList.sizeOf());
            assertTrue(restaurantList.containsRestaurant("Samsoonie"));
            assertTrue(restaurantList.containsRestaurant("McDonald's"));
            assertTrue(restaurantList.getRestaurant("Samsoonie").isVisited());
            assertFalse(restaurantList.getRestaurant("McDonald's").isVisited());
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }
}
