import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/**
 * Created by airbag on 12/9/14.
 */
public class CustomerGroupDetails {

    private String groupManagerName;
    private Queue<RentalRequest> rentalRequests;
    private Vector<Customer> customers;


    public CustomerGroupDetails(String groupManagerName) {
        this.groupManagerName = groupManagerName;
        this.customers = new Vector<Customer>();
        this.rentalRequests = new LinkedList<RentalRequest>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);

    }

    public void addRentalRequest(RentalRequest rentalRequest) {
        rentalRequests.add(rentalRequest);

    }

    public RentalRequest pullRentalRequest() {
        if (!rentalRequests.isEmpty())
            return rentalRequests.remove();
        return null;
    }

    public boolean isRequestsLeft() {
        return !rentalRequests.isEmpty();
    }

    public Iterator<Customer> customerIterator(){
        return customers.iterator();
    }

    public int numOfCustomers() {
        return customers.size();

    }

    public String toString() {

        StringBuilder stringBuilder = new StringBuilder("This CustomerGroupDetails object contains: \n");

        stringBuilder.append("Group Manager name: ");
        stringBuilder.append(groupManagerName);
        stringBuilder.append("\nRental Requests: \n");

        for (RentalRequest rentalRequest : rentalRequests) {
            stringBuilder.append(rentalRequest);
        }

        stringBuilder.append("Customers:\n");

        for (Customer customer : customers) {
            stringBuilder.append(customer);
        }

        return stringBuilder.toString();
    }
}
