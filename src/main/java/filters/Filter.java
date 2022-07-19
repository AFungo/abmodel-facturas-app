package filters;

import java.util.Objects;

public class Filter {

    String attribute;
    Object value;
    Comparison comparison;

    public Filter(String attribute, Object value, Comparison comparison) {
        Objects.requireNonNull(attribute, "The attribute cannot be null");
        Objects.requireNonNull(comparison, "The comparison cannot be null");

        this.attribute = attribute;
        this.value = value;
        this.comparison = comparison;
    }

    public String getAttribute() {
        return attribute;
    }

    public Object getValue() {
        return value;
    }

    public Comparison getComparison() {
        return comparison;
    }

}
