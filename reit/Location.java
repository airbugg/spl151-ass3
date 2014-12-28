package reit;

/**
 * Created by airbag on 12/9/14.
 */
public class Location {

    private double x;
    private double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double calculateDistance(Location other) {
        return Math.sqrt(Math.abs(other.x - this.x) + Math.abs(other.y - this.y));
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
