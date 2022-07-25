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


        int i = 0;
        for (String att : attributes) { 
            //add the id of the withholding 
            if(att.equals("withholding")) csvLine[i] = ticket.getID().get("id").toString();
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

        int i = 0;
        for (String att : attributes) {
            if(att.equals("provider")) csvLine[i] = ((Provider)values.get("provider"))
                                                                .getID().get("id").toString();
            else if(att.equals("sector")) csvLine[i] = ((Sector)values.get("sector"))
                                                                .getID().get("id").toString();
            else csvLine[i] = values.get(att).toString(); 
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
 
        int i = 0;
        for (String att : attributes) {
            if(att.equals("provSector")) csvLine[i] = ((Sector)values.get("provSector"))
                                                                    .getID().get("id").toString();
            else csvLine[i] = values.get(att).toString();
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
