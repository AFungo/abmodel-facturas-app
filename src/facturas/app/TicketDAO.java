/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nacho
 */
public class TicketDAO {
    
    public static ResultSet ticketsBetween(Date initialDate, Date finalDate) {
        try {
            Connection connection = DBManager.getConnection();
            Statement stm = connection.createStatement();

            ResultSet result = stm.executeQuery("SELECT * FROM Ticket WHERE date >= " + initialDate + " AND date <= " + finalDate);
            return result;

        } catch (SQLException ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static void createTicket(Ticket ticket) {
        try {
            Connection connection = DBManager.getConnection();
            Statement stm = connection.createStatement();

            String query = "INSERT INTO Ticket (noTicket, iva, totalAmount, date, exchangeType, ticketType, providerCuit) "
                + "VALUES (" + ticket.getSQLValues() + ")";
            stm.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public static ResultSet getTickets() {
        try {
            Connection connection = DBManager.getConnection();
            Statement stm = connection.createStatement();

            ResultSet result = stm.executeQuery("SELECT * FROM Ticket");
            return result;

        } catch (SQLException ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
