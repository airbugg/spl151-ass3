/**
 * Created by airbag on 12/9/14.
 */
public class RepairMaterialInformation implements Comparable<RepairMaterialInformation>{

    private String name;
    private int quantity;

    public RepairMaterialInformation(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public int compareTo(RepairMaterialInformation o) {
        return name.compareTo(o.name);
    }
}
