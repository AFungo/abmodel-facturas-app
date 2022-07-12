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
 * this class implements DAO interface for Sectors table
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

    @Override
    public Set<Sector> getAll() {
        if (!cacheWasLoaded) {
            loadCache();
        }
        return cache;
    }

    @Override
    public boolean save(Sector sector) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

    @Override
    public boolean update(Sector sector, Map<String, Object> params) {
        throw new UnsupportedOperationException("TODO: Implement");
    }

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
