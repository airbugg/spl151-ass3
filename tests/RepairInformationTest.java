import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class RepairInformationTest {


    private RepairInformation content;
    @Before
    public void setUp() throws Exception {
        content = new RepairMaterialInformation("stove",2);

    }

    public static LinkedList<Pair> generatePairList() {
        LinkedList<Pair> list = new LinkedList<Pair>();
        list.addLast(new Pair("kalsimo",4));
        list.addLast(new Pair("sand",2));
        list.addLast(new Pair("iron",8));
        return list;
    }



    public static void printList(LinkedList list){
        for(Iterator it= list.listIterator() ; it.hasNext(); ){
            System.out.println(it.next().toString());
        }
    }
}