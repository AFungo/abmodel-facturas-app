/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import facturas.app.Controller;
import facturas.app.models.Ticket;
import facturas.app.models.Provider;
import facturas.app.utils.Pair;
import facturas.app.utils.FormatUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nacho
 */
public class TicketDAO extends DAO {
    
    public static void addTicket(Ticket ticket) {
        Pair<String, String> sqlValues = FormatUtils.ticketToSQL(ticket);
        Provider provider = (Provider)ticket.getValues().get("provider");
        if (!ProviderDAO.providerExist(provider.getCuit())) {
            ProviderDAO.addProvider(provider);
        }
        String query = "INSERT INTO Ticket (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";
        executeQuery(query, true);
    }
        
    public static List<Ticket> getTickets() {
        ResultSet result = executeQuery("SELECT * FROM Ticket", false);
        List<Ticket> ticketsList = getTicketsList(result);
        return ticketsList;
    }
    
    public static List<Ticket> getTickets(SQLFilter filters) {
        if (filters == null) {
            throw new IllegalArgumentException("The parameter filters can not be null");
        }
        ResultSet result = executeQuery("SELECT * FROM Ticket" + filters.get(), false);
        List<Ticket> ticketsList = getTicketsList(result);
        return ticketsList;
    }

    private static List<Ticket> getTicketsList(ResultSet result) {
        List<Ticket> ticketsList = new LinkedList<>();
        try {
            while(result.next()) {
                Map<String, String> ticketAttributes = new HashMap<>();
                ticketAttributes.put("date", result.getString(2));
                ticketAttributes.put("type", result.getString(3));
                ticketAttributes.put("number", result.getString(4));
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
                ticketAttributes.put("issuedByMe", result.getString(15));
                ticketsList.add(new Ticket(ticketAttributes));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ticketsList;
    }
}
