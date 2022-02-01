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
import java.util.LinkedList;
import java.util.List;
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
    
    public static boolean providerExist(String cuit) {
        ResultSet result = executeQuery("SELECT * FROM Provider WHERE cuit='" + cuit + "'", false);
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
     
    public static Provider getProvider(String cuit) {
        ResultSet result = executeQuery("SELECT * FROM Provider WHERE cuit='" + cuit + "'", false);
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
    
    public static void changeDirection(String cuit, String direction) {
        if (providerExist(cuit)) {
            String query = "UPDATE Provider SET direction = '" + direction  + "' WHERE cuit = '" + cuit + "'";
            executeQuery(query, true);
        } else {
            System.out.println("The provider of cuit " + cuit + "was not found");
        }
    }
    
    private static Provider createProvider(ResultSet result) throws SQLException {
        Provider prov = new Provider(result.getString(3), result.getString(1), result.getString(2));
        prov.addDirection(result.getString(4));
        prov.addSector(result.getString(5));
        return prov;
    }
}
