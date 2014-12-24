import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by airbag on 12/9/14.
 */
public class RunnableMaintenanceRequest implements Runnable{

    public Asset fAssetToRepair;
    private double fSleepTime;
    private HashMap<String, ArrayList<RepairToolInformation>> repairToolInformationMap;
    private HashMap<String, ArrayList<RepairMaterialInformation>> repairMaterialInformationMap;
    private Warehouse warehouse;


    public RunnableMaintenanceRequest(Asset asset,
                                      HashMap<String,ArrayList<RepairToolInformation>> repairToolInformation,
                                      HashMap<String,ArrayList<RepairMaterialInformation>> repairMaterialInformation,
                                      Warehouse warehouse){
        fAssetToRepair = asset;
        fSleepTime = 0;
        repairToolInformationMap = repairToolInformation;
        repairMaterialInformationMap = repairMaterialInformation;
        this.warehouse = warehouse;
    }


    @Override
    public void run() { // forest, run!

    }

}
