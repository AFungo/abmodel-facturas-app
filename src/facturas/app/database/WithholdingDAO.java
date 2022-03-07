/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import facturas.app.Controller;
import static facturas.app.database.DAO.executeQuery;
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
 *
 * @author nacho
 */
public class WithholdingDAO {

    public static String addWithholding(Withholding withholding) {
        Pair<String, String> sqlValues = FormatUtils.withholdingToSQL(withholding);
        Provider provider = (Provider)withholding.getValues().get("provider");
        
        SQLFilter filter = new SQLFilter();
        filter.add("docNo", "=", provider.getValues().get("docNo"), String.class);
        if (!ProviderDAO.providerExist(filter)) {
            ProviderDAO.addProvider(provider);
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
        
    public static List<Withholding> getWithholdings() {
        String query = "SELECT * FROM Withholding";
        ResultSet result = executeQuery(query , false, true);
        List<Withholding> withholdingsList = getWithholdingsList(result);
        return withholdingsList;
    }
    
    public static List<Withholding> getWithholdingsWithNoTicket() {
        String query = "SELECT Withholding.* FROM Withholding LEFT JOIN Ticket ON Withholding.id = Ticket.id WHERE Ticket.id IS NULL";
        ResultSet result = executeQuery(query , false, true);
        List<Withholding> withholdingsList = getWithholdingsList(result);
        return withholdingsList;
    }
    
    public static boolean exists(SQLFilter filter) {
        String query = "SELECT * FROM Withholding " + filter.get();
        ResultSet result = executeQuery(query, false, true);
        try {
            return result.next();
        } catch (SQLException e) {
            throw new IllegalStateException(e.toString());
        }
    }
    
    public static List<Withholding> getWithholdings(SQLFilter filters) {
        if (filters == null) {
            throw new IllegalArgumentException("The parameter filters can not be null");
        }
        String query = "SELECT * FROM Withholding " + filters.get();
        ResultSet result = executeQuery(query, false, true);
        List<Withholding> withholdingsList = getWithholdingsList(result);
        return withholdingsList;
    }

    public static void changeAttribute(SQLFilter filters, String attribute, String value, boolean quotes) {
        if (quotes) {
            value = "'" + value + "'";
        }
        String query = "UPDATE Withholding SET " + attribute + " = " + value  + filters.get();
        executeQuery(query, true, false);
    }
    
    public static void deleteAttribute(SQLFilter filters, String attribute) {
        String query = "UPDATE Withholding SET " + attribute + " =  null " + filters.get();
        executeQuery(query, true, false);
    }
    
    public static void remove(SQLFilter filters) {
        String query = "DELETE FROM Withholding " + filters.get();
        executeQuery(query, true, true);
    }
    
    private static List<Withholding> getWithholdingsList(ResultSet result) {
        List<Withholding> whithholdingList = new LinkedList<>();
        try {
            while(result.next()) {
                Map<String, String> WithholdingAttributes = new HashMap<>();
                WithholdingAttributes.put("id", result.getString(1));
                WithholdingAttributes.put("date", result.getString(2));
                WithholdingAttributes.put("number", result.getString(3));
                Map<String, String> prov = ProviderDAO.getProvider(result.getString(4)).getValues();
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
                whithholdingList.add(new Withholding(WithholdingAttributes));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return whithholdingList;
    }
}
