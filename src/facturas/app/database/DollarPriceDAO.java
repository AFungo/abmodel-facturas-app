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
        DollarPrice price = null;
        try {
            if (!result.next()) {
                return null;
            } else {
                Map<String, String> priceAttributes = new HashMap<>();
                priceAttributes.put("date", result.getString(1));
                priceAttributes.put("buy", result.getString(2));
                priceAttributes.put("sell", result.getString(3));
                price = new DollarPrice(priceAttributes);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DollarPriceDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return price;
    }
}
