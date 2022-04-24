package facturas.app.database;

import facturas.app.utils.FilterUtils;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class used to represent a set of condition for a SQL query
 */
public class SQLFilter {
    
    Map<String,List<Condition>> conditions = new HashMap<>();
    Map<String,List<Condition>> orConditions = new HashMap<>();
    
    /**
     * Add a condition to the set of conditions
     * 
     * @param attr attribute of the table
     * @param comparison comparison operator that will be used
     * @param val value to which the attribute will be compared
     * @param valClass class of the value/attribute
     */
    public final void add(String attr, String comparison, Object val, Class<?> valClass) {
        List<Condition> conds = conditions.get(attr);
        if (conds == null) {
            conds = new LinkedList();
        }
        conds.add(new Condition(attr, comparison, val, valClass));
        conditions.put(attr, conds);
    }
    
    /**
     * Add a condition to the set of conditions
     * 
     * @param condition condition to be added to the set
     */
    public final void add(Condition condition) {
        String attr = condition.getAttr();
        List<Condition> conds = conditions.get(attr);
        if (conds == null) {
            conds = new LinkedList();
        }
        conds.add(condition);
        conditions.put(attr, conds);
    }
    
    /**
     * Add a complex condition with several disjunctions
     * 
     * @param attr attribute of the table
     * @param comparison comparison operator that will be used
     * @param vals values to which the attribute will be compared
     * @param valClass class of the values/attribute
     */
    public void addDisjunction(String attr, String comparison, Object vals, Class<?> valClass) {    
        List<Condition> orFilter = new LinkedList<>();
        for (Object v : (List<Object>)vals) {
            orFilter.add(new Condition(attr, comparison, v, valClass));
        }
        orConditions.put(attr, orFilter);
    }
    
    /**
     * Add a complex condition with several disjunctions
     * 
     * @param condition complex condition
     * @param attr attribute of the table
     */
    public void addDisjunction(List<Condition> condition, String attr) {    
        orConditions.put(attr, condition);
    }
    
    /**
     * Remove all the conditions with a specific attribute
     * 
     * @param attr attribute used to search the conditions to remove
     * @return the list of removed conditions
     */
    public List<Condition> removeCondition(String attr) {
        List<Condition> removed = conditions.remove(attr);
        if (removed == null) {
            removed = new LinkedList<>();
        }
        
        return removed;
    }
    
    /**
     * Remove all the disjunctions conditions with a specific attribute
     * 
     * @param attr attribute used to search the conditions to remove
     * @return returns a list of list because it could be several or conditions of the same attribute
     */
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
     * Get all the conditions with a specific attribute
     * 
     * @param attr attribute used to search the conditions to get
     * @return the list of gotten conditions
     */
    public List<Condition> getCondition(String attr) {
        List<Condition> condition = conditions.get(attr);
        if (condition == null) {
            condition = new LinkedList<>();
        }
        
        return condition;
    }
    
    /**
     * Get all the disjunctions conditions with a specific attribute
     * 
     * @param attr attribute used to search the conditions to get
     * @return returns a list of list because it could be several or conditions of the same attribute
     */
    public List<List<Condition>> getOrCondition(String attr) {
        List<List<Condition>> conditions = new LinkedList<>();
        List<Condition> cond = orConditions.get(attr);
        while (cond != null) {
            conditions.add(cond);
            cond = orConditions.get(cond);
        }
        return conditions;
    }
    
    /**
     * Gets the WHERE clause for SQL using the conditions added
     * 
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
        
        
        String lastConjunction = getComplexCondition(FilterUtils.concatenateLists(conditions.values()), "AND");
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
    
    /**
     * Returns a copy of this filter
     * 
     * @return an SQLFilter object with the same data as this
     */
    public SQLFilter clone() {
        SQLFilter clone = new SQLFilter();
        //creating a hashmap for conditions because clone method can just be invoked in a HashMap
        HashMap<String,List<Condition>> conditionsClone = (HashMap<String,List<Condition>>) this.conditions;
        clone.conditions = (Map<String, List<Condition>>) conditionsClone.clone();
        
        HashMap<String,List<Condition>> orConditionsClone = (HashMap<String,List<Condition>>) this.orConditions;
        clone.orConditions = (Map<String, List<Condition>>) orConditionsClone.clone();
        
        return clone;
    }
    
    /**
     * Checks if the filter is empty
     * 
     * @return return true iff the SQFilter is empty
     */
    public boolean isEmpty() {
        return conditions.isEmpty() && orConditions.isEmpty();
    }
    
}
