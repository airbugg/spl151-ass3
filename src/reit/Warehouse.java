package reit;

import reit.RepairMaterial;
import reit.RepairTool;

import java.util.HashMap;
import java.util.Map;

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

    public synchronized void printStatistics() {
        synchronized (System.out){
            System.out.println("the total amount of tools used in the program: ");
            for(Map.Entry<String,RepairTool> entry : repairToolContainer.entrySet()){
                System.out.println("[" + entry.getKey() + "] =" + entry.getValue().getTotalAcquired());
            }
            System.out.println("the total amount of materials used in the program: ");
            for(Map.Entry<String,RepairMaterial> entry : repairMaterialContainer.entrySet()){
                System.out.println("[" + entry.getKey() + "] =" + entry.getValue().getTotalAcquired());
            }
        }
    }
}