import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by airbag on 12/8/14.
 */
public class Management {

    // fields
    private Warehouse warehouse;
    private BlockingQueue<RentalRequest> rentalRequests;
    private Assets assets;
    private Vector<CustomerGroupDetails> customers;
    private Vector<ClerkDetails> clerks;
    private HashMap<String,RepairMaterialInformation> repairMaterialInformation;
    private HashMap<String,RepairToolInformation> repairToolInformation;


    public Management() { // public constructor

        this.rentalRequests = new LinkedBlockingDeque<RentalRequest>();
    }

    public void addClerk() {

    }

    public void addCustomerGroup() {

    }

    public void addItemRepairTool() {

    }

    public void addItemRepairMaterial() {

    }

    public void submitDamageReport(DamageReport damageReport) {

    }

    public void addRentalRequest(RentalRequest rentalRequest) {
        rentalRequests.add(rentalRequest);
    }
}
