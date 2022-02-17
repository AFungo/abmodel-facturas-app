/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import facturas.app.Controller;
import facturas.app.models.Ticket;
import facturas.app.models.Withholding;
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
        String query = "INSERT INTO Ticket (id, " + sqlValues.getFst() + ") "
            + "VALUES (NEXT VALUE FOR id_seq, " + sqlValues.getSnd() + ")";
        executeQuery(query, true);
    }
        
    public static List<Withholding> getTickets() {
        ResultSet result = executeQuery("SELECT * FROM Ticket", false);
        List<Withholding> ticketsList = getTicketsList(result);
        return ticketsList;
    }
    
    public static List<Withholding> getTickets(SQLFilter filters) {
        if (filters == null) {
            throw new IllegalArgumentException("The parameter filters can not be null");
        }
        ResultSet result = executeQuery("SELECT * FROM Ticket" + filters.get(), false);
        List<Withholding> ticketsList = getTicketsList(result);
        return ticketsList;
    }

    public static void changeAttribute(SQLFilter filters, String attribute, String value) {
        String query = "UPDATE Ticket SET " + attribute + " = '" + value  + "' " + filters.get();
        executeQuery(query, true);
    }
    
    public static void remove(SQLFilter filters) {
        String query = "DELETE FROM Ticket " + filters.get();
        executeQuery(query, true);
    }
    
    private static List<Withholding> getTicketsList(ResultSet result) {
        List<Withholding> ticketsList = new LinkedList<>();
        try {
            while(result.next()) {
                Map<String, String> ticketAttributes = new HashMap<>();
                ticketAttributes.put("id", result.getString(1));
                ticketAttributes.put("date", result.getString(2));
                ticketAttributes.put("type", result.getString(3));
                ticketAttributes.put("number", result.getString(4));
                if (result.getString(5) != null) ticketAttributes.put("numberTo", result.getString(5));
                ticketAttributes.put("authCode", result.getString(6));
                Map<String, String> prov = ProviderDAO.getProvider(result.getString(7)).getValues();
                ticketAttributes.put("docType", prov.get("docType"));
                ticketAttributes.put("docNo", prov.get("docNo"));
                ticketAttributes.put("name", prov.get("name"));
                ticketAttributes.put("direction", prov.get("direction"));
                ticketAttributes.put("provSector", prov.get("sector"));
                ticketAttributes.put("alias", prov.get("alias"));
                ticketAttributes.put("exchangeType", result.getString(8));
                ticketAttributes.put("exchangeMoney", result.getString(9));
                if (result.getString(10) != null) ticketAttributes.put("netAmountWI", result.getString(10));
                if (result.getString(11) != null) ticketAttributes.put("netAmountWOI", result.getString(11));
                if (result.getString(12) != null) ticketAttributes.put("amountImpEx", result.getString(12));
                if (result.getString(13) != null) ticketAttributes.put("iva", result.getString(13));
                ticketAttributes.put("totalAmount", result.getString(14));
                ticketAttributes.put("issuedByMe", result.getString(15));
                ticketAttributes.put("sector", result.getString(16));
                ticketAttributes.put("delivered", result.getString(17));
                ticketsList.add(new Ticket(ticketAttributes));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ticketsList;
    }
}
