/**
 * Created by airbag on 12/9/14.
 */
public class RentalRequest {

    // fields
    private final int DAYS_TO_MILLISECONDS = 24000;
    private enum RequestStatus {INCOMPLETE, FULFILLED, INPROGRESS, COMPLETE}
    private RequestStatus requestStatus;
    private String id;
    private String assetType;
    private int assetSize;
    private int durationOfStay;
    private Asset asset;

    public RentalRequest(String id, String assetType, int assetSize, int durationOfStay) {
        this.id = id;
        this.assetType = assetType;
        this.assetSize = assetSize;
        this.durationOfStay = durationOfStay;
        requestStatus = RequestStatus.INCOMPLETE;
    }

    public boolean isFulfilled() {
        return (requestStatus == RequestStatus.FULFILLED);
    }

    public void fulfill(Asset asset) {
        this.asset = asset;
        requestStatus = RequestStatus.FULFILLED;
    }

    public boolean isSuitable(Asset asset) {
        return asset.isSuitable(assetType, assetSize);
    }

    public void inProgress() {
        requestStatus = RequestStatus.INPROGRESS;
        asset.occupy();
    }

    public void complete() {
        asset.vacate();
        requestStatus = RequestStatus.COMPLETE;
    }

    public void incomplete() {
        requestStatus = RequestStatus.INCOMPLETE;
    }

    public int stay() {
        return durationOfStay * DAYS_TO_MILLISECONDS;
    }

    public void updateDamage(double damagePercentage) {
        asset.updateDamage(damagePercentage);
    }

    public DamageReport createDamageReport(double totalDamage) {
        return new DamageReport(asset, totalDamage);
    }

    public String report() {
        return "[ Request ID: " +
                id + " ; Asset Type: " +
                assetType + " ; Asset Size: " +
                assetSize + " ; Duration:" +
                durationOfStay + " ; Status: " +
                requestStatus.toString() + " ] \n";
    }

    public String requestId() {
        return id;
    }

}
