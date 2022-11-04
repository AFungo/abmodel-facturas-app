package builder;

import models.Ticket;
import models.Withholding;
import utils.Parser;

import java.util.HashMap;
import java.util.Map;

public class TicketBuilder implements Builder{

    @Override
    public Object build(Object... data) {
        Withholding withholding = (Withholding)data[0];

        Map<String, Object> ticketValues = new HashMap<String, Object>(){{
            put("withholding", withholding);
            put("type", (String) data[1]);
            put("numberTo", Integer.valueOf((String)data[2]));
            put("authCode", (String) data[3]);
            put("exchangeType", Float.parseFloat((String) data[4]));
            put("exchangeMoney", (String) data[5]);
            put("netAmountWI", Float.parseFloat((String) data[6]));
            put("netAmountWOI", Parser.parseFloat((String)data[7]));
            put("amountImpEx", Parser.parseFloat((String)data[8]));
            put("ivaTax", Parser.parseFloat((String)data[9]));
            put("totalAmount", Float.parseFloat((String)data[10]));
            put("issuedByMe", (Boolean) data[11]);
        }};

        return new Ticket(ticketValues);

    }
}
