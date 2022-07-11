package facturas.app.database;

import facturas.app.models.DollarPrice;
import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object used for the DollarPrice's table of the database
 */
public class DollarPriceDAO extends DAO {
    
    /**
     * Add a new dollar price to the database
     * 
     * @param price dollar price to be added
     */
    public static void add(DollarPrice price) {
        Pair<String, String> sqlValues = FormatUtils.dollarPriceToSQL(price);
        String query = "INSERT INTO DollarPrice (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";
        executeQuery(query, true, true);
    }
        
    /**
     * Dollar prices getter
     * 
     * @return a list with all dollar prices
     */
    public static List<DollarPrice> get() {
        String query = "SELECT * FROM DollarPrice";
        ResultSet result = executeQuery(query, false, true);
        List<DollarPrice> price = buildDollarPrices(result);
        return price;
    }
    
    /**
     * Dollar prices getter using the date as filter
     *
     * @param date date used as filter
     * @return a dollar price of a specific date
     */
    public static DollarPrice get(Date date) {
        String query = "SELECT * FROM DollarPrice WHERE date = '" + date.toString() + "'";
        ResultSet result = executeQuery(query, false, true);
        DollarPrice price = buildDollarPrice(result);
        return price;
    }
    
    /**
     * Check if the database has prices stored
     * 
     * @return true iff the database has no prices stored 
     */
    public static boolean isEmpty() {
        String query = "SELECT COUNT(date) FROM DollarPrice";
        ResultSet result = executeQuery(query, false, true);
        boolean noPrices = true;
        try {
            result.next();
            int amount = result.getInt(1);
            if (amount != 0) {
                noPrices = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DollarPriceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return noPrices;
    }
    
    /**
    * Gets the price of the closest date to the given date
    * 
     * @param date date used in the search
     * @return the price of the closest date to the given date
    */
    public static DollarPrice getAproximatePrice(Date date) {
        String query = "SELECT * FROM DollarPrice WHERE date = (SELECT MAX(date) FROM DollarPrice WHERE "
                + "date < '" + date.toString() + "')";
        ResultSet before = executeQuery(query, false, true);
        
        query = "SELECT * FROM DollarPrice WHERE date > '" + date.toString() + "' FETCH FIRST 1 ROWS ONLY";
        ResultSet after = executeQuery(query, false, true);
        
        DollarPrice priceBefore = buildDollarPrice(before);
        DollarPrice priceAfter = buildDollarPrice(after);
        
        //we assume that the user will load more than 1 sigle price, so this will always find another date
        if (priceBefore == null)
            return priceAfter;
        else if (priceAfter == null)
            return priceBefore;
        else {
            return getNearestDatePrice(priceBefore, priceAfter, date);
        }
    }
    
    private static DollarPrice getNearestDatePrice(DollarPrice priceBefore, DollarPrice priceAfter, Date currentDate) {
        Date dateBefore = (Date) priceBefore.getValues().get("date");
        Date dateAfter = (Date) priceAfter.getValues().get("date");
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
    
    private static List<DollarPrice> buildDollarPrices(ResultSet result) {
        List<DollarPrice> pricesList = new LinkedList<>();
        try {
            while(result.next()) {
                Map<String, Object> priceAttributes = new HashMap<>();
                priceAttributes.put("date", result.getString(1));
                priceAttributes.put("buy", result.getString(2));
                priceAttributes.put("sell", result.getString(3));
                pricesList.add(new DollarPrice(priceAttributes));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DollarPriceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pricesList;
    }
    
    private static DollarPrice buildDollarPrice(ResultSet result) {
        List<DollarPrice> pricesList = buildDollarPrices(result);
        if (pricesList.isEmpty()) {
            return null;
        }
        return pricesList.get(0);
    }
}
