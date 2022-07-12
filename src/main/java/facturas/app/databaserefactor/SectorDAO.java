/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.databaserefactor;

import facturas.app.models.Sector;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * 
 */
public class SectorDAO implements DAO<Sector>{
    
    private static SectorDAO instance;

    private final Set<Sector> cache;
    private final boolean cacheWasLoaded;

    public static SectorDAO getInstance() {
        if (instance == null) {
            instance = new SectorDAO();
        }
        return instance;
    }

    private SectorDAO() {
        cache = new HashSet<>();
        cacheWasLoaded = false;
    }

    /**
     * This method return all the sectors stored in the cache,
     * if the cache is not loaded then it must be loaded first.
     *
     * @return a set of sectors from the cache
     */
    @Override
    public Set<Sector> getAll() {
        if (!cacheWasLoaded) {
            loadCache();
        }
        return cache;
    }

    /**
     * Given a sector, this is saved in the database, and if
     * this could be saved then is added to the cache and
     * return true, else return false.
     *
     * @param sector to be saved
     * @return true iff the sector was saved
     */
    @Override
    public boolean save(Sector sector) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * Given a sector and a set of values, this is updated
     * in the database, and if this could be updated, then is
     * updated in the cache and return true, else return false
     *
     * @param sector to be updated
     * @param params to be used in the update process
     * @return true iff the sector was updated
     */
    @Override
    public boolean update(Sector sector, Map<String, Object> params) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * Given a sector, this is deleted from the database,
     * and if this could be deleted, then is deleted in
     * the cache too and return true, else return false
     *
     * @param sector to be deleted
     * @return true iff the sector was deleted
     */
    @Override
    public boolean delete(Sector sector) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * This method must load the cache with the data from the database
     */
    private void loadCache() {
        throw new UnsupportedOperationException("TODO: Implement");
    }    
}
