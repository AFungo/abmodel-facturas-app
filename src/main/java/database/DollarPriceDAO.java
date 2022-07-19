package database;

import models.DollarPrice;
import models.set.ModelSet;
import utils.Pair;
import logger.Handler;
import utils.sql.SQLUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *this class implements DAO interface for DollarPrices model
 * 
 * 
 */
public class DollarPriceDAO implements DAO<DollarPrice>{
    
    private static DollarPriceDAO instance;

    private final ModelSet<DollarPrice> cache;
    private boolean cacheLoaded;

    public static DollarPriceDAO getInstance() {
        if (instance == null) {
            instance = new DollarPriceDAO();
        }
        return instance;
    }

    private DollarPriceDAO() {
        cache = new ModelSet<>();
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

        Pair<String, String> sqlValues = SQLUtils.modelToSQL(dollarPrice);
        String query = "INSERT INTO DollarPrice (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";

        int generatedId = DatabaseUtils.executeCreate(query);
        if (generatedId == 0) {
            return false;
        }

        dollarPrice.setValues(Collections.singletonMap("id", generatedId));
        cache.add(dollarPrice);
        return true;
    }

    @Override
    public boolean update(DollarPrice dollarPrice, Map<String, Object> params) {
        prepareCache();
        
        String query = "UPDATE DollarPrice SET " + SQLUtils.mapToSQLValues(params) + " WHERE date = "
        + "'" + dollarPrice.getValues().get("date") + "'";
        
        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }

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
                    put("id", Date.valueOf(result.getString(1)));
                    put("date", Date.valueOf(result.getString(2)));
                    put("buy", Float.parseFloat(result.getString(3)));
                    put("sell", Float.parseFloat(result.getString(4)));
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
