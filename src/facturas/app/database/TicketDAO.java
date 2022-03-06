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
        
        SQLFilter filter = new SQLFilter();
        filter.add("docNo", "=", provider.getValues().get("docNo"), String.class);
        if (!ProviderDAO.providerExist(filter)) {
            ProviderDAO.addProvider(provider);
        }
        String query = "INSERT INTO Ticket (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";
        executeQuery(query, true, true);
    }
        
    public static List<Ticket> getTickets() {
        String query = "SELECT * FROM Withholding INNER JOIN Ticket ON Ticket.id = Withholding.id";
        ResultSet result = executeQuery(query, false, true);
        List<Ticket> ticketsList = getTicketsList(result);
        return ticketsList;
    }
    
    public static List<Ticket> getTickets(SQLFilter filters) {
        if (filters == null) {
            throw new IllegalArgumentException("The parameter filters can not be null");
        }
        
        String query = "SELECT * FROM Withholding INNER JOIN Ticket ON Ticket.id = Withholding.id " + filters.get();
        ResultSet result = executeQuery(query, false, true);
        List<Ticket> ticketsList = getTicketsList(result);
        return ticketsList;
    }

    public static void changeAttribute(SQLFilter filters, String attribute, String value) {
        String query = "UPDATE Ticket SET " + attribute + " = '" + value  + "' " + filters.get();
        executeQuery(query, true, false);
    }
    
    public static void remove(SQLFilter filters) {
        String query = "DELETE FROM Ticket " + filters.get();
        executeQuery(query, true, true);
    }
    
    private static List<Ticket> getTicketsList(ResultSet result) {
        List<Ticket> ticketsList = new LinkedList<>();
        try {
            while(result.next()) {
                Map<String, String> ticketAttributes = new HashMap<>();
                ticketAttributes.put("id", result.getString(1));
                ticketAttributes.put("date", result.getString(2));
                ticketAttributes.put("number", result.getString(3));
                //provider
                Map<String, String> prov = ProviderDAO.getProvider(result.getString(4)).getValues();
                ticketAttributes.put("docType", prov.get("docType"));
                ticketAttributes.put("docNo", prov.get("docNo"));
                ticketAttributes.put("name", prov.get("name"));
                ticketAttributes.put("direction", prov.get("direction"));
                ticketAttributes.put("provSector", prov.get("sector"));
                ticketAttributes.put("alias", prov.get("alias"));
                
                ticketAttributes.put("iva", result.getString(5));
                ticketAttributes.put("profits", result.getString(6));
                ticketAttributes.put("delivered", result.getString(7));
                ticketAttributes.put("sector", result.getString(8));
                //9 is ticket id
                ticketAttributes.put("type", result.getString(10));
                if (result.getString(11) != null) ticketAttributes.put("numberTo", result.getString(10));
                ticketAttributes.put("authCode", result.getString(12));
                ticketAttributes.put("exchangeType", result.getString(13));
                ticketAttributes.put("exchangeMoney", result.getString(14));
                if (result.getString(15) != null) ticketAttributes.put("netAmountWI", result.getString(15));
                if (result.getString(16) != null) ticketAttributes.put("netAmountWOI", result.getString(16));
                if (result.getString(17) != null) ticketAttributes.put("amountImpEx", result.getString(17));
                if (result.getString(18) != null) ticketAttributes.put("ivaTax", result.getString(18));
                ticketAttributes.put("totalAmount", result.getString(19));
                ticketAttributes.put("issuedByMe", result.getString(20));
                ticketsList.add(new Ticket(ticketAttributes));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ticketsList;
    }
}
