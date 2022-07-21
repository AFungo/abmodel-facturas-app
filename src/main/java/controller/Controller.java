package controller;

import builder.ModelBuilder;
import database.*;
import filters.*;
import models.*;
import models.set.ModelSet;
import utils.Pair;
import calculations.PricesList;
import utils.csv.CSVUtils;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 * @author nacho
 */
public class Controller {
    
    private final int daysLimit = 4;
    
    public int getDaysLimit() {
        return daysLimit;
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
            // TODO: backup
            ModelBuilder.buildFromAFIPData(data, issuedByMe);
        }
    }

    public void loadTicket(Object[] values) {
        ModelBuilder.buildTicket(values);
    }

    public void loadWithholding(Object[] values) {
        ModelBuilder.buildWithholding(values);
    }

    public void loadDollarPrices(File f) {
        List<String[]> data = CSVUtils.readCSV(f, ModelBuilder.DollarPriceHeader);
        Objects.requireNonNull(data, "null dollar price");
        ModelBuilder.buildDollarPricesFromData(data);
    }

    public PricesList getProfit(Filter ticketsFilters, Filter withholdingsFilters, boolean inDollars) {
        List<Ticket> tickets = getTickets(ticketsFilters);
        List<Withholding> withholdings = getWithholdings(withholdingsFilters);
        //load tickets
        PricesList pricesList = new PricesList(inDollars);
        
        for(Ticket t : tickets) {
            pricesList.loadTicketValues(t, daysLimit, inDollars);
        }
        //load withholdings
        for (Withholding w : withholdings) {
            pricesList.loadPriceInWithholding(w, daysLimit, inDollars);
        }
        return pricesList;
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
        throw new UnsupportedOperationException("Implement if needed");
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

    public void addProvider(Map<String, Object> values) {
        Provider provider = new Provider(values);
        ProviderDAO.getInstance().save(provider);
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

    public void resetDB() {
        DBManager.deleteDB();
        DBManager.initializeDB();
    }

    public void filterWithholding(Filter filter, List<Withholding> tickets) {
        /* TODO: think what we going to do with the double withholdings
        Pair<Float,Float> ivaBounds = FilterUtils.getFilterValues(filter, "iva");
        Pair<Float,Float> profitsBounds = FilterUtils.getFilterValues(filter, "profits");
        
        for (Withholding t : tickets) {
            filterWithholdingValue(t, "iva", ivaBounds.getFst(), ivaBounds.getSnd(),
                    w -> w.setValues(Collections.singletonMap("iva", null)));
            filterWithholdingValue(t, "profits", profitsBounds.getFst(), profitsBounds.getSnd(),
                    w -> w.setValues(Collections.singletonMap("profits", null)));
        }
         */
    }
    
    private void filterWithholdingValue(Withholding w, String attr, Float minVal, Float maxVal, 
            Consumer<Withholding> deleteAttr) {
        Map<String,Object> values = w.getValues();
        
        if (values.get(attr) != null) {
            Float val = (Float) values.get(attr);
            if (minVal != null) {
                if (val < minVal) {
                    deleteAttr.accept(w);
                    return;
                }
            }
            if (maxVal !=  null) {
                if (maxVal < val) {
                    deleteAttr.accept(w);
                }
            }
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