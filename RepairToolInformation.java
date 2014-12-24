/**
 * Created by airbag on 12/9/14.
 */
public class RepairToolInformation implements Comparable<RepairToolInformation>{

    // fields
    private String name;
    private int quantity;

    public RepairToolInformation(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public int compareTo(RepairToolInformation o) {
        return name.compareTo(o.name);
    }
}
