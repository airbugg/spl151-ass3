/**
 * Created by gal on 12/17/2014.
 */
class RepairInformation {
    private String fName;
    private int fQuantity;

    public RepairInformation(String Name, int quantity) {
        fName = Name;
        fQuantity = quantity;
    }

    public String Name() {
        return fName;
    }

    public int quantity() {
        return fQuantity;
    }


}
