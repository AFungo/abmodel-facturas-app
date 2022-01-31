/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturas.app.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author agustinnolasco
 */
public class SectorDAO extends DAO {
    
    public static void addSector(String name) {
        String query = "INSERT INTO Sector (name) "
            + "VALUES ('"+ name + "')";
        executeQuery(query, true);
    }
    
    public static List<String> getSectors() {
        ResultSet result = executeQuery("SELECT * FROM Sector", false);
        List<String> providers = new LinkedList<>();
        try {
            while (result.next()) {
                providers.add(result.getString(1));
            }
            return providers;
        } catch (SQLException ex) {
            Logger.getLogger(ProviderDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex.toString());
        }
    }
}
