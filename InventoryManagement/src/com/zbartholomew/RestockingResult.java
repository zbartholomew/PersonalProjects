package com.zbartholomew;

/**
 * <h1> Represents the results from attempting to restock inventory from a {@link Product} </h1>
 * <p> We should have all necessary information about whether a restocking request
 * was s success or failure.  This class will aid in troubleshooting errors. </p>
 *
 * @author Zach Bartholomew
 */
public class RestockingResult {

    private Location location;
    private int inventory;
    private String productId;
    private int amountToRestock;
    private boolean success;
    private String message;

    /**
     * Instantiate a {@link RestockingResult} object
     *
     * @param location        - {@link Location} of product
     * @param inventory       - Inventory value after restocking
     * @param productId       - Product ID
     * @param amountToRestock - Amount being restocked
     * @param success         - Boolean indicating success / failure - true / false
     * @param message         - {@link Message} general message detail type of success or failure
     */
    public RestockingResult(Location location, int inventory, String productId, int amountToRestock,
                            boolean success, String message) {
        this.location = location;
        this.inventory = inventory;
        this.productId = productId;
        this.amountToRestock = amountToRestock;
        this.success = success;
        this.message = message;
    }

    /**
     * Instantiate a {@link RestockingResult} object
     *
     * @param amountToRestock - Amount being restocked
     * @param success         - Boolean indicating success / failure - true / false
     * @param message         - {@link Message} general message detail type of success or failure
     */
    public RestockingResult(int amountToRestock, boolean success, String message) {
        this.amountToRestock = amountToRestock;
        this.success = success;
        this.message = message;
    }

    /**
     * @return a success or failure message with detail.
     */
    public String getDetailMessage() {
        if (productId == null) {
            return getMessage();
        }
        return success ? message +
                "\nProduct ID: " + productId +
                "\nLocation: " + location.getName() +
                "\nAmount Restocked: " + amountToRestock +
                "\nNew Inventory: " + inventory
                :
                "Failure Restocking!" +
                        "\nProduct ID: " + productId +
                        "\nLocation: " + location.getName() +
                        "\nAmount Requested to Restock: " + amountToRestock +
                        "\nInventory: " + inventory +
                        "\nFailure Message: " + message;
    }

    /**
     * @return simple message
     */
    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
