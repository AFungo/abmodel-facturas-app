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
 * Data Access Object used for the Ticket's table of the database
 */
public class TicketDAO extends DAO {

    /**
     * Add a new ticket to the database
     *
     * @param ticket ticket to be added
     */
    public static void add(Ticket ticket) {
        Pair<String, String> sqlValues = FormatUtils.ticketToSQL(ticket);
        Provider provider = (Provider) ticket.getValues().get("provider");

        SQLFilter filter = new SQLFilter();
        filter.add("docNo", "=", provider.getValues().get("docNo"), String.class);
        if (!ProviderDAO.exist(filter)) {
            ProviderDAO.add(provider);
        }
        String query = "INSERT INTO Ticket (" + sqlValues.getFst() + ") "
                + "VALUES (" + sqlValues.getSnd() + ")";
        executeQuery(query, true, true);
    }

    /**
     * Tickets getter
     *
     * @return a list of all tickets
     */
    public static List<Ticket> get() {
        String query = "SELECT * FROM Withholding INNER JOIN Ticket ON Ticket.id = Withholding.id";
        ResultSet result = executeQuery(query, false, true);
        List<Ticket> ticketsList = getTicketsList(result);
        return ticketsList;
    }

    /**
     * Tickets getter using a filter
     *
     * @param filter filter used in the query
     * @return a list of all tickets obtained using the filter
     */
    public static List<Ticket> get(SQLFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("The parameter filters can not be null");
        }

        String query = "SELECT * FROM Withholding INNER JOIN Ticket ON Ticket.id = Withholding.id " + filter.get();
        ResultSet result = executeQuery(query, false, true);
        List<Ticket> ticketsList = getTicketsList(result);
        return ticketsList;
    }

    /**
     * Given a filter to search tickets and an attribute, updates the
     * attribute of selected tickets with the given value
     *
     * @param filter filter used for search tickets
     * @param attribute attribute of the tickets that will be updated
     * @param value value used for the update
     * @param quotes true iff the values require quotes
     */
    public static void update(SQLFilter filter, String attribute, String value, boolean quotes) {
        if (quotes) {
            value = "'" + value + "'";
        }
        String query = "UPDATE Ticket SET " + attribute + " = " + value + filter.get();
        executeQuery(query, true, false);
    }

    /**
     * Given a filter, search the tickets and delete them
     * 
     * @param filter filter used for the tickets search
     */
    public static void remove(SQLFilter filter) {
        String query = "DELETE FROM Ticket " + filter.get();
        executeQuery(query, true, true);
    }

    private static List<Ticket> getTicketsList(ResultSet result) {
        List<Ticket> ticketsList = new LinkedList<>();
        try {
            while (result.next()) {
                Map<String, String> ticketAttributes = new HashMap<>();
                ticketAttributes.put("id", result.getString(1));
                ticketAttributes.put("date", result.getString(2));
                ticketAttributes.put("number", result.getString(3));
                //provider
                Map<String, String> prov = ProviderDAO.get(result.getString(4)).getValues();
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
                if (result.getString(11) != null) {
                    ticketAttributes.put("numberTo", result.getString(10));
                }
                ticketAttributes.put("authCode", result.getString(12));
                ticketAttributes.put("exchangeType", result.getString(13));
                ticketAttributes.put("exchangeMoney", result.getString(14));
                if (result.getString(15) != null) {
                    ticketAttributes.put("netAmountWI", result.getString(15));
                }
                if (result.getString(16) != null) {
                    ticketAttributes.put("netAmountWOI", result.getString(16));
                }
                if (result.getString(17) != null) {
                    ticketAttributes.put("amountImpEx", result.getString(17));
                }
                if (result.getString(18) != null) {
                    ticketAttributes.put("ivaTax", result.getString(18));
                }
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
