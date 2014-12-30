package reit;

/**
 * Created by airbag on 12/9/14.
 */
class RepairMaterialInformation implements Comparable<RepairMaterialInformation> {

    private String name;
    private int quantity;

    RepairMaterialInformation(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    String getName() {
        return name;
    }

    int getQuantity() {
        return quantity;
    }

    @Override
    public int compareTo(RepairMaterialInformation o) {
        if (name.compareTo(o.name) == 0) {
            return quantity - o.quantity;
        }
        return name.compareTo(o.name);
    }
}
