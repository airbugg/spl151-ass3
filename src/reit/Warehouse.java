package reit;

import reit.RepairMaterial;
import reit.RepairTool;

import java.util.HashMap;
import java.util.Map;

/**
 * Warehouse:
 * This class represents an Warehouse object, with mandatory fields, as defined in the assignment documentation.
 * Created by gal on 12/8/2014.
 */
public class Warehouse {

    private HashMap<String, RepairTool> repairToolContainer;
    private HashMap<String, RepairMaterial> repairMaterialContainer;


    /**
     * default constructor, initializing the data-structures of the object
     */
    public Warehouse() {
        this.repairToolContainer = new HashMap<String, RepairTool>();
        this.repairMaterialContainer = new HashMap<String, RepairMaterial>();
    }


    /**
     * adding new tool to the container
     * @param toolName
     * @param quantity - the initialized quantity (might change during the run of the simulator)
     */
    public void addRepairTool(String toolName, int quantity) {
        repairToolContainer.put(toolName, new RepairTool(toolName, quantity));
    }

    /**
     * adding new material to the container
     * @param materialName
     * @param quantity - the initialized quantity (might change during the run of the simulator)
     */
    public void addRepairMaterial(String materialName, int quantity) {
        repairMaterialContainer.put(materialName, new RepairMaterial(materialName, quantity));
    }

    /**
     *
     * @param toolName - the tool need to be rent
     * @param quantity - the quantity needed
     */
    public void rentATool(String toolName, int quantity) {
        repairToolContainer.get(toolName).acquireTool(quantity);
    }

    /**
     *
     * @param materialName - the material needed
     * @param quantity - the quantity needed
     */
    public void takeMaterial(String materialName, int quantity) {
        repairMaterialContainer.get(materialName).acquireMaterial(quantity);
    }

    /**
     *
     * @param toolName
     * @param quantity
     */
    public void releaseTool(String toolName, int quantity) {
        repairToolContainer.get(toolName).releaseTool(quantity);
    }

    /**
     * a part of the statistics printing, printing the amount of
     * tools/materials used so far in the simulator
     */
    public synchronized StringBuilder WarehouseStatistics() {
        StringBuilder WarehouseStatistics = new StringBuilder();
        WarehouseStatistics.append("current warehouse statistics:" + "\n");
        WarehouseStatistics.append("the total amount of tools used in the program: " + "\n");
            for(Map.Entry<String,RepairTool> entry : repairToolContainer.entrySet()){
                WarehouseStatistics.append("[" + entry.getKey() + "] = " + entry.getValue().getTotalAcquired() + "\n");
            }
        WarehouseStatistics.append("the total amount of materials used in the program: " + "\n");
            for(Map.Entry<String,RepairMaterial> entry : repairMaterialContainer.entrySet()){
                WarehouseStatistics.append("[" + entry.getKey() + "] = " + entry.getValue().getTotalAcquired() + "\n");
            }
        return WarehouseStatistics;
        }

}
