package persistence;

import model.Location;
import model.Restaurant;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRestaurant(String name, ArrayList<Location> locations,
                                   String ethnicity, int visits, Restaurant r) {
        assertEquals(name, r.getName());
        assertEquals(locations, r.getLocations());
        assertEquals(ethnicity, r.getCuisine().getEthnicity());
        assertEquals(visits, r.getVisited());
    }
}
