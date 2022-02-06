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
import facturas.app.models.DollarPrice;
import facturas.app.models.Provider;
import facturas.app.models.Ticket;
import facturas.app.utils.FormatUtils;
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

/**
 *
 * @author nacho
 */
public class Controller {
    
    public void loadTickets(File f, boolean issuedByMe) {
        List<String> stringTickets = readCsv(f, "ticket");
            
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
    
    public void loadDollarPrices(File f) {
        List<String> stringPrices = readCsv(f, "price");

        List<DollarPrice> prices = new LinkedList<>();
        for (String priceStr : stringPrices) {
            prices.add(new DollarPrice(FormatUtils.dollarPriceCsvToDict(priceStr)));
        }
            
        for (DollarPrice p : prices) {
            DollarPriceDAO.addDollarPrice(p);
        }
    }
    
    public String validateTicketParam(java.util.Date date, JComboBox<String> provider, JComboBox<String> docType
            , Map<String, String> values) {
        String message = "<html>Los siguientes datos son invalidos: ", invalidations = "";
        if (date == null) 
            invalidations += "<br/>Fecha no introducida, ";
        if (provider.getSelectedItem() == null) {
            if (docType.getSelectedItem() == null || values.get("docNo").isEmpty() || values.get("name").isEmpty()) {
                invalidations += "<br/>No se introdujeron datos sobre el proveedor ";
            }
        }
        
        if (values.get("type").isEmpty())
            invalidations += "<br/>No se especifico el tipo de comprobante, ";
        
        if (values.get("exchangeMoney").isEmpty())
            invalidations += "<br/>No se introdujo el tipo de moneda, ";
        
        if (!FormatUtils.validTicketInput(values))
            invalidations += "<br/>Algunos de los valores numericos introducidos estan mal formateados";
        
        if (invalidations.isEmpty()) {
            return null;
        } else {
            invalidations += "</html>";
            return message + invalidations;
        }
    }
    
    public ProfitCalculator getProfit(Map<String, Object> selectedFilters, boolean inDollars) {
        ProfitCalculator profit = new ProfitCalculator();
        for(Ticket t : getTickets(selectedFilters)) {
            if (inDollars) {
                getDayPrice(t); 
            }
            profit.addTicket(t, inDollars);
        }
        return profit;
    }
    
    public void createTicket(String ticketData) {
        boolean issuedByMe = true;  //for now this will be fixed to true
        Ticket ticket = new Ticket(FormatUtils.ticketCsvToDict(ticketData, issuedByMe));
        TicketDAO.addTicket(ticket);
    }
    
    public List<Ticket> getTickets() {
        return TicketDAO.getTickets();
    }
    
    public List<Ticket> getTickets(Map<String, Object> selectedFilters) {
        SQLFilter filters = new SQLFilter(selectedFilters);
        if (filters.isEmpty()) {
            return getTickets();
        } else {
            return TicketDAO.getTickets(filters);
        }
    }
    
    public List<Provider> getProviders() {
        return ProviderDAO.getProviders();
    }
    public void changeAttribute(SQLFilter filter, String attribute, String value){
        TicketDAO.changeAttribute(filter, attribute, value);
    }
    private void getDayPrice(Ticket t) {
        Date ticketDate = (Date)t.getValues().get("date");
        DollarPrice price = DollarPriceDAO.getPrice(ticketDate);
        if (price == null) {
            price = DollarPriceDAO.getAproximatePrice(ticketDate);  //gets the price for the nearest date to ticketDate
        }
        
        t.addDollarPrice(price);
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
    private List<String> readCsv(File f, String type) {
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
        
        return stringItems;
    }
}