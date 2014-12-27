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

    private XMLStreamReader initializeReader (String initialDataXmlPath)
            throws FileNotFoundException, XMLStreamException {
        // initialize parser
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(initialDataXmlPath));

        return reader;
    }

    public static Management createManagement() {

    }


}
