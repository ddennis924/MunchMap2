package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationTest {

    @Test
    public void setGetLocationTest() {
        Location l = new Location("124 town", "RBC");

        assertEquals("124 town", l.getAddress());
        assertEquals("RBC", l.getArea());

        l.setAddress("2222 no 2 road");

        assertEquals("2222 no 2 road", l.getAddress());


        l.setArea("Richmond");

        assertEquals("Richmond", l.getArea());
    }
}
