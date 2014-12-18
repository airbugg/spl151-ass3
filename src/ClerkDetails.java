/**
 * Created by airbag on 12/9/14.
 */
public class ClerkDetails {

    private String fName;
    private Location fLocation;

    public ClerkDetails(String name, double x, double y){
        fName = name;
        fLocation = new Location(x,y);
    }
}
