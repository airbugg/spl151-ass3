import reit.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Eugene on 27/12/2014.
 */
public final class XMLParser {

    private XMLParser() {
    }

    private static XMLStreamReader initializeReader (String xmlPath)
            throws FileNotFoundException, XMLStreamException {
        // initialize parser
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(xmlPath));

        return reader;
    }

    public static Management createManagement() {
        return new Management();
    }

    public static void parseInitialData(String initialDataXmlPath)
            throws FileNotFoundException, XMLStreamException {

        Warehouse warehouse = new Warehouse();
        String name = "";
        int quantity = 0;

        XMLStreamReader reader = initializeReader(initialDataXmlPath);
        String elementContent = "";

        while (reader.hasNext()) {

            int event = reader.next();

            switch (event) {

                case XMLStreamConstants.START_ELEMENT:
                    String startElement = reader.getLocalName();

                    if ("Name".equals(startElement)) {
                        name = elementContent;

                    }

                    break;

                case XMLStreamConstants.CHARACTERS:
                    elementContent = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    String endElement = reader.getLocalName();

                    if ("Tool".equals(endElement)) {
                        warehouse.addRepairTool(name, quantity);

                    }
                    if ("Material".equals(endElement)) {
                        warehouse.addRepairMaterial(name, quantity);

                    }
                    if ("Name".equals(endElement)) {
                        name = elementContent;

                    }
                    if ("Quantity".equals(endElement)) {
                        quantity = Integer.parseInt(elementContent);

                    }
            }
        }
    }


}
