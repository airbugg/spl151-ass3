import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class RunnableMaintenanceRequestTest {

    private Management management;
    @Before
    public void setUp() throws Exception {
        management = new Management();
        management.repairToolInformationContainer = generateRepairToolInformationContainer();
        management.repairMaterialInformationContainer = generateRepairMaterialInformationContainer();
        management.assets = generateAssets();

    }

    private Assets generateAssets() {
        return null;
    }

    private HashMap<String, RepairMaterialInformation> generateRepairMaterialInformationContainer() {
        HashMap<String, RepairMaterialInformation> map = new HashMap<String, RepairMaterialInformation>();
        map.put("stove",new RepairMaterialInformation("stove",RepairInformationTest.generatePairList()));
        map.put("microwave",new RepairMaterialInformation("microwave",RepairInformationTest.generatePairList()));
        return map;
    }

    private HashMap<String, RepairToolInformation> generateRepairToolInformationContainer() {
        HashMap<String, RepairToolInformation> map = new HashMap<String, RepairToolInformation>();
        map.put("stove",new RepairToolInformation("stove",RepairInformationTest.generatePairList()));
        map.put("microwave",new RepairToolInformation("microwave",RepairInformationTest.generatePairList()));
        return map;
    }

    @Test
    public void testRun() throws Exception {

    }
}