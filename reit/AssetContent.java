package reit;

/**
 * Created by airbag on 12/9/14.
 */
class AssetContent {
    private final int DAMAGE_THRESHOLD = 65;
    private String name;
    private double health;
    private double repairCostMultiplier;

    public AssetContent(String contentName, double repairCostMultiplier) {
        name = contentName;
        health = 100;
        this.repairCostMultiplier = repairCostMultiplier;
    }

    public void breakAsset(double percentage) {
        health -= percentage;
    }

    public void fix() {
        health = 100;
    }

    public double timeToFix() {
        return (100 - health) * repairCostMultiplier;
    }
    public boolean isBroken() {
        return health <= DAMAGE_THRESHOLD;
    }

    public String getName() {
        return name;
    }
    public String toString() {
        return "[Content=" + name + "][Health=" + health + "][RepairMultiplier=" + repairCostMultiplier + "]";
    }
}