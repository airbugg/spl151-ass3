import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
public class RepairMaterial {

    public String name;
    public int quantity;

    public RepairMaterial(String name, int quantity){ // constructor
        this.name = name;
        this.quantity = quantity;
    }
    @SuppressWarnings("unused")
    private RepairMaterial() { this(null,0); }
}
