import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;


public class Assets {

    private ArrayList<Asset> assets;
    private ArrayBlockingQueue<DamageReport> damageReports;

    public Assets() {
        this.assets = new ArrayList<Asset>();
    }

    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    public Asset find(String type, int size) {

        for (Asset asset : assets) {
            if (asset.isSuitable(type,size))
                return asset;
        }
        return null;
    }

    public void addDamageReport(DamageReport damageReport) { // adds another damageReport to
        damageReports.add(damageReport);
    }

    public Iterator<DamageReport> getDamageReports() { // returns iterator for damageReport list
        return damageReports.iterator();
    }



}
