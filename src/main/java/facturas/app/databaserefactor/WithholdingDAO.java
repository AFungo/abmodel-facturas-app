package facturas.app.databaserefactor;

import facturas.app.models.Provider;
import facturas.app.models.Withholding;
import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;
import logger.Handler;

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

    private final Set<Withholding> cache;
    private boolean cacheLoaded;

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

    @Override
    public Set<Withholding> getAll() {
        prepareCache();
        return cache;
    }

    @Override
    public boolean save(Withholding withholding) {
        prepareCache();

        Pair<String, String> sqlValues = FormatUtils.withholdingToSQL(withholding);
        String query = "INSERT INTO Withholding (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";

        int generatedId = DatabaseUtils.executeCreate(query);
        if (generatedId == 0) {
            return false;
        }

        //add id to withholding
        withholding.setValues(new HashMap<String, Object>() {{
            put("id", String.valueOf(generatedId));
        }});
        cache.add(withholding);
        //add item to cache if executeQuery was successful
        return true;
    }

    @Override
    public boolean update(Withholding withholding, Map<String, Object> params) {
        prepareCache();

        String query = "UPDATE Withholding SET " + FormatUtils.mapToSQLValues(params) + " WHERE id = " 
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
