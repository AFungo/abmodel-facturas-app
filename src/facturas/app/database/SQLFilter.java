/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import facturas.app.utils.FormatUtils;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Agustin
 */
public class SQLFilter {
    
    List<Condition> conditions = new LinkedList<>();
    List<List<Condition>> orConditions = new LinkedList<>();
    
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

        List<Object> providersDocs = (List<Object>)selectedFilters.get("providersDocs");
        if (!providersDocs.isEmpty()) { 
            System.out.println("Hi! the list is not empty");
            addDisjunction("providerDoc", "=", providersDocs, String.class); 
        } else {
            System.out.println("The list is empty");
        }
        
        List<Object> typesList = (List<Object>)selectedFilters.get("ticketTypesList");
        if (!typesList.isEmpty()) { addDisjunction("type", "=", typesList, String.class); }
        
        text = (String)selectedFilters.get("sector");
        if (!text.isEmpty()) { add("sector", "=", text, String.class); }
        
        if ((boolean)selectedFilters.get("sale")) {
            add("issuedByMe", "=", true, Boolean.class);
        } else if ((boolean)selectedFilters.get("purchase")) {
            add("issuedByMe", "=", false, Boolean.class);
        }
        
    }
    
    public final void add(String attr, String comparison, Object val, Class<?> valClass) {
        conditions.add(new Condition(attr, comparison, val, valClass));
    }
    
    public void addDisjunction(String attr, String comparison, List<Object> vals, Class<?> valClass) {    
        List<Condition> orFilter = new LinkedList<>();
        for (Object v : vals) {
            orFilter.add(new Condition(attr, comparison, v, valClass));
        }
        orConditions.add(orFilter);
    }
    
    /**
     * @return the string corresponding to "CONDITION" of WHERE clause of SQL
     */
    public String get() {
        String sqlCode = " WHERE ";
        boolean firstOne = true; // The first iteration should not add the AND connector
        for (List<Condition> orCondition : orConditions) {
            if (!firstOne) {
                sqlCode += " AND ";
            }
            
            sqlCode += "(" + getComplexCondition(orCondition, "OR") + ")";

            firstOne = false;
        }
        
        
        String lastConjunction = getComplexCondition(conditions, "AND");
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
