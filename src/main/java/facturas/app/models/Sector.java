package facturas.app.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sector {

    Map<String, Object> values;

    public Sector(Map<String, Object> values) {
        this.values = new HashMap<>(values);
        assert repOk();
    }

    public void setValues(Map<String, Object> values) {
        for (String s : values.keySet()) {
            this.values.replace(s, values.get(s));
        }
        assert repOk();
    }

    public Map<String, Object> getValues() {
        return new HashMap<>(values);
    }

    private boolean repOk() {
        Set<String> requiredKeys = Stream.of("name").collect(Collectors.toSet());
        if (!requiredKeys.containsAll(values.keySet())) {
            return false;
        }
        return values.get("name") != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sector sector = (Sector) o;

        return values.equals(sector.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

}
