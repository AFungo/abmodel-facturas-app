package builder;

import java.util.*;

import database.ProviderDAO;
import database.TicketDAO;
import database.WithholdingDAO;
import models.*;
import utils.Parser;

import java.sql.Date;

public class ModelBuilder {

    public final static String[] AFIPTransmitterHeader = {"Fecha","Tipo","Punto de Venta","Número Desde","Número Hasta","Cód. Autorización","Tipo Doc. Emisor","Nro. Doc. Emisor","Denominación Emisor","Tipo Cambio","Moneda","Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"};
    public final static String[] AFIPReceiverHeader = {"Fecha","Tipo","Punto de Venta","Número Desde","Número Hasta","Cód. Autorización","Tipo Doc. Receptor","Nro. Doc. Receptor","Denominación Receptor","Tipo Cambio","Moneda","Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"};
    public final static String[] DollarPriceHeader = {"Fecha cotizacion","Compra","Venta"};

    /**
     * Crates the models from the info given from a file and saves them in database.
     *
     * @param data tickets csv AFIP file
     * @param issuedByMe boolean representing if the ticket was issued by me
     */
    public static void buildFromAFIPData(List<String[]> data, boolean issuedByMe) {
        //for each row get the values create the models and put it in the values
        for (String[] row : data) {
            Provider provider = buildProvider(row[7], row[8], row[9]);;
            
            Withholding withholding = buildWithholding(row[0], row[1], row[2] + row[3],
                    provider);//files[i][2] + files[i][3] create the number of ticket/withholding

            buildTicket(withholding, row[0], row[4], row[5], row[9], row[10], row[11],
                    row[12], row[13], row[14], row[15], issuedByMe);
        }
    }

    /*
     * This method take a Object[] with params of the ticket, then build the ticket try to save it in the db
     * and return it
     */
    public static Ticket buildTicket(Object... data){
        Withholding withholding = (Withholding)data[0];

        Map<String, Object> ticketValues = new HashMap<String, Object>(){{
            put("withholding", withholding);
            put("type", data[1]);
            put("numberTo", data[2]);
            put("authCode", data[3]);
            put("exchangeType", Float.parseFloat((String) data[4]));
            put("exchangeMoney", data[5]);
            put("netAmountWI", Float.parseFloat((String) data[6]));
            put("netAmountWOI", Parser.parseFloat((String)data[7]));
            put("amountImpEx", Parser.parseFloat((String)data[8]));
            put("ivaTax", Parser.parseFloat((String)data[9]));
            put("totalAmount", Float.parseFloat((String)data[10]));
            put("issuedByMe", data[11]);
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
    public static Withholding buildWithholding(Object... data){
        Sector sector = (Sector) ProviderDAO.getInstance().getAll().stream()
                        .filter(p -> p.getID().equals(((Provider)data[2]).getID()))
                        .findFirst().get().getValues().get("sector");
        
        Map<String, Object> withholdingValues = new HashMap<String, Object>(){{
            put("date", Date.valueOf((String)data[0]));
            put("number", data[1]);
            put("provider", data[2]);
            put("sector", sector);
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
    public static Provider buildProvider(Object... data) {
        Map<String, Object> providerValues = new HashMap<String, Object>(){{
            put("docType", data[0]);
            put("docNo", data[1]);
            put("name", data[2]);
            if (data.length == 6) {
                put("address", data[3]);
                put("sector", data[4]);
                put("alias", data[5]);
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

    public static void buildDollarPricesFromData(List<String[]> data) {
        throw new UnsupportedOperationException("TODO");
    }

    public static DollarPrice buildDollarPrice(Object... data) {
        throw new UnsupportedOperationException("TODO");
    }

}