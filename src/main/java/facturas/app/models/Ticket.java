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
        return new HashMap<>(this.values);
    }

    public boolean isIncome() {
        boolean isCredit = !((String) values.get("type")).contains("Crédito");
        return (Boolean) values.get("issuedByMe") == isCredit;
    }

    protected static Set<String> requiredKeys() {
        return Stream.of("numberTo", "authCode", "exchangeType", "totalAmount", "exchangeMoney", "type", "netAmountWI",
                        "netAmountWOI", "amountImpEx", "ivaTax", "issuedByMe", "withholding")
                .collect(Collectors.toSet());
    }

    private boolean repOk() {
        if (!requiredKeys().containsAll(values.keySet())) {
            return false;
        }
        return values.get("exchangeType") != null && values.get("totalAmount") != null &&
                values.get("exchangeMoney") != null && values.get("type") != null && values.get("withholding") !=null;
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