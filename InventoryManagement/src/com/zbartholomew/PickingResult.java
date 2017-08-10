package com.zbartholomew;

/**
 * <h1> Represents the results from attempting to pick inventory from a {@link Product} </h1>
 * <p> We should have all necessary information about whether a picking request
 * was a success or failure.  This class will aid in troubleshooting errors. </p>
 *
 * @author Zach Bartholomew
 */
public class PickingResult {

    private Location location;
    private int inventory;
    private String productId;
    private int amountToPick;
    private boolean success;
    private String message;

    /**
     * Instantiate a {@link PickingResult} object
     *
     * @param location     - {@link Location} of product
     * @param inventory    - Inventory value after restocking
     * @param productId    - Product ID
     * @param amountToPick - Amount being restocked
     * @param success      - Boolean indicating success / failure - true / false
     * @param message      - {@link Message} general message detail type of success or failure
     */

    public PickingResult(Location location, int inventory, String productId, int amountToPick,
                         boolean success, String message) {
        this.location = location;
        this.inventory = inventory;
        this.productId = productId;
        this.amountToPick = amountToPick;
        this.success = success;
        this.message = message;
    }

    /**
     * Instantiate a {@link PickingResult} object
     *
     * @param amountToPick - Amount being restocked
     * @param success      - Boolean indicating success / failure - true / false
     * @param message      - {@link Message} general message detail type of success or failure
     */
    public PickingResult(int amountToPick, boolean success, String message) {
        this.amountToPick = amountToPick;
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
                "\nAmount Picked: " + amountToPick +
                "\nNew Inventory: " + inventory
                :
                "Failure Picking!" +
                        "\nProduct ID: " + productId +
                        "\nLocation: " + location.getName() +
                        "\nAmount Requested to Pick: " + amountToPick +
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