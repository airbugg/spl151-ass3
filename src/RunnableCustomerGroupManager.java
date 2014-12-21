import java.util.Iterator;
import java.util.concurrent.*;

/**
 * Created by airbag on 12/9/14.
 */
public class RunnableCustomerGroupManager implements Runnable {

    // fields
    private CustomerGroupDetails customerGroupDetails;
    private BlockingQueue<RentalRequest> rentalRequests;
    private Management management;


    public RunnableCustomerGroupManager(Management management,
                                        CustomerGroupDetails customerGroupDetails,
                                        BlockingQueue<RentalRequest> rentalRequests) {
        this.management = management;
        this.customerGroupDetails = customerGroupDetails;
        this.rentalRequests = rentalRequests;
    }


    @Override
    public void run() {

        while (!rentalRequests.isEmpty()) { // let's iterate over these rental requests..

            RentalRequest currentRequest = rentalRequests.peek();
            management.addRentalRequest(rentalRequests.remove()); // send rentalRequest to Management

            System.out.println("Sent Request to Management. Waiting for fulfillment.."); //TODO: log event
            synchronized (currentRequest) {
                try {
                    while (!currentRequest.isFulfilled()) {
                        currentRequest.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Current request fulfilled. Simulating stay..."); //TODO: log event

            try {
                simulateStay(currentRequest);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void simulateStay(RentalRequest currentRequest) throws ExecutionException, InterruptedException{

        currentRequest.inProgress();

        double totalDamage = 0;

        // we'll sum up the damage values returned by each simulated customer
        Executor executor = Executors.newCachedThreadPool();
        CompletionService<Double> completionService = new ExecutorCompletionService<Double>(executor);

        Iterator<Customer> customerIterator = customerGroupDetails.customerIterator();

        while(customerIterator.hasNext()){ // launch each customer as a CallableSimulateStayInAsset
            completionService.submit(new CallableSimulateStayInAsset(currentRequest,customerIterator.next()));
        }

        for (int i=0; i<customerGroupDetails.numOfCustomers(); i++){ // sum up damages
            totalDamage += completionService.take().get();
        }

        // submit DamageReport to management
        management.submitDamageReport(currentRequest.createDamageReport(totalDamage));
    }
}
