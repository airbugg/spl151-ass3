import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WarehouseTest {

    Warehouse warehouse;

    @Before
    public void setUp() throws Exception {
    this.warehouse = new Warehouse();
        warehouse.addRepairTool("screwdriver",6);
        warehouse.addRepairMaterial("kalsiom",34);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAddRepairTool() throws Exception {
        warehouse.addRepairTool("shpachtel", 3);
        assertEquals(3, warehouse.repairToolContainer.get("shpachtel").quantity);

    }

    @Test
    public void testAddRepairMaterial() throws Exception {
        warehouse.addRepairMaterial("sement", 9);
        assertEquals("sement", warehouse.repairMaterialContainer.get("sement").materialName);
    }

    @Test
    public void testRentATool() throws Exception {
        warehouse.rentATool("screwdriver",18);
        assertEquals(0,warehouse.repairToolContainer.get("screwdriver").quantity);
    }

    @Test
    public void testTakeMaterial() throws Exception {
        warehouse.takeMaterial("kalsiom",36);
        assertEquals(0,warehouse.repairMaterialContainer.get("kalsiom").quantity);
    }

    @Test
    public void testReleaseTool() throws Exception {
        warehouse.releaseTool("screwdriver",2);
        assertEquals(8,warehouse.repairToolContainer.get("screwdriver").quantity);
    }
}