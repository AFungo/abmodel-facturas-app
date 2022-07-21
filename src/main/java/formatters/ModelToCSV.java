package formatters;

import java.util.List;
import java.util.Map;

import models.*;

/**
 * This class have all the methods who need to transform a model into a csv representation
 */
public class ModelToCSV {

    /**
     * given a ticket return a CSV line representation of the ticket
     * @param ticket to be transformed
     * @return a CSV line representation of the ticket
     */
    public static String[] toCSV(Ticket ticket) {
        
        Map<String, Object> values = ticket.getValues();
        List<String> attributes = Ticket.getAttributes();

        String[] csvLine = new String[attributes.size()];


        csvLine[0] = ticket.getID().get("id").toString();//add the id of the withholding 
        attributes.remove("withholding");//remove withholding of the set of attributes
        int i = 1;
        for (String att : attributes) {
            csvLine[i] = values.get(att).toString(); 
            i++;     
        }
        return csvLine;    
    }

    /**
     * given a withholding return a CSV line representation of the withholding
     * @param withholding to be transformed
     * @return a CSV line representation of the withholding
     */
    public static String[] toCSV(Withholding withholding) {
        Map<String, Object> values = withholding.getValues();
        List<String> attributes = Withholding.getAttributes();

        String[] csvLine = new String[attributes.size()];

        Provider provider = (Provider)values.get("provider");
        Sector sector = (Sector)values.get("sector");

        csvLine[0] = provider.getID().get("id").toString();//add provider to csvLine
        attributes.remove("provider");//remove provider from attributes

        csvLine[1] = sector.getID().get("id").toString();//add sector to csvLine
        attributes.remove("sector");// remove sector from attributes

        int i = 2;
        for (String att : attributes) {
            csvLine[i] = values.get(att).toString(); 
            i++;     
        }
        return csvLine;    
    }

     /**
     * given a Provider return a CSV line representation of the Provider
     * @param Provider to be transformed
     * @return a CSV line representation of the Provider
     */
    public static String[] toCSV(Provider provider) {
        Map<String, Object> values = provider.getValues();
        List<String> attributes = Provider.getAttributes();

        String[] csvLine = new String[attributes.size()];

        Sector sector = (Sector)values.get("provSector");

        csvLine[0] = sector.getID().get("id").toString();//add sector to csvLine
        attributes.remove("provSector");// remove sector from attributes

        int i = 1;
        for (String att : attributes) {
            csvLine[i] = values.get(att).toString(); 
            i++;     
        }
        return csvLine;
    }

     /**
     * given a DollarPrice return a CSV line representation of the dollar price
     * @param dollarPrice the dollar price be transformed
     * @return a CSV line representation of the DollarPrice
     */
    public static String[] toCSV(DollarPrice dollarPrice) {
        Map<String, Object> values = dollarPrice.getValues();
        List<String> attributes = DollarPrice.getAttributes();

        String[] csvLine = new String[attributes.size()];
        int i = 0;
        for (String att : attributes) {
            csvLine[i] = values.get(att).toString(); 
            i++;     
        }
        return csvLine;
    }

     /**
     * given a Sector return a CSV line representation of the sector
     * @param sector the sector be transformed
     * @return a CSV line representation of the sector
     */
    public static String[] toCSV(Sector sector) {
        Map<String, Object> values = sector.getValues();
        List<String> attributes = Sector.getAttributes();

        String[] csvLine = new String[attributes.size()];
        int i = 0;
        for (String att : attributes) {
            csvLine[i] = values.get(att).toString(); 
            i++;     
        }
        return csvLine;
    }
}
