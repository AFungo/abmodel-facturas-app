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
 * 
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

    /**
     * This method return all the providers stored in the cache,
     * if the cache is not loaded then it must be loaded first.
     *
     * @return a set of provider from the cache
     */
    @Override
    public Set<Provider> getAll() {
        if (!cacheWasLoaded) {
            loadCache();
        }
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
        throw new UnsupportedOperationException("TODO: Implement");
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
        throw new UnsupportedOperationException("TODO: Implement");
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
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * This method must load the cache with the data from the database
     */
    private void loadCache() {
        throw new UnsupportedOperationException("TODO: Implement");
    }    
}
