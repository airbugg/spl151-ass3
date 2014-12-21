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

    public static LinkedList<AssetContent> generateContentList() {
        LinkedList<AssetContent> list = new LinkedList<AssetContent>();
        list.addLast(new AssetContent("stove", 2));
        list.addLast(new AssetContent("microwave",7));
        return list;
    }

    @Test
    public void testUpdateDamage() throws Exception {
        asset.updateDamage(33);
        assertEquals("",asset.whatsDamaged());
        asset.updateDamage(33);
        assertEquals("stove,microwave",asset.whatsDamaged());
    }

    @Test
    public void testBrokenContentsList(){
        asset.updateDamage(70);
        LinkedList<AssetContent> list = asset.BrokenContentsList();
        ListIterator<AssetContent> it = list.listIterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
            }
        }
    }
