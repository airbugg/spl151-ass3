package reit;

import java.util.concurrent.Callable;

/**
 * Created by airbag on 12/9/14.
 */
class CallableSimulateStayInAsset implements Callable {

    // fields

    private final RentalRequest rentalRequest;
    private final Customer customer;

    CallableSimulateStayInAsset(RentalRequest rentalRequest, Customer customer) {
        this.rentalRequest = rentalRequest;
        this.customer = customer;
    }

    @Override
    public Object call() throws Exception {
        Thread.sleep(rentalRequest.stay()); // simulate stay
        return retrieveDamagePercentage();

    }

    // retrieve damage inflicted percentage,
    // calculated by customer.
    private Double retrieveDamagePercentage() {
        return customer.vandalize();
    }
}
