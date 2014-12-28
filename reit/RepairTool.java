package reit;

import java.util.concurrent.Semaphore;

class RepairTool implements Comparable<RepairTool> {

    private Semaphore quantity;
    private String name;

    RepairTool(String name, int quantity) {
        this.name = name;
        this.quantity = new Semaphore(quantity);
    }

    void acquireTool(int quantity) {
        try {
            this.quantity.acquire(quantity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void releaseTool(int amount) {
        quantity.release(amount);
    }

    String getName() {
        return name;
    }

    int getQuantity() {
        return quantity.availablePermits();
    }

    public String toString() {
        return "[" + name + " - " + quantity + "]";
    }

    @Override
    public int compareTo(RepairTool o) {
        return name.compareTo(o.name);
    }
}
