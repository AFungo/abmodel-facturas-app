package calculations;

import database.DollarPriceDAO;
import models.DollarPrice;
import models.Ticket;
import models.Withholding;
import utils.Pair;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

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
        if (DollarPriceDAO.getInstance().getAll().isEmpty() && inDollars) {
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
            Optional<DollarPrice> priceOptional = DollarPriceDAO.getInstance()
                    .getAll().stream().filter(d -> d.getValues().get("date").equals(ticketDate)).findFirst();
            if (priceOptional.isPresent()) {
                price = priceOptional.get();
            } else {
                price = getApproximatePrice(ticketDate);  //gets the price for the nearest date to ticketDate
                dayPriceMissing = true;
            }
            datePrices.put(ticketDate, price);  //set as price for ticketDate
        }

        t.setValues(Collections.singletonMap("dollarPrice", price));    //set price attribute on ticket
        if (dayPriceMissing) {
            checkDaysDistance(daysLimit, ticketDate, price);
        }
    }

    private DollarPrice getApproximatePrice(Date ticketDate) {
        List<DollarPrice> higherPrice = DollarPriceDAO.getInstance().getAll().stream()
                .filter(d -> ((Date)d.getValues().get("date")).compareTo(ticketDate) > 0).collect(Collectors.toList());
        List<DollarPrice> lowerPrice = DollarPriceDAO.getInstance().getAll().stream()
                .filter(d -> ((Date)d.getValues().get("date")).compareTo(ticketDate) < 0).collect(Collectors.toList());
        Optional<DollarPrice> minHigherPrice = higherPrice.stream().min(Comparator.comparing(d -> (Date)d.getValues().get("date")));
        Optional<DollarPrice> maxLowerPrice = lowerPrice.stream().max(Comparator.comparing(d -> (Date)d.getValues().get("date")));

        if (minHigherPrice.isPresent() && maxLowerPrice.isPresent()) {
            return getNearestDatePrice(minHigherPrice.get(), maxLowerPrice.get(), ticketDate);
        } else if (!minHigherPrice.isPresent() && maxLowerPrice.isPresent()) {
            return maxLowerPrice.get();
        } else if (minHigherPrice.isPresent()) {
            return minHigherPrice.get();
        } else {
            throw new IllegalStateException("No price loaded");
        }
    }

    private static DollarPrice getNearestDatePrice(DollarPrice priceBefore, DollarPrice priceAfter, Date currentDate) {
        Date dateBefore = (Date)priceBefore.getValues().get("date");
        Date dateAfter = (Date)priceAfter.getValues().get("date");
        //getting time of each date
        long currentTime = currentDate.getTime();
        long afterTime = dateAfter.getTime();
        long beforeTime = dateBefore.getTime();
        //getting difference between both dates
        long afterAbs = Math.abs(afterTime - currentTime);
        long beforeAbs = Math.abs(currentTime - beforeTime);
        //return the DollarPrice of the nearest date
        return afterAbs <= beforeAbs ? priceAfter : priceBefore;
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