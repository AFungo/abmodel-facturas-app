/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.databaserefactor;

import facturas.app.models.Provider;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * this class implements DAO interface for Provider model
 */
public class ProviderDAO implements DAO<Provider>{
    
    private static ProviderDAO instance;

    private final Set<Provider> cache;
    private final boolean cacheWasLoaded;

    public static ProviderDAO getInstance() {
        if (instance == null) {
            instance = new ProviderDAO();
        }
        return instance;
    }

    private ProviderDAO() {
        cache = new HashSet<>();
        cacheWasLoaded = false;
    }

    @Override
    public Set<Provider> getAll() {
        if (!cacheWasLoaded) {
            loadCache();
        }
        return cache;
    }

    public boolean save(Provider provider) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    @Override
    public boolean update(Provider provider, Map<String, Object> params) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    @Override
    public boolean delete(Provider provider) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * This method must load the cache with the data from the database
     */
    private void loadCache() {
        throw new UnsupportedOperationException("TODO: Implement");
    }    
}
