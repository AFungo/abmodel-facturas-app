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
        ResultSet result = executeQuery("SELECT * FROM Ticket WHERE date >= " + initialDate + " AND date <= " + finalDate, false);
        List<Ticket> ticketsList = getTicketsList(result);
        return ticketsList;
    }
    
    public static void createTicket(Ticket ticket) {
        String [ ] sqlValues = ticket.getSQLValues();
        Provider provider = ticket.getProvider();
        if (!ProviderDAO.providerExist(provider.getCuit())) {
            ProviderDAO.createProvider(provider);
        }
        String query = "INSERT INTO Ticket (" + sqlValues[0] + ") "
            + "VALUES (" + sqlValues[1] + ")";
        executeQuery(query, true);
    }
        
    public static List<Ticket> getTickets() {
        ResultSet result = executeQuery("SELECT * FROM Ticket", false);
        List<Ticket> ticketsList = getTicketsList(result);
        return ticketsList;
    }
    
    private static ResultSet executeQuery(String query, boolean update) {
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
        } catch (SQLException ex) {
            Logger.getLogger(TicketDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private static List<Ticket> getTicketsList(ResultSet result) {
        List<Ticket> ticketsList = new ArrayList<>();
        try {
            while(result.next()) {
                int len = result.getMetaData().getColumnCount();
                String [ ] ticketAttributes = new String [16]; //15 is the amount of attributes in Ticket class
                for(int i = 2, j = 0; i <= len; i++, j++) { // i=2 because the id is not stored in ticket objects
                    if (i == 7) { //loading provider data
                        Provider prov = ProviderDAO.getProvider(result.getString(i));
                        ticketAttributes[j++] = prov.getDocType();
                        ticketAttributes[j++] = prov.getCuit();
                        ticketAttributes[j] = prov.getName();
                    } else if (i == 4) { //loading sale point and number from
                        ticketAttributes[j++] = result.getString(i);    //sale point which is already noTicket
                        ticketAttributes[j] = "";   //number from will be empty since we already have the noTicket
                    } else //normal loading
                        ticketAttributes[j] = result.getString(i);
                }
                
                ticketsList.add(new Ticket(ticketAttributes));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ticketsList;
    }
}
