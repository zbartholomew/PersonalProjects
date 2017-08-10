package com.zbartholomew;

/**
 * <h1> Represents a Location for a {@link Product}. </h1>
 * A Location can have multiple Products,
 * but a Product can only have one Location
 *
 * @author Zach Bartholomew
 */
public final class Location {

    private final String name;

    /**
     * Instantiates a {@link Location} with given unique name.
     *
     * @param name - unique name of the location
     * @throws IllegalArgumentException - location already exists
     */
    public Location(String name) throws IllegalArgumentException {

        // populate map, as long as, location name does not exist
        if (ProductInventoryManager.getLocationMap().putIfAbsent(name, this) == null) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Location already exists");
        }
    }

    public String getName() {
        return name;
    }
}

