package com.zbartholomew;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <h1> Represents a {@link Product}'s inventory manager </h1>
 * Where we can pick inventory and restock inventory.
 * <p/>
 * Thread-safe
 *
 * @author Zach Bartholomew
 */
public class ProductInventoryManager implements InventoryManagementSystem {

    /**
     * Maps the Location's unique name to a {@link Location}
     * <p/>
     * Thread-safe
     */
    private static ConcurrentMap<String, Location> locationMap = new ConcurrentHashMap<>();

    /**
     * Maps a {@link Product} to the String ID of that product.
     * <p/>
     * Thread-safe
     */
    private static ConcurrentMap<String, Product> productMap = new ConcurrentHashMap<>();


    @Override
    public PickingResult pickProduct(String productId, int amountToPick) {

        // Input parameter error check
        if (amountToPick <= Product.minInventory) {
            return new PickingResult(amountToPick, false, Message.PICKER_MIN_INVENTORY.getDescription());
        } else if (amountToPick > Product.maxInventory) {
            return new PickingResult(amountToPick, false, Message.PICKER_MAX_INVENTORY.getDescription());
        }

        // Get the Product from productMap
        Product product = productMap.get(productId);
        if (product == null) {
            throw new IllegalArgumentException(Message.PRODUCT_DOES_NOT_EXIST.getDescription());
        }


        // locking down the product we may alter
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (product) {
            // Verification occurs in setInventory
            if (product.setInventory(product.getInventory() - amountToPick)) {
                return new PickingResult(product.getLocation(), product.getInventory(),
                        productId, amountToPick, true, Message.PICKER_SUCCESS.getDescription());
            } else {
                return new PickingResult(product.getLocation(), product.getInventory(),
                        productId, amountToPick, false, Message.PICKER_FAILURE.getDescription());
            }
        }
    }

    @Override
    public RestockingResult restockProduct(String productId, int amountToRestock) {

        // Input parameter error check
        if (amountToRestock <= Product.minInventory) {
            return new RestockingResult(amountToRestock, false, Message.RESTOCKER_MIN_INVENTORY.getDescription());
        } else if (amountToRestock > Product.maxInventory) {
            return new RestockingResult(amountToRestock, false, Message.RESTOCKER_MAX_INVENTORY.getDescription());
        }

        // Get the Product from productMap
        Product product = productMap.get(productId);
        if (product == null) {
            throw new IllegalArgumentException(Message.PRODUCT_DOES_NOT_EXIST.getDescription());
        }


        // locking down the product we may alter
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (product) {
            // Verification occurs in setInventory
            if (product.setInventory(product.getInventory() + amountToRestock)) {
                return new RestockingResult(product.getLocation(), product.getInventory(),
                        productId, amountToRestock, true, Message.RESTOCKER_SUCCESS.getDescription());
            } else {
                return new RestockingResult(product.getLocation(), product.getInventory(),
                        productId, amountToRestock, false, Message.RESTOCKER_FAILURE.getDescription());
            }
        }
    }

    public static Location getLocation(String name) {
        return locationMap.get(name);
    }

    public static Product getProduct(String productId) {
        return productMap.get(productId);
    }

    public static ConcurrentMap<String, Location> getLocationMap() {
        return locationMap;
    }

    public static ConcurrentMap<String, Product> getProductMap() {
        return productMap;
    }
}

