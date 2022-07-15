package builder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utils.csv.*;

import models.Provider;
import models.Ticket;
import models.Withholding;

public class ModelBuilder {

    /**
     * create a map of models from a file of tickets
     * @param pathToFile location of the tickets csv
     * @return map of models
     */
    public static Map<String, List<Object>> buildFromFile(String pathToFile) {
        
        String[][] files = CSVUtils.readCSV(pathToFile);

        //define the models to return
        Map<String, List<Object>> values = new HashMap<String, List<Object>>() {{//no me gusta el nombre pq no son values son models values estan adentro de los models
                put("tickets", new LinkedList<>());
                put("withholding", new LinkedList<>());
                put("providers", new LinkedList<>());
        }};
        
        //for each string[] get the values create the models and put it in the values
        for( int i = 0; i <= files.length; i++) {
            final int index = i;
            //create a provider
            Map<String, Object> providerVal = new HashMap<String, Object>(){{
                put("docType", files[index][6]);
                put("docNo", files[index][7]);
                put("name", files[index][8]);
            }};
            Provider provider = new Provider(providerVal);
            //aca deberiamos chequear si el provider existe
            values.get("provider").add(provider);
            
            //create withholding
            Map<String, Object> withholdingVal = new HashMap<String, Object>() {{
                put("date", files[index][0]);
                put("number", files[index][2]+files[index][3]);
                put("provider", provider);
            }};
            Withholding withholding = new Withholding(withholdingVal);
            //chequear q no este repetida
            values.get("withholding").add(withholding);
            
            //create ticket
            Map<String, Object> ticket = new HashMap<String, Object>(){{
                put("withholding", withholding);
                put("type", files[index][1]);
                put("numberTo", files[index][4]);
                put("authCode", files[index][5]);
                put("exchangeType", files[index][9]);
                put("exchangeMoney", files[index][10]);
                put("netAmountWI", files[index][11]);
                put("netAmountWOI", files[index][12]);
                put("amountImpEx", files[index][13]);
                put("ivaTax", files[index][14]);
                put("totalAmount", files[index][15]);
            //    put("issuedByMe", );//fijarse q el nacho sabe como distinguis si es echo ppor mi o no, o que me lo mande desde el perfil
    
            }};
            values.get("ticket").add(new Ticket(ticket));


        }
        //deberiamos cargar todo a la db?
        return values;
    }

}