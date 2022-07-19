package models;

import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Agustin Nolasco
 */
public class DollarPrice implements Model {

    private final Map<String, Object> values;
    Map<String, Class<?>> types;

    public DollarPrice(Map<String, Object> values) {
        this.values = new HashMap<>(values);
        setTypes();
        assert repOk();
    }

    private void setTypes() {
        types = new HashMap<String, Class<?>>() {{
            put("id", Integer.class);
            put("date", Date.class);
            put("buy", Float.class);
            put("sell", Float.class);
        }};
    }

    public Map<String, Class<?>> getTypes() {
        return new HashMap<>(types);
    }

    public void setValues(Map<String, Object> values) {
        for (String key : values.keySet()) {
            this.values.put(key, values.get(key));
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

    private boolean repOk() {
        Set<String> requiredKeys = Stream.of("id", "date", "buy", "sell").collect(Collectors.toSet());
        if (!requiredKeys.containsAll(values.keySet())) {
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

        return values.get("date") != null && values.get("buy") != null && values.get("sell") != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DollarPrice other = (DollarPrice) o;

        return values.get("date").equals(other.values.get("date"));
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

}
