package reit;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by airbag on 12/9/14.
 */
class RunnableMaintenanceRequest implements Runnable {


    private Asset fAsset;
    private HashMap<String, ArrayList<RepairToolInformation>> repairToolInformationMap;
    private HashMap<String, ArrayList<RepairMaterialInformation>> repairMaterialInformationMap;
    private SortedMap<String, Integer> requiredTools;
    private SortedMap<String, Integer> requiredMaterials;
    private Warehouse warehouse;
    private List<String> contentToFix;
    private CountDownLatch shiftLatch;


    public RunnableMaintenanceRequest(Asset asset,
                                      HashMap<String, ArrayList<RepairToolInformation>> repairToolInformation,
                                      HashMap<String, ArrayList<RepairMaterialInformation>> repairMaterialInformation,
                                      Warehouse warehouse,
                                      CountDownLatch shiftLatch) {
        fAsset = asset;
        repairToolInformationMap = repairToolInformation;
        repairMaterialInformationMap = repairMaterialInformation;
        requiredTools = new TreeMap<String, Integer>();
        requiredMaterials = new TreeMap<String, Integer>();
        this.warehouse = warehouse;
        this.contentToFix = contentToFix();
        this.shiftLatch = shiftLatch;
    }

    @Override
    public void run() { // forest, run!
        acquireTools();
        acquireMaterials();
        repairingAsset(fAsset.timeToFix()); // sleeping...
        repairAsset(); // actually restoring health.
        shiftLatch.countDown();
    }

    private List<String> contentToFix() {
        return Arrays.asList(fAsset.whatsDamaged().split(","));
    }

    private void repairAsset() {
        fAsset.repairAsset();
    }

    private void acquireTools() {
        Management.logger.info("MAINTENANCE: ACQUIRING TOOLS...");
        for (String content : contentToFix) {
            ArrayList<RepairToolInformation> toolList = repairToolInformationMap.get(content);

            for (RepairToolInformation tool : toolList) {
                if (!requiredTools.containsKey(tool.getName())) {
                    requiredTools.put(tool.getName(),tool.getQuantity());
                }
                else if (requiredTools.get(tool.getName()).intValue() < tool.getQuantity()) {
                    requiredTools.put(tool.getName(), tool.getQuantity());
                }
            }
        }
    }

    private void acquireMaterials() {
        Management.logger.info("MAINTENANCE: ACQUIRING MATERIALS...");
        for (String content : contentToFix) {
            ArrayList<RepairMaterialInformation> materialList = repairMaterialInformationMap.get(content);

            for (RepairMaterialInformation material : materialList) {
                if (!requiredMaterials.containsKey(material.getName())) {
                    requiredMaterials.put(material.getName(),material.getQuantity());
                }
                else {
                    Integer updateQuantity = requiredMaterials.get(material.getName()) + material.getQuantity();
                    requiredMaterials.put(material.getName(),updateQuantity);
                }
            }
        }
    }

    private void releaseTools() {
        Management.logger.info("MAINTENANCE: RELEASING TOOLS...");
        for (Map.Entry<String,Integer> entry : requiredTools.entrySet()) {
            warehouse.releaseTool(entry.getKey(),entry.getValue());
        }
    }


    private void repairingAsset (long timeToFix) {
        Management.logger.info("MAINTENANCE: FIXING ASSET...");
        try {
            Thread.sleep(timeToFix);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
