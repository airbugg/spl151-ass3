import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by airbag on 12/9/14.
 */
public class RunnableMaintenanceRequest implements Runnable{

    private Asset fAssetToRepair;
    private double fSleepTime;
    private HashMap<String,RepairToolInformation> repairToolInformationContainer;
    private HashMap<String,RepairMaterialInformation> repairMaterialInformationContainer;
    private Assets assets;
    private Warehouse warehouse;


    public RunnableMaintenanceRequest(Asset asset, HashMap<String,RepairToolInformation> repairToolInformation, HashMap<String,RepairMaterialInformation> repairMaterialInformation, Assets assets, Warehouse warehouse){
        fAssetToRepair = asset;
        fSleepTime = 0;
        repairToolInformationContainer = repairToolInformation;
        repairMaterialInformationContainer = repairMaterialInformation;
        this.assets = assets;
        this.warehouse = warehouse;
    }


    @Override
    public void run() {
        LinkedList<AssetContent> brokenContents = fAssetToRepair.BrokenContentsList();
        ListIterator<AssetContent> it = brokenContents.listIterator();
        LinkedList<RepairToolInformation> neededTools = new LinkedList<RepairToolInformation>();
        LinkedList<RepairMaterialInformation> neededMaterials = new LinkedList<RepairMaterialInformation>();
        while(it.hasNext()){
            AssetContent currentContent = it.next();
            fSleepTime += currentContent.timeToSleep();

        }

    }

}
