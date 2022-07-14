package utils.sql;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import models.*;
import utils.Pair;

public class SQLUtils {

    private static final Set<Class<?>> needDoubleQuotes = Stream.of(
            Date.class, String.class
    ).collect(Collectors.toSet());

    private static final Set<Class<?>> modelsTypes = Stream.of(
            Withholding.class, Ticket.class, Provider.class, Sector.class, DollarPrice.class
    ).collect(Collectors.toSet());

    /**
     * Transforms a Model into a SQL format value.
     * 
     * @param model to be transformed
     * @return a pair of strings containing the attributes and its values
     */
     public static Pair<String, String> modelToSQL(Model model) {
        Map<String, Object> modelValues = model.getValues();
        List<String> attributes = new LinkedList<>(), values = new LinkedList<>();

        for(String key : modelValues.keySet().stream().filter(k -> modelValues.get(k) != null).collect(Collectors.toSet())) {
            if (modelsTypes.contains(modelValues.get(key).getClass())) {
                Map<String, Object> subModelValues = ((Model)modelValues.get(key)).getID();
                for(String idKey : subModelValues.keySet()) {
                    attributes.add(idKey);
                    values.add(valueToSQL(subModelValues.get(idKey)));
                }
            } else {
                attributes.add(key);
                values.add(valueToSQL(modelValues.get(key)));
            }
        }

        return new Pair<>(
                String.join(", ", attributes),
                String.join(", ", values)
        );
    }

    /**
     * Given an object return its representation in SQL
     * adding double quotes iff the class of the value
     * is in the set of types that need double quotes.
     *
     * @param value to be converted to SQL format
     * @return SQL formatted value
     */
    private static String valueToSQL(Object value) {
        if (needDoubleQuotes.contains(value.getClass())) {
            return "'" + value + "'";
        }
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
        List<String> values = params.keySet().stream()
                .map(k -> k + " = " + valueToSQL(params.get(k)))
                .collect(Collectors.toList());
        return String.join(", ", values);
    }

}