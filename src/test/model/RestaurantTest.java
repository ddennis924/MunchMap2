package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {
    Restaurant r1;

    @BeforeEach
    public void setUp() {
        ArrayList<Location> r1locations = new ArrayList<>();
        ArrayList<String> dishes = new ArrayList<>();
        dishes.add("Bulgogi");
        dishes.add("Kimchi");
        Cuisine r1cuisine = new Cuisine("Korean", dishes);
        r1locations.add(new Location("124 road", "UBC"));
        r1 = new Restaurant("diner1", r1locations, r1cuisine, 0);

        assertEquals(-1, r1.getPrice());
        assertEquals(-1, r1.getRating());
        assertEquals(r1cuisine, r1.getCuisine());
        assertEquals("no review found", r1.getReview());
        assertEquals(0, r1.getVisited());
        assertEquals("diner1", r1.getName());
        assertEquals(r1locations, r1.getLocations());

        assertFalse(r1.isVisited());
    }

    @Test
    public void settersTest() {
        r1.setName("diner4");
        assertEquals("diner4", r1.getName());

        r1.setVisited(10);

        assertTrue(r1.isVisited());
        assertEquals(10, r1.getVisited());

        r1.setReview("very good");

        assertEquals("very good", r1.getReview());

        r1.setRating(3.0);
        assertEquals(3.0, r1.getRating());

        r1.setPrice(30.3);
        assertEquals(30.3, r1.getPrice());

        Cuisine german = new Cuisine("German", new ArrayList<>());

        r1.setCuisine(german);
        assertEquals("German", r1.getCuisine().getEthnicity());

        r1.setCuisine(new Cuisine("Korean", new ArrayList<>()));

    }
    @Test
    public void addLocationTest() {
        assertFalse(r1.locationIn("Richmond"));

        Location richmond = new Location("222 street", "Richmond");
        r1.addLocation(richmond);
        r1.addLocation(richmond);

        assertTrue(r1.locationIn("Richmond"));
        assertEquals(2, r1.getLocations().size());
        assertEquals("222 street", r1.getLocations().get(1).getAddress());

        r1.addLocation(new Location("333 road", "UBC"));

        assertEquals(3, r1.getLocations().size());
    }

    @Test
    public void removeLocationTest() {
        addLocationTest();
        assertFalse(r1.removeLocation("a"));
        assertTrue(r1.removeLocation("124 road"));

        assertEquals(2, r1.getLocations().size());

        r1.addLocation(new Location("222 street", "Vancouver"));

        assertEquals(3, r1.getLocations().size());
    }

    @Test
    public void visitTest() {
        r1.visit(10.0,12.0);

        assertEquals(10, r1.getRating());
        assertEquals(12,r1.getPrice());
        assertEquals(1, r1.getVisited());

        assertTrue(r1.isVisited());

        r1.visit(8.0, 10.0);

        assertEquals(9, r1.getRating());
        assertEquals(11, r1.getPrice());
        assertEquals(2, r1.getVisited());

        r1.visit(5.0, 12.5);

        assertEquals(((5.0+9.0*2.0)/3), r1.getRating());
        assertEquals(((12.5+11*2)/3),r1.getPrice());
        assertEquals(3, r1.getVisited());
    }



}
