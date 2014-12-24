/**
 * Created by airbag on 12/9/14.
 */
public class Customer {

    // fields
    public enum VandalismType { Arbitrary, Fixed, None };
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

         case Arbitrary: // generate random number in minDamage - maxDamage range
             damage = minDamage + Math.random() * (maxDamage - minDamage);
             break;

         case Fixed: // calc average
             damage = (minDamage + maxDamage) / 2;
             break;

         case None: // leave default value unchanged
             break;
        }

        return damage;
    }

    public String toString() {
        return "[ Customer Name: " +
                name + " ; Vandalism Type: " +
                vandalismType.toString() + " ; minDamage: " +
                minDamage + " ; maxDamage: " +
                maxDamage + " ]\n";
    }

}
