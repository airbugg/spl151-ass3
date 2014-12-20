import java.util.concurrent.BlockingQueue;


/**
 * Created by airbag on 12/9/14.
 */
public class RunnableClerk implements Runnable{

    private ClerkDetails clerkDetails;
    private BlockingQueue<RentalRequest> rentalRequests;
    private int numOfRentalRequests;

    public RunnableClerk(ClerkDetails clerkDetails, BlockingQueue<RentalRequest> rentalRequests) {
        this.clerkDetails = clerkDetails;
        this.rentalRequests = rentalRequests;
    }


    @Override
    public void run() {
        while (true) {

            if (!rentalRequests.isEmpty()) {
                RentalRequest currRequest = rentalRequests.peek();
                if (!currRequest.isFulfilled()) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (currRequest) {
                        System.out.println("About to fulfill request...");
                        //currRequest.fulfill();
                        System.out.println("About to notify Group Manager...");
                        currRequest.notify();
                    }
                }

            }
        }
    }
}
