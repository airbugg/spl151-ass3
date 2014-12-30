package reit;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gal on 12/8/2014.
 */
class RepairMaterial implements Comparable<RepairMaterial> {
    private String materialName;
    private Semaphore quantity;
    private AtomicInteger totalAcquired;


    RepairMaterial(String materialName, int quantity) {
        this.materialName = materialName;
        this.quantity = new Semaphore(quantity);

    }

    void acquireMaterial (int quantity) {
        try {
            this.quantity.acquire(quantity);
            totalAcquired.addAndGet(quantity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    String getName() {
        return materialName;
    }

    public int getTotalAcquired(){ return totalAcquired.get();}

    int getQuantity() {
        return quantity.availablePermits();
    }


    @Override
    public int compareTo(RepairMaterial o) {
        return materialName.compareTo(o.materialName);
    }
}
