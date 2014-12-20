import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by airbag on 12/9/14.
 */
public class Driver {

    public static void main(String[] args) throws Exception {

        // parse XML
        Management management = Parser.createManagement("xml/InitialData.xml", "xml/Assets.xml");
        Parser.parseAssetContentRepairDetails(management, "xml/AssetContentsRepairDetails.xml");
        Parser.parseCustomersGroups(management, "xml/CustomersGroups.xml");

        System.out.println("Finished parsing files.");
        System.out.println(management);
    }
}
