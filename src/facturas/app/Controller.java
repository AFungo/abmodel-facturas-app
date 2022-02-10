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
import javax.swing.JTextField;

/**
 *
 * @author nacho
 */
public class Controller {
    
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
    
    //ticket is a boolean representing if the validation is for ticket or withholding
    public String validateParam(java.util.Date date, JComboBox<String> provider, JComboBox<String> docType
            , Map<String, String> values, boolean ticket) {
        String message = "<html>", invalidations = "";
        if (date == null) 
            invalidations += "<br/>Fecha no introducida";
        if (provider.getSelectedItem() == null) {
            if (docType.getSelectedItem() == null || values.get("docNo").isEmpty() || values.get("name").isEmpty()) {
                invalidations += "<br/>No se introdujeron datos sobre el proveedor";
            }
        }
        
        if (values.get("type").isEmpty())
            invalidations += "<br/>No se especifico el tipo de comprobante";
        
        if (ticket && values.get("exchangeMoney").isEmpty())
            invalidations += "<br/>No se introdujo el tipo de moneda";
        
        boolean[] numerics = FormatUtils.validTicketInput(values, ticket);
        invalidations += addInvalidNumerics(numerics);
        
        if (invalidations.isEmpty()) {
            return null;
        } else {
            invalidations += "</html>";
            return message + invalidations;
        }
    }
    
    private String addInvalidNumerics(boolean[] numerics) {
        String invalidations = "";
        if (!numerics[0])
            invalidations += "<br/>Numero de ticket mal escrito";
        if (!numerics[1])
            invalidations += "<br/>Numero de documento del proveedor mal escrito";
        if (!numerics[2])
            invalidations += "<br/>Importe total mal escrito";
        if (numerics.length == 3) //case of withholding
            return invalidations;
        //otherwise check for ticket inputs
        if (!numerics[3])
            invalidations += "<br/>Importe Op. Exentas mal escrito";
        if (!numerics[4])
            invalidations += "<br/>Tipo de cambio mal escrito";
        if (!numerics[5])
            invalidations += "<br/>Iva mal escrito";
        if (!numerics[6])
            invalidations += "<br/>Importe neto gravado mal escrito";
        if (!numerics[7])
            invalidations += "<br/>Importe neto no gravado mal escrito";
        
        return invalidations;
    }
    
    public Map<String, Float> getProfit(Map<String, Object> selectedFilters, boolean inDollars) {
        ProfitCalculator profit = new ProfitCalculator();
        List<Withholding> tickWi= getTickets(selectedFilters);
        tickWi.addAll(getWithholdings(selectedFilters));
        for(Withholding t : tickWi) {
            //if (t instanceof Ticket) {
            if (inDollars) {
                getDayPrice(t); 
                }
            if (t instanceof Ticket) profit.addTicket((Ticket)t, inDollars);
            else profit.addRetention(t, inDollars);
        }
        return profit.getValues();
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
        SQLFilter filters = new SQLFilter(selectedFilters);
        if (filters.isEmpty()) return getTickets();
        else return TicketDAO.getTickets(filters);            
    }

    public List<Withholding> getWithholdings(Map<String, Object> selectedFilters) {
        SQLFilter filters = new SQLFilter(selectedFilters);
        if(filters.isEmpty()) return WithholdingDAO.getWithholdings();
        else return WithholdingDAO.getWithholdings(filters);
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
    
    private void getDayPrice(Withholding t) {
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
    
    public void cleanTextField(JTextField[] textField){
            for(JTextField t : textField) t.setText("");
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
        return new Pair<List<String>,Boolean>(stringItems, issuedByMe);
    }
}