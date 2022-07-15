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
        for(int i = 0; i <= files.length; i++) {

            //create a provider
            Map<String, Object> providerVal = new HashMap<String, Object>(){{
                put("docType", files[i][6]);
                put("docNo", files[i][7]);
                put("name", files[i][8]);
            }};
            Provider provider = new Provider(providerVal);
            //aca deberiamos chequear si el provider existe
            values.get("provider").add(provider);
            
            //create withholding
            Map<String, Object> withholdingVal = new HashMap<String, Object>() {{
                put("date", files[i][0]);
                put("number", files[i][2]+files[i][3]);
                put("provider", provider);
            }};
            Withholding withholding = new Withholding(withholdingVal);
            //chequear q no este repetida
            values.get("withholding").add(withholding);
            
            //create ticket
            Map<String, Object> ticket = new HashMap<String, Object>(){{
                put("withholding", withholding);
                put("type", files[i][1]);
                put("numberTo", files[i][4]);
                put("authCode", files[i][5]);
                put("exchangeType", files[i][9]);
                put("exchangeMoney", files[i][10]);
                put("netAmountWI", files[i][11]);
                put("netAmountWOI", files[i][12]);
                put("amountImpEx", files[i][13]);
                put("ivaTax", files[i][14]);
                put("totalAmount", files[i][15]);
                put("issuedByMe", );//fijarse q el nacho sabe como distinguis si es echo ppor mi o no, o que me lo mande desde el perfil
    
            }};
            values.get("ticket").add(new Ticket(ticket));


        }
        //deberiamos cargar todo a la db?
        return values;
    }

}