package facturas.app.models;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Agustin Nolasco
 */
public class Withholding {

    private final Map<String, Object> values;

    public Withholding(Map<String, Object> values) {
        this.values = new HashMap<>(values);
        assert repOk();
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

    protected static Set<String> requiredKeys() {
        return Stream.of("date", "number", "provider", "sector", "iva", "profits", "id", "delivered", "dollarPrice")
                .collect(Collectors.toSet());
    }

    private boolean repOk() {
        if (!requiredKeys().containsAll(values.keySet())) {
            return false;
        }
        return values.get("date") != null && values.get("number") != null && values.get("provider") != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Withholding withholding = (Withholding) o;

        return values.equals(withholding.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

}