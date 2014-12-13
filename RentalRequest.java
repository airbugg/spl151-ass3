/**
 * Created by airbag on 12/9/14.
 */
public class RentalRequest {

    // fields
    final int DAYS_TO_MILLISECONDS = 24000;

    private enum RequestStatus { INCOMPLETE, FULFILLED, INPROGRESS, COMPLETE };
    private RequestStatus requestStatus;
    private String Id;
    private String assetType;
    private int assetSize;
    private int durationOfStay;
    private Asset asset;

    public RentalRequest(String Id, String assetType, int assetSize, int durationOfStay) {
        this.Id = Id;
        this.assetType = assetType;
        this.assetSize = assetSize;
        this.durationOfStay = durationOfStay;
        requestStatus = RequestStatus.INCOMPLETE;
    }

    public boolean isFulfilled() {
        return (requestStatus==RequestStatus.FULFILLED);
    }

    public void fulfill(Asset asset){
        this.asset = asset;
        requestStatus = RequestStatus.FULFILLED;
    }

    public void inProgress() { requestStatus = RequestStatus.INPROGRESS; }

    public void complete() { requestStatus = RequestStatus.COMPLETE; }

    public void incomplete() { requestStatus = RequestStatus.INCOMPLETE; }

    public int stay(){
        return durationOfStay * DAYS_TO_MILLISECONDS;
    }

    public void updateDamage(double damagePercentage) {
        asset.updateDamage(damagePercentage);
    }
}
