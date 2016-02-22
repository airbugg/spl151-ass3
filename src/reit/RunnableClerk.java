package reit;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by airbag on 12/9/14.
 */
class RunnableClerk implements Runnable {

    private double shiftLength;
    private ClerkDetails clerkDetails;
    private BlockingQueue<RentalRequest> rentalRequests;
    private RentalRequest currentRequest;
    private Assets assets;
    private CyclicBarrier barrier;
    private AtomicInteger nUnhandledRequests;
    private Semaphore reportSemaphore;
    private Object beginNewShift;
    private CountDownLatch nClerksWorking;

    RunnableClerk(ClerkDetails clerkDetails,
                         BlockingQueue<RentalRequest> rentalRequests,
                         CyclicBarrier barrier,
                         Assets assets,
                         AtomicInteger nUnhandledRequests,
                         Semaphore reportSemaphore,
                         Object beginNewShift,
                         CountDownLatch nClerksWorking) {
        this.clerkDetails = clerkDetails;
        this.rentalRequests = rentalRequests;
        this.barrier = barrier;
        this.assets = assets;
        this.currentRequest = null;
        this.nUnhandledRequests = nUnhandledRequests;
        this.reportSemaphore = reportSemaphore;
        this.beginNewShift = beginNewShift;
        this.nClerksWorking = nClerksWorking;
    }


    @Override
    public void run() {
        while (nUnhandledRequests.getAndDecrement() > 0) { // thread safety is a must. decrement request count by 1 per iteration.
            while (shiftLength < 8) { // 8 hour shifts..
                Management.LOGGER.info(clerkDetails.getName() + " is up!");
                try {
                    currentRequest = rentalRequests.take(); // pull rental request from queue
                    synchronized (currentRequest) {
                        Asset asset = assets.find(currentRequest, clerkDetails);
                        asset.book();
                        Management.LOGGER.info(asset.getName() + " was booked by " + clerkDetails.getName()); // TODO: update statistics (id, asset)
                        travelToAsset(asset); // wasting time
                        currentRequest.fulfill(clerkDetails, asset);
                        reportSemaphore.acquire(1);
                        currentRequest.notifyAll(); // asset is booked, request fulfilled. notify customers.
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Management.LOGGER.info(clerkDetails.getName() + " is off-duty.");
                barrier.await(); // done for today. waiting for everyone else to finish so that I, too, could go home..

                synchronized (beginNewShift) { // praying next shift won't be as bad as the last.
                    beginNewShift.wait();
                    beginShift(); // crap.
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        Management.LOGGER.info(clerkDetails.getName() + ": NO RENTAL REQUESTS LEFT. TERMINATING...");
        nClerksWorking.countDown();
    }

    private void travelToAsset(Asset asset) throws InterruptedException {
        long timeToSleep = clerkDetails.distanceToTravel(asset) * 2;
        Management.LOGGER.info(clerkDetails.getName() + " is traveling for " + timeToSleep + " miles.");
        shiftLength += timeToSleep;
        Thread.sleep(timeToSleep * Management.SEC_TO_MILL); // sleep at the wheel, to and fro
    }

    private void beginShift() { // i hate mondays
        shiftLength = 0;
    }
}
