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
public class Ticket extends Withholding {

    private final Map<String, Object> values;

    public Ticket(Map<String, Object> values) {
        super(withholdingValues(values));
        this.values = new HashMap<>(values);
        this.values.keySet().removeAll(Withholding.requiredKeys());
        assert repOk();
    }

    @Override
    public void setValues(Map<String, Object> values) {
        Map<String, Object> newWithholdingValues = new HashMap<>(values);
        newWithholdingValues.keySet().retainAll(Withholding.requiredKeys());
        super.setValues(newWithholdingValues);
        Map<String, Object> newTicketValues = new HashMap<>(values);
        newTicketValues.keySet().retainAll(requiredKeys());
        for (String s : newTicketValues.keySet()) {
            this.values.put(s, values.get(s));
        }
        assert repOk();
    }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>(this.values);
        values.putAll(super.getValues());
        return values;
    }

    public boolean isIncome() {
        boolean isCredit = !((String) values.get("type")).contains("Crédito");
        return (Boolean) values.get("issuedByMe") == isCredit;
    }

    private static Map<String, Object> withholdingValues(Map<String, Object> values) {
        Map<String, Object> withholdingValues = new HashMap<>();
        for (String key : Withholding.requiredKeys()) {
            withholdingValues.put(key, values.get(key));
        }
        return withholdingValues;
    }

    protected static Set<String> requiredKeys() {
        return Stream.of("numberTo", "authCode", "exchangeType", "totalAmount", "exchangeMoney", "type", "netAmountWI",
                        "netAmountWOI", "amountImpEx", "ivaTax", "issuedByMe")
                .collect(Collectors.toSet());
    }

    private boolean repOk() {
        if (!requiredKeys().containsAll(values.keySet())) {
            return false;
        }
        return values.get("exchangeType") != null && values.get("totalAmount") != null
                && values.get("exchangeMoney") != null && values.get("type") != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return values.equals(ticket.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

}