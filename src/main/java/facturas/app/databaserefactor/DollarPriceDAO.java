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
 *this class implements DAO interface for DollarPrices model
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

    @Override
    public Set<DollarPrice> getAll() {
        if (!cacheWasLoaded) {
            loadCache();
        }
        return cache;
    }

    public boolean save(DollarPrice dollarPrice) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    @Override
    public boolean update(DollarPrice dollarPrice, Map<String, Object> params) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

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
