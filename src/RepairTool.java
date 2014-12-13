import java.lang.String;

/**
 * Created by gal on 12/8/2014.
 */
public class RepairTool {

    private String toolName;
    private int totalQuantity;
    private int currentQuantity;
    private StringBuilder currentStatus;

    public RepairTool(String toolName, int quantity){
        this.toolName = toolName;
        totalQuantity = quantity;
        currentQuantity = quantity;
        currentStatus = new StringBuilder();
    }

    public synchronized void rentATool(int quantity){
        if(quantity > this.currentQuantity){
            totalQuantity += quantity - this.currentQuantity;
            currentQuantity += quantity - this.currentQuantity;
        }
        currentQuantity -= quantity;
    }

    public synchronized void releaseTool(int quantity){
        currentQuantity += quantity;
    }


    public String toolNeme(){
        return toolName;
    }

    public int currentQuantity(){
        return currentQuantity;
    }

    public int totalQuantity(){
        return totalQuantity;
    }

    public synchronized String toString(){
        currentStatus.delete(0,currentStatus.length());
        currentStatus.append(" current amount of " + toolName + " used int the simulator is " + (totalQuantity - currentQuantity));
        return String.valueOf(currentStatus);
    }


}
