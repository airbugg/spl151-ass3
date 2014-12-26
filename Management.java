import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Created by airbag on 12/8/14.
 */
public class Management {

    // fields
    public static final Logger logger = Logger.getLogger(Management.class.getName());

    private Warehouse warehouse;
    private Assets assets;

    private HashMap<String, ArrayList<RepairMaterialInformation>> repairMaterialInformationMap;
    private HashMap<String, ArrayList<RepairToolInformation>> repairToolInformationMap;

    private Semaphore reportSemaphore;
    private CyclicBarrier clerkShiftBarrier;
    private CountDownLatch maintenanceShiftLatch;
    private AtomicInteger nUnhandledRequests;
    private int nUnhandledRequestsPerShift;
    private Object beginNewShift;
    private BlockingQueue<RentalRequest> rentalRequests;

    private Vector<CustomerGroupDetails> customers;
    private Vector<ClerkDetails> clerks;

    private int nMaintenanceWorkers;

    public Management(Warehouse warehouse, Assets assets) { // public constructor
        this.warehouse = warehouse;
        this.assets = assets;
        this.rentalRequests = new LinkedBlockingDeque<RentalRequest>();
        this.clerks = new Vector<ClerkDetails>();
        this.repairToolInformationMap = new HashMap<String, ArrayList<RepairToolInformation>>();
        this.repairMaterialInformationMap = new HashMap<String, ArrayList<RepairMaterialInformation>>();
        this.customers = new Vector<CustomerGroupDetails>();
        this.nUnhandledRequests = new AtomicInteger(0);
        this.nUnhandledRequestsPerShift = 0;
        this.nMaintenanceWorkers = 0;
        this.beginNewShift = new Object();
        this.reportSemaphore = new Semaphore(0);
    }

    public void simulate() { // main simulation loop

        init();
        runCustomers();
        runClerks();

        while (nUnhandledRequests.get() > 0) {
            waitForReports(); // waiting until management acquires all damageReports
            updateDamage(); // iterate over acquired damage reports, update damage.
            beginMaintenanceShift(); // call maintenance
            beginNewShift(); // notify clerks a new shift has begun.
        }

        logger.info("<<<<<<<<< SIMULATION COMPLETE!!! >>>>>>>>>");
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

    public void setTotalNumberOfRentalRequests(int nRentalRequests) { // TODO: THERE MUST BE A BETTER WAY...
        this.nUnhandledRequests.set(nRentalRequests);
        this.nUnhandledRequestsPerShift = nRentalRequests;
    }

    public void setNumberOfMaintenanceWorkers(int nMaintenanceWorkers) { // TODO: THERE MUST BE A BETTER WAY..
        this.nMaintenanceWorkers = nMaintenanceWorkers;
    }

    public void submitDamageReport(DamageReport damageReport) {
        assets.submitDamageReport(damageReport); // submit report to Assets.
        reportSemaphore.release(1);
        logger.info("MANAGEMENT: damageReport was submitted.");
    }

    public void addRentalRequest(RentalRequest rentalRequest) {
        rentalRequests.add(rentalRequest);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Management: \n");

        for (CustomerGroupDetails customerGroupDetails : customers) {
            stringBuilder.append(customerGroupDetails).append("\n");
        }

        for (ClerkDetails clerkDetails : clerks) {
            stringBuilder.append(clerkDetails).append("\n");
        }

        for (SortedMap.Entry<String, ArrayList<RepairToolInformation>> entry : repairToolInformationMap.entrySet()) {
            stringBuilder.append(entry.getKey()).append(" \tTools: [");
            for (RepairToolInformation repairToolInformation: entry.getValue()) {
                stringBuilder.append("<").append(repairToolInformation.getName()).append(",").append(repairToolInformation.getQuantity()).append(">");
            }
            stringBuilder.append("]\n");
        }
        return stringBuilder.toString();
    }

    private void init() {
        reportSemaphore.release(nUnhandledRequests.get()); // initialize reportSemaphore to # of rental requests
        nUnhandledRequestsPerShift = nUnhandledRequests.get(); // before the simulation begins, these are equal
    }

    private void runCustomers() {
        logger.info("MANAGEMENT: Initializing Customer Groups...");
        for (CustomerGroupDetails customerGroup : customers) {
            new Thread(new RunnableCustomerGroupManager(this, customerGroup)).start();
        }
    }

    private void runClerks() {
        // ensuring we'll wait for all clerks to end their shift
        clerkShiftBarrier = new CyclicBarrier(clerks.size() + 1); // +1 for management
        logger.info("MANAGEMENT: Initializing RunnableClerks...");
        for (ClerkDetails clerk : clerks) {
            new Thread(new RunnableClerk(clerk, rentalRequests,
                    clerkShiftBarrier, assets,
                    nUnhandledRequests, reportSemaphore,
                    beginNewShift)).start();
        }
    }

    private void beginMaintenanceShift() {
        logger.info("MANAGEMENT: Maintenance shift begins.. Retrieving damaged asset list.");
        ArrayList<Asset> damagedAssets = assets.getDamagedAssets();
        // initialize latch. when this reaches zero, maintenance work is done.
        maintenanceShiftLatch = new CountDownLatch(damagedAssets.size());

        ExecutorService maintenanceExecutor = Executors.newFixedThreadPool(nMaintenanceWorkers);
        logger.info("MANAGEMENT: Executing maintenance worker threads..");
        for (Asset asset : damagedAssets) {
            maintenanceExecutor.execute(new RunnableMaintenanceRequest(asset,
                    repairToolInformationMap,
                    repairMaterialInformationMap,
                    warehouse,
                    maintenanceShiftLatch));
        }

        try {
            logger.info("MANAGEMENT: Waiting for maintenance workers to finish repairs..");
            maintenanceShiftLatch.await(); // waiting for maintenance workers to finish repairs
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(assets.toString());
        maintenanceExecutor.shutdown();
        logger.info("MAINTENANCE: Done! let's hit the bar!");
        // done. let's hit the bar!
    }

    private void updateDamage() {
        assets.applyDamage();
    }

    private void beginNewShift() {
        synchronized (beginNewShift) {
            logger.info("MANAGEMENT: CALLING ALL CLERKS!! NEW SHIFT HAS BEGUN!");
            beginNewShift.notifyAll();
        }
    }

    private void waitForReports() {

        try {
            logger.info("MANAGEMENT: Waiting for clerks to finish their shift.");
            clerkShiftBarrier.await(); // waiting for all clerks to finish their shift.
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        // all clerks are off-duty.
        // draining the semaphore will provide us with the # of reports we should expect to be submitted.
        // immediately acquiring the drained amount will guarantee that only once all reports have been submitted,
        // maintenance will be called upon.

        int semaphoreCount = reportSemaphore.drainPermits();
        try {
            reportSemaphore.acquire(nUnhandledRequestsPerShift - semaphoreCount); // essentially emptying the semaphore
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        nUnhandledRequestsPerShift = nUnhandledRequests.get();
        reportSemaphore.release(nUnhandledRequestsPerShift); // filling it up again, before clerks' next shift begins
    }
}
