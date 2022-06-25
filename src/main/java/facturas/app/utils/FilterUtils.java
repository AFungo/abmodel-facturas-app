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
 * Methods to manipulate the filters 
 * 
 */
public class FilterUtils {
    
    /**
     * creates a ticket filter
     * @param row ¿?
     * @param ticketsTable ¿? 
     * @return a new SQLFilter
     */
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
    
    /**
     * Creates a new tickets filter
     * @param values a map who have the values for the filter
     * @return the new SQLFilter with the upgrade of the paramas
     */
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
    
    /**
     * Creates a new ticket filter
     * @param values a map with the values
     * @param prov provider who want to filter
     * @param date date who want to filter
     * @return a SQLFilter with the new values
     */ 
    public static SQLFilter createTicketFilter(Map<String, String> values, Provider prov, java.util.Date date) {
        SQLFilter filter = new SQLFilter();
        filter.add("providerDoc", "=", prov.getValues().get("docNo"), String.class);
        filter.add("number", "=", values.get("number"), String.class);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date formatedDate = FormatUtils.dateGen(sdf.format(date));
        filter.add("date", "=", formatedDate, Date.class);
        
        return filter;
    }
    
    /**
    * removes filters of attributes of withholding and puts them in a new filter
    * @param filter filter who we want to separate the withholding filters
    * @return a new SQLFilter with only withholdings filters
    */
    public static SQLFilter separateWithholdingFilter(SQLFilter filter) {
        SQLFilter withholdingFilter = new SQLFilter();
        String[] attributesToRemove = {"id", "date", "number", "providerDoc", "delivered", "sector", "iva", "profits"};
        transferFilters(withholdingFilter, filter, attributesToRemove);
        
        return withholdingFilter;
    }

    /**
     * removes iva and profits attributes from filter and copies but doesn't 
     * remove the rest, all is put in a new filter
     * @param filter filter that we want to separate into withholding filter
     */
    public static SQLFilter separateWithholdingSpecialFilter(SQLFilter filter) {
        SQLFilter withholdingFilter = new SQLFilter();
        String[] attributesToRemove = {"iva", "profits"};
        String[] attributesToCopy = {"id", "date", "number", "providerDoc", "delivered", "sector"};
        transferFilters(withholdingFilter, filter, attributesToRemove);
        copyFilters(withholdingFilter, filter, attributesToCopy);
        
        return withholdingFilter;
    }
    
    /**
     * remove of the filter iva and profits
     * @param filter SQLFilter who have the current filter
     * @return a SQLFilter without iva and profit
     */
    public static SQLFilter removeIvaAndProfits(SQLFilter filter) {
        SQLFilter ivaProfitFilter = new SQLFilter();
        String[] attributesToRemove = {"iva", "profits"};
        transferFilters(ivaProfitFilter, filter, attributesToRemove);

        return ivaProfitFilter;
    }
    
    /*
     removes filter attributes from one filter and puts them in another
     takes two filters one to remove from and other to add to, also takes an
     array containing the attributes to transfer
     */
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
    
    /*
     copies filter attributes from one filter to another
     takes two filters one to copy from and other to add to, also takes an
     array containing the attributes to copy
     */
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
    /**
     * get a collection of list of conditions and make a only one list
     * @param lists it's the collection of list who we want to concatenate
     * @return a list with the concatenation of the list
     */
    public static List<Condition> concatenateLists(Collection<List<Condition>> lists) { 
        List<Condition> conditions = new LinkedList<> ();
        for (List<Condition> cond : lists) {
            conditions.addAll(cond);
        }
        
        return conditions;
    }
    /**
     * get a values of a specific filter
     * @param filter a SQLFilter with all the filter
     * @param attr the name of the filter who we want to get the values
     * @return a pair with the values of the specific filter
     */
    public static Pair<Float,Float> getFilterValues(SQLFilter filter, String attr) {
        List<Condition> conditions = filter.getCondition(attr);
        Pair<Float,Float> result = new Pair<> (null, null);
        for (Condition c : conditions) {
            if (c.getComparison() == ">=") {
                result.setFst((Float) c.getVal());
            } else if (c.getComparison() == "<=") {
                result.setSnd((Float) c.getVal());
            }
        }
        
        return result;
    }
}