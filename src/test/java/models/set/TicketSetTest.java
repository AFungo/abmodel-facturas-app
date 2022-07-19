package models.set;

import models.Provider;
import models.Ticket;
import models.Withholding;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class TicketSetTest {

    Ticket ticket;

    @BeforeEach
    void setUp() {
        Provider provider = new Provider(new HashMap<String, Object>() {{
            put("docNo", "45123123");
            put("name", "Manolito");
            put("docType", "CUIT");
        }});

        Withholding withholding = new Withholding(new HashMap<String, Object>() {{
            put("date", Date.valueOf("2018-12-25"));
            put("number", "0123456789");
            put("provider", provider);
        }});

        ticket = new Ticket(new HashMap<String, Object>() {{
            put("withholding", withholding);
            put("type", "6 - Factura B");
            put("exchangeType", 1.5f);
            put("exchangeMoney", "$");
            put("totalAmount", 1000.0f);
            put("issuedByMe", true);
        }});
    }

    @Test
    void testFilterByEqualsTo() {
        ModelSet<Ticket> tickets = new ModelSet<>();
        tickets.add(ticket);
        ModelSet<Ticket> ticketsB = tickets.filterByEqualsTo("type", "6 - Factura B");
        ModelSet<Ticket> ticketsC = tickets.filterByEqualsTo("type", "7 - Factura C");
        assertThat(ticketsB).contains(ticket);
        assertThat(ticketsC).doesNotContain(ticket);
    }

    @Test
    void testFilterByGreaterThan() {
        ModelSet<Ticket> tickets = new ModelSet<>();
        tickets.add(ticket);
        ModelSet<Ticket> ticketsLess = tickets.filterByGreaterOrEqualsThan("exchangeType", 1.0f);
        ModelSet<Ticket> ticketsEquals = tickets.filterByGreaterOrEqualsThan("exchangeType", 1.5f);
        ModelSet<Ticket> ticketsGreater = tickets.filterByGreaterOrEqualsThan("exchangeType", 2.0f);
        assertThat(ticketsLess).contains(ticket);
        assertThat(ticketsEquals).contains(ticket);
        assertThat(ticketsGreater).doesNotContain(ticket);
    }

    @Test
    void testFilterByLessThan() {
        ModelSet<Ticket> tickets = new ModelSet<>();
        tickets.add(ticket);
        ModelSet<Ticket> ticketsLess = tickets.filterByLessOrEqualsThan("totalAmount", 899.0f);
        ModelSet<Ticket> ticketsEquals = tickets.filterByLessOrEqualsThan("totalAmount", 1000f);
        ModelSet<Ticket> ticketsGreater = tickets.filterByLessOrEqualsThan("totalAmount", 1000.1f);
        assertThat(ticketsLess).doesNotContain(ticket);
        assertThat(ticketsEquals).contains(ticket);
        assertThat(ticketsGreater).contains(ticket);
    }

}
