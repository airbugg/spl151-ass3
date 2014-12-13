import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class RepairTool {

    public String name;
    public int quantity;

    @SuppressWarnings("unused")
    private RepairTool() { this(null,0); }

    public RepairTool(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }
}
