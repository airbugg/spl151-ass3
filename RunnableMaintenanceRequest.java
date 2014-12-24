import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by airbag on 12/9/14.
 */
public class RunnableMaintenanceRequest implements Runnable{

    public Asset fAssetToRepair;
    private double fSleepTime;
    private HashMap<String, ArrayList<RepairToolInformation>> repairToolInformationContainer;
    private HashMap<String, ArrayList<RepairMaterialInformation>> repairMaterialInformationContainer;
    private Warehouse warehouse;


    public RunnableMaintenanceRequest(Asset asset,
                                      HashMap<String,ArrayList<RepairToolInformation>> repairToolInformation,
                                      HashMap<String,ArrayList<RepairMaterialInformation>> repairMaterialInformation,
                                      Warehouse warehouse){
        fAssetToRepair = asset;
        fSleepTime = 0;
        repairToolInformationContainer = repairToolInformation;
        repairMaterialInformationContainer = repairMaterialInformation;
        this.warehouse = warehouse;
    }


    @Override
    public void run() {
        ArrayList<AssetContent> brokenContents = fAssetToRepair.BrokenContentsList();
        ListIterator<AssetContent> it = brokenContents.listIterator();
        //adds sleep time
        while(it.hasNext()){
            AssetContent currentContent = it.next();
            fSleepTime += currentContent.timeToSleep();
        }
        it = brokenContents.listIterator();
        ArrayList<RepairToolInformation> toolsToReturn = new ArrayList<RepairToolInformation>();
        //renting tools and materials
        while(it.hasNext()){
            AssetContent currentContent = it.next();
            ListIterator<RepairToolInformation> repairToolListIt = repairToolInformationContainer.get(currentContent.Name()).listIterator();
            //renting tools
            while(repairToolListIt.hasNext()){
                RepairToolInformation currentTool = repairToolListIt.next();
                warehouse.rentATool(currentTool.Name(),currentTool.quantity());
                //remember what tool we need to return
                toolsToReturn.addLast(new RepairToolInformation(currentTool.Name(),currentTool.quantity()));
            }
            ListIterator<RepairMaterialInformation> repairMaterialListIterator = repairMaterialInformationContainer.get(currentContent.Name()).listIterator();
            //renting materials
            while(repairMaterialListIterator.hasNext()){
                RepairMaterialInformation currentMaterial = repairMaterialListIterator.next();
                warehouse.takeMaterial(currentMaterial.Name(),currentMaterial.quantity());
            }
        }
        //sleep needed time
        try {
            Thread.sleep(Math.round(fSleepTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //release the tools
        ListIterator<RepairToolInformation> toolsToReturnIt = toolsToReturn.listIterator();
        while(toolsToReturnIt.hasNext()){
            RepairToolInformation currentTool = toolsToReturnIt.next();
            warehouse.releaseTool(currentTool.Name(),currentTool.quantity());
        }
        //heal the contents
        fAssetToRepair.Heal();
        fAssetToRepair.makeAvailable();
    }

}
