/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import facturas.app.utils.FormatUtils;
import facturas.app.utils.Pair;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Agustin
 */
public class SQLFilter {
    
    /**
     * The first pair is used for store the name of the field and the operator
     * that will be used in the condition and the second pair is used for store
     * the value for the condition and the class of that value (for the cast).
     */
    Map<Pair<String,String>, Pair<Object,Class<?>>> filters = new HashMap<>();
    
    public SQLFilter() {
        filters = new HashMap<>();
    }
    
    public SQLFilter(Map<String, Object> selectedFilters) {
        String text = (String)selectedFilters.get("startDate");
        if (!text.isEmpty()) { filters.put(new Pair<> ("date", ">"), new Pair(FormatUtils.dateGen(text), Date.class)); }
        text = (String)selectedFilters.get("finishDate");
        if (!text.isEmpty()) { filters.put(new Pair<> ("date", "<"), new Pair(FormatUtils.dateGen(text), Date.class)); }
        text = (String)selectedFilters.get("minTotal");
        if (!text.isEmpty()) { filters.put(new Pair<> ("totalAmount", ">"), new Pair(Float.parseFloat(text), Float.class)); }
        text = (String)selectedFilters.get("maxTotal");
        if (!text.isEmpty()) { filters.put(new Pair<> ("totalAmount", "<"), new Pair(Float.parseFloat(text), Float.class)); }
        
        text = (String)selectedFilters.get("minIva");
        if (!text.isEmpty()) { filters.put(new Pair<> ("iva", ">"), new Pair(Float.parseFloat(text), Float.class)); }
        text = (String)selectedFilters.get("maxIva");
        if (!text.isEmpty()) { filters.put(new Pair<> ("iva", "<"), new Pair(Float.parseFloat(text), Float.class)); }

        text = (String)selectedFilters.get("companyCuit");
        if (!text.isEmpty()) { filters.put(new Pair<> ("providerCuit", "="), new Pair(text, String.class)); }
        
        List<String> typesList = (List<String>)selectedFilters.get("ticketTypesList");
        if (!typesList.isEmpty()) { filters.put(new Pair<> ("type", "="), new Pair(typesList, List.class)); }
    }
    
    public void add(Pair<String, String> fieldOperator, Pair<Object,Class<?>> values) {
        filters.put(fieldOperator, values); //adding filter
    }
    
    /**
     * @return the string corresponding to "CONDITION" of WHERE clause of SQL
     */
    public String get() {
        String sqlCode = " WHERE";
        boolean firstOne = true; // The first iteration should not add the AND connector
        for (Pair<String,String> k : filters.keySet()) {
            if (!firstOne) 
                sqlCode +=  " AND";
            
            Pair<Object, Class<?>> value = filters.get(k);
            Class<?> valueClass = value.getSnd();
            
            if (valueClass == List.class) {
                List<String> values = (List<String>)value.getFst(); // when the type is a list, operator will be a =
                sqlCode += loadList(values, k);
            } else {
                sqlCode += " " + k.getFst() + " " + k.getSnd() + " ";
            
                if (valueClass == String.class) 
                    sqlCode += "'" + valueClass.cast(value.getFst()) + "'" ;
                else if (valueClass == Date.class) 
                    sqlCode += "'" + (Date)value.getFst() + "'";
                else
                    sqlCode += valueClass.cast(value.getFst());
            }
            firstOne = false;
        }
        return sqlCode;
    }
    
    private String loadList(List<String> list, Pair<String,String> pred) {
        String result = " (";
        for (String value : list) 
            result += pred.getFst() + " " + pred.getSnd() + " '" + value + "' OR ";

        result = result.substring(0, result.length() - 4);  //remove the last " OR "
        return result + ")";
    }
    
    public boolean isEmpty() {
        return filters.isEmpty();
    }
}
