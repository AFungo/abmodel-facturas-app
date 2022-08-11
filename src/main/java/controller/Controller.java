package controller;

import backup.BackUpBuilder;
import builder.ModelBuilder;
import database.*;
import filters.*;
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

    public void resetDB() {
        DBManager.deleteDB();
        DBManager.initializeDB();
    }

    public void loadTicketsFromAFIPFile(File f) {
        List<String[]> data;

        boolean issuedByMe = true;
        data = CSVUtils.readCSV(f, ModelBuilder.AFIPReceiverHeader);
        if (data == null) {
            issuedByMe = false;
            data = CSVUtils.readCSV(f, ModelBuilder.AFIPTransmitterHeader);
        }

        if (data != null) {
            File folder = new File("./");   //folder at local
            LocalTime currentTime = LocalTime.now();
            String filename = FixedData.getBackupFolderName("carga-tickets--" + currentTime + "--"
                    + currentTime.getHour() + "-" + currentTime.getMinute() + "\\");
            BackUpBuilder.saveBackup(folder, filename);
            ModelBuilder.buildFromAFIPData(data, issuedByMe);
        }
    }

    public void loadDollarPricesFromFile(File f) {
        List<String[]> data = CSVUtils.readCSV(f, ModelBuilder.DollarPriceHeader);
        Objects.requireNonNull(data, "null dollar price");
        ModelBuilder.buildDollarPricesFromData(data);
    }

    public void loadTicket(Object[] values) {
        ModelBuilder.buildTicket(values);
    }

    public Withholding loadWithholding(Object[] values) {
        return ModelBuilder.buildWithholding(values);
    }

    public void loadProvider(Object[] values) {
        ModelBuilder.buildProvider(values);
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

    // TODO: this method and the previous one can be the save, indeed we can generefy it taking a Map instead of
    //  one attribute and one value.
    public void deleteWithholdingAttribute(Filter filter, String attribute) {
        ModelSet<Withholding> withholdings = Filter.applyFilters(WithholdingDAO.getInstance().getAll(), filter);
        for (Withholding w : withholdings) {
            WithholdingDAO.getInstance().update(w, Collections.singletonMap(attribute, null));
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

    public void deleteProviderAttribute(Filter filter, String attribute) {
        ModelSet<Provider> providers = Filter.applyFilters(ProviderDAO.getInstance().getAll(), filter);
        for (Provider p : providers) {
            ProviderDAO.getInstance().update(p, Collections.singletonMap(attribute, null));
        }
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