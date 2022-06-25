package facturas.app.databaserefactor;

import facturas.app.database.DBManager;

import java.sql.*;

public class DatabaseUtils {

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
