/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import facturas.app.utils.FormatUtils;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Agustin
 */
public class SQLFilter {
    
    Map<String,Condition> conditions = new HashMap<>();
    Map<String,List<Condition>> orConditions = new HashMap<>();
    
    public SQLFilter() { }
       
    public SQLFilter(Map<String, Object> selectedFilters, boolean isTicket) {
        String text;
        text = (String)selectedFilters.get("id");
        if (FormatUtils.tryParse(text, "Integer")) {
            add("id", "=", Integer.parseInt(text), Integer.class);
        }
        
        text = (String)selectedFilters.get("number");
        if (FormatUtils.tryParse(text, "Integer")) {
            add("number", "=", text, String.class);
        }
        
        text = (String) selectedFilters.get("startDate");
        if (text != null && !text.isEmpty()) { add("date", ">=", FormatUtils.dateGen(text), Date.class); }
        text = (String)selectedFilters.get("finishDate");
        if (text != null && !text.isEmpty()) { add("date", "<=", FormatUtils.dateGen(text), Date.class); }
        text = (String)selectedFilters.get("minTotal");
        if (text != null && !text.isEmpty()) { add("totalAmount", ">=", Float.parseFloat(text), Float.class); }
        text = (String)selectedFilters.get("maxTotal");
        if (!text.isEmpty()) { add("totalAmount", "<=", Float.parseFloat(text), Float.class); }

        List<Object> providersDocs = (List<Object>)selectedFilters.get("providersDocs");
        if (!providersDocs.isEmpty()) { addDisjunction("providerDoc", "=", providersDocs, String.class); }
        
        text = (String)selectedFilters.get("sector");
        if (!text.isEmpty()) { add("sector", "=", text, String.class); }
        
        text = (String)selectedFilters.get("providerDoc");
        if (!text.isEmpty()) { add("providerDoc", "=", text, String.class); }
        
        
        if (isTicket) {
            text = (String)selectedFilters.get("minIva");
            if (!text.isEmpty()) { add("iva", ">=", Float.parseFloat(text), Float.class); }
            text = (String)selectedFilters.get("maxIva");
            if (!text.isEmpty()) { add("iva", "<=", Float.parseFloat(text), Float.class); }
            
            List<Object> typesList = (List<Object>)selectedFilters.get("ticketTypesList");
            if (!typesList.isEmpty()) { addDisjunction("type", "=", typesList, String.class); }
            
            if ((boolean)selectedFilters.get("sale")) {
                add("issuedByMe", "=", true, Boolean.class);
            } else if ((boolean)selectedFilters.get("purchase")) {
                add("issuedByMe", "=", false, Boolean.class);
            }
        }
        
    }
    
    public final void add(String attr, String comparison, Object val, Class<?> valClass) {
        conditions.put(attr, new Condition(attr, comparison, val, valClass));
    }
    
    public final void add(Condition condition) {
        conditions.put(condition.getAttr(), condition);
    }
    
    public void addDisjunction(String attr, String comparison, List<Object> vals, Class<?> valClass) {    
        List<Condition> orFilter = new LinkedList<>();
        for (Object v : vals) {
            orFilter.add(new Condition(attr, comparison, v, valClass));
        }
        orConditions.put(attr, orFilter);
    }
    
    public void addDisjunction(List<Condition> condition, String attr) {    
        orConditions.put(attr, condition);
    }
    
    public List<Condition> removeCondition(String attr) {
        List<Condition> removed = new LinkedList<>();
        Condition cond = conditions.remove(attr);
        while (cond != null) {
            removed.add(cond);
            cond = conditions.remove(cond);
        }
        return removed;
    }
    
    //returns a list of list because it could be several or conditions of the same attribute
    public List<List<Condition>> removeOrCondition(String attr) {
        List<List<Condition>> removed = new LinkedList<>();
        List<Condition> cond = orConditions.remove(attr);
        while (cond != null) {
            removed.add(cond);
            cond = orConditions.remove(cond);
        }
        return removed;
    }
    
    /**
     * @return the string corresponding to "CONDITION" of WHERE clause of SQL
     */
    public String get() {
        String sqlCode = " WHERE ";
        boolean firstOne = true; // The first iteration should not add the AND connector
        for (List<Condition> orCondition : orConditions.values()) {
            if (!firstOne) {
                sqlCode += " AND ";
            }
            
            sqlCode += "(" + getComplexCondition(orCondition, "OR") + ")";

            firstOne = false;
        }
        
        
        String lastConjunction = getComplexCondition(new LinkedList(conditions.values()), "AND");
        if (!lastConjunction.equals("")) {
            if (!firstOne) {
                sqlCode += " AND ";
            }
            sqlCode += lastConjunction;
        }
        
        return sqlCode;
    }
    
    private String getComplexCondition(List<Condition> conditions, String connectorOp) {
        String sqlCode = "";
        boolean firstOne = true;
        
        for (Condition c : conditions) {
            if (!firstOne) {
                sqlCode +=  " " + connectorOp + " ";
            }
            
            sqlCode += c.getAttr() + " " + c.getComparison() + " ";

            if (c.getValClass() == String.class) {
                sqlCode += "'" + c.getValClass().cast(c.getVal()) + "'" ;
            } else if (c.getValClass() == Date.class) {
                sqlCode += "'" + (Date)c.getVal() + "'";
            } else {
                sqlCode += c.getValClass().cast(c.getVal());
            }
            
            firstOne = false;
        }
        
        return sqlCode;
    }
    
    public boolean isEmpty() {
        return conditions.isEmpty() && orConditions.isEmpty();
    }
    
}
