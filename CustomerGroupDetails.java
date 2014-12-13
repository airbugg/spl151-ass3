import java.util.Iterator;
import java.util.LinkedList;

import java.util.Queue;

/**
 * Created by airbag on 12/9/14.
 */
public class CustomerGroupDetails {

    private String groupManagerName;
    private Queue<RentalRequest> rentalRequests;
    private LinkedList<Customer> customers;


    public CustomerGroupDetails(String groupManagerName, LinkedList<Customer> customers, Queue<RentalRequest> rentalRequests) {
        this.groupManagerName = groupManagerName;
        this.customers = customers;
        this.rentalRequests = rentalRequests;
    }

    public void addCustomer() {

    }

    public void addRentalRequest() {

    }

    public Iterator<Customer> customerIterator(){
        return customers.iterator();
    }

    public int numOfCustomers() {
        return customers.size();
    }

}
