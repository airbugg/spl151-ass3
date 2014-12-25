/**
 * Created by airbag on 12/9/14.
 */
class Driver {

    public static void main(String[] args) throws Exception {

        // parse XML
        Management management = Parser.createManagement("xml/InitialData.xml", "xml/Assets.xml");
        Parser.parseAssetContentRepairDetails(management, "xml/AssetContentsRepairDetails.xml");
        Parser.parseCustomersGroups(management, "xml/CustomersGroups.xml");

        System.out.println("Finished parsing files.");
        management.simulate();
        //System.out.println(management);
    }
}
