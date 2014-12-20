/**
 * Created by airbag on 12/9/14.
 */
public class AssetContent{
    private String name;
    private double health;
    private int repairCostMultiplier;
    private final int HEALTHY_CONTENT = 65;

    public AssetContent(String contentName, int repairCostMultiplier){
        name = contentName;
        health = 100;
        this.repairCostMultiplier = repairCostMultiplier;
    }

    public void reduceHealth(double percentage){
        health -= percentage;
    }

    public void heal(){
        health = 100;
    }

    public boolean isHealthy(){
        return health >= HEALTHY_CONTENT;
    }

    public String toString(){
        return "the content name is " + name + " and multiplier is " + repairCostMultiplier;
    }

    public String name() { return name; }
}
