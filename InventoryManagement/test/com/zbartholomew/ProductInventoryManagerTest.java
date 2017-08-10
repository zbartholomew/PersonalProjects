package com.zbartholomew;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <h1> {@link ProductInventoryManager} testing class. </h1>
 * <p> Verifies the picking and restocking of a Products inventory
 * and checks the {@link RestockingResult} / {@link PickingResult}
 * return values are correct</p>
 *
 * @author Zach Bartholomew
 */
public class ProductInventoryManagerTest {

    @BeforeClass
    public static void createDummyData() {
        Location location1 = new Location("First1 location1");
        new Product("123", 12, location1);

        Location location2 = new Location("Second2 location2");
        new Product("456", 60, location2);

        Location location3 = new Location("Third3 location3");
        new Product("789", 100, location3);

        Location location4 = new Location("Fourth4 location4");
        new Product("147", 0, location4);

        new Product("452", 5, location4);
    }

    @Test
    public void testSuccessfulPickProduct() {
        ProductInventoryManager manager = new ProductInventoryManager();

        PickingResult pickingResult = manager.pickProduct("123", 4);
        Product product = ProductInventoryManager.getProductMap().get("123");
        String message = "Success Picking!" +
                "\nProduct ID: " + product.getId() +
                "\nLocation: " + product.getLocation().getName() +
                "\nAmount Picked: " + 4 +
                "\nNew Inventory: " + product.getInventory();

        assertEquals(message, pickingResult.getDetailMessage());
    }

    @Test
    public void testUnsuccessfulPickProductShouldGetErrorMessage() {
        ProductInventoryManager manager = new ProductInventoryManager();

        PickingResult pickingResult = manager.pickProduct("123", 13);
        Product product = ProductInventoryManager.getProductMap().get("123");
        String message = "Failure Picking!" +
                "\nProduct ID: " + product.getId() +
                "\nLocation: " + product.getLocation().getName() +
                "\nAmount Requested to Pick: " + 13 +
                "\nInventory: " + product.getInventory() +
                "\nFailure Message: " + "Not enough inventory to process request.";

        assertEquals(message, pickingResult.getDetailMessage());
    }

    @Test
    public void testSuccessfulRestockProduct() {
        ProductInventoryManager manager = new ProductInventoryManager();

        RestockingResult restockingResult = manager.restockProduct("452", 100);
        Product product = ProductInventoryManager.getProductMap().get("452");
        String message = "Success Restocking!" +
                "\nProduct ID: " + product.getId() +
                "\nLocation: " + product.getLocation().getName() +
                "\nAmount Restocked: " + 100 +
                "\nNew Inventory: " + product.getInventory();

        assertEquals(message, restockingResult.getDetailMessage());
    }

    @Test
    public void testUnsuccessfulRestockProductShouldGetErrorMessage() {
        ProductInventoryManager manager = new ProductInventoryManager();

        RestockingResult restockingResult = manager.restockProduct("789", Integer.MAX_VALUE - 2);
        Product product = ProductInventoryManager.getProductMap().get("789");
        String message = "Failure Restocking!" +
                "\nProduct ID: " + product.getId() +
                "\nLocation: " + product.getLocation().getName() +
                "\nAmount Requested to Restock: " + (Integer.MAX_VALUE - 2) +
                "\nInventory: " + product.getInventory() +
                "\nFailure Message: " + "Too much inventory to process request.  Inventory cannot exceed: " +
                Product.maxInventory + ".";

        assertEquals(message, restockingResult.getDetailMessage());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testProductIDNotFoundPicking() {
        ProductInventoryManager inventoryManager = new ProductInventoryManager();
        inventoryManager.pickProduct("54997998", 3);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testProductIDNotFoundRestocking() {
        ProductInventoryManager inventoryManager = new ProductInventoryManager();
        inventoryManager.restockProduct("54997998", 3);
    }
}