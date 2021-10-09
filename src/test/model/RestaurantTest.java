package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant r1;
    Restaurant r2;
    Restaurant r3;
    Restaurant r4;
    RestaurantList rl1;
    ArrayList<Location> mcLocations;

    @BeforeEach
    public void Setup() {
        mcLocations = new ArrayList<>();
        mcLocations.add(new Location("1100 main mall", "UBC"));
        mcLocations.add(new Location("2314 no 2 road", "Richmond"));

        assertEquals(2, mcLocations.size());
        assertEquals("1100 main mall", mcLocations.get(0).getAddress());

        Cuisine mcCuisine = new Cuisine("FastFood", new ArrayList<>());
        mcCuisine.addDish("Burgers");
        mcCuisine.addDish("Fries");

        assertEquals(2, mcCuisine.getDishes().size());
        assertTrue(mcCuisine.containsDish("Burgers"));

        r1 = new Restaurant("McDonald's", mcLocations, mcCuisine, 0);

        ArrayList<Location> samLocations = new ArrayList<>();
        samLocations.add(new Location("1111 no 2 road", "RBC"));

        assertEquals(1, samLocations.size());

        r2 = new Restaurant("Samsoonie", samLocations, new Cuisine("Korean", new ArrayList<>()),
                6);

        r2.setRating(7);
        r2.setPrice(10);
        r2.setReview("good food");

        assertEquals("Samsoonie", r2.getName());
        assertEquals("Korean", r2.getCuisine().toString());


        ArrayList<Location> sakuLocations = new ArrayList<>();
        sakuLocations.add(new Location("123 west ave", "Vancouver"));

        Cuisine sakuCuisine = new Cuisine("Japanese", new ArrayList<>());
        sakuCuisine.addDish("Bulgogi");
        sakuCuisine.addDish("Pancakes");

        assertEquals(2, sakuCuisine.getDishes().size());

        sakuCuisine.removeDish("Pancakes");

        assertFalse(sakuCuisine.containsDish("Pancakes"));
        assertEquals(1, sakuCuisine.getDishes().size());


        r3 = new Restaurant("Saku", sakuLocations, sakuCuisine, 0);

        ArrayList<Location> suraLocations = new ArrayList<>();
        suraLocations.add(new Location("6 road", "Vancouver"));
        suraLocations.add(new Location("parliament", "Victoria"));

        r4 = new Restaurant("Sura", suraLocations, new Cuisine("Korean", new ArrayList<>()), 0);

        rl1 = new RestaurantList();

        rl1.addRestaurant(r1);
        rl1.addRestaurant(r2);
        rl1.addRestaurant(r3);
        rl1.addRestaurant(r4);

        assertEquals(4, rl1.sizeOf());
        assertEquals(0, rl1.indexOf(r1));
        assertEquals(3, rl1.indexOf(r4));
        assertTrue(rl1.containsRestaurant("Saku"));
        assertTrue(rl1.containsRestaurant("Sura"));
        assertEquals(r1, rl1.getRestaurant("McDonald's"));
    }

    @Test
    public void addRemoveLocationTest() {
        r3.addLocation(new Location("456 east ave", "Vancouver"));

        assertEquals(2, r3.getLocations().size());
        assertEquals("456 east ave", r3.getLocations().get(1).getAddress());

        r3.removeLocation("123 west ave");

        assertEquals(1, r3.getLocations().size());
        assertEquals("456 east ave", r3.getLocations().get(0).getAddress());
        assertFalse(r3.locationIn("Richmond"));

        r3.addLocation(new Location("912 Alexandra road", "Richmond"));

        assertEquals(2, r3.getLocations().size());
        assertTrue(r3.locationIn("Richmond"));

    }

    @Test
    public void setNameTest() {
        r2.setName("Samsoona");
        assertEquals("Samsoona", r2.getName());

        r3.setName("poo");
        assertEquals("poo", r3.getName());
    }

    @Test
    public void visitTest() {
        r2.visit(10,12);
        double rate = (10.0+42.0)/7.0;

        assertEquals(rate, r2.getRating());
        assertEquals(rate,r2.getPrice());
        assertEquals(7, r2.getVisited());

        assertFalse(r3.isVisited());
        r3.visit(12,15);

        assertEquals("Saku",r3.getName());
        assertEquals(12, r3.getRating());
        assertEquals(15, r3.getPrice());
        assertEquals(1, r3.getVisited());

        assertTrue(r3.isVisited());

        rl1.getRestaurant("Saku").visit(7, 30);

        assertEquals(9.75, r3.getRating());
        assertEquals(22.5, r3.getPrice());
        assertEquals(2, r3.getVisited());

    }

    @Test
    public void removeRestaurantTest() {
        assertFalse(rl1.removeRestaurant("A&W"));
        assertEquals(4,rl1.sizeOf());
        assertTrue(rl1.removeRestaurant("Saku"));

        assertEquals(3, rl1.sizeOf());
        assertFalse(rl1.containsRestaurant("Saku"));
        assertTrue(rl1.removeRestaurant("Sura"));
        assertTrue(rl1.removeRestaurant("McDonald's"));
        assertEquals(1, rl1.sizeOf());

        rl1.addRestaurant(r4);
        assertEquals(2, rl1.sizeOf());
        assertFalse(rl1.removeRestaurant("Saku"));
    }

    @Test
    public void sortByLuxuryTest() {
        visitTest();

        RestaurantList rl2 = rl1.sortByLuxury();
        assertEquals(2, rl2.sizeOf());
        assertEquals(r3, rl2.nextRestaurant());
        assertEquals(1, rl2.indexOf(r2));

        for (int i = 1; i <= 10; i++) {
            Restaurant r = new Restaurant("r", mcLocations,
                    new Cuisine("Indian", new ArrayList<>()), 1);
            r.setPrice(i);
            r.setRating(i);
            rl1.addRestaurant(r);
        }
        assertEquals(14, rl1.sizeOf());

        RestaurantList rl3 = rl1.sortByLuxury();
        assertEquals(12, rl2.sizeOf());
        assertFalse(rl2.containsRestaurant("McDonald's"));
        assertEquals(r3, rl3.nextRestaurant());
        assertEquals(10, rl3.get(2).getPrice());
    }

    @Test
    public void sortByCheapest() {
        sortByLuxuryTest();
        RestaurantList rl4 = rl1.sortByCheapest();

        assertEquals(12, rl4.sizeOf());
        assertFalse(rl4.containsRestaurant("McDonald's"));
        assertEquals(1, rl4.nextRestaurant().getPrice());
        assertEquals(11, rl4.indexOf(r3));
    }

    @Test
    public void sortByRatingTest() {
        sortByLuxuryTest();

        RestaurantList rl4 = rl1.sortByRating();

        assertEquals(12, rl4.sizeOf());
        assertFalse(rl4.containsRestaurant("McDonald's"));
        assertEquals(10, rl4.nextRestaurant().getRating());
        assertEquals(r3, rl4.get(1));
    }


    @Test
    public void sortByCuisine() {
        RestaurantList rl2 = rl1.sortByCuisine("Korean");

        assertEquals(r2, rl2.nextRestaurant());
        assertEquals(r4, rl2.get(1));
        assertEquals(r3, rl2.get(3));

        RestaurantList rl3 = rl1.sortByCuisine("Japanese");

        assertEquals(r3, rl3.nextRestaurant());
        assertEquals(r2, rl3.get(1));

        RestaurantList rl4 = rl1.sortByCuisine("Gq");

        assertEquals(r1, rl4.nextRestaurant());
        assertEquals(r4, rl4.get(3));
    }

    @Test
    public void randomRestaurantRatingTest() {

    }


}