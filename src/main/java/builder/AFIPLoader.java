package builder;

import java.io.File;
import java.util.*;

import databaserefactor.DollarPriceDAO;
import databaserefactor.ProviderDAO;
import databaserefactor.SectorDAO;
import databaserefactor.TicketDAO;
import databaserefactor.WithholdingDAO;
import utils.csv.*;


import models.DollarPrice;
import models.Provider;
import models.Sector;
import models.Ticket;
import models.Withholding;

public class AFIPLoader {

    private final static String[] AFIPTransmitterHeader = {"Fecha","Tipo","Punto de Venta","Número Desde","Número Hasta","Cód. Autorización","Tipo Doc. Emisor","Nro. Doc. Emisor","Denominación Emisor","Tipo Cambio","Moneda","Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"};
    private final static String[] AFIPReceiverHeader = {"Fecha","Tipo","Punto de Venta","Número Desde","Número Hasta","Cód. Autorización","Tipo Doc. Receptor","Nro. Doc. Receptor","Denominación Receptor","Tipo Cambio","Moneda","Imp. Neto Gravado","Imp. Neto No Gravado","Imp. Op. Exentas","IVA","Imp. Total"};

    /**
     * Crates the models from the info given from a AFIP file and saves them in database
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
            String[] f = files[i];
            build(f, issuedByMe);
        }
    }

    /*
    * this method take a String[] of values and create a new ticket
     */
    private static void build(String[] line, boolean issuedByMe){
        ProviderBuilder providerBuilder = new ProviderBuilder();
        Provider provider = (Provider)providerBuilder.build(line[7], line[8], line[9]);
        provider = saveProvider(provider);

        WithholdingBuilder withholdingBuilder = new WithholdingBuilder();
        Withholding withholding = (Withholding) withholdingBuilder.build(line[0], line[1], line[2] + line[3],
                provider);//files[i][2] + files[i][3] create the number of ticket/withholding
        withholding = saveWithholding(withholding);

        TicketBuilder ticketBuilder = new TicketBuilder();
        Ticket ticket = (Ticket) ticketBuilder.build(withholding, line[0], line[4], line[5], line[9], line[10], line[11],
                line[12], line[13], line[14], line[15], issuedByMe);
        saveTicket(ticket);
    }



    /*
     * this method take a ticket, and try to save it, if its already exist return db value;
     * and return it
     */
    private static Ticket saveTicket(Ticket ticket){
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
     * this method take a withholding, and try to save it, if its already exist return db value;
     */
    private static Withholding saveWithholding(Withholding withholding){
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
     * this method take a provider, and try to save it, if its already exist return db value;
     */
    private static Provider saveProvider(Provider provider) {
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

    /*
     * this method take a dollar price, and try to save it, if its already exist return db value;
     * @param dollar price to be saved
     * @return dollar price
     */
    private static DollarPrice saveDollarPrice(DollarPrice dollarPrice){
        if (!DollarPriceDAO.getInstance().save(dollarPrice)) {
            Optional<DollarPrice> dollarPriceOptional = DollarPriceDAO.getInstance().getAll().stream()
                    .filter(p -> p.getID().equals(dollarPrice.getID()))
                    .findFirst();
            if (dollarPriceOptional.isPresent()) {
                return dollarPriceOptional.get();
            } else {
                throw new IllegalStateException("The dollarPrice could not be saved but also not obtained");
            }
        }
        return dollarPrice;
    }

    /*
     * this method take a sector, and try to save it, if its already exist return db value;
     * @param sector we want save in db
     * @return sector
     */
    private static Sector saveSector(Sector sector){
        if (!SectorDAO.getInstance().save(sector)) {
            Optional<Sector> sectorOptional = SectorDAO.getInstance().getAll().stream()
                    .filter(p -> p.getID().equals(sector.getID()))
                    .findFirst();
            if (sectorOptional.isPresent()) {
                return sectorOptional.get();
            } else {
                throw new IllegalStateException("The sector could not be saved but also not obtained");
            }
        }
        return sector;
    }

}