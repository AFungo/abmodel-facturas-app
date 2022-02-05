/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import facturas.app.Controller;
import static facturas.app.database.DAO.executeQuery;
import facturas.app.models.Withholding;
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

    public static void addWithholding(Withholding withholding) {
        if (withholding instanceof Withholding)
            throw new IllegalArgumentException("Unable to add as withholding, Withholdings must be added to Withholding table");
        Pair<String, String> sqlValues = FormatUtils.withholdingToSQL(withholding);
        Provider provider = (Provider)withholding.getValues().get("provider");
        
        SQLFilter filter = new SQLFilter();
        filter.add("cuit", "=", provider.getDocNo(), String.class);
        if (!ProviderDAO.providerExist(filter)) {
            ProviderDAO.addProvider(provider);
        }
        String query = "INSERT INTO Withholding (" + sqlValues.getFst() + ") "
            + "VALUES (" + sqlValues.getSnd() + ")";
        executeQuery(query, true);
    }
        
    public static List<Withholding> getWithholdings() {
        ResultSet result = executeQuery("SELECT * FROM Withholding", false);
        List<Withholding> withholdingsList = getWithholdingsList(result);
        return withholdingsList;
    }
    
    public static List<Withholding> getWithholdings(SQLFilter filters) {
        if (filters == null) {
            throw new IllegalArgumentException("The parameter filters can not be null");
        }
        ResultSet result = executeQuery("SELECT * FROM Withholding" + filters.get(), false);
        List<Withholding> withholdingsList = getWithholdingsList(result);
        return withholdingsList;
    }

    private static List<Withholding> getWithholdingsList(ResultSet result) {
        List<Withholding> whithholdingList = new LinkedList<>();
        try {
            while(result.next()) {
                Map<String, String> WithholdingAttributes = new HashMap<>();
                WithholdingAttributes.put("id", result.getString(1));
                WithholdingAttributes.put("date", result.getString(2));
                WithholdingAttributes.put("type", result.getString(3));
                WithholdingAttributes.put("number", result.getString(4));
                Map<String, String> prov = ProviderDAO.getProvider(result.getString(5)).getValues();
                WithholdingAttributes.put("docType", prov.get("docType"));
                WithholdingAttributes.put("docNo", prov.get("docNo"));
                WithholdingAttributes.put("name", prov.get("name"));
                WithholdingAttributes.put("direction", prov.get("direction"));
                WithholdingAttributes.put("provSector", prov.get("sector"));
                WithholdingAttributes.put("delivered", result.getString(6));
                WithholdingAttributes.put("totalAmount", result.getString(7));
                whithholdingList.add(new Withholding(WithholdingAttributes));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return whithholdingList;
    }
}
