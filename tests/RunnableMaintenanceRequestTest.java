import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class RunnableMaintenanceRequestTest {

    private RunnableMaintenanceRequest request;
    private RunnableMaintenanceRequest request2;
    private RunnableMaintenanceRequest request3;
    private RunnableMaintenanceRequest request4;
    private RunnableMaintenanceRequest request5;
    private Warehouse warehouse;
    private HashMap<String, LinkedList<RepairMaterialInformation>> repairMaterialInformationContainer;
    private HashMap<String, LinkedList<RepairToolInformation>> repairToolInformationContainer;
    @Before
    public void setUp() throws Exception {
        warehouse = generateWarehouse();
        repairToolInformationContainer = generateRepairToolInformationContainer();
        repairMaterialInformationContainer = generateRepairMaterialInformationContainer();
        request = new RunnableMaintenanceRequest(generateAsset(),repairToolInformationContainer,repairMaterialInformationContainer,warehouse);
        request2 = new RunnableMaintenanceRequest(new Asset("thePalace","palace",23,45,AssetTest.generateContentList(),200000,90),repairToolInformationContainer,repairMaterialInformationContainer,warehouse);
        request3 = new RunnableMaintenanceRequest(new Asset("thePlace","palace",13,455,AssetTest.generateContentList(),200,60),repairToolInformationContainer,repairMaterialInformationContainer,warehouse);
        request4 = new RunnableMaintenanceRequest(new Asset("homlesplace","place",453,4555,AssetTest.generateContentList(),80,9),repairToolInformationContainer,repairMaterialInformationContainer,warehouse);
        request5 = new RunnableMaintenanceRequest(new Asset("theShitHole","shithole",27773,45565,AssetTest.generateContentList(),100,10),repairToolInformationContainer,repairMaterialInformationContainer,warehouse);

    }

    private Warehouse generateWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.addRepairTool("driller",200);
        warehouse.addRepairTool("spoon",200);
        warehouse.addRepairTool("gun",500);
        warehouse.addRepairMaterial("kalsiom",900);
        warehouse.addRepairMaterial("sand",900);
        warehouse.addRepairMaterial("iron",210);

        return warehouse;
    }

    private Asset generateAsset() {
        return new Asset("gals","house",1,1,AssetTest.generateContentList(),500,4);
    }

    private Assets generateAssets() {
        ArrayList<Asset> assetList = new ArrayList<Asset>();
        assetList.add(new Asset("gals","house",1,1,AssetTest.generateContentList(),500,4));
        return new Assets(assetList);
    }

    private HashMap<String, LinkedList<RepairMaterialInformation>> generateRepairMaterialInformationContainer() {
        HashMap<String, LinkedList<RepairMaterialInformation>> map = new HashMap<String, LinkedList<RepairMaterialInformation>>();
        map.put("stove",RepairInformationTest.generateRepairMaterialInformationList());
        map.put("microwave",RepairInformationTest.generateRepairMaterialInformationList());
        return map;
    }

    private HashMap<String, LinkedList<RepairToolInformation>> generateRepairToolInformationContainer() {
        HashMap<String, LinkedList<RepairToolInformation>> map = new HashMap<String, LinkedList<RepairToolInformation>>();
        map.put("stove",RepairInformationTest.generateRepairToolInformationList());
        map.put("microwave",RepairInformationTest.generateRepairToolInformationList());
        return map;
    }


    @Test
    public void testRun() throws Exception {
        request.fAssetToRepair.makeUnavailable();
        request.fAssetToRepair.updateDamage(90);
        request2.fAssetToRepair.makeUnavailable();
        request2.fAssetToRepair.updateDamage(70);
        request3.fAssetToRepair.makeUnavailable();
        request3.fAssetToRepair.updateDamage(90);
        request4.fAssetToRepair.makeUnavailable();
        request4.fAssetToRepair.updateDamage(70);
        request5.fAssetToRepair.makeUnavailable();
        request5.fAssetToRepair.updateDamage(100);
        assertEquals("Unavailable", request.fAssetToRepair.status());
        assertEquals("Unavailable", request2.fAssetToRepair.status());
        assertEquals("Unavailable", request3.fAssetToRepair.status());
        assertEquals("Unavailable", request4.fAssetToRepair.status());
        assertEquals("Unavailable", request5.fAssetToRepair.status());
        Thread t1 = new Thread(request);
        t1.start();
        Thread t2 = new Thread(request2);
        t2.start();
        Thread t3 = new Thread(request3);
        t3.start();
        Thread t4 = new Thread(request4);
        t4.start();
        Thread t5 = new Thread(request5);
        t5.start();
        while(t1.isAlive() || t2.isAlive() || t3.isAlive() || t4.isAlive() || t5.isAlive()){
            System.out.println(warehouse.getRepairTool("driller").currentQuantity());
            Thread.sleep(1000);
        }
        assertEquals("Available",request.fAssetToRepair.status());
        assertEquals("",request.fAssetToRepair.whatsDamaged());
        assertEquals("Available",request2.fAssetToRepair.status());
        assertEquals("", request2.fAssetToRepair.whatsDamaged());
        assertEquals("Available",request3.fAssetToRepair.status());
        assertEquals("",request3.fAssetToRepair.whatsDamaged());
        assertEquals("Available",request4.fAssetToRepair.status());
        assertEquals("", request4.fAssetToRepair.whatsDamaged());
        assertEquals("Available",request5.fAssetToRepair.status());
        assertEquals("", request5.fAssetToRepair.whatsDamaged());
        System.out.println(warehouse.getRepairTool("driller").currentQuantity());
    }
}