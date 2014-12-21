import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by airbag on 12/8/14.
 */
public class Management {

    // fields
    private Warehouse warehouse;
    private Assets assets;

    private HashMap<String, ArrayList<RepairMaterialInformation>> repairMaterialInformationMap;
    private HashMap<String, ArrayList<RepairToolInformation>> repairToolInformationMap;

    AtomicInteger nUnhandledRequests;
    private BlockingQueue<RentalRequest> rentalRequests;
    private Vector<CustomerGroupDetails> customers;
    private Vector<ClerkDetails> clerks;



    public Management(Warehouse warehouse, Assets assets) { // public constructor
        this.warehouse = warehouse;
        this.assets = assets;
        this.rentalRequests = new LinkedBlockingDeque<RentalRequest>();
        this.clerks = new Vector<ClerkDetails>();
        this.repairToolInformationMap = new HashMap<String, ArrayList<RepairToolInformation>>();
        this.repairMaterialInformationMap = new HashMap<String, ArrayList<RepairMaterialInformation>>();
        this.customers = new Vector<CustomerGroupDetails>();
        this.nUnhandledRequests = new AtomicInteger(0);
    }

    public void run() {

    }

    public void addClerk(ClerkDetails clerkDetails) {
        clerks.add(clerkDetails);
    }

    public void addCustomerGroup(CustomerGroupDetails customerGroupDetails) {
        customers.add(customerGroupDetails);
    }

    public void addItemRepairTool(String name,
                                  ArrayList<RepairToolInformation> repairToolInformation) {
        repairToolInformationMap.put(name, repairToolInformation);
    }

    public void addItemRepairMaterial(String name,
                                      ArrayList<RepairMaterialInformation> repairMaterialInformation) {
        repairMaterialInformationMap.put(name, repairMaterialInformation);
    }

    public void submitDamageReport(DamageReport damageReport) {
        assets.submitDamageReport(damageReport);
    }

    public void addRentalRequest(RentalRequest rentalRequest) {
        rentalRequests.add(rentalRequest);
        nUnhandledRequests.getAndIncrement();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Management: \n");

        for (CustomerGroupDetails customerGroupDetails : customers) {
            stringBuilder.append(customerGroupDetails);
        }

        return stringBuilder.toString();
    }

    private void runCustomers() {
        for (CustomerGroupDetails customerGroup : customers) {
            (new Thread )
        }
    }

    private void runClerks() {

    }

    private void runMaintenance() {

    }

    private void updateDamage() {

    }
}
