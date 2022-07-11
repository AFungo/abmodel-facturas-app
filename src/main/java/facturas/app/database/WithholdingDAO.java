package facturas.app.database;

import facturas.app.Controller;
import facturas.app.models.Provider;
import facturas.app.models.Withholding;
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
 * Data Access Object used for table Withholding's table of the database
 */
public class WithholdingDAO extends DAO {

    /**
     * Add a new withholding to the database
     * 
     * @param withholding withholding to be added
     * @return the id of the withholding added
     */
    public static String add(Withholding withholding) {
        Pair<String, String> sqlValues = FormatUtils.withholdingToSQL(withholding);
        Provider provider = (Provider)withholding.getValues().get("provider");
        
        SQLFilter filter = new SQLFilter();
        filter.add("docNo", "=", provider.getValues().get("docNo"), String.class);
        if (!ProviderDAO.exist(filter)) {
            ProviderDAO.add(provider);
        }
        String query = "INSERT INTO Withholding (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";
        ResultSet generatedKeys = executeQuery(query, true, true);
        
        String id = "";
        try {
            generatedKeys.next();
            id = String.valueOf(generatedKeys.getInt(1));
        } catch (SQLException ex) {
            Logger.getLogger(WithholdingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    /**
     * Given a filter, checks if the executed query returns an empty set 
     * or not
     * 
     * @param filter filter that will be used in the query
     * @return true iff the result if not empty
     */
    public static boolean exist(SQLFilter filter) {    
        String query = "SELECT * FROM Withholding " + filter.get();
        ResultSet result = executeQuery(query, false, true);
        try {
            return result.next();
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    /**
     * Getter of withholding's that has no tickets associated
     * 
     * @return all withholding's that has no tickets associated
     */
    public static List<Withholding> getWithholdingsWithNoTicket() {
        String query = "SELECT Withholding.* FROM Withholding LEFT JOIN Ticket ON Withholding.id = Ticket.id WHERE Ticket.id IS NULL";
        ResultSet result = executeQuery(query , false, true);
        List<Withholding> withholdingsList = getWithholdingsList(result);
        return withholdingsList;
    }
    
    /**
     * Withholding's getter
     * 
     * @return a list of all withholding's
     */
    public static List<Withholding> get() {
        String query = "SELECT * FROM Withholding";
        ResultSet result = executeQuery(query , false, true);
        List<Withholding> withholdingsList = getWithholdingsList(result);
        return withholdingsList;
    }
    
    /**
     * Withholding's getter using a filter
     * 
     * @param filter filter used for the query
     * @return a list of all withholding's obtained using the filter
     */
    public static List<Withholding> get(SQLFilter filter) {
        if (filter == null) {
            throw new IllegalArgumentException("The parameter filters can not be null");
        }
        String query = "SELECT * FROM Withholding " + filter.get();
        ResultSet result = executeQuery(query, false, true);
        List<Withholding> withholdingsList = getWithholdingsList(result);
        return withholdingsList;
    }

    /**
     * Given a filter to search withholding's and an attribute, updates the
     * attribute of selected withholding's with the given value
     *
     * @param filter filter used for search withholding's
     * @param attribute attribute of the withholding's that will be updated
     * @param value value used for the update
     * @param quotes true iff the values require quotes
     */
    public static void update(SQLFilter filter, String attribute, String value, boolean quotes) {
        if (quotes) {
            value = "'" + value + "'";
        }
        String query = "UPDATE Withholding SET " + attribute + " = " + value  + filter.get();
        executeQuery(query, true, false);
    }
    
    /**
     * Given a filter and an attribute set the attribute of the searched
     * withholding's with null
     * 
     * @param filter filter used for search withholding's
     * @param attribute attribute that will be set as null
     */
    public static void setNull(SQLFilter filter, String attribute) {
        String query = "UPDATE Withholding SET " + attribute + " =  null " + filter.get();
        executeQuery(query, true, false);
    }
    
    /**
     * Given a filter, search the withholding's and delete them
     * 
     * @param filter filter used for the withholding's search
     */
    public static void remove(SQLFilter filter) {
        String query = "DELETE FROM Withholding " + filter.get();
        executeQuery(query, true, true);
    }
    
    private static List<Withholding> getWithholdingsList(ResultSet result) {
        List<Withholding> withholdingList = new LinkedList<>();
        try {
            while(result.next()) {
                Map<String, Object> WithholdingAttributes = new HashMap<>();
                WithholdingAttributes.put("id", result.getString(1));
                WithholdingAttributes.put("date", result.getString(2));
                WithholdingAttributes.put("number", result.getString(3));
                Map<String, Object> prov = ProviderDAO.get(result.getString(4)).getValues();
                WithholdingAttributes.put("docType", prov.get("docType"));
                WithholdingAttributes.put("docNo", prov.get("docNo"));
                WithholdingAttributes.put("name", prov.get("name"));
                WithholdingAttributes.put("direction", prov.get("direction"));
                WithholdingAttributes.put("provSector", prov.get("sector"));
                WithholdingAttributes.put("alias", prov.get("alias"));
                WithholdingAttributes.put("iva", result.getString(5));
                WithholdingAttributes.put("profits", result.getString(6));
                WithholdingAttributes.put("delivered", result.getString(7));
                WithholdingAttributes.put("sector", result.getString(8));
                withholdingList.add(new Withholding(WithholdingAttributes));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return withholdingList;
    }
}
