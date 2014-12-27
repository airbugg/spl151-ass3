package reit;

import java.util.concurrent.Semaphore;

class RepairTool implements Comparable<RepairTool> {

    private Semaphore quantity;
    private String name;

    public RepairTool(String name, int quantity) {
        this.name = name;
        this.quantity = new Semaphore(quantity);
    }

    public void acquireTool(int quantity) {
        try {
            this.quantity.acquire(quantity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void releaseTool(int amount) {
        quantity.release(amount);
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
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
