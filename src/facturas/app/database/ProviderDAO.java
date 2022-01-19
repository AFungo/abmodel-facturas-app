/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.db;

import facturas.app.models.Provider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Agustin
 */
public class ProviderDAO {
    
    public static void addProvider(Provider provider) {
        String [ ] sqlValues = provider.getSQLValues();
        String query = "INSERT INTO Provider (" + sqlValues[0] + ") "
            + "VALUES (" + sqlValues[1] + ")";
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
                for(int i = 1; i <= len; i++)
                    providerAttributes[i-1] = result.getString(i);
                return new Provider(providerAttributes[2], providerAttributes[0], providerAttributes[1]);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProviderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; //in case of exception or provider not found
    }
    
    private static ResultSet executeQuery(String query, boolean update) {
        try {
            Connection connection = DBManager.getConnection();
            Statement stm = connection.createStatement();
            
            if (update){
                stm.executeUpdate(query);
                return null;
            } else {
                ResultSet result = stm.executeQuery(query);
                return result;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
