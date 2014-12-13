/**
 * Created by airbag on 12/9/14.
 */
public class Location {

    private double x;
    private double y;


    public Location(double x_coordinate, double y_coordinate) {
        x = x_coordinate;
        y = y_coordinate;
    }

    public double calculateDistance(Location other){
        return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2));
    }
}
