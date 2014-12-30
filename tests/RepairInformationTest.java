import org.junit.Before;
import reit.RepairInformation;

import java.util.LinkedList;

public class RepairInformationTest {


    private RepairInformation content;
    @Before
    public void setUp() throws Exception {
        content = new RepairMaterialInformation("stove",2);

    }

    public static LinkedList<RepairToolInformation> generateRepairToolInformationList() {
        LinkedList<RepairToolInformation> list = new LinkedList<RepairToolInformation>();
        list.addLast(new RepairToolInformation("driller",4));
        list.addLast(new RepairToolInformation("spoon",2));
        list.addLast(new RepairToolInformation("gun",8));
        return list;
    }

    public static LinkedList<RepairMaterialInformation> generateRepairMaterialInformationList() {
        LinkedList<RepairMaterialInformation> list = new LinkedList<RepairMaterialInformation>();
        list.addLast(new RepairMaterialInformation("kalsiom",4));
        list.addLast(new RepairMaterialInformation("sand",2));
        list.addLast(new RepairMaterialInformation("iron",8));
        return list;
    }
}