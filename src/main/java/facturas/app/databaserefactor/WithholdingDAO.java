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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        Provider provider = (Provider)withholding.getValues().get("provider");
        
        SQLFilter filter = new SQLFilter();
        filter.add("docNo", "=", provider.getValues().get("docNo"), String.class);
        
        String query = "INSERT INTO Withholding (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";
        try{
            DatabaseUtils.executeQuery(query, true, false);
        }catch(Exception e){
            return false;//if no succes to load in database we return false and don't load in the cache
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
        try{
            DatabaseUtils.executeQuery(query, true, false);
        }catch(Exception e){
            return false;
        }
        //update cache if executeQuery was successful
        cache.remove(withholding);//los set no tienen para updatear un objeto
        whitholding.set(params);
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
        
        try{
            DatabaseUtils.executeQuery(query, true, true);
        }catch(Exception e){
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
                Map<String, Object> WithholdingAttributes = new HashMap<>();
                WithholdingAttributes.put("id", Integer.parseInt(result.getString(1)));
                WithholdingAttributes.put("date", Date.valueOf(result.getString(2)));
                WithholdingAttributes.put("number", result.getString(3));
                Provider prov = Provider.get(result.getString(4));//ver esto deberia ser el id del provider y necesito saber donde buscar
                WithholdingAttributes.put("iva", Float.parseFloat(result.getString(5)));
                WithholdingAttributes.put("profits", Float.parseFloat(result.getString(6)));
                WithholdingAttributes.put("delivered", Boolean.valueOf(result.getString(7)));
                WithholdingAttributes.put("sector", result.getString(8));
                cache.add(new Withholding(WithholdingAttributes));
            }
        } catch (SQLException ex) {
            cache.clear();//Iff fails in load an object cache are emmpty, all are load or nthing are load
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }        
    
    }
    

}

















