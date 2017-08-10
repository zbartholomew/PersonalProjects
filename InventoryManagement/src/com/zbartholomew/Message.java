package com.zbartholomew;

/**
 * Message details for successful or errors in picking/restocking
 */
enum Message {
    PICKER_MIN_INVENTORY(0, "Amount to pick must be greater than " + Product.minInventory + "."),
    PICKER_MAX_INVENTORY(1, "Amount to pick cannot be greater than " + Product.maxInventory + "."),
    PICKER_SUCCESS(2, "Success Picking!"),
    PICKER_FAILURE(3, "Not enough inventory to process request."),
    RESTOCKER_MIN_INVENTORY(10, "Amount to restock must be greater than " + Product.minInventory + "."),
    RESTOCKER_MAX_INVENTORY(11, "Amount to restock cannot be greater than " + Product.maxInventory + "."),
    RESTOCKER_SUCCESS(2, "Success Restocking!"),
    RESTOCKER_FAILURE(3, "Too much inventory to process request.  Inventory cannot exceed: " +
            Product.maxInventory + "."),
    PRODUCT_DOES_NOT_EXIST(100, "Product does not exist");


    private final int code;
    private final String description;

    Message(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

