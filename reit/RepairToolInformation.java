package reit;

/**
 * Created by airbag on 12/9/14.
 */
class RepairToolInformation implements Comparable<RepairToolInformation> {

    // fields
    private String name;
    private int quantity;

    public RepairToolInformation(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int compareTo(RepairToolInformation o) {
        if (name.compareTo(o.name) == 0) {
            return quantity - o.quantity;
        }
        return name.compareTo(o.name);
    }
}
