import java.util.Iterator;
import java.util.concurrent.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Created by airbag on 12/9/14.
 */
class RunnableCustomerGroupManager implements Runnable {

    // fields
    private static final Logger logger = Logger.getLogger(RunnableCustomerGroupManager.class.getName());

    private CustomerGroupDetails customerGroupDetails;
    private Management management;


    public RunnableCustomerGroupManager(Management management,
                                        CustomerGroupDetails customerGroupDetails) {
        this.management = management;
        this.customerGroupDetails = customerGroupDetails;
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);
    }


    @Override
    public void run() {
        logger.info(customerGroupDetails.getManager() + "'s Customer Group is alive!");
        while (customerGroupDetails.isRequestsLeft()) { // let's iterate over these rental requests..

            logger.info("Pulling first request from queue.");
            RentalRequest currentRequest = customerGroupDetails.pullRentalRequest();
            logger.info("Pushing request to shared repository in Management..");
            management.addRentalRequest(currentRequest); // send rentalRequest to Management

            synchronized (currentRequest) {
                try {
                    while (!currentRequest.isFulfilled()) {
                        logger.info("Waiting for request to be fulfilled...");
                        currentRequest.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            logger.info("Current request fulfilled. Simulating stay...");

            try {
                simulateStay(currentRequest);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void simulateStay(RentalRequest currentRequest) throws ExecutionException, InterruptedException {
        logger.info(customerGroupDetails + " updated rentalRequest " + currentRequest.requestId() + " status to INPROGRESS");
        currentRequest.inProgress();

        double totalDamage = 0;

        // we'll sum up the damage values returned by each simulated customer
        Executor executor = Executors.newCachedThreadPool();
        CompletionService<Double> completionService = new ExecutorCompletionService<Double>(executor);

        Iterator<Customer> customerIterator = customerGroupDetails.customerIterator();
        logger.info("Simulating living...");
        while (customerIterator.hasNext()) { // launch each customer as a CallableSimulateStayInAsset
            completionService.submit(new CallableSimulateStayInAsset(currentRequest, customerIterator.next()));
        }

        for (int i = 0; i < customerGroupDetails.numOfCustomers(); i++) { // sum up damages
            totalDamage += completionService.take().get();
        }

        // vacate asset
        currentRequest.complete();
        logger.info(customerGroupDetails + " updated rentalRequest " + currentRequest.requestId() + " status to COMPLETE");
        // submit DamageReport to management
        logger.info(customerGroupDetails + " is Submitting damageReport to management.");
        management.submitDamageReport(currentRequest.createDamageReport(totalDamage));
    }
}