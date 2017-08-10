package com.zbartholomew;

/**
 * Implementations of this interface including access to shared data must be
 * thread-safe.
 */
public interface InventoryManagementSystem {

    /**
     * Deduct 'amountToPick' of the given 'productId' from inventory.
     *
     * @param productId    The ID of the product to pick
     * @param amountToPick The quantity of the product to pick
     * @return The {@link PickingResult} object that contains:
     * Information about the Product that was picked, as well as,
     * a message stating whether the Product was successfully picked
     * or not
     */
    PickingResult pickProduct(String productId, int amountToPick);

    /**
     * Add 'amountToRestock' of the given productId to inventory.
     *
     * @param productId       The ID of the product to restock
     * @param amountToRestock The quantity of the product to restock
     * @return The {@link RestockingResult} object that contains:
     * Information about the Product that was restocked, as well as,
     * a message stating whether the Product was successfully restocked
     * or not
     */
    RestockingResult restockProduct(String productId, int amountToRestock);
}
