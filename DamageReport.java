/**
 * Created by airbag on 12/9/14.
 */
public class DamageReport {

    private Asset asset;
    private double damagePercent;

    public DamageReport(Asset asset, double totalDamage) {
        this.asset = asset;
        this.damagePercent = totalDamage;
    }

    public void inflictDamage() {
        asset.updateDamage(damagePercent);
    }
}
