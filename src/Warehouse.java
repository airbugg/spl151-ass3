import java.util.HashMap;

/**
 * Created by gal on 12/8/2014.
 */
public class Warehouse implements WarehouseInterface {

    private HashMap<String,RepairTool> repairToolContainer; // a data structor that contains all repair-tools of the warehouse
    private HashMap<String,RepairMaterial> repairMaterialContainer; // a data structor that contains all repair-materials of the warehouse



    public Warehouse() {
        this.repairToolContainer = new HashMap<String, RepairTool>();
        this.repairMaterialContainer = new HashMap<String, RepairMaterial>();
    }


    public void addRepairTool(String toolName, int quantity) {
        repairToolContainer.put(toolName,new RepairTool(toolName,quantity));
    }


    public void addRepairMaterial(String materialName, int quantity) {
        repairMaterialContainer.put(materialName,new RepairMaterial(materialName,quantity));
    }


    public void rentATool(String toolName, int quantity) {
        repairToolContainer.get(toolName).rentATool(quantity);
    }


    public void takeMaterial(String materialName, int quantity) {
        repairMaterialContainer.get(materialName).takeMaterial(quantity);
    }


    public void releaseTool(String toolName, int quantity) {
        repairToolContainer.get(toolName).releaseTool(quantity);
    }

    public RepairTool getRepairTool(String repairTool){ return repairToolContainer.get(repairTool);}

    public RepairMaterial getRepairMaterial(String repairMaterial){ return repairMaterialContainer.get(repairMaterial);}
}

