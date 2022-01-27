/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import static facturas.app.database.DAO.executeQuery;
import facturas.app.models.DollarPrice;
import facturas.app.utils.Formater;
import facturas.app.utils.Pair;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nacho
 */
public class DollarPriceDAO extends DAO {
    
    public static void addDollarPrice(DollarPrice price) {
        Pair<String, String> sqlValues = Formater.dollarPriceToSQL(price);
        String query = "INSERT INTO DollarPrice (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";
        executeQuery(query, true);
    }
    
    public static DollarPrice getPrice(Date date) {
        ResultSet result = executeQuery("SELECT * FROM DollarPrice WHERE date = '" + date.toString() + "'", false);
        DollarPrice price = buildDollarPrice(result);
        return price;
    }
    
    public static DollarPrice getAproximatePrice(Date date) {
        ResultSet before = executeQuery("SELECT * FROM DollarPrice WHERE date = ("
                + "SELECT MAX(date) FROM DollarPrice WHERE date < '" + date.toString() + "')", false);
        ResultSet after = executeQuery("SELECT * FROM DollarPrice WHERE date > '" + date.toString() + "' FETCH FIRST 1 ROWS ONLY", false);
        
        DollarPrice priceBefore = buildDollarPrice(before);
        DollarPrice priceAfter = buildDollarPrice(after);
        
        //we assume that the user will load more than 1 sigle price, so this will always find another date
        if (priceBefore == null)
            return priceAfter;
        else if (priceAfter == null)
            return priceBefore;
        else {
            Date dateAfter = priceAfter.getDate();
            Date dateBefore = priceBefore.getDate();
            //getting time of each date
            long currentTime = date.getTime();
            long afterTime = dateAfter.getTime();
            long beforeTime = dateBefore.getTime();
            //getting difference between both dates
            long afterAbs = Math.abs(afterTime - currentTime);
            long beforeAbs = Math.abs(currentTime - beforeTime);
            //return the DollarPrice of the nearest date
            DollarPrice finalPrice = afterAbs <= beforeAbs ? priceAfter : priceBefore;
            return finalPrice;
        }
    }
    
    private static DollarPrice buildDollarPrice(ResultSet result) {
        DollarPrice price = null;
        try {
            if (!result.next()) {
                return null;
            }
            Map<String, String> priceAttributes = new HashMap<>();
            priceAttributes.put("date", result.getString(1));
            priceAttributes.put("buy", result.getString(2));
            priceAttributes.put("sell", result.getString(3));
            price = new DollarPrice(priceAttributes);
        } catch (SQLException ex) {
            Logger.getLogger(DollarPriceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return price;
    }
}
