/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaserefactor;

import models.Sector;
import utils.FormatUtils;
import utils.Pair;
import logger.Handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
    private boolean cacheLoaded;

    public static SectorDAO getInstance() {
        if (instance == null) {
            instance = new SectorDAO();
        }
        return instance;
    }

    private SectorDAO() {
        cache = new HashSet<>();
        cacheLoaded = false;
    }

    @Override
    public Set<Sector> getAll() {
        if (!cacheLoaded) {
            loadCache();
        }
        return cache;
    }

    @Override
    public boolean save(Sector sector) {
        prepareCache();

        Pair<String, String> sqlValues = FormatUtils.sectorToSQL(sector);
        String query = "INSERT INTO Sector (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";

        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }

        cache.add(sector);
        //add item to cache if executeQuery was successful

        return true;
    }

    @Override
    public boolean update(Sector sector, Map<String, Object> params) {
        prepareCache();

        String query = "UPDATE Sector SET " + FormatUtils.mapToSQLValues(params) + " WHERE name = "
                + "'" + sector.getValues().get("name") + "'";

        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }

        //update cache if executeQuery was successful
        cache.remove(sector);
        sector.setValues(params);
        cache.add(sector);
        return true;

    }

    @Override
    public boolean delete(Sector sector) {
        prepareCache();
        String query = "DELETE FROM Sector WHERE name = '" + sector.getValues().get("name") + "'";
        
        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }
        
        cache.remove(sector);
        return true;
    }

    /**
     * This method must load the cache with the data from the database
     */
    private void loadCache() {
        String query = "SELECT * FROM Sector";
        ResultSet result = DatabaseUtils.executeQuery(query);
        try {
            while(result.next()) {
                cache.add(new Sector(new HashMap<String, Object>() {{
                    put("name", result.getString(1));
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
