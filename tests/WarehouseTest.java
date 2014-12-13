import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WarehouseTest {

     Warehouse warehouse;

    @Before
    public void setUp() throws Exception {
    this.warehouse = new Warehouse();
        warehouse.addRepairTool("screwdriver",0);
        warehouse.addRepairMaterial("kalsiom",34);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAddRepairTool() throws Exception {
        warehouse.addRepairTool("shpachtel", 3);
        assertEquals(3, warehouse.repairToolContainer.get("shpachtel").currentQuantity());

    }

    @Test
    public void testAddRepairMaterial() throws Exception {
        warehouse.addRepairMaterial("sement", 9);
        assertEquals("sement", warehouse.repairMaterialContainer.get("sement").materialName());
    }

    @Test
    public void testRentATool() throws Exception {
        warehouse.rentATool("screwdriver",9);
        assertEquals(0,warehouse.repairToolContainer.get("screwdriver").currentQuantity());
    }

    @Test
    public void testTakeMaterial() throws Exception {
        warehouse.takeMaterial("kalsiom",36);
        assertEquals(0,warehouse.repairMaterialContainer.get("kalsiom").currentQuantity());
    }

    @Test
    public void testReleaseTool() throws Exception {
        warehouse.rentATool("screwdriver",10);
        warehouse.releaseTool("screwdriver",10);
        assertEquals(10,warehouse.repairToolContainer.get("screwdriver").currentQuantity());
    }

    @Test
    public void testTostring(){
        /**
         warehouse.rentATool("screwdriver", 10);
         System.out.println(warehouse.repairToolContainer.get("screwdriver").toString());
         warehouse.releaseTool("screwdriver", 3);
         System.out.println(warehouse.repairToolContainer.get("screwdriver").toString());
         warehouse.rentATool("screwdriver", 17);
         System.out.println(warehouse.repairToolContainer.get("screwdriver").toString());

         warehouse.takeMaterial("kalsiom", 27);
         System.out.println(warehouse.repairMaterialContainer.get("kalsiom").toString());
         warehouse.takeMaterial("kalsiom", 27);
         System.out.println(warehouse.repairMaterialContainer.get("kalsiom").toString());
         **/

    }

    @Test
    public void testSynchronized(){
        Thread t1 = new Thread(){
            public  void run(){
                for(int i=1; i <= 10; i++){
                    warehouse.rentATool("screwdriver", 1);
                }
            }
        };
        Thread t2 = new Thread(){
            public void run(){
                for(int i=1; i <= 10; i++){
                    warehouse.rentATool("screwdriver", 1);
                }
            }

        };
    t1.start();
    t2.start();
        while(t1.isAlive() || t2.isAlive()){
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        assertEquals(20,warehouse.repairToolContainer.get("screwdriver").totalQuantity());
    }

}