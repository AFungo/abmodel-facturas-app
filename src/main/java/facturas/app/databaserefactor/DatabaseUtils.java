package facturas.app.databaserefactor;

import facturas.app.database.DBManager;
import logger.Handler;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtils {

    /**
     * Executes the requested query
     *
     * @param query the query to be executed
     * @param update Boolean indicating if the query is an update of an existing row or an addition of a new row
     * @param returnKeys Boolean indicating if the generated keys needs to be returned
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
            if (e.getSQLState().equals("23505")) {  //duplicate item
                Handler.showErrorMessage("El item que se intento cargar ya estaba cargado");
            } else {                                //unknown error
                Handler.logUnexpectedError(e, "query: " + query + "\n" + e.toString());
            }
            return null;
        }
    }
    
}
