import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by airbag on 12/9/14.
 */
public class RunnableMaintenanceRequest implements Runnable{

    public Asset fAssetToRepair;
    private double fSleepTime;
    private HashMap<String,LinkedList<RepairToolInformation>> repairToolInformationContainer;
    private HashMap<String,LinkedList<RepairMaterialInformation>> repairMaterialInformationContainer;
    private Warehouse warehouse;


    public RunnableMaintenanceRequest(Asset asset, HashMap<String,LinkedList<RepairToolInformation>> repairToolInformation, HashMap<String,LinkedList<RepairMaterialInformation>> repairMaterialInformation, Warehouse warehouse){
        fAssetToRepair = asset;
        fSleepTime = 0;
        repairToolInformationContainer = repairToolInformation;
        repairMaterialInformationContainer = repairMaterialInformation;
        this.warehouse = warehouse;
    }


    @Override
    public void run() {
        LinkedList<AssetContent> brokenContents = fAssetToRepair.BrokenContentsList();
        ListIterator<AssetContent> it = brokenContents.listIterator();
        //adds sleep time
        while(it.hasNext()){
            AssetContent currentContent = it.next();
            fSleepTime += currentContent.timeToSleep();
        }
        it = brokenContents.listIterator();
        LinkedList<RepairToolInformation> toolsToReturn = new LinkedList<RepairToolInformation>();
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
            Thread.sleep((long) fSleepTime);
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
