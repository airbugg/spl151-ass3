import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;


/**
 * Created by airbag on 12/9/14.
 */
class RunnableClerk implements Runnable {

    private final int SEC_TO_MILL = 1000;
    private double shiftLength;
    private ClerkDetails clerkDetails;
    private BlockingQueue<RentalRequest> rentalRequests;
    private RentalRequest currentRequest;
    private Assets assets;
    private CyclicBarrier barrier;
    private AtomicInteger nUnhandledRequests;
    private Semaphore reportSemaphore;
    private Object beginNewShift;

    public RunnableClerk(ClerkDetails clerkDetails,
                         BlockingQueue<RentalRequest> rentalRequests,
                         CyclicBarrier barrier,
                         Assets assets,
                         AtomicInteger nUnhandledRequests,
                         Semaphore reportSemaphore,
                         Object beginNewShift) {
        this.clerkDetails = clerkDetails;
        this.rentalRequests = rentalRequests;
        this.barrier = barrier;
        this.assets = assets;
        this.currentRequest = null;
        this.nUnhandledRequests = nUnhandledRequests;
        this.reportSemaphore = reportSemaphore;
        this.beginNewShift = beginNewShift;
    }


    @Override
    public void run() {
        Management.logger.info(clerkDetails.toString() + " is alive!!");
        while (nUnhandledRequests.get() > 0) {
            Management.logger.info(clerkDetails + ": begins new shift.");
            while (shiftLength < 8) { // 8 hour shifts..
                try {
                    currentRequest = rentalRequests.take(); // pull rental request from queue
                    Management.logger.info(clerkDetails + ": started handling request: " + currentRequest.requestId());
                    synchronized (currentRequest) {
                        Management.logger.info(clerkDetails + ": looking for suitable Asset for request # " + currentRequest.requestId());
                        Asset asset = assets.find(currentRequest);
                        Management.logger.info(clerkDetails + ": found a suitable Asset: " + asset + " for request # " +
                                currentRequest.requestId() + ". Booking!");
                        asset.book();
                        Management.logger.info(clerkDetails + ": Asset Booked! Traveling to asset..");
                        travelToAsset(asset); // wasting time
                        currentRequest.fulfill(asset);
                        reportSemaphore.release(1);
                        nUnhandledRequests.getAndDecrement(); // decrement unhandled requests count by one, safely..
                        currentRequest.notifyAll(); // asset is booked, request fulfilled. notify customers.
                        Management.logger.info(clerkDetails + ": Arrived. Request fulfilled. notifying Group Manager.");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Management.logger.info(clerkDetails + ": off-duty. let's get hammered!");
                barrier.await(); // done for today. waiting for everyone else to finish so that I, too, could go home..

                synchronized (beginNewShift) { // praying next shift won't be as bad as the last.
                    Management.logger.info(clerkDetails + ": Waiting for Management to schedule new shift..");
                    beginNewShift.wait();

                    Management.logger.info(clerkDetails + ": I'M UP, I'M UP!!!");
                    beginShift(); // crap.
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        Management.logger.info(clerkDetails + ": NO MORE REQUESTS LEFT TO HANDLE! I QUIT!");
    }

    private void travelToAsset(Asset asset) throws InterruptedException {
        long timeToSleep = clerkDetails.distanceToTravel(asset) * 2;
        Management.logger.info(clerkDetails + ": Traveling for " + timeToSleep + " Miles.");
        shiftLength += timeToSleep;
        Thread.sleep(timeToSleep * SEC_TO_MILL); // sleep at the wheel, to and fro
    }

    private void beginShift() { // i hate mondays
        shiftLength = 0;
    }
}
