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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nacho
 */
public class PricesList {
    //values such iva, total amount, etc
    private ProfitCalculator calculator;
    //prices of the already checked days to reduce database calls
    private Map<Date,DollarPrice> datePrices;
    //list of prices that exceed the days limit
    private List<Pair<Date,String>> missingPrices;
    
    public PricesList(boolean inDollars) {
        calculator = new ProfitCalculator ();
        datePrices = new HashMap<> ();
        missingPrices = new LinkedList();
        if (DollarPriceDAO.isEmpty() && inDollars) {
            throw new IllegalStateException("No dollar prices loaded");
        }
    }
    
    public Map<String,Float> getValues() {
        return calculator.getValues();
    }
    
    public List<Pair<Date,String>> getMissingPrices() {
        return missingPrices;
    }
    
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
    
    public void loadPriceInWithholding(Withholding w, int daysLimit, boolean inDollars) {
        if (inDollars) {
            setDollarPrice(w, daysLimit);
        }
        calculator.addRetention(w, inDollars);
    }
    
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
        
        t.addDollarPrice(price);    //set price attribute on ticket
        if (dayPriceMissing) {
            checkDaysDistance(daysLimit, ticketDate, price);
        }
    }
    
    private void checkDaysDistance(int daysLimit, Date ticketDate, DollarPrice price) {
        long limit = 86400000 * (daysLimit + 1);
        long currentTime = ticketDate.getTime();
        long nearestTime = price.getDate().getTime();
        long timeDiference = Math.abs(nearestTime - currentTime);
        if (timeDiference >= limit) {
            missingPrices.add(new Pair<Date,String> (ticketDate, Long.toString(timeDiference / 86400000)));
        }
    }
    
}