/**
 * Created by gal on 12/8/2014.
 */
public class RepairMaterial implements Comparable<RepairMaterial>{
    private String materialName;
    private int totalQuantity;
    private int currentQuantity;
    private StringBuilder currentStatus;



    public RepairMaterial(String materialName, int quantity){
        this.materialName = materialName;
        totalQuantity = quantity;
        currentQuantity = quantity;
        currentStatus = new StringBuilder();

    }

    public synchronized void takeMaterial(int quantity) {
        if (quantity > currentQuantity) {
            totalQuantity += quantity - currentQuantity;
            currentQuantity += quantity - currentQuantity;
        }
        currentQuantity -= quantity;
    }


    public String materialName(){
        return materialName;
    }

    public int currentQuantity(){
        return currentQuantity;
    }

    public int totalQuantity() { return  totalQuantity;  }



    public synchronized String toString(){
        currentStatus.delete(0,currentStatus.length());
        currentStatus.append("the current quantity of " + materialName + " beeing used is " + (totalQuantity - currentQuantity));
        return String.valueOf(currentStatus);
    }

    @Override
    public int compareTo(RepairMaterial o) {
        return materialName.compareTo(o.materialName);
    }
}
