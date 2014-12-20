/**
 * Created by airbag on 12/9/14.
 *
 */

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public final class Parser {

    private Parser() { // private constructor for static class
    }


    public static Management createManagement(String initialDataXmlPath,
                                              String assetsXmlPath) throws XMLStreamException{

        // Management object
        Management management = null;

        // Warehouse fields
        Warehouse warehouse = new Warehouse();

        // tools and materials fields
        String name = "";
        int quantity = 0;

        // clerk fields
        Location location = null;

        // initialize parser
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(initialDataXmlPath));

        String elementContent = "";

        while (reader.hasNext()) {

            int event = reader.next();

            switch (event) {

                case XMLStreamConstants.START_ELEMENT:
                    String startElement = reader.getLocalName();

                    if (startElement.equals("Location")) {
                        int x = Integer.parseInt(reader.getAttributeValue(0));
                        int y = Integer.parseInt(reader.getAttributeValue(1));

                        location = new Location(x,y);

                    }
                    if (startElement.equals("Staff")) { // creating Management object here
                        management = new Management(warehouse, parseAssets(assetsXmlPath));

                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                   elementContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String endElement = reader.getLocalName();

                    if (endElement.equals("Name")) {
                        name = elementContent;

                    }
                    if (endElement.equals("Quantity")) {
                        quantity = Integer.parseInt(elementContent);

                    }
                    if (endElement.equals("Tool")) {
                        warehouse.addRepairTool(name, quantity);

                    }
                    if (endElement.equals("Material")) {
                        warehouse.addRepairMaterial(name, quantity);

                    }
                    if (endElement.equals("Clerk")) {
                        management.addClerk(new ClerkDetails(name, location));

                    }
                    break;
            }

        }
        return management;
    } //TODO: TEST.

    public static Assets parseAssets(String xmlPath) throws XMLStreamException {

        // initialize Assets object
        Assets assets = new Assets();

        // initialize single asset
        Asset asset = null;

        // initialize AssetContent object
        AssetContent assetContent = null;

        // initialize AssetContent fields
        int repairMultiplier = 0;

        // initialize Asset fields
        String name = null;
        String type = null;
        int size = 0;
        Location location = null;
        int costPerNight = 0;



        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(xmlPath));

        String elementContent = null;

        while (reader.hasNext()) {

            int event = reader.next();

            switch (event) {

                case XMLStreamConstants.START_ELEMENT:
                    String startElement = reader.getLocalName();

                    if (startElement.equals("Location")) {
                        int x = Integer.parseInt(reader.getAttributeValue(0));
                        int y = Integer.parseInt(reader.getAttributeValue(1));

                        location = new Location(x,y);

                    }
                    if (startElement.equals("AssetContents")) {
                        asset = new Asset(name, type, location, costPerNight, size);

                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    elementContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String endElement = reader.getLocalName();

                    if (endElement.equals("Name")) {
                        name = elementContent;

                    }
                    if (endElement.equals("Type")) {
                        type = elementContent;

                    }
                    if (endElement.equals("Size")) {
                        size = Integer.parseInt(elementContent);

                    }
                    if (endElement.equals("CostPerNight")) {
                        costPerNight = Integer.parseInt(elementContent);

                    }
                    if (endElement.equals("RepairMultiplier")) {
                        repairMultiplier = Integer.parseInt(elementContent);

                    }
                    if (endElement.equals("AssetContent")) {
                        asset.addContent(new AssetContent(name, repairMultiplier));

                    }
                    if (endElement.equals("Asset")) {
                        assets.addAsset(asset);

                    }
                    break;
            }
        }
        return assets;
    } //TODO: TEST.

    public static void parseCustomersGroups (Management management,
                                             String xmlPath) throws XMLStreamException {

        // collection fields
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
                        management.addCustomerGroup(customerGroup);
                    }
            }
        }
    } //TODO: TEST.

    public static void parseAssetContentRepairDetails (Management management,
                                                       String xmlPath) throws  XMLStreamException {

        ArrayList<RepairToolInformation> tools = new ArrayList<RepairToolInformation>();
        ArrayList<RepairMaterialInformation> materials = new ArrayList<RepairMaterialInformation>();

        String contentName = null;
        String name = null;
        int quantity = 0;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(xmlPath));

        String elementContent = "";

        while (reader.hasNext()) {

            int event = reader.next();

            switch (event) {

                case XMLStreamConstants.START_ELEMENT:
                    String startElement = reader.getLocalName();

                    if (startElement.equals("Tools")) {
                        contentName = elementContent;
                        tools = new ArrayList<RepairToolInformation>();

                    }
                    if (startElement.equals("Materials")) {
                        materials = new ArrayList<RepairMaterialInformation>();

                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    elementContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String endElement = reader.getLocalName();

                    if (endElement.equals("Name")) {
                        name = elementContent;

                    }
                    if (endElement.equals("Quantity")) {
                        quantity = Integer.parseInt(elementContent);

                    }
                    if (endElement.equals("Tool")) {
                        tools.add(new RepairToolInformation(name, quantity));

                    }
                    if (endElement.equals("Material")) {
                        materials.add(new RepairMaterialInformation(name, quantity));

                    }
                    if (endElement.equals("Tools")) {
                        management.addItemRepairTool(contentName, tools);

                    }
                    if (endElement.equals("Materials")) {
                        management.addItemRepairMaterial(contentName, materials);

                    }
                    break;
            }

        }
    } //TODO: TEST.
}
