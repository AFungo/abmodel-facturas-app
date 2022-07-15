package models;

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
public class Provider implements Model {

    private final Map<String, Object> values;
    private Map<String, Class<?>> types;

    public Provider(Map<String, Object> values) {
        this.values = new HashMap<>(values);
        setTypes();
        assert repOk();
    }

    private void setTypes() {
        types = new HashMap<String, Class<?>>() {{
            put("docNo", String.class);
            put("name", String.class);
            put("docType", String.class);
            put("address", String.class);
            put("provSector", String.class);
            put("alias", String.class);
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

    public Map<String, Object> getID() {
        return Collections.singletonMap("docNo", values.get("docNo"));
    }
    
    private boolean repOk() {
        Set<String> requiredKeys = Stream.of
                ("docNo", "name", "docType", "address", "provSector", "alias")
                .collect(Collectors.toSet());
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

        return values.get("docNo") != null && values.get("name") != null && values.get("docType") != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provider provider = (Provider) o;

        return values.equals(provider.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

}