package reit;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


class Assets {

    private ArrayList<Asset> assets;
    private BlockingQueue<DamageReport> damageReports;
    private Object assetLock;

    Assets() {
        this.assets = new ArrayList<Asset>();
        assetLock = new Object();
        this.damageReports = new LinkedBlockingQueue<DamageReport>();
    }

    void addAsset(Asset asset) {
        assets.add(asset);
    }

    Asset find(RentalRequest rentalRequest, ClerkDetails clerk) {
        boolean assetFound = false;
        Asset suitableAsset = null;
        synchronized (assetLock) {
            while (!assetFound) { // while no suitable asset has been found
                for (Asset asset : assets) { // look for assets
                    if (rentalRequest.isSuitable(asset)) { // if suitable asset has been found
                        assetFound = true;
                        return asset; // TODO: UGLY HACK.
                    }
                }
                try { // no available assets have been found
                    Management.logger.info(clerk.getName() + " couldn't find a suitable asset. WAITING...");
                    assetLock.wait(); // so we wait.
                    Management.logger.info("NOTIFY ALL CLERKS! AN ASSET HAS BEEN VACATED!!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return suitableAsset;
    }

    void submitDamageReport(DamageReport damageReport) { // adds another damageReport to
        damageReports.add(damageReport);
        synchronized (assetLock) {
            try {
                assetLock.notifyAll(); // let everyone know a new asset is now available
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    ArrayList<Asset> getDamagedAssets() {
        ArrayList<Asset> damagedProperty = new ArrayList<Asset>();

        for (Asset asset : assets) { // TODO: figure out where the hell we should use damageReport..
            if (asset.isWrecked())
                damagedProperty.add(asset);
        }

        return damagedProperty;
    }

    void applyDamage() { // iterate over damage reports, inflict damage
        for (DamageReport damageReport : damageReports) {
            damageReport.inflictDamage();
        }

        damageReports.clear();
    }


    public String toString() {
        StringBuilder output = new StringBuilder("::reit.Asset Report::\n");

        for (Asset asset : assets) {
            output.append(asset).append("\n");
        }
        return output.toString();
    }
}