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
import facturas.app.utils.ProfitCalculator;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            TicketDAO.addTicket(ticket);
        });
    }
    
    public void loadTicket(Map<String, String> values) {
        TicketDAO.addTicket(new Ticket(values));
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
        String message = "<html>", invalidations = "";
        List<String> sectors = getItemsFromComboBox(sectorsComboBox);
                
        if(values.get("name").isEmpty()){ invalidations += "<br/> Nombre no introducido";}
        if(values.get("docNo").isEmpty()){
            invalidations += "<br/> Numero documento no introducido";
        } else if (!FormatUtils.tryParse(values.get("docNo"), "Integer")){ 
            invalidations += "<br/> Numero documento mal escrito";
        }
        
        if(values.get("docType").isEmpty()){ invalidations += "<br/> Tipo de documento no introdcido";}
        
        String providerSector = values.get("provSector"); //if not null or empty and doesn't exists
        if (providerSector != null && (!providerSector.isEmpty()) && (!sectors.contains(providerSector))) {
            invalidations += "<br/>El rubro del comprobante no existe";
        }
        if (invalidations.isEmpty()) {
            return null;
        } else {
            invalidations += "</html>";
            return message + invalidations;
        }
    }
    
    //ticket is a boolean representing if the validation is for ticket or withholding
    public String validateParam(java.util.Date date, Map<String, String> values, boolean ticket, 
            JComboBox<String> sectorsComboBox, List<Provider> selectedProvider) {
        
        List<String> sectors = getItemsFromComboBox(sectorsComboBox);
        String message = "<html>", invalidations = "";
        if (date == null) 
            invalidations += "<br/>Fecha no introducida";
        if (selectedProvider.isEmpty())
            invalidations += "<br/>El proveedor elegido no existe";
        
        String ticketSector = values.get("sector"); //if not null or empty and doesn't exists
        if (ticketSector != null && (!ticketSector.isEmpty()) && (!sectors.contains(ticketSector)))
            invalidations += "<br/>El rubro del comprobante no existe";
        
        if (values.get("type") == null || values.get("type").isEmpty())
            invalidations += "<br/>No se especifico el tipo de comprobante";
        
        if (ticket && values.get("exchangeMoney").isEmpty())
            invalidations += "<br/>No se introdujo el tipo de moneda";
        
        boolean[] numerics = FormatUtils.validTicketInput(values, ticket);
        invalidations += addInvalidNumerics(numerics, ticket);
        
        if (invalidations.isEmpty()) {
            return null;
        } else {
            invalidations += "</html>";
            return message + invalidations;
        }
    }
    
    private String addInvalidNumerics(boolean[] numerics, boolean ticket) {
        String invalidations = "";
        int i = 0;
        if (!numerics[i++])
            invalidations += "<br/>Numero de ticket mal escrito";
        if (!numerics[i++])
            invalidations += "<br/>Importe total mal escrito";
        if (!ticket) //case of withholding
            return invalidations;
        //otherwise check for ticket inputs
        if (!numerics[i++])
            invalidations += "<br/>Importe Op. Exentas mal escrito";
        if (!numerics[i++])
            invalidations += "<br/>Tipo de cambio mal escrito";
        if (!numerics[i++] || !numerics[i++] || !numerics[i++])
            invalidations += "<br/>algunos de los Ivas mal escritos";
        if (!numerics[i++])
            invalidations += "<br/>Importe neto gravado mal escrito";
        if (!numerics[i++])
            invalidations += "<br/>Importe neto no gravado mal escrito";
        
        return invalidations;
    }
    
    private List<String> getItemsFromComboBox(JComboBox<String> sectorsComboBox) {
        List<String> items = new LinkedList<>();
        for (int i = 0; i < sectorsComboBox.getItemCount(); i++) {
            items.add(sectorsComboBox.getItemAt(i));
        }
        return items;
    }
    
    public Pair<Map<String,Float>,List<Pair<Date,String>>> getProfit(Map<String, Object> selectedFilters, boolean inDollars) {
        ProfitCalculator profit = new ProfitCalculator();
        List<Withholding> tickWi= getTickets(selectedFilters);
        tickWi.addAll(getWithholdings(selectedFilters));
        
        List<Pair<Date,String>> missingDays = new LinkedList<>();
        for(Withholding t : tickWi) {
            if (inDollars) {
                Pair<Date,String> miss = getDayPrice(t);
                if (miss != null) missingDays.add(miss);
            }
            if (t instanceof Ticket) profit.addTicket((Ticket)t, inDollars);
            else profit.addRetention(t, inDollars);
        }
        return new Pair<> (profit.getValues(), missingDays);
    }
    
    public void createTicket(String ticketData) {
        boolean issuedByMe = true;  //for now this will be fixed to true
        Ticket ticket = new Ticket(FormatUtils.ticketCsvToDict(ticketData, issuedByMe));
        TicketDAO.addTicket(ticket);
    }
    
    public List<Withholding> getTickets() {
        return TicketDAO.getTickets();
    }

    public List<Withholding> getWithholdings() {
        return WithholdingDAO.getWithholdings();
    }
    
    public List<Withholding> getTickets(Map<String, Object> selectedFilters) {
        SQLFilter filters = new SQLFilter(selectedFilters, true);
        if (filters.isEmpty()) return getTickets();
        else return TicketDAO.getTickets(filters);            
    }

    public List<Withholding> getTickets(SQLFilter filters) {
        if (filters.isEmpty()) return getTickets();
        else return TicketDAO.getTickets(filters);            
    }

    public List<Withholding> getWithholdings(Map<String, Object> selectedFilters) {
        SQLFilter filters = new SQLFilter(selectedFilters, false);
        if(filters.isEmpty()) return WithholdingDAO.getWithholdings();
        else return WithholdingDAO.getWithholdings(filters);
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
    
    public void removeItem(SQLFilter filter, boolean isTicket) {
        if (isTicket) {
            TicketDAO.remove(filter);
        } else {
            WithholdingDAO.remove(filter);
        }
    }
    
    private Pair<Date,String> getDayPrice(Withholding t) {
        Date ticketDate = (Date)t.getValues().get("date");
        DollarPrice price = DollarPriceDAO.getPrice(ticketDate);
        if (price == null) {
            price = DollarPriceDAO.getAproximatePrice(ticketDate);  //gets the price for the nearest date to ticketDate
        }
        t.addDollarPrice(price);
        
        long limit = 86400000 * (daysLimit + 1);
        long currentTime = ticketDate.getTime();
        long nearestTime = price.getDate().getTime();
        long timeDiference = Math.abs(nearestTime - currentTime);
        if (timeDiference >= limit) {
            //System.out.println("Dollar price is too far away from the limit by " + (timeDiference / 86400000) + " days");
            return new Pair<Date,String> (ticketDate, Long.toString(timeDiference / 86400000));
        }
        return null;
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
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String initialLine = stringItems.remove(0); //Remove the first row of the file for checking
        if (!FormatUtils.validFormat(initialLine, type)) {
            throw new IllegalArgumentException("File does not have a valid format to be loaded\nFile: " + f.getPath());
        }
        
        Boolean issuedByMe = initialLine.contains("Receptor");
        return new Pair<>(stringItems, issuedByMe);
    }
}