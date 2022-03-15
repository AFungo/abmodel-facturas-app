/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.utils;

import facturas.app.database.Condition;
import facturas.app.database.SQLFilter;
import facturas.app.models.Provider;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;

/**
 *
 * @author nacho
 */
public class FilterUtils {
    
    public static SQLFilter createTicketFilter(int row, JTable ticketsTable) {
        SQLFilter filter = new SQLFilter();
        Date date = (Date)ticketsTable.getValueAt(row, 1); //1 is the date column
        filter.add("date", "=", date, Date.class);
        String noTicket = (String)ticketsTable.getValueAt(row, 3); //3 is the noTicket column
        filter.add("number", "=", noTicket, String.class);
        String cuit = (String)ticketsTable.getValueAt(row, 6); //6 is the cuit column
        filter.add("providerDoc", "=", cuit, String.class);
        
        return filter;
    }
    
    public static SQLFilter createTicketFilter(Map<String, String> values) {
        SQLFilter filter = new SQLFilter();
        Date date = FormatUtils.dateGen(values.get("date"));
        filter.add("date", "=", date, Date.class);
        String noTicket = values.get("number");
        filter.add("number", "=", noTicket, String.class);
        String docNo = values.get("docNo");
        filter.add("providerDoc", "=", docNo, String.class);
        
        return filter;
    }
    
    public static SQLFilter createTicketFilter(Map<String, String> values, Provider prov, java.util.Date date) {
        SQLFilter filter = new SQLFilter();
        filter.add("providerDoc", "=", prov.getValues().get("docNo"), String.class);
        filter.add("number", "=", values.get("number"), String.class);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date formatedDate = FormatUtils.dateGen(sdf.format(date));
        filter.add("date", "=", formatedDate, Date.class);
        
        return filter;
    }
    
    /*
        removes filters of attributes of withholding and puts them in a new filter
    */
    public static SQLFilter separateWithholdingFilter(SQLFilter filter) {
        SQLFilter withholdingFilter = new SQLFilter();
        String[] attributesToRemove = {"id", "date", "number", "providerDoc", "delivered", "sector", "iva", "profits"};
        transferFilters(withholdingFilter, filter, attributesToRemove);
        
        return withholdingFilter;
    }

    public static SQLFilter separateWithholdingSpecialFilter(SQLFilter filter) {
        SQLFilter withholdingFilter = new SQLFilter();
        String[] attributesToRemove = {"iva", "profits"};
        String[] attributesToCopy = {"id", "date", "number", "providerDoc", "delivered", "sector"};
        transferFilters(withholdingFilter, filter, attributesToRemove);
        copyFilters(withholdingFilter, filter, attributesToCopy);
        
        return withholdingFilter;
    }
    
    public static SQLFilter removeIvaAndProfits(SQLFilter filter) {
        SQLFilter ivaProfitFilter = new SQLFilter();
        String[] attributesToRemove = {"iva", "profits"};
        transferFilters(ivaProfitFilter, filter, attributesToRemove);

        return ivaProfitFilter;
    }
    
    private static void transferFilters(SQLFilter filterToAdd, SQLFilter filterToRemove, String[] attributes) {
        for (String attr : attributes) {
            for (Condition cond : filterToRemove.removeCondition(attr)) {
                filterToAdd.add(cond);  //transfering and conditions
            }
            for (List<Condition> cond : filterToRemove.removeOrCondition(attr)) {
                filterToAdd.addDisjunction(cond, attr); //transfering or conditions
            }
        }
    }
    
    private static void copyFilters(SQLFilter filterToAdd, SQLFilter filterToCopy, String[] attributes) {
        for (String attr : attributes) {
            for (Condition cond : filterToCopy.getCondition(attr)) {
                filterToAdd.add(cond);  //transfering and conditions
            }
            for (List<Condition> cond : filterToCopy.getOrCondition(attr)) {
                filterToAdd.addDisjunction(cond, attr); //transfering or conditions
            }
        }
    }
    
    public static List<Condition> concatenateLists(Collection<List<Condition>> lists) { 
        List<Condition> conditions = new LinkedList<> ();
        for (List<Condition> cond : lists) {
            conditions.addAll(cond);
        }
        
        return conditions;
    }
}