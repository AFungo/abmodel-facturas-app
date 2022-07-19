package models;

import java.sql.Date;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Agustin Nolasco
 */
public class Withholding implements Model {

    private final Map<String, Object> values;
    private HashMap<String, Class<?>> types;

    public Withholding(Map<String, Object> values) {
        this.values = new HashMap<>(values);
        setTypes();
        assert repOk();
    }

    // TODO: Remove this, remove the relation of inheritance Ticket -> Withholding
    public Withholding() {
        values = null;
    }

    private void setTypes() {
        types = new HashMap<String, Class<?>>() {{
            put("id", Integer.class);
            put("date", Date.class);
            put("number", String.class);
            put("provider", Provider.class);
            put("iva", Float.class);
            put("profits", Float.class);
            put("delivered", Boolean.class);
            put("sector", Sector.class);
        }};
    }

    public Map<String, Class<?>> getTypes() {
        return new HashMap<>(types);
    }

    public void setValues(Map<String, Object> values) {
        for (String s : values.keySet()) {
            this.values.put(s, values.get(s));
        }
        assert repOk();
    }

    public Map<String, Object> getValues() {
        return new HashMap<>(values);
    }

    @Override
    public Map<String, Object> getID() {
        return Collections.singletonMap("id", values.get("id"));
    }

    /**
     * 
     * @return all the attributes of the model
     */
    public static List<String> getAttributes() {
        return Stream.of("provider", "sector", "id", "date", "number", "iva", "profits", "delivered", "dollarPrice")
                .collect(Collectors.toList());
    }

    private boolean repOk() {
        if (!getAttributes().containsAll(values.keySet())) {
            return false;
        }

        for (String key : values.keySet()) {
            Object value = values.get(key);
            if (value != null) {
                if (value.getClass() != types.get(key)) {
                    return false;
                }
            }
        }

        return values.get("date") != null && values.get("number") != null && values.get("provider") != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Withholding other = (Withholding) o;

        return values.get("date").equals(other.values.get("date"))
                && values.get("number").equals(other.values.get("number"))
                && values.get("provider").equals(other.values.get("provider"));
    }

    @Override
    public int hashCode() {
        int result = 31;
        result += 17 * values.get("date").hashCode();
        result += 17 * values.get("number").hashCode();
        result += 17 * values.get("provider").hashCode();
        return result;
    }

}