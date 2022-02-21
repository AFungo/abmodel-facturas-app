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
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

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
        } catch (DerbySQLIntegrityConstraintViolationException e) {
            System.out.println("Item was rejected from database for being repeated");
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }

}
