import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by airbag on 12/9/14.
 */
public class Asset {

    private String name;
    private String type;
    private Location location;
    private LinkedList<AssetContent> assetContentContainer;
    private String status;
    private int costPerNight;
    private int size;

    public Asset(String name, String type, double x, double y, LinkedList<AssetContent> contentsList, int costPerNight, int size) {
        this.name = name;
        this.type = type;
        location = new Location(x, y);
        assetContentContainer = new LinkedList<AssetContent>(contentsList);
        status = "AVAILABLE";
        this.costPerNight = costPerNight;
        this.size = size;
    }

    public void updateDamage(double percentage) {
        ListIterator<AssetContent> it = assetContentContainer.listIterator();
        while (it.hasNext()) {
            it.next().reduceHealth(percentage);
        }
        checkHealth();

    }

    private void checkHealth() {
        boolean stillAvailable = true;
        ListIterator<AssetContent> it = assetContentContainer.listIterator();
        while (it.hasNext() && stillAvailable) {
            if (!it.next().isHealthy()) {
                status = "UNAVAILABLE";
                stillAvailable = false;
            }
        }
    }

    public String whatsDamaged() {
        String damagedGoods = "";
        ListIterator<AssetContent> it = assetContentContainer.listIterator();
        while (it.hasNext()) {
            if (!it.next().isHealthy()) {
                it.previous();
                damagedGoods += it.next().Name() + ",";
            }
        }
        if (damagedGoods.length() > 0) {
            damagedGoods = damagedGoods.substring(0, damagedGoods.length() - 1);
        }
        return damagedGoods;
    }

    public String status() {
        return status;
    }


    public int size() {
        return size;
    }

    public LinkedList<AssetContent> BrokenContentsList(){
        LinkedList<AssetContent> list = new LinkedList<AssetContent>();
        ListIterator<AssetContent> it = assetContentContainer.listIterator();
        while (it.hasNext()) {
            if (!it.next().isHealthy()) {
                it.previous();
                list.addLast(new AssetContent(it.next()));
            }
        }
        return list;
    }
}