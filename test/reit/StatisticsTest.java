package reit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StatisticsTest {

    Warehouse warehouse;
    Statistics statistics;
    @Before
    public void setUp() throws Exception {
        warehouse = generateWarehouse();
        statistics = new Statistics(warehouse);
    }

    @Test
    public void testToString() throws Exception {
            statistics.addIncome(90);
            statistics.addFulfilledRequest("request1",generateRentalRequest());
            warehouse.rentATool("driller",25);
            warehouse.rentATool("driller",55);
            warehouse.takeMaterial("kalsiom",90);
            warehouse.takeMaterial("kalsiom",10);
            statistics.addIncome(10);

        System.out.println(statistics.toString());
    }

    private RentalRequest generateRentalRequest() {
        RentalRequest request = new RentalRequest("request1","house",2,8);
        request.fulfill(new Asset("gal's crib","house",new Location(3,3),6,5));
        return request;
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
}