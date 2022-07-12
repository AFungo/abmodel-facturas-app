package facturas.app.databaserefactor;

import facturas.app.database.DBManager;
import logger.Handler;

import java.sql.*;

public class DatabaseUtils {

    /**
     * Executes the requested query for reading data
     *
     * @param query the query to be executed
     * @return the requested values from the database as a ResultSet object
     */
    protected static ResultSet executeQuery(String query) {
        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement stm;
            stm = connection.prepareStatement(query);
            ResultSet result = stm.executeQuery();

            return result;

        } catch (SQLException e) {
            Handler.logUnexpectedError(e, "query: " + query + "\n" + e.toString());
            return null;
        }
    }

    /**
     * Executes the requested update/insertion/deletion
     * @param query the query to be executed
     * @return the number of rows affected, 0 if an error 
     * occurred or no rows were affected
     */
    protected static int executeUpdate(String query) {
        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement stm;
            stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = stm.executeUpdate();
        
            return affectedRows;
        
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {  //duplicate item
                Handler.showErrorMessage("El item que se intento cargar ya estaba cargado");
            } else {                                //unknown error
                Handler.logUnexpectedError(e, "query: " + query + "\n" + e.toString());
            }
            return 0;
        }
    }
    
}
