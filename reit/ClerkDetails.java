package reit;

/**
 * ClerkDetails:
 * This is a Callable wrapper for Customer class. It's purpose is to simulate the duration of stay and damage
 * inflicted by a single Customer.
 * @version 1.0
 *
 */
class ClerkDetails {

    private final String name;
    private final Location location;

    ClerkDetails(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    long distanceToTravel(Asset asset) {
        return asset.distanceToClerk(location);
    }

    String getName() {
        return "[" + name + "]";
    }

    public String toString() {
        return "[name=" + name + "][Location=" + location + "]";
    }
}
