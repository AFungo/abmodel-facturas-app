package models.set;

import models.Model;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ModelSet<T extends Model> extends HashSet<T> {

    public ModelSet() {
        super();
    }

    private ModelSet(Collection<T> c) {
        super(c);
    }

    public ModelSet<T> filterByEqualsTo(String attribute, Object value) {
        return new ModelSet<>(stream()
                .filter(t -> t.getValues().get(attribute).equals(value))
                .collect(Collectors.toSet())
        );
    }

    public ModelSet<T> filterByGreaterOrEqualsThan(String attribute, Object value) {
        return new ModelSet<>(stream()
                .filter(t -> ((Comparable<Object>) t.getValues().get(attribute)).compareTo(value) >= 0)
                .collect(Collectors.toSet())
        );
    }

    public ModelSet<T> filterByLessOrEqualsThan(String attribute, Object value) {
        return new ModelSet<>(stream()
                .filter(t -> ((Comparable<Object>)t.getValues().get(attribute)).compareTo(value) <= 0)
                .collect(Collectors.toSet())
        );
    }

    public ModelSet<T> union(ModelSet<T> other) {
        ModelSet<T> result = new ModelSet<>(this);
        result.addAll(other);
        return result;
    }

    public ModelSet<T> intersection(ModelSet<T> other) {
        ModelSet<T> result = new ModelSet<>(this);
        result.retainAll(other);
        return result;
    }

    public ModelSet<T> difference(ModelSet<T> other) {
        ModelSet<T> result = new ModelSet<>(this);
        result.removeAll(other);
        return result;
    }

}