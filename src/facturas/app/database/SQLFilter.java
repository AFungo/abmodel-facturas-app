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
    
    public final void add(String attr, String comparison, Object val, Class<?> valClass) {
        conditions.put(attr, new Condition(attr, comparison, val, valClass));
    }
    
    public final void add(Condition condition) {
        conditions.put(condition.getAttr(), condition);
    }
    
    public void addDisjunction(String attr, String comparison, Object vals, Class<?> valClass) {    
        List<Condition> orFilter = new LinkedList<>();
        for (Object v : (List<Object>)vals) {
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
