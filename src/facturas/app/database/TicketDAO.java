/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import facturas.app.Controller;
import facturas.app.models.Ticket;
import facturas.app.models.Provider;
import facturas.app.utils.TicketFormater;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nacho
 */
public class TicketDAO extends DAO {
    
    public static List<Ticket> ticketsBetween(Date initialDate, Date finalDate) {
        ResultSet result = executeQuery("SELECT * FROM Ticket WHERE date >= " + initialDate + " AND date <= " + finalDate, false);
        List<Ticket> ticketsList = getTicketsList(result);
        return ticketsList;
    }
    
    public static void addTicket(Ticket ticket) {
        String [ ] sqlValues = TicketFormater.ticketToSQL(ticket);
        Provider provider = ticket.getProvider();
        if (!ProviderDAO.providerExist(provider.getCuit())) {
            ProviderDAO.addProvider(provider);
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

    private static List<Ticket> getTicketsList(ResultSet result) {
        List<Ticket> ticketsList = new ArrayList<>();
        try {
            while(result.next()) {
                Hashtable<String, String> ticketAttributes = new Hashtable<>();
                ticketAttributes.put("date", result.getString(2));
                ticketAttributes.put("ticketType", result.getString(3));
                ticketAttributes.put("noTicket", result.getString(4));
                if (result.getString(5) != null) ticketAttributes.put("numberTo", result.getString(5));
                ticketAttributes.put("authCode", result.getString(6));
                Provider prov = ProviderDAO.getProvider(result.getString(7));
                ticketAttributes.put("providerDocType", prov.getDocType());
                ticketAttributes.put("providerCuit", prov.getCuit());
                ticketAttributes.put("providerName", prov.getName());
                ticketAttributes.put("exchangeType", result.getString(8));
                ticketAttributes.put("exchangeMoney", result.getString(9));
                if (result.getString(10) != null) ticketAttributes.put("netAmountWI", result.getString(10));
                if (result.getString(11) != null) ticketAttributes.put("netAmountWOI", result.getString(11));
                if (result.getString(12) != null) ticketAttributes.put("amountImpEx", result.getString(12));
                if (result.getString(13) != null) ticketAttributes.put("iva", result.getString(13));
                ticketAttributes.put("totalAmount", result.getString(14));
                
                ticketsList.add(new Ticket(ticketAttributes));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ticketsList;
    }
}
