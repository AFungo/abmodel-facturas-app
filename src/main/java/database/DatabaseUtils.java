package database;

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

            return stm.executeQuery();

        } catch (SQLException e) {
            Handler.logUnexpectedError(e, "query: " + query + "\n" + e);
            return null;
        }
    }

    /**
     * Executes the requested update/insertion/deletion
     * @param query the query to be executed
     * @return the number of rows affected, 0 if an error 
     * occurred or no rows were affected
     */
    protected static int executeCreate(String query) {
        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement stm;
            stm = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = stm.executeUpdate();
        
            if (affectedRows == 0) {
                return 0;
            }
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();

            return generatedKeys.getInt(1);
        
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {  //duplicate item
                Handler.showErrorMessage("El item que se intento cargar ya estaba cargado");
            } else if (!(e.getSQLState().equals("42Z23") && e.getMessage()
                    .contains("Attempt to modify an identity column 'ID'."))) {
                return 0;
            } else {                                //unknown error
                Handler.logUnexpectedError(e, "query: " + query + "\n" + e);
            }
            return 0;
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
            stm = connection.prepareStatement(query);

            return stm.executeUpdate();
        
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {  //duplicate item
                Handler.showErrorMessage("El item que se intento cargar ya estaba cargado");
            } else {                                //unknown error
                Handler.logUnexpectedError(e, "query: " + query + "\n" + e);
                System.out.println("Error: " + e);
            }
            return 0;
        }
    }
    
}
