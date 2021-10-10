package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Random.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantListTest {
    Restaurant r1;
    Restaurant r2;
    Restaurant r3;
    Restaurant r4;
    RestaurantList rl1;
    ArrayList<Location> mcLocations;
    int count1;
    int count2;
    int count3;
    int count4;
    int count5;
    ArrayList<Integer> counts;

    @BeforeEach
    public void Setup() {
        count1 = 0;
        count2 = 0;
        count3 = 0;
        count4 = 0;
        count5 = 0;
        counts = new ArrayList<>();
        counts.add(count1);
        counts.add(count2);
        counts.add(count3);
        counts.add(count4);
        counts.add(count5);

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
        samLocations.add(new Location("1111 no 2 road", "Richmond"));

        assertEquals(1, samLocations.size());

        r2 = new Restaurant("Samsoonie", samLocations, new Cuisine("Korean", new ArrayList<>()),
                5);

        r2.setVisited(6);
        r2.setRating(7);
        r2.setPrice(10);
        r2.setReview("good food");
        r2.getCuisine().addDish("Kimchi");

        assertEquals("Samsoonie", r2.getName());
        assertEquals("Korean", r2.getCuisine().getEthnicity());


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
        rl1.addRestaurant(r4);

        assertEquals(4, rl1.getRestaurants().size());
        assertEquals(0, rl1.indexOf(r1));
        assertEquals(3, rl1.indexOf(r4));
        assertTrue(rl1.containsRestaurant("Saku"));
        assertTrue(rl1.containsRestaurant("Sura"));
        assertEquals(r1, rl1.getRestaurant("McDonald's"));
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

        addManyRestaurants();
        assertEquals(14, rl1.sizeOf());

        RestaurantList rl3 = rl1.sortByLuxury();
        assertEquals(12, rl2.sizeOf());
        assertFalse(rl2.containsRestaurant("McDonald's"));
        assertEquals(r3, rl3.nextRestaurant());
        assertEquals(10, rl3.get(2).getPrice());
    }

    private void addManyRestaurants() {
        for (int i = 1; i <= 10; i++) {
            Restaurant r = new Restaurant(("r" + i), mcLocations,
                    new Cuisine("Indian", new ArrayList<>()), 1);
            r.setPrice(i);
            r.setRating(i);
            rl1.addRestaurant(r);
        }
    }

    @Test
    public void sortByCheapest() {
        visitTest();
        addManyRestaurants();
        RestaurantList rl4 = rl1.sortByCheapest();

        assertEquals(12, rl4.sizeOf());
        assertFalse(rl4.containsRestaurant("McDonald's"));
        assertEquals(1, rl4.nextRestaurant().getPrice());
        assertEquals(11, rl4.indexOf(r3));
    }

    @Test
    public void sortByRatingTest() {
        visitTest();
        addManyRestaurants();

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
    public void sortInLocationTest() {
        addManyRestaurants();
        RestaurantList rl2 = rl1.sortInLocation("Korea");
        RestaurantList rl3 = rl1.sortInLocation("UBC");

        assertEquals(0, rl2.sizeOf());
        assertEquals(11, rl3.sizeOf());
        assertEquals("McDonald's", rl3.nextRestaurant().getName());

    }

    @Test
    public void sortByVisitedTest() {
        rl1.removeRestaurant("McDonald's");
        RestaurantList rl2 = rl1.sortByVisited();

        assertEquals(0, rl2.sizeOf());

        addManyRestaurants();

        RestaurantList rl3 = rl1.sortByVisited();

        assertEquals(10, rl3.sizeOf());
        assertEquals(1, rl3.nextRestaurant().getRating());
    }

    @Test
    public void sortByWishlistTest() {
        RestaurantList rl2 = rl1.sortByWishlist();

        assertEquals(3, rl2.sizeOf());
        assertEquals("McDonald's", rl2.nextRestaurant().getName());

        rl1.removeRestaurant("McDonald's");
        rl1.removeRestaurant("Saku");
        rl1.removeRestaurant("Sura");
        assertEquals(1, rl1.sizeOf());

        RestaurantList rl3 = rl1.sortByVisited();

        assertEquals(0, rl3.sizeOf());
    }

    public void checkCount() {
        int pass = 0;
        for (int c : counts) {
            if ((300 > c) && (c > 100)) {
                pass++;
            }
        }
        assertEquals(5,pass);
    }

    @Test
    public void randomRestaurantRatingTest() {
        addManyRestaurants();

        Restaurant randomRestaurant = rl1.randomRestaurantRating(10, "UBC");
        Restaurant randomRestaurant2 = rl1.randomRestaurantRating(2, "Korea");

        assertEquals("r10", randomRestaurant.getName());
        assertEquals(10 ,randomRestaurant.getPrice());
        assertNull(randomRestaurant2);

        for (int i = 1000; i <= 1000; i++) {
            Restaurant randomRestaurant3 = rl1.randomRestaurantRating(6, "UBC");
            if (10 == randomRestaurant3.getRating()) {
                count1++;
            } else if (9 == randomRestaurant3.getRating()) {
                count2++;
            } else if (8 == randomRestaurant3.getRating()) {
                count3++;
            } else if (7 == randomRestaurant3.getRating()) {
                count4++;
            } else {
                count5++;
            }
        }

        checkCount();
    }

    @Test
    public void randomRestaurantPriceTest() {
        addManyRestaurants();

        Restaurant randomRestaurant = rl1.randomRestaurantPrice(1, "UBC");
        Restaurant randomRestaurant2 = rl1.randomRestaurantPrice(2, "Korea");

        assertEquals("r1", randomRestaurant.getName());
        assertEquals(1 ,randomRestaurant.getPrice());
        assertNull(randomRestaurant2);

        for (int i = 1000; i <= 1000; i++) {
            Restaurant randomRestaurant3 = rl1.randomRestaurantRating(5, "UBC");
            if (5 == randomRestaurant3.getPrice()) {
                count1++;
            } else if (4 == randomRestaurant3.getPrice()) {
                count2++;
            } else if (3 == randomRestaurant3.getPrice()) {
                count3++;
            } else if (2 == randomRestaurant3.getPrice()) {
                count4++;
            } else {
                count5++;
            }
        }
        checkCount();
    }

    @Test
    public void randomRestaurantCuisine() {
        addManyRestaurants();

        Restaurant randomRestaurant = rl1.randomRestaurantCuisine("FastFood", "UBC");
        Restaurant randomRestaurant2 = rl1.randomRestaurantCuisine("German", "Korea");

        assertEquals("McDonald's", randomRestaurant.getName());
        assertEquals(-1 ,randomRestaurant.getPrice());
        assertNull(randomRestaurant2);

        rl1.removeRestaurant("McDonald's");

        for (int i = 1000; i <= 1000; i++) {
            Restaurant randomRestaurant3 = rl1.randomRestaurantCuisine("Indian", "Richmond");
            if ("r1".equals(randomRestaurant3.getName()) | "r2".equals(randomRestaurant3.getName())) {
                count1++;
            } else if ("r3".equals(randomRestaurant3.getName()) | "r4".equals(randomRestaurant3.getName())) {
                count2++;
            } else if ("r5".equals(randomRestaurant3.getName()) | "r6".equals(randomRestaurant3.getName())) {
                count3++;
            } else if ("r7".equals(randomRestaurant3.getName()) | "r8".equals(randomRestaurant3.getName())) {
                count4++;
            } else {
                count5++;
            }
        }
        checkCount();
    }

    @Test
    public void randomRestuarantDishTest() {
        addManyRestaurants();

        Restaurant randomRestaurant = rl1.randomRestaurantDish("Kimchi", "Richmond");
        Restaurant randomRestaurant2 = rl1.randomRestaurantDish("HotDog", "Korea");

        assertEquals("Samsoonie", randomRestaurant.getName());
        assertEquals(10 ,randomRestaurant.getPrice());
        assertNull(randomRestaurant2);

        rl1.removeRestaurant("McDonald's");

        for (int i = 1000; i <= 1000; i++) {
            Restaurant randomRestaurant3 = rl1.randomRestaurantCuisine("Burgers", "Richmond");
            if ("r1".equals(randomRestaurant3.getName()) | "r2".equals(randomRestaurant3.getName())) {
                count1++;
            } else if ("r3".equals(randomRestaurant3.getName()) | "r4".equals(randomRestaurant3.getName())) {
                count2++;
            } else if ("r5".equals(randomRestaurant3.getName()) | "r6".equals(randomRestaurant3.getName())) {
                count3++;
            } else if ("r7".equals(randomRestaurant3.getName()) | "r8".equals(randomRestaurant3.getName())) {
                count4++;
            } else {
                count5++;
            }
        }
        checkCount();
    }


}