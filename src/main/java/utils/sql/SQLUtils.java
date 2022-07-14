package utils.sql;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.Provider;
import models.Sector;
import models.Withholding;
import utils.Pair;

public class SQLUtils {
    
    /**
     * Transforms withholding data into a SQL format data
     * 
     * @param w withholding with data to transform
     * @return a pair of strings containing the attributes to insert and the actual values
     * to insert to those attributes
     */
        //Este deberia ser un metodo general, sirve para todos los datos
     public static Pair<String, String> withholdingToSQL(Withholding w) {
        Map<String, Object> dict = w.getValues();
        List<String> attributes = new LinkedList<>(), values = new LinkedList<>();

        //for each attribute can not be null we add at the string for sql query
        for(String key : dict.keySet().stream().filter( k -> dict.get(k)!=null).collect(Collectors.toSet())) {   

            //en estos iff si lo instancias a cualquiera de los modelos podrias usar el getid
            if (dict.get(key).getClass() == Provider.class) {//iff is a provider object add the columns for the fk
                Map<String, Object> provDict = ((Provider)dict.get(key)).getID();
                for(String idKey : provDict.keySet()) {
                    attributes.add(idKey);
                    values.add(valueToSQL(provDict.get(idKey)));        
                }
            } else if(dict.get(key).getClass() == Sector.class){//iff is a sector object add the columns for the fk
                Map<String, Object> secDict = ((Sector)dict.get(key)).getID();
                for(String idKey : secDict.keySet()) {
                    attributes.add(idKey);
                    values.add(valueToSQL(secDict.get(idKey)));        
                }
            }else {
                attributes.add(key);
                values.add(valueToSQL((String)dict.get(key)));
            }
        }
        return new Pair<>(String.join(", ", attributes), String.join(", ", values));//separete the attriutes and values with ,
    }

    /*
     * this method check iff a value need '' for be inserted into the database
     */
    private static String valueToSQL(Object value) {
        if(value == String.class || value == Date.class) return "'" + value + "'";
        return (String)value;
    }

    /** 
     * Transforms a map into a string with a SQL form as follows:
     * attr1 = value1, attr2 = value2 ...
     * 
     * @param params map from attribute names to values
     * @return a string representing the map in a SQL form
     */
    public static String mapToSQLValues(Map<String, Object> params) {
        List<String> values = new LinkedList<>();
        for (String key : params.keySet()) {
            if (params.get(key).getClass() == String.class || params.get(key).getClass() == Date.class) {
                values.add(key + " = '" + params.get(key) + "'");
            } else {
                values.add(key + " = " + params.get(key));
            }
        }

        return String.join(", ", values);
    }

}