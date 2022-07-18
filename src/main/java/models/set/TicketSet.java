package models.set;

import models.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class TicketSet extends HashSet<Ticket> {

    public TicketSet() {
        super();
    }

    private TicketSet(Collection<Ticket> c) {
        super(c);
    }

    public TicketSet filterByEqualsTo(String attribute, Object value) {
        return new TicketSet(stream()
                .filter(t -> t.getValues().get(attribute).equals(value))
                .collect(Collectors.toSet())
        );
    }

    public TicketSet filterByGreaterOrEqualsThan(String attribute, Object value) {
        return new TicketSet(stream()
                .filter(t -> ((Comparable) t.getValues().get(attribute)).compareTo(value) >= 0)
                .collect(Collectors.toSet())
        );
    }

    public TicketSet filterByLessOrEqualsThan(String attribute, Object value) {
        return new TicketSet(stream()
                .filter(t -> ((Comparable)t.getValues().get(attribute)).compareTo(value) <= 0)
                .collect(Collectors.toSet())
        );
    }

    public TicketSet union(TicketSet other) {
        TicketSet result = new TicketSet(this);
        result.addAll(other);
        return result;
    }

    public TicketSet intersection(TicketSet other) {
        TicketSet result = new TicketSet(this);
        result.retainAll(other);
        return result;
    }

    public TicketSet difference(TicketSet other) {
        TicketSet result = new TicketSet(this);
        result.removeAll(other);
        return result;
    }

}