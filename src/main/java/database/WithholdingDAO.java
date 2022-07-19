package database;

import models.Provider;
import models.Sector;
import models.Withholding;
import models.set.ModelSet;
import utils.Pair;
import utils.Parser;
import logger.Handler;
import utils.sql.SQLUtils;

import java.sql.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * this class implements DAO interface for Withholding model
 *
 */
public class WithholdingDAO implements DAO<Withholding> {

    private static WithholdingDAO instance;

    private final ModelSet<Withholding> cache;
    private boolean cacheLoaded;

    public static WithholdingDAO getInstance() {
        if (instance == null) {
            instance = new WithholdingDAO();
        }
        return instance;
    }

    private WithholdingDAO() {
        cache = new ModelSet<>();
        cacheLoaded = false;
    }

    @Override
    public ModelSet<Withholding> getAll() {
        prepareCache();
        return new ModelSet<>(cache);
    }

    @Override
    public boolean save(Withholding withholding) {
        prepareCache();

        Pair<String, String> sqlValues = SQLUtils.modelToSQL(withholding);
        String query = "INSERT INTO Withholding (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";

        int generatedId = DatabaseUtils.executeCreate(query);
        if (generatedId == 0) {
            return false;
        }

        withholding.setValues(Collections.singletonMap("id", generatedId));
        cache.add(withholding);
        return true;
    }

    @Override
    public boolean update(Withholding withholding, Map<String, Object> params) {
        prepareCache();

        String query = "UPDATE Withholding SET " + SQLUtils.mapToSQLValues(params) + " WHERE id = "
        + withholding.getValues().get("id");
        
        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }

        //update cache if executeQuery was successful
        cache.remove(withholding);
        withholding.setValues(params);  //remove and add for rehashing
        cache.add(withholding);
        return true;
    }

    @Override
    public boolean delete(Withholding withholding) {
        prepareCache();

        String query = "DELETE FROM Withholding WHERE id = " + withholding.getValues().get("id");    
        
        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }
        
        cache.remove(withholding);
        return true;
    }

    /**
     * This method must load the cache with the data from the database
     *///para mi deberia retornar un booleano
    private void loadCache() {
        String query = "SELECT * FROM Withholding";
        ResultSet result = DatabaseUtils.executeQuery(query);
        try {
            while(result.next()) {
                String providerId = result.getString(4);
                Optional<Provider> provider = ProviderDAO.getInstance().getAll()
                        .stream().filter(p -> p.getValues().get("id").equals(providerId)).findFirst();
                String sectorId = result.getString(8);
                Optional<Sector> sector = SectorDAO.getInstance().getAll()
                        .stream().filter(s -> s.getValues().get("id").equals(sectorId)).findFirst();
                cache.add(new Withholding(new HashMap<String, Object>() {{
                    put("id", Integer.parseInt(result.getString(1)));
                    put("date", Date.valueOf(result.getString(2)));
                    put("number", result.getString(3));
                    if (!provider.isPresent()) {
                        throw new IllegalStateException("Cannot find provider");
                    }
                    put("provider", provider.get());
                    put("iva", Parser.parseFloat(result.getString(5)));
                    put("profits", Parser.parseFloat(result.getString(6)));
                    put("delivered", Parser.parseBool(result.getString(7)));
                    if (sector.isPresent()) {
                        put("sector", sector.get());
                    } else {    //if no sector, use provider's sector
                        put("sector", SectorDAO.getInstance().getAll()
                                .stream().filter(s -> s.getValues().get("sector")
                                        .equals(provider.get().getValues().get("sector")))
                                .findFirst());
                    }
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
