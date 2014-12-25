import java.util.concurrent.Callable;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Created by airbag on 12/9/14.
 */
class CallableSimulateStayInAsset implements Callable {

    // fields

    private RentalRequest rentalRequest;
    private Customer customer;

    public CallableSimulateStayInAsset(RentalRequest rentalRequest, Customer customer) {
        this.rentalRequest = rentalRequest;
        this.customer = customer;
    }

    @Override
    public Object call() throws Exception {
        Management.logger.info(customer.getCustomerName() + " is staying for " + rentalRequest.stay() / 24000 + " days.");
        Thread.sleep(rentalRequest.stay()); // simulate stay

        return retrieveDamagePercentage();
    }

    // retrieve damage inflicted percentage,
    // calculated by customer.
    private Double retrieveDamagePercentage() {
        return customer.vandalize();
    }
}
