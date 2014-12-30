import org.junit.Before;
import org.junit.Test;
import reit.Statistics;

import java.util.concurrent.ArrayBlockingQueue;

public class StatisticsTest {

    Statistics statistics;
    Warehouse warehouse;

    @Before
    public void setUp() throws Exception {
        Warehouse warehouse = new Warehouse();
        warehouse.addRepairTool("shpactel",80);
        warehouse.addRepairTool("Toaster",400);
        warehouse.addRepairTool("driller",100);
        warehouse.addRepairMaterial("sand",34);
        warehouse.addRepairMaterial("iron",12);
        this.warehouse = warehouse;

        ArrayBlockingQueue<RentalRequest> rentalRequests = new ArrayBlockingQueue<RentalRequest>(10);
        RentalRequest request1 = new RentalRequest("request1","palace",7,10);
        rentalRequests.add(request1);
        RentalRequest request2 = new RentalRequest("request2","home",2,375);
        rentalRequests.add(request2);
        RentalRequest request3 = new RentalRequest("request3","shithole",1,1);
        rentalRequests.add(request3);
        RentalRequest request4 = new RentalRequest("request4","gym",90,10222);
        rentalRequests.add(request4);

        statistics = new Statistics(warehouse,rentalRequests);
    }

    @Test
    public void testShowWarehouseStatistics() throws Exception {
        warehouse.takeMaterial("sand",20);
        statistics.showWarehouseStatistics();
    }

    @Test
    public void testShowRentalRequestsStatistics() throws Exception {

    }

    @Test
    public void testAddIncome() throws Exception {
            statistics.addIncome(900);
            statistics.printIncome();
    }
}