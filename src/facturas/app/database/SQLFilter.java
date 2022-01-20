/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facturas.app.database;

import facturas.app.utils.Pair;
import java.util.HashMap;
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
    
    public void add(Pair<String, String> fieldOperator, Pair<Object,Class<?>> values) {
        filters.put(fieldOperator, values);
    }
    
    /**
     * @return the string corresponding to "CONDITION" of WHERE clause of SQL
     */
    public String get() {
        String sqlCode = " WHERE";
        boolean firstOne = true; // The first iteration should not add the AND connector
        for (Pair<String,String> k : filters.keySet()) {
            if (!firstOne) {
                sqlCode += " AND";
            }
            
            Pair<Object, Class<?>> value = filters.get(k);
            sqlCode += " " + k.getFst() + " " + k.getSnd() + " ";
            
            Class<?> valueClass = value.getSnd();
            if (valueClass == String.class) {
                sqlCode += "'" + valueClass.cast(value.getFst()) + "'" ;
            } else {
                sqlCode += valueClass.cast(value.getFst());
            } // FIXME: We should add a special case for dates?
            firstOne = false;
        }
        return sqlCode;
    }
    
}
