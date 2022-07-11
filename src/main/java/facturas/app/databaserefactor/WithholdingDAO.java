package facturas.app.databaserefactor;

import facturas.app.database.SQLFilter;
import facturas.app.models.Provider;
import facturas.app.models.Withholding;
import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        DatabaseUtils.executeQuery(query, true, false);
        
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
        ResultSet queryResult = DatabaseUtils.executeQuery(query, true, false);

        //update cache if executeQuery was successful

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
        throw new UnsupportedOperationException("TODO: Implement");
    }

    /**
     * This method must load the cache with the data from the database
     */
    private void loadCache() {
        throw new UnsupportedOperationException("TODO: Implement");
    }

}
