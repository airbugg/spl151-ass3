import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class AssetsTest {

    Assets assets;

    @Before
    public void setUp() throws Exception {
        assets = new Assets();
        assets.addAsset(new Asset("gal's crib", "crib", 1 ,1 , AssetTest.generateContentList(), 500,5));
        assets.addAsset(new Asset("fistuk","lounge",23,12,AssetTest.generateContentList(),700,2));
        assets.addAsset(new Asset("hostelWorld", "lounge", 20, 122, AssetTest.generateContentList(), 600, 4));


    }

    @Test
    public void testAddAsset() throws Exception {
        assets.printQueue();
        assets.addAsset(new Asset("gal's crib", "crib", 1 ,1 , AssetTest.generateContentList(), 500,90));
        System.out.println("\n");
        assets.printQueue();

    }

    @Test
    public void testDamagedAssets() throws Exception {

    }

    @Test
    public void testFindAsset() throws Exception {

    }
}