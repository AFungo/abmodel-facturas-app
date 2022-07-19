package database;

import models.Provider;
import models.set.ModelSet;
import utils.Pair;
import logger.Handler;
import utils.sql.SQLUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * this class implements DAO interface for Provider model
 * 
 */
public class ProviderDAO implements DAO<Provider>{
    
    private static ProviderDAO instance;

    private final ModelSet<Provider> cache;
    private boolean cacheLoaded;

    public static ProviderDAO getInstance() {
        if (instance == null) {
            instance = new ProviderDAO();
        }
        return instance;
    }

    private ProviderDAO() {
        cache = new ModelSet<>();
        cacheLoaded = false;
    }

    @Override
    public ModelSet<Provider> getAll() {
        prepareCache();
        return new ModelSet<>(cache);
    }

    public boolean save(Provider provider) {
        prepareCache();

        Pair<String, String> sqlValues = SQLUtils.modelToSQL(provider);
        String query = "INSERT INTO Provider (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";

        int generatedId = DatabaseUtils.executeCreate(query);
        if (generatedId == 0) {
            return false;
        }

        provider.setValues(Collections.singletonMap("id", generatedId));
        cache.add(provider);
        //add item to cache if executeQuery was successful
        return true;
    }

    @Override
    public boolean update(Provider provider, Map<String, Object> params) {
        prepareCache();
      
        String query = "UPDATE Provider SET " + SQLUtils.mapToSQLValues(params) + " WHERE docNo = "
                + "'" + provider.getValues().get("docNo") + "'";

        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }
        
        //update cache if executeQuery was successful
        cache.remove(provider);
        provider.setValues(params); //remove and add for rehashing
        cache.add(provider);
        return true;
    }

    @Override
    public boolean delete(Provider provider) {
        prepareCache();
        
        String query = "DELETE FROM Provider WHERE docNo = '" + provider.getValues().get("docNo") + "'";
        
        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }
        
        cache.remove(provider);
        return true;
    }

    /**
     * This method must load the cache with the data from the database
     */
    private void loadCache() {
        String query = "SELECT * FROM Provider";
        ResultSet result = DatabaseUtils.executeQuery(query);
        try {
            while(result.next()) {
                cache.add(new Provider(new HashMap<String, Object>() {{
                    put("id", result.getString(1));
                    put("docNo", result.getString(2));
                    put("name", result.getString(3));
                    put("docType", result.getString(4));
                    put("address", result.getString(5));
                    put("provSector", result.getString(6));
                    put("alias", result.getString(7));
                }}));
            }
        } catch (SQLException ex) {
            cache.clear();  //in case of failing the cache is emptied, everything is loaded or nothing is loaded
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
