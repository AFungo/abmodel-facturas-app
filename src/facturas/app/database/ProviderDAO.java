/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import facturas.app.models.Provider;
import facturas.app.utils.Formater;
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
        Pair<String, String> sqlValues = Formater.providerToSQL(provider);
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
     
    public static Provider getProvider(String cuit) {
        ResultSet result = executeQuery("SELECT * FROM Provider WHERE cuit='" + cuit + "'", false);
        try {
            if (result.next()) {
                int len = result.getMetaData().getColumnCount();
                String [ ] providerAttributes = new String [len];
                for(int i = 1; i <= len; i++) {
                    providerAttributes[i-1] = result.getString(i);
                }
                return new Provider(providerAttributes[2], providerAttributes[0], providerAttributes[1]);
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ProviderDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex.toString());
        }
    }
    
    public static List<Provider> getProviders(String name) {
        ResultSet result = executeQuery("SELECT * FROM Provider WHERE name='" + name + "'", false);
        List<Provider> providers = new LinkedList<>();
        try {
            while (result.next()) {
                int len = result.getMetaData().getColumnCount();
                String [ ] providerAttributes = new String [len];
                for(int i = 1; i <= len; i++) {
                    providerAttributes[i-1] = result.getString(i);
                }
                providers.add(new Provider(providerAttributes[2], providerAttributes[0], providerAttributes[1]));
            }
            return providers;
        } catch (SQLException ex) {
            Logger.getLogger(ProviderDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex.toString());
        }
    }

    public static List<Provider> getProviders() {
        ResultSet result = executeQuery("SELECT * FROM Provider", false);
        List<Provider> providersList = new LinkedList<>();
        
        try {
            while(result.next()) {
                int len = result.getMetaData().getColumnCount();
                String [ ] providerAttributes = new String [len];
                for(int i = 1; i <= len; i++)
                    providerAttributes[i-1] = result.getString(i);
                providersList.add(new Provider(providerAttributes[2], providerAttributes[0], providerAttributes[1]));
            }
            return providersList;
        } catch (SQLException ex) {
            Logger.getLogger(ProviderDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex.toString());
        }
    }
    
}
