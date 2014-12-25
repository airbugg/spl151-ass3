import java.util.Iterator;
import java.util.concurrent.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Created by airbag on 12/9/14.
 */
class RunnableCustomerGroupManager implements Runnable {

    // field

    private CustomerGroupDetails customerGroupDetails;
    private Management management;


    public RunnableCustomerGroupManager(Management management,
                                        CustomerGroupDetails customerGroupDetails) {
        this.management = management;
        this.customerGroupDetails = customerGroupDetails;

    }


    @Override
    public void run() {
        Management.logger.info(customerGroupDetails.getManager() + "'s Customer Group is alive!");

        while (customerGroupDetails.isRequestsLeft()) { // let's iterate over these rental requests..

            RentalRequest currentRequest = customerGroupDetails.pullRentalRequest();
            Management.logger.info(customerGroupDetails.getManager() + ": Pulled request " + currentRequest.requestId() + " from queue.");

            Management.logger.info(customerGroupDetails.getManager() + ": Pushing request " + currentRequest.requestId() + " to shared repository in Management..");
            management.addRentalRequest(currentRequest); // send rentalRequest to Management

            synchronized (currentRequest) {
                try {
                    while (!currentRequest.isFulfilled()) {
                        Management.logger.info(customerGroupDetails.getManager() + ": Waiting for request " + currentRequest.requestId() + " to be fulfilled...");
                        currentRequest.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            Management.logger.info(customerGroupDetails.getManager() + ": request" + currentRequest.requestId() +" fulfilled. Simulating stay...");

            try {
                simulateStay(currentRequest);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Management.logger.info(customerGroupDetails.getManager() + ": No rental requests left to handle. WE'RE DONE!");
    }

    private void simulateStay(RentalRequest currentRequest) throws ExecutionException, InterruptedException {
        Management.logger.info(customerGroupDetails.getManager() + ": updated rentalRequest " + currentRequest.requestId() + " status to: IN PROGRESS");
        currentRequest.inProgress();

        double totalDamage = 0;

        // we'll sum up the damage values returned by each simulated customer
        Executor executor = Executors.newCachedThreadPool();
        CompletionService<Double> completionService = new ExecutorCompletionService<Double>(executor);

        Iterator<Customer> customerIterator = customerGroupDetails.customerIterator();
        Management.logger.info(customerGroupDetails.getManager() + ": Simulating living...");
        while (customerIterator.hasNext()) { // launch each customer as a CallableSimulateStayInAsset
            completionService.submit(new CallableSimulateStayInAsset(currentRequest, customerIterator.next()));
        }

        for (int i = 0; i < customerGroupDetails.numOfCustomers(); i++) { // sum up damages
            totalDamage += completionService.take().get();
        }

        // vacate asset
        currentRequest.complete();
        Management.logger.info(customerGroupDetails.getManager() + ": updated rentalRequest " + currentRequest.requestId() + " status to: COMPLETE");
        // submit DamageReport to management
        Management.logger.info(customerGroupDetails.getManager() + ": SUBMITTING DAMAGE REPORT TO MANAGEMENT.");
        management.submitDamageReport(currentRequest.createDamageReport(totalDamage));
    }
}
