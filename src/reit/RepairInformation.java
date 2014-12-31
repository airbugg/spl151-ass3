package reit;

import javafx.util.Pair;

import java.util.LinkedList;

/**
 * RepairInformation:
 * A class that represents needed material/tool and its quantity in order to
 * repair one content.
 *
 * There are 2 classes that extends this class: RepairToolInformation and RepairMaterialInformation
 * Created by gal on 12/17/2014.
 */
public class RepairInformation {
    private String fName;
    private int fQuantity;

    public RepairInformation(String Name, int quantity) {
        fName = Name;
        fQuantity = quantity;
    }

    /**
     *
     * @return - The name of the tool/material
     */
    public String Name(){ return fName;     }

    /**
     *
     * @return - The quantity needed from the tool/material
     */
    public int quantity(){ return fQuantity;    }


}
