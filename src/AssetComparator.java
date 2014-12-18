import java.util.Comparator;

/**
 * Created by gal on 12/13/2014.
 */
public class AssetComparator implements Comparator<Asset>{

    public AssetComparator(){}


    @Override
    public int compare(Asset asset1, Asset asset2) {
        if (asset1.size() < asset2.size()){
            return -1;
        }
        else if (asset1.size() > asset2.size()){
            return 1;
        }
        else{
            return 0;
        }
    }
}
