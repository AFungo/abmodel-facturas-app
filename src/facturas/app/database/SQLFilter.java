/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Agustin
 */
public class SQLFilter {
    
    List<Condition> filters = new LinkedList<>();
    
    public SQLFilter() { }
       
    public SQLFilter(Map<String, Object> selectedFilters) {
        String text = (String) selectedFilters.get("startDate");
        if (text != null && !text.isEmpty()) { add("date", ">=", FormatUtils.dateGen(text), Date.class); }
        text = (String)selectedFilters.get("finishDate");
        if (text != null && !text.isEmpty()) { add("date", "<=", FormatUtils.dateGen(text), Date.class); }
        text = (String)selectedFilters.get("minTotal");
        if (!text.isEmpty()) { add("totalAmount", ">=", Float.parseFloat(text), Float.class); }
        text = (String)selectedFilters.get("maxTotal");
        if (!text.isEmpty()) { add("totalAmount", "<=", Float.parseFloat(text), Float.class); }
        
        text = (String)selectedFilters.get("minIva");
        if (!text.isEmpty()) { add("iva", ">=", Float.parseFloat(text), Float.class); }
        text = (String)selectedFilters.get("maxIva");
        if (!text.isEmpty()) { add("iva", "<=", Float.parseFloat(text), Float.class); }

        text = (String)selectedFilters.get("companyCuit");
        if (!text.isEmpty()) { add("providerDoc", "=", text, String.class); }
        
        List<String> typesList = (List<String>)selectedFilters.get("ticketTypesList");
        if (!typesList.isEmpty()) { add("type", "=", typesList, List.class); }
        
        if ((boolean)selectedFilters.get("sale")) {
            add("issuedByMe", "=", true, Boolean.class);
        } else if ((boolean)selectedFilters.get("purchase")) {
            add("issuedByMe", "=", false, Boolean.class);
        }
        
    }
    
    public final void add(String attr, String comparison, Object val, Class<?> valClass) {
        filters.add(new Condition(attr, comparison, val, valClass));
    }
    
    public void addDisjunction(String attribute, String comparison, List<Object> values, Class<?> valueClass) {    
    }
    
    
    /**
     * @return the string corresponding to "CONDITION" of WHERE clause of SQL
     */
    public String get() {
        String sqlCode = " WHERE";
        boolean firstOne = true; // The first iteration should not add the AND connector
        for (Condition f : filters) {
            if (!firstOne) {
                sqlCode +=  " AND";
            }
     
            Class<?> valueClass = f.getValClass();
            
            if (valueClass == List.class) {
                List<String> values = (List<String>)f.getVal(); // when the type is a list, operator will be a =
                sqlCode += loadList(values, f.getAttr(), f.getComparison());
            } else {
                sqlCode += " " + f.getAttr() + " " + f.getComparison() + " ";
            
                if (valueClass == String.class) 
                    sqlCode += "'" + valueClass.cast(f.getVal()) + "'" ;
                else if (valueClass == Date.class) 
                    sqlCode += "'" + (Date)f.getVal() + "'";
                else
                    sqlCode += valueClass.cast(f.getVal());
            }
            firstOne = false;
        }
        return sqlCode;
    }
    
    private String loadList(List<String> list, String attr, String comparison) {
        String result = " (";
        for (String value : list) 
            result += attr + " " + comparison + " '" + value + "' OR ";

        result = result.substring(0, result.length() - 4);  //remove the last " OR "
        return result + ")";
    }
    
    public boolean isEmpty() {
        return filters.isEmpty();
    }
}
