import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Created by airbag on 12/9/14.
 */
class RunnableMaintenanceRequest implements Runnable {

    private static final Logger logger = Logger.getLogger(RunnableMaintenanceRequest.class.getName());

    private final int SEC_TO_MILL = 1000;
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
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);

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
        for (String content : contentToFix) {
            for (RepairToolInformation repairTool : repairToolInformationMap.get(content)) {
                if (requiredTools.get(repairTool) == null ) {
                    requiredTools.put(repairTool.getName(),
                            Integer.valueOf(repairTool.getQuantity()));

                }
                else if (requiredTools.get(repairTool) < Integer.valueOf(repairTool.getQuantity())) {
                    requiredTools.put(repairTool.getName(),
                            Integer.valueOf(repairTool.getQuantity()));

                }
            }
        }

        for (Map.Entry<String, Integer> entry : requiredTools.entrySet()) {
            warehouse.rentATool(entry.getKey(), entry.getValue());
        }
    }

    private void acquireMaterials() { // TODO: Consider unifying under one function, accepting generics
        for (String content : contentToFix) {
            for (RepairMaterialInformation repairMaterial : repairMaterialInformationMap.get(content)) {
                if (requiredMaterials.get(repairMaterial) == null ) {
                    requiredMaterials.put(repairMaterial.getName(),
                            Integer.valueOf(repairMaterial.getQuantity()));

                }
                else if (requiredMaterials.get(repairMaterial) < Integer.valueOf(repairMaterial.getQuantity())) {
                    requiredMaterials.put(repairMaterial.getName(),
                            Integer.valueOf(repairMaterial.getQuantity()));

                }
            }
        }

        for (Map.Entry<String, Integer> entry : requiredMaterials.entrySet()) {
            warehouse.takeMaterial(entry.getKey(), entry.getValue());
        }
    }

    private void releaseTools() {
        for (Map.Entry<String,Integer> entry : requiredTools.entrySet()) {
            warehouse.releaseTool(entry.getKey(),entry.getValue());
        }
    }


    private void repairingAsset (double timeToFix) {
        try {
            Thread.sleep(Math.round(timeToFix * SEC_TO_MILL));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
