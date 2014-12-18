/**
 * Created by airbag on 12/9/14.
 */
public class AssetContent{
    private String Name;
    private double Health;
    private int RepairCostmultiplier;
    private final int HEALTHYCONTENT = 65;

    public AssetContent(String contentName, int RepairCostmultiplier){
        Name = contentName;
        Health = 100;
        this.RepairCostmultiplier = RepairCostmultiplier;
    }

    public AssetContent(AssetContent other){
        Name = other.Name;
        Health = other.Health;
        RepairCostmultiplier = other.RepairCostmultiplier;
    }

    public void reduceHealth(double precentage){
        Health -= precentage;
    }

    public void Heal(){
        Health = 100;
    }

    public boolean isHealthy(){
        return Health >= HEALTHYCONTENT;
    }

    public String toString(){
        return "the content name is " + Name + " and miltiplier is " + RepairCostmultiplier;
    }

    public String Name() { return Name;}

    public double timeToSleep(){ return (100-Health)*RepairCostmultiplier; }
}
