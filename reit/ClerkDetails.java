package reit;

/**
 * Created by airbag on 12/9/14.
 */
class ClerkDetails {

    private String name;
    private Location location;

    public ClerkDetails(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public long distanceToTravel(Asset asset) {
        return asset.distanceToClerk(location);
    }

    public String getName() {
        return "[" + name + "]";
    }

    public String toString() {
        return "[name=" + name + "][reit.Location=" + location + "]";
    }
}
