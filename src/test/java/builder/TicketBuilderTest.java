package builder;

import models.Provider;
import models.Sector;
import models.Ticket;
import models.Withholding;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketBuilderTest {

    @ParameterizedTest
    @MethodSource("ticketDataGenerator")
    void buildTicketTest(Withholding withholding, String type, Integer numberTo, String authCode,
                         Float exchangeType, String exchangeMoney, Float netAmountWI,
                         Float netAmountWOI, Float amountImpEx, Float ivaTax,
                         Float totalAmount, boolean issuedByMe) {

        Ticket expectedTicket = new Ticket(
                new HashMap<String, Object>() {{
                    put("withholding", withholding);
                    put("type", type);
                    put("numberTo", numberTo);
                    put("authCode", authCode);
                    put("exchangeType", exchangeType);
                    put("exchangeMoney", exchangeMoney);
                    put("netAmountWI", netAmountWI);
                    put("netAmountWOI", netAmountWOI);
                    put("amountImpEx", amountImpEx);
                    put("ivaTax", ivaTax);
                    put("totalAmount", totalAmount);
                    put("issuedByMe", issuedByMe);
            }}
        );

        Builder tb = new TicketBuilder();
            Ticket ticket = (Ticket)tb.build(withholding, type, numberTo.toString(), authCode, exchangeType.toString(),
                    exchangeMoney, netAmountWI.toString(), netAmountWOI.toString(), amountImpEx.toString(),
                    ivaTax.toString(), totalAmount.toString(), issuedByMe
        );

        assertEquals(expectedTicket.getValues() ,ticket.getValues());
    }

    static Stream<Arguments> ticketDataGenerator() {

        Provider provider = new Provider(
                new HashMap<String, Object>(){{
                    put("id", 2);
                    put("docNo", "321213");
                    put("name", "Carlo");
                    put("docType", "CUIT");
                    put("address", "Calle falsa 123");
                    put("provSector", "Agricultura");
                    put("alias", "Carlito el pillo");
                }}
        );
        Withholding withholding = new Withholding(
                new HashMap<String, Object>(){{
                    put("id", 123);
                    put("date", Date.valueOf("2022-03-10"));
                    put("number", "123");
                    put("provider", provider);
                    put("iva", (float)123.2);
                    put("profits", (float)322.2);
                    put("delivered", false);
                    put("sector", null);
                }}
        );
        return Stream.of(
                Arguments.of(withholding, "Factura A", 1234, "1", (float)200.33, "Dollars",
                        (float) 23000.43, (float)2131.23, (float)231311.43, (float)321321.12,
                        (float)2121.32, true)
        );
    }
}