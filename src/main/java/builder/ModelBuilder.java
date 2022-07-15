package builder;

import java.io.File;
import java.util.*;

import databaserefactor.ProviderDAO;
import databaserefactor.TicketDAO;
import databaserefactor.WithholdingDAO;
import utils.csv.*;

import models.Provider;
import models.Ticket;
import models.Withholding;

public class ModelBuilder {

    /**
     * create a map of models from a file of tickets
     * @param file tickets csv file
     * @return map of models
     */
    public static void buildFromFile(File file, String header) {
        
        String[][] files = CSVUtils.readCSV(file, header);

        //for each string[] get the values create the models and put it in the values
        for( int i = 0; i <= files.length; i++) {
            final int index = i;
            //create a provider
            Provider provider = buildProvider(files[index][6], files[index][7], files[index][8]);

            //create withholding
            Map<String, Object> withholdingVal = new HashMap<String, Object>() {{
                put("date", files[index][0]);
                put("number", files[index][2]+files[index][3]);
                put("provider", provider);
            }};
            Withholding withholding = new Withholding(withholdingVal);
            //chequear q no este repetida
            WithholdingDAO.getInstance().save(withholding);
            
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
            TicketDAO.getInstance().save(new Ticket(ticket));
        }
    }

    private static Provider buildProvider(String... data) {
        String[] values = (String[]) Arrays.stream(data).toArray();

        Map<String, Object> providerValues = new HashMap<String, Object>(){{
            put("docType", values[0]);
            put("docNo", values[1]);
            put("name", values[2]);
            if (values.length == 6) {
                put("address", values[3]);
                put("sector", values[4]);
                put("alias", values[5]);
            }
        }};

        Provider provider = new Provider(providerValues);
        if (!ProviderDAO.getInstance().save(provider)) {
            Optional<Provider> providerOptional = ProviderDAO.getInstance().getAll().stream()
                    .filter(p -> p.getID().equals(provider.getID()))
                    .findFirst();
            if (providerOptional.isPresent()) {
                return providerOptional.get();
            } else {
                throw new IllegalStateException("The provider could not be saved but could be obtained too");
            }
        }
        return provider;
    }

}