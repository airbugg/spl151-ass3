import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;


public class Assets {

    private ArrayList<Asset> assets;
    private ArrayBlockingQueue<DamageReport> damageReports;
    private Object assetLock;

    public Assets() {
        this.assets = new ArrayList<Asset>();
        assetLock = new Object();
    }

    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    public Asset find(RentalRequest rentalRequest) {
        boolean assetFound = false;
        Asset suitableAsset = null;
        synchronized (assetLock) {
            while (!assetFound) { // while no suitable asset has been found
                for (Asset asset : assets) { // look for assets
                    if (rentalRequest.isSuitable(asset)) { // if suitable asset has been found
                        assetFound = true;
                         suitableAsset = asset;
                    }
                }
                try { // no available assets have been found
                    assetLock.wait(); // so we wait.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return suitableAsset;
    }

    public void submitDamageReport(DamageReport damageReport) { // adds another damageReport to
        damageReports.add(damageReport);
        assetLock.notifyAll(); // let everyone know a new asset is now available
    }

    public ArrayList<Asset> getDamagedAssets() {
        ArrayList<Asset> damagedProperty = new ArrayList<Asset>();

        for (Asset asset : assets) { // TODO: figure out where the hell we should use damageReport..
            if (asset.isWrecked())
                damagedProperty.add(asset);
        }

        return damagedProperty;
    }

    public void applyDamage() { // iterate over damage reports, inflict damage
        for (DamageReport damageReport : damageReports) {
            damageReport.inflictDamage();
        }

        damageReports.clear();
    }
}