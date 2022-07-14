package facturas.app.database;

import facturas.app.models.Provider;
import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object used for the Provider's table of the database
 */
public class ProviderDAO extends DAO {
    
    /**
     * Add a new provider to the database
     *
     * @param provider provider to be added
     */
    public static void add(Provider provider) {
        Pair<String, String> sqlValues = FormatUtils.providerToSQL(provider);
        String query = "INSERT INTO Provider (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";
        executeQuery(query, true, true);
    }
    
    /**
     * Given a filter, checks if the executed query returns an empty set 
     * or not
     * 
     * @param filter filter that will be used in the query
     * @return true iff the result if not empty
     */
    public static boolean exist(SQLFilter filter) {
        String query = "SELECT * FROM Provider " + filter.get();
        ResultSet result = executeQuery(query, false, true);
        try {
            return result.next();
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    /**
     * Providers getter
     *
     * @return a list of all providers
     */
    public static List<Provider> get() {
        String query = "SELECT * FROM Provider";
        ResultSet result = executeQuery(query, false, true);
        List<Provider> providers = new LinkedList<>();
        
        try {
            while(result.next()) {
                providers.add(createProvider(result));
            }
            return providers;
        } catch (SQLException ex) {
            Logger.getLogger(ProviderDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex.toString());
        }
    }
    
    /**
     * Providers getter using a filter
     *
     * @param filter filter used in the query
     * @return a list of all provider's obtained using the filter
     */
    public static List<Provider> get(SQLFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("The parameter filters can not be null");
        }
        
        String query = "SELECT * FROM Provider" + filter.get();
        ResultSet result = executeQuery(query, false, true);
        List<Provider> providers = new LinkedList<>();
        try {
            while (result.next()) {
                providers.add(createProvider(result));
            }
            return providers;
        } catch (SQLException ex) {
            Logger.getLogger(ProviderDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex.toString());
        }
    }
     
    /**
     * Provider getter using the document number
     *
     * @param docNo document number used in the query
     * @return a provider with that document requested if this exists
     */
    public static Provider get(String docNo) {
        String query = "SELECT * FROM Provider WHERE docNo='" + docNo + "'";
        ResultSet result = executeQuery(query, false, true);
        try {
            if (result.next()) {
                return createProvider(result);
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ProviderDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex.toString());
        }
    }
    
    /**
     * Given a filter to search providers and an attribute, updates the
     * attribute of selected providers with the given value
     *
     * @param filter filter used for search providers
     * @param attribute attribute of the providers that will be updated
     * @param value value used for the update
     */
    public static void update(SQLFilter filter, String attribute, String value) {
        if (exist(filter)) {
            String query = "UPDATE Provider SET " + attribute + " = '" + value  + "' " + filter.get();
            executeQuery(query, true, false);
        } else {
            System.out.println("A provider with filters (" + filter.get() + ") was not found");
        }
    }
    
    /**
     * Given a filter and an attribute set the attribute of the searched
     * providers with null
     * 
     * @param filter filter used for search providers
     * @param attribute attribute that will be set as null
     */
    public static void setNull(SQLFilter filter, String attribute) {
        String query = "UPDATE Provider SET " + attribute + " =  null " + filter.get();
        executeQuery(query, true, false);
    }
    
    /**
     * Given a non-empty filter deletes the providers that matches filter
     * 
     * @param filter filter used for delete providers
     */
    public static void delete(SQLFilter filter) {
        if (filter.isEmpty()) {
            throw new IllegalArgumentException("cannot delete provider without filter");
        }
        String query = "DELETE FROM Provider" + filter.get();
        executeQuery(query, true, false);
    }
    
    private static Provider createProvider(ResultSet result) throws SQLException {
        Map<String, Object> values = new HashMap<>();
        values.put("docNo", result.getString(1));
        values.put("name", result.getString(2));
        values.put("docType", result.getString(3));
        values.put("direction", result.getString(4));
        values.put("provSector", result.getString(5));
        values.put("alias", result.getString(6));
        Provider prov = new Provider(values);
        return prov;
    }
}
