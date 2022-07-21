package controller;

import builder.ModelBuilder;
import database.*;
import filters.*;
import models.*;
import models.set.ModelSet;
import org.apache.commons.collections.map.SingletonMap;
import utils.FixedData;
import utils.FormatUtils;
import utils.Pair;
import calculations.PricesList;
import utils.Validate;
import utils.csv.CSVUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
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
    
    public String validateProviderParam(Map<String, Object> values, JComboBox<String> sectorsComboBox) {
        return Validate.providerInput(values, sectorsComboBox);
    }
    
    //ticket is a boolean representing if the validation is for ticket or withholding
    public String validateParam(java.util.Date date, Map<String, String> values, boolean ticket, 
        JComboBox<String> sectorsComboBox, List<Provider> selectedProvider) {

        String message = Validate.withholdingInput(date, sectorsComboBox, values, selectedProvider, ticket);
        if (ticket) {
            message += Validate.ticketInput(date, values);
        }
        
        if (!message.isEmpty()) {
            return "<html>" + message + "</html>";
        }

        return null;
    }
    
    public PricesList getProfit(SQLFilter ticketsFilters, SQLFilter withholdingsFilters, boolean inDollars) {
        List<Ticket> tickets = getTickets(ticketsFilters);
        List<Withholding> withholdings = getWithholdings(withholdingsFilters);
        //load tickets
        PricesList pricesList = new PricesList(inDollars);
        
        for(Withholding t : tickets) {
            pricesList.loadTicketValues((Ticket)t, daysLimit, inDollars);
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

    public void changeTicketAttribute(SQLFilter filter, String attribute, String value, boolean quotes) {
        //getting id of withholding
        SQLFilter withholdingFilter = FilterUtils.separateWithholdingFilter(filter);
        List<Withholding> withholdings = WithholdingDAO.get(withholdingFilter);
        filter.add("id", "=", withholdings.get(0).getValues().get("id"), Integer.class);
        
        TicketDAO.update(filter, attribute, value, quotes);
    }
    
    public void updateWithholding(Filter filter, String attribute, Object value) {
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

    public void deleteProviderAttribute(Filter filter, String attribute) {
        ModelSet<Provider> providers = Filter.applyFilters(ProviderDAO.getInstance().getAll(), filter);
        for (Provider p : providers) {
            ProviderDAO.getInstance().update(p, Collections.singletonMap(attribute, null));
        }
    }

    // TODO: for some reason the genius that implement this remove withholdings, why this method is not called remove
    //  withholding?, and the boolean isTicket was never used, then was removed.
    public void removeItem(Filter filter) {
        ModelSet<Withholding> withholdings = Filter.applyFilters(WithholdingDAO.getInstance().getAll(), filter);
        List<Boolean> results = withholdings.stream().map(w -> WithholdingDAO.getInstance().delete(w)).collect(Collectors.toList());
        if (results.stream().anyMatch(r -> r.equals(false))) {
            throw new IllegalArgumentException("Some items dont exists or can not be removed");
        }
    }

    public Ticket makeNegative(Ticket t) {
        Map<String, Object> values = FormatUtils.objectToStringMap(t.getValues());
        values.put("totalAmount", "-" + values.get("totalAmount"));
        if (values.get("netAmountWI") != null) values.put("netAmountWI","-" + values.get("netAmountWI"));
        if (values.get("netAmountWOI") != null) values.put("netAmountWOI","-" + values.get("netAmountWOI"));
        if (values.get("amountImpEx") != null) values.put("amountImpEx","-" + values.get("amountImpEx"));
        if (values.get("ivaTax") != null) values.put("ivaTax", "-" + values.get("ivaTax"));
        return new Ticket(values);
    }
    
    public void resetDB() {
            DBManager.deleteDB();
            DBManager.initializeDB();
    }

    public boolean updateProviderDoc(String newDoc, String oldDoc) {
        Optional<Provider> optionalProvider = ProviderDAO.getInstance().getAll().stream()
                .filter(p -> p.getValues().get("docNo").equals(oldDoc)).findFirst();
        return optionalProvider.filter(provider -> ProviderDAO.getInstance()
                .update(provider, Collections.singletonMap("docNo", newDoc)))
                .isPresent();
    }
    
    public void filterWithholding(SQLFilter filter, List<Withholding> tickets) {
        Pair<Float,Float> ivaBounds = FilterUtils.getFilterValues(filter, "iva");
        Pair<Float,Float> profitsBounds = FilterUtils.getFilterValues(filter, "profits");
        
        for (Withholding t : tickets) {
            filterWithholdingValue(t, "iva", ivaBounds.getFst(), ivaBounds.getSnd(),
                    w -> w.setValues(Collections.singletonMap("iva", null)));
            filterWithholdingValue(t, "profits", profitsBounds.getFst(), profitsBounds.getSnd(),
                    w -> w.setValues(Collections.singletonMap("profits", null)));
        }
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
    
    public void createBackup(File folder, String filename) {
        if (folder == null) {
            throw new IllegalArgumentException("File is null");
        }
        
        if (filename == null || filename.equals("")) {
            filename = FixedData.getBackupFolderName("backup");
        }
        //create backup folder (could be several backups in the folder)
        File backupFolder = new File(folder, filename);
        backupFolder.mkdir();
        
        //tickets backup
        backupData(backupFolder, () -> TicketDAO.get(), t -> FormatUtils.ticketToCsv(t), 
                FixedData.getTicketAppFormat(), "tickets");
        //withholdings backup
        backupData(backupFolder, () -> WithholdingDAO.getWithholdingsWithNoTicket(), 
                w -> FormatUtils.withholdingToCsv(w), FixedData.getWithholdingAppFormat(), "withholdings");
        //providers backup
        backupData(backupFolder, () -> ProviderDAO.get(), p -> FormatUtils.providerToCsv(p), 
                FixedData.getProviderAppFormat(), "providers");
        //sectors backup
        backupData(backupFolder, () -> SectorDAO.get(), s -> s + ";", FixedData.getSectorAppFormat(), "sectors");
        //dollar prices backup
        backupData(backupFolder, () -> DollarPriceDAO.get(), p -> FormatUtils.dollarPriceToCsv(p), 
                FixedData.getDollarPriceFileFormat(), "prices");
    }
    
    public void loadBackup(File folder) {
        if (folder == null) {
            throw new IllegalArgumentException("File is null");
        } else if (!folder.getName().contains("backup-")) {
            throw new IllegalArgumentException("Folder " + folder.getPath() + " is not a valid backup folder");
        }
        //load dollar prices
        loadBackupData(folder,"prices.csv", "price", e -> DollarPriceDAO.getInstance().save(e),
                s -> new DollarPrice(FormatUtils.dollarPriceCsvBackupToDict(s)));  //function removes the ; at the end of the sector name
        //load sectors
        loadBackupData(folder,"sectors.csv", "sectorBackup", e -> SectorDAO.getInstance().save(e),
                s -> new Sector(Collections.singletonMap("name", "AMONGAS")));  //function removes the ; at the end of the sector name
        //load providers
        loadBackupData(folder,"providers.csv", "providerBackup", e -> ProviderDAO.getInstance().save(e),
                s -> new Provider(FormatUtils.providerCsvBackupToDict(s)));
        //load tickets
        loadBackupData(folder,"tickets.csv", "ticketBackup", e -> TicketDAO.getInstance().save(e),
                s -> new Ticket(FormatUtils.ticketCsvBackupToDict(s)));
        //load withholdings
        loadBackupData(folder,"withholdings.csv", "withholdingBackup", e -> WithholdingDAO.getInstance().save(e),
                s -> new Withholding(FormatUtils.withholdingCsvBackupToDict(s)));
    }
    
    private <E> void loadBackupData(File parentFolder, String filename, String formatId, Consumer<E> loadDAO,
            Function<String,E> formater) {
        File fileCsv = new File(parentFolder, filename);
        if (fileCsv.exists()) {
            List<String> itemData = readCsv(fileCsv, formatId).getFst();
            for (String s : itemData) {
                E e = formater.apply(s);
                try {
                    loadDAO.accept(e);  //we don't care about repeated item exceptions
                } catch (IllegalStateException ex) {//if the exception is not for repeated item throw it
                    if (!ex.getMessage().contains("<23505> duplicate item")) {
                        throw ex;
                    }
                }
            }
        }
    }
    
    private <E> void backupData(File backupFolder, Supplier<List<E>> dao, Function<E,String> formater, 
            String format, String itemName) {
        List<E> items = dao.get();
        if (items.isEmpty()) {   //if there is no items we don't save anything
            System.out.println("no " + itemName + " to backup");
            return ;
        }
        File dataBackup = createFile(backupFolder, itemName + ".csv");
        writeToFile(dataBackup, formater, format, items, itemName);
    }
    
    private File createFile(File parentFolder, String filename) {
        File file = new File(parentFolder, filename);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            throw new IllegalStateException("failed to create file at : " + file.getAbsolutePath() + "\n" + ex);
        }
        return file;
    }
    
    private <E> void writeToFile(File fileToWrite, Function<E,String> formater, String format, List<E> items, 
            String itemName) {
        Writer writer;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(fileToWrite), StandardCharsets.UTF_8);
            writer.write(format);    //first line gives format to be identified at loading
            for (E s : items) {
                writer.append("\n" + formater.apply(s));
            }
            writer.close();
            System.out.println(itemName + " backup done succesfully");
            
        } catch (IOException ex) {
            throw new IllegalStateException("failed to write on file: " + fileToWrite.getAbsolutePath() + "\n" + ex.toString());
        }
    }

}