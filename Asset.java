import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by airbag on 12/9/14.
 */
public class Asset {

    private String name;
    private String	type;
    private Location location;
    private LinkedList<AssetContent> assetContentContainer;
    private enum Status {AVAILABLE, BOOKED, OCCUPIED, UNAVAILABLE};
    private	Status status;
    private int costPerNight;
    private int size;

    public Asset(String name, String type, Location location,
                 int costPerNight, int size) {
        this.name = name;
        this.type = type;
        this.location = location;
        assetContentContainer = new LinkedList<AssetContent>();
        status = Status.AVAILABLE;
        this.costPerNight = costPerNight;
        this.size = size;
    }

    public void addContent(AssetContent assetContent) {
        assetContentContainer.add(assetContent);
    }

    public boolean isWrecked () {
        return !(status == Status.UNAVAILABLE);
    }

    public void updateDamage(double percentage){
        ListIterator<AssetContent> it = assetContentContainer.listIterator();
        while(it.hasNext()){
            it.next().reduceHealth(percentage);
        }
        checkHealth();
    }

    private void checkHealth() {
        boolean stillAvailable = true;
        ListIterator<AssetContent> it = assetContentContainer.listIterator();

        while(it.hasNext() && stillAvailable){
            if(!it.next().isHealthy()){
                status = Status.UNAVAILABLE;
                stillAvailable = false;
            }
        }
    }

    public String whatsDamaged(){
        String damagedGoods = "";
        ListIterator<AssetContent> it = assetContentContainer.listIterator();

        while(it.hasNext()){
            if(!it.next().isHealthy()){
                it.previous();
                damagedGoods += it.next().name() + ",";
            }
        }
        if(damagedGoods.length() > 0){
            damagedGoods = damagedGoods.substring(0,damagedGoods.length()-1);
        }
        return damagedGoods;
    }

    public boolean isSuitable(String type, int size){
        return (this.type == type &&
                this.size >= size &&
                this.status == Status.AVAILABLE);
    }

    public long distanceToClerk (Location location) {
        return (Math.round(this.location.calculateDistance(location)));
    }

    public void book() {
        status = Status.BOOKED;
    }

    public void occupy() {
        status = Status.OCCUPIED;
    }

    public void vacate() {
        status = status.AVAILABLE;
    }
}