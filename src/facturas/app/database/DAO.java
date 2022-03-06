/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturas.app.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Agustin
 */
public abstract class DAO {

    protected static ResultSet executeQuery(String query, boolean update, boolean returnKeys) {
        try {
            Connection connection = DBManager.getConnection();
            PreparedStatement stm;
            if (returnKeys) {
                stm = connection.prepareStatement(query,
                                          Statement.RETURN_GENERATED_KEYS);
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
            if (e.getSQLState() == "23505") {
                throw new IllegalStateException("<23505> duplicate item: " + e.toString());
            } else {
                throw new IllegalStateException(e.toString());
            }
        }
    }
}
