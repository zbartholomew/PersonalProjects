package com.zbartholomew;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <h1> {@link Product} testing class. </h1>
 * <p> Verifies all errors are thrown appropriately
 * and the productMap populates correctly</p>
 *
 * @author Zach Bartholomew
 */
public class ProductTest {

    @BeforeClass
    public static void createDummyData() {
        Location location1 = new Location("First location");
        new Product("1236", 12, location1);

        Location location2 = new Location("Second location");
        new Product("4561", 60, location2);

        Location location3 = new Location("Third location");
        new Product("7891", 100, location3);

        Location location4 = new Location("Fourth location");
        new Product("1471", 0, location4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addExistingProductShouldThrowException() {
        Location location = new Location("First location");
        new Product("1236", 12, location);
    }

    @Test (expected = IllegalArgumentException.class)
    public void addProductWithNegativeInventoryShouldThrowException() {
        Location location = new Location("Some location");
        new Product("987654321", -1, location);
    }

    @Test (expected = IllegalArgumentException.class)
    public void addProductWithAboveMaxInventoryShouldThrowException() {
        Location location = new Location("location");
        new Product("654987", Product.maxInventory+1, location);
    }

    @Test
    public void addProductShouldPopulateProductMap() {
        Location location = new Location("New location");
        Product newProduct = new Product("123654987", 12, location);

        assertEquals(newProduct, ProductInventoryManager.getProductMap().get("123654987"));
        assertEquals(12, newProduct.getInventory());
        assertEquals(location, newProduct.getLocation());
        assertEquals("123654987", newProduct.getId());
        assertEquals("New location", location.getName());
    }
}