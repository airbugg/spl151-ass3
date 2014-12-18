import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;


public class Assets {

    ArrayList<Asset> assets;
    ArrayBlockingQueue<DamageReport> damageReports;

    public Assets(ArrayList<Asset> assets) {
        this.assets = assets;
    }

    public Asset find(String type, int size) {

        for (Asset asset : assets) {
            if (asset.isSuitable(type,size))
                return asset;
        }
        return null;
    }

    public void addDamageReport(DamageReport damageReport) {
        damageReports.add(damageReport);
    }

    public Iterator<DamageReport> getDamageReports() {
        return damageReports.iterator();
    }



}
