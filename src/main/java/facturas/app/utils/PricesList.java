/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.database.DollarPriceDAO;
import facturas.app.models.DollarPrice;
import facturas.app.models.Ticket;
import facturas.app.models.Withholding;
import java.sql.Date;
import java.util.*;

/**
 * class for manage the price of the dollar
 * 
 */
public class PricesList {
    //values such iva, total amount, etc
    private ProfitCalculator calculator;
    //prices of the already checked days to reduce database calls
    private Map<Date,DollarPrice> datePrices;
    //list of prices that exceed the days limit
    private List<Pair<Date,String>> missingPrices;
    
    /**
     * constructor of the class
     * @param inDollars 
     */
    public PricesList(boolean inDollars) {
        calculator = new ProfitCalculator ();
        datePrices = new HashMap<> ();
        missingPrices = new LinkedList();
        if (DollarPriceDAO.isEmpty() && inDollars) {
            throw new IllegalStateException("No dollar prices loaded");
        }
    }
    
    /**
     * @return a map with the values
     */
    public Map<String,Float> getValues() {
        return calculator.getValues();
    }
    
    /**
     * @return a list of pairs with the missing prices
     */
    public List<Pair<Date,String>> getMissingPrices() {
        return missingPrices;
    }
    
    /**
     * load the ticket values in a ProfitCalculator with the amounts in dollars or  in pesos
     * @param t a ticket for load
     * @param daysLimit maximum days betwen missing dollars prices
     * @param inDollars if we want the amounts in dollars or not
     */
    public void loadTicketValues(Ticket t, int daysLimit, boolean inDollars) {
        if (!inDollars) {
            calculator.addTicket((Ticket)t);
        } else {
            Float exchangeType = (Float)t.getValues().get("exchangeType");
            if (exchangeType == 1.0f) { //exchange type is in pesos
                setDollarPrice(t, daysLimit);
            }

            calculator.addTicketInDollars((Ticket)t);
        }
    }
    
    /**
     * load the withholding values in a ProfitCalculator with the amounts in dollars or in pesos
     * @param w withholding who want to charge in profit calculator
     * @param daysLimit maximum days between missing dollar prices
     * @param inDollars if we want the amount in dollars or not
     */
    public void loadPriceInWithholding(Withholding w, int daysLimit, boolean inDollars) {
        if (inDollars) {
            setDollarPrice(w, daysLimit);
        }
        calculator.addRetention(w, inDollars);
    }
    
    /*
    * set the dollar price in days who the price is missed
    */
    private void setDollarPrice(Withholding t, int daysLimit) {
        Date ticketDate = (Date)t.getValues().get("date");
        DollarPrice price = datePrices.get(ticketDate);
        boolean dayPriceMissing = false;
        
        if (price == null) {    //take price from database and load it in hashmap
             price = DollarPriceDAO.get(ticketDate);
             if (price == null) {
                price = DollarPriceDAO.getAproximatePrice(ticketDate);  //gets the price for the nearest date to ticketDate
                dayPriceMissing = true;
            }
            datePrices.put(ticketDate, price);  //set as price for ticketDate
        }
        
        t.setValues(Collections.singletonMap("dollarPrice", price));    //set price attribute on ticket
        if (dayPriceMissing) {
            checkDaysDistance(daysLimit, ticketDate, price);
        }
    }
    
    /**
     * check the days distance between missed dollars prices  
     */
    private void checkDaysDistance(int daysLimit, Date ticketDate, DollarPrice price) {
        long limit = 86400000L * (daysLimit + 1);
        long currentTime = ticketDate.getTime();
        long nearestTime = ((Date) price.getValues().get("date")).getTime();
        long timeDiference = Math.abs(nearestTime - currentTime);
        if (timeDiference >= limit) {
            missingPrices.add(new Pair<Date,String> (ticketDate, Long.toString(timeDiference / 86400000)));
        }
    }
    
}