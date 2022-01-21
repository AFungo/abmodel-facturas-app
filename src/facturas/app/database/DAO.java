/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturas.app.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

/**
 *
 * @author Agustin
 */
public abstract class DAO {

    protected static ResultSet executeQuery(String query, boolean update) {
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
        } catch (DerbySQLIntegrityConstraintViolationException e) {
            System.out.println("Item was rejected from database for being repeated");
            return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }

}
