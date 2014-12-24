

public class RepairTool implements Comparable<RepairTool> {

    private String name;
    public int quantity;

    public RepairTool(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public String toString() { return "[" + name + " - " + quantity + "]" ;}

    @Override
    public int compareTo(RepairTool o) {
        return name.compareTo(o.name);
    }
}
