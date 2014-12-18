import java.util.HashMap;


public class Warehouse {

    public HashMap<String,RepairTool> tools;

    public HashMap<String,RepairMaterial> materials;


    public Warehouse(HashMap<String, RepairTool> tools, HashMap<String,RepairMaterial> materials) {
        this.tools = tools;
        this.materials = materials;
    }
}
