package facturas.app.databaserefactor;

import facturas.app.Controller;
import facturas.app.database.SQLFilter;
import facturas.app.models.Provider;
import facturas.app.models.Withholding;
import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;
import java.sql.Date;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WithholdingDAO implements DAO<Withholding> {

    private static WithholdingDAO instance;

    private final Set<Withholding> cache;
    private final boolean cacheLoaded;

    public static WithholdingDAO getInstance() {
        if (instance == null) {
            instance = new WithholdingDAO();
        }
        return instance;
    }

    private WithholdingDAO() {
        cache = new HashSet<>();
        cacheLoaded = false;
    }

    /**
     * This method return all the withholdings stored in the cache,
     * if the cache is not loaded then it must be loaded first.
     *
     * @return a set of withholding from the cache
     */
    @Override
    public Set<Withholding> getAll() {
        if (!cacheLoaded) {
            loadCache();
        }
        return cache;
    }

    /**
     * Given a withholding, this is saved in the database, and if
     * this could be saved then is added to the cache and
     * return true, else return false.
     *
     * @param withholding to be saved
     * @return true iff the withholding was saved
     */
    @Override
    public boolean save(Withholding withholding) {
        Pair<String, String> sqlValues = FormatUtils.withholdingToSQL(withholding);

        String query = "INSERT INTO Withholding (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";

        ResultSet operationResult = DatabaseUtils.executeQuery(query, true, false);
        if (operationResult == null) {
            return false;
        }

        cache.add(withholding);
        //add item to cache if executeQuery was successful

        return true;
    }

    /**
     * Given a withholding and a set of values, this is updated
     * in the database, and if this could be updated, then is
     * updated in the cache and return true, else return false
     *
     * @param withholding to be updated
     * @param params column names with the new values to update
     * @return true iff the withholding was updated
     */
    @Override
    public boolean update(Withholding withholding, Map<String, Object> params) {
        String query = "UPDATE Withholding SET " + FormatUtils.mapToSQLValues(params) + " WHERE id = " 
        + withholding.getValues().get("id");
        
        ResultSet operationResult = DatabaseUtils.executeQuery(query, true, false);
        if (operationResult == null) {
            return false;
        }

        //update cache if executeQuery was successful
        cache.remove(withholding);//los set no tienen para updatear un objeto
        withholding.setValues(params);
        cache.add(withholding);
        return true;
    }

    /**
     * Given a withholding, this is deleted from the database,
     * and if this could be deleted, then is deleted in
     * the cache too and return true, else return false
     *
     * @param withholding to be deleted
     * @return true iff the withholding was deleted
     */
    @Override
    public boolean delete(Withholding withholding) {
        String query = "DELETE FROM Withholding WHERE id = " + withholding.getValues().get("id");    
        
        ResultSet operationResult = DatabaseUtils.executeQuery(query, true, true);
        if (operationResult == null) {
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
        ResultSet result = DatabaseUtils.executeQuery(query , false, true);
        try {
            while(result.next()) {
                String docNo = result.getString(4);
                Optional<Provider> provider = ProviderDAO.getInstance().getAll()
                        .stream().filter(p -> p.getValues().get("docNo").equals(docNo)).findFirst();
                cache.add(new Withholding(new HashMap<String, Object>() {{
                    put("id", Integer.parseInt(result.getString(1)));
                    put("date", Date.valueOf(result.getString(2)));
                    put("number", result.getString(3));
                    if (!provider.isPresent()) {
                        throw new IllegalStateException("Cannot find provider");
                    }
                    put("provider", provider.get());
                    put("iva", Float.parseFloat(result.getString(5)));
                    put("profits", Float.parseFloat(result.getString(6)));
                    put("delivered", Boolean.valueOf(result.getString(7)));
                    put("sector", result.getString(8));
                }}));
            }
        } catch (SQLException ex) {
            cache.clear();//Iff fails in load an object cache are emmpty, all are load or nthing are load
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}