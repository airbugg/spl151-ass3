package reit;

import reit.RepairMaterial;
import reit.RepairTool;

import java.util.HashMap;

/**
 * Created by gal on 12/8/2014.
 */
public class Warehouse {

    private HashMap<String, RepairTool> repairToolContainer;
    private HashMap<String, RepairMaterial> repairMaterialContainer;


    public Warehouse() {
        this.repairToolContainer = new HashMap<String, RepairTool>();
        this.repairMaterialContainer = new HashMap<String, RepairMaterial>();
    }


    public void addRepairTool(String toolName, int quantity) {
        repairToolContainer.put(toolName, new RepairTool(toolName, quantity));
    }

    public void addRepairMaterial(String materialName, int quantity) {
        repairMaterialContainer.put(materialName, new RepairMaterial(materialName, quantity));
    }

    public void rentATool(String toolName, int quantity) {
        repairToolContainer.get(toolName).acquireTool(quantity);
    }

    public void takeMaterial(String materialName, int quantity) {
        repairMaterialContainer.get(materialName).acquireMaterial(quantity);
    }

    public void releaseTool(String toolName, int quantity) {
        repairToolContainer.get(toolName).releaseTool(quantity);
    }
}