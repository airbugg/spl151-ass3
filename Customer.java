/**
 * Created by airbag on 12/9/14.
 */
public class Customer {

    // fields
    public enum VandalismType { ARBITRARY, FIXED, NONE };
    private VandalismType vandalismType;
    private String name;
    private int minDamage;
    private int maxDamage;

    public Customer(String name, VandalismType vandalismType, int minDamage, int maxDamage) {
        this.name = name;
        this.vandalismType = vandalismType;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public double vandalize(){

        double damage = 0.5; // default wear&tear value

        switch (vandalismType) {

         case ARBITRARY: // generate random number in minDamage - maxDamage range
             damage = minDamage + Math.random() * (maxDamage - minDamage);
             break;

         case FIXED: // calc average
             damage = (minDamage + maxDamage) / 2;
             break;

         case NONE: // leave default value unchanged
             break;
        }

        return damage;
    }

}
