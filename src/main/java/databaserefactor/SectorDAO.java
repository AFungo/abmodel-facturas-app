package databaserefactor;

import models.Sector;
import utils.Pair;
import logger.Handler;
import utils.sql.SQLUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

        Pair<String, String> sqlValues = SQLUtils.modelToSQL(sector);
        String query = "INSERT INTO Sector (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";

        int generatedId = DatabaseUtils.executeCreate(query);
        if (generatedId == 0) {
            return false;
        }

        sector.setValues(Collections.singletonMap("id", generatedId));
        cache.add(sector);
        //add item to cache if executeQuery was successful

        return true;
    }

    @Override
    public boolean update(Sector sector, Map<String, Object> params) {
        prepareCache();

        String query = "UPDATE Sector SET " + SQLUtils.mapToSQLValues(params) + " WHERE name = "
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
                    put("id", result.getString(1));
                    put("name", result.getString(2));
                    }}));
            }
        } catch (SQLException ex) {
            cache.clear();//Iff fails in load an object cache are empty, all are load or nothing are load
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
