/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.databaserefactor;

import facturas.app.models.DollarPrice;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * 
 */
public class DollarPriceDAO implements DAO<DollarPrice>{
    
    private static DollarPriceDAO instance;

    private final Set<DollarPrice> cache;
    private final boolean cacheWasLoaded;

    public static DollarPriceDAO getInstance() {
        if (instance == null) {
            instance = new DollarPriceDAO();
        }
        return instance;
    }

    private DollarPriceDAO() {
        cache = new HashSet<>();
        cacheWasLoaded = false;
    }

    /**
     * This method return all the dollar prices stored in the cache,
     * if the cache is not loaded then it must be loaded first.
     *
     * @return a set of dollar prices from the cache
     */
    @Override
    public Set<DollarPrice> getAll() {
        if (!cacheWasLoaded) {
            loadCache();
        }
        return cache;
    }

    /**
     * Given a dollar price, this is saved in the database, and if
     * this could be saved then is added to the cache and
     * return true, else return false.
     *
     * @param dollarPrice to be saved
     * @return true iff the dollar price was saved
     */
    public boolean save(DollarPrice dollarPrice) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * Given a dollar price and a set of values, this is updated
     * in the database, and if this could be updated, then is
     * updated in the cache and return true, else return false
     *
     * @param dollarPrice to be updated
     * @param params to be used in the update process
     * @return true iff the dollar price was updated
     */
    @Override
    public boolean update(DollarPrice dollarPrice, Map<String, Object> params) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * Given a dollar price, this is deleted from the database,
     * and if this could be deleted, then is deleted in
     * the cache too and return true, else return false
     *
     * @param dollarPrice to be deleted
     * @return true iff the dollar price was deleted
     */
    @Override
    public boolean delete(DollarPrice dollarPrice) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * This method must load the cache with the data from the database
     */
    private void loadCache() {
        throw new UnsupportedOperationException("TODO: Implement");
    }    
}
