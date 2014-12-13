import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class AssetTest {

    private Asset asset;
    @Before
    public void setUp() throws Exception {
        LinkedList<AssetContent> contentList = new LinkedList<AssetContent>(generateContentList());
        asset = new Asset("gal's crib", "crib", 1 ,1 , contentList, 500, 5);
    }

    private LinkedList<AssetContent> generateContentList() {
        LinkedList<AssetContent> list = new LinkedList<AssetContent>();
        list.addLast(new AssetContent("stove", 2));
        list.addLast(new AssetContent("microwave",7));
        list.addLast(new AssetContent("fridge",22));
        return list;
    }

    @Test
    public void testUpdateDamage() throws Exception {
        asset.updateDamage(33);
        assertEquals("",asset.whatsDamaged());
        assertEquals("AVAILABLE",asset.status());
        asset.updateDamage(33);
        assertEquals("stove,microwave,fridge",asset.whatsDamaged());
        assertEquals("UNAVAILABLE",asset.status());
    }
}