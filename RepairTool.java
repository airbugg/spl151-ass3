

public class RepairTool {

    private String name;
    private int quantity;

    public RepairTool(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public String toString() { return "[" + name + " - " + quantity + "]" ;}

}
