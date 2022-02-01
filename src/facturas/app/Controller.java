/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

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

/**
 *
 * @author nacho
 */
public class Controller {
    
    public void loadTickets(File f) {
        List<String> stringTickets = readCsv(f, "ticket");
            
        List<Ticket> tickets = new LinkedList<>();
        for (String strTicket : stringTickets) {
            boolean issuedByMe = true;  //for now this will be fixed to true
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
    
    private void getDayPrice(Ticket t) {
        Date ticketDate = (Date)t.getValues().get("date");
        DollarPrice price = DollarPriceDAO.getPrice(ticketDate);
        if (price == null) {
            price = DollarPriceDAO.getAproximatePrice(ticketDate);  //gets the price for the nearest date to ticketDate
        }
        
        t.addDollarPrice(price);
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