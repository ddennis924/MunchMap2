package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
