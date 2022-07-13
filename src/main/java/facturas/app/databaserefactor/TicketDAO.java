package facturas.app.databaserefactor;

import facturas.app.models.Ticket;
import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;
import logger.Handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * this class implements DAO interface for Tiket model
 * 
 */
public class TicketDAO implements DAO<Ticket> {

    private static TicketDAO instance;

    private final Set<Ticket> cache;
    private boolean cacheLoaded;

    public static TicketDAO getInstance() {
        if (instance == null) {
            instance = new TicketDAO();
        }
        return instance;
    }

    private TicketDAO() {
        cache = new HashSet<>();
        cacheLoaded = false;
    }

    @Override
    public Set<Ticket> getAll() {
        if (!cacheLoaded) {
            loadCache();
        }
        return cache;
    }

    @Override
    public boolean save(Ticket ticket) {
        prepareCache();        
        Pair<String, String> sqlValues = FormatUtils.ticketToSQL(ticket);
        String query = "INSERT INTO Ticket (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";

        int generatedId = DatabaseUtils.executeCreate(query);
        if (generatedId == 0) {
            return false;
        }

        //add id to withholding
        ticket.setValues(new HashMap<String, Object>() {{
            put("id", String.valueOf(generatedId));
        }});
        cache.add(ticket);//add item to cache if executeQuery was successful

        return true;
    }


    @Override
    public boolean update(Ticket ticket, Map<String, Object> params) {
        prepareCache();

        String query = "UPDATE Ticket SET " + FormatUtils.mapToSQLValues(params) + " WHERE id = " 
        + ticket.getValues().get("id");
        
        int affectedRows = DatabaseUtils.executeUpdate(query);

        if (affectedRows == 0) {
            return false;
        }

        //update cache if executeQuery was successful
        cache.remove(ticket);
        ticket.setValues(params);
        cache.add(ticket);
        return true;
     }

    @Override
    public boolean delete(Ticket ticket) {
        prepareCache();        

        String query = "DELETE FROM Ticket WHERE id = " + ticket.getValues().get("id");    
        
        int affectedRows = DatabaseUtils.executeUpdate(query);
        if (affectedRows == 0) {
            return false;
        }

        cache.remove(ticket);
        return true;
    }

    /**
     * This method must load the cache with the data from the database
     */
    private void loadCache() {
        String query = "SELECT * FROM Ticket";
        ResultSet result = DatabaseUtils.executeQuery(query);
        try {
            while(result.next()) {
                cache.add(new Ticket(new HashMap<String, Object>() {{
                    put("id", Integer.parseInt(result.getString(1)));
                    put("type", result.getString(2));
                    if (result.getString(3) != null) {
                        put("numberTo", Integer.parseInt(result.getString(3)));
                    }
                    put("authCode", result.getString(4));
                    put("exchangeType", Float.parseFloat(result.getString(5)));
                    put("exchangeMoney", result.getString(6));
                    if (result.getString(7) != null) {
                        put("netAmountWI", Float.parseFloat(result.getString(7)));
                    }
                    if (result.getString(8) != null) {
                        put("netAmountWOI", Float.parseFloat(result.getString(8)));
                    }
                    if (result.getString(9) != null) {
                        put("amountImpEx", Float.parseFloat(result.getString(9)));
                    }
                    if (result.getString(10) != null) {
                        put("ivaTax", Float.parseFloat(result.getString(10)));
                    }
                    put("totalAmount", Float.parseFloat(result.getString(11)));
                    put("issuedByMe", Boolean.valueOf(result.getString(12)));            
                }}));
            }
        } catch (SQLException ex) {
            cache.clear();//Iff fails in load an object cache are emmpty, all are load or nthing are load
            Handler.logUnexpectedError(ex);
        }

    }
    private void prepareCache() {
        if (!cacheLoaded) {
            loadCache();
            cacheLoaded = true;
        }
    }
}
