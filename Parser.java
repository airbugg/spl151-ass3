/**
 * Created by airbag on 12/9/14.
 *
 */

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.HashMap;
import java.util.Vector;

public final class Parser {

    private Parser() { // private constructor for static class
    }


    public static Warehouse parseInitialData(String xmlPath) throws XMLStreamException{

        // Warehouse fields
        Warehouse warehouse = null;
        HashMap<String, RepairTool> tools = null;
        HashMap<String, RepairMaterial> materials = null;

        // tools and materials fields
        String name = null;
        int quantity = 0;

        // initialize parser
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(xmlPath));

        String elementContent = null;

        while (reader.hasNext()) {

            int event = reader.next();

            switch (event) {

                case XMLStreamConstants.START_ELEMENT:
                    String startElement = reader.getLocalName();

                    if (startElement.equals("Tools")) {
                        tools = new HashMap<String, RepairTool>();

                    }
                    if (startElement.equals("Materials")) {
                        materials = new HashMap<String, RepairMaterial>();

                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                   elementContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String endElement = reader.getLocalName();

                    if (endElement.equals("Warehouse")) {
                        warehouse = new Warehouse(tools, materials);

                    }
                    if (endElement.equals("name")) {
                        name = elementContent;

                    }
                    if (endElement.equals("quantity")) {
                        quantity = Integer.parseInt(elementContent);

                    }
                    if (endElement.equals("Tool")) {
                        RepairTool tool = new RepairTool(name, quantity);
                        tools.put(name, tool);

                    }
                    if (endElement.equals("Material")) {
                        RepairMaterial material = new RepairMaterial(name, quantity);
                        materials.put(name, material);

                    }
                    break;
            }

        }
        return warehouse;
    }

    public static Assets parseAssets(String xmlPath) throws XMLStreamException {

        Assets assets;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(xmlPath));

        return assets;
    }

    public static Vector<CustomerGroupDetails> parseCustomersGroups (String xmlPath) throws XMLStreamException {

        // collection fields
        Vector<CustomerGroupDetails> customers = new Vector<CustomerGroupDetails>();
        CustomerGroupDetails customerGroup = null;

        // Customer fields
        String name = null;
        Customer.VandalismType vandalismType = Customer.VandalismType.NONE;
        int minDamage = 0;
        int maxDamage = 0;

        // RentalRequest fields
        String id = null;
        String type = null;
        int size = 0;
        int duration = 0;

        // initialize parser
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(xmlPath));

        String elementContent = null;

        while (reader.hasNext()) {

            int event = reader.next();

            switch (event) {

                case XMLStreamConstants.START_ELEMENT:
                    String startElement = reader.getElementText();

                    if (startElement.equals("Request")) {
                        id = reader.getAttributeValue(null,"id");

                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    elementContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String endElement = reader.getLocalName();

                    if (endElement.equals("GroupManagerName")) {
                        customerGroup = new CustomerGroupDetails(elementContent);

                    }
                    if (endElement.equals("Name")) {
                        name = elementContent;

                    }
                    if (endElement.equals("Vandalism")) {
                        vandalismType = Customer.VandalismType.valueOf(elementContent);

                    }
                    if (endElement.equals("MinimumDamage")) {
                        minDamage = Integer.parseInt(elementContent);

                    }
                    if (endElement.equals("MaximumDamage")) {
                        maxDamage = Integer.parseInt(elementContent);

                    }
                    if (endElement.equals("Customer")) {
                        customerGroup.addCustomer(new Customer(name,vandalismType,minDamage,maxDamage));

                    }
                    if (endElement.equals("Type")) {
                        type = elementContent;

                    }
                    if (endElement.equals("Size")) {
                        size = Integer.parseInt(elementContent);

                    }
                    if (endElement.equals("Duration")) {
                        duration = Integer.parseInt(elementContent);

                    }
                    if (endElement.equals("Request")) {
                        customerGroup.addRentalRequest(new RentalRequest(id,type,size,duration));

                    }
                    if (endElement.equals("CustomerGroupDetails")) {
                        customers.add(customerGroup);
                    }
            }
        }

        return customers;
    }

    public static parseAssetContentRepairDetails (String xmlPath) throws  XMLStreamException {

    }

}
