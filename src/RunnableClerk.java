import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by airbag on 12/9/14.
 */
public class RunnableClerk implements Runnable{

    private final int SEC_TO_MILL = 1000;
    private double currentShiftLength;
    private ClerkDetails clerkDetails;
    private BlockingQueue<RentalRequest> rentalRequests;
    private RentalRequest currentRequest;
    private Assets assets;
    private CyclicBarrier barrier;
    private AtomicInteger nUnhandledRequests;

    public RunnableClerk(ClerkDetails clerkDetails,
                         BlockingQueue<RentalRequest> rentalRequests,
                         CyclicBarrier barrier,
                         Assets assets,
                         AtomicInteger nUnhandledRequests) {
        this.clerkDetails = clerkDetails;
        this.rentalRequests = rentalRequests;
        this.barrier = barrier;
        this.assets = assets;
        this.currentRequest = null;
        this.nUnhandledRequests = nUnhandledRequests;
    }


    @Override
    public void run() {
        while (!rentalRequests.isEmpty()) {
            while (currentShiftLength < 8) { // min 8 hour shifts..
                try {
                    currentRequest = rentalRequests.take(); // pull rental request from queue

                    synchronized (currentRequest) {
                        Asset asset = assets.find(currentRequest);
                        asset.book();
                        travelToAsset(asset); // wasting time
                        currentRequest.fulfill(asset);
                        nUnhandledRequests.getAndDecrement(); // decrement unhandled requests count by one, safely..
                        notify(); // asset is booked, request fulfilled. notify customers.
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try { // done for today. waiting for a new shift to begin.
                barrier.await();
                beginShift(); // done waiting. back to work.
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private void travelToAsset(Asset asset) throws InterruptedException {
        long timeToSleep = clerkDetails.distanceToTravel(asset);
        currentShiftLength += timeToSleep;
        Thread.sleep(timeToSleep * SEC_TO_MILL); // sleep at the wheel
    }

    private void beginShift() { // i hate mondays
        currentShiftLength = 0;
    }
}
