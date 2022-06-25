package facturas.app.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object used for the Sector's table of the database
 */
public class SectorDAO extends DAO {
    
    /**
     * Add a new sector to the database
     *
     * @param name name of the sector to be added
     */
    public static void add(String name) {
        String query = "INSERT INTO Sector (name) "
            + "VALUES ('"+ name + "')";
        executeQuery(query, true, true);
    }
    
    /**
     * Given a name, checks if the executed query returns an empty set 
     * or not
     * 
     * @param name name that will be used in the query
     * @return true iff the result if not empty
     */
    public static boolean exist(String name) {
        String query = "SELECT * FROM Sector WHERE name = '" + name + "'";
        ResultSet result = executeQuery(query, false, true);
        try {
            return result.next();
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    /**
     * Sectors getter
     *
     * @return a list of all sectors
     */
    public static List<String> get() {
        ResultSet result = executeQuery("SELECT * FROM Sector", false, true);
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
    
    /**
     * Given a name, search the sectors and delete them
     * 
     * @param name name used for the sectors search
     */
    public static void remove(String name) {
        String query = "DELETE FROM Sector WHERE name = '" + name + "'";
        executeQuery(query, true, false);
    }
    
}
