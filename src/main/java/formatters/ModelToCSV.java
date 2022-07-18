package formatters;

import java.util.Map;
import java.util.Set;

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
        Set<String> attributes = Ticket.getAttributes();

        String[] csvLine = new String[attributes.size()];


        csvLine[0] = ticket.getID().get("id").toString();//add the id of the withholding 
        attributes.remove(values.get("withholding"));//remove withholding of the set of attributes
        int i = 1;
        for (String att : attributes) {
            csvLine[i] = values.get(att).toString(); 
            i++;     
        }
        return csvLine;    
    }

    public static String[] toCSV(Withholding withholding) {
        throw new UnsupportedOperationException("TODO");
    }

    public static String[] toCSV(Provider provider) {
        throw new UnsupportedOperationException("TODO");
    }

}
