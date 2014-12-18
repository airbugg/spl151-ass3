
public class RepairMaterial {

    private String name;
    private int quantity;

    public RepairMaterial(String name, int quantity){ // constructor
        this.name = name;
        this.quantity = quantity;
    }

    public String toString() { return "[" + name + " - " + quantity + "]" ;}
}
