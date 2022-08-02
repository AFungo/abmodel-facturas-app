package calculations;

import database.DollarPriceDAO;
import models.DollarPrice;
import models.Ticket;
import models.Withholding;
import utils.Pair;

import java.sql.Date;
import java.util.*;

/**
 * class for manage the price of the dollar
 *
 */
public class PricesList {
    //values such iva, total amount, etc
    private ProfitCalculator calculator;
    //list of prices that exceed the days limit
    private Set<Pair<Date,String>> missingPrices;
    private boolean inDollars;

    /**
     * constructor of the class
     * @param inDollars
     */
    public PricesList(boolean inDollars) {
        calculator = new ProfitCalculator();
        missingPrices = new HashSet<>();
        this.inDollars = inDollars;
        if (DollarPriceDAO.getInstance().getAll().isEmpty() && inDollars) {
            throw new IllegalStateException("No dollar prices loaded");
        }
    }

    public void calculateSummary(List<Ticket> tickets, List<Withholding> withholdings) {
        //load tickets
        for(Ticket t : tickets) {
            if (inDollars) {
                loadTicketValuesInDollars(t);
            } else {
                loadTicketValuesInPesos(t);
            }
        }
        //load withholdings
        for (Withholding w : withholdings) {
            if (inDollars) {
                loadWithholdingValuesInDollars(w);
            } else {
                loadWithholdingValuesInPesos(w);
            }
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
        return new LinkedList<Pair<Date, String>>(missingPrices);
    }

    /**
     * load the ticket values in a ProfitCalculator with the amounts in dollars or  in pesos
     * @param t a ticket for load
     */
    public void loadTicketValuesInDollars(Ticket t) {
        Float exchangeType = (Float)t.getValues().get("exchangeType");
        DollarPrice dollarPrice = DollarPriceManager.getDollarPrice((Withholding)t.getValues().get("withholding"));
        int daysOfDifference = DollarPriceManager.getDaysDifference((Date) t.getValues().get("date"), dollarPrice);
        //if exchange type is pesos and days of difference is greater than the limit
        if (exchangeType == 1.0f && daysOfDifference > DollarPriceManager.getDaysRoundLimit()) {
            addMissingPrice((Date)t.getValues().get("date"), daysOfDifference);
        }
        calculator.addTicketInDollars(t, dollarPrice);
    }

    /**
     * load the ticket values in a ProfitCalculator with the amounts in dollars or  in pesos
     * @param t a ticket for load
     */
    public void loadTicketValuesInPesos(Ticket t) {
        calculator.addTicketInPesos(t);
    }

    /**
     * load the withholding values in a ProfitCalculator with the amounts in dollars or in pesos
     * @param w withholding who want to charge in profit calculator
     */
    public void loadWithholdingValuesInDollars(Withholding w) {
        Float exchangeType = (Float)w.getValues().get("exchangeType");
        DollarPrice dollarPrice = DollarPriceManager.getDollarPrice(w);
        int daysOfDifference = DollarPriceManager.getDaysDifference((Date) w.getValues().get("date"), dollarPrice);
        //if exchange type is pesos and days of difference is greater than the limit
        if (exchangeType == 1.0f && daysOfDifference > DollarPriceManager.getDaysRoundLimit()) {
            addMissingPrice((Date)w.getValues().get("date"), daysOfDifference);
        }

        calculator.addWithholdingInDollars(w, dollarPrice);
    }

    public void loadWithholdingValuesInPesos(Withholding w) {
        calculator.addWithholdingInPesos(w);
    }

    private void addMissingPrice(Date date, int daysDifference) {
        missingPrices.add(new Pair<Date,String>(date, Integer.toString(daysDifference)));
    }

}