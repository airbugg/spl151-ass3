import java.util.HashMap;
import java.util.concurrent.BlockingDeque;

/**
 * Created by airbag on 12/8/14.
 */
public class Management {
    public static Assets assets;
    public static BlockingDeque<RentalRequest> requestsQueue;
    public static Warehouse warehouse;
    public static HashMap<String,RepairMaterialInformation> repairMaterialInformationContainer;
    public static HashMap<String,RepairToolInformation> repairToolInformationContainer;

    public Management(){

    }
}
