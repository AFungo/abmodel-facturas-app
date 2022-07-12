/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.databaserefactor;

import facturas.app.models.Provider;
import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;
import logger.Handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * 
 */
public class ProviderDAO implements DAO<Provider>{
    
    private static ProviderDAO instance;

    private final Set<Provider> cache;
    private boolean cacheLoaded;

    public static ProviderDAO getInstance() {
        if (instance == null) {
            instance = new ProviderDAO();
        }
        return instance;
    }

    private ProviderDAO() {
        cache = new HashSet<>();
        cacheLoaded = false;
    }

    /**
     * This method return all the providers stored in the cache,
     * if the cache is not loaded then it must be loaded first.
     *
     * @return a set of provider from the cache
     */
    @Override
    public Set<Provider> getAll() {
        prepareCache();
        return cache;
    }

    /**
     * Given a provider, this is saved in the database, and if
     * this could be saved then is added to the cache and
     * return true, else return false.
     *
     * @param provider to be saved
     * @return true iff the provider was saved
     */
    public boolean save(Provider provider) {
        Pair<String, String> sqlValues = FormatUtils.providerToSQL(provider);
        String query = "INSERT INTO Provider (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";
            
        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }
        
        prepareCache();
        cache.add(provider);
        //add item to cache if executeQuery was successful
        return true;
    }

    /**
     * Given a provider and a set of values, this is updated
     * in the database, and if this could be updated, then is
     * updated in the cache and return true, else return false
     *
     * @param provider to be updated
     * @param params to be used in the update process
     * @return true iff the provider was updated
     */
    @Override
    public boolean update(Provider provider, Map<String, Object> params) {
        String query = "UPDATE Provider SET " + FormatUtils.mapToSQLValues(params)  
        + "' " + provider.getValues().get("docNo");

        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }
        
        prepareCache();
        //update cache if executeQuery was successful
        cache.remove(provider);
        provider.setValues(params); //remove and add for rehashing
        cache.add(provider);
        return true;
    }

    /**
     * Given a provider, this is deleted from the database,
     * and if this could be deleted, then is deleted in
     * the cache too and return true, else return false
     *
     * @param provider to be deleted
     * @return true iff the provider was deleted
     */
    @Override
    public boolean delete(Provider provider) {
        String query = "DELETE FROM Provider WHERE docNo = '" + provider.getValues().get("docNo") + "'";
        
        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }
        
        prepareCache();
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
                    put("docNo", result.getString(1));
                    put("name", result.getString(2));
                    put("docType", result.getString(3));
                    put("direction", result.getString(4));
                    put("provSector", result.getString(5));
                    put("alias", result.getString(6));
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
