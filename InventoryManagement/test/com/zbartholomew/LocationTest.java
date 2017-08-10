package com.zbartholomew;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <h1> {@link Location} testing class. </h1>
 * <p> Verifies a duplicate location cannot be added</p>
 *
 * @author Zach Bartholomew
 */
public class LocationTest {

    @BeforeClass
    public static void createDummyData() {
        new Location("First1 location");

        new Location("Second2 location");

        new Location("Third3 location");

        new Location("Fourth4 location");
    }

    @Test(expected = IllegalArgumentException.class)
    public void addExistingExistingShouldThrowException() {
        Location location = new Location("First1 location");
    }
}