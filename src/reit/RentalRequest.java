package reit;

import java.sql.Time;
import java.time.LocalTime;

/**
 * Created by airbag on 12/9/14.
 */
class RentalRequest {



    // fields
    private enum RequestStatus {INCOMPLETE, FULFILLED, INPROGRESS, COMPLETE}
    private RequestStatus requestStatus;
    private final CustomerGroupDetails fCustomerGroupDetails;
    private ClerkDetails fClerkDetails;
    private final String id;
    private final String assetType;
    private final int assetSize;
    private final int durationOfStay;
    private Asset asset;
    private Time fTime;

    RentalRequest(String id, String assetType, int assetSize, int durationOfStay,
                  CustomerGroupDetails customerGroupDetails) {
        this.id = id;
        this.assetType = assetType;
        this.assetSize = assetSize;
        this.durationOfStay = durationOfStay;
        this.fCustomerGroupDetails = customerGroupDetails;
        this.fClerkDetails = null;
        this.fTime = null;
        requestStatus = RequestStatus.INCOMPLETE;
    }

    boolean isFulfilled() {
        return (requestStatus == RequestStatus.FULFILLED);
    }

    String getAssetName(){ return asset.getName();}

    String getCustomerManagerName() { return fCustomerGroupDetails.getName(); }

    String getClerkName() { return fClerkDetails.getName(); }

    String getRequestFulfilmentTime() { return "[" + fTime.toString() + "]"; }

    void fulfill(ClerkDetails clerkDetails, Asset asset) {
        this.asset = asset;
        this.fClerkDetails = clerkDetails;
        this.fTime = Time.valueOf(LocalTime.now());
        requestStatus = RequestStatus.FULFILLED;
        Management.LOGGER.info(getId() + " status changed to FULFILLED.");
    }

    int calculateCost() {
        return durationOfStay*asset.getCostPerNight()*fCustomerGroupDetails.numOfCustomers();
    }

    boolean isSuitable(Asset asset) {
        return asset.isSuitable(assetType, assetSize);
    }

    void inProgress() {
        requestStatus = RequestStatus.INPROGRESS;
        asset.occupy();
        Management.LOGGER.info(getId() + " status changed to IN PROGRESS.");
    }

    void complete() {
        asset.vacate();
        requestStatus = RequestStatus.COMPLETE;
        Management.LOGGER.info(getId() + " status changed to COMPLETE.");
    }

    int stay() {
        return durationOfStay * Management.DAYS_TO_MILLISECONDS;
    }

    DamageReport createDamageReport(double totalDamage) {
        return new DamageReport(asset, totalDamage);
    }

    int getDurationOfStay () {
        return durationOfStay;
    }

    String getId() {
        return "[Rental Request " + id + "]";

    }
    public String toString() {
        return "[ID=" + id +
                "][Type=" + assetType +
                "][Size=" + assetSize +
                "][Duration=" + durationOfStay +
                "][Status=" + requestStatus.toString() +
                "]";
    }
}
