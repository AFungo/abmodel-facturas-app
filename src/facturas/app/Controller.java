/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import concurrency.Lock;
import facturas.app.database.DBManager;
import facturas.app.database.DollarPriceDAO;
import facturas.app.database.ProviderDAO;
import facturas.app.database.SQLFilter;
import facturas.app.database.SectorDAO;
import facturas.app.database.TicketDAO;
import facturas.app.database.WithholdingDAO;
import facturas.app.models.DollarPrice;
import facturas.app.models.Provider;
import facturas.app.models.Ticket;
import facturas.app.models.Withholding;
import facturas.app.utils.FilterUtils;
import facturas.app.utils.FixedData;
import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;
import facturas.app.utils.PricesList;
import facturas.app.utils.Validate;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.Consumer;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author nacho
 */
public class Controller {
    
    private final int daysLimit = 4;
    
    public int getDaysLimit() {
        return daysLimit;
    }
    
    //the lock comes already locked
    public void loadTickets(File f, Lock backupLock) {
        try {
            Pair<List<String>,Boolean> csvContent = readCsv(f, "ticket");
            List<String> stringTickets = csvContent.getFst();
            boolean issuedByMe = csvContent.getSnd();

            List<Ticket> tickets = new LinkedList<>();
            for (String strTicket : stringTickets) {
                tickets.add(new Ticket(FormatUtils.ticketCsvToDict(strTicket, issuedByMe)));
            }
            backupLock.unlock();
            backupLock.lock();
            tickets.forEach((ticket) -> {
                try {
                    createTicketOnDB(ticket);
                } catch (IllegalStateException ex) {//if the exception is not for repeated item throw it
                    if (!ex.getMessage().contains("<23505> duplicate item")) {
                        throw ex;
                    }
                }
            });
        } catch (Exception ex) {
            backupLock.fail(ex);
        } finally {
            backupLock.finalUnlock();
        }
    }
    
    public void loadTicket(Map<String, String> values) {
        createTicketOnDB(new Ticket(values));
    }
    
    public void loadWithholding(Map<String, String> values) {
        WithholdingDAO.add(new Withholding(values));
    }
    
    public void loadDollarPrices(File f) {
        List<String> stringPrices = readCsv(f, "price").getFst();

        List<DollarPrice> prices = new LinkedList<>();
        for (String priceStr : stringPrices) {
            prices.add(new DollarPrice(FormatUtils.dollarPriceCsvToDict(priceStr)));
        }
            
        for (DollarPrice p : prices) {
            try {
                    DollarPriceDAO.add(p);
                } catch (IllegalStateException ex) {//if the exception is not for repeated item throw it
                    System.out.println("se repitio un item");
                    if (!ex.getMessage().contains("<23505> duplicate item")) {
                        throw ex;
                    }
                }
        }
    }
    
    public String validateProviderParam(Map<String, String> values, JComboBox<String> sectorsComboBox){
        String message = Validate.providerInput(values, sectorsComboBox);
        return message;
    }
    
    //ticket is a boolean representing if the validation is for ticket or withholding
    public String validateParam(java.util.Date date, Map<String, String> values, boolean ticket, 
        JComboBox<String> sectorsComboBox, List<Provider> selectedProvider) {

        String message = Validate.withholdingInput(date, sectorsComboBox, values, selectedProvider);
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
    
    public void createTicket(String ticketData) {
        boolean issuedByMe = true;  //for now this will be fixed to true
        Ticket ticket = new Ticket(FormatUtils.ticketCsvToDict(ticketData, issuedByMe));
        createTicketOnDB(ticket);
    }

    public List<Ticket> getTickets(SQLFilter filters) {
        if (filters.isEmpty()) return TicketDAO.get();
        else return TicketDAO.get(filters);            
    }

    public List<Withholding> getWithholdings(SQLFilter selectedFilters) {
        if(selectedFilters.isEmpty()) return WithholdingDAO.get();
        else return WithholdingDAO.get(selectedFilters);
    }
   
    public List<Provider> getProviders() {
        return ProviderDAO.get();
    }

    public void changeTicketAttribute(SQLFilter filter, String attribute, String value, boolean quotes){
        //getting id of withholding
        SQLFilter withholdingFilter = FilterUtils.separateWithholdingFilter(filter);
        List<Withholding> withholdings = WithholdingDAO.get(withholdingFilter);
        filter.add("id", "=", withholdings.get(0).getValues().get("id"), Integer.class);
        
        TicketDAO.update(filter, attribute, value, quotes);
    }
    
    public void changeWithholdingAttribute(SQLFilter filter, String attribute, String value, boolean quotes){
        WithholdingDAO.update(filter, attribute, value, quotes);
    }
    
    public void deleteWithholdingAttribute(SQLFilter filter, String attribute){
        WithholdingDAO.setNull(filter, attribute);
    }

    public void deleteProviderAttribute(SQLFilter filter, String attribute){
        ProviderDAO.setNull(filter, attribute);
    }
    
    public void removeItem(SQLFilter filter, boolean isTicket) {
            WithholdingDAO.remove(filter);
    }
    public Ticket makeNegative(Ticket t){
        Map<String, String> values = FormatUtils.objectToStringMap(t.getValues());
        values.put("totalAmount", "-" + values.get("totalAmount"));
        if (values.get("netAmountWI") != null) values.put("netAmountWI","-" + values.get("netAmountWI"));
        if (values.get("netAmountWOI") != null) values.put("netAmountWOI","-" + values.get("netAmountWOI"));
        if (values.get("amountImpEx") != null) values.put("amountImpEx","-" + values.get("amountImpEx"));
        if (values.get("ivaTax") != null) values.put("ivaTax", "-" + values.get("ivaTax"));
        return new Ticket(values);
    }
    
    public void resetDB(){
            DBManager.deleteDB();
            DBManager.initializeDB();
    }

    public List<Provider> getProviders(SQLFilter filters){
        return ProviderDAO.get(filters);
    }

    public void changeProviderAttribute(SQLFilter filters, String attribute, String value) {
        ProviderDAO.update(filters, attribute, value);
    }
    
    public JTable createMissingPricesTable(List<Pair<Date,String>> data) {
        int length = data.size(), i = 0;
        Object[][] rows = new Object[length][2];
        for (Pair<Date,String> p : data) {
            rows[i][0] = p.getFst();
            rows[i][1] = p.getSnd();
            i++;
        }
        Object[] cols = {"Fecha","Dias hasta el precio mas cercano"};
        JTable table = new JTable(rows, cols);
        table.setDefaultEditor(Object.class, null);
        table.setCellSelectionEnabled(true);
        return table;
    }
    
    private void createTicketOnDB(Ticket ticket) {
        String id = WithholdingDAO.add(ticket);
        ticket.addId(id);
        TicketDAO.add(ticket);
    }
    
    public boolean updateProviderDoc(String newDoc, String oldDoc) {
        //looking if new doc is not used already
        SQLFilter filter = new SQLFilter();
        filter.add("docNo", "=", newDoc, String.class);
        if (ProviderDAO.exist(filter)) {
            return false;
        }
        //creation of new provider
        filter.removeCondition("docNo");
        filter.add("docNo", "=", oldDoc, String.class);
        Provider prov = ProviderDAO.get(filter).get(0);
        prov.modifyDocNo(newDoc);
        ProviderDAO.add(prov);
        //moving tickets from old doc to new doc
        SQLFilter withholdingFilter = new SQLFilter();
        withholdingFilter.add("providerDoc", "=", oldDoc, String.class);
        WithholdingDAO.update(withholdingFilter, "providerDoc", newDoc, true);
        //deleting old provider
        ProviderDAO.delete(filter);
        
        return true;
    }
    
    public void filterWithholdings(SQLFilter filter, List<Withholding> tickets) {
        Pair<Float,Float> ivaBounds = FilterUtils.getFilterValues(filter, "iva");
        Pair<Float,Float> profitsBounds = FilterUtils.getFilterValues(filter, "profits");
        
        for (Withholding t : tickets) {
            filterWithholdingValue(t, "iva", ivaBounds.getFst(), ivaBounds.getSnd(), w -> w.deleteIva());
            filterWithholdingValue(t, "profits", profitsBounds.getFst(), profitsBounds.getSnd(), w -> w.deleteProfits());
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
                    return;
                }
            }
        }
    }
    
    public JTable createTableToDelete(Object[] rowToDelete) {
        Object[][] rows = {rowToDelete};
        Object[] cols = {"id", "Fecha", "Tipo", "Nro factura", "Denominación Emisor", "Imp. Total"};
        JTable table = new JTable(rows, cols);
        table.setDefaultEditor(Object.class, null);
        table.setCellSelectionEnabled(true);
        return table;
    }
    
    public void cleanTextField(JTextField[] textField){
            for(JTextField t : textField) t.setText("");
    }
    
    public void addProvider(Map<String, String> values){
        Provider provider = new Provider(values);
        ProviderDAO.add(provider);
    }
    
    public boolean providerHasTickets(String docNo) {
        SQLFilter filter = new SQLFilter();
        filter.add("providerDoc", "=", docNo, String.class);
        return WithholdingDAO.exist(filter);
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
        System.out.println("file at: " + backupFolder.getAbsolutePath() + "\nand: " + backupFolder.getPath());
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
        loadBackupData(folder,"prices.csv", "price", e -> DollarPriceDAO.add(e), 
                s -> new DollarPrice(FormatUtils.dollarPriceCsvBackupToDict(s)));  //function removes the ; at the end of the sector name
        //load sectors
        loadBackupData(folder,"sectors.csv", "sectorBackup", e -> SectorDAO.add(e), 
                s -> s.split(";")[0]);  //function removes the ; at the end of the sector name
        //load providers
        loadBackupData(folder,"providers.csv", "providerBackup", e -> ProviderDAO.add(e), 
                s -> new Provider(FormatUtils.providerCsvBackupToDict(s)));
        //load tickets
        loadBackupData(folder,"tickets.csv", "ticketBackup", e -> createTicketOnDB(e), 
                s -> new Ticket(FormatUtils.ticketCsvBackupToDict(s)));
        //load withholdings
        loadBackupData(folder,"withholdings.csv", "withholdingBackup", e -> WithholdingDAO.add(e), 
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
            throw new IllegalStateException("failed to create file at : " + file.getAbsolutePath() + "\n" + ex.toString());
        }
        return file;
    }
    
    private <E> void writeToFile(File fileToWrite, Function<E,String> formater, String format, List<E> items, 
            String itemName) {
        FileWriter writer;
        try {
            writer = new FileWriter(fileToWrite);
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
    
    private Pair<List<String>,Boolean> readCsv(File f, String type) {
        if (f == null) {
            throw new IllegalArgumentException("File is null");
        }
            
        List<String> stringItems = new LinkedList<>();
        try {
            stringItems = Files.readAllLines(f.toPath(), Charset.forName("UTF-8"));
        } catch (IOException ex) {
            throw new IllegalStateException(ex.toString());
        }
        
        String initialLine = stringItems.remove(0); //Remove the first row of the file for checking
        if (!FormatUtils.validFormat(initialLine, type)) {
            throw new IllegalArgumentException("File does not have a valid format to be loaded\nFile: " + f.getPath());
        }
        
        Boolean issuedByMe = initialLine.contains("Receptor");
        return new Pair<>(stringItems, issuedByMe);
    }
}