/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaserefactor;

import models.DollarPrice;
import utils.FormatUtils;
import utils.Pair;
import logger.Handler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *this class implements DAO interface for DollarPrices model
 * 
 * 
 */
public class DollarPriceDAO implements DAO<DollarPrice>{
    
    private static DollarPriceDAO instance;

    private final Set<DollarPrice> cache;
    private boolean cacheLoaded;

    public static DollarPriceDAO getInstance() {
        if (instance == null) {
            instance = new DollarPriceDAO();
        }
        return instance;
    }

    private DollarPriceDAO() {
        cache = new HashSet<>();
        cacheLoaded = false;
    }

    @Override
    public Set<DollarPrice> getAll() {
        if (!cacheLoaded) {
            loadCache();
        }
        return cache;
    }

    @Override
    public boolean save(DollarPrice dollarPrice) {
        prepareCache();

        Pair<String, String> sqlValues = FormatUtils.dollarPriceToSQL(dollarPrice);
        String query = "INSERT INTO DollarPrice (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";

        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }

        cache.add(dollarPrice);
        //add item to cache if executeQuery was successful

        return true;

    }

    @Override
    public boolean update(DollarPrice dollarPrice, Map<String, Object> params) {
        prepareCache();
        
        String query = "UPDATE DollarPrice SET " + FormatUtils.mapToSQLValues(params) + " WHERE date = " 
        + "'" + dollarPrice.getValues().get("date") + "'";
        
        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }

        //update cache if executeQuery was successful
        cache.remove(dollarPrice);
        dollarPrice.setValues(params);
        cache.add(dollarPrice);
        return true;
    }

    @Override
    public boolean delete(DollarPrice dollarPrice) {
        prepareCache();

        String query = "DELETE FROM DollarPrice WHERE date = '" + dollarPrice.getValues().get("date") + "'";
        
        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }
        
        cache.remove(dollarPrice);
        return true;    
    }

    /**
     * This method must load the cache with the data from the database
     */
    private void loadCache() {
        String query = "SELECT * FROM DollarPrice";
        ResultSet result = DatabaseUtils.executeQuery(query);
        try {
            while(result.next()) {
                cache.add(new DollarPrice(new HashMap<String, Object>() {{
                    put("date", Date.valueOf(result.getString(1)));
                    put("buy", Float.parseFloat(result.getString(2)));
                    put("sell", Float.parseFloat(result.getString(3)));
                }}));
            }
        } catch (SQLException ex) {
            cache.clear();//Iff fails in load an object cache are emmpty, all are load or nthing are load
            Handler.logUnexpectedError(ex);
        }
}    
    
    private void prepareCache() {
        if (!cacheLoaded) {
            loadCache();
            cacheLoaded = true;
        }
    }

}
