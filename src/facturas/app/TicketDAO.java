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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nacho
 */
public class TicketDAO {
    
    public static List<Ticket> ticketsBetween(Date initialDate, Date finalDate) {
        try {
            Connection connection = DBManager.getConnection();
            Statement stm = connection.createStatement();

            ResultSet result = stm.executeQuery("SELECT * FROM Ticket WHERE date >= " + initialDate + " AND date <= " + finalDate);

            List<Ticket> ticketsList = new ArrayList<>();
            while(result.next()) {
                int len = result.getMetaData().getColumnCount();
                String str = "";
                for(int i = 1; i <= len; i++) {
                    str += " \" " + result.getString(i) + " \" ,";  //turning it into a csv format
                }
                str = str.substring(0, str.length() - 1); //removing the last comma
                ticketsList.add(new Ticket(str));

            }
            return ticketsList;

        } catch (SQLException ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static void createTicket(Ticket ticket) {
        try {
            Connection connection = DBManager.getConnection();
            Statement stm = connection.createStatement();

            String query = "INSERT INTO Ticket (provider_cuit, noTicket, iva, date, totalAmount, exchangeType, ticketType) "
                + "VALUES (" + Ticket.getSQLValues() + ")";
            stm.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public static List<Ticket> getTickets() {
        try {
            Connection connection = DBManager.getConnection();
            Statement stm = connection.createStatement();

            ResultSet result = stm.executeQuery("SELECT * FROM Ticket");

            List<Ticket> ticketsList = new ArrayList<>();
            while(result.next()) {
                int len = result.getMetaData().getColumnCount();
                String str = "";
                for(int i = 1; i <= len; i++) {
                    str += " \" " + result.getString(i) + " \" ,";  //turning it into a csv format
                }
                str = str.substring(0, str.length() - 1); //removing the last comma
                ticketsList.add(new Ticket(str));

            }
            return ticketsList;

        } catch (SQLException ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
