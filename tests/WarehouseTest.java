import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WarehouseTest {
    /** the object under test**/
     Warehouse warehouse;

    /**
     * creates a new warehouse and inserts a tool and a material to the data structors
     *
     * @pre: none
     * @post: warehouse.repairToolContainer.containsKey("screwdriver") == true
     * @post: warehouse.repairMaterialContainer.containsKey("kalsiom") == true
     */
    @Before
    public void setUp() throws Exception {
    this.warehouse = new Warehouse();
        warehouse.addRepairTool("screwdriver",0);
        warehouse.addRepairMaterial("kalsiom",0);

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

         warehouse.rentATool("screwdriver", 10);
         assertEquals("current amount of screwdriver used int the simulator is 10", warehouse.repairToolContainer.get("screwdriver").toString());
         warehouse.releaseTool("screwdriver", 3);
         assertEquals("current amount of screwdriver used int the simulator is 7", warehouse.repairToolContainer.get("screwdriver").toString());
         warehouse.rentATool("screwdriver", 17);
         assertEquals("current amount of screwdriver used int the simulator is 24",warehouse.repairToolContainer.get("screwdriver").toString());

         warehouse.takeMaterial("kalsiom", 27);
         assertEquals("the current quantity of kalsiom beeing used is 27", warehouse.repairMaterialContainer.get("kalsiom").toString());
         warehouse.takeMaterial("kalsiom", 27);
         assertEquals("the current quantity of kalsiom beeing used is 54",warehouse.repairMaterialContainer.get("kalsiom").toString());

    }


    @Test
    public void testSynchronizedRepairTool(){
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

    @Test
    public void testSynchronizedRepairMaterial(){
        Thread t1 = new Thread(){
            public  void run(){
                for(int i=1; i <= 16; i++){
                    warehouse.takeMaterial("kalsiom", 2);
                }
            }
        };
        Thread t2 = new Thread(){
            public void run(){
                for(int i=1; i <= 16; i++){
                    warehouse.takeMaterial("kalsiom", 1);
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
        assertEquals(48,warehouse.repairMaterialContainer.get("kalsiom").totalQuantity());
    }
    }
