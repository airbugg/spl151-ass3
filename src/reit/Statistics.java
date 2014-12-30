package reit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by airbag on 12/9/14.
 */
public class Statistics {
    private Warehouse warehouse;
    private AtomicInteger MoneyGained;
    private HashMap<String,RentalRequest> rentalRequestsFulfilled;

    /**
     * default constructor
     * @param warehouse - the
     */
    public Statistics(Warehouse warehouse){
        this.warehouse = warehouse;
        rentalRequestsFulfilled = new HashMap<String, RentalRequest>();
        MoneyGained = new AtomicInteger(0);
    }

    public  void showWarehouseStatistics(){
        synchronized(System.out){
            warehouse.printStatistics();
        }
    }

    /**
     * 
     * @return
     */
    public synchronized void addFulfilledRequest(String id, RentalRequest request) {
        rentalRequestsFulfilled.put(id,request);
    }


    public StringBuilder showFulfilledRentalRequests(){
        StringBuilder requests = new StringBuilder();
        for(Map.Entry<String,RentalRequest> entry : rentalRequestsFulfilled.entrySet()){
            requests.append("request id: " + entry.getKey() + " was fulfilled by the asset: " + entry.getValue().getAssetName());
        }
        return requests;
    }

    public void addIncome(int income){
        MoneyGained.addAndGet(income);
    }

    public void printStatistics(){
            System.out.println("Current income summed is " + MoneyGained.toString());
            showWarehouseStatistics();
            System.out.println(showFulfilledRentalRequests());
    }

}
