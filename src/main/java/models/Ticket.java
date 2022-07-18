package models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Agustin Nolasco
 */
public class Ticket extends Withholding implements Model{

    private final Map<String, Object> values;
    private HashMap<String, Class<?>> types;

    public Ticket(Map<String, Object> values) {
        this.values = new HashMap<>(values);
        setTypes();
        assert repOk();
    }

    private void setTypes() {
        types = new HashMap<String, Class<?>>() {{
            put("withholding", Withholding.class);
            put("type", String.class);
            put("numberTo", Integer.class);
            put("authCode", String.class);
            put("exchangeType", Float.class);
            put("exchangeMoney", String.class);
            put("netAmountWI", Float.class);
            put("netAmountWOI", Float.class);
            put("amountImpEx", Float.class);
            put("ivaTax", Float.class);
            put("totalAmount", Float.class);
            put("issuedByMe", Boolean.class);
        }};
    }

    public Map<String, Class<?>> getTypes() {
        return new HashMap<>(types);
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

    @Override
    public Map<String, Object> getID() {
        return Collections.singletonMap("id", ((Withholding)values.get("withholding")).getID());
    }

    public boolean isIncome() {
        boolean isCredit = !((String) values.get("type")).contains("Crédito");
        return (Boolean) values.get("issuedByMe") == isCredit;
    }

    public static Set<String> getAttributes() {
        return Stream.of("withholding", "numberTo", "authCode", "type", "exchangeType", "exchangeMoney", "netAmountWI",
                        "netAmountWOI", "amountImpEx", "ivaTax", "totalAmount", "issuedByMe")
                .collect(Collectors.toSet());
    }

    private boolean repOk() {
        if (!getAttributes().containsAll(values.keySet())) {
            return false;
        }

        for (String key : values.keySet()) {
            Object value = values.get(key);
            if (value != null) {
                if (value.getClass() != types.get(key)) {
                    return false;
                }
            }
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