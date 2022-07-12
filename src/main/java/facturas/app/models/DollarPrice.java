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
public class DollarPrice {

    private final Map<String, Object> values;

    public DollarPrice(Map<String, Object> values) {
        this.values = new HashMap<>(values);
        assert repOk();
    }

    public void setValues(Map<String, Object> values) {
        for (String key : values.keySet()) {
            this.values.replace(key, values.get(key));
        }
        assert repOk();
    }

    public Map<String, Object> getValues() {
        return new HashMap<>(values);
    }

    private boolean repOk() {
        Set<String> requiredKeys = Stream.of("date", "buy", "sell").collect(Collectors.toSet());
        if (!requiredKeys.containsAll(values.keySet())) {
            return false;
        }

        for (String key : requiredKeys) {
            if (values.get(key) == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DollarPrice dollarPrice = (DollarPrice) o;

        return values.equals(dollarPrice.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

}
