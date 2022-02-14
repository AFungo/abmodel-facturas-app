/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Agustin
 */
public class ProviderDAO extends DAO {
    
    public static void addProvider(Provider provider) {
        Pair<String, String> sqlValues = FormatUtils.providerToSQL(provider);
        String query = "INSERT INTO Provider (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";
        executeQuery(query, true);
    }
    
    public static boolean providerExist(SQLFilter filters) {
        ResultSet result = executeQuery("SELECT * FROM Provider " + filters.get(), false);
        try {
            return result.next();
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    public static List<Provider> getProviders(SQLFilter filters) {
        if (filters == null) {
            throw new IllegalArgumentException("The parameter filters can not be null");
        }
        
        ResultSet result = executeQuery("SELECT * FROM Provider" + filters.get(), false);
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
     
    public static Provider getProvider(String docNo) {
        ResultSet result = executeQuery("SELECT * FROM Provider WHERE docNo='" + docNo + "'", false);
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

    public static List<Provider> getProviders() {
        ResultSet result = executeQuery("SELECT * FROM Provider", false);
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
    
    public static void changeAttribute(SQLFilter filters, String attribute, String value) {
        if (providerExist(filters)) {
            String query = "UPDATE Provider SET " + attribute + " = '" + value  + "' " + filters.get();
            executeQuery(query, true);
        } else {
            System.out.println("A provider with filters (" + filters.get() + ") was not found");
        }
    }
    
    private static Provider createProvider(ResultSet result) throws SQLException {
        Map<String, String> values = new HashMap<>();
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
