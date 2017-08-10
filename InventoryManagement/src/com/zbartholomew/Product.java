package com.zbartholomew;

/**
 * <h1> Represents a Product </h1>
 * Each product will be stored at one and only one named {@link Location} within the warehouse.
 * <p/>
 * Thread-safe
 *
 * @author Zach Bartholomew
 */
public final class Product {

    /**
     * Unique String ID for Product -- in production code we may want to generate a unique ID
     * but for testing we will manually enter our own
     */
    private final String id;
    private int inventory;

    private final Location location;

    /**
     * Max inventory allowed - this should probably be changed to realistic number
     */
    public static final int maxInventory = Integer.MAX_VALUE - 1;

    /**
     * Not allowing negative inventory
     */
    public static final int minInventory = 0;


    /**
     * Instantiates a {@link Product} with given unique ID, Inventory level, and {@link Location}.
     *
     * @param id        - unique id given to product
     * @param inventory - inventory of item
     * @param location  - unique Location of product
     * @throws IllegalArgumentException - inventory value cannot be negative
     * @throws IllegalArgumentException - inventory value cannot be greater than max inventory
     * @throws IllegalArgumentException - product ID already exists
     */
    public Product(final String id, int inventory, final Location location) throws IllegalArgumentException {

        // error checking inventory value
        if (inventory < minInventory) {
            throw new IllegalArgumentException("Inventory value cannot be less than " + minInventory + ".");
        } else if (inventory > maxInventory) {
            throw new IllegalArgumentException("Inventory value cannot be greater than " + maxInventory + ".");
        }

        // populate map, as long as, product id does not exist
        if (ProductInventoryManager.getProductMap().putIfAbsent(id, this) == null) {
            this.id = id;
            this.inventory = inventory;
            this.location = location;
        } else {
            throw new IllegalArgumentException("Product ID already exists.");
        }
    }

    /**
     * Get {@link Product} ID
     *
     * @return ID of product
     */
    public String getId() {
        return id;
    }

    /**
     * Get current inventory of {@link Product}
     *
     * @return current inventory of Product
     */
    public int getInventory() {
        return inventory;
    }

    /**
     *
     * @param inventory - new inventory value that will be set
     * @return true if the new inventory has been set with the new value.
     * false if the new inventory was not able to be set
     */
    public boolean setInventory(int inventory) {

        // error checking inventory value
        if ((inventory < minInventory) || (inventory > maxInventory)) {
            return false;
        } else {
            this.inventory = inventory;
            return true;
        }
    }

    /**
     * Get {@link Location} of {@link Product}
     *
     * @return location of product
     */
    public Location getLocation() {
        return location;
    }
}