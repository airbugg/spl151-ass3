/**
 * Created by gal on 12/8/2014.
 */
public interface WarehouseInterface {


    void addRepairTool(String toolName, int quantity);
    void addRepairMaterial(String materialName, int quantity);
    void rentATool(String toolName, int quantity);
    void takeMaterial(String materialName, int quantity);
    void releaseTool(String toolName, int quantityS);
}
