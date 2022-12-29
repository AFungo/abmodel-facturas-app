package controller;

import backup.BackUpBuilder;
import builder.Builder;
import builder.BuilderFactory;
import database.*;
import filters.*;
import loader.AFIPLoader;
import loader.ProviderLoader;
import loader.TicketLoader;
import loader.WithholdingLoader;
import models.*;
import models.set.ModelSet;
import utils.FixedData;
import utils.csv.CSVUtils;

import java.io.File;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author nacho
 */
public class Controller {

    //TODO: Maybe we can do it controller test for some methods
    public void resetDB() {
        DBManager.deleteDB();
        DBManager.initializeDB();
    }

    public void loadTicketsFromAFIPFile(File f) {
        List<String[]> data;

        boolean issuedByMe = true;
        data = CSVUtils.readCSV(f, AFIPLoader.AFIPReceiverHeader);
        if (data == null) {
            issuedByMe = false;
            data = CSVUtils.readCSV(f, AFIPLoader.AFIPTransmitterHeader);
        }

        if (data != null) {
            File folder = new File("./");   //folder at local
            LocalTime currentTime = LocalTime.now();
            String filename = FixedData.getBackupFolderName("carga-tickets--" + currentTime + "--"
                    + currentTime.getHour() + "-" + currentTime.getMinute() + "\\");
            BackUpBuilder backUpBuilder = new BackUpBuilder();
            backUpBuilder.createBackup(folder, filename);
            AFIPLoader.loadFromAFIPFile(f);
        }
    }

    public void loadDollarPricesFromFile(File f) {
//        List<String[]> data = CSVUtils.readCSV(f, ModelBuilder.DollarPriceHeader);
//        Objects.requireNonNull(data, "null dollar price");
//        ModelBuilder.buildDollarPricesFromData(data);
    }

    public Ticket loadTicket(Object[] values) {
        Builder ticketBuilder = BuilderFactory.create(ModelEnum.TICKET);
        Ticket ticket = (Ticket) ticketBuilder.build(values);
        TicketLoader ticketLoader = new TicketLoader();
        return ticketLoader.load(ticket);
    }

    public Withholding loadWithholding(Object[] values) {
        Builder withholdingBuilder = BuilderFactory.create(ModelEnum.WITHHOLDING);
        Withholding withholding = (Withholding) withholdingBuilder.build(values);
        WithholdingLoader withholdingLoader = new WithholdingLoader();
        return withholdingLoader.load(withholding);
    }

    public Provider loadProvider(Object[] values) {
        Builder providerBuilder = BuilderFactory.create(ModelEnum.PROVIDER);
        Provider provider = (Provider) providerBuilder.build(values);
        ProviderLoader providerLoader = new ProviderLoader();
        return providerLoader.load(provider);
    }

    /**
     * This method returns the tickets, if the filters are empty then
     * returns all the tickets otherwise returns the tickets post-apply
     * the filters.
     *
     * @param filters to be applied
     * @return a list of all tickets after apply them the filters
     */
    public List<Ticket> getTickets(Filter... filters) {
        ModelSet<Ticket> tickets = TicketDAO.getInstance().getAll();
        return new ArrayList<>(Filter.applyFilters(tickets, filters));
    }

    /**
     * This method returns the withholdings, if the filters are empty then
     * returns all the withholdings otherwise returns the tickets post-apply
     * the filters.
     *
     * @param filters to be applied
     * @return a list of all withholdings after apply them the filters
     */
    public List<Withholding> getWithholdings(Filter... filters) {
        ModelSet<Withholding> withholdings = WithholdingDAO.getInstance().getAll();
        return new ArrayList<>(Filter.applyFilters(withholdings, filters));
    }

    /**
     * This method returns the providers, if the filters are empty then
     * returns all the providers otherwise returns the tickets post-apply
     * the filters.
     *
     * @param filters to be applied
     * @return a list of all providers after apply them the filters
     */
    public List<Provider> getProviders(Filter... filters) {
        ModelSet<Provider> providers = ProviderDAO.getInstance().getAll();
        return new ArrayList<>(Filter.applyFilters(providers, filters));
    }

    /**
     *
     * @param filters
     * @return
     */
    public List<Sector> getSector(Filter... filters) {
       ModelSet<Sector> sectors = SectorDAO.getInstance().getAll();
       return new ArrayList<>(Filter.applyFilters(sectors, filters));
    }

    /**
     *
     * @param filters
     * @return
     */
    public List<DollarPrice> getDollarPrice(Filter... filters) {
        throw new UnsupportedOperationException("Implement if needed");
    }

    public void updateTickets(Filter filter, String attribute, String value) {
        ModelSet<Ticket> tickets = Filter.applyFilters(TicketDAO.getInstance().getAll(), filter);
        for (Ticket t : tickets) {
            TicketDAO.getInstance().update(t, Collections.singletonMap(attribute, value));
        }
    }
    
    public void updateWithholdings(Filter filter, String attribute, Object value) {
        ModelSet<Withholding> withholdings = Filter.applyFilters(WithholdingDAO.getInstance().getAll(), filter);
        for (Withholding w : withholdings) {
            WithholdingDAO.getInstance().update(w, Collections.singletonMap(attribute, value));
        }
    }

    public void updateProvider(Filter filter, String attribute, String value) {
        ModelSet<Provider> providers = Filter.applyFilters(ProviderDAO.getInstance().getAll(), filter);
        for (Provider p : providers) {
            ProviderDAO.getInstance().update(p, Collections.singletonMap(attribute, value));
        }
    }

    public boolean updateProviderDoc(String newDoc, String oldDoc) {
        Optional<Provider> optionalProvider = ProviderDAO.getInstance().getAll().stream()
                .filter(p -> p.getValues().get("docNo").equals(oldDoc)).findFirst();
        return optionalProvider.filter(provider -> ProviderDAO.getInstance()
                        .update(provider, Collections.singletonMap("docNo", newDoc)))
                .isPresent();
    }

    /**
     * This method use a filter to search the
     *
     * @param filter applied to withholdings to remove a subset of all withholdings
     */
    public void deleteWithholdings(Filter filter) {
        ModelSet<Withholding> withholdings = Filter.applyFilters(WithholdingDAO.getInstance().getAll(), filter);
        List<Boolean> results = withholdings.stream().map(w -> WithholdingDAO.getInstance().delete(w)).collect(Collectors.toList());
        if (results.stream().anyMatch(r -> !r)) {
            throw new IllegalArgumentException("Some items dont exists or can not be removed");
        }
    }

    public boolean providerHasTickets(String docNo) {
        Optional<Provider> optionalProvider = ProviderDAO.getInstance().getAll().stream()
                .filter(p -> p.getID().get("docNo").equals(docNo)).findFirst();
        if (optionalProvider.isPresent()) {
            Provider provider = optionalProvider.get();
            return WithholdingDAO.getInstance().getAll().stream()
                    .anyMatch(w -> w.getValues().get("provider").equals(provider));
        } else {
            throw new IllegalArgumentException("The given docNo has no provider associated");
        }
    }

}