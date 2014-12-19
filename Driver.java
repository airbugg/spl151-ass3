import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by airbag on 12/9/14.
 */
public class Driver {

    public void init(){


        Location location = new Location(3,3);
        LinkedList<Customer> customers = new LinkedList<Customer>();

        customers.add(new Customer("Benny", Customer.VandalismType.ARBITRARY, 1, 10));
        customers.add(new Customer("Ester", Customer.VandalismType.FIXED,1,10));
        customers.add(new Customer("Nick", Customer.VandalismType.NONE,1,10));

        BlockingQueue<RentalRequest> rentalRequests = new LinkedBlockingQueue<RentalRequest>();
        Management management = new Management();

        rentalRequests.add(new RentalRequest("1","Caravan",1,1));

        CustomerGroupDetails customerGroup = new CustomerGroupDetails("Nick",customers,rentalRequests);

        RunnableClerk clerk = new RunnableClerk(new ClerkDetails("Oz",location),management.rentalRequests);

        RunnableCustomerGroupManager manager = new RunnableCustomerGroupManager(management,customerGroup,rentalRequests);

        Thread clerkThread = new Thread(clerk,"something");
        Thread groupManager = new Thread(manager,"something");
        System.out.println("Starting..");
        clerkThread.start();
        groupManager.start();

    }

    public static void main(String[] args) throws Exception {

        Warehouse warehouse = Parser.parseInitialData("InitialData.xml");
        System.out.println("Finished parsing Warehouse object. Warehouse contains the following: ");

        for (Map.Entry<String, RepairTool> entry : warehouse.tools.entrySet()) {
            System.out.println(entry.getValue());
        }

        for (Map.Entry<String, RepairMaterial> entry : warehouse.materials.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
