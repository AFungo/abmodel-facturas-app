/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

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
    
    public static void createProvider(Provider provider) {
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
