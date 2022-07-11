package facturas.app.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Agustin Nolasco
 */
public class Provider {

    Map<String, Object> values;

    public Provider(Map<String, Object> values) {
        this.values = new HashMap<>(values);
        assert repOk();
    }

    public void setValues(Map<String, String> values) {
        for (String s : values.keySet()) {
            this.values.replace(s, values.get(s));
        }
        assert repOk();
    }

    public Map<String, Object> getValues() {
        return new HashMap<>(values);
    }

    private boolean repOk() {
        Set<String> requiredKeys = Stream.of
                ("docNo", "name", "docType", "direction", "provSector", "alias")
                .collect(Collectors.toSet());
        if (!values.keySet().containsAll(requiredKeys)) {
            return false;
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