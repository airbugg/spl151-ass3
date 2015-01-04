package reit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Statistics:
 * This class keeps simulator's given-time statistics about:
 * 1) how much money was gained soo far
 * 2) amount of tools and materials used soo far
 * 3) fulfilled rental requests and their information
 *
 * to our enjoyment
 *
 * Created by airbag on 12/9/14.
 */
public class Statistics {
    private Warehouse warehouse;
    private AtomicInteger MoneyGained;
    private ArrayList<RentalRequest> rentalRequestsFulfilled;

    /**
     * default constructor
     * @param warehouse - the main warehouse of the program which we who's statistics we'll need to print statistics
     */
    public Statistics(Warehouse warehouse){
        this.warehouse = warehouse;
        rentalRequestsFulfilled = new ArrayList<RentalRequest>();
        MoneyGained = new AtomicInteger(0);
    }

    /**
     *
     * @param request- the request fulfilled
     */
    public synchronized void addFulfilledRequest(RentalRequest request) {
        rentalRequestsFulfilled.add(request);
    }


    /**
     *
     * @return- StringBuilder of fulfilled requests and their information
     */
    private StringBuilder showFulfilledRentalRequests(){
        StringBuilder requests = new StringBuilder("[RENTAL REQUESTS STATISTICS]\n\n");
        for(RentalRequest rentalRequest : rentalRequestsFulfilled) {
            requests.append(rentalRequest.getId());
            requests.append("\t\t");
            requests.append(rentalRequest.getCustomerManagerName());
            requests.append("\tfor asset ");
            requests.append(rentalRequest.getAssetName());
            requests.append("\tFulfilled at ");
            requests.append(rentalRequest.getRequestFulfilmentTime());
            requests.append("\tby ");
            requests.append(rentalRequest.getClerkName());
            requests.append("\n");
        }
        return requests;
    }

    /**
     *
     * @param income- the income needed to sum for the total income of the program
     */
    void addIncome(int income){
        MoneyGained.addAndGet(income);
    }

    /**
     * prints the current: 1) total money gained during the simulator
     *                     2) the amount of tools and materials used so far in the simulator
     *                     3) the information of the requests fulfilled so far
     */
    public String toString(){
        StringBuilder currentStatistics = new StringBuilder();
        currentStatistics.append("\n[TOTAL INCOME]\n[");
        currentStatistics.append(MoneyGained.toString());
        currentStatistics.append(" NIS]");
        currentStatistics.append(warehouse.WarehouseStatistics());
        currentStatistics.append("\n\n");
        currentStatistics.append(showFulfilledRentalRequests());

        return new String(currentStatistics);
    }
}
