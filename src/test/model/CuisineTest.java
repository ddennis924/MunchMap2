package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CuisineTest {

    @Test
    public void setGetCuisineTest() {
        ArrayList<String> dishes = new ArrayList<>();
        Cuisine c = new Cuisine("German", dishes);

        assertFalse(c.containsDish("Sausage"));
        c.addDish("Sausage");
        c.addDish("Pizza");

        assertEquals("German",  c.getEthnicity());
        assertEquals(2, c.getDishes().size());
        assertEquals("Pizza", c.getDishes().get(1));
        assertTrue(c.containsDish("Sausage"));
        assertFalse(c.containsDish("Apple"));

        c.setEthnicity("Korean");

        assertEquals("Korean", c.getEthnicity());

        assertTrue(c.removeDish("Sausage"));

        assertEquals(1, c.getDishes().size());

        assertFalse(c.containsDish("Sausage"));
        assertFalse(c.removeDish("Sausage"));

        assertEquals(1,c.getDishes().size());

        c.addDish("Bulgogi");

        assertTrue(c.containsDish("Bulgogi"));

        assertTrue(c.removeDish("Pizza"));

        assertEquals(1, c.getDishes().size());

    }
}
