import java.util.HashMap;

/**
 * Created by gal on 12/8/2014.
 */
public class Warehouse implements WarehouseInterface {

    public HashMap<String,RepairTool> repairToolContainer;
    public HashMap<String,RepairMaterial> repairMaterialContainer;


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
        if(repairToolContainer.get(toolName).quantity < quantity){
            buyTool(toolName, quantity - repairToolContainer.get(toolName).quantity);
        }
        repairToolContainer.get(toolName).quantity = repairToolContainer.get(toolName).quantity - quantity;
    }

    public void takeMaterial(String materialName, int quantity) {
        if(repairMaterialContainer.get(materialName).quantity < quantity){
            buyMaterial(materialName, quantity - repairMaterialContainer.get(materialName).quantity);
        }
        repairMaterialContainer.get(materialName).quantity = repairMaterialContainer.get(materialName).quantity - quantity;
    }

    public void releaseTool(String toolName, int quantity) {
        repairToolContainer.get(toolName).quantity = repairToolContainer.get(toolName).quantity + quantity;
    }

    private void buyTool(String toolType, int quantity) {
        repairToolContainer.get(toolType).quantity += quantity;
    }

    private void buyMaterial(String materialType, int quantity) {
        repairMaterialContainer.get(materialType).quantity += quantity;
    }



}
