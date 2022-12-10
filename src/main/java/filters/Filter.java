package filters;

import models.Model;
import models.set.ModelSet;

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

    public static <T extends Model> ModelSet<T> applyFilters(ModelSet<T> items, Filter... filters) {
        ModelSet<T> filteredItems = new ModelSet<>(items);
        for (Filter filter : filters) {
            switch (filter.getComparison()) {
                case EQUALS:
                    filteredItems = filteredItems.filterByEqualsTo(filter.getAttribute(), filter.getValue());
                    break;
                case GREATER_EQUALS:
                    filteredItems = filteredItems.filterByGreaterOrEqualsThan(filter.getAttribute(), filter.getValue());
                    break;
                case LESS_EQUALS:
                    filteredItems = filteredItems.filterByLessOrEqualsThan(filter.getAttribute(), filter.getValue());
                    break;
            }
        }
        return filteredItems;
    }

}
