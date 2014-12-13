import java.util.HashMap;

/**
 * Created by airbag on 12/9/14.
 */
public class Asset {

    private String name;
    private String	type;
    private Location location;
    public HashMap<String,AssetContent> assetContentContainer;
    private	String status;
    private int costPerNight;
    private int size;

    public Asset(String name, String type, double x_coordinate, double y_coordinate, int costPerNight, int size){
        this.name = name;
        this.type = type;
        location = new Location(x_coordinate, y_coordinate);
        status = "Available";
        this.costPerNight = costPerNight;
        this.size = size;
    }


}
