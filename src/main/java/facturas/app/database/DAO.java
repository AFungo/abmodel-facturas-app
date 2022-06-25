package facturas.app.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Abstract class that implements a unique method used in all
 * concrete DAO classes
 */
public abstract class DAO {

    /**
     * Execute the requested query
     * 
     * @param query the query to be executed
     * @param update Boolean argument that check if the query is an update
     * @param returnKeys Boolean argument that check if return keys will be used
     * @return the requested values from the database
     */
    protected static ResultSet executeQuery(String query, boolean update, boolean returnKeys) {
        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement stm;
            if (returnKeys) {
                stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            } else {
                stm = connection.prepareStatement(query);
            }
            
            if (update) {
                stm.executeUpdate();
                return stm.getGeneratedKeys();
            } else {
                ResultSet result = stm.executeQuery();
                return result;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new IllegalStateException("query: " + query + "\n" + "<23505> duplicate item: " + e.toString());

            } else {
                throw new IllegalStateException("query: " + query + "\n" + e.toString());
            }
        }
    }
    
}
