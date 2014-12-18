import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by airbag on 12/9/14.
 */
public class Assets {
    private PriorityQueue<Asset> assets;

    public Assets(){
        assets = new PriorityQueue<Asset>(10,new AssetComparator());
    }

    public void addAsset(Asset asset){
        assets.add(asset);
    }

    public LinkedList<Asset> damagedAssets(){
        return null;
    }

    public Asset findAsset(String type, int size){
        return null;
    }


    public void printQueue() {
        Iterator<Asset> it = assets.iterator();
        while(it.hasNext()){
            System.out.println(it.next().size());
        }
    }
}
