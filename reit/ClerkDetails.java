package reit;

/**
 * Created by airbag on 12/9/14.
 */
class ClerkDetails {

    private String name;
    private Location location;

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
