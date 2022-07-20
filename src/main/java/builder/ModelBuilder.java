package builder;

import java.io.File;
import java.util.*;

import databaserefactor.ProviderDAO;
import databaserefactor.TicketDAO;
import databaserefactor.WithholdingDAO;
import utils.Parser;
import utils.csv.*;

import java.sql.Date;

import models.Provider;
import models.Sector;
import models.Ticket;
import models.Withholding;

public class ModelBuilder {

    private final static String[] AFIPTransmitterHeader = {"Fecha","Tipo","Punto de Venta","Número Desde","Número Hasta","Cód. Autorización","Tipo Doc. Emisor","Nro. Doc. Emisor","Denominación Emisor","Tipo Cambio","Moneda","Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"};
    private final static String[] AFIPReceiverHeader = {"Fecha","Tipo","Punto de Venta","Número Desde","Número Hasta","Cód. Autorización","Tipo Doc. Receptor","Nro. Doc. Receptor","Denominación Receptor","Tipo Cambio","Moneda","Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"};

    /**
     * Crates the models from the info given from a file and saves them in database
     * @param file tickets csv AFIP file
     */
    public static void buildFromAFIPFile(File file) {

        Boolean issuedByMe = false;
        String[][] files;
        try {
            files = CSVUtils.readCSV(file, AFIPTransmitterHeader);
        } catch (IllegalArgumentException e) {  //in case the header didn't match, we assume the receiver
            issuedByMe = true;                  //header will match
            files = CSVUtils.readCSV(file, AFIPReceiverHeader);
            //in case this file doesn't match either the exception will be raised further in the program
        }

        //for each string[] get the values create the models and put it in the values
        for( int i = 1; i <= files.length; i++) {
            Provider provider = buildProvider(files[i][7], files[i][8], files[i][9]);;
            
            Withholding withholding = buildWithholding(files[i][0], files[i][1], files[i][2] + files[i][3],
                    provider);//files[i][2] + files[i][3] create the number of ticket/withholding

            buildTicket(withholding, files[i][0], files[i][4], files[i][5], files[i][9], files[i][10], files[i][11],
                    files[i][12], files[i][13], files[i][14], files[i][15], issuedByMe);
        }
    }
    
    /*
     * This method take a Object[] with params of the ticket, then build the ticket try to save it in the db
     * and return it
     */
    private static Ticket buildTicket(Object... data){
        Withholding withholding = (Withholding)data[0];

        Map<String, Object> ticketValues = new HashMap<String, Object>(){{
            put("withholding", withholding);
            put("type", (String) data[1]);
            put("numberTo", (String) data[2]);
            put("authCode", (String) data[3]);
            put("exchangeType", Float.parseFloat((String) data[4]));
            put("exchangeMoney", (String) data[5]);
            put("netAmountWI", Float.parseFloat((String) data[6]));
            put("netAmountWOI", Parser.parseFloat((String)data[7]));
            put("amountImpEx", Parser.parseFloat((String)data[8]));
            put("ivaTax", Parser.parseFloat((String)data[9]));
            put("totalAmount", Float.parseFloat((String)data[10]));
            put("issuedByMe", (Boolean) data[11]);
        }};

        Ticket ticket = new Ticket(ticketValues);
        
        if (!TicketDAO.getInstance().save(ticket)) {
            Optional<Ticket> ticketOptional = TicketDAO.getInstance().getAll().stream()
                    .filter(p -> p.getID().equals(ticket.getID()))
                    .findFirst();
            if (ticketOptional.isPresent()) {
                return ticketOptional.get();
            } else {
                throw new IllegalStateException("The ticket could not be saved but also not obtained");
            }
        }
        return ticket;
    }

    /*
     * this method takes a Object[] with the data of a withholding, build, try to save it in the db and return it
     */
    private static Withholding buildWithholding(Object... data){
        Sector sector = (Sector) ProviderDAO.getInstance().getAll().stream()
                        .filter(p -> p.getID().equals(((Provider)data[2]).getID()))
                        .findFirst().get().getValues().get("sector");
        
        Map<String, Object> withholdingValues = new HashMap<String, Object>(){{
            put("date", Date.valueOf((String)data[0]));
            put("number", (String) data[1]);
            put("provider", data[2]);
            put("sector", sector);
            if(data.length >= 7){
                put("iva", data[3]);
                put("profits", data[4]);
                put("delivered", data[5]);
                if(data[6] != null) put("sector", data[6]);
                if(data.length == 8) put("id", data[7]);
            }
        }};

        Withholding withholding = new Withholding(withholdingValues);
        
        if (!WithholdingDAO.getInstance().save(withholding)) {
            Optional<Withholding> withholdingOptional = WithholdingDAO.getInstance().getAll().stream()
                    .filter(p -> p.getID().equals(withholding.getID()))
                    .findFirst();
            if (withholdingOptional.isPresent()) {
                return withholdingOptional.get();
            } else {
                throw new IllegalStateException("The withholding could not be saved but also not obtained");
            }
        }
        return withholding;
    }

    /*
     * this method take a String[], build a provider with the data, try to save it in the db and return it;
     */
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
                throw new IllegalStateException("The provider could not be saved but also not obtained");
            }
        }
        return provider;
    }

}