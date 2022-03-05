/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import facturas.app.database.DBManager;
import facturas.app.database.DollarPriceDAO;
import facturas.app.database.ProviderDAO;
import facturas.app.database.SQLFilter;
import facturas.app.database.TicketDAO;
import facturas.app.database.WithholdingDAO;
import facturas.app.models.DollarPrice;
import facturas.app.models.Provider;
import facturas.app.models.Ticket;
import facturas.app.models.Withholding;
import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;
import facturas.app.utils.PricesList;
import facturas.app.utils.Validate;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    
    public void loadTickets(File f) {
        Pair<List<String>,Boolean> csvContent = readCsv(f, "ticket");
        List<String> stringTickets = csvContent.getFst();
        boolean issuedByMe = csvContent.getSnd();
        
        List<Ticket> tickets = new LinkedList<>();
        for (String strTicket : stringTickets) {
            tickets.add(new Ticket(FormatUtils.ticketCsvToDict(strTicket, issuedByMe)));
        }

        tickets.forEach((ticket) -> {
            createTicketOnDB(ticket);
        });
    }
    
    public void loadTicket(Map<String, String> values) {
        createTicketOnDB(new Ticket(values));
    }
    
    public void loadWithholding(Map<String, String> values) {
        WithholdingDAO.addWithholding(new Withholding(values));
    }
    
    public void loadDollarPrices(File f) {
        List<String> stringPrices = readCsv(f, "price").getFst();

        List<DollarPrice> prices = new LinkedList<>();
        for (String priceStr : stringPrices) {
            prices.add(new DollarPrice(FormatUtils.dollarPriceCsvToDict(priceStr)));
        }
            
        for (DollarPrice p : prices) {
            DollarPriceDAO.addDollarPrice(p);
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
        if (filters.isEmpty()) return TicketDAO.getTickets();
        else return TicketDAO.getTickets(filters);            
    }

    public List<Withholding> getWithholdings(SQLFilter selectedFilters) {
        if(selectedFilters.isEmpty()) return WithholdingDAO.getWithholdings();
        else return WithholdingDAO.getWithholdings(selectedFilters);
    }
   
    public List<Provider> getProviders() {
        return ProviderDAO.getProviders();
    }

    public void changeTicketAttribute(SQLFilter filter, String attribute, String value){
        TicketDAO.changeAttribute(filter, attribute, value);
    }
    
    public void changeWithholdingAttribute(SQLFilter filter, String attribute, String value){
        WithholdingDAO.changeAttribute(filter, attribute, value);
    }
    
    public void deleteWithholdingAttribute(SQLFilter filter, String attribute){
        WithholdingDAO.deleteAttribute(filter, attribute);
    }

    public void deleteProviderAttribute(SQLFilter filter, String attribute){
        ProviderDAO.deleteAttribute(filter, attribute);
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
        return ProviderDAO.getProviders(filters);
    }

    public void changeAttributeProviderDAO(SQLFilter filters, String attribute, String value){
        ProviderDAO.changeAttribute(filters, attribute, value);
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
        String id = WithholdingDAO.addWithholding(ticket);
        ticket.addId(id);
        TicketDAO.addTicket(ticket);
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
        ProviderDAO.addProvider(provider);
    }
    
    private Pair<List<String>,Boolean> readCsv(File f, String type) {
        if (f == null) {
            throw new IllegalArgumentException("File is null");
        }
            
        List<String> stringItems = new LinkedList<>();
        try {
            stringItems = Files.readAllLines(f.toPath(), Charset.defaultCharset());
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